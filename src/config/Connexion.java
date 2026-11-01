package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connexion {
    private String url="jdbc:mysql://localhost:3306/projetjava?autoReconnect=true&useSSL=false";
    private String login="root";
    private String pwd="";
    
    private static Connexion instance;  
    private static Connection connection;
    
    public static Connexion getInstance(){
        if(instance == null){
            instance = new Connexion();
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }  
    
    private Connexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,login,pwd);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}