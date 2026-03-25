package servico;

import modelo.ChatRequest;
import modelo.ChatResponse;
import modelo.Message;
import repositorio.ChatPdfRepository;

public class ChatPdfService {
    private ChatPdfRepository chatPdfRepository = new ChatPdfRepository();

    //METODS
    public ChatResponse enviarPergunta(String sourceId, String textoPergunta) {

        Message pergunta = new Message("user", textoPergunta);

        ChatRequest pedido = new ChatRequest(
                sourceId,
                new Message[]{pergunta}
        );
        return chatPdfRepository.enviarPergunta(pedido);
    }
}
