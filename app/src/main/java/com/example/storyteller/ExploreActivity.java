package com.example.storyteller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.storyteller.Adapter.MyItemGroupAdapter;
import com.example.storyteller.Interface.IFirebaseLoadListener;
import com.example.storyteller.Model.ItemData;
import com.example.storyteller.Model.ItemGroup;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ExploreActivity extends AppCompatActivity implements IFirebaseLoadListener,NavigationView.OnNavigationItemSelectedListener {

    AlertDialog dialog;
    IFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView my_recycler_view;
    DatabaseReference myData;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        //===========navigation bar=============
        Toolbar toolbar = findViewById(R.id.toolbar);
        //
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();





        //==========

        myData = FirebaseDatabase.getInstance().getReference("MyData");

        dialog = new SpotsDialog.Builder().setContext(this).build();
        iFirebaseLoadListener = this;

        //view
        my_recycler_view = (RecyclerView) findViewById(R.id.recycle_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        //Load Data



        getFireBaseDate();

    }






    private void getFireBaseDate() {

        dialog.show();
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();

                for(DataSnapshot groupSnapShot:dataSnapshot.getChildren()){


                    ItemGroup itemGroup = new ItemGroup();
                    itemGroup.setHeaderTitle(groupSnapShot.child("headerTitle").getValue(true).toString());
////////////////////////////////
                    GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>(){};
/////////////////////////////////

                    itemGroup.setListItem(groupSnapShot.child("listItem").getValue(genericTypeIndicator));

                    itemGroups.add(itemGroup);


                }

                iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                iFirebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        MyItemGroupAdapter adapter = new MyItemGroupAdapter(this,itemGroupList);
        my_recycler_view.setAdapter(adapter);
        dialog.dismiss();

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_home:
                startActivity(new Intent(ExploreActivity.this,ExploreActivity.class));
                break;
            case R.id.nav_about:
                break;
            case R.id.nav_upload:
                startActivity(new Intent(ExploreActivity.this,pdfReaderActivity.class));
                //Toast.makeText(this, "upload", Toast.LENGTH_SHORT).show();
            break;
        }
        return true;
    }
}
