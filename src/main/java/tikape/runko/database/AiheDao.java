package tikape.runko.database;

import tikape.runko.domain.Aihe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AiheDao implements Dao<Aihe, Integer> {
    private Database database;

    public AiheDao(Database data) {
        this.database = data;
    }

    @Override
    public Aihe findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihe WHERE id = ?");
        stmt.setObject(1, key);


        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) return null;

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
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        for(Aihe a : aiheet){
            a.setMaara(keskusteluDao.viestienMaara(a.getId()));
        }

        return aiheet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        keskusteluDao.deleteFrom(key);
        Connection connection = database.getConnection();
        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM Aihe WHERE id = ?");
        stmnt.setObject(1, key);
        stmnt.execute();
        stmnt.close();
        connection.close();

    }

    @Override
    public void add(Aihe lisattava) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmnt = connection.prepareStatement("INSERT INTO Aihe ('aihe') VALUES (?)");
        stmnt.setObject(1, lisattava.getNimi());
        stmnt.execute();
        stmnt.close();
        connection.close();
    }

    public int viestienMaara(int id) throws SQLException {
        Connection connection = database.getConnection();
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);

        PreparedStatement stmnt = connection.prepareStatement("SELECT id FROM Aihe WHERE aihe_id = ?");
        stmnt.setObject(1, id);
        ResultSet rs = stmnt.executeQuery();
        ArrayList<Integer> list = new ArrayList();
        while (rs.next()) {
            list.add(rs.getInt("id"));
        }
        rs.close();
        stmnt.close();
        connection.close();
        int palautus = 0;
        for (int i : list) {
            palautus += keskusteluDao.viestienMaara(i);
        }

        return palautus;

    }


}
