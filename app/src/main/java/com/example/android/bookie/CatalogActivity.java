package com.example.android.bookie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bookie.Adapter.RCAdapter;
import com.example.android.bookie.Adapter.RecyclerAdapter;
import com.example.android.bookie.Model.ReadersChoice;
import com.example.android.bookie.Model.TopSellers;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatalogActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SearchView searchView;

    RecyclerView featuredRecycler;
    RecyclerView readersRecycler;

    private RecyclerAdapter recyclerAdapter;
    private RCAdapter readersAdapter;

    private DatabaseReference myRef;

    private ArrayList<TopSellers> topSellersArrayList;
    private ArrayList<ReadersChoice> readersChoiceArrayList;

    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout_catalog);
        navigationView = (NavigationView) findViewById(R.id.nav_view_catalog);
        toolbar = (Toolbar) findViewById(R.id.toolbar_catalog);


        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_catalog);

        featuredRecycler = (RecyclerView)findViewById(R.id.featured_recycler);
        featuredRecycler();

        readersRecycler = (RecyclerView)findViewById(R.id.readers_recycler);
        readersRecycler();

        myRef = FirebaseDatabase.getInstance().getReference();

        topSellersArrayList = new ArrayList<>();
        readersChoiceArrayList = new ArrayList<>();

        clearTopSeller();
        clearReaders();

        getDataFromFirebaseTopSellers();
        getDataFromFirebaseReadersChoice();




    }

    private void getDataFromFirebaseReadersChoice() {

        Query query = myRef.child("readerschoice");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                clearReaders();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ReadersChoice readersChoice = new ReadersChoice();
                    readersChoice.setImageUrl(snapshot1.child("image").getValue().toString());
                    readersChoice.setAuthor(snapshot1.child("author").getValue().toString());
                    readersChoice.setPrice(Integer.parseInt(snapshot1.child("price").getValue().toString()));
                    readersChoice.setRating(Integer.parseInt(snapshot1.child("rating").getValue().toString()));
                    readersChoice.setName(snapshot1.getKey());// getKey() is used to get json object which is the name of the book here

                    readersChoiceArrayList.add(readersChoice);
                }
                readersAdapter = new RCAdapter(getApplicationContext(),readersChoiceArrayList);
                readersRecycler.setAdapter(readersAdapter);
                readersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void clearReaders() {

        if(readersChoiceArrayList != null){
            readersChoiceArrayList.clear();
        }

        if(readersAdapter != null){
            readersAdapter.notifyDataSetChanged();
        }

        else{
            readersChoiceArrayList = new ArrayList<>();
        }
    }

    private void readersRecycler() {

        readersRecycler.setHasFixedSize(true);
        readersRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    private void getDataFromFirebaseTopSellers() {

        Query query = myRef.child("topsellers");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                clearTopSeller();

                for(DataSnapshot snapshot1 : snapshot.getChildren()){

                    TopSellers topSellers = new TopSellers();
                    topSellers.setImageUrl(snapshot1.child("image").getValue().toString());
                    topSellers.setAuthor(snapshot1.child("author").getValue().toString());
                    topSellers.setPrice(Integer.parseInt(snapshot1.child("price").getValue().toString()));
                    topSellers.setRating(Integer.parseInt(snapshot1.child("rating").getValue().toString()));
                    topSellers.setName(snapshot1.getKey()); // getKey() is used to get json object which is the name of the book here


                    topSellersArrayList.add(topSellers);

                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(),topSellersArrayList);
                featuredRecycler.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void clearTopSeller(){  // ensuring list is empty and then adding the data to it

        if(topSellersArrayList != null){
            topSellersArrayList.clear();
        }

        if(recyclerAdapter != null){
            recyclerAdapter.notifyDataSetChanged();
        }

        else{
            topSellersArrayList = new ArrayList<>();
        }



    }



    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

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