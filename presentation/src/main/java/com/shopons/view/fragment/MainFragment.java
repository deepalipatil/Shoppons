package com.shopons.view.fragment;


import android.app.Fragment;
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
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.shopons.R;
import com.shopons.adapter.StoreRecyclerAdapter;
import com.shopons.domain.Location;
import com.shopons.domain.Store;
import com.shopons.presenter.LocationPresenter;
import com.shopons.presenter.StorePresenter;
import com.shopons.view.activity.MainActivity;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */


public class MainFragment extends Fragment {

    private static final String TAG="####MainFragment";
    private Button btnLoc;
    private BroadcastReceiver mLocationReceiver;
    private BroadcastReceiver mInternetReceiver;
    private LocationPresenter mLocationPresenter;
    private StorePresenter mStorePresenter;
    private boolean mFirstCalled=false;
    private Location mlocation;
    private List<Store> storeList;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private boolean gotUserLocation=false;
    private Location currLoc,searchLoc;
    private int pageNo=0;
    private StoreRecyclerAdapter adapter;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment getInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        btnLoc=(Button)getActivity().findViewById(R.id.btn_location);
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);

        mLocationReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Inside BroadCast receiver","");
                checkGpsStatus();
            }
        };


        getActivity().registerReceiver(mLocationReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));

        storeList=new ArrayList<Store>();
        currLoc=new Location(-1,-1);
        searchLoc=new Location(-1,-1);
        mlocation=new Location(-1,-1);
        mStorePresenter=new StorePresenter();
        mLocationPresenter=new LocationPresenter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new StoreRecyclerAdapter(recyclerView,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager= (LinearLayoutManager)recyclerView.getLayoutManager();
                int totalItem=linearLayoutManager.getItemCount();
                int lastVisibleItemPos=linearLayoutManager.findLastCompletelyVisibleItemPosition();

                if(adapter.getItemCount()!=0 && lastVisibleItemPos==totalItem-1)
                {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"End of List ",Toast.LENGTH_SHORT).show();
                    pageNo++;
                    getStoreListing(mlocation, pageNo);
                }

                if(progressBar.getVisibility()==View.VISIBLE && lastVisibleItemPos!=totalItem-1)
                    progressBar.setVisibility(View.GONE);
            }
        });


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

        if(!mFirstCalled)
        {
            mFirstCalled=true;
            checkGpsStatus();
        }


        MainActivity mainActivity=(MainActivity)this.getActivity();
        Location searchPara=mainActivity.getSearchLocation();
        if(searchPara!=null){
            searchLoc.setLatitude(searchPara.getLatitude());
            searchLoc.setLongitude(searchPara.getLongitude());
            progressBar.setVisibility(View.VISIBLE);
            pageNo=0;
            adapter.clearList();
            getStoreListing(searchLoc, pageNo);
        }


    }


    void getStoreListing(Location location, final int pageNo) {
        mlocation.setLatitude(location.getLatitude());
        mlocation.setLongitude(location.getLongitude());
        if (mlocation != null && mlocation.getLatitude()!=-1 && mlocation.getLongitude()!=-1) {
            Log.d(TAG,"Inside getStorelisting Looking for ");
            Log.d("###ActivityResult",""+mlocation.getLongitude());
            Log.d("###ActivityResult",""+mlocation.getLatitude());

            mStorePresenter.getStore( mlocation.getLatitude(),mlocation.getLongitude(),pageNo,new Subscriber<List<Store>>(){
                @Override
                public void onCompleted() {

                    if (storeList == null)
                    {
                        Log.d("#####MainFragment", "Store list is empty");
                        recyclerView.setVisibility(View.GONE);
                        return;
                    }
                   progressBar.setVisibility(View.GONE);
                   Log.d("#####MainFragment", "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    if(e instanceof SocketTimeoutException)
                        Toast.makeText(getActivity(),"Time out exception has occured",Toast.LENGTH_SHORT).show();
                    else
                        e.printStackTrace();

                }

                @Override
                public void onNext(List<Store> stores) {
                    if (stores != null) {
                        for (Store element : stores) {
                            Log.d("#####MainFragment", element.getName());
                            Log.d(TAG,""+pageNo);
                        }
                        if(pageNo>0)
                            adapter.appendList(stores);
                        else
                            adapter.setItemArrayList(stores);

                        Log.d("Size of new List", "" + adapter.getItemCount());
                    }
                    else
                    {
                        Log.d(TAG,"EndOfList");
                    }
                }

            });

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
        //getActivity().unregisterReceiver(mLocationReceiver);
        //getActivity().unregisterReceiver(mInternetReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mLocationReceiver);
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

    void getUserCurrentLocation()
    {
        mLocationPresenter.getUserLocation(new Subscriber<Location>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"OnCompleted");
                pageNo=0;
                getStoreListing(currLoc,pageNo);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Crashlytics.logException(e);
            }

            @Override
            public void onNext(Location location) {
                //if(location!=null){

                location.getLongitude();
                currLoc.setLongitude(location.getLongitude());
                currLoc.setLatitude(location.getLatitude());
                adapter.setCurrentUserLocation(location);
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
                }//}
            }
        });
    }
    void checkGpsStatus()
    {
        if(isGpsOn(getActivity())==true)
        {
            getUserCurrentLocation();
        }
        else
        {
            currLoc.setLatitude(-1);
            currLoc.setLongitude(-1);
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
                        materialDialog.dismiss();
                        //mFirstCalled=false;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        mlocation.setLatitude(-1);
                        mlocation.setLongitude(-1);
                        materialDialog.dismiss();
                    }
                });
        materialDialog.show();
    }


}
