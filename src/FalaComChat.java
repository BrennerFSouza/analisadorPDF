import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

void main() {
    String apiKey = "sec_u455RyzsK8cg06gJ5u6Bg21r9D3YdMM0"; // Sua chave
    String sourceId = "src_XNuV6I2sBfjWd2W9b3Ju0"; // O ID que você recebeu no upload

    // Criando o corpo do JSON manualmente (para evitar dependências externas)
    // Em projetos reais, use Jackson ou Gson para montar esse JSON
    String jsonBody = """
            {
              "sourceId": "%s",
              "messages": [
                {
                  "role": "user",
                  "content": "Qual o valor do estradiol?"
                }
              ]
            }
            """.formatted(sourceId);

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.chatpdf.com/v1/chats/message"))
            .header("x-api-key", apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

    try {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            IO.println("Resposta do Chat: " + response.body());
        } else {
            IO.println("Erro " + response.statusCode() + ": " + response.body());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}