package tikape.runko.database;

import tikape.runko.domain.Viesti;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViestiDao implements Dao<Viesti, Integer> {
    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestit WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String sisalto = rs.getString("sisältö");
        String lahettaja = rs.getString("lahettaja");
        String aika = rs.getString("aika");

        Viesti o = new Viesti(id, sisalto, lahettaja, aika);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestit");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lahettaja");
            String sisalto = rs.getString("viesti");
            String  aika = rs.getString("aika");
            viestit.add(new Viesti(id, sisalto, lahettaja, aika ));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;

    }

    public List<Viesti> findAllInKeskustelu(int key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestit WHERE keskustelu_id = ?");
        stmt.setObject(1, key);


        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("sender");
            String sisalto = rs.getString("message");
            String aika = rs.getDate("time").toString();
            viestit.add(new Viesti(id, sisalto, lahettaja, aika ));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;

    }

    public List<Integer> viestienMaara(int key) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(id) AS määrä FROM Viestit" +
                " WHERE keskustelu_id = ? GROUP BY Keskustelu_id ORDER BY Keskustelu_id");
        stmt.setObject(1, key);


        ResultSet rs = stmt.executeQuery();
        List<Integer> numero = new ArrayList<>();
        while (rs.next()) {
            numero.add(rs.getInt("määrä"));
        }

        rs.close();
        stmt.close();
        connection.close();

        return numero;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
