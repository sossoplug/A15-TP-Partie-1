/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author adams
 */
@Path("categorie")
public class CategorieOperations {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CategorieOperations
     */
    public CategorieOperations() {
    }

    /**
     * Retrieves representation of an instance of
     * service.config.CategorieOperations
     *
     * @param nocategorie
     * @param nom
     * @return an instance of java.lang.String
     */
    //---------------------------INSERTION DANS LA TABLE CATEGORIE--------------------------------------------------------
    //http://localhost:8080/A15-TP-Partie-1/galerieArt/categorie/insert&1006&Art Asiatique
    
    @GET
    @Path("insert&{nocategorie}&{nom}")

    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)

    public String InsertIntoCategorie(@PathParam("nocategorie") String nocategorie,
            @PathParam("nom") String nom) {

        JSONObject reponse = new JSONObject();
        reponse.accumulate("Statut", "Erreur");
        reponse.accumulate("Message", "Insertion échouée");
        reponse.clear();

        try {
            Connection cn = utils.DBOperation.connectionBd();

            String sql = "insert into categorie values (?,?) ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setString(1, nocategorie);
            stm.setString(2, nom);

            int rows = stm.executeUpdate();

            if (rows > 0) {
                reponse.accumulate("Statut", "OK");
                reponse.accumulate("Message", "Insertion réussie");
            }
            stm.close();
            cn.close();
        } catch (SQLException e) {

        }

        return reponse.toString();

    }

    //-------------------UPDATE DANS LA TABLE CATEGORIE--------------------------------
     
    @GET
    @Path("update&{nocategorie}&{nom}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String updateIntoCategorie(@PathParam("nocategorie") String nocategorie,
            @PathParam("nom") String nom) {

        JSONObject reponse = new JSONObject();
        reponse.accumulate("Statut", "Erreur");
        reponse.accumulate("Message", "Modification échouée");
        reponse.clear();

        try {
            Connection cn = utils.DBOperation.connectionBd();

            String sql = "update categorie set nom = ? where nocategorie = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setString(1, nom);
            stm.setString(2, nocategorie);

            int rows = stm.executeUpdate();

            if (rows > 0) {
                reponse.accumulate("Statut", "OK");
                reponse.accumulate("Message", "Modification réussie");
            }
            stm.close();
            cn.close();
        } catch (SQLException e) {

        }

        return reponse.toString();

    }

    //----------------SUPRESSION DANS LA TABLE CATEGORIE-----------------------------------------
    
    @GET
    @Path("delete&{nocategorie}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String deleteIntoClients(@PathParam("nocategorie") String nocategorie) {

        JSONObject reponse = new JSONObject();
        reponse.accumulate("Statut", "Erreur");
        reponse.accumulate("Message", "Suppression échouée");
        reponse.clear();

        try {
            Connection cn = utils.DBOperation.connectionBd();

            String sql = "delete  from categorie where nocategorie = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setString(1, nocategorie);

            int rows = stm.executeUpdate();

            if (rows > 0) {
                reponse.accumulate("Statut", "OK");
                reponse.accumulate("Message", "Suppression réussie");
            }
            stm.close();
            cn.close();
        } catch (SQLException e) {

        }

        return reponse.toString();

    }

    //----------LISTER TOUT LE CONTENU DE LA TABLE CATEGORIE-------------------------------------------
     
    @GET
    @Path("allCategorie")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String selectAllFromClients() {

        JSONArray mainJSON = new JSONArray();

        try {
            Connection cn = utils.DBOperation.connectionBd();

            Statement stm = cn.createStatement();
            String sql = "select * from categorie";
            ResultSet rs = stm.executeQuery(sql);

            JSONObject categorie = new JSONObject();

            String nocategorie;
            String nom;

            while (rs.next()) {

                nocategorie = rs.getString("nocategorie");
                nom = rs.getString("nom");

                categorie.accumulate("nocategorie", nocategorie);
                categorie.accumulate("nom", nom);

                mainJSON.add(categorie);
                categorie.clear();

            }
            rs.close();
            stm.close();
            cn.close();
        } catch (SQLException e) {

        }

        return mainJSON.toString();
    }

    //--------LISTER PAR ELEMENT DE LA TABLE--------
   
    @GET
    @Path("singleCategorie&{nocategorie}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String selectSingleCategorie(@PathParam("nocategorie") String nocategorie) {

        JSONObject singleCategorie = new JSONObject();
        singleCategorie.accumulate("Statut", "Erreur");
        singleCategorie.accumulate("Message", "Categorie not exists");

        try {
            Connection cn = utils.DBOperation.connectionBd();

            String sql = "select * from categorie where nocategorie = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setString(1, nocategorie);

            ResultSet rs = stm.executeQuery();

            String noCategorie;
            String nom;

            while (rs.next()) {

                noCategorie = rs.getString("nocategorie");
                nom = rs.getString("nom");

                singleCategorie.clear();
                singleCategorie.accumulate("nocategorie", noCategorie);
                singleCategorie.accumulate("nom", nom);

            }
            rs.close();
            stm.close();
            cn.close();
        } catch (SQLException e) {

        }

        return singleCategorie.toString();

    }
}
