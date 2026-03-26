import configuracao.ApiConfig;
import modelo.ChatRequest;
import modelo.ChatResponse;
import modelo.Message;
import servico.ChatPdfService;

void main() {


    System.out.println("Iniciando Analisador PDF com ChatPDF...");

    String textoPergunta = IO.readln("Digite sua pergunta\n");
    String sourceId = ApiConfig.obterSourceId();




}