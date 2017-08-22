package ir.logcisims.mantegh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnSettingDB;
    private EditText editTextPassword, editTextUserName;
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
                UserAuth userAuth = new UserAuth(userName, password);
                /*if(!userAuth.login()) {
                    Toast.makeText(getBaseContext(), "نام کاربری یا رمز را اشتباه وارد کرده اید.", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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

}
