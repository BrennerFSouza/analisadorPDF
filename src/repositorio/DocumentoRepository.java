package repositorio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Documento;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DocumentoRepository {
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setPrettyPrinting()
            .create();

    public DocumentoRepository() {
    }

    public Documento carregarDocumento(String textDocument, Class<Documento> documentoClass) {
        return gson.fromJson(textDocument, documentoClass);
    }

    public void deletarDocumento(Documento doc) {
        try {
            Files.delete(Path.of(doc.getNome() + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String lerDocumento(String nome) {
        try{
            return Files.readString(Path.of(nome));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Documento buscarPorNome(String nome) {
        try{
            if (!nome.contains(".json")){nome += ".json";}
            String json = Files.readString(Path.of(nome));
            return gson.fromJson(json, Documento.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void salvar(Documento doc) {
        try(FileWriter writer = new FileWriter(doc.getNome() + ".json")){
            String json = gson.toJson(doc);
            writer.write(json);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
