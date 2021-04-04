package com.example.android.bookie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.bookie.BookDetail;
import com.example.android.bookie.R;
import com.example.android.bookie.Model.TopSellers;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>   {

    private static final String Tag = "RecyclerView for Top Sellers";
    private Context mContext;
    private ArrayList<TopSellers> topSellersArrayList;


    public RecyclerAdapter(Context mContext, ArrayList<TopSellers> topSellersArrayList) {
        this.mContext = mContext;
        this.topSellersArrayList = topSellersArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView author,price,name;
        RatingBar ratingBar;
        CardView cardView;


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
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_layout,parent,false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.author.setText(topSellersArrayList.get(position).getAuthor());
        holder.price.setText(Integer.toString(topSellersArrayList.get(position).getPrice()));
        holder.ratingBar.setNumStars((int) topSellersArrayList.get(position).getRating());
        holder.name.setText(topSellersArrayList.get(position).getName());
        // we use glide library for imageView
        Glide.with(mContext).load(topSellersArrayList.get(position).getImageUrl()).into(holder.bookImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, BookDetail.class);
                intent.putExtra("name",topSellersArrayList.get(position).getName());
                intent.putExtra("image",topSellersArrayList.get(position).getImageUrl());
                intent.putExtra("author",topSellersArrayList.get(position).getAuthor());
                intent.putExtra("price",topSellersArrayList.get(position).getPrice());
                intent.putExtra("rating",topSellersArrayList.get(position).getRating());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);



            }
        });



    }


    @Override
    public int getItemCount() {
        return topSellersArrayList.size();
    }




}
