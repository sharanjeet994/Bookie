package com.example.android.bookie;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    public BookDetail bookDetail;

    OrdersList ordersList;

    private static final String LOG_TAN = WordAdapter.class.getSimpleName();


    public WordAdapter(Activity context, ArrayList<Word> wordlist) {

        super(context, 0, wordlist);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);


        }



        Word currentWord =getItem(position);

        TextView bookName =(TextView)listItemView.findViewById(R.id.list_item_name);
        bookName.setText(currentWord.getName());
        TextView author = (TextView)listItemView.findViewById(R.id.list_item_author);
        author.setText(currentWord.getAuthor());

        TextView price = (TextView)listItemView.findViewById(R.id.list_item_price);
        price.setText(String.valueOf(currentWord.getPrice()));

        ImageView imageView =(ImageView)listItemView.findViewById(R.id.list_image_book);

        Picasso.get().load(currentWord.getImage()).into(imageView);

        Button remove = (Button)listItemView.findViewById(R.id.list_item_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("AddedToCart").child(currentWord.getName());
                databaseReference.removeValue();

            }


        });


        return listItemView;
    }



}