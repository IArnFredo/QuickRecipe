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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ExploreAfterAdapter extends RecyclerView.Adapter<ExploreAfterAdapter.ViewHolder> {
    Context context;
    public ArrayList<String> Photo = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();
    public StorageReference storageReference;
    FirebaseFirestore db;
    public ExploreAfterAdapter(Context context, ArrayList<String> namaMakanan, ArrayList<String> photo, ArrayList<String> bahanBahan, ArrayList<String> langkah_pembuatan, ArrayList<String> videoUrl, ArrayList<String> idUser, ArrayList<String> idMakanan) {
        this.context = context;
        this.NamaMakanan = namaMakanan;
        this.Photo = photo;
        this.BahanBahan = bahanBahan;
        this.Langkah_pembuatan = langkah_pembuatan;
        this.videoUrl = videoUrl;
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public ExploreAfterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_explore_after,parent,false);
        ExploreAfterAdapter.ViewHolder viewHolder = new ExploreAfterAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAfterAdapter.ViewHolder holder,final int position) {
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        holder.name.setText(NamaMakanan.get(position));
        CollectionReference userRef = db.collection("users");
        final String[] username = new String[1];
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

        StorageReference imagesRef = storageReference.child(Photo.get(position));
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.photo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        holder.explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("id_user", idUser.get(position));
                intent.putExtra("nama_makanan", NamaMakanan.get(position));
                intent.putExtra("bahan", BahanBahan.get(position));
                intent.putExtra("langkah", Langkah_pembuatan.get(position));
                intent.putExtra("video_url", videoUrl.get(position));
                intent.putExtra("nameUser",username[0]);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return NamaMakanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout explore;
        ImageView photo;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            explore = itemView.findViewById(R.id.listexploreafter);
            photo = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
