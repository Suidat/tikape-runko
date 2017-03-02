package tikape.runko.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Viesti {
    private String aika;
    private String teksti;
    private String lahettaja;
    private int id;
    private int keskusteluId;

    public Viesti(int id, String sender, String teksti, String aika, int keskustelu){
        this.id = id;
        this.teksti = teksti;
        this.lahettaja = sender;
        this.aika = aika;
        this.keskusteluId = keskustelu;
    }



    public Viesti( String sender, String teksti, int keskusteluId){
        DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
        Date date = new Date();
        this.teksti = teksti;
        this.lahettaja = sender;
        this.aika = df.format(date);
        this.keskusteluId = keskusteluId;
    }

    public Viesti(){
        this.id = 0;
        this.teksti = null;
        this.lahettaja = null;
        this.aika = null;
    }

    public void setLahettaja(String item){this.lahettaja=item;}

    public void setTeksti(String item){this.teksti=item;}

    public void setId(int item){this.id=item;}

    public void setAika(String item){this.aika=item;}

    public void setKeskusteluId(int item){this.keskusteluId = item;}

    public String getLahettaja(){return this.lahettaja;}

    public String getTeksti(){return this.teksti;}

    public int getId(){return this.id;}

    public String getAika(){ return this.aika;}

    public int getKeskusteluId(){return this.keskusteluId;}

}
