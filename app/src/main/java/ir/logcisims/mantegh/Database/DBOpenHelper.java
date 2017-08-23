package ir.logcisims.mantegh.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amirbakhtiari on 21/08/2017.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "settings.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SETTING = "settings";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_HOST = "HostName";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_DBNAME = "dbName";
    public static final String COLUMN_PORT = "Port";

    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_SETTING + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_HOST + " TEXT, " +
                    COLUMN_USER + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, "	+
                    COLUMN_DBNAME + " TEXT, "	+
                    COLUMN_PORT + " TEXT " +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
        onCreate(db);
    }

    public void deleteData(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + TABLE_SETTING);
    }

    public Cursor readData(SQLiteDatabase db) {
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SETTING, null);
        return cursor;
    }
}
