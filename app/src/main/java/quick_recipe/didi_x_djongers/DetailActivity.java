package quick_recipe.didi_x_djongers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class DetailActivity extends AppCompatActivity {
    private Button btnBack;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ImageView imageView;
    VideoView kotakVideo;
    TextView title, stepview, namauser,bahanview;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        storageReference = storage.getInstance().getReference();
        title = findViewById(R.id.title);
        bahanview = findViewById(R.id.ingredient);
        stepview = findViewById(R.id.step);
        namauser = findViewById(R.id.uploaded);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnBack = findViewById(R.id.back);
        kotakVideo = (VideoView) findViewById(R.id.videoPreview);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        ReadyExtra();
    }

    private void ReadyExtra() {
            db = FirebaseFirestore.getInstance();
            String nameUser = getIntent().getStringExtra("nameUser");
            String nama = getIntent().getStringExtra("nama_makanan");
            String bahan = getIntent().getStringExtra("bahan");
            String langkah = getIntent().getStringExtra("langkah");
            String videoUrl = getIntent().getStringExtra("video_url");
//            StorageReference imagesRef = storageReference.child(foto);
//
//            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri)
//                {
//                    Glide.with(DetailActivity.this)
//                            .load(uri)
//                            .into(imageView);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//
//                }
//            });
            namauser.setText(nameUser);
            title.setText(nama);
            bahanview.setText(bahan);
            stepview.setText(langkah);

            StorageReference videoRef = storageReference.child(videoUrl);

            videoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri)
                {
                    kotakVideo.setVideoURI(uri);
                    MediaController controller = new MediaController(DetailActivity.this);
                    controller.setMediaPlayer(kotakVideo);
                    kotakVideo.setMediaController(controller);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
    }

    public String goToRegister() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return null;
    }
}