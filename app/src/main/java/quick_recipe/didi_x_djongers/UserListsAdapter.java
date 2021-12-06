package quick_recipe.didi_x_djongers;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class UserListsAdapter extends RecyclerView.Adapter<UserListsAdapter.ViewHolder> {
    Context context;
    public ArrayList<String> imageResep = new ArrayList<>();
    public ArrayList<String> NamaMakanan = new ArrayList<>();
    public ArrayList<String> BahanBahan = new ArrayList<>();
    public ArrayList<String> Langkah_pembuatan = new ArrayList<>();
    public ArrayList<String> videoUrl = new ArrayList<>();
    public ArrayList<String> idUser = new ArrayList<>();
    public ArrayList<String> idMakanan = new ArrayList<>();
    public StorageReference storageReference;
    FirebaseFirestore db;

    public UserListsAdapter(Context context, ArrayList<String> imageResep,  ArrayList<String> NamaMakanan,  ArrayList<String> BahanBahan,  ArrayList<String> Langkah_pembuatan,  ArrayList<String> videoUrl,  ArrayList<String> idUser,  ArrayList<String> idMakanan) {
        this.context = context;
        this.imageResep = imageResep;
        this.NamaMakanan = NamaMakanan;
        this.BahanBahan = BahanBahan;
        this.Langkah_pembuatan = Langkah_pembuatan;
        this.videoUrl = videoUrl;
        this.idUser = idUser;
        this.idMakanan = idMakanan;
    }

    @NonNull
    @Override
    public UserListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_userlist,parent,false);
        UserListsAdapter.ViewHolder viewHolder = new UserListsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        holder.listnama.setText(NamaMakanan.get(position));
        StorageReference imagesRef = storageReference.child(imageResep.get(position));
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
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.imagelist);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
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
        CollectionReference recipe = db.collection("recipes");
        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure to Delete ?")
                        .setMessage("Once you delete, the deleted data cannot be restored anymore !!!")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                recipe.whereEqualTo("IdMakanan", idMakanan.get(position)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                document.getReference().delete();
                                                Toast.makeText(context, "Your recipe has been deleted", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(context,UserLists.class);
                                                context.startActivity(intent);
                                            }
                                        } else {
                                            Log.d("DATA", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
//                                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//                                StorageReference photoRef = firebaseStorage.getReferenceFromUrl("makananFoto/"+idMakanan.get(position));
//                                StorageReference videoRef = firebaseStorage.getReferenceFromUrl("makananVideo/"+idMakanan.get(position));
//
//                                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception exception) {
//                                        Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                                videoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception exception) {
//                                        Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
//                                    }
//                                });

                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return NamaMakanan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        TextView listnama;
        ImageView imagelist;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            listnama = (TextView) itemView.findViewById(R.id.listMenu);
            imagelist = (ImageView) itemView.findViewById(R.id.listGambarMenu);
            delete = (ImageButton) itemView.findViewById(R.id.delete);
        }
    }
}
