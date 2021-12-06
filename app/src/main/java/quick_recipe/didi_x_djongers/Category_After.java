package quick_recipe.didi_x_djongers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Category_After extends AppCompatActivity {
    RecyclerView recyclerView_main;
    public ArrayList<String> Photo = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_after);

        Photo = getIntent().getStringArrayListExtra("photo");
        NamaMakanan = getIntent().getStringArrayListExtra("nama_makanan");
        BahanBahan = getIntent().getStringArrayListExtra("bahan");
        Langkah_pembuatan = getIntent().getStringArrayListExtra("langkah");
        videoUrl = getIntent().getStringArrayListExtra("video_url");
        idUser = getIntent().getStringArrayListExtra("id_user");
        idMakanan = getIntent().getStringArrayListExtra("id_makanan");

        recyclerView_main = findViewById(R.id.exploreAfter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView_main.setLayoutManager(staggeredGridLayoutManager);
        CategoryAdapter mainAdapter = new CategoryAdapter(Category_After.this,Photo,NamaMakanan, BahanBahan, Langkah_pembuatan, videoUrl, idUser, idMakanan );

        recyclerView_main.setAdapter(mainAdapter);

        btnBack = (Button) findViewById(R.id.backbtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });
    }
    public String home() {
        Intent intent = new Intent(this, Category.class);
        startActivity(intent);
        return null;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(Category_After.this,Category.class));
    }

}