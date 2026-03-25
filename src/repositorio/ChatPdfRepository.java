package repositorio;

import com.google.gson.Gson;
import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;

import java.net.http.HttpClient;

public class ChatPdfRepository {
    private final HttpClient client;
    private final Gson gson = new Gson();
    private final String apiKey = ApiConfig.obterApiKey();

    public ChatPdfRepository() {
        this.client = client;
    }

    public ChatResponse enviarPergunta(ChatRequest request) {
        try {
            String jsonRequest = gson.toJson(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.URL_BASE + "/chats/message"))
                    .header("x-api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = client.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() != 200) {
                System.err.println("❌ API ERRO " + response.statusCode());
                return null;
            }

            return gson.fromJson(response.body(), ChatResponse.class);
        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
            return null;
        }
    }

    public
}
