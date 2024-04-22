package m2l.desktop.gestion.controller;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import m2l.desktop.gestion.model.AffichageIntervention;
import m2l.desktop.gestion.model.Intervenant;
import m2l.desktop.gestion.model.Intervention;
import m2l.desktop.gestion.model.Salle;

/**
 *
 * @author nathalie
 */
public class InterventionsJourTableviewController implements Initializable{

    /**
     * Gestion de la connexion à la base de données
     */
    private Connection connexion;
    private Statement stmt;
    /**
     * List contenant les données à affiché dans le
     * premier onglet
     */
    private ObservableList<AffichageIntervention> donnees_interventions;

    /**
     * SITUATION B : Mise en place l’onglet « Voir toutes les interventions ».
     */
    private ObservableList<AffichageIntervention> donnees_interventions_jour_all;
    @FXML
    public TableView interventionJour;

    @FXML
    public TableColumn<AffichageIntervention,String> salleCol_all;
    @FXML
    public TableColumn <AffichageIntervention,String>intervenantCol_all;
    @FXML
    public TableColumn <AffichageIntervention,Number>contactCol_all;
    @FXML
    public TableColumn <AffichageIntervention,String>motifCol_all;
    @FXML
    public TableColumn <AffichageIntervention,String>statutCol_all;
    @FXML
    public Label dateJour;

    //liste contenant les salles à afficher
    private List <AffichageIntervention> liste_des_interventions_du_jour = new ArrayList<AffichageIntervention>();


    /**
     * Méthode lancée lors de l'appui sur le bouton
     * sur la page d'accueil
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
        Date date_du_jour = new Date();
        String sd=formatter.format(date_du_jour);
         dateJour.setText(sd);
        configureOngletInterventionsJour();


    }

    private void configureOngletInterventionsJour()
    {
        try
        {
            //configuration de la base de données
            String url1 = "jdbc:mysql://localhost:3337/sitem2l?&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            //utilisateur pour connexion à la BDD
            String user = "gestuser";
            //mot de passe pour connexion à la BDD
            String password = "gestpass";

            //établissement de la connexion à la base de données
            connexion = (Connection) DriverManager.getConnection(url1, user, password);

            if (connexion != null)
            {
                System.out.println("Connexion à la base de donnée gestsalle");

                try
                {
                    System.out.println("Chargement des salles du jour...");
                    //création d'un "Statement" pour exécuter la requête
                    stmt = connexion.createStatement();
                    String sql = "SELECT I.date, I.heure, motif, I.statut, S.nom as nomSalle, P.nom AS nomIntervenant, prenom, telephone " +
                            "FROM interventions I " +
                            "JOIN salles S ON S.numeroSalle = numSalle " +
                            "JOIN intervenants P ON P.numeroInter = numIntervenant " +
                            "WHERE I.date = CURDATE() " +
                            "ORDER BY I.date ASC, I.heure ASC";
                    System.out.println(this.getClass()+" - requête :"+sql);
                    //exécution de la requête
                    ResultSet rs = stmt.executeQuery(sql);
                    //parcours des enregistrements résultats,
                    //création de nouveaux objets "salle" et
                    //ajout de cet objet dans la liste
                    while(rs.next())
                    {
                        liste_des_interventions_du_jour.add(new AffichageIntervention(new Salle(rs.getString("nomSalle")),
                                new Intervenant(rs.getString("nomIntervenant"),rs.getString("prenom"),Integer.valueOf(rs.getString("telephone"))),
                                new Intervention(rs.getString("motif"), rs.getString("statut"))
                        ));
                    }
                    salleCol_all.setCellValueFactory(cell->cell.getValue().getNomSalleProperty());
                    intervenantCol_all.setCellValueFactory(cell->cell.getValue().getIntervenantProperty());
                    contactCol_all.setCellValueFactory(cell->cell.getValue().getContactProperty());
                    motifCol_all.setCellValueFactory(cell->cell.getValue().getMotifProperty());
                    statutCol_all.setCellValueFactory(cell->cell.getValue().getStatutProperty());
                    donnees_interventions_jour_all= FXCollections.observableList(liste_des_interventions_du_jour);

                    if (interventionJour == null) {
                        System.out.println("TableView is null");
                    } else {
                        System.out.println("TableView is not null");
                    }
                    interventionJour.setItems(donnees_interventions_jour_all);

                    rs.close();
                }
                catch(SQLException se)
                {
                    //exécuté si la requête ne s'est pas bien exécutée
                    se.printStackTrace();
                }
                finally
                {


                    if(stmt!=null)
                    {
                        try
                        {
                            stmt.close();
                        } catch (SQLException ex)
                        {
                            //Logger.getLogger(BarMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        catch (SQLException ex)
        {
            //s'il y a une erreur le message suivant s'affiche en ligne de commande
            System.out.println("Erreur de connexion à la bdd. Identifiant ou mot de passe invalide.");
            ex.printStackTrace();

        }

    }


}
