package com.example.android.bookie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RCAdapter extends RecyclerView.Adapter<RCAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView for Readers Choice";
    private Context mContext;
    private ArrayList<ReadersChoice> readersChoiceArrayList;

    public RCAdapter(Context mContext, ArrayList<ReadersChoice> readersChoiceArrayList) {
        this.mContext = mContext;
        this.readersChoiceArrayList = readersChoiceArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView author,price,name;
        RatingBar ratingBar;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.book_image);
            author = itemView.findViewById(R.id.author_name);
            price = itemView.findViewById(R.id.price);
            ratingBar = itemView.findViewById(R.id.rating_bar);
            name = itemView.findViewById(R.id.book_name);

        }
    }



    @NonNull
    @Override
    public RCAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_layout,parent,false);

      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.author.setText(readersChoiceArrayList.get(position).getAuthor());
        holder.price.setText(Integer.toString(readersChoiceArrayList.get(position).getPrice()));
        holder.ratingBar.setNumStars((int) readersChoiceArrayList.get(position).getRating());
        holder.name.setText(readersChoiceArrayList.get(position).getName());
        // we use glide library for imageView
        Glide.with(mContext).load(readersChoiceArrayList.get(position).getImageUrl()).into(holder.bookImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BookDetail.class);
                intent.putExtra("name",readersChoiceArrayList.get(position).getName());
                intent.putExtra("image",readersChoiceArrayList.get(position).getImageUrl());
                intent.putExtra("author",readersChoiceArrayList.get(position).getAuthor());
                intent.putExtra("price",readersChoiceArrayList.get(position).getPrice());
                intent.putExtra("rating",readersChoiceArrayList.get(position).getRating());

                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return readersChoiceArrayList.size();
    }
}
