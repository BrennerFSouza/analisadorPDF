package modelo;

import java.util.Date;

public class Documento {
    private String sourceId;
    private Date alterationDate;
    private Registro[] registros;

    public Documento(String sourceId, Date alterationDate, Registro[] registros) {
        this.sourceId = sourceId;
        this.alterationDate = alterationDate;
        this.registros = registros;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Date getAlterationDate() {
        return alterationDate;
    }

    public void setAlterationDate(Date alterationDate) {
        this.alterationDate = alterationDate;
    }

    public Registro[] getRegistros() {
        return registros;
    }

    public void setRegistros(Registro[] registros) {
        this.registros = registros;
    }
}
