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
        if(doc.isTemAlteracao()){
            atualizarDocumentoSistema(doc);
        }

        Message pergunta = new Message("user", textoPergunta);
        String sourceId = doc.getSourceId();
        ChatRequest pedido = new ChatRequest(
                sourceId,
                new Message[]{pergunta}
        );
        return chatPdfRepository.enviarPergunta(pedido);
    }

    private void atualizarDocumentoSistema(Documento doc){
        DocumentoService documentoService = new DocumentoService();
        chatPdfRepository.subirDocumento(doc.getOrientacoes());
        documentoService.atualizarSorceID(doc, doc.getSourceId());
    }
}
