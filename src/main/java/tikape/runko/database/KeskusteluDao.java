package tikape.runko.database;

import tikape.runko.domain.Keskustelu;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KeskusteluDao implements Dao<Keskustelu, Integer>{

    
    private Database database;
    
    public KeskusteluDao(Database database){this.database = database;}
    
    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelut WHERE id = ?");

        ResultSet rs = stmt.executeQuery();
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        Keskustelu keskustelu = new Keskustelu(id, nimi);

        rs.close();
        stmt.close();
        connection.close();


        return keskustelu;    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelut");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");


            keskustelut.add(new Keskustelu(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
