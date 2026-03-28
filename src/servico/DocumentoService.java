package servico;

import modelo.Documento;
import modelo.Orientacao;
import repositorio.DocumentoRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentoService {
    private final DocumentoRepository repository = new DocumentoRepository();

    public DocumentoService() {
    }

    public String[] listarDocumentos() {
        var path = new File(".");

        return path.list(((dir, name) -> name.endsWith(".json")));

    }

    public Documento criarDocumento(String nome) {
        var documento = new Documento(nome);
        return repository.salvar(documento);
    }

    public Documento editarDocumento(Documento doc) {
        doc.setDataAlteracao(new Date());
        doc.setTemAlteracao(true);
        return repository.salvar(doc);

    }

    public Documento atualizarSorceID(Documento doc, String sorceId) {
        doc.setSourceId(sorceId);
        doc.setTemAlteracao(false);
        return repository.salvar(doc);
    }

    public Documento buscarDocumento(String nome) {

        String textDocument = repository.lerDocumento(nome);
        return repository.carregarDocumento(textDocument, Documento.class);

    }

    public boolean deletarDocumento(Documento doc) {
        repository.deletarDocumento(doc);
        return true;
    }

    public List<Orientacao> listarOrientacoes(Documento doc) {
        return doc.getOrientacoes();
    }

    public Documento incluirOrientacao(Documento doc, String title, String descricao) {

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
        doc.setTemAlteracao(true);

        repository.salvar(doc);
        return doc;

    }

    public Documento editarOrientacao(Documento doc, Long id, String title, String content) {

        List<Orientacao> orientacoes = doc.getOrientacoes();

        if (orientacoes.isEmpty()) {
            return doc;
        }

        for (int i = 0; i < orientacoes.size(); i++) {
            if (orientacoes.get(i).getId() == id) {
                orientacoes.get(i).setTitle(title);
                orientacoes.get(i).setContent(content);
                break;
            }
        }
        doc.setOrientacoes(orientacoes);

        editarDocumento(doc);


        return doc;

    }


    public boolean deletarOrientacao(Documento doc, long id) {
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

        editarDocumento(doc);
        return true;
    }
}
