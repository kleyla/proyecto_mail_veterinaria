
package servicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class ConnectionDB {
    private static ConnectionDB instance = null;

    private final String db_name = "db_grupo03sa";
    private final String port = "5432";
    private final String host = "mail.ficct.uagrm.edu.bo";
    private final String usuario = "grupo03sa";
    private final String password = "grupo03grupo03";


    private Connection conn;

    private ConnectionDB() {
    }

    public synchronized static ConnectionDB getInstance() {
        
        instance = new ConnectionDB();
        instance.contectar();

        return instance;
    }

    public Connection getConnection() {
        return conn;
    }
    public void cerrarConexion(){
        try {
            conn.close();
          
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
          ConnectionDB.instance = null;
        }
    }
    
    private void contectar() {
        String urlDatabase = "jdbc:postgresql://" + host + ":" + port + "/" + db_name;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(urlDatabase, usuario, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
        }
    }
}
