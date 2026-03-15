package modelo;

import java.util.*;

public class Document {
    private String sourceId;
    private String documentName;
    private Date creationDate;
    private Date alterationDate;
    private boolean haveAlteration;
    private List<Orientation> orientations;


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

    public List<Orientation> getOrientations() {
        if(this.orientations == null){
            return Collections.emptyList();
        }
        return orientations;
    }

    public void setOrientations(List<Orientation> orientations) {
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
                ", orientations=" + orientations +
                '}';
    }
}
