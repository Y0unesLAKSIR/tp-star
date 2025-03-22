package com.example.tp3.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tp3.beans.Star;
import com.example.tp3.R;
import com.example.tp3.service.StarService;
import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> {
    private List<Star> stars;
    private Context context;
    private StarService service;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.stars = stars;
        this.service = StarService.getInstance();
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false);
        return new StarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        final Star star = stars.get(position);
        Glide.with(context).load(star.getImg()).apply(new RequestOptions().override(100, 100)).into(holder.img);
        holder.name.setText(star.getName());
        holder.stars.setRating(star.getStar());
        
        // Set click listener for editing
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(star);
            }
        });
        
        // Set long click listener for deletion
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDeleteConfirmationDialog(star);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() { 
        return stars.size(); 
    }
    
    private void showEditDialog(final Star starToEdit) {
        // Inflate the edit layout
        View editView = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null);
        
        // Get references to the views
        final ImageView imgView = editView.findViewById(R.id.img);
        final RatingBar ratingBar = editView.findViewById(R.id.ratingBar);
        final TextView idView = editView.findViewById(R.id.idss);
        
        // Set the data
        Glide.with(context).load(starToEdit.getImg()).into(imgView);
        ratingBar.setRating(starToEdit.getStar());
        idView.setText(String.valueOf(starToEdit.getId()));
        
        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Modifier " + starToEdit.getName())
               .setView(editView)
               .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       // Update the star's rating
                       float newRating = ratingBar.getRating();
                       starToEdit.setStar(newRating);
                       
                       // Update in service
                       service.update(starToEdit);
                       
                       // Update the adapter
                       notifyDataSetChanged();
                       
                       Toast.makeText(context, "Note mise à jour pour " + starToEdit.getName(), Toast.LENGTH_SHORT).show();
                   }
               })
               .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               })
               .create()
               .show();
    }

    private void showDeleteConfirmationDialog(final Star star) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmation de suppression")
               .setMessage("Voulez-vous vraiment supprimer " + star.getName() + " ?")
               .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       try {
                           service.delete(star);
                           stars.remove(star);
                           notifyDataSetChanged(); 
                           
                           Toast.makeText(context, star.getName() + " a été supprimé(e)", Toast.LENGTH_SHORT).show();
                       } catch (Exception e) {
                           Toast.makeText(context, "Erreur lors de la suppression: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               })
               .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               })
               .show();
    }

    public class StarViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        RatingBar stars;
        
        public StarViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
            stars = itemView.findViewById(R.id.stars);
        }
    }
}
