package tikape.runko.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Viesti {
    private String timeStamp;
    private String message;
    private String sender;
    private int id;
    private int keskusteluId;

    public Viesti(int id, String lahettaja, String viesti, String aika, int keskustelu){
        this.id = id;
        this.message = viesti;
        this.sender = lahettaja;
        this.timeStamp = aika;
        this.keskusteluId = keskustelu;
    }



    public Viesti( String lahettaja, String viesti, int keskusteluId){
        DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
        Date date = new Date();
        this.message = viesti;
        this.sender = lahettaja;
        this.timeStamp = df.format(date);
        this.keskusteluId = keskusteluId;
    }

    public Viesti(){
        this.id = 0;
        this.message = null;
        this.sender = null;
        this.timeStamp = null;
    }

    public void setSender(String item){this.sender=item;}

    public void setMessage(String item){this.message=item;}

    public void setId(int item){this.id=item;}

    public void setDate(String item){this.timeStamp=item;}

    public String getSender(){return this.sender;}

    public String getMessage(){return this.message;}

    public int getId(){return this.id;}

    public String getDate(){ return this.timeStamp;}

}
