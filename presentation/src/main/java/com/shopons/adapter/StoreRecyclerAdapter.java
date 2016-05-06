package com.shopons.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.domain.Location;
import com.shopons.domain.Store;
import com.shopons.view.activity.shop_info;
import com.shopons.view.activity.MapsActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by komal on 2/3/16.
 */
public class StoreRecyclerAdapter extends BaseRecyclerViewAdapter<Store,StoreRecyclerAdapter.StoreCardView> {
    Context context;
    Location currentLoc;
    public static double lat;
    public static double lng;

    public StoreRecyclerAdapter(RecyclerView recyclerView,Context context)
    {
       super(recyclerView);
        this.context=context;
        currentLoc=new Location(-1,-1);
    }

    public void setCurrentUserLocation(Location location)
    {
        currentLoc.setLatitude(location.getLatitude());
        currentLoc.setLongitude(location.getLongitude());
    }
    @Override
    public StoreCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_card, parent, false);
        StoreCardView storeCardView=new StoreCardView(v);
        return storeCardView;
    }


    double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    @Override
    public void onBindViewHolder(final StoreCardView holder, final int position) {
        holder.store_name.setText(getItem(position).getName());
        if(getItem(position).getLocality()!="")
            holder.store_address.setText(getItem(position).getLocality()+","+getItem(position).getCity());
        else
            holder.store_address.setText(getItem(position).getCity());
        //Ratings
        if(getItem(position).getRating()==0.0)
        {
            holder.ratings.setVisibility(View.GONE);
        }
        else
        {
            holder.ratings.setText(""+getItem(position).getRating());
        }
        // Background Image
        if(getItem(position).getThumbnail()!=null && getItem(position).getThumbnail()!="") {
            Picasso.with(context).load(getItem(position).getThumbnail()).fit().centerCrop().into(holder.img_background);
        }

        if(getItem(position).getTag_men() )
        {
            Log.d("Adapter", "Inside if condition of men "+getItem(position).getTag_men() );
            holder.tagMen.setVisibility(View.VISIBLE);
        }
        else
            holder.tagMen.setVisibility(View.GONE);

        if(getItem(position).getTag_women())
            holder.tagWomen.setVisibility(View.VISIBLE);
        else
          holder.tagWomen.setVisibility(View.GONE);

        if(getItem(position).getTag_kids())
            holder.tagKids.setVisibility(View.VISIBLE);
        else
            holder.tagKids.setVisibility(View.GONE);

        if(currentLoc.getLatitude()==-1 && currentLoc.getLongitude()==-1)
            holder.linear_place.setVisibility(View.GONE);

       else
        {
            double distance=getDistanceFromLatLonInKm(currentLoc.getLatitude(),currentLoc.getLongitude(),
                    getItem(position).getLocation().getLatitude(),getItem(position).getLocation().getLongitude());
            holder.distance.setText(String.format("%.1f",distance)+"km");
        }

        lat=getItem(position).getLocation().getLatitude();
        lng=getItem(position).getLocation().getLongitude();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context,shop_info.class);
                intent.putExtra("store_id",getItem(position).getId());
                context.startActivity(intent);
            }
        });

        holder.linear_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapsActivity.class);
                context.startActivity(intent);
            }
        });

    }


    class StoreCardView extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView img_background;
        RelativeLayout relative_store_info;
        TextView store_name,store_address;
        LinearLayout linear_place;
        ImageView place;
        TextView distance;
        LinearLayout linear_tags;
        TextView ratings;
        TextView tagMen,tagWomen,tagKids;


        public StoreCardView(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.cadview);
            img_background=(ImageView)itemView.findViewById(R.id.img_background);
            relative_store_info=(RelativeLayout)itemView.findViewById(R.id.relative_store_info);
            store_name=(TextView)itemView.findViewById(R.id.store_name);
            store_address=(TextView)itemView.findViewById(R.id.store_addr);
            linear_place=(LinearLayout)itemView.findViewById(R.id.linear_place);
            place=(ImageView)itemView.findViewById(R.id.btn_place);
            distance=(TextView)itemView.findViewById(R.id.distance);
            linear_tags=(LinearLayout)itemView.findViewById(R.id.linear_tags);
            ratings=(TextView)itemView.findViewById(R.id.ratings);
            tagMen=(TextView)itemView.findViewById(R.id.tag_men);
            tagWomen=(TextView)itemView.findViewById(R.id.tag_women);
            tagKids=(TextView)itemView.findViewById(R.id.tag_kids);
            tagMen.setVisibility(View.GONE);
            tagWomen.setVisibility(View.GONE);
            tagKids.setVisibility(View.GONE);

        }
    }
}
