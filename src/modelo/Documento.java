package modelo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class Documento {
    private String sourceId;
    private String documentName;
    private Date creationDate;
    private Date alterationDate;
    private boolean haveAlteration;
    private Registro[] registros;

    public Documento(String documentName) {
        this.documentName = documentName;
        this.creationDate = new Date();
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


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setAlterationDate(Date alterationDate) {
        this.alterationDate = alterationDate;
    }

    public Date getAlterationDate() {
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

    @Override
    public String toString() {
        return "Documento{" +
                "sourceId='" + sourceId + '\'' +
                ", documentName='" + documentName + '\'' +
                ", creationDate=" + creationDate +
                ", alterationDate=" + alterationDate +
                ", haveAlteration=" + haveAlteration +
                ", registros=" + Arrays.toString(registros) +
                '}';
    }
}
