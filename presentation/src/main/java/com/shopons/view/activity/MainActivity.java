package com.shopons.view.activity;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.shopons.R;
import com.shopons.view.fragment.MainFragment;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_list)
    ListView drawer_list;

    @Bind(R.id.btn_location)
    Button btn_location;

    ActionBar actionBar;
    boolean mIsLoggedIn=false;

    final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    final String TAG="####MainActivity";


    final String[] drawer_login_items={"Home","Favorite","About","Contact Us"};
    final String[] drawer_not_login_items={"Login","About","Contact Us"};


    @OnClick(R.id.btn_location)
    void startLocationIntent()
    {
      try{
        Intent intent =
                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .build(this);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
    } catch (GooglePlayServicesRepairableException e) {
        // TODO: Handle the error.
    } catch (GooglePlayServicesNotAvailableException e) {
        // TODO: Handle the error.
    }

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                btn_location.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MainFragment fragment= new MainFragment();

        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();

        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.Drawer_Opened,R.string.Drawer_Closed)
         {
             @Override
             public void onDrawerClosed(View drawerView) {
                 super.onDrawerClosed(drawerView);
                 actionBarDrawerToggle.syncState();
             }

             @Override
             public void onDrawerOpened(View drawerView) {
                 super.onDrawerOpened(drawerView);


             }
         };


        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBar=getSupportActionBar();

        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        actionBarDrawerToggle.syncState();

        initNavigationHeader();
        initMenu();

    }
    void initNavigationHeader()
    {
        //If user is not logged in
        View header=LayoutInflater.from(this).inflate(R.layout.header,null,false);
        navigationView.addHeaderView(header);
       //Add condition of if user is logged in
    }

    void initMenu()
    {
        if(mIsLoggedIn)
        {
            ArrayAdapter<String> arAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,
                    drawer_login_items);
            drawer_list.setAdapter(arAdapter);
        }
        else
        {
            ArrayAdapter<String> arAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,
                    drawer_not_login_items);
            drawer_list.setAdapter(arAdapter);
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawers();
        }
       else
            super.onBackPressed();

    }
}
