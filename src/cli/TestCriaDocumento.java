import configuracao.ApiConfig;
import modelo.ChatResponse;
import modelo.Documento;
import modelo.Orientacao;
import servico.ChatPdfService;
import servico.DocumentoService;

import static java.lang.IO.readln;

void main() {
    //LISTAR DOCUMENTOS
    DocumentoService documentoservice = new DocumentoService();
    ChatPdfService chatPdfService = new ChatPdfService();




    int seletorNumerico = -1;
    int seletorDocumento = -1;
    int seletorNumericoOrientacao = -1;
    while (seletorNumerico != 0) {
        System.out.println("Lista de opções");
        System.out.println("0 - para sair do documento");
        System.out.println("1 - Listar Documentos");
        System.out.println("2 - Criar Novo Documento");
        System.out.println("3 - Selecionar Documento");

        seletorNumerico = Integer.parseInt(readln("Selecione:\n"));
        String[] listaDocumentos = documentoservice.listarDocumentos();

        switch (seletorNumerico) {
            case 0:
                break;

            case 1:

                System.out.println("Lista de documentos");
                if (listaDocumentos != null) {
                    for (int i = 0; i < listaDocumentos.length; i++) {
                        System.out.println(i + 1 + " - " + listaDocumentos[i]);
                    }
                }
                System.out.println();

                break;
            case 2:

                String nomeNovoDocumento = readln("Digite o nome para o novo Documento");
                documentoservice.criarDocumento(nomeNovoDocumento);
                break;
            case 3:
                seletorDocumento = Integer.parseInt(readln("Qual documento deseja selecionar?\n"));
                Documento documento = documentoservice.buscarDocumento(listaDocumentos[seletorDocumento - 1]);
                System.out.println("Documento Selecionado");
                System.out.println(documento);
                String nomeDoc = documento.getNome();
                chatPdfService.gerarPdfEmMemoria(documento.getNome(),documento.getOrientacoes());
                seletorNumericoOrientacao = -1;
                while (seletorNumericoOrientacao != 0) {
                    System.out.println("0 - para sair do documento");
                    System.out.println("1 - Listar Orientações");
                    System.out.println("2 - Criar nova orientação");
                    System.out.println("3 - Alterar orientação");
                    System.out.println("4 - Deletar orientação");
                    System.out.println("5 - Deletar documento");
                    System.out.println("6 - Perguntar para IA");
                    System.out.println("7 - Atualizar Id documento");

                    seletorNumericoOrientacao = Integer.parseInt(readln("Selecione uma opção"));

                    String titulo = null;
                    String conteudo = null;

                    long id;

                    switch (seletorNumericoOrientacao) {
                        case 0:
                            System.out.println("Saindo do documento...");
                            break;

                        case 1:
                            List<Orientacao> listaOrientacoes = documentoservice.listarOrientacoes(documento);

                            if (!listaOrientacoes.isEmpty()) {
                                for (int i = 0; i < listaOrientacoes.size(); i++) {
                                    System.out.println(listaOrientacoes.get(i));
                                }
                            } else {
                                System.out.println("Sem orientações");
                            }

                            break;

                        case 2:
                            titulo = readln("Digite o titulo da orientação:\n");
                            conteudo = readln("Digite a descrição:\n");

                            documentoservice.incluirOrientacao(nomeDoc, titulo, conteudo);
                            System.out.println("Documento atualizado...");

                            break;

                        case 3:
                            id = Long.parseLong(readln("Qual item deseja editar?\n"));
                            titulo = readln("Digite o titulo da orientação:\n");
                            conteudo = readln("Digite a descrição:\n");

                            documentoservice.editarOrientacao(documento, id, titulo, conteudo);
                            System.out.println("Documento atualizado...");



                            break;

                        case 4:
                            id = Long.parseLong(readln("Qual item deseja editar?\n"));

                            if (documentoservice.deletarOrientacao(documento, id)) {
                                System.out.println("Item removido com sucesso...");
                            } else {
                                System.out.println("!!! Erro ao Deletar !!!");
                            }
                            break;

                        case 5:
                            if (documentoservice.deletarDocumento(documento)) {
                                System.out.print("Item removido com sucesso");
                            } else {
                                System.out.println("Erro ao deletar");
                            }
                            break;

                        case 6:

                            String textoPergunta = IO.readln("Digite sua pergunta\n");

                            ChatResponse resposta = chatPdfService.enviarPergunta(documento, textoPergunta);
                            System.out.print("⏳ Enviando pergunta para ChatPDF");

                            if (resposta != null && resposta.getContent() != null) {
                                System.out.println("\n✅ RESPOSTA DO CHATPDF:");
                                System.out.println("📄 " + resposta.getContent());
                            } else {
                                System.out.println("\n❌ Erro na resposta da API");
                            }
                            break;

                        case 7:
                            String sorceId = readln("Digite o id do sorce:\n");
                            documentoservice.atualizarSorceID(documento, sorceId);
                            break;
                        default:
                            System.out.println("!!! Valor invalido !!!");
                            break;

                    }

                }


                break;

            default:
                System.out.print("Opção invalida");
                break;
        }

    }




}