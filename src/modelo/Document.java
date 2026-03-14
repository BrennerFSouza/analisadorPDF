package modelo;

import java.util.Arrays;
import java.util.Date;

public class Document {
    private String sourceId;
    private String documentName;
    private Date creationDate;
    private Date alterationDate;
    private boolean haveAlteration;
    private Orientation[] orientations;

    public Document(String documentName) {
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

    public Orientation[] getOrientations() {
        return orientations;
    }

    public void setOrientations(Orientation[] orientations) {
        this.orientations = orientations;
    }

    @Override
    public String toString() {
        return "Document{" +
                "sourceId='" + sourceId + '\'' +
                ", documentName='" + documentName + '\'' +
                ", creationDate=" + creationDate +
                ", alterationDate=" + alterationDate +
                ", haveAlteration=" + haveAlteration +
                ", registros=" + Arrays.toString(orientations) +
                '}';
    }
}
