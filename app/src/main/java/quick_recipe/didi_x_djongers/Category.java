package quick_recipe.didi_x_djongers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Category extends AppCompatActivity {
    private Button btnBack;
    ImageButton riceBtn;
    ImageButton chickenBtn;
    ImageButton seafoodBtn;
    ImageButton redmeatBtn;
    ImageButton vegetablesBtn;
    ImageButton eggsBtn;
    ImageButton noodlesBtn;
    ImageButton dessertBtn;


    FirebaseFirestore db;
    public ArrayList<String> List = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> iduser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        List.clear();
        NamaMakanan.clear();
        BahanBahan.clear();
        Langkah_pembuatan.clear();
        videoUrl.clear();
        iduser.clear();
        idMakanan.clear();

        btnBack = findViewById(R.id.back);
        riceBtn = (ImageButton) findViewById(R.id.riceBtn);
        chickenBtn = (ImageButton) findViewById(R.id.chickenBtn);
        seafoodBtn = (ImageButton) findViewById(R.id.seafoodBtn);
        redmeatBtn = (ImageButton) findViewById(R.id.redmeatBtn);
        vegetablesBtn = (ImageButton) findViewById(R.id.vegetablesBtn);
        eggsBtn = (ImageButton) findViewById(R.id.eggsBtn);
        noodlesBtn = (ImageButton) findViewById(R.id.noodlesBtn);
        dessertBtn = (ImageButton) findViewById(R.id.dessertBtn);

        db = FirebaseFirestore.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });

        chickenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Chicken").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });

        riceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Rice").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });

        seafoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Seafood").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });

        redmeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Red Meat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });

        vegetablesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Vegetables").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });

        eggsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Eggs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });

        noodlesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Noodles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });


        dessertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference docRef = db.collection("recipes");
                docRef.whereEqualTo("Kategori1","Noodles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(document.getString("PhotoUrl"));
                                NamaMakanan.add(document.getString("Nama_resep"));
                                BahanBahan.add(document.getString("Bahan_bahan"));
                                Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                                videoUrl.add(document.getString("videoUrl"));
                                iduser.add(document.getString("IdUser"));
                                idMakanan.add(document.getString("IdMakanan"));
                            }
                        } else {
                            Log.d("DATA", "Error getting documents: ", task.getException());
                        }
                        Intent intent = new Intent(Category.this, Category_After.class);
                        intent.putStringArrayListExtra("photo",List);
                        intent.putStringArrayListExtra("nama_makanan", NamaMakanan);
                        intent.putStringArrayListExtra("bahan", BahanBahan);
                        intent.putStringArrayListExtra("langkah", Langkah_pembuatan);
                        intent.putStringArrayListExtra("video_url", videoUrl);
                        intent.putStringArrayListExtra("id_user", iduser);
                        intent.putStringArrayListExtra("id_makanan", idMakanan);
                        startActivity(intent);
                    }
                });
            }
        });


    }

    public String home() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return null;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Category.this,MainActivity.class));
    }
}