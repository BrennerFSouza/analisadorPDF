package repositorio;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import modelo.Documento;
import modelo.Orientacao;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PDFRepository {


    public byte[] criarPDFMemoria(Documento doc) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String caminhoFonte = "fonts/arial.ttf";
            BaseFont bf = BaseFont.createFont(
                    ChatPdfRepository.class.getClassLoader().getResource(caminhoFonte).toString(),
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );
            Font fonteTituloDoc = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font fonteTitulo = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font fonteCorpo = new Font(Font.HELVETICA, 10, Font.NORMAL);

            Document document = new Document();

            // 2. O PdfWriter vincula o documento ao stream de memória
            PdfWriter.getInstance(document, out);

            document.open();

            // 3. Adicionamos o conteúdo
            Paragraph tituloDoc = new Paragraph(doc.getNome(), fonteTituloDoc); // <--- Fonte aqui
            tituloDoc.setAlignment(Element.ALIGN_CENTER);
            tituloDoc.setSpacingAfter(30f);
            document.add(tituloDoc);

            for (Orientacao orientacao : doc.getOrientacoes()) {

                Paragraph tituloOrientacao = new Paragraph("ID: " + orientacao.getId() + " - " + textoTratado(orientacao.getTitle()), fonteTitulo);
                tituloOrientacao.setSpacingBefore(15f);
                document.add(tituloOrientacao);

                Paragraph corpoOrientacao = new Paragraph(textoTratado(orientacao.getContent()), fonteCorpo);
                corpoOrientacao.setSpacingBefore(5f);   // Pequeno espaço entre o título e o texto dele
                corpoOrientacao.setSpacingAfter(10f);
                document.add(corpoOrientacao);
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String textoTratado(String texto) {
        return new String(texto.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public void gerarDocumentoPDF(byte[] pdfBytes) {
        try (FileOutputStream fos = new FileOutputStream("resultado_teste.pdf")) {
            fos.write(pdfBytes);
            fos.flush();
            System.out.println("PDF gerado com sucesso! Verifique o arquivo: resultado_teste.pdf");
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
