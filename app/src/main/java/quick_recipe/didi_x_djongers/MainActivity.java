package quick_recipe.didi_x_djongers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "Main";
    RecyclerView recyclerView_main;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;
    String userId;
//    ArrayList<User> userArrayList;
    public ArrayList<String> List = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();
    public ArrayList<String> nameUser = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();
        if (mUser != null){
            startActivity(new Intent(this,MainActivityAfterLogin.class));
        } else if(mUser == null){

        }
        db = FirebaseFirestore.getInstance();

        CollectionReference docRef = db.collection("recipes");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        List.add(document.getString("PhotoUrl"));
                        NamaMakanan.add(document.getString("Nama_resep"));
                        BahanBahan.add(document.getString("Bahan_bahan"));
                        Langkah_pembuatan.add(document.getString("Langkah_pembuatan"));
                        videoUrl.add(document.getString("videoUrl"));
                        idUser.add(document.getString("IdUser"));
                        idMakanan.add(document.getString("IdMakanan"));
                    }
                    recyclerView_main = findViewById(R.id.recycler_main);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                    recyclerView_main.setLayoutManager(staggeredGridLayoutManager);
                    MainAdapter mainAdapter = new MainAdapter(MainActivity.this, List, NamaMakanan, BahanBahan, Langkah_pembuatan, videoUrl,idUser,idMakanan, nameUser);

                    recyclerView_main.setAdapter(mainAdapter);
                } else {
                    Log.d("DATA", "Error getting documents: ", task.getException());
                }
            }
        });

//        Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
//        Tool bar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        nav
        Menu menu = navigationView.getMenu();
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_category:
                Intent category = new Intent(this, Category.class);
                startActivity(category);
                break;
            case R.id.nav_upload:
                Intent upload = new Intent(this,UploadRecipe.class);
                startActivity(upload);
                break;
            case R.id.nav_login:
                Intent login = new Intent(this, Login.class);
                startActivity(login);
                break;
            case R.id.nav_profile:
                Intent profile = new Intent(this, Profile.class);
                startActivity(profile);
                break;
            case R.id.nav_explore:
                Intent explore = new Intent(this, Explore.class);
                startActivity(explore);
                break;
//            case R.id.logout:
//                mAuth.signOut();
//                Intent logout = new Intent(this, Login.class);
//                startActivity(logout);
//                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

