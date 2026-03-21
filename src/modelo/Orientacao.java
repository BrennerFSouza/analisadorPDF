package modelo;

public class Orientacao {
    private long id;
    private String title;
    private String content;

    public Orientacao(long id, String title, String content) {
        if (title.length() < 10) {
            throw new RuntimeException("Titulo deve ter mais que 10 caracteres: " + title);
        }
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Orientacao{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
