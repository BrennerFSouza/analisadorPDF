import modelo.Registro;

void main() {

    long id = ThreadLocalRandom.current().nextLong(0, 1_000_000_000L);
    String title = "ola";
    String content = "teste de conteudo";

    var registro1 = new Registro(id, title, content);

    System.out.println(registro1);

}