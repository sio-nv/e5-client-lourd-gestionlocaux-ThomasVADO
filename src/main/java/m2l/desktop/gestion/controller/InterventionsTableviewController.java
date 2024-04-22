package m2l.desktop.gestion.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import m2l.desktop.gestion.AjoutIntervention;
import m2l.desktop.gestion.model.AffichageIntervention;
import m2l.desktop.gestion.model.Intervenant;
import m2l.desktop.gestion.model.Intervention;
import m2l.desktop.gestion.model.Salle;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InterventionsTableviewController implements Initializable {


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
     * récupération des éléments définis dans la vue (fxml)
     */
    @FXML
    public TableView todayInt;

    @FXML
    public TableColumn<AffichageIntervention,String> salleCol;
    @FXML
    public TableColumn <AffichageIntervention,String>intervenantCol;
    @FXML
    public TableColumn <AffichageIntervention,Number>contactCol;
    @FXML
    public TableColumn <AffichageIntervention,String>motifCol;
    //SITUATION A : Finalisation de l’affichage l’onglet « Interventions du jour ».
    @FXML
    public TableColumn <AffichageIntervention,String>statutCol;
    //SITUATION A : Finalisation de l’affichage l’onglet « Interventions du jour ».
    @FXML
    public TableColumn <AffichageIntervention,String>dateCol;



    /**
     * SITUATION B : Mise en place l’onglet « Voir toutes les interventions ».
     */
    private ObservableList<AffichageIntervention> donnees_interventions_all;
    @FXML
    public TableView toutesInterventions;

    @FXML
    public TableColumn<AffichageIntervention,String> salleCol_all;
    @FXML
    public TableColumn <AffichageIntervention,String>intervenantCol_all;
    @FXML
    public TableColumn <AffichageIntervention,Number>contactCol_all;
    @FXML
    public TableColumn <AffichageIntervention,String>motifCol_all;
    @FXML
    public TableColumn <AffichageIntervention,String>dateCol_all;
    @FXML
    public Label dateJour;

    //liste contenant les salles à afficher
    private List<AffichageIntervention> liste_des_interventions = new ArrayList<AffichageIntervention>();


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
        configureOngletInterventions();

    }



    private void configureOngletInterventions()
    {
        try
        {
            //configuration de la base de données
            String url1 = "jdbc:mysql://localhost:3337/sitem2l?&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            //utilisateur pour connexion à la BDD
            String user = "gestuser";
            //            //mot de passe pour connexion à la BDD
            String password = "gestpass";

            //établissement de la connexion à la base de données
            connexion = (Connection) DriverManager.getConnection(url1, user, password);

            if (connexion != null)
            {
                System.out.println("Connexion à la base de donnée gestsalle");

                try
                {
                    System.out.println("Chargement des salles...");
                    stmt = connexion.createStatement();
                    String sql = "SELECT I.date, I.heure, motif, S.nom as nomSalle, P.nom " +
                            "AS nomIntervenant, prenom, telephone FROM interventions I " +
                            "join salles S on S.numeroSalle=numSalle " +
                            "join intervenants P on P.numeroInter = numIntervenant";
                    System.out.println(this.getClass()+" - requête :"+sql);
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next())
                    {
                        liste_des_interventions.add(new AffichageIntervention(new Salle(rs.getString("nomSalle")),
                                new Intervenant(rs.getString("nomIntervenant"),rs.getString("prenom"),Integer.valueOf(rs.getString("telephone"))),
                                new Intervention(rs.getString("motif"), rs.getDate("date"))
                        ));
                    }
                    salleCol_all.setCellValueFactory(cell->cell.getValue().getNomSalleProperty());
                    intervenantCol_all.setCellValueFactory(cell->cell.getValue().getIntervenantProperty());
                    contactCol_all.setCellValueFactory(cell->cell.getValue().getContactProperty());
                    motifCol_all.setCellValueFactory(cell->cell.getValue().getMotifProperty());
                    dateCol_all.setCellValueFactory(cell->cell.getValue().getDateProperty());
                    donnees_interventions_all=FXCollections.observableList(liste_des_interventions);
                    toutesInterventions.setItems(donnees_interventions_all);
                    rs.close();
                }
                catch(SQLException se)
                {
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

    public void ajoutIntervention(MouseEvent mouseEvent)
    {
        Scene scene = (Scene) ((ImageView)mouseEvent.getSource()).getScene();
        Stage stage = (Stage) scene.getWindow();
        new AjoutIntervention(stage);
    }

}
