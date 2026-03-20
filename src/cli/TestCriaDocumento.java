import modelo.Orientacao;
import servico.DocumentoService;

import static java.lang.IO.readln;

void main()  {
    //LISTAR DOCUMENTOS
    var service = new DocumentoService();



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
        String[] listaDocumentos = service.listarDocumentos();

        switch (seletorNumerico){
            case 0:
                break;

            case 1:

                System.out.println("Lista de documentos");
                if (listaDocumentos != null){
                    for (int i = 0; i < listaDocumentos.length; i++) {
                        System.out.println(i+1 +" - "+listaDocumentos[i]);
                    }
                }
                System.out.println();

                break;
            case 2:

                String nomeNovoDocumento = readln("Digite o nome para o novo Documento");
                service.createDocument(nomeNovoDocumento);
                break;
            case 3:
                seletorDocumento = Integer.parseInt(readln("Qual documento deseja selecionar?\n"));
                var document = service.getDocumento(listaDocumentos[seletorDocumento - 1]);
                System.out.println("Documento Selecionado");
                System.out.println(service.toString());
                seletorNumericoOrientacao = -1;
                while (seletorNumericoOrientacao != 0){
                    System.out.println("0 - para sair do documento");
                    System.out.println("1 - Listar Orientações");
                    System.out.println("2 - Criar nova orientação");
                    System.out.println("3 - Alterar orientação");
                    System.out.println("4 - Deletar orientação");
                    System.out.println("5 - Deletar documento");

                    seletorNumericoOrientacao = Integer.parseInt(readln("Selecione uma opção"));

                    String nome = null;
                    String conteudo = null;
                    long id;

                    switch (seletorNumericoOrientacao){
                        case 0:
                            System.out.println("Saindo do documento...");
                            break;

                        case 1:
                            List<Orientacao> listaOrientacoes = service.listarOrientacoes();

                            if (!listaOrientacoes.isEmpty()){
                                for (int i = 0; i < listaOrientacoes.size(); i++) {
                                    System.out.println(listaOrientacoes.get(i));
                                }
                            }else {
                                System.out.println("Sem orientações");
                            }

                            break;

                        case 2:
                            nome = readln("Digite o titulo da orientação:\n");
                            conteudo = readln("Digite a descrição:\n");

                            if (service.includeOrientation(nome, conteudo)){
                                System.out.println("Documento atualizado...");
                            }else {
                                System.out.println("!!! Erro ao salvar !!!");
                            }

                            break;

                        case 3:
                            id = Long.parseLong(readln("Qual item deseja editar?\n"));
                            nome = readln("Digite o titulo da orientação:\n");
                            conteudo = readln("Digite a descrição:\n");

                            if (service.setOrientation(id, nome, conteudo)){
                                System.out.println("Documento atualizado...");
                            }else {
                                System.out.println("!!! Erro ao salvar !!!");
                            }

                            break;

                        case 4:
                            id = Long.parseLong(readln("Qual item deseja editar?\n"));

                            if (service.deletarOrientacao(id)){
                                System.out.println("Item removido com sucesso...");
                            }else {
                                System.out.println("!!! Erro ao Deletar !!!");
                            }
                            break;

                        case 5:
                            if (service.deleteDocument(service.getDocumento().getDocumentoNome() + ".json")){
                                System.out.printf("Item removido com sucesso");
                            }else {
                                System.out.println("Erro ao deletar");
                            }
                        default:
                            System.out.println("!!! Valor invalido !!!");
                            break;

                    }

                }


                break;

            default:
                System.out.printf("Opção invalida");
                break;
        }

    }




}