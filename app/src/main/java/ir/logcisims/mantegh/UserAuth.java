package ir.logcisims.mantegh;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.StreamHandler;

import javax.xml.transform.dom.DOMLocator;

import ir.logcisims.mantegh.Database.ConfigDatabase;
import ir.logcisims.mantegh.Database.MySql;

/**
 * Created by amirbakhtiari on 21/08/2017.
 */

public class UserAuth {

    private String mUserName;
    private String mPassword;

    private boolean mIsLogin;
    private MySql mySql;

    public UserAuth() {
        mIsLogin = false;
    }

    public UserAuth(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    public boolean login(Context context) {
        boolean logged = false;

        mySql = new MySql(context);
        ResultSet rs = mySql.executeSQL(String.format("SELECT COUNT(*) FROM users WHERE sUser='%s' AND sPassword=MD5('%s')", mUserName, mPassword));

        try {
            if(rs.next()) {
                if(rs.getInt(1) > 0)
                    logged = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logged = false;
        }
        return logged;
    }

    public boolean logout() {
        return true;
    }

    public boolean isLoging() {
        return mIsLogin;
    }

    private void setUserName(String userName) {
        mUserName = userName;
    }

    private void setPassword(String password) {
        mPassword = password;
    }
}
