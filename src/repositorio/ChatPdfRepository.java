package repositorio;

import com.google.gson.Gson;
import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;
import modelo.Documento;
import modelo.Orientacao;
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
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
                System.err.println("❌ API ERRO " + response.statusCode());
                return null;
            }

            return gson.fromJson(response.body(), ChatResponse.class);
        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
            return null;
        }
    }


    public Documento subirDocumento(List<Orientacao> orientacoes) {
        try {
            return null;
            /*
            String jsonRequest = gson.toJson(orientacoes);
            Map<String, String> data = new HashMap<>();
            data.put("url", jsonRequest);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(ApiConfig.URL_BASE + "/sources/add-file"))
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

            return gson.fromJson(response.body(), Documento.class);

             */
        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
            return null;
        }
    }


    public byte[] gerarPDF(String nomeDoc, List<Orientacao> orientacoes) {
        String jsonString = gson.toJson(orientacoes);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            // 2. O PdfWriter vincula o documento ao stream de memória
            PdfWriter.getInstance(document, out);

            document.open();

            // 3. Adicionamos o conteúdo
            document.add(new Paragraph(nomeDoc));
            document.add(new Paragraph(" ")); // Espaço
            document.add(new Paragraph(jsonString));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Retorna o PDF como um array de bytes
        return out.toByteArray();
    }
}


