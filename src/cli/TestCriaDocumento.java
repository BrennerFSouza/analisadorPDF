import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Documento;
import modelo.Registro;

void main() {

    //TESTE CRIAÇÃO REGISTRO
    long id = ThreadLocalRandom.current().nextLong(0, 1_000_000_000L);
    String title = "teste de titulo";
    String content = "teste de conteudo";

    var registro1 = new Registro(id, title, content);

    System.out.println(registro1);

    //TESTE CRIAÇÃO DOCUMENTO
    String nomeDocumento = "Nome teste";

    var documento1 = new Documento(nomeDocumento);
    System.out.println(documento1);

    Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .setPrettyPrinting()
                    .create();

    String json = gson.toJson(documento1);
    System.out.println("Json Gerado: \n" + json);

    try(FileWriter writer = new FileWriter(documento1.getDocumentName() + ".json")){
        writer.write(json);
        System.out.printf("Salvo com sucesso");
    }catch (IOException e){
        e.printStackTrace();
    }


}