package ar.reloadersystem.autheticaciongoogle.MVPExample.Vista.LoginView;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import ar.reloadersystem.autheticaciongoogle.MVPExample.Presentador.LoginPresenter.GoogleLoginController;
import ar.reloadersystem.autheticaciongoogle.R;

public class VistaLogin extends AppCompatActivity {

    GoogleLoginController googleLoginController;
    SignInButton signInButton;
    GoogleSignInOptions googleSignInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_login);  signInButton = findViewById(R.id.btn_Google);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleLoginController = new GoogleLoginController(getApplicationContext());
                googleLoginController.signOut();
                googleLoginController.initializeApiClient(googleSignInOptions);
                //btn_Google.performClick();
                googleLoginController.makeGoogleLoggeedIn(VistaLogin.this);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GoogleLoginController.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("GOOGLE", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Bundle args = new Bundle();
            args.putString("email", acct.getEmail());
            args.putString("idUser", acct.getId());
            args.putString("givenName", acct.getGivenName());
            args.putString("familyName", acct.getFamilyName());
            args.putString("displayName", acct.getDisplayName());
            googleLoginController.onConnected(args);

        } else {
            Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
