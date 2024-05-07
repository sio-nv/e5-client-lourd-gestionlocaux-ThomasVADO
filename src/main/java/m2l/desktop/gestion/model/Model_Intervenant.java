package m2l.desktop.gestion.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Model_Intervenant {
    private static Connection connexion;
    private static ObservableList<Intervenant> intervenants = FXCollections.observableArrayList();

    public static ObservableList<Intervenant> getIntervenants() {
        return intervenants;
    }
    public static ObservableList<String> getNomsIntervenants() {
        ObservableList<String> noms = FXCollections.observableArrayList();
        for (Intervenant intervenant : getIntervenants()) {
            if (intervenant.getNom() != null && intervenant.getPrenom() != null) { // Assurez-vous que le numéro et le nom ne sont pas nuls
                String intervenantInfo = intervenant.getNom() + " " + intervenant.getPrenom();
                noms.add(intervenantInfo);
            }
        }
        return noms;
    }

    public static void connect_to_database() {
        String url = "jdbc:mysql://localhost:3337/sitem2l?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "gestuser";
        String password = "gestpass";
        try {
            if (connexion == null || connexion.isClosed()) {
                connexion = DriverManager.getConnection(url, user, password);
                System.out.println("Connexion établie avec succès.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur de connexion à la BDD: " + ex.getMessage());
        }
    }
/**
    public static void selectSalles() {
        String sql = "SELECT prenom, nom FROM intervenants";
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            intervenants.clear();
            while (rs.next()) {
                String prenomIntervenant = rs.getString("prenom");
                String nomIntervenant = rs.getString("nom");
                Intervenant i = new Intervenant(prenomIntervenant, nomIntervenant);
                intervenants.add(i);
            }
        } catch (SQLException se) {
            System.err.println("Erreur lors de l'exécution de la requête: " + se.getMessage());
        }
        System.out.println("Nombre de salles chargées: " + salles.size());
    }
 **/
}
