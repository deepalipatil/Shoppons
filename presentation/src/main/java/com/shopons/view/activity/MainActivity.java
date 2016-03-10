package com.shopons.view.activity;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;

import com.google.android.gms.maps.model.LatLng;
import com.shopons.R;
import com.shopons.domain.Location;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.shopons.domain.User;

import com.shopons.view.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseScreen  implements AdapterView.OnItemClickListener {

    @Bind(R.id.drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigationView)
    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_list)
    ListView drawer_list;

    @Bind(R.id.btn_location)
    Button btn_location;

    @Bind(R.id.search)
    ImageView search;

    @OnClick(R.id.search)
            void searchById()
    {
    }

    ActionBar actionBar;
    boolean mIsLoggedIn=false;
    View mHeaderView;

    Location mLocation;
    Bundle bundle=null;

    final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG="####MainActivity";
    private int mPosition;
    private LoginPresenter mLoginPresenter;


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
        public Location getSearchLocation()
        {return mLocation;}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latLng=place.getLatLng();
                mLocation=new Location(latLng.longitude,latLng.latitude);

                Log.d("###ActivityResult","longitude "+latLng.longitude);
                Log.d("###ActivityResult","latitude "+latLng.latitude);

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

    Location getSearchedLocation()
    {
        return mLocation;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        User user = new User();
        final BaseScreen baseScreen = new BaseScreen();
        //baseScreen.changeStatusBarColor(R.color.colorPrimary);
        MainFragment fragment= new MainFragment();

       /* if(mLocation!=null)
        {bundle=new Bundle();
        bundle.putDouble("lat",mLocation.getLatitude());
        bundle.putDouble("long",mLocation.getLongitude());
=======
        //addFragment(R.id.container, MainFragment.getInstance(), MainFragment.TAG);
        mLoginPresenter = new LoginPresenter();
        toolbar.setTitle(getString(R.string.home));
        mLastMenuItemId = -1;
        mPosition = 0;
        mIsLoggedIn = user.isLoggedIn();
>>>>>>> 1731b678ee568d551c7b7bd187030d4d4d9104ca

        fragment.setArguments(bundle);}*/
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
        if(mIsLoggedIn)
        {
            View header = LayoutInflater.from(this).inflate(R.layout.navigation_drawer_header, null);
            navigationView.addHeaderView(header);

        }
        else {
            View header1 = LayoutInflater.from(this).inflate(R.layout.navigation_drawer_header1, null);
            navigationView.addHeaderView(header1);
        }


       //Add condition of if user is logged in
    }

    void initMenu()
    {
        if(mIsLoggedIn)
        {

            //ArrayAdapter<String> arAdapter=new ArrayAdapter<String>(MainActivity.this,R.layout.itm_list,drawer_login_items);

            //drawer_list.setAdapter(arAdapter);
           // MenuInflater inflater = getMenuInflater();
            //inflater.inflate(R.menu.navigation_drawer_item1,menu);

            Menu menu = navigationView.getMenu();
            navigationView.inflateMenu(R.menu.navigation_drawer_item1); //inflate new items.



        }
        else
        {
           // ArrayAdapter<String> arAdapter=new ArrayAdapter<String>(MainActivity.this,R.layout.itm_list,
                  //  drawer_not_login_items);
            //drawer_list.setAdapter(arAdapter);
           // MenuInflater inflater = getMenuInflater();
           // inflater.inflate(R.menu.navigation_drawer_item,menu);
            Menu menu = navigationView.getMenu();
            navigationView.inflateMenu(R.menu.navigation_drawer_item); //inflate new items.


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
        switch (item.getItemId()){
            case R.id.login: {
                Log.d(TAG,"######Login clicked");
                Intent intent = new Intent(getApplicationContext(), CallSocialLoginActivity.class);
                startActivity(intent);
            }
        }

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
            moveTaskToBack(true);


    }

    class NavListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String item =  ((TextView) view).getText().toString();
            switch (item)
            {
                case "Login":
                {}
            }
        }
    }


    /* void initNavigationView(final User user) {

        drawer_list.setOnItemClickListener(this);
        updateAdapter(user);
        if (user != null) {
            mIsLoggedIn = true;
            mHeaderView = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item_navigation_headerview, drawer_list, false);
            mHeaderView.findViewById(R.id.profile_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // startActivity(Intents.getShowProfileIntent());
                    //overridePendingTransition(R.anim.in_right, R.anim.out_left);
                    //finish();
                }
            });
            if (!user.getProfilePicUrl().isEmpty()) {
                Log.d("##HomeActivity", "Height " + ImageUtils.DpToPx(this, 72));
                if (user.getProfilePicUrl().contains("facebook") || user.getProfilePicUrl().contains("goo")) {
                    Picasso.with(this).load(user.getProfilePicUrl())
                            .placeholder(R.drawable.image_place_hold)
                            .into((CircleImageView) mHeaderView.findViewById(R.id.profile_image));
                } else {
                    Picasso.with(this).load(user.getProfilePicUrl() + "?height=150")
                            .placeholder(R.drawable.image_place_hold)
                            .into((CircleImageView) mHeaderView.findViewById(R.id.profile_image));
                }
            }
            ((TextView) mHeaderView.findViewById(R.id.user_email))
                    .setText(user.getEmailAddress());
            ((TextView) mHeaderView.findViewById(R.id.user_email))
                    .setTypeface(Typefaces.get(Typefaces.Type.ROBOTO_REGULAR));
            ((TextView) mHeaderView.findViewById(R.id.user_name))
                    .setText(user.getFullName());
            ((TextView) mHeaderView.findViewById(R.id.user_name))
                    .setTypeface(Typefaces.get(Typefaces.Type.ROBOTO_MEDIUM));
            mDrawerList.addHeaderView(mHeaderView);
            mListAdapter.setSelected(1);

        } else {
            mFooterView.setVisibility(View.VISIBLE);
            mListAdapter.setSelected(0);
        }
    }*/

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mPosition == position) {
            drawerLayout.closeDrawers();
            return;
        }
    }
}
