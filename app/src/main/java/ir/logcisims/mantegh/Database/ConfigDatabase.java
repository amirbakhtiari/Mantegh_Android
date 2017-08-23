package ir.logcisims.mantegh.Database;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by amirbakhtiari on 23/08/2017.
 */

final public class ConfigDatabase {

    private String host;
    private String userName;
    private String password;
    private int port;
    private String dbName;
    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public ConfigDatabase(Context context) {
        dbOpenHelper = new DBOpenHelper(context);

        Cursor cursor = dbOpenHelper.readData(sqLiteDatabase);

        cursor.moveToFirst();
        host = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_HOST));
        userName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_USER));
        password = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PASSWORD));
        port = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_PORT));
        dbName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DBNAME));
    }


    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }
}
