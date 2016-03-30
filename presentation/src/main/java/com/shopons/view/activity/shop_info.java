
package com.shopons.view.activity;

import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.domain.BrandInfo;
import com.shopons.domain.StoreDetails;
import com.shopons.presenter.StorePresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Win7 on 11-Feb-16.
 */
public class shop_info extends AppCompatActivity{
    Boolean details_added=false;


    private ActionBar actionBar;
    ImageView share_btn;
    String store_id;
    StorePresenter mStorePresenter;
    StoreDetails store;

    @Bind(R.id.store_name)
    TextView store_name;

    @Bind(R.id.store_address)
    TextView store_addr;

    @Bind(R.id.num_of_reviews)
    TextView review_count;

    @Bind(R.id.store_city)
    TextView city;

    @OnClick(R.id.call)
    void callStorePerson()
    {
        Intent intent =
                new Intent(Intent.ACTION_DIAL);    //ACTION_CALL -to initiate call,add to manifest CALL_PHONE
        intent.setData(Uri.parse("tel:"+"+(91)-20-39548358"));
        startActivity(intent);
    }

    @OnClick(R.id.back)
    void closeActivity()
    {
        onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.info);
        ButterKnife.bind(this);

        //addButtonClickListner();
        ImageButton button = (ImageButton) findViewById(R.id.title);
        mStorePresenter=new StorePresenter();


        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);

        Intent intent=getIntent();
        store_id=intent.getStringExtra("store_id");
    }

    @Override
    protected void onResume() {
        super.onResume();

        mStorePresenter.getStoreDetails(store_id, new Subscriber<StoreDetails>() {
            @Override
            public void onCompleted() {

                store_name.setText(store.getName());
                store_addr.setText(store.getAddress());
                if(store.getReviews()==0)
                    review_count.setVisibility(View.INVISIBLE);
                review_count.setText(""+store.getReviews());
                city.setText(store.getCity());

                add_row(store.getBrandInfoList());
                Log.d("###Shop_info","Completed!!!!");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onNext(StoreDetails storeDetails) {
                store=storeDetails;



                Log.d("###Shop_info","Got details");

            }
        });
    }
    public void add_row(List<BrandInfo> list) {
        if(details_added==false) {
            details_added=true;

            TextView t1v, t2v;
            // View v;
            TableLayout stk = (TableLayout) findViewById(R.id.tableLayout2);
            for (int i = 0; i < list.size(); i++) {
                TableRow tbrow = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tbrow.setLayoutParams(lp);
                t1v = new TextView(this);
                t1v.setText(list.get(i).getCategory());
                t1v.setLines(1);
                t1v.setGravity(Gravity.LEFT);
                t1v.setPadding(15, 15, 8, 0);

                t2v = new TextView(this);
                t2v.setText("20");
                t2v.setGravity(Gravity.RIGHT);

                tbrow.addView(t1v);
                stk.addView(tbrow, i);


            }
        }
    }

    public void address_map(View view)
    {
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);
    }

    public void share_method(View view)
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


   /* public void addButtonClickListner() {

        Button call = (Button) findViewById(R.id.call);

        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {

                Intent intent =
                        new Intent(Intent.ACTION_DIAL);    //ACTION_CALL -to initiate call,add to manifest CALL_PHONE
                intent.setData(Uri.parse(store.getContact()));
                startActivity(intent);


            }
        });


    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        LayoutInflater layoutInflater= this.getLayoutInflater();
        //View view = layoutInflater.inflate(R.layout.actionbar_shop, null);
        //share_btn= (ImageView) findViewById(R.id.share);

        //getSupportActionBar().setCustomView(view);
       // getSupportActionBar().setDisplayShowCustomEnabled(true);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //actionToggle.syncState();
    }



}
