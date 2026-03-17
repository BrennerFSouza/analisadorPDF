package servico;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Document;
import modelo.Orientation;

import javax.print.Doc;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DocumentService {
    private Document document;
    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setPrettyPrinting()
            .create();

    public DocumentService() {
    }

    public DocumentService(Document document) {
        this.document = document;
    }

    public void setDocument(Document document){
        this.document = document;
    }

    public String[] listDocuments(){
      var path = new File(".");

      return path.list(((dir, name) -> name.endsWith(".json")));

    }
    public boolean createDocument(String nome) {
        var document = new Document(nome);
        String json = gson.toJson(document);

        try (FileWriter writer = new FileWriter(nome + ".json")) {
            writer.write(json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editDocument(Document document){
        String json = gson.toJson(document);

        try (FileWriter writer = new FileWriter(document.getDocumentName() + ".json")) {
            writer.write(json);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Document readDocument(String nome){
        try{
            String textDocument = Files.readString(Path.of(nome));
            setDocument(gson.fromJson(textDocument, Document.class));
            return gson.fromJson(textDocument, Document.class);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Orientation> listOrientations(){
            return document.getOrientations();
    }

    @Override
    public String toString() {
        return "DocumentService{" +
                "document=" + document +
                '}';
    }

    public boolean includeOrientation(String title, String descricao){
        List<Orientation> listaAtual = document.getOrientations();
        long id = 1;
        
        if (!listaAtual.isEmpty()){
            for (int i = 0; i < listaAtual.size(); i++) {
                if (listaAtual.get(i).getId() == id){
                    id++;
                }
            }
        }

        List<Orientation> listaNova = (listaAtual == null)? new ArrayList<>() : new ArrayList<>(listaAtual);

        listaNova.add(new Orientation(id, title, descricao));

        document.setOrientations(listaNova);

        if(editDocument(document)){
            return true;
        }else{
            return false;
        }
        
    }

    public boolean setOrientation(Long id, String title, String content){

        List<Orientation> listaAtual = document.getOrientations();

        if (!listaAtual.isEmpty()){
            for (int i = 0; i < listaAtual.size(); i++) {
                if (listaAtual.get(i).getId() == id){
                    listaAtual.get(i).setTitle(title);
                    listaAtual.get(i).setContent(content);
                }
            }
        }else{
            return false;
        }
        document.setOrientations(listaAtual);

        if(editDocument(document)){
            return true;
        }else{
            return false;
        }

    }




}
