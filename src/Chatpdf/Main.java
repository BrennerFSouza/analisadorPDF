import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;
import modelo.Message;
import servico.ChatPdfService;

void main() {
        ChatPdfService service = new ChatPdfService();

    System.out.println("Iniciando Analisador PDF com ChatPDF...");

    String textoPergunta = IO.readln("Digite sua pergunta\n");
    String sourceId = ApiConfig.obterSourceId();


    ChatResponse resposta = service.enviarPergunta(sourceId, textoPergunta);
    System.out.print("⏳ Enviando pergunta para ChatPDF");

    if (resposta != null && resposta.getContent() != null) {
        System.out.println("\n✅ RESPOSTA DO CHATPDF:");
        System.out.println("📄 " + resposta.getContent());
    } else {
        System.out.println("\n❌ Erro na resposta da API");
    }

}