/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.config;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Path("artiste")
public class ArtisteOperations {

    @Context
    private UriInfo context;

    
    public ArtisteOperations() {
    }

    //**************************insertion dans la table Artiste**********************************
    @GET
    @Path("insert&{id}&{biographie}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String InsertArtiste(@PathParam("id") int artisteid,
                                @PathParam("biographie") String biographie){
        
        
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Status", "Error");
        reponse.accumulate("Message", "Insertion échoué");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "insert into artiste values (?,?) ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,artisteid);
            stm.setString(2,biographie);
            
            int rows = stm.executeUpdate();
            
            if (rows > 0) {
                reponse.accumulate("Status", "OK");
                reponse.accumulate("Message", "Insertion réussi");
            }
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return reponse.toString();
    
    }
    //***********************modification de l'Artiste******************************
    @GET
    @Path("update&{id}&{biographie}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String updateArtiste(@PathParam("id") int artisteid,
                                @PathParam("biographie") String biographie){
        
        
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Status", "Error");
        reponse.accumulate("Message", "Modification échoué");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "update artiste set biographie = ? where artisteidutilisateur = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setString(1,biographie);
            stm.setInt(2,artisteid);
            
            int rows = stm.executeUpdate();
            
            if (rows > 0) {
                reponse.accumulate("Status", "OK");
                reponse.accumulate("Message", "Modification réussi");
            }
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return reponse.toString();
    
    }
    
    //**************************Afficher la liste des Artiste*****************************
     @GET
    @Path("allArtiste")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String selectAllArtiste() {
        
        JSONArray mainJSON = new JSONArray();
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            Statement stm = cn.createStatement();
            String sql = "select * from artiste";
            ResultSet rs = stm.executeQuery(sql);
            
            JSONObject art = new JSONObject();
            
            int idArtiste;
            String biographie;
            
            while (rs.next()) {

                idArtiste = rs.getInt("artisteidutilisateur");
                biographie = rs.getString("biographie");
                
                art.accumulate("id", idArtiste);
                art.accumulate("biographie", biographie);
                
                mainJSON.add(art);
                art.clear();
                
            }
            rs.close();
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
             System.out.println(e.getMessage());
        }
        
        return mainJSON.toString();
    }
    //************************Afficher un artiste par son ID**************************** 
    @GET
    @Path("singleArtiste&{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String singleArtiste(@PathParam("id") int artisteid){
        
        JSONObject singleArtiste = new JSONObject();
        singleArtiste.accumulate("Status", "Error");
        singleArtiste.accumulate("Message", "Artiste inexistant");
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "select * from artiste where artisteidutilisateur = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,artisteid);
            
            ResultSet rs = stm.executeQuery();
            
            
            int idArtiste;
            String biographie;
            
            while (rs.next()) {

                idArtiste = rs.getInt("artisteidutilisateur");
                biographie = rs.getString("biographie");
                
                singleArtiste.clear();
                singleArtiste.accumulate("id", idArtiste);
                singleArtiste.accumulate("biographie", biographie);
                    
            }
            rs.close();
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return singleArtiste.toString();
        
        
    }
    /// ******************Suprimer un Artiste****************************
    @GET
    @Path("delete&{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String deleteIntoClients(@PathParam("id") int artisteid){
    
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Status", "Error");
        reponse.accumulate("Message", "Suppression échoué");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "delete  from Artiste where artisteidutilisateur = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,artisteid);
            
            int rows = stm.executeUpdate();
            
            if (rows > 0) {
                reponse.accumulate("Status", "OK");
                reponse.accumulate("Message", "Suppression réussi");
            }
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return reponse.toString();
    
    }
    
    //************************** cas d'utilisation:Envoyer Message*****************
    
    @GET
    @Path("envoyer&{idMessage}&{dateMsg}&{objetMsg}&{texte}&{sender}&{receiver}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String envoyerMessage(@PathParam("idMessage") int idMsg,
                                @PathParam("dateMsg") Date dateM,
                                @PathParam("objetMsg") String objetM,
                               @PathParam("texte") String txt,
                               @PathParam("sender") int utiSender,
                               @PathParam("receiver") int utiReceiver){
        
        
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Status", "Error");
        reponse.accumulate("Message", "Envoi échoué");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "insert into message values (?,?,?,?,?,?) ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,idMsg);
            stm.setDate(2,dateM);
            stm.setString(3, objetM);
            stm.setString(4, txt);
            stm.setInt(5, utiSender);
            stm.setInt(6,utiReceiver);
            
            int rows = stm.executeUpdate();
            
            if (rows > 0) {
                reponse.accumulate("Status", "OK");
                reponse.accumulate("Message", "Envoi réussi");
            }
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return reponse.toString();
    
    }
    
    
    
}
