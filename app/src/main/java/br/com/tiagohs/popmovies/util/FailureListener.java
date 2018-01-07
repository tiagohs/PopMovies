package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuthException;

import br.com.tiagohs.popmovies.R;

/*

    "ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
    ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
    ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
    ("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
    ("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
    ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
    ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
    ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
    ("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
    ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
    ("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
    ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
    ("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
    ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
    ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
    ("ERROR_WEAK_PASSWORD", "The given password is invalid."));

 */

public class FailureListener implements OnFailureListener {

    // Firebase Auth

    public static final String ERROR_INVALID_CUSTOM_TOKEN = "ERROR_INVALID_CUSTOM_TOKEN";
    public static final String ERROR_CUSTOM_TOKEN_MISMATCH = "ERROR_CUSTOM_TOKEN_MISMATCH";
    public static final String ERROR_INVALID_CREDENTIAL = "ERROR_INVALID_CREDENTIAL";
    public static final String ERROR_INVALID_EMAIL = "ERROR_INVALID_EMAIL";
    public static final String ERROR_WRONG_PASSWORD = "ERROR_WRONG_PASSWORD";
    public static final String ERROR_USER_MISMATCH = "ERROR_USER_MISMATCH";
    public static final String ERROR_REQUIRES_RECENT_LOGIN = "ERROR_REQUIRES_RECENT_LOGIN";
    public static final String ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL = "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL";
    public static final String ERROR_EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE";
    public static final String ERROR_CREDENTIAL_ALREADY_IN_USE = "ERROR_CREDENTIAL_ALREADY_IN_USE";
    public static final String ERROR_USER_DISABLED = "ERROR_USER_DISABLED";
    public static final String ERROR_USER_TOKEN_EXPIRED = "ERROR_USER_TOKEN_EXPIRED";
    public static final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
    public static final String ERROR_INVALID_USER_TOKEN = "ERROR_INVALID_USER_TOKEN";
    public static final String ERROR_OPERATION_NOT_ALLOWED = "ERROR_OPERATION_NOT_ALLOWED";
    public static final String ERROR_WEAK_PASSWORD = "ERROR_WEAK_PASSWORD";

    private Context mContext;

    public FailureListener(Context context) {
        mContext = context;
    }

    @Override
    public void onFailure(@NonNull Exception e) {

        String errorCode = ((FirebaseAuthException) e).getErrorCode();

        if (errorCode != null) {
            switch(((FirebaseAuthException) e).getErrorCode()) {
                case ERROR_INVALID_CUSTOM_TOKEN:
                    onShowToast(R.string.invalid_custom_token);
                    break;
                case ERROR_CUSTOM_TOKEN_MISMATCH:
                    onShowToast(R.string.token_mismatch);
                    break;
                case ERROR_INVALID_CREDENTIAL:
                    onShowToast(R.string.invalid_credential);
                    break;
                case ERROR_INVALID_EMAIL:
                    onShowToast(R.string.invalid_email);
                    break;
                case ERROR_WRONG_PASSWORD:
                    onShowToast(R.string.wrong_password);
                    break;
                case ERROR_USER_MISMATCH:
                    onShowToast(R.string.user_mismatch);
                    break;
                case ERROR_REQUIRES_RECENT_LOGIN:
                    onShowToast(R.string.requires_recent_login);
                    break;
                case ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL:
                    onShowToast(R.string.account_exists_diff_credential);
                    break;
                case ERROR_EMAIL_ALREADY_IN_USE:
                    onShowToast(R.string.email_already_in_use);
                    break;
                case ERROR_CREDENTIAL_ALREADY_IN_USE:
                    onShowToast(R.string.credential_already_in_use);
                    break;
                case ERROR_USER_DISABLED:
                    onShowToast(R.string.user_disable);
                    break;
                case ERROR_USER_TOKEN_EXPIRED:
                    onShowToast(R.string.user_token_expired);
                    break;
                case ERROR_USER_NOT_FOUND:
                    onShowToast(R.string.user_not_found);
                    break;
                case ERROR_INVALID_USER_TOKEN:
                    onShowToast(R.string.invalid_user_token);
                    break;
                case ERROR_OPERATION_NOT_ALLOWED:
                    onShowToast(R.string.operation_not_allowed);
                    break;
                case ERROR_WEAK_PASSWORD:
                    onShowToast(R.string.weak_password);
                    break;
                default:
                    onShowToast(R.string.erro_unexpected);
            }
        } else {
            onShowToast(R.string.erro_unexpected);
        }
    }

    private void onShowToast(int msgId) {
        onShowToast(mContext.getString(msgId));
    }

    private void onShowToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}
