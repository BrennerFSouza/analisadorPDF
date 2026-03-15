package servico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Document;

import javax.print.Doc;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DocumentService {
    private String nome;
    private Document document;
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setPrettyPrinting()
            .create();

    public DocumentService() {
    }

    public boolean createDocument(String nome, Document document) {


        String json = gson.toJson(document);

        try (FileWriter writer = new FileWriter(nome + ".json")) {
            writer.write(json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Document readDocument(String nome){
        try{
        String textDocument = Files.readString(Path.of(nome + ".json"));

        return gson.fromJson(textDocument, Document.class);

        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
