import java.sql.*;
public class Main {

    public static void main(String[] args) throws SQLException {
        testConnection();
    }

    private static void testConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Spidermanenbatman2023";
        Connection con = DriverManager.getConnection(url);
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
        con.close();
    }
}
