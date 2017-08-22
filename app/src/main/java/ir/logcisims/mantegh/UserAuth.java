package ir.logcisims.mantegh;

/**
 * Created by amirbakhtiari on 21/08/2017.
 */

public class UserAuth {

    private String mUserName;
    private String mPassword;

    private boolean mIsLogin;

    public UserAuth() {
        mIsLogin = false;
    }

    public UserAuth(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    public void login() {

    }

    public void logout() {

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
