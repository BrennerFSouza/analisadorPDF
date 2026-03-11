package modelo;

public class ChatRequest {
    private String sourceId;
    private Message[] messages;

    //CONSTRUCTOR

    public ChatRequest(){}

    public ChatRequest(String sourceId, Message[] messages){
        this.sourceId = sourceId;
        this.messages = messages;
    }

    //METODS

    public String getSourceId() {
        return sourceId;
    }

    public Message[] getMessages() {
        return messages;
    }
}
