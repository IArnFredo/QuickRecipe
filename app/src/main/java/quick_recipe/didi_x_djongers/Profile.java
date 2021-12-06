package quick_recipe.didi_x_djongers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;

public class Profile extends AppCompatActivity {
    ShapeableImageView profile;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser;
    FirebaseStorage storage;
    StorageReference storageRef = storage.getInstance().getReference();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mUser = mAuth.getCurrentUser();
        if (mUser != null){

        } else if(mUser == null){
            Toast.makeText(this, "You Must Login First!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Login.class));
        }
        String email = mUser.getEmail();
        String name = mUser.getDisplayName();
        userId = mUser.getUid();
        TextView gantiemail = (TextView)findViewById(R.id.user_email);
        gantiemail.setText(email);

        TextView username = (TextView)findViewById(R.id.username);
        username.setText(name);
        profile = (ShapeableImageView) findViewById(R.id.imageview);

        StorageReference imagesRef = storageRef.child("profile/"+userId);

        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Profile.this)
                        .load(uri)
                        .into(profile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });



        Button editaccountbtn = findViewById(R.id.editaccount);

        editaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editaccount();
            }
        });
        Button logoutbtn = findViewById(R.id.logoutbtn);

        Button btnBack = findViewById(R.id.backbtn);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backhome = new Intent(Profile.this,MainActivityAfterLogin.class);
                startActivity(backhome);
            }
        });

        Button favBtn = findViewById(R.id.favbtn);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favorites = new Intent(Profile.this, UserLists.class);
                startActivity(favorites);
            }
        });

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent favorites = new Intent(Profile.this, MainActivity.class);
                Toast.makeText(Profile.this, "Logout Successful!", Toast.LENGTH_SHORT).show();
                startActivity(favorites);
            }
        });

    }
    public void editaccount(){
        Intent profile= new Intent(this, EditProfile.class);
        startActivity(profile);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Profile.this,MainActivityAfterLogin.class));
    }
}