package main.java.cli;

import main.java.modelo.ChatResponse;
import main.java.modelo.Documento;
import main.java.modelo.Orientacao;
import main.java.servico.ChatPdfService;
import main.java.servico.DocumentoService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {

    // Serviços
    private DocumentoService documentoService = new DocumentoService();
    private ChatPdfService chatPdfService = new ChatPdfService();

    // Componentes da Interface
    private DefaultListModel<String> modeloListaDocumentos;
    private JList<String> listaVisalDocumentos;

    private DefaultListModel<Orientacao> modeloListaOrientacoes;
    private JList<Orientacao> listaVisualOrientacoes;

    private JTextArea areaChat;
    private JTextField campoPergunta;
    private JTextField campoFiltroId;
    private List<Orientacao> listaCompletaOrientacoes;

    // Estado da aplicação
    private Documento documentoSelecionado = null;

    public static void main(String[] args) {
        // Aplica o tema escuro antes de iniciar qualquer componente visual
        aplicarTemaEscuro();

        // Inicia a interface gráfica de forma segura
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }

    // --- MAGIA DO DARK MODE AQUI ---
    private static void aplicarTemaEscuro() {
        try {
            // Tenta usar o Nimbus LookAndFeel (nativo, mas mais bonito que o padrão Metal)
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Define a paleta de cores do Dark Mode
            Color fundoBase = new Color(43, 43, 43);       // Cinza bem escuro para o fundo da janela
            Color fundoPaineis = new Color(60, 63, 65);    // Cinza médio para listas e áreas de texto
            Color textoClaro = new Color(187, 187, 187);   // Texto cinza claro (não branco puro para não cansar a vista)
            Color textoBranco = Color.WHITE;               // Texto para itens selecionados
            Color destaqueAzul = new Color(75, 110, 175);  // Azul suave para botões e seleções

            // Injeta as cores no UIManager
            UIManager.put("control", fundoBase);
            UIManager.put("info", fundoBase);
            UIManager.put("nimbusBase", fundoBase);
            UIManager.put("nimbusAlertYellow", new Color(255, 220, 35));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", destaqueAzul);
            UIManager.put("nimbusGreen", new Color(176, 179, 50));
            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
            UIManager.put("nimbusLightBackground", fundoPaineis);
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(169, 46, 34));
            UIManager.put("nimbusSelectedText", textoBranco);
            UIManager.put("nimbusSelectionBackground", destaqueAzul);
            UIManager.put("text", textoClaro);
            UIManager.put("TitledBorder.titleColor", textoClaro); // Cor dos títulos das bordas
            UIManager.put("Label.foreground", textoClaro); // Cor dos textos (labels)

            // Ajusta a cor das caixas de diálogo (JOptionPane)
            UIManager.put("OptionPane.background", fundoBase);
            UIManager.put("Panel.background", fundoBase);

        } catch (Exception e) {
            System.err.println("Não foi possível aplicar o tema escuro. Usando o padrão.");
        }
    }

    public MainGUI() {
        setTitle("Gerenciador de Documentos e IA");
        setSize(1000, 650); // Aumentei um pouco a janela para ficar mais elegante
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new BorderLayout());

        inicializarComponentes();
        carregarDocumentos();
    }

    private void inicializarComponentes() {
        // --- PAINEL ESQUERDO: Lista de Documentos ---
        modeloListaDocumentos = new DefaultListModel<>();
        listaVisalDocumentos = new JList<>(modeloListaDocumentos);
        listaVisalDocumentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaVisalDocumentos.addListSelectionListener(e -> selecionarDocumento());

        JPanel painelEsquerdo = new JPanel(new BorderLayout());
        painelEsquerdo.setPreferredSize(new Dimension(250, 0));
        painelEsquerdo.setBorder(BorderFactory.createTitledBorder("Documentos"));
        painelEsquerdo.add(new JScrollPane(listaVisalDocumentos), BorderLayout.CENTER);

        JButton btnNovoDoc = new JButton("Novo Documento");
        btnNovoDoc.addActionListener(e -> criarNovoDocumento());
        painelEsquerdo.add(btnNovoDoc, BorderLayout.SOUTH);

        add(painelEsquerdo, BorderLayout.WEST);

        // --- PAINEL CENTRAL: Orientações e Ações do Documento ---
        JPanel painelCentral = new JPanel(new BorderLayout());

        // Topo do painel central (Ações do Documento)
        JPanel painelAcoesDoc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDeletarDoc = new JButton("Deletar Documento");
        JButton btnAtualizarSource = new JButton("Atualizar Source ID");

        btnDeletarDoc.addActionListener(e -> deletarDocumentoSelecionado());
        btnAtualizarSource.addActionListener(e -> atualizarSourceId());

        painelAcoesDoc.add(btnDeletarDoc);
        painelAcoesDoc.add(btnAtualizarSource);
        painelCentral.add(painelAcoesDoc, BorderLayout.NORTH);

        // Centro do painel central (Lista de Orientações)
        modeloListaOrientacoes = new DefaultListModel<>();
        listaVisualOrientacoes = new JList<>(modeloListaOrientacoes);
        listaVisualOrientacoes.setCellRenderer(new OrientacaoRenderer());

        JPanel painelOrientacoes = new JPanel(new BorderLayout());
        painelOrientacoes.setBorder(BorderFactory.createTitledBorder("Orientações do Documento"));

        // --- Campo de Filtro por ID ---
        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.add(new JLabel("Filtrar por ID:"));
        campoFiltroId = new JTextField(10);

        // Adiciona um "ouvinte" que filtra a lista automaticamente enquanto você digita
        campoFiltroId.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltroId(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltroId(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aplicarFiltroId(); }
        });
        painelFiltro.add(campoFiltroId);
        painelOrientacoes.add(painelFiltro, BorderLayout.NORTH);

        // Adiciona a lista de orientações
        painelOrientacoes.add(new JScrollPane(listaVisualOrientacoes), BorderLayout.CENTER);

        // Botões para Orientações
        JPanel painelBotoesOri = new JPanel(new FlowLayout());
        JButton btnNovaOri = new JButton("Nova");
        JButton btnEditarOri = new JButton("Editar");
        JButton btnExcluirOri = new JButton("Excluir");

        btnNovaOri.addActionListener(e -> criarOrientacao());
        btnEditarOri.addActionListener(e -> editarOrientacao());
        btnExcluirOri.addActionListener(e -> excluirOrientacao());

        painelBotoesOri.add(btnNovaOri);
        painelBotoesOri.add(btnEditarOri);
        painelBotoesOri.add(btnExcluirOri);
        painelOrientacoes.add(painelBotoesOri, BorderLayout.SOUTH);

        painelCentral.add(painelOrientacoes, BorderLayout.CENTER);
        add(painelCentral, BorderLayout.CENTER);

        // --- PAINEL DIREITO: Área da IA ---
        JPanel painelIA = new JPanel(new BorderLayout());
        painelIA.setPreferredSize(new Dimension(320, 0)); // Um pouquinho mais largo para melhor leitura
        painelIA.setBorder(BorderFactory.createTitledBorder("ChatPDF IA"));

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);

        // Margem interna para o texto não colar na borda da IA
        areaChat.setMargin(new Insets(10, 10, 10, 10));

        painelIA.add(new JScrollPane(areaChat), BorderLayout.CENTER);

        JPanel painelPergunta = new JPanel(new BorderLayout());
        campoPergunta = new JTextField();
        JButton btnEnviar = new JButton("Perguntar");
        btnEnviar.addActionListener(e -> enviarPerguntaIA());

        // Permite enviar a pergunta apertando "Enter" no campo de texto
        campoPergunta.addActionListener(e -> enviarPerguntaIA());

        painelPergunta.add(campoPergunta, BorderLayout.CENTER);
        painelPergunta.add(btnEnviar, BorderLayout.EAST);
        painelIA.add(painelPergunta, BorderLayout.SOUTH);

        add(painelIA, BorderLayout.EAST);
    }

    // =========================================================
    // MÉTODOS DE AÇÃO
    // =========================================================

    private void carregarDocumentos() {
        modeloListaDocumentos.clear();
        String[] lista = documentoService.listarDocumentos();
        if (lista != null) {
            for (String docName : lista) {
                modeloListaDocumentos.addElement(docName);
            }
        }
    }

    private void criarNovoDocumento() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome para o novo Documento:");
        if (nome != null && !nome.trim().isEmpty()) {
            documentoService.criarDocumento(nome);
            carregarDocumentos();
        }
    }

    private void selecionarDocumento() {
        String nomeDoc = listaVisalDocumentos.getSelectedValue();
        if (nomeDoc != null) {
            documentoSelecionado = documentoService.buscarDocumento(nomeDoc);
            carregarOrientacoes();
            areaChat.setText("Documento selecionado: " + nomeDoc + "\nPronto para perguntas.\n\n");
        } else {
            documentoSelecionado = null;
            modeloListaOrientacoes.clear();
        }
    }

    private void deletarDocumentoSelecionado() {
        if (documentoSelecionado == null) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente deletar este documento?", "Aviso", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            documentoService.deletarDocumento(documentoSelecionado);
            carregarDocumentos();
            documentoSelecionado = null;
            modeloListaOrientacoes.clear();
        }
    }

    private void atualizarSourceId() {
        if (documentoSelecionado == null) return;
        String sourceId = JOptionPane.showInputDialog(this, "Digite o novo Source ID:");
        if (sourceId != null && !sourceId.trim().isEmpty()) {
            documentoService.atualizarSorceID(documentoSelecionado, sourceId);
            JOptionPane.showMessageDialog(this, "Source ID atualizado com sucesso.");
        }
    }

    private void carregarOrientacoes() {
        modeloListaOrientacoes.clear();
        if (documentoSelecionado != null) {
            listaCompletaOrientacoes = documentoService.listarOrientacoes(documentoSelecionado);
            if (listaCompletaOrientacoes != null) {
                for (Orientacao ori : listaCompletaOrientacoes) {
                    modeloListaOrientacoes.addElement(ori);
                }
            }
        } else {
            listaCompletaOrientacoes = null;
        }

        if (campoFiltroId != null) {
            campoFiltroId.setText("");
        }
    }

    private void aplicarFiltroId() {
        if (listaCompletaOrientacoes == null) return;

        String idBusca = campoFiltroId.getText().trim();
        modeloListaOrientacoes.clear();

        if (idBusca.isEmpty()) {
            for (Orientacao ori : listaCompletaOrientacoes) {
                modeloListaOrientacoes.addElement(ori);
            }
        } else {
            for (Orientacao ori : listaCompletaOrientacoes) {
                if (String.valueOf(ori.getId()).contains(idBusca)) {
                    modeloListaOrientacoes.addElement(ori);
                }
            }
        }
    }

    private String solicitarTextoMultilinha(String tituloDialogo, String textoInicial) {
        JTextArea areaTexto = new JTextArea(10, 40);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);

        if (textoInicial != null) {
            areaTexto.setText(textoInicial);
        }

        JScrollPane scrollPane = new JScrollPane(areaTexto);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                tituloDialogo,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            return areaTexto.getText();
        }
        return null;
    }

    private void criarOrientacao() {
        if (documentoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um documento primeiro.");
            return;
        }

        String titulo = JOptionPane.showInputDialog(this, "Título da Orientação:");
        if (titulo == null) return;

        String conteudo = solicitarTextoMultilinha("Digite a Descrição/Conteúdo:", "");
        if (conteudo == null || conteudo.trim().isEmpty()) return;

        documentoSelecionado = documentoService.incluirOrientacao(documentoSelecionado, titulo, conteudo);
        carregarOrientacoes();
    }

    private void editarOrientacao() {
        Orientacao ori = listaVisualOrientacoes.getSelectedValue();
        if (ori == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma orientação na lista para editar.");
            return;
        }

        String novoTitulo = JOptionPane.showInputDialog(this, "Novo título:", ori.getTitle());
        if (novoTitulo == null) return;

        String novoConteudo = solicitarTextoMultilinha("Nova descrição:", ori.getContent());
        if (novoConteudo == null || novoConteudo.trim().isEmpty()) return;

        long id = ori.getId();
        documentoSelecionado = documentoService.editarOrientacao(documentoSelecionado, id, novoTitulo, novoConteudo);
        carregarOrientacoes();
    }

    private void excluirOrientacao() {
        Orientacao ori = listaVisualOrientacoes.getSelectedValue();
        if (ori == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma orientação na lista para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir esta orientação?", "Aviso", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            long id = ori.getId();
            boolean sucesso = documentoService.deletarOrientacao(documentoSelecionado, id);
            if (sucesso) {
                carregarOrientacoes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao deletar orientação.");
            }
        }
    }

    private void enviarPerguntaIA() {
        if (documentoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um documento primeiro!");
            return;
        }

        String pergunta = campoPergunta.getText();
        if (pergunta.trim().isEmpty()) return;

        areaChat.append("Você: " + pergunta + "\n");
        campoPergunta.setText("");

        areaChat.append("⏳ Pensando...\n");

        new Thread(() -> {
            try {
                ChatResponse resposta = chatPdfService.enviarPergunta(documentoSelecionado, pergunta);

                SwingUtilities.invokeLater(() -> {
                    if (resposta != null && resposta.getContent() != null) {
                        areaChat.append("🤖 IA: " + resposta.getContent() + "\n\n");
                    } else {
                        areaChat.append("❌ Erro na resposta da API.\n\n");
                    }
                    areaChat.setCaretPosition(areaChat.getDocument().getLength());
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    areaChat.append("❌ Erro ao conectar com IA: " + e.getMessage() + "\n\n");
                });
            }
        }).start();
    }

    // --- Renderizador Customizado para as Orientações ---
    class OrientacaoRenderer extends JTextArea implements ListCellRenderer<Orientacao> {
        public OrientacaoRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);

            // Alterei a cor da linha divisória para ficar harmoniosa com o Dark Mode
            Color corDaLinha = new Color(80, 80, 80); // Cinza mais escurinho

            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, corDaLinha),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Orientacao> list, Orientacao value, int index, boolean isSelected, boolean cellHasFocus) {
            setText("📌 [ID: " + value.getId() + "] " + value.getTitle() + "\n" + value.getContent());

            int width = list.getWidth();
            if (width > 0) {
                setSize(new Dimension(width, Short.MAX_VALUE));
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            return this;
        }
    }
}