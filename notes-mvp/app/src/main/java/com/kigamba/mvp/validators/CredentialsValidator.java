package com.kigamba.mvp.validators;

import com.kigamba.mvp.Utils;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */

public class CredentialsValidator {

    public static final int USERNAME_ERROR = 1;
    public static final int PASSWORD_ERROR = 2;
    public static final int CREDENTIALS_OK = 3;

    public static int isCredentialsOk(String username, String password) {
        if (Utils.isBlank(username)) {
            return USERNAME_ERROR;
        }

        if (Utils.isBlank(password)) {
            return PASSWORD_ERROR;
        }

        return CREDENTIALS_OK;
    }

}
