package tikape.runko.domain;

import java.util.Date;

public class Keskustelu {
    private int id;
    private String nimi;
    private String aika;
    private int aiheId;

    public Keskustelu(int id, String nimi){
        this.aiheId = id;
        this.nimi = nimi;
    }

    public Keskustelu(String nimi, int aiheId, String aika){
        this.nimi=nimi;
        this.aiheId=aiheId;
        this.aika = aika;
    }
    
    public Keskustelu(int tunnus, String nimi, String aika, int aiheId) {
        this.id = tunnus;
        this.nimi = nimi;
        this.aika = aika;
        this.aiheId = aiheId;
    }

    public int getId() {
        return this.id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public void setId(int tunnus) {
        this.id = tunnus;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
}
