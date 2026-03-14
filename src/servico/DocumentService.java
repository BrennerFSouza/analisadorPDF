package servico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Document;

import java.io.FileWriter;
import java.io.IOException;

public class DocumentService {
    private String nome;
    private Document document;

    public DocumentService(String nome, Document document) {
        this.nome = nome;
        this.document = document;
    }

    public boolean createDocument() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(document);

        try (FileWriter writer = new FileWriter(document.getDocumentName() + ".json")) {
            writer.write(json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
