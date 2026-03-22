package Chatpdf;

public class ChatResponse {
    private String content;
    private String sourceId;
    private String sessionId;

    //CONSTRUCTORS
    public ChatResponse() {
    }

    public ChatResponse(String text, String sourceId, String sessionId) {
        this.content = text;
        this.sourceId = sourceId;
        this.sessionId = sessionId;
    }

    //METODS

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
