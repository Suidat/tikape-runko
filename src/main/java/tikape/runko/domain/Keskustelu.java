package tikape.runko.domain;


public class Keskustelu {
    private int id;
    private String nimi;
    private String aika;
    private int aihe;
    private int maara;

    public Keskustelu(int id, String nimi){
        this.aihe = id;
        this.nimi = nimi;
    }

    public Keskustelu(String nimi, int id){
        this.id = id;
        this.nimi = nimi;
    }

    public Keskustelu(String nimi, int aihe, String aika){
        this.nimi=nimi;
        this.aihe=aihe;
        this.aika = aika;
    }
    
    public Keskustelu(int tunnus, String nimi, int aihe) {
        this.id = tunnus;
        this.nimi = nimi;
        this.aihe = aihe;
    }

    public Keskustelu(String nimi, int id, int maara) {
        this.id = id;
        this.nimi = nimi;
        this.maara = maara;
    }

    public int getId() {
        return this.id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public int getAihe() {return this.aihe;}

    public void setAihe(int tunnus){this.aihe = tunnus;}

    public void setId(int tunnus) {
        this.id = tunnus;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setMaara(int maara){this.maara = maara;}

    public int getMaara(){return this.maara;}

    public void setAika(String aika){this.aika = aika;}

    public String getAika(){return this.aika;}
}
