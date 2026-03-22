package repositorio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Documento;

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
}
