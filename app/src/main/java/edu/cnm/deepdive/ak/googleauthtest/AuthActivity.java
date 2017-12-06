package edu.cnm.deepdive.ak.googleauthtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class AuthActivity extends AppCompatActivity {

  private static final int RC_SIGN_IN = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_auth);
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.server_client_id))
        .requestEmail()
        .build();
    // Build a GoogleSignInClient with the options specified by gso.
    final GoogleSignInClient client = GoogleSignIn.getClient(this, gso);

    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if (account != null){

      openMainActivity(account.getIdToken());
    } else {
      SignInButton signInButton = (SignInButton) findViewById(R.id.login);
      signInButton.setVisibility(View.VISIBLE);
      signInButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent signInIntent = client.getSignInIntent();
          startActivityForResult(signInIntent, RC_SIGN_IN);
        }
      });
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);
        openMainActivity(account.getIdToken());
      } catch (ApiException e) {
        // The ApiException status code indicates the detailed failure reason.
        // Please refer to the GoogleSignInStatusCodes class reference for more information.
        Log.d("Auth Test", "signInResult:failed code=" + e.getStatusCode());
//        updateUI(null);
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
      }
    }

  }

  private void openMainActivity(String idToken){
    //TODO- open Main Activity
    Log.d("Auth Activity", idToken);

  }


}
