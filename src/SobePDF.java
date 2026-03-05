import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

void main() throws Exception {
    String apiKey = "sec_u455RyzsK8cg06gJ5u6Bg21r9D3YdMM0";
    Path file = Path.of("exame.pdf");

    // Criamos um ID único para separar os dados (Boundary)
    String boundary = "JavaBoundary" + UUID.randomUUID().toString();

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.chatpdf.com/v1/sources/add-file"))
            .header("x-api-key", apiKey)
            .header("Content-Type", "multipart/form-data; boundary=" + boundary)
            .POST(buildMultipartBody(file, boundary))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    IO.println("Status: " + response.statusCode());
    IO.println("Resposta: " + response.body());
}

private static HttpRequest.BodyPublisher buildMultipartBody(Path file, String boundary) throws IOException {
    var byteList = new ArrayList<byte[]>();
    String separator = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"file\"; filename=\""
            + file.getFileName() + "\"\r\nContent-Type: application/pdf\r\n\r\n";

    byteList.add(separator.getBytes());
    byteList.add(Files.readAllBytes(file));
    byteList.add(("\r\n--" + boundary + "--\r\n").getBytes());

    return HttpRequest.BodyPublishers.ofByteArrays(byteList);
}