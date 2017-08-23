package ir.logcisims.mantegh.Database;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by amirbakhtiari on 20/08/2017.
 */

public class MySql {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static String host;
    private static String user;
    private static String password;
    private static int port;
    private static String db;

    private static final String TAG = "MYSQL";

    public Connection conn;
    private static Statement stmt;

    private static boolean isConnected;


    public MySql(Context context) {

        ConfigDatabase configDatabase = new ConfigDatabase(context);

        host = configDatabase.getHost();
        user = configDatabase.getUserName();
        password = configDatabase.getPassword();
        port = configDatabase.getPort();
        db = configDatabase.getDbName();

        connectToDB();
    }

    public boolean connected() {
        return isConnected;
    }

    private void connectToDB() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, e.getMessage());
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + String.valueOf(port) + "/" + db, user, password);
            isConnected = true;
        } catch(SQLException e) {
            e.printStackTrace();
            isConnected = false;
        }
    }

    public ResultSet executeSQL(String query) {
        ResultSet rs = null;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rs == null) {
            Log.d("Auth", "Empty");
        }
        return rs;
    }

    public void finalize() {
        try {
            conn.close();
        } catch (SQLException e) {

        }
    }
}
