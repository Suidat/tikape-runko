package tikape.runko.database;

import tikape.runko.domain.Keskustelu;

import java.sql.SQLException;
import java.util.List;

public class KeskusteluDao implements Dao<Keskustelu, Integer>{

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
