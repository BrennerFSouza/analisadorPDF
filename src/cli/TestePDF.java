package cli;

import modelo.Documento;
import servico.ChatPdfService;
import servico.DocumentoService;

public class TestePDF {

    static void main(String[] args) {
        DocumentoService documentoService = new DocumentoService();
        ChatPdfService chatPdfService = new ChatPdfService();


        Documento documento = documentoService.buscarDocumento("Documento Teste.json");
        System.out.println(documento);
        chatPdfService.gerarPdf(documento);
    }
}