package quick_recipe.didi_x_djongers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    private Button btnBack, btnLogin;
    private EditText inputName, inputEmail, inputPassword;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView register = (TextView) findViewById(R.id.dontHaveAccount);
        TextView forgot = (TextView) findViewById(R.id.forgetPw);

        btnBack = findViewById(R.id.back);
        inputName = findViewById(R.id.nameInput);
        inputEmail = findViewById(R.id.emailinput);
        inputPassword = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.signIn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgetPass.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent register = new Intent(view.getContext(),Register.class);
                startActivity(register);
            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                performLogin();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null){
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));
        } else if(user == null){

        }
    }

    private void performLogin(){
      String email = inputEmail.getText().toString();
      String password = inputPassword.getText().toString();

      if (!email.matches(emailPattern)) {
        inputEmail.setError("Enter an email address!");
      } else if (email.isEmpty()) {
        inputEmail.setError("Email cannot be empty!");
      } else if (password.length()<6) {
        inputPassword.setError("Password cannot be less than 6 character.");
      } else if (password.isEmpty() || password.length()>16) {
          inputPassword.setError("Password cannot be more than 16 character.");
      } else {
        progressDialog.setMessage("Login Into Your Account...");
        progressDialog.setTitle("Login");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                progressDialog.dismiss();
                user = mAuth.getCurrentUser();
                  if (user.isEmailVerified())
                  {
                      Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(Login.this,MainActivityAfterLogin.class));
                      finish();
                  }
                  else
                  {
                      Toast.makeText(Login.this, "Email Must Be Verified!", Toast.LENGTH_SHORT).show();
                      mAuth.signOut();
                      startActivity(new Intent(Login.this,Login.class));
                  }

              } else {
                progressDialog.dismiss();
                mAuth.signOut();
                Toast.makeText(Login.this, "Your Email or Password is incorrect!", Toast.LENGTH_SHORT).show();
              }
            }

        });
       }
    }

    public String goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return null;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Login.this,MainActivity.class));
    }
}