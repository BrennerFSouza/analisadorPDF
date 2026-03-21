package servico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Documento;
import modelo.Orientacao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DocumentoService {
    private Documento documento;
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setPrettyPrinting()
            .create();

    public DocumentoService() {
    }

    public DocumentoService(Documento documento) {
        this.documento = documento;
    }

    public Documento buscarDocumento() {
        return documento;
    }

    public void editarDocumento(Documento documento) {
        this.documento = documento;
    }

    public String[] listarDocumentos() {
        var path = new File(".");

        return path.list(((dir, name) -> name.endsWith(".json")));

    }

    public boolean criarDocumento(String nome) {
        var document = new Documento(nome);
        String json = gson.toJson(document);

        try (FileWriter writer = new FileWriter(nome + ".json")) {
            writer.write(json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarDocument(Documento documento) {
        String json = gson.toJson(documento);

        try (FileWriter writer = new FileWriter(documento.getDocumentoNome() + ".json")) {
            writer.write(json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Documento buscarDocumento(String nome) {
        try {
            String textDocument = Files.readString(Path.of(nome));
            editarDocumento(gson.fromJson(textDocument, Documento.class));
            return gson.fromJson(textDocument, Documento.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deletarDocumento(String nome) {
        try {
            Files.delete(Path.of(nome));

            return true;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Orientacao> listarOrientacoes() {
        return documento.getOrientacoes();
    }

    @Override
    public String toString() {
        return
                "" + documento + '\n';
    }

    public boolean incluirOrientacao(String title, String descricao) {
        List<Orientacao> listaAtual = documento.getOrientacoes();
        long id = 1;

        if (!listaAtual.isEmpty()) {
            for (int i = 0; i < listaAtual.size(); i++) {
                if (listaAtual.get(i).getId() == id) {
                    id++;
                }
            }
        }

        List<Orientacao> listaNova = (listaAtual == null) ? new ArrayList<>() : new ArrayList<>(listaAtual);

        listaNova.add(new Orientacao(id, title, descricao));

        documento.setOrientacoes(listaNova);

        return editarDocument(documento);

    }

    public boolean editarOrientacao(Long id, String title, String content) {

        List<Orientacao> listaAtual = documento.getOrientacoes();

        if (!listaAtual.isEmpty()) {
            for (int i = 0; i < listaAtual.size(); i++) {
                if (listaAtual.get(i).getId() == id) {
                    listaAtual.get(i).setTitle(title);
                    listaAtual.get(i).setContent(content);
                    break;
                }
            }
        } else {
            return false;
        }
        documento.setOrientacoes(listaAtual);

        return editarDocument(documento);

    }


    public boolean deletarOrientacao(long id) {

        List<Orientacao> listaAtual = documento.getOrientacoes();
        int encontrado = -1;
        if (!listaAtual.isEmpty()) {
            for (int i = 0; i < listaAtual.size(); i++) {
                if (listaAtual.get(i).getId() == id) {
                    encontrado = i;
                    break;
                }
            }
        } else {
            return false;
        }

        if (encontrado == -1) {
            return false;
        }

        List<Orientacao> listaNova = new ArrayList<>(listaAtual);

        listaNova.remove(listaNova.get(encontrado));
        documento.setOrientacoes(listaNova);

        return editarDocument(documento);
    }
}
