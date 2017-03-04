package tikape.runko.database;

import tikape.runko.domain.Keskustelu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {
    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelut WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        Keskustelu keskustelu = new Keskustelu(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return keskustelu;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelut");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            keskustelut.add(new Keskustelu(nimi, id));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    @Override
    public void add(Keskustelu lisattava) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmnt = connection.prepareStatement("INSERT INTO Keskustelut (nimi, aihe_id) VALUES (?,?)");
        stmnt.setObject(1, lisattava.getNimi());
        stmnt.setObject(2, lisattava.getAiheId());
        stmnt.execute();
        stmnt.close();
        connection.close();

    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        ViestiDao viestiDao = new ViestiDao(database);

        viestiDao.deleteFrom(key);

        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM Keskustelut WHERE id = ?");
        stmnt.setObject(1, key);
        stmnt.execute();
        stmnt.close();
        connection.close();
    }

    public List<Keskustelu> findAllInAihe(int key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelut WHERE aihe_id = ?");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            keskustelut.add(new Keskustelu(nimi, id));
        }

        rs.close();
        stmt.close();
        ViestiDao viestiDao = new ViestiDao(database);

        for (Keskustelu k : keskustelut) {
            k.setMaara(viestiDao.viestienMaara(k.getId()));
            k.setAika(viestiDao.viimeisinViesti(k.getId()));
        }
        connection.close();

        return keskustelut;
    }

    public void deleteFrom(Integer aihe) throws SQLException {
        ViestiDao viestiDao = new ViestiDao(database);
        Connection connection = database.getConnection();

        PreparedStatement stmnt = connection.prepareStatement("DELETE FROM Keskustelut WHERE aihe_id = ?");
        stmnt.setObject(1, aihe);
        PreparedStatement stmnt2 = connection.prepareStatement("SELECT id FROM Keskustelut WHERE aihe_id = ?");
        stmnt2.setObject(1, aihe);
        ResultSet rs = stmnt2.executeQuery();
        ArrayList<Integer> arr = new ArrayList<>();
        while (rs.next()) {
            arr.add(rs.getInt("id"));
        }
        stmnt2.close();
        for (int i : arr) {
            viestiDao.deleteFrom(i);
        }
        stmnt.execute();
        stmnt.close();
        connection.close();


    }

    public int viestienMaara(int id) throws SQLException {
        Connection connection = database.getConnection();
        ViestiDao viestiDao = new ViestiDao(database);
        PreparedStatement stmnt = connection.prepareStatement("SELECT id FROM Keskustelut WHERE aihe_id = ?");
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
            palautus += viestiDao.viestienMaara(i);
        }

        return palautus;
    }

    public String viimeisinViesti(int key) throws SQLException{
        Connection connection = database.getConnection();
        TreeSet<String> set = new TreeSet<>();
        PreparedStatement stmnt = connection.prepareStatement("SELECT * FROM Keskustelut WHERE aihe_id = ?");
        stmnt.setObject(1, key);
        ResultSet rs = stmnt.executeQuery();
        ArrayList<Integer> list = new ArrayList<>();
        while(rs.next()){
            list.add(rs.getInt("id"));
        }
        rs.close();
        stmnt.close();
        connection.close();
        ViestiDao viestiDao = new ViestiDao(database);
        for(int i : list){
            set.add(viestiDao.viimeisinViesti(i));
        }
        return set.last();
    }



}
