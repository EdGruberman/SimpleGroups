package edgruberman.bukkit.simplegroups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import edgruberman.bukkit.messagemanager.MessageLevel;
import edgruberman.bukkit.messagemanager.MessageManager;

public class Main extends org.bukkit.plugin.java.JavaPlugin {
    
    protected static MessageManager messageManager;
    
    private static Connection connection;
    
    public void onLoad() {
        Configuration.load(this);
    }
    
    public void onEnable() {
        Main.messageManager = new MessageManager(this);
        Main.messageManager.log("Version " + this.getDescription().getVersion());
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            Main.messageManager.log(MessageLevel.SEVERE, "Unable to find JDBC sqlserver driver.", e);
        }
        
        String connectionUrl = "jdbc:sqlserver://" + this.getConfiguration().getString("serverName") + ";"
            + "databaseName=" + this.getConfiguration().getString("databaseName") + ";"
            + "integratedSecurity=true;"
        ;
        try {
            Main.connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            Main.messageManager.log(MessageLevel.SEVERE, "Unable to connect to database.", e);
        }
        
        Main.messageManager.log("Plugin Enabled");
    }
    
    public void onDisable() {
        Main.messageManager.log("Plugin Disabled");
        Main.messageManager = null;
    }
    
    protected static boolean isGroupExists(String ign) {
        try {
            String SQL = "SELECT IGN FROM Groups WHERE IGN = '" + ign + "'";
            Statement stmt = Main.connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            return rs.next();
         } catch (Exception e) {
             Main.messageManager.log(MessageLevel.SEVERE, "Error querying database for \"" + ign + "\" group existence.", e);
         }
         
         return false;
    }
    
    protected static List<Member> getMembers(Group group) {
        List<Member> members = new ArrayList<Member>();
        
        try {
            String SQL = "SELECT Players_Name FROM Members WHERE Groups_IGN = '" + group.getName().replaceAll("'", "''") + "'";
            Statement stmt = Main.connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                members.add(new Member(rs.getString("Players_Name")));
            }
            rs.close();
            stmt.close();
         } catch (Exception e) {
             Main.messageManager.log(MessageLevel.SEVERE, "Error querying database for members of \"" + group.getName() + "\".", e);
         }
         
         Main.messageManager.log(MessageLevel.FINE, "Group \"" + group.getName() + "\" loaded; Members: " + members.size());
         
         return members;
    }
}
