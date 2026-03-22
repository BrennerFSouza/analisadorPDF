package repositorio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Documento;

import java.io.FileWriter;
import java.io.IOException;

public class DocumentoRepository {
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setPrettyPrinting()
            .create();

    public DocumentoRepository() {
    }

    public String converterParaJson(Documento document) {
        return gson.toJson(document);
    }

    public Documento converterJsonParaDocumento(String textDocument, Class<Documento> documentoClass) {
        return gson.fromJson(textDocument, documentoClass);
    }

    public void salvarDocumento(String s, String json){
        try(FileWriter writer = new FileWriter(s)){
            writer.write(json);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
