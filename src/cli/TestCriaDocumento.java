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
    String[] listaDocumentos = service.listDocuments();
    System.out.println("Lista de documentos");
    if (listaDocumentos != null){
        for (int i = 0; i < listaDocumentos.length; i++) {
            System.out.println(i+1 +" - "+listaDocumentos[i]);
        }
    }

    String seletor = readln("Selecione o documento");

    int seletorNumerico = Integer.parseInt(seletor);

    if (seletorNumerico == 0){
        String nomeNovoDocumento = readln("Digite o nome para o novo Documento");
        service.createDocument(nomeNovoDocumento);
    } else if (seletorNumerico > 0 && seletorNumerico <= listaDocumentos.length) {

        var document = service.readDocument(listaDocumentos[seletorNumerico - 1]);
        System.out.println(document);


    }else {
        System.out.println("Item não encontrado");
    }

    System.out.println("Documento selecionado:");
    System.out.println(service.toString());

    seletorNumerico = -1;
    while (seletorNumerico != 0){
        System.out.println("0 - para sair do documento");
        System.out.println("1 - Listar Orientações");
        System.out.println("2 - Criar nova orientação");
        System.out.println("3 - Alterar orientação");
        System.out.println("4 - Deletar orientação");

        seletorNumerico = Integer.parseInt(readln("Selecione uma opção"));



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
                String nome = readln("Digite o titulo da orientação:\n");
                String descricao = readln("Digite a descrição:\n");

                if (service.includeOrientation(nome, descricao)){
                    System.out.println("Documento atualizado...");
                }else {
                    System.out.println("!!! Erro ao salvar !!!");
                }

                break;

            case 3:
                Long id = Long.parseLong(readln("Qual item deseja editar?\n"));
                String novoNome = readln("Digite o titulo da orientação:\n");
                String novoContent = readln("Digite a descrição:\n");

                if (service.setOrientation(id, novoNome, novoContent)){
                    System.out.println("Documento atualizado...");
                }else {
                    System.out.println("!!! Erro ao salvar !!!");
                }

                break;

            case 4:
                break;

            default:
                System.out.println("!!! Valor invalido !!!");
                break;

        }

    }

}