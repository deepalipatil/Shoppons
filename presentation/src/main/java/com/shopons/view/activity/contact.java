package com.shopons.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.view.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by win7 on 5/18/2016.
 */
public class contact extends AppCompatActivity{

    private ActionBar actionBar;

    @Bind(R.id.edittext1)
    EditText editTextname;

    @Bind(R.id.edittext2)
    EditText editTextemail;

    @Bind(R.id.edittext3)
    EditText editTextMessage;

    @Bind(R.id.but)
    Button send;

    @Bind(R.id.info_label)
    TextView info;

    public void callStorePerson(View view)
    {
        Intent intent =
                new Intent(Intent.ACTION_DIAL);    //ACTION_CALL -to initiate call,add to manifest CALL_PHONE
        intent.setData(Uri.parse("tel:"+"+(91)-20-39548358"));
        startActivity(intent);
    }

    public void BackActivity(View view)
    {
        //Intent intent = new Intent(getBaseContext(),MainActivity.class);
        //startActivity(intent);
        //finish();
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        ButterKnife.bind(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        BaseFragment.setRegularFont(editTextMessage);
        BaseFragment.setRegularFont(editTextemail);
        BaseFragment.setRegularFont(editTextname);
        TextView butFont = (TextView)findViewById(R.id.but);
        BaseFragment.setRegularFont(butFont);
        info.setTextColor(getResources().getColor(R.color.white));
        BaseFragment.setTypeFace(info);
        return;

    }
    public void addButtonClickListner(View v) {

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String name = editTextname.getText().toString();

                String sub = editTextemail.getText().toString();

                String message = editTextMessage.getText().toString();


                Intent email = new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_EMAIL, new String[]{name});
                email.putExtra(Intent.EXTRA_TEXT, sub);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("plain/text");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                //startActivity(email);


            }

        });
    }

    public void share_method(View view)
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "extra text that you want to put");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


}
