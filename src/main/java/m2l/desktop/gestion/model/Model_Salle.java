package m2l.desktop.gestion.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class Model_Salle {
    private static Connection connexion;
    private static ObservableList<Salle> salles = FXCollections.observableArrayList();

    public static ObservableList<Salle> getSalles() {
        return salles;
    }
    public static ObservableList<String> getNomsSalles() {
        ObservableList<String> noms = FXCollections.observableArrayList();
        for (Salle salle : getSalles()) {
            if (salle.getNom() != null && salle.getNum() > 0) { // Assurez-vous que le numéro et le nom ne sont pas nuls
                String salleInfo = salle.getNum() + " - " + salle.getNom();
                noms.add(salleInfo);
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

    public static void selectSalles() {
        String sql = "SELECT numeroSalle, nom FROM salles";
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            salles.clear();
            while (rs.next()) {
                int numSalle = rs.getInt("numeroSalle");
                String nomSalle = rs.getString("nom");
                Salle s = new Salle(numSalle, nomSalle);
                salles.add(s);
            }
        } catch (SQLException se) {
            System.err.println("Erreur lors de l'exécution de la requête: " + se.getMessage());
        }
        System.out.println("Nombre de salles chargées: " + salles.size());
    }
}
