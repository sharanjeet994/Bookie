package com.example.android.bookie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class BookDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nameTxV,authorTxV,priceTxV;
    RatingBar ratingBar;

    String name;
    String image;
    String author;
    int rating;
    int price;
    int priceExample = 23;

    int id =0;


    Context context;

    ImageButton addToCart;

    ImageView imageView,bookInCartImageView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    public OrdersList ordersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        nameTxV = findViewById(R.id.book_detail_name);
        authorTxV = findViewById(R.id.book_detail_author);
        priceTxV = findViewById(R.id.book_detail_price);
        ratingBar = findViewById(R.id.book_detail_rating);
        imageView = findViewById(R.id.book_detail_image);

        bookInCartImageView = findViewById(R.id.list_image_book);

        Intent i = getIntent();

        name = i.getStringExtra("name");
        price = i.getIntExtra("price",priceExample);
        author = i.getStringExtra("author");
        image = i.getStringExtra("image");
        rating = i.getIntExtra("rating",3);


        nameTxV.setText(name);
        authorTxV.setText(author);
        priceTxV.setText(String.valueOf(price));
        ratingBar.setNumStars(rating);
        Picasso.get().load(image).into(imageView);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_book_detail);
        navigationView = (NavigationView)findViewById(R.id.nav_view_book_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar_book_detail);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        addToCart = (ImageButton) findViewById(R.id.add_to_cart_button);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 final HashMap<String,Object> map = new HashMap<>();
                map.put("BookDetails",new Word(name,author,price,image));

                FirebaseDatabase.getInstance().getReference().child("AddedToCart").child(name).updateChildren(map);

                Toast.makeText(BookDetail.this,"Added To Cart ",Toast.LENGTH_SHORT).show();


            }
        });


    }



    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

            super.onBackPressed();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_catalog:
                startActivity(new Intent(this,CatalogActivity.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(this,ProfileActivity.class));
                break;
            case R.id.nav_orders:
                startActivity(new Intent(this,OrdersList.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}