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
        Date aika = rs.getDate("aika");

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
            Date aika = rs.getDate("aika");
            viestit.add(new Viesti(id, sisalto, lahettaja, aika ));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
