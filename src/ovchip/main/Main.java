package ovchip.main;

import ovchip.dao.ReizigerDAO;
import ovchip.dao.ReizigerDAOPsql;
import ovchip.domain.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection conn = null;

        // Start verbinding met de database
        conn = startConnection();

        // Test de verbinding door een query uit te voeren
        testConnection(conn);

        // Maak een ReizigerDAO aan en test de CRUD-operaties
        ReizigerDAO reizigerDAO = new ReizigerDAOPsql(conn);
        testReizigerDAO(reizigerDAO);


        // Sluit de verbinding met de database
        closeConnection(conn);

    }


    // Maakt verbinding met de database
    private static Connection startConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Spidermanenbatman2023";
        return DriverManager.getConnection(url);
    }

    // Sluit de connectie met de database
    private static void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }


    // Test de connectie met de database door middel van een query
    private static void testConnection(Connection con) throws SQLException {
        String query = "SELECT * FROM reiziger;";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet set = statement.executeQuery();
        System.out.println("Alle reizigers:");
        while (set.next()) {
            int reizigerId = set.getInt("reiziger_id");
            String voorletters = set.getString("voorletters");
            String tussenvoegsel = set.getString("tussenvoegsel");
            String achternaam = set.getString("achternaam");
            String geboortedatum = set.getString("geboortedatum");

            String volledigeNaam;
            if (tussenvoegsel != null) {
                volledigeNaam = voorletters + ". " + tussenvoegsel + " " + achternaam;
            } else {
                volledigeNaam = voorletters + ". " + achternaam;
            }

            System.out.printf("#%d: %s (%s)\n", reizigerId, volledigeNaam, geboortedatum);
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = null;
        reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum).toLocalDate());
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Test update operatie
        System.out.println("[Test] Update reiziger 77 (Sietske Boers) achternaam naar 'Jansen'");
        sietske.setAchternaam("Jansen");
        rdao.update(sietske);
        Reiziger updatedReiziger = null;
        updatedReiziger = rdao.findById(77);
        System.out.println("Geüpdatete reiziger: " + updatedReiziger);
        System.out.println();

        // Test delete operatie
        System.out.println("[Test] Verwijder reiziger 77 (Sietske Jansen)");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("Aantal reizigers na verwijderen: " + reizigers.size() + "\n");

        // Controleer of reiziger 77 succesvol is verwijderd
        Reiziger deletedReiziger = null;
        deletedReiziger = rdao.findById(77);
        if (deletedReiziger == null) {
            System.out.println("Reiziger 77 is succesvol verwijderd.");
        } else {
            System.out.println("Reiziger 77 is NIET succesvol verwijderd: " + deletedReiziger);
        }
    }
}




