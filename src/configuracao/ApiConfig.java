package configuracao;

public class ApiConfig {
    private static final String NOME_API_KEY = "chatpdf.api.key";


    public static String obterApiKey(){
        String apiKey = System.getProperty(NOME_API_KEY);
        if (apiKey == null || apiKey.isEmpty()){
            System.err.println("❌ ERRO: Execute com -D" + NOME_API_KEY + "=sua-chave");
            System.exit(1);
        }

        return apiKey;
    }
}
