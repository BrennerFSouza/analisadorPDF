package main.java.modelo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Documento {
    private String sourceId;
    private String nomeDocumento;
    private Date dataCriacao;
    private Date dataAlteracao;
    private boolean temAlteracao;
    private List<Orientacao> orientacoes;


    public Documento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
        this.dataCriacao = new Date();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getNome() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }


    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public boolean isTemAlteracao() {
        return temAlteracao;
    }

    public void setTemAlteracao(boolean temAlteracao) {
        this.temAlteracao = temAlteracao;
    }

    public List<Orientacao> getOrientacoes() {
        if (this.orientacoes == null) {
            return Collections.emptyList();
        }
        return orientacoes;
    }

    public void setOrientacoes(List<Orientacao> orientacoes) {
        this.orientacoes = orientacoes;
    }

    @Override
    public String toString() {
        return
                "sourceId='" + sourceId + '\n' +
                        "documentName='" + nomeDocumento + '\n' +
                        "creationDate=" + dataCriacao + '\n' +
                        "alterationDate=" + dataAlteracao + '\n' +
                        "haveAlteration=" + temAlteracao + '\n' +
                        "orientacoes=" + orientacoes;
    }
}
