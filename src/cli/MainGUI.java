package cli;

import modelo.ChatResponse;
import modelo.Documento;
import modelo.Orientacao;
import servico.ChatPdfService;
import servico.DocumentoService;

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
        // Inicia a interface gráfica de forma segura
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }

    public MainGUI() {
        setTitle("Gerenciador de Documentos e IA");
        setSize(900, 600);
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

        // >>> ADICIONE ESTA LINHA AQUI <<<
        listaVisualOrientacoes.setCellRenderer(new OrientacaoRenderer());

        // Centro do painel central (Lista de Orientações)
        modeloListaOrientacoes = new DefaultListModel<>();
        listaVisualOrientacoes = new JList<>(modeloListaOrientacoes);
        listaVisualOrientacoes.setCellRenderer(new OrientacaoRenderer());

        JPanel painelOrientacoes = new JPanel(new BorderLayout());
        painelOrientacoes.setBorder(BorderFactory.createTitledBorder("Orientações do Documento"));

        // --- NOVO: Campo de Filtro por ID ---
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
        painelOrientacoes.add(painelFiltro, BorderLayout.NORTH); // Coloca o filtro no topo

        painelOrientacoes.add(new JScrollPane(listaVisualOrientacoes), BorderLayout.CENTER);
        painelOrientacoes.setBorder(BorderFactory.createTitledBorder("Orientações do Documento"));
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
        painelIA.setPreferredSize(new Dimension(300, 0));
        painelIA.setBorder(BorderFactory.createTitledBorder("ChatPDF IA"));

        areaChat = new JTextArea();
        areaChat.setEditable(false);
        areaChat.setLineWrap(true);
        areaChat.setWrapStyleWord(true);
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
            // Guarda a lista original na nossa nova variável
            listaCompletaOrientacoes = documentoService.listarOrientacoes(documentoSelecionado);
            if (listaCompletaOrientacoes != null) {
                for (Orientacao ori : listaCompletaOrientacoes) {
                    modeloListaOrientacoes.addElement(ori);
                }
            }
        } else {
            listaCompletaOrientacoes = null;
        }

        // Limpa o campo de filtro ao trocar de documento
        if (campoFiltroId != null) {
            campoFiltroId.setText("");
        }
    }

    // --- NOVO: Método que filtra a lista visual ---
    private void aplicarFiltroId() {
        if (listaCompletaOrientacoes == null) return;

        String idBusca = campoFiltroId.getText().trim();
        modeloListaOrientacoes.clear(); // Limpa a tela

        if (idBusca.isEmpty()) {
            // Se o filtro estiver vazio, mostra tudo de novo
            for (Orientacao ori : listaCompletaOrientacoes) {
                modeloListaOrientacoes.addElement(ori);
            }
        } else {
            // Se tiver texto, filtra verificando se o ID contém o número digitado
            for (Orientacao ori : listaCompletaOrientacoes) {
                if (String.valueOf(ori.getId()).contains(idBusca)) {
                    modeloListaOrientacoes.addElement(ori);
                }
            }
        }
    }

    // Método auxiliar para criar uma janela com suporte a quebra de linha
    private String solicitarTextoMultilinha(String tituloDialogo, String textoInicial) {
        JTextArea areaTexto = new JTextArea(10, 40); // 10 linhas, 40 colunas
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

        // Importante: certifique-se de que a classe Orientacao possui os métodos getTitulo() e getConteudo()
        String novoTitulo = JOptionPane.showInputDialog(this, "Novo título:", ori.getTitle());
        if (novoTitulo == null) return;

        String novoConteudo = solicitarTextoMultilinha("Nova descrição:", ori.getContent());
        if (novoConteudo == null || novoConteudo.trim().isEmpty()) return;

        // Importante: certifique-se de que a classe Orientacao possui o método getId()
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
            long id = ori.getId(); // Certifique-se do getId()
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
        campoPergunta.setText(""); // limpa o campo

        // Colocando um aviso visual antes da requisição
        areaChat.append("⏳ Pensando...\n");

        // Thread separada para não travar a UI
        new Thread(() -> {
            try {
                ChatResponse resposta = chatPdfService.enviarPergunta(documentoSelecionado, pergunta);

                SwingUtilities.invokeLater(() -> {
                    // Substitui o "⏳ Pensando..." pela resposta real (lógica simples adicionando o texto abaixo)
                    if (resposta != null && resposta.getContent() != null) {
                        areaChat.append("🤖 IA: " + resposta.getContent() + "\n\n");
                    } else {
                        areaChat.append("❌ Erro na resposta da API.\n\n");
                    }
                    // Move o scroll para o final
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
            // Cria um espaçamento interno e uma linha cinza para separar os itens
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Orientacao> list, Orientacao value, int index, boolean isSelected, boolean cellHasFocus) {
            // Formata como o texto vai aparecer na lista (ajuste os getters se necessário)
            setText("📌 [ID: " + value.getId() + "] " + value.getTitle() + "\n" + value.getContent());

            // Calcula a largura correta para a quebra de linha funcionar dinamicamente
            int width = list.getWidth();
            if (width > 0) {
                setSize(new Dimension(width, Short.MAX_VALUE));
            }

            // Altera a cor se o usuário clicar/selecionar o item
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