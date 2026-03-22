package Chatpdf;

public class ApiConfig {
    public static final String URL_BASE = "https://api.chatpdf.com/v1";
    private static final String NOME_API_KEY = "chatpdf.api.key";
    private static final String NOME_SOURCE_ID = "chatpdf.source.id";

    public static String obterApiKey() {
        String apiKey = System.getProperty(NOME_API_KEY);
        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("❌ ERRO: Execute com -D" + NOME_API_KEY + "=sua-chave");
            System.exit(1);
        }

        return apiKey;
    }

    public static String obterSourceId() {
        String sourceId = System.getProperty(NOME_SOURCE_ID);
        if (sourceId == null || sourceId.isEmpty()) {
            System.err.println("❌ ERRO: Execute com -D" + NOME_SOURCE_ID + "=seu-sourceId");
            System.exit(1);
        }
        return sourceId;
    }

}
