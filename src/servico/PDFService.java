package servico;

import modelo.Documento;
import repositorio.PDFRepository;


public class PDFService {
    private final PDFRepository repository = new PDFRepository();

    public byte[] gerarPDF(Documento doc) {
        return repository.criarPDFMemoria(doc);

    }

    public void gerarDocumentoPDF(byte[] pdfBytes) {
        repository.gerarDocumentoPDF(pdfBytes);

    }
}
