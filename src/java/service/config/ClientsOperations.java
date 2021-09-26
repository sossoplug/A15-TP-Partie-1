package service.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Path("clients")
public class ClientsOperations {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DataBaseOperation
     */
    public ClientsOperations() {
    }

    /**
     * Retrieves representation of an instance of service.config.ClientsOperations
     * @return an instance of java.lang.String
     */
    @GET
    @Path("allClients")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String selectAllFromClients() {
        
        JSONArray mainJSON = new JSONArray();
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            Statement stm = cn.createStatement();
            String sql = "select * from clients";
            ResultSet rs = stm.executeQuery(sql);
            
            JSONObject client = new JSONObject();
            
            int idClient;
            String adresse;
            
            while (rs.next()) {

                idClient = rs.getInt("clientidutilisateur");
                adresse = rs.getString("adresselivraison");
                
                client.accumulate("id", idClient);
                client.accumulate("adresse", adresse);
                
                mainJSON.add(client);
                client.clear();
                
            }
            rs.close();
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
             ///TRAITEMENT
        }
        
        return mainJSON.toString();
    }
    
    @GET
    @Path("singleClient&{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String selectSingleClient(@PathParam("id") int clientid){
        
        JSONObject singleClient = new JSONObject();
        singleClient.accumulate("Statut", "Erreur");
        singleClient.accumulate("Message", "Employee id not exists");
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "select * from clients where clientidutilisateur = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,clientid);
            
            ResultSet rs = stm.executeQuery();
            
            
            int idClient;
            String adresse;
            
            while (rs.next()) {

                idClient = rs.getInt("clientidutilisateur");
                adresse = rs.getString("adresselivraison");
                
                singleClient.clear();
                singleClient.accumulate("id", idClient);
                singleClient.accumulate("adresse", adresse);
                    
            }
            rs.close();
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            ///TRAITEMENT
        }
        
        return singleClient.toString();
        
        
    }
    
    
    @GET
    @Path("insert&{id}&{adresse}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String InsertIntoClients(@PathParam("id") int clientid,
                                @PathParam("adresse") String adresse){
        
        
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Statut", "Erreur");
        reponse.accumulate("Message", "Insertion échouée");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "insert into clients values (?,?) ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,clientid);
            stm.setString(2,adresse);
            
            int rows = stm.executeUpdate();
            
            if (rows > 0) {
                reponse.accumulate("Statut", "OK");
                reponse.accumulate("Message", "Insertion réussie");
            }
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            ///TRAITEMENT
        }
        
        return reponse.toString();
    
    }
    
    @GET
    @Path("update&{id}&{adresse}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String updateIntoClients(@PathParam("id") int clientid,
                                @PathParam("adresse") String adresse){
        
        
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Statut", "Erreur");
        reponse.accumulate("Message", "Modification échouée");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "update clients set adresselivraison = ? where clientidutilisateur = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setString(1,adresse);
            stm.setInt(2,clientid);
            
            int rows = stm.executeUpdate();
            
            if (rows > 0) {
                reponse.accumulate("Statut", "OK");
                reponse.accumulate("Message", "Modification réussie");
            }
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            ///TRAITEMENT
        }
        
        return reponse.toString();
    
    }
    
    @GET
    @Path("delete&{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String deleteIntoClients(@PathParam("id") int clientid){
    
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Statut", "Erreur");
        reponse.accumulate("Message", "Suppression échouée");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "delete  from clients where clientidutilisateur = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,clientid);
            
            int rows = stm.executeUpdate();
            
            if (rows > 0) {
                reponse.accumulate("Statut", "OK");
                reponse.accumulate("Message", "Suppression réussie");
            }
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            ///TRAITEMENT
        }
        
        return reponse.toString();
    
    }
    
    
   
}
