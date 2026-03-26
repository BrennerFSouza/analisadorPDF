package servico;

import modelo.ChatRequest;
import modelo.ChatResponse;
import modelo.Documento;
import modelo.Message;
import repositorio.ChatPdfRepository;

public class ChatPdfService {
    private ChatPdfRepository chatPdfRepository = new ChatPdfRepository();

    //METODS
    public ChatResponse enviarPergunta(Documento doc, String textoPergunta) {

        Message pergunta = new Message("user", textoPergunta);
        String sourceId = doc.getSourceId();
        ChatRequest pedido = new ChatRequest(
                sourceId,
                new Message[]{pergunta}
        );
        return chatPdfRepository.enviarPergunta(pedido);
    }
}
