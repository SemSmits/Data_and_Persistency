package ovchip.dao;

import ovchip.domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {

        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, reiziger.getId());
        ps.setString(2, reiziger.getVoorletters());
        ps.setString(3, reiziger.getTussenvoegsel());
        ps.setString(4, reiziger.getAchternaam());
        ps.setDate(5, Date.valueOf(reiziger.getGeboortedatum()));

        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;

    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {

        String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, reiziger.getVoorletters());
        ps.setString(2, reiziger.getTussenvoegsel());
        ps.setString(3, reiziger.getAchternaam());
        ps.setDate(4, Date.valueOf(reiziger.getGeboortedatum()));
        ps.setInt(5, reiziger.getId());

        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;

    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {

        String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, reiziger.getId());

        int rows = ps.executeUpdate();
        ps.close();
        return rows > 0;

    }

    @Override
    public Reiziger findById(int id) throws SQLException {

        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int reizigerId = rs.getInt("reiziger_id");
            String voorletters = rs.getString("voorletters");
            String tussenvoegsel = rs.getString("tussenvoegsel");
            String achternaam = rs.getString("achternaam");
            LocalDate geboortedatum = rs.getDate("geboortedatum").toLocalDate();

            Reiziger reiziger = new Reiziger(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum);
            rs.close();
            ps.close();
            return reiziger;
        }

        rs.close();
        ps.close();
        return null;

    }

    @Override
    public List<Reiziger> findByGbdatum(Date geboortedatum) {
        return List.of();
    }

    @Override
    public List<Reiziger> findByGbdatum(LocalDate geboortedatum) throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();

        String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDate(1, Date.valueOf(geboortedatum));
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int reizigerId = rs.getInt("reiziger_id");
            String voorletters = rs.getString("voorletters");
            String tussenvoegsel = rs.getString("tussenvoegsel");
            String achternaam = rs.getString("achternaam");
            LocalDate geboortedatumReiziger = rs.getDate("geboortedatum").toLocalDate();

            Reiziger reiziger = new Reiziger(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatumReiziger);
            reizigers.add(reiziger);
        }

        rs.close();
        ps.close();

        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigers = new ArrayList<>();

        String query = "SELECT * FROM reiziger";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int reizigerId = rs.getInt("reiziger_id");
            String voorletters = rs.getString("voorletters");
            String tussenvoegsel = rs.getString("tussenvoegsel");
            String achternaam = rs.getString("achternaam");
            LocalDate geboortedatum = rs.getDate("geboortedatum").toLocalDate();

            Reiziger reiziger = new Reiziger(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum);
            reizigers.add(reiziger);
        }

        rs.close();
        ps.close();

        return reizigers;
    }
}
