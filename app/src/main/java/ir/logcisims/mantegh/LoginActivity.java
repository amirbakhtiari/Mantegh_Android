package ir.logcisims.mantegh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin, btnSettingDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MySql mySql = new MySql();
                    }
                }).start();*/
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
