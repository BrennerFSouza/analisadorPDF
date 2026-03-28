package repositorio;

import com.google.gson.Gson;
import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;
import modelo.Documento;
import modelo.Orientacao;
import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.BaseFont;
import org.openpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String caminhoFonte = "fonts/arial.ttf";
            BaseFont bf = BaseFont.createFont(
                    ChatPdfRepository.class.getClassLoader().getResource(caminhoFonte).toString(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );
            Font fonteTituloDoc = new Font(bf, 16, Font.BOLD);
            Font fonteTitulo = new Font(bf, 12, Font.BOLD);
            Font fonteCorpo = new Font(bf, 10, Font.NORMAL);

            Document document = new Document();

            // 2. O PdfWriter vincula o documento ao stream de memória
            PdfWriter.getInstance(document, out);

            document.open();

            // 3. Adicionamos o conteúdo
            Paragraph tituloDoc = new Paragraph(nomeDoc, fonteTituloDoc); // <--- Fonte aqui
            tituloDoc.setAlignment(Element.ALIGN_CENTER);
            tituloDoc.setSpacingAfter(30f);
            document.add(tituloDoc);

            for (Orientacao orientacao : orientacoes) {

                String tituloLimpo = new String(orientacao.getTitle().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                String conteudoLimpo = new String(orientacao.getContent().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                Paragraph tituloOrientacao = new Paragraph("ID: " + orientacao.getId() + " - " + tituloLimpo, fonteTitulo);
                tituloOrientacao.setSpacingBefore(15f);
                document.add(tituloOrientacao);

                Paragraph corpoOrientacao = new Paragraph(conteudoLimpo, fonteCorpo);
                corpoOrientacao.setSpacingBefore(5f);   // Pequeno espaço entre o título e o texto dele
                corpoOrientacao.setSpacingAfter(10f);
                document.add(corpoOrientacao);
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}


