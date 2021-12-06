package quick_recipe.didi_x_djongers;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaSession2Service;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class UploadRecipe extends AppCompatActivity {
    private Button btnBack, foto, video, upRecipe;
    private EditText etName;
    private TextInputLayout etIngredients, etStep;
    private ImageView kotakFoto;
    private VideoView kotakVideo;

    private Spinner category1, category2;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;

//    public Bitmap imageBitmap;
    public Uri videoUri;
    public Uri Imageuri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    public StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);

        btnBack = (Button) findViewById(R.id.backbtn);
        upRecipe = (Button) findViewById(R.id.uploadRecipe);
        etName = findViewById(R.id.nameInput);
        etIngredients = findViewById(R.id.recipeIngredientsInput);
        etStep = findViewById(R.id.stepInput);
        category1 = findViewById(R.id.category1);
        category2 = findViewById(R.id.category2);
        foto = (Button) findViewById(R.id.selectImage);
        video = (Button) findViewById(R.id.selectVideo);
        kotakFoto = (ImageView) findViewById(R.id.imagePreview);
        kotakVideo = (VideoView) findViewById(R.id.videoPreview);

        etName.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        storageReference = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null){

        } else if(mUser == null){
            Toast.makeText(this, "You Must Login First!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,Login.class));
        }
        upRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecipe();
            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                this, R.layout.custom_spinner,
                getResources().getStringArray(R.array.category1)
        );
        adapter1.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        category1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(
                this, R.layout.custom_spinner,
                getResources().getStringArray(R.array.category2)
        );
        adapter2.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        category2.setAdapter(adapter2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home();
            }
        });

        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(kotakVideo);
        kotakVideo.setMediaController(controller);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        });
    }

    private void insertRecipe() {
        String name = etName.getText().toString();
        String ingredients = etIngredients.getEditText().getText().toString();
        String steps = etStep.getEditText().getText().toString();
        String firstCategory = category1.getSelectedItem().toString();
        String secondCategory = category2.getSelectedItem().toString();

        if (name.isEmpty()) {
            etName.setError("Food name cannot be empty!");
        } else if (ingredients.isEmpty()) {
            etIngredients.setError("Ingredients cannot be empty!");
        } else if (steps.isEmpty()) {
            etStep.setError("Step cannot be empty!");
        } else {
            String mUserID = mAuth.getUid();
            String uniqueID = UUID.randomUUID().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            StorageReference fileRef = storageReference.child("makananFoto/" + uniqueID);
            StorageReference videoRef = storageReference.child("makananVideo/" + uniqueID);

            Map<String, Object> resep = new HashMap<>();
            resep.put("IdUser", mUserID);
            resep.put("IdMakanan", uniqueID);
            resep.put("Nama_resep", name);
            resep.put("Bahan_bahan", ingredients);
            resep.put("Langkah_pembuatan", steps);
            resep.put("Kategori1", firstCategory);
            resep.put("Kategori2", secondCategory);
            resep.put("PhotoUrl", "makananFoto/" + uniqueID);
            resep.put("videoUrl", "makananVideo/" + uniqueID);
            resep.put("search", name.toLowerCase());

            db.collection("recipes")
                    .add(resep)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(UploadRecipe.this, "Recipe Uploaded!", Toast.LENGTH_SHORT).show();
                            fileRef.putFile(Imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadRecipe.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                            videoRef.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UploadRecipe.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            Toast.makeText(UploadRecipe.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                            home();
                        }
                    });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Imageuri = data.getData();
            kotakFoto.setImageURI(Imageuri);
        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = data.getData();
            kotakVideo.setVideoURI(videoUri);
            kotakVideo.seekTo(900000);
            kotakVideo.start();
        }else{
            Toast.makeText(UploadRecipe.this, "Cannot Upload Image or Video", Toast.LENGTH_SHORT).show();
        }
    }

    public String home() {
        Intent intent = new Intent(this, MainActivityAfterLogin.class);
        startActivity(intent);
        return null;
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(UploadRecipe.this,MainActivityAfterLogin.class));
    }
}