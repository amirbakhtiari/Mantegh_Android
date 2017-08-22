package ir.logcisims.mantegh.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ir.logcisims.mantegh.ServerSettingActivity;

/**
 * Created by amirbakhtiari on 20/08/2017.
 */

public class MySql {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private String host;
    private String user;
    private String password;
    private int port;
    private String db;

    private static final String TAG = "MYSQL";

    private static Connection conn;
    private static Statement stmt;

    private static boolean isConnected;


    public MySql(String host, String user, String password, int port, String dataBase) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
        this.db = dataBase;
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

            stmt = conn.createStatement();
            String sql;
            sql = "SELECT iID FROM persons";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Log.d(TAG, rs.getString("iID"));
            }

            rs.close();
            stmt.close();
            conn.close();
            isConnected = true;
        } catch(SQLException e) {
            Log.d(TAG, e.getMessage());
            isConnected = false;
        }
    }
}
