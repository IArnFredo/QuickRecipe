package quick_recipe.didi_x_djongers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Context context;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    public ArrayList<String> List = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();
    public ArrayList<String> nameUser = new ArrayList<>();
    public StorageReference storageReference;
    FirebaseFirestore db;

    public MainAdapter(Context context, ArrayList<String> List, ArrayList<String> NamaMakanan, ArrayList<String> BahanBahan, ArrayList<String> Langkah_pembuatan, ArrayList<String> videoUrl, ArrayList<String> idUser,ArrayList<String> idMakanan, ArrayList<String> nameUser){
        this.context = context;
        this.List = List;
        this.NamaMakanan = NamaMakanan;
        this.BahanBahan = BahanBahan;
        this.Langkah_pembuatan = Langkah_pembuatan;
        this.videoUrl = videoUrl;
        this.idUser = idUser;
        this.idMakanan = idMakanan;
        this.nameUser = nameUser;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        StorageReference imagesRef = storageReference.child(List.get(position));
        final String[] username = new String[1];

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        CollectionReference userRef = db.collection("users");
        userRef.whereEqualTo("IdUser", idUser.get(position)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        username[0] = document.getString("Nama");
                    }
                } else {
                    Log.d("DATA", "Error getting documents: ", task.getException());
                }
            }
        });

        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                
            }
        });

        holder.listMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("image_url", List.get(position));
                intent.putExtra("nama_makanan", NamaMakanan.get(position));
                intent.putExtra("bahan", BahanBahan.get(position));
                intent.putExtra("langkah", Langkah_pembuatan.get(position));
                intent.putExtra("video_url", videoUrl.get(position));
                intent.putExtra("id_makanan", idMakanan.get(position));
                intent.putExtra("nameUser",username[0]);

                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout listMenu;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listMenu = itemView.findViewById(R.id.listMenu);
            imageView = (ImageView) itemView.findViewById(R.id.main_image);
        }
    }
}
