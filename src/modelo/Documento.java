package modelo;

import java.time.LocalDateTime;
import java.util.Date;

public class Documento {
    private String sourceId;
    private String documentName;
    private LocalDateTime creationDate;
    private LocalDateTime alterationDate;
    private boolean haveAlteration;
    private Registro[] registros;

    public Documento(String documentName) {
        this.documentName = documentName;
        this.creationDate = LocalDateTime.now();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setAlterationDate(LocalDateTime alterationDate) {
        this.alterationDate = alterationDate;
    }

    public LocalDateTime getAlterationDate() {
        return alterationDate;
    }

    public boolean isHaveAlteration() {
        return haveAlteration;
    }

    public void setHaveAlteration(boolean haveAlteration) {
        this.haveAlteration = haveAlteration;
    }

    public Registro[] getRegistros() {
        return registros;
    }

    public void setRegistros(Registro[] registros) {
        this.registros = registros;
    }
}
