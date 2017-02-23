package tikape.runko.database;

import tikape.runko.domain.Aihe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AiheDao implements Dao<Aihe, Integer> {
    private Database database;

    public AiheDao(Database data){this.database = data;}

    @Override
    public Aihe findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihe WHERE id = ?");
        stmt.setObject(1, key);


        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if(!hasOne) return null;

            Integer id = rs.getInt("id");
                String nimi = rs.getString("aihe");
            Aihe aihe = new Aihe(id, nimi);

        rs.close();
        stmt.close();
        connection.close();


        return aihe;
    }

    @Override
    public List<Aihe> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihe");

        ResultSet rs = stmt.executeQuery();
        List<Aihe> aiheet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("Aihe");
            aiheet.add(new Aihe(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return aiheet;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
