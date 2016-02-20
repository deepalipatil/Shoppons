package com.shopons.view.activity;

import android.support.v7.app.ActionBar;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.RelativeLayout;;
import com.shopons.R;

import java.util.ArrayDeque;

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

    ActionBar actionBar;
    boolean mIsLoggedIn=false;

    int height;

    final String[] drawer_login_items={"Home","Favorite","About","Contact Us"};
    final String[] drawer_not_login_items={"Login","About","Contact Us"};


    @OnClick(R.id.btn_location)
    void setLocation()
    {


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
}
