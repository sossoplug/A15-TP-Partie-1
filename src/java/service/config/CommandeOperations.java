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

@Path("commande")
public class CommandeOperations {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CommandeOperations
     */
    public CommandeOperations() {
    }

    /**
     * Retrieves representation of an instance of service.config.CommandeOperations
     * @return an instance of java.lang.String
     */
    
    @GET
    @Path("insert&{id}&{prix}&{dateAchat}&{idClient}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String InsertCommande(@PathParam("id") int idCommande,
                                @PathParam("prix") double prixCommande,
                                @PathParam("dateAchat") Date dateCommande,
                                @PathParam("idClient") int idClient){
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Status", "Error");
        reponse.accumulate("Message", "Insertion échoué");
        reponse.clear();
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "insert into commande values (?,?,?,?) ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,idCommande);
            stm.setDouble(2,prixCommande);
            stm.setDate(3, dateCommande);
            stm.setInt(4, idClient);
            
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
    
    @GET
    @Path("update&{id}&{prix}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String updateCommandes(@PathParam("id") int idCommande,
            @PathParam("prix") double prixCommande){
        
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Status", "Error");
        reponse.accumulate("Message", "Modification échoué");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "update commande set prix = ? where nocommande = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setDouble(1, prixCommande);
            stm.setInt(2, idCommande);
            
            
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
    
    @GET
    @Path("allOrders")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String selectAllOrders() {

        JSONArray mainJSON = new JSONArray();

        try {
            Connection cn = utils.DBOperation.connectionBd();

            Statement stm = cn.createStatement();
            String sql = "select * from commande";
            ResultSet rs = stm.executeQuery(sql);

            JSONObject cmd = new JSONObject();

            int noCommande;
            double prixCommande;
            String dateAchat;
            int idClient;

            while (rs.next()) {

                noCommande = rs.getInt("nocommande");
                prixCommande = rs.getDouble("prix");
                dateAchat = rs.getString("dateachat");
                idClient = rs.getInt("clientid");

                cmd.accumulate("No Commande", noCommande);
                cmd.accumulate("Prix", prixCommande);
                cmd.accumulate("Date achat", dateAchat);
                cmd.accumulate("ID client", idClient);

                mainJSON.add(cmd);
                cmd.clear();

            }
            rs.close();
            stm.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return mainJSON.toString();
    }
    
    @GET
    @Path("singleCommande&{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String selectSingleCommande(@PathParam("id") int commandeid){
        JSONObject singleCommande = new JSONObject();
        singleCommande.accumulate("Status", "Error");
        singleCommande.accumulate("Message", "Artiste inexistant");
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "select * from commande where nocommande = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,commandeid);
            
            ResultSet rs = stm.executeQuery();
            
            
            int idCommande;
            double prix;
            String dateAchat;
            int idClient;
            
            while (rs.next()) {

                idCommande = rs.getInt("nocommande");
                prix = rs.getDouble("prix");
                dateAchat = rs.getString("dateAchat");
                idClient = rs.getInt("clientid");
                
                singleCommande.clear();
                singleCommande.accumulate("idCommande", idCommande);
                singleCommande.accumulate("prix", prix);
                singleCommande.accumulate("dateAchat", dateAchat);
                singleCommande.accumulate("idClient", idClient);
                    
            }
            rs.close();
            stm.close();
            cn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return singleCommande.toString();
    }
    
    @GET
    @Path("delete&{id}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String deleteIntoCommandes(@PathParam("id") int commandeid){
        JSONObject reponse = new JSONObject();
        reponse.accumulate("Status", "Error");
        reponse.accumulate("Message", "Suppression échoué");
        reponse.clear();
        
        
        try{
            Connection cn = utils.DBOperation.connectionBd();
            
            String sql = "delete  from commande where nocommande = ? ";
            PreparedStatement stm = cn.prepareStatement(sql);
            stm.setInt(1,commandeid);
            
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
    
//________________cas utilisation clients avec tous les commandes________________
    @GET
    @Path("clientCommandes&{client}")
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    public String ClientCommandes(@PathParam("client") int noclient) {

        JSONArray mainJSON = new JSONArray();

        try {
            Connection cn = utils.DBOperation.connectionBd();

            Statement stm = cn.createStatement();
            String sql = "select * from commande where clientid="+noclient;
	    
	    //stm.setInt(4,noclient);
			
            ResultSet rs = stm.executeQuery(sql);

            JSONObject cmd = new JSONObject();

            int idClient;
            int noCommande;
            double prixCommande;
            String dateAchat;

            while (rs.next()) {

                noCommande = rs.getInt("nocommande");
                prixCommande = rs.getDouble("prix");
                dateAchat = rs.getString("dateachat");
                idClient = rs.getInt("clientid");

                cmd.accumulate("ID client", idClient);
                cmd.accumulate("No Commande", noCommande);
                cmd.accumulate("Prix", prixCommande);
                cmd.accumulate("Date achat", dateAchat);

                mainJSON.add(cmd);
                cmd.clear();

            }
            rs.close();
            stm.close();
            cn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return mainJSON.toString();
    }
    
}
