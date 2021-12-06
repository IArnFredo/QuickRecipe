package quick_recipe.didi_x_djongers;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    public ArrayList<String> Photo = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();
    public ArrayList<String> nameUser = new ArrayList<>();

    public StorageReference storageReference;
    private MainAdapter.ViewHolder holder;
    FirebaseFirestore db;
    public CategoryAdapter(Context context, ArrayList<String> Photo, ArrayList<String> NamaMakanan, ArrayList<String> BahanBahan, ArrayList<String> Langkah_pembuatan, ArrayList<String> videoUrl, ArrayList<String> idUser, ArrayList<String> idMakanan) {
        this.context = context;
        this.Photo = Photo;
        this.NamaMakanan = NamaMakanan ;
        this.BahanBahan = BahanBahan ;
        this.Langkah_pembuatan = Langkah_pembuatan ;
        this.videoUrl = videoUrl;
        this.idUser = idUser;
        this.idMakanan = idMakanan;
        this.nameUser = nameUser;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_layout,parent,false);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();
        holder.text.setText(NamaMakanan.get(position));
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
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageReference.child(Photo.get(position));
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
                Toast.makeText(context, "Failed to retrieve image.", Toast.LENGTH_SHORT).show();
            }
        });

        holder.listCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("id_user", idUser.get(position));
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
        return NamaMakanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout listCategory;
        ImageView imageView;
        TextView text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listCategory = itemView.findViewById(R.id.listcategory);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            text = (TextView) itemView.findViewById(R.id.name);
        }
    }
}
