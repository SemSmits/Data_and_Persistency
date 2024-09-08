import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        Connection con = startConnection();
        try {
            testConnection(con);
        } finally {
            closeConnection(con);
        }
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
}
