import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Document;
import modelo.Orientation;
import servico.DocumentService;

void main() {

    //TESTE CRIAÇÃO REGISTRO


    long id = ThreadLocalRandom.current().nextLong(0, 1_000_000_000L);
    String title = "teste de titulo";
    String content = "teste de conteudo";

    var registro1 = new Orientation(id, title, content);

    System.out.println(registro1);

    //TESTE CRIAÇÃO DOCUMENTO
    String nomeDocumento = "Nome teste";

    var documento1 = new Document(nomeDocumento);

    System.out.println(documento1.getCreationDate());

    var service = new DocumentService();

    if(service.createDocument(nomeDocumento, documento1)){
        System.out.println("Salvado com sucesso");
    }else{
        System.out.println("Erro ao salvar");
    }

    //TESTE CARREGAR DOCUMENTO

    Document documento2 = service.readDocument(nomeDocumento);

    System.out.println(documento2);
    System.out.println(documento2.getCreationDate());










}