package tikape.runko.database;

import tikape.runko.domain.Viesti;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ViestiDao implements Dao<Viesti, Integer> {
    private Database database;
    DateFormat df = new SimpleDateFormat("HH:mm:ss dd/MM/yy");
    Date date;

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

        int id = rs.getInt("id");
        String sisalto = rs.getString("sisältö");
        String lahettaja = rs.getString("lahettaja");
        String aika = rs.getString("aika");
        int keskustelu = rs.getInt("keskusteluId");
        Viesti o = new Viesti(id, sisalto, lahettaja, aika, keskustelu);

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
            int id = rs.getInt("id");
            String lahettaja = rs.getString("lahettaja");
            String sisalto = rs.getString("viesti");
            String  aika = rs.getString("aika");
            int keskustelu = rs.getInt("keskusteluId");
            viestit.add(new Viesti(id, sisalto, lahettaja, aika, keskustelu ));
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
            int id = rs.getInt("id");
            String lahettaja = rs.getString("sender");
            String sisalto = rs.getString("message");
            String aika = rs.getString("time");
            int keskustelu = rs.getInt("keskusteluId");
            viestit.add(new Viesti(id, sisalto, lahettaja, aika, keskustelu));
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

    public void lisaaViesti(Viesti viesti, int keskustelu) throws SQLException{



        Connection connection = database.getConnection();

        PreparedStatement stmnt = connection.prepareStatement(
                "INSERT INTO Viestit (sender, message, time,  Keskustelu_id) VALUES (?,?,?,?)");


    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM Viestit WHERE id = ?");
        stmnt.setObject(1, key);
        stmnt.execute();
        stmnt.close();
        connection.close();
    }

    public void deleteFrom(Integer keskustelu) throws SQLException{
        Connection connection = database.getConnection();
        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM Viestit WHERE keskustelu_id = ?");
        stmnt.setObject(1, keskustelu);
        stmnt.execute();
        stmnt.close();
        connection.close();
    }

    @Override
    public void add(Viesti lisattava) throws SQLException {
        Connection connection = database.getConnection();
        /*PreparedStatement stmnt = connection.prepareStatement("INSERT INTO Viestit () VALUES ()");
        stmnt.setObject(1, keskustelu);
        stmnt.execute();
        stmnt.close();*/
        connection.close();
    }


}
