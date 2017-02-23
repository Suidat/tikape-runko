package tikape.runko.domain;

public class Keskustelu {
    private int id;
    private String name;

    public Keskustelu(int tunnus, String nimi) {
        id = tunnus;
        name = nimi;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int tunnus) {
        this.id = tunnus;
    }

    public void setName(String nimi) {
        this.name = nimi;
    }
}
