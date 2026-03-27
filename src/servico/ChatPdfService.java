package servico;

import modelo.*;
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;
import repositorio.ChatPdfRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
        Documento novoDoc = chatPdfRepository.subirDocumento(doc.getOrientacoes());
        documentoService.atualizarSorceID(doc, doc.getSourceId());
    }

    public byte[] gerarPdfEmMemoria(String nomeDoc,List<Orientacao> orientacoes) {
        byte[] pdfBytes = chatPdfRepository.gerarPDF(nomeDoc, orientacoes);
        try (FileOutputStream fos = new FileOutputStream("resultado_teste.pdf")) {
            fos.write(pdfBytes);
            fos.flush();
            System.out.println("PDF gerado com sucesso! Verifique o arquivo: resultado_teste.pdf");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }

        return pdfBytes;

    }
}