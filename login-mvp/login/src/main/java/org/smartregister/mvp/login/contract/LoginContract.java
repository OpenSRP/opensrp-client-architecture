package org.smartregister.mvp.login.contract;

/**
 * Created by keyman on 4/30/2018.
 */

public interface LoginContract {
    interface Presenter {
        void attemptLogin(String email, String password);
    }

    interface View {

        void setEmailError(int resourceId);

        void resetEmailError();

        void setPasswordError(int resourceId);

        void resetPaswordError();

        void showProgress(final boolean show);

        void finish();
    }

    interface Model {
        boolean registerAccount(String mEmail, String mPassword);
    }

}
