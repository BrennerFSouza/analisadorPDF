package modelo;

public class ChatResponse {
    private String text;
    private  String sourceId;
    private String sessionId;

    //CONSTRUCTORS
    public ChatResponse() {}

    public ChatResponse(String text, String sourceId, String sessionId) {
        this.text = text;
        this.sourceId = sourceId;
        this.sessionId = sessionId;
    }

    //METODS

    public String getText() {
        return text;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
