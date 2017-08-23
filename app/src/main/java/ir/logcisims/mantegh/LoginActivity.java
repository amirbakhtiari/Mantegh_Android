package ir.logcisims.mantegh;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnSettingDB;
    private EditText editTextPassword, editTextUserName;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if(userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getBaseContext(), "وارد کردن نام کاربری و رمز الزامی است.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(login != null)
                    login.cancel(true);

                login = new Login(userName, password);
                login.execute();
            }
        });

        btnSettingDB = (Button) findViewById(R.id.btnSettingDB);
        btnSettingDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDbSetting = new Intent(LoginActivity.this, ServerSettingActivity.class);
                startActivity(intentDbSetting);
            }
        });

    }

    private class Login extends AsyncTask<Void, Void, Boolean> {
        public boolean logged = false;
        private String userName;
        private String password;

        public Login(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            UserAuth userAuth = new UserAuth(userName, password);
            logged = userAuth.login(getBaseContext());
            return logged;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(logged) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "نام کاربری یا رمز اشتباه است.", Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);
            }
        }

        @Override
        protected void onPreExecute() {
            btnLogin.setEnabled(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(login == null)
            return;
        login.cancel(true);
    }

}
