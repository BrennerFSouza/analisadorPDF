package servico;

import modelo.Documento;
import modelo.Orientacao;
import repositorio.DocumentoRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocumentoService {
    private DocumentoRepository repository = new DocumentoRepository();

    public DocumentoService() {
    }

    public String[] listarDocumentos() {
        var path = new File(".");

        return path.list(((dir, name) -> name.endsWith(".json")));

    }

    public void criarDocumento(String nome) {
        var documento = new Documento(nome);
        repository.salvar(documento);
    }

    public void editarDocument(Documento doc) {
        repository.salvar(doc);

    }

    public Documento buscarDocumento(String nome) {

        String textDocument = repository.lerDocumento(nome);
        return  repository.converterJsonParaDocumento(textDocument, Documento.class);



    }

    public boolean deletarDocumento(String nome) {
        repository.deletarDocumento(nome);
        return true;
    }

    public List<Orientacao> listarOrientacoes(Documento doc) {
        return doc.getOrientacoes();
    }

    public void incluirOrientacao(String nomeDoc,String title, String descricao) {

        Documento doc = repository.buscarPorNome(nomeDoc);
        List<Orientacao> orientacoes = new ArrayList<>(doc.getOrientacoes());
        long id = 1;

        if (!orientacoes.isEmpty()) {
            for (int i = 0; i < orientacoes.size(); i++) {
                if (orientacoes.get(i).getId() == id) {
                    id++;
                }
            }
        }

        orientacoes.add(new Orientacao(id, title, descricao));

        doc.setOrientacoes(orientacoes);

        repository.salvar(doc);

    }

    public void editarOrientacao(Documento doc,Long id, String title, String content) {

        List<Orientacao> orientacoes = doc.getOrientacoes();

        if (!orientacoes.isEmpty()) {
            for (int i = 0; i < orientacoes.size(); i++) {
                if (orientacoes.get(i).getId() == id) {
                    orientacoes.get(i).setTitle(title);
                    orientacoes.get(i).setContent(content);
                    break;
                }
            }
        }
        doc.setOrientacoes(orientacoes);

        editarDocument(doc);

    }


    public boolean deletarOrientacao(Documento doc,long id) {
        List<Orientacao> orientacoes = new ArrayList<>(doc.getOrientacoes());
        int encontrado = -1;
        if (!orientacoes.isEmpty()) {
            for (int i = 0; i < orientacoes.size(); i++) {
                if (orientacoes.get(i).getId() == id) {
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



        orientacoes.remove(orientacoes.get(encontrado));
        doc.setOrientacoes(orientacoes);

        editarDocument(doc);
        return true;
    }
}
