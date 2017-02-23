package tikape.runko.database;

import tikape.runko.domain.Viesti;

import java.sql.*;
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
        return null;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
