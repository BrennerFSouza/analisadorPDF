import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Document;
import modelo.Orientation;
import servico.DocumentService;

import static java.lang.IO.readln;

void main()  {
/*
    //TESTE CRIAÇÃO REGISTRO


    long id = ThreadLocalRandom.current().nextLong(0, 1_000_000_000L);
    String title = "teste de titulo";
    String content = "teste de conteudo";

    var registro1 = new Orientation(id, title, content);

    System.out.println(registro1);

    //TESTE CRIAÇÃO DOCUMENTO
    String nomeDocumento = "Nome teste 2";

    var documento1 = new Document(nomeDocumento);

    System.out.println(documento1.getCreationDate());

    var service = new DocumentService();

    if(service.createDocument(nomeDocumento, documento1)){
        System.out.println("Salvado com sucesso");
    }else{
        System.out.println("Erro ao salvar");
    }

    //TESTE CARREGAR DOCUMENTO

    Document documento2 = service.readDocument(nomeDocumento + ".json");

    System.out.println(documento2);
    System.out.println(documento2.getCreationDate());
*/


    //LISTAR DOCUMENTOS
    var service = new DocumentService();



    int seletorNumerico = -1;
    int seletorDocumento = -1;
    while (seletorNumerico != 0) {
        System.out.println("Lista de opções");
        System.out.println("0 - para sair do documento");
        System.out.println("1 - Listar Documentos");
        System.out.println("2 - Criar Novo Documento");
        System.out.println("3 - Selecionar Documento");

        seletorNumerico = Integer.parseInt(readln("Selecione:\n"));
        String[] listaDocumentos = service.listDocuments();

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
                var document = service.readDocument(listaDocumentos[seletorDocumento - 1]);
                System.out.println("Documento Selecionado");
                System.out.println(service.toString());
                seletorNumerico = -1;
                while (seletorNumerico != 0){
                    System.out.println("0 - para sair do documento");
                    System.out.println("1 - Listar Orientações");
                    System.out.println("2 - Criar nova orientação");
                    System.out.println("3 - Alterar orientação");
                    System.out.println("4 - Deletar orientação");
                    System.out.println("5 - Deletar documento");

                    seletorNumerico = Integer.parseInt(readln("Selecione uma opção"));

                    String nome = null;
                    String content = null;
                    long id;

                    switch (seletorNumerico){
                        case 0:
                            System.out.println("Saindo do documento...");
                            break;

                        case 1:
                            List<Orientation> listaOrientations = service.listOrientations();

                            if (!listaOrientations.isEmpty()){
                                for (int i = 0; i < listaOrientations.size(); i++) {
                                    System.out.println(listaOrientations.get(i));
                                }
                            }else {
                                System.out.println("Sem orientações");
                            }

                            break;

                        case 2:
                            nome = readln("Digite o titulo da orientação:\n");
                            content = readln("Digite a descrição:\n");

                            if (service.includeOrientation(nome, content)){
                                System.out.println("Documento atualizado...");
                            }else {
                                System.out.println("!!! Erro ao salvar !!!");
                            }

                            break;

                        case 3:
                            id = Long.parseLong(readln("Qual item deseja editar?\n"));
                            nome = readln("Digite o titulo da orientação:\n");
                            content = readln("Digite a descrição:\n");

                            if (service.setOrientation(id, nome, content)){
                                System.out.println("Documento atualizado...");
                            }else {
                                System.out.println("!!! Erro ao salvar !!!");
                            }

                            break;

                        case 4:
                            id = Long.parseLong(readln("Qual item deseja editar?\n"));

                            if (service.deleteOrientation(id)){
                                System.out.println("Item removido com sucesso...");
                            }else {
                                System.out.println("!!! Erro ao Deletar !!!");
                            }
                            break;

                        case 5:
                            if (service.deleteDocument(service.getDocument().getDocumentName() + ".json")){
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



/*
    seletorNumerico = -1;
    while (seletorNumerico != 0){
        System.out.println("0 - para sair do documento");
        System.out.println("1 - Listar Orientações");
        System.out.println("2 - Criar nova orientação");
        System.out.println("3 - Alterar orientação");
        System.out.println("4 - Deletar orientação");

        seletorNumerico = Integer.parseInt(readln("Selecione uma opção"));

        String nome = null;
        String content = null;
        long id;

        switch (seletorNumerico){
            case 0:
                System.out.println("Saindo do documento...");
                break;

            case 1:
                List<Orientation> listaOrientations = service.listOrientations();

                if (!listaOrientations.isEmpty()){
                for (int i = 0; i < listaOrientations.size(); i++) {
                    System.out.println(listaOrientations.get(i));
                }
                }else {
                    System.out.println("Sem orientações");
                }

                break;

            case 2:
                nome = readln("Digite o titulo da orientação:\n");
                content = readln("Digite a descrição:\n");

                if (service.includeOrientation(nome, content)){
                    System.out.println("Documento atualizado...");
                }else {
                    System.out.println("!!! Erro ao salvar !!!");
                }

                break;

            case 3:
                id = Long.parseLong(readln("Qual item deseja editar?\n"));
                nome = readln("Digite o titulo da orientação:\n");
                content = readln("Digite a descrição:\n");

                if (service.setOrientation(id, nome, content)){
                    System.out.println("Documento atualizado...");
                }else {
                    System.out.println("!!! Erro ao salvar !!!");
                }

                break;

            case 4:
                id = Long.parseLong(readln("Qual item deseja editar?\n"));

                if (service.deleteOrientation(id)){
                    System.out.println("Item removido com sucesso...");
                }else {
                    System.out.println("!!! Erro ao Deletar !!!");
                }
                break;

            default:
                System.out.println("!!! Valor invalido !!!");
                break;

        }

    }


 */

}