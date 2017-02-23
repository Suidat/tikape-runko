package tikape.runko.database;

import tikape.runko.domain.Aihe;

import java.sql.SQLException;
import java.util.List;

public class AiheDao implements Dao<Aihe, Integer> {


    @Override
    public Aihe findOne(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<Aihe> findAll() throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
