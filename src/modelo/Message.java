package modelo;

public class Message {
    private String role;
    private String content;

    //Constructor
    public Message(){}
    public Message(String role, String content){
        this.role = role;
        this.content = content;
    }

    //Metodos

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
