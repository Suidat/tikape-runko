package tikape.runko.database;

import tikape.runko.domain.Keskustelu;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeskusteluDao implements Dao<Keskustelu, Integer>{

    
    private Database database;
    
    public KeskusteluDao(Database database){this.database = database;}
    
    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelut WHERE id = ?");
        stmt.setObject(1, key);

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

    public List<Keskustelu> findAllInAihe(int key) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelut WHERE aihe_id=?");
        stmt.setObject(1, key);


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
        Connection connection = database.getConnection();

        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM Keskustelut WHERE id = ?");
        stmnt.setObject(1, key);
        stmnt.execute();
        stmnt.close();
        connection.close();
    }

    public void deleteFrom(Integer aihe) throws  SQLException{
        ViestiDao viestiDao = new ViestiDao(database);
        Connection connection = database.getConnection();

        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM Keskustelut WHERE aihe_id = ?");
        stmnt.setObject(1, aihe);
        stmnt.execute();
        stmnt.close();
        connection.close();
        PreparedStatement stmnt2 = connection.prepareStatement("SELECT * FROM Keskustelut WHERE aihe_id = ?");
        stmnt2.setObject(1, aihe);
        ResultSet rs = stmnt2.executeQuery();
        while(rs.next()) {
            viestiDao.deleteFrom(rs.getInt("id"));
        }
        stmnt2.close();
        connection.close();
    }


    @Override
    public void add(Keskustelu lisattava) throws SQLException {

    }


}
