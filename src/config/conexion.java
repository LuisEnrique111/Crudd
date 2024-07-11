package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexion {

    Connection con;

    public conexion() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://informacion1.cx64m66e6epw.us-east-1.rds.amazonaws.com:3306/dbContacto?characterEncoding=UTF-8", "Luis", "Yunogasai20.");
        } catch (Exception e) {
            System.out.println("Error clase conexion: " + e);
        }
    }

    public Connection getConnection(){
        return con;
    }
    
    public void getdemo(){
    }
}
