package ir.logcisims.mantegh;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import ir.logcisims.mantegh.Database.DBOpenHelper;
import ir.logcisims.mantegh.Database.MySql;

public class ServerSettingActivity extends AppCompatActivity {

    private Button btnSave, btnTestConnection;
    private EditText editTextHostname, editTextUser, editTextPassword, editTextDbName, editTextPort;

    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;
    private CheckConnectToDb checkConnectToDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_setting);

        init();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hostName = editTextHostname.getText().toString();
                String user = editTextUser.getText().toString();
                String password = editTextPassword.getText().toString();
                String dbName = editTextDbName.getText().toString();
                String port = editTextPort.getText().toString();

                if(hostName.isEmpty()) {
                    Toast.makeText(ServerSettingActivity.this, "لطفا نام هاست یا آی پی خود را وارد نمایید.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(user.isEmpty()) {
                    Toast.makeText(ServerSettingActivity.this, "لطفا نام کاربری بانک اطلاعاتی خود را وارد نمایید.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.isEmpty()) {
                    Toast.makeText(ServerSettingActivity.this, "لطفا رمز بانک اطلاعاتی خود را وارد نمایید.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(port.isEmpty()) {
                    Toast.makeText(ServerSettingActivity.this, "لطفا پورت بانک اطلاعاتی خود را وارد نمایید.", Toast.LENGTH_SHORT).show();
                    return;
                }


                dbOpenHelper = new DBOpenHelper(ServerSettingActivity.this);

                db = dbOpenHelper.getWritableDatabase();
                dbOpenHelper.deleteData(db);

                ContentValues values = new ContentValues();

                values.put(dbOpenHelper.COLUMN_HOST, hostName);
                values.put(dbOpenHelper.COLUMN_USER, user);
                values.put(dbOpenHelper.COLUMN_PASSWORD, password);
                values.put(dbOpenHelper.COLUMN_DBNAME, dbName);
                values.put(dbOpenHelper.COLUMN_PORT, port);
                long rowId = db.insert(dbOpenHelper.TABLE_SETTING, null, values);

                if(rowId > 0)
                    Toast.makeText(ServerSettingActivity.this, "اطلاعات بانک اطلاعاتی شما با موفقیت ذخیره شد.", Toast.LENGTH_SHORT).show();
            }
        });

        btnTestConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnectToDb = new CheckConnectToDb();
                checkConnectToDb.execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataSetting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkConnectToDb.cancel(true);
    }

    private void init() {
        btnSave = (Button) findViewById(R.id.btnSave);
        btnTestConnection = (Button) findViewById(R.id.btnTestConnection);

        editTextHostname = (EditText) findViewById(R.id.editTextHostName);
        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextDbName = (EditText) findViewById(R.id.editTextDbName);
        editTextPort = (EditText) findViewById(R.id.editTextPort);
    }

    private void getDataSetting() {
        dbOpenHelper = new DBOpenHelper(ServerSettingActivity.this);
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_SETTING, null);
        cursor.moveToFirst();

        editTextHostname.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_HOST)));
        editTextUser.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_USER)));
        editTextPassword.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PASSWORD)));
        editTextDbName.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DBNAME)));
        editTextPort.setText(cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PORT)));
    }

    private class CheckConnectToDb extends AsyncTask<Void, Void, Boolean> {
        private boolean isConnected = false;

        private String host;
        private String user;
        private String password;
        private int port;
        private String dBName;

        public CheckConnectToDb() {
            dbOpenHelper = new DBOpenHelper(ServerSettingActivity.this);
            SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DBOpenHelper.TABLE_SETTING, null);
            cursor.moveToFirst();
            host = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_HOST));
            user = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_USER));
            password = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_PASSWORD));
            port = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.COLUMN_PORT));
            dBName = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_DBNAME));
        }
        @Override
        protected void onPreExecute() {
            Toast.makeText(getBaseContext(), "لطفا صبر کنید.", Toast.LENGTH_LONG).show();
            btnTestConnection.setEnabled(false);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            MySql mySql = new MySql(host, user, password, port, dBName);
            isConnected = mySql.connected();
            return isConnected;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(isConnected)
                Toast.makeText(getBaseContext(), "ارتباط برقرار است.", Toast.LENGTH_LONG).show();
             else
                Toast.makeText(getBaseContext(), "لطفا اطلاعات هاست خود را چک کنید\nامکان اتصال به بانک اطاعاتی وجود ندارد.", Toast.LENGTH_LONG).show();

            btnTestConnection.setEnabled(true);
        }
    }
}
