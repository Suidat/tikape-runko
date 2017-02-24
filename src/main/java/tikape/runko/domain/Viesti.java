package tikape.runko.domain;

public class Viesti {
    private String timeStamp;
    private String message;
    private String sender;
    private int id;

    public Viesti(int id, String lahettaja, String viesti, String aika){
        this.id = id;
        this.message = viesti;
        this.sender = lahettaja;
        this.timeStamp = aika;
    }

    public Viesti( String lahettaja, String viesti, String aika){
        this.message = viesti;
        this.sender = lahettaja;
        this.timeStamp = aika;
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
