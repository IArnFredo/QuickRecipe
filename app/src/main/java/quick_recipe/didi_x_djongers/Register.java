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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private Button btnBack, btnRegister;
    private EditText inputName, inputEmail, inputPassword, inputRetypePassword;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseAuth.AuthStateListener mAuthListener;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    StorageReference storageRef = storage.getInstance().getReference();
    StorageReference imagesRef = storageRef.child("profile/user.png");
    Uri poto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final TextView login = (TextView) findViewById(R.id.iHaveAccount);
        btnBack = findViewById(R.id.back);
        inputName = findViewById(R.id.nameInput);
        inputEmail = findViewById(R.id.emailinput);
        inputPassword = findViewById(R.id.passwordInput);
        inputRetypePassword = findViewById(R.id.passwordRetypeInput);
        btnRegister = findViewById(R.id.signUp);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(view.getContext(), Login.class);
                startActivity(login);
            }

        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });
    }

    public String goToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return null;
    }

    private void PerforAuth() {
        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String passwordRetype = inputRetypePassword.getText().toString();

        if (name.isEmpty()) {
            inputName.setError("Name cannot be empty!");
        } else if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter an email address!");
        } else if (email.isEmpty()) {
            inputEmail.setError("Email cannot be empty!");
        } else if (password.length() < 6) {
            inputPassword.setError("Password cannot be less than 6 character!");
        } else if (password.isEmpty() || password.length() > 16) {
            inputPassword.setError("Password cannot be more than 16 character!");
        } else if (!password.equals(passwordRetype)) {
            inputRetypePassword.setError("Password doesn't match.");
        } else {
            progressDialog.setMessage("Signing Up New Account...");
            progressDialog.setTitle("Sign Up");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mUser = mAuth.getCurrentUser();
                        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                poto = uri;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(Register.this, "Failed to retrieve image.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        String userId = mUser.getUid();
                        Map<String, Object> user = new HashMap<>();
                        user.put("IdUser",userId);
                        user.put("Nama",name);

                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + mUserID);
//                        Toast.makeText(UploadRecipe.this, "Recipe uploaded!", Toast.LENGTH_SHORT).show();

                                    //                    }
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Register.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        mUser.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                        }
                                    }
                                });

                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Please check your email for verification.", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    progressDialog.dismiss();
                                    startActivity(new Intent(Register.this,Register.class));
                                } else {
                                    Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(Register.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                        progressDialog.dismiss();
                    }
                }
            });
        }

//    private void sendUserToNextActivity() {
//      Intent intent = new Intent(Register.this, Login.class);
//      startActivity(intent);
//    }
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(Register.this,Login.class));
    }
}