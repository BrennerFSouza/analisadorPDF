package servico;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpResponse.BodyHandlers.ofString;

public class ChatPdfService {
    private final HttpClient client;

    private final Gson gson;

    private final String apiKey;

    //CONSTRUCTOR
    public ChatPdfService() {  // ← Construtor VAZIO (sem parâmetros)
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.apiKey = ApiConfig.obterApiKey();
    }

    //METODS
    public ChatResponse enviarPergunta(ChatRequest request){
        try{
            String jsonRequest = gson.toJson(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.URL_BASE + "/chats/message"))
                    .header("x-api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = client.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() != 200){
                System.err.println("❌ API ERRO " + response.statusCode());
                return null;
            }

            return gson.fromJson(response.body(), ChatResponse.class);
        }catch (Exception e){
            System.err.println("❌ ERRO: " + e.getMessage());
            return null;
        }
    }
}
