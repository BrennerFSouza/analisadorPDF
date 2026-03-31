package main.java.repositorio;

import com.google.gson.Gson;
import main.java.configuracao.ApiConfig;
import main.java.modelo.ChatRequest;
import main.java.modelo.ChatResponse;
import main.java.modelo.Documento;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ChatPdfRepository {
    private final HttpClient client;
    private final Gson gson;
    private final String apiKey;

    //CONSTRUCTOR
    public ChatPdfRepository() {
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
        this.apiKey = ApiConfig.obterApiKey();
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
                System.err.println("❌ API ERRO " + response.statusCode() + " - " + response.body());
                return null;
            }

            return gson.fromJson(response.body(), ChatResponse.class);
        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
            return null;
        }
    }


    public ChatResponse subirDocumento(byte[] pdfBytes, String documentoNome) {
        String nomeFinal = documentoNome.endsWith(".pdf") ? documentoNome : documentoNome + ".pdf";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost(ApiConfig.URL_BASE + "/sources/add-file");
            uploadFile.addHeader("x-api-key", apiKey);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody(
                    "file",
                    pdfBytes,
                    ContentType.APPLICATION_PDF,
                    nomeFinal
            );

            uploadFile.setEntity(builder.build());


            return httpClient.execute(uploadFile, response -> {
                String responseBody = EntityUtils.toString(response.getEntity());

                if (response.getCode() >= 200 && response.getCode() < 300) {
                    System.out.println(responseBody);
                    return gson.fromJson(responseBody, ChatResponse.class);
                } else {
                    System.err.println("Erro API: " + response.getCode() + " - " + responseBody);
                    return null;
                }
            });

        } catch (Exception e) {
            System.err.println("❌ ERRO de conexão ou parsing: " + e.getMessage());
            return null;
        }
    }

    public void deletarDocumento(Documento doc) {
        try {
            if (doc.getSourceId() == null){
                return;
            }
            Map<String, List<String>> payload = Collections.singletonMap(
                    "sources",
                    Collections.singletonList(doc.getSourceId())
            );
            String jsonRequest = gson.toJson(payload);
            System.out.println(jsonRequest);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.URL_BASE + "/sources/delete"))
                    .header("x-api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = client.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response);
            if (response.statusCode() != 200) {
                throw new RuntimeException("❌ API ERRO " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ ERRO: " + e.getMessage());
        }
    }



}


