package tikape.runko.domain;


public class Kayttaja {
    private int id;
    private String nimike;
    private String salasana;

    public Kayttaja(int tunnus, String name, String pass) {
        id = tunnus;
        nimike = name;
        salasana = pass;
    }
}
