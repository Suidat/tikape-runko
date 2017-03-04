package tikape.runko.domain;

public class Aihe {
    private int id;
    private String nimi;
    private int maara;
    private String aika;

    public Aihe(String nimi){
        this.nimi=nimi;
    }

    public Aihe(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public Aihe(int id, String nimi, int maara) {
        this.id = id;
        this.nimi = nimi;
        this.maara = maara;
    }

    public int getId(){return this.id;}
    public String getNimi(){return this.nimi;}
    public int getMaara(){return this.maara;}
    public void setMaara(int maara){this.maara=maara;}
    public void setAika(String aika){this.aika=aika;}
    public String getAika(){return this.aika;}
}
    