package quick_recipe.didi_x_djongers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserLists extends AppCompatActivity {
    public ArrayList<String> imageResep = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();
    Button backbtn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lists);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null){

        } else if(mUser == null){
            startActivity(new Intent(this,Login.class));
        }

        Button btnBack = findViewById(R.id.backbtn);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backhome = new Intent(UserLists.this,Profile.class);
                startActivity(backhome);
            }
        });

        String userID = mUser.getUid();
        db = FirebaseFirestore.getInstance();

        CollectionReference docRef = db.collection("recipes");
        docRef.whereEqualTo("IdUser",userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        imageResep.add(document.getString("PhotoUrl"));
                        NamaMakanan.add(document.getString("Nama_resep"));
                        BahanBahan.add(document.getString("Bahan_bahan"));
                        Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                        videoUrl.add(document.getString("videoUrl"));
                        idUser.add(document.getString("IdUser"));
                        idMakanan.add(document.getString("IdMakanan"));
                    }
                    RecyclerView recyclerView = findViewById(R.id.RecyclerList);
                    GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(UserLists.this, LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    UserListsAdapter adapter = new UserListsAdapter(UserLists.this,imageResep,NamaMakanan,BahanBahan,Langkah_pembuatan,videoUrl,idUser,idMakanan);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("DATA", "Error getting documents: ", task.getException());
                }


            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(UserLists.this,Profile.class));
    }
}