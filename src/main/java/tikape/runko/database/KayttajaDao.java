package tikape.runko.database;

import tikape.runko.domain.Kayttaja;

import java.sql.SQLException;
import java.util.List;

public class KayttajaDao implements Dao<Kayttaja, Integer> {
    @Override
    public Kayttaja findOne(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<Kayttaja> findAll() throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public void add(Kayttaja lisattava) throws SQLException {

    }


}
