import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;
import modelo.Message;
import servico.ChatPdfService;

void main() {

    System.out.println("Iniciando Analisador PDF com ChatPDF...");

    try{
        ChatPdfService service = new ChatPdfService();

        String sourceId = ApiConfig.obterSourceId();


        Message pergunta = new Message("user", "Resuma este PDF em 3 frases");

        ChatRequest pedido = new ChatRequest(
                sourceId,
                new Message[]{pergunta}
        );

        System.out.print("⏳ Enviando pergunta para ChatPDF");
        ChatResponse resposta = service.enviarPergunta(pedido);

        if (resposta != null && resposta.getContent() != null){
            System.out.println("\n✅ RESPOSTA DO CHATPDF:");
            System.out.println("📄 " + resposta.getContent());
        } else {
            System.out.println("\n❌ Erro na resposta da API");
        }

    }catch (Exception e){
        System.err.println("💥 ERRO FATAL: " + e.getMessage());
        e.printStackTrace();
    }


}