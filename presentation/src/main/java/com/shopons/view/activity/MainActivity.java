package com.shopons.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.shopons.R;
import com.shopons.adapter.DrawerListAdapter;
import com.shopons.domain.Location;
import com.shopons.domain.User;
import com.shopons.model.DrawerMenu;
import com.shopons.presenter.LoginPresenter;
import com.shopons.utils.DialogsHelper;
import com.shopons.utils.FontUtils;
import com.shopons.view.fragment.MainFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;

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
        Log.d("###MainActivity","Inside Search on Click");
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(intent);
    }

    ActionBar actionBar;
    boolean mIsLoggedIn;
    private DrawerListAdapter mListAdapter;
    private View mHeaderView;

    Location mLocation;
    Bundle bundle=null;

    final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG="####MainActivity";
    private int mPosition;
    private LoginPresenter mLoginPresenter;
    private User user;


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
                mLocation=new Location(latLng.latitude,latLng.longitude);
                Log.d(TAG, "Place: " + place.getName());
                btn_location.setText(place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.d(TAG, status.getStatusMessage());
                Log.d(TAG,"ERROR:(:(");

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }

    private void init() {
        Log.d(TAG, "getting user info");
        mHeaderView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.navigation_drawer_header, drawer_list, false);
        //if(!mIsLoggedIn)
        initNavigationView(user);

        // {
        mLoginPresenter.getUserInfo(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "getting user info");
            }

            @Override
            public void onError(final Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(final User user) {
                //initToolbar();


               // Log.d(TAG, "User name:" + user.getName());
               // Log.d(TAG, "User Email" + user.getEmail());
                initNavigationView(user);
            }
        });
        // }
    }

    @Override
    public void onBackPressed() {
        DialogsHelper.showInteractiveDialog(this, "OK", "CANCEL", "Close Application?", "Do you want to close application?",
                new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                        finish();
                    }
                }, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                });
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (!mLastTag.equals(MainFragment.TAG)) {
            removeFragment(mLastTag);
            if (mIsLoggedIn) {
                switchFragmentLogin(1);
            } else {
                switchFragmentSkip(0);
            }
            drawerLayout.closeDrawers();
            return;
        }
        finish();
        // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addFragment(R.id.container, MainFragment.getInstance(), MainFragment.TAG);
        mLoginPresenter = new LoginPresenter();
        //toolbar.setTitle(getString(R.string.home));
        mLastMenuItemId = -1;
        mPosition = 0;
        mIsLoggedIn = false;
        mListAdapter = new DrawerListAdapter(drawer_list);
        drawer_list.setAdapter(mListAdapter);
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close
        )

        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                syncState();
//                checkAndOpenToolbarMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
                //hideToolbarMenu();
            }
        };

        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoginPresenter != null) {
            mLoginPresenter.destroy();
        }
        mListAdapter.clearAllItems();
    }

    protected void hideToolbarMenu() {
        toolbar.getMenu().getItem(0).setVisible(false);
    }

    protected void checkAndOpenToolbarMenu() {
        if (mLastTag.equals(MainFragment.TAG))
            toolbar.getMenu().getItem(0).setVisible(true);
    }

    protected void showToolbarMenu() {
        toolbar.getMenu().getItem(mPosition).setVisible(true);
    }

    private void initNavigationView(final User user) {

        drawer_list.setOnItemClickListener(this);
        updateAdapter(user);
        if (user != null) {
            mIsLoggedIn= true;
            mHeaderView=navigationView.getHeaderView(0);
            //mHeaderView = LayoutInflater.from(MainActivity.this)
            //      .inflate(R.layout.navigation_drawer_header, drawer_list, false);
            if (!user.getPhoto_url().isEmpty()) {
                Log.d("##HomeActivity", "Height ");
                //cLog.d()
                if (user.getPhoto_url().contains("facebook") || user.getPhoto_url().contains("goo")) {
                    Picasso.with(this).load(user.getPhoto_url())
                            .placeholder(R.drawable.placeholder)
                            .into((CircleImageView) mHeaderView.findViewById(R.id.profile_image));
                } else {
                    Picasso.with(this).load(user.getPhoto_url() + "?height=150")
                            .placeholder(R.drawable.placeholder)
                            .into((CircleImageView) mHeaderView.findViewById(R.id.profile_image));
                }
            }
            ((TextView) mHeaderView.findViewById(R.id.email))
                    .setText(user.getEmail());
            ((TextView) mHeaderView.findViewById(R.id.email))
                    .setTypeface(FontUtils.getFonts(this.getBaseContext(), "Arcon-Regular.otf"));
            ((TextView) mHeaderView.findViewById(R.id.username))
                    .setText(user.getName());
            ((TextView) mHeaderView.findViewById(R.id.username))
                    .setTypeface(FontUtils.getFonts(this.getBaseContext(), "Arcon-Regular.otf"));
            // drawer_list.addHeaderView(mHeaderView);
            mListAdapter.setSelected(1);

        }
        if(!mIsLoggedIn) {
            mHeaderView = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.navigation_drawer_header, drawer_list, false);

            Picasso.with(this).load(R.drawable.profile_pic)
                    .placeholder(R.drawable.placeholder)
                    .into((CircleImageView) mHeaderView.findViewById(R.id.profile_image));
            ((TextView) mHeaderView.findViewById(R.id.username)).setAllCaps(true);
            ((TextView) mHeaderView.findViewById(R.id.username)).setText("Shopons");
            ((TextView) mHeaderView.findViewById(R.id.username))
                    .setTypeface(FontUtils.getFonts(this.getBaseContext(), "Arcon-Regular.otf"));
            navigationView.addHeaderView(mHeaderView);
            mListAdapter.setSelected(0);
        }
    }

    private void updateAdapter(final User user) {
        final ArrayList<DrawerMenu> list = new ArrayList<>();
        if (user != null) {
            // user logged in
            for (String text : getResources().getStringArray(R.array.home_drawer_loggedin_menu)) {
                try {
                    list.add(new DrawerMenu(text, getIconId(text), getSelectedIconId(text)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }
            list.get(0).setIsSelected(true);
        } else {
            for (String text : getResources().getStringArray(R.array.home_drawer_menu)) {
                try {
                    list.add(new DrawerMenu(text, getIconId(text), getSelectedIconId(text)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
            }
            list.get(0).setIsSelected(true);
        }
        mListAdapter.setItemArrayList(list);
    }

    public int getIconId(final String text) throws Exception {
        switch (text.toLowerCase()) {
            case "home":
                return R.drawable.menu_home;

            case "logout":
                return R.drawable.menu_login;

            case "login":
                return R.drawable.menu_logout;

            case "about us":
                return R.drawable.menu_contact;

            case "contact us":
                return R.drawable.menu_login;

        }
        throw new Exception("No icon found to map! " + text);
    }

    public int getSelectedIconId(final String text) throws Exception {
        switch (text.toLowerCase()) {
            case "home":
                return R.drawable.menu_home_select;

            case "logout":
                return R.drawable.menu_logout_select;

            case "login":
                return R.drawable.menu_login_select;

            case "about us":
                return R.drawable.menu_contact_select;

            case "contact us":
                return R.drawable.menu_login_select;

        }
        throw new Exception("No icon found to map! " + text);
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mPosition == position) {
            drawerLayout.closeDrawers();
            return;
        }
        if (mIsLoggedIn) {
            switchFragmentLogin(position);
        } else {
            switchFragmentSkip(position);
        }
        mListAdapter.notifyDataSetChanged();
    }

    private void switchFragmentSkip(final int position) {
        switch (position) {
            case 0:
                Intent intent=new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                //finish();
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                //showToolbarMenu();
                break;
            case 1:
                Intent intent1 =new Intent(getBaseContext(),CallSocialLoginActivity.class);
                startActivity(intent1);
                //finish();
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                break;
            case 2:
                Intent intent2 =new Intent(getBaseContext(),About.class);
                startActivity(intent2);
                //finish();
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                break;
            case 3:
                Intent intent3 =new Intent(getBaseContext(),contact.class);
                startActivity(intent3);
               // finish();
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                break;
        }
    }

    /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()){
            case R.id.login: {
                Log.d(TAG,"######Login clicked");
                Intent intent = new Intent(getApplicationContext(), CallSocialLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }*/

    private void switchFragmentLogin(final int position) {
        switch (position) {
            case 0:
                //showToolbarMenu();
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
               // finish();
                removeFragment(mLastTag);
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                break;
            case 1:
                //showToolbarMenu();
                Intent intent2 =new Intent(getBaseContext(),About.class);
                startActivity(intent2);
                //finish();
                removeFragment(mLastTag);
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                break;
            case 2:
                //showToolbarMenu();
                Intent intent3 =new Intent(getBaseContext(),contact.class);
                startActivity(intent3);
                //finish();
                removeFragment(mLastTag);
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                break;
            case 3:
                removeFragment(mLastTag);
                mIsLoggedIn=false;
                mLoginPresenter.logoutUser(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(final Throwable e) {
                        Toast.makeText(getBaseContext(), "Error while logging out!",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Intent intent1 =new Intent(getBaseContext(),CallSocialLoginActivity.class);
                        startActivity(intent1);
                        //finish();
                    }
                });
                switchFragmentSkip(0);
                drawerLayout.closeDrawers();
                mPosition = position;
                mListAdapter.setSelected(position);
                break;

        }
    }

}