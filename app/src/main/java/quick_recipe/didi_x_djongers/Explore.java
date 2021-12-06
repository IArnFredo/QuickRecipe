package quick_recipe.didi_x_djongers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Explore extends AppCompatActivity {

    private Button searchBtn;
    private EditText searchBar;
    public RecyclerView recyclerView;

    private FirebaseFirestore db;
    public ArrayList<String> List = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();
    public ArrayList<String> List2 = new ArrayList<>();
    public ArrayList<String> NamaMakanan2 = new ArrayList<>();
    public ArrayList<String> BahanBahan2 = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan2 = new ArrayList<>();
    public ArrayList<String> videoUrl2 = new ArrayList<>();
    public ArrayList<String> idUser2 = new ArrayList<>();
    public ArrayList<String> idMakanan2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        Button btnBack = findViewById(R.id.backbtn);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        searchBar = (EditText) findViewById(R.id.searchBar);
        recyclerView = (RecyclerView) findViewById(R.id.exploreAfter);
        db = FirebaseFirestore.getInstance();

        List.clear();
        NamaMakanan.clear();
        BahanBahan.clear();
        Langkah_pembuatan.clear();
        videoUrl.clear();
        idUser.clear();
        idMakanan.clear();

        List2.clear();
        NamaMakanan2.clear();
        BahanBahan2.clear();
        Langkah_pembuatan2.clear();
        videoUrl2.clear();
        idUser2.clear();
        idMakanan2.clear();

        // Query
        CollectionReference docRef = db.collection("recipes");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        images = new ArrayList(Arrays.asList(document.getString("PhotoUrl"),,document.getString("PhotoUrl"),document.getString("PhotoUrl")));
                        List.add(document.getString("PhotoUrl"));
                        NamaMakanan.add(document.getString("Nama_resep"));
                        BahanBahan.add(document.getString("Bahan_bahan"));
                        Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                        videoUrl.add(document.getString("videoUrl"));
                        idUser.add(document.getString("IdUser"));
                        idMakanan.add(document.getString("IdMakanan"));

                    }
                } else {
                    Log.d("DATA", "Error getting documents: ", task.getException());
                }
                recyclerView = (RecyclerView) findViewById(R.id.exploreAfter);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
                staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                ExploreAdapter mainAdapter = new ExploreAdapter(Explore.this,NamaMakanan,List,BahanBahan,Langkah_pembuatan,videoUrl,idUser,idMakanan);
                recyclerView.setAdapter(mainAdapter);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backhome = new Intent(Explore.this, MainActivity.class);
                startActivity(backhome);
            }
       });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchBar.getText().toString();
                db.collection("recipes");
                CollectionReference docRef = db.collection("recipes");
                if (search.isEmpty()) {
                    searchBar.setError("Food name cannot be empty!");
                }else{
                    docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String data = document.getString("search");
                                    if (data.contains(search.toLowerCase())){
                                        List2.add(document.getString("PhotoUrl"));
                                        NamaMakanan2.add(document.getString("Nama_resep"));
                                        BahanBahan2.add(document.getString("Bahan_bahan"));
                                        Langkah_pembuatan2.add(document.getString("Langkah_pembuatan"));
                                        videoUrl2.add(document.getString("videoUrl"));
                                        idUser2.add(document.getString("IdUser"));
                                        idMakanan2.add(document.getString("IdMakanan"));

                                    }else if (!data.contains(search.toLowerCase())){
//                                    Toast.makeText(Explore.this, "There is no "+search, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                Intent intent = new Intent(Explore.this, Explore_After.class);
                                intent.putStringArrayListExtra("photo",List2);
                                intent.putStringArrayListExtra("nama_makanan", NamaMakanan2);
                                intent.putStringArrayListExtra("bahan", BahanBahan2);
                                intent.putStringArrayListExtra("langkah", Langkah_pembuatan2);
                                intent.putStringArrayListExtra("video_url", videoUrl2);
                                intent.putStringArrayListExtra("id_user", idUser2);
                                intent.putStringArrayListExtra("id_makanan", idMakanan2);

                                startActivity(intent);

                            } else {
                                Log.d("DATA", "Error getting documents: ", task.getException());
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(Explore.this,MainActivity.class));
    }
}