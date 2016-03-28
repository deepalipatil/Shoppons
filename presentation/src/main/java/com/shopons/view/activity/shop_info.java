package in.komal.com.mynavigation;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Win7 on 11-Feb-16.
 */
public class shop_info extends Activity {


    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info);

        addButtonClickListner();
        ImageButton button = (ImageButton) findViewById(R.id.title);



        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);



        // setContentView(R.layout.western);
    }


    public void addButtonClickListner() {

        Button call = (Button) findViewById(R.id.call);

        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {

                Intent intent =
                        new Intent(Intent.ACTION_DIAL);    //ACTION_CALL -to initiate call,add to manifest CALL_PHONE
                intent.setData(Uri.parse("tel:8380857890"));
                startActivity(intent);


            }
        });

        /*ImageView share= (ImageView) findViewById(R.id.share);

        share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));


            }
        });*/


    }

   // @Override
    //public void onClick(View view) {

        /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));*/

    //}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        LayoutInflater layoutInflater= this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.actionbar_shop, null);
        //ImageView share= (ImageView) findViewById(R.id.share);
        //share.setOnClickListener(this);
        //share.setOnClickListener(new View.OnClickListener() {

            //@Override
            //public void onClick(View view) {

                /*Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));*/


            //}
        //});


       /* btn_location=(Button)view.findViewById(R.id.btn_location);
        if(city!="Mumbai")
       {
            btn_location.setText(city);
        }*/

        getActionBar().setCustomView(view);
       getActionBar().setDisplayShowCustomEnabled(true);


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
