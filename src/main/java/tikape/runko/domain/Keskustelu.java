package tikape.runko.domain;

import java.util.Date;

public class Keskustelu {
    private int id;
    private String nimi;
    private Date aika;
    private int aiheId;
    
    public Keskustelu(int tunnus, String nimi) {
        this.id = tunnus;
        this.nimi = nimi;
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
