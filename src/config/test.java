
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */
public class test {
        public static void main(String[] args) throws ClassNotFoundException {

        String jdbcUrl = "jdbc:mysql://informacion1.cx64m66e6epw.us-east-1.rds.amazonaws.com:3306/dbContacto?characterEncoding=UTF-8";
        String username = "Luis";
        String password = "Yunogasai20.";

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //conexion
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            if (connection != null) {
                System.out.println("Conexion exitosa");
            }

        } catch (SQLException e) {
            System.out.println("error conexion base de datos: " + e);
        }

    }
}
