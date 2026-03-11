import configuracao.ApiConfig;
import modelo.Message;

void main() {

    String apiKey = ApiConfig.obterApiKey();
    System.out.println(apiKey);

    var message = new Message("Teste rule", "Teste content");

    System.out.println(message.getRole());
    System.out.println(message.getContent());


}