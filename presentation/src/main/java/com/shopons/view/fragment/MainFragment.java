package com.shopons.view.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shopons.R;
import com.shopons.adapter.StoreRecyclerAdapter;
import com.shopons.domain.Location;
import com.shopons.domain.Store;
import com.shopons.presenter.LocationPresenter;
import com.shopons.presenter.StorePresenter;
import com.shopons.view.activity.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */


public class MainFragment extends Fragment {
    private final String TAG="####MainFragment";
    Button btnLoc;
    BroadcastReceiver mLocationReceiver;
    BroadcastReceiver mInternetReceiver;
    LocationPresenter mLocationPresenter;
    StorePresenter mStorePresenter;
    private boolean mFirstCalled=false;
    Location mlocation;
    List<Store> storeList;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    boolean gotUserLocation=false;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        btnLoc=(Button)getActivity().findViewById(R.id.btn_location);
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);

        Log.d(TAG,"Inside on createView");
        mLocationReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkGpsStatus();
            }
        };

        mInternetReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(isConnected())
                {
                    checkGpsStatus();
                }
            }
        };

        mlocation=new Location(-1,-1);
        mStorePresenter=new StorePresenter();
        mLocationPresenter=new LocationPresenter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        storeList=new ArrayList<Store>();

       return view;

    }


      boolean isConnected()
      {
          final ConnectivityManager cm = (ConnectivityManager) getActivity()
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
          final NetworkInfo netInfo = cm.getActiveNetworkInfo();
          return (netInfo != null && netInfo.isConnected());
      }
    @Override
    public void onResume() {
        super.onResume();

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())!=ConnectionResult.SUCCESS)
        {
            Log.d(TAG,"Error connecting to service");
            return;
        }

        getActivity().registerReceiver(mLocationReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
        //getActivity().registerReceiver(mInternetReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        if(!mFirstCalled)
        {

            mFirstCalled=true;
            checkGpsStatus();

        }


        MainActivity mainActivity=(MainActivity)this.getActivity();
        Location searchedLoc=mainActivity.getSearchLocation();
        if(searchedLoc!=null){
            mlocation.setLatitude(searchedLoc.getLatitude());
            mlocation.setLongitude(searchedLoc.getLongitude());
            getStoreListing();
        }


    }


    void getStoreListing() {
        // mStorePresenter.getStore(mlocation.getLongitude(), mlocation.getLatitude(), new Subscriber<List<Store>>(){
        //if location is null then get results for some default location
        if (mlocation != null && mlocation.getLatitude()!=-1 && mlocation.getLongitude()!=-1) {
            Log.d(TAG,"Inside getStorelisting Looking for ");
            Log.d("###ActivityResult",""+mlocation.getLongitude());
            Log.d("###ActivityResult",""+mlocation.getLatitude());
            mStorePresenter.getStore(mlocation.getLongitude(), mlocation.getLatitude(), new Subscriber<List<Store>>(){


                @Override
                public void onCompleted() {
                    Log.d("#####MainFragment", "onCompleted");
                    if (storeList == null)
                    {
                        Log.d("#####MainFragment", "Store list is empty");
                        recyclerView.setVisibility(View.GONE);
                        return;
                    }



                    StoreRecyclerAdapter adapter = new StoreRecyclerAdapter(storeList);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    storeList.clear();

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();

                }

                @Override
                public void onNext(List<Store> stores) {

                    for (Store element : stores) {
                        Log.d("#####MainFragment", element.getName());
                        storeList.add(element);
                    }
                    Log.d("Size of new List",""+storeList.size());
                }
            });
          //  mStorePresenter.getStore(74.01997938752174, 18.32242280497763, new Subscriber<List<Store>>() {


        }
        else
        {
            Log.d(TAG,"Location variable is set to null");
            Log.d(TAG,"Fetch result for default location");
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mLocationReceiver);
        //getActivity().unregisterReceiver(mInternetReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mLocationPresenter!=null)
            mLocationPresenter.destroy();
    }

    boolean isGpsOn(Context context)
    {
        String allowedLocationProviders =
                Settings.System.getString(context.getContentResolver(),
                        Settings.System.LOCATION_PROVIDERS_ALLOWED);
        if (allowedLocationProviders == null) {
            allowedLocationProviders = "";
        }
        return allowedLocationProviders.contains(LocationManager.GPS_PROVIDER);
    }

    void checkGpsStatus()
    {
        if(isGpsOn(getActivity())==true)
        {
            mLocationPresenter.getUserLocation(new Subscriber<Location>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG,"OnCompleted");
                    getStoreListing();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(Location location) {
                    if(location!=null){
                    mlocation.setLongitude(location.getLongitude());
                    mlocation.setLatitude(location.getLatitude());
                    Log.d(TAG,"OnNext");
                    Log.d(TAG,"latitude "+location.getLatitude());
                    Log.d(TAG,"longitude "+location.getLongitude());
                    Geocoder geocoder= new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
                        String city = addresses.get(0).getLocality();
                        btnLoc.setText(city);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}
                }
            });
        }
        else
        {
            mlocation=null;
            showNoGpsDialog();
        }
    }
    void showNoGpsDialog()
    {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(getActivity())
                .title("Location Service Disabled").content("To detect your location  automatically, you need to enable Location services")
                .positiveText("Settings").negativeText("Dismiss")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mFirstCalled=false;
                        materialDialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        materialDialog.dismiss();

                    }
                });
        materialDialog.show();
    }


}
