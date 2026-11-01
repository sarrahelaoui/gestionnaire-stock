package config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUD {
    
    Connexion mc = Connexion.getInstance();

    // AJOUT : double les apostrophes pour ne pas casser la requête SQL (ex: L'oreal)
    public static String esc(String s) {
        return s == null ? "" : s.replace("'", "''");
    }
    
    public boolean executeInsert( String sql){ 
            try {
                Statement statement = mc.getConnection().createStatement();
                statement.executeUpdate(sql);
                System.out.println("Insertion effectué avec success");
                return true;
            } catch (SQLException ex) {
                System.err.println("Erreur de Insertion");
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
    }
    
    public boolean executeUpdate(String sql)
    {
            try {
                Statement statement = mc.getConnection().createStatement();
                statement.executeUpdate(sql);
                System.out.println("Modification effectué avec success");
                return true;
            } catch (SQLException ex) {
                System.err.println("Erreur de Modification");
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
    }
    
    public boolean executeDelete(String sql)
    {
            try {
                Statement statement = mc.getConnection().createStatement();
                statement.executeUpdate(sql);
                System.out.println("Suppression effectué avec success");
                return true;
            } catch (SQLException ex) {
                System.err.println("Erreur de Suppression");
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
    }
    
    public ResultSet executeSelect(String sql)
    {
            try {
                Statement statement = mc.getConnection().createStatement();
                ResultSet rs = statement.executeQuery(sql);
                System.out.println("Selection effectué ! ");
                return rs;
            } catch (SQLException ex) {
                System.err.println("Erreur de Selection ! ");
                Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
    }
}