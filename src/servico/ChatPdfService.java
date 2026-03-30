package servico;

import modelo.*;
import repositorio.ChatPdfRepository;

public class ChatPdfService {
    private PDFService pdfService = new PDFService();
    private ChatPdfRepository chatPdfRepository = new ChatPdfRepository();

    //METODS
    public ChatResponse enviarPergunta(Documento doc, String textoPergunta) {
        if(doc.isTemAlteracao()){
            doc = atualizarDocumentoSistema(doc);
        }

        Message pergunta = new Message("user", textoPergunta);
        String sourceId = doc.getSourceId();
        ChatRequest pedido = new ChatRequest(
                sourceId,
                new Message[]{pergunta}
        );
        return chatPdfRepository.enviarPergunta(pedido);
    }

    private Documento atualizarDocumentoSistema(Documento doc){
        DocumentoService documentoService = new DocumentoService();
        byte[] pdfBytes = pdfService.gerarPDF(doc);
        if (doc.getSourceId() != null){
            deletarDocumento(doc);
        }
        ChatResponse response = chatPdfRepository.subirDocumento(pdfBytes, doc.getNome());


        return documentoService.atualizarSorceID(doc, response.getSourceId());
    }

    public void deletarDocumento(Documento doc) {
        chatPdfRepository.deletarDocumento(doc);
    }

    public byte[] gerarPdf(Documento doc) {
        byte[] pdfBytes = pdfService.gerarPDF(doc);
        pdfService.gerarDocumentoPDF(pdfBytes);
        return pdfBytes;

    }


}