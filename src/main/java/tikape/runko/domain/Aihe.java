package tikape.runko.domain;

public class Aihe {
    private int id;
    private String nimi;

    public Aihe(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    public int getId(){return this.id;}
    public String getNimi(){return this.nimi;}
}
    