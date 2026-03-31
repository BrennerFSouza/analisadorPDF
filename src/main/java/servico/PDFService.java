package main.java.servico;

import main.java.modelo.Documento;
import main.java.repositorio.PDFRepository;


public class PDFService {
    private final PDFRepository repository = new PDFRepository();

    public byte[] gerarPDF(Documento doc) {
        return repository.criarPDFMemoria(doc);

    }

    public void gerarDocumentoPDF(byte[] pdfBytes) {
        repository.gerarDocumentoPDF(pdfBytes);

    }
}
