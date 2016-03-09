package com.shopons.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.domain.Store;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by komal on 2/3/16.
 */
public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreRecyclerAdapter.StoreCardView> {
    Context context;
    List<Store> mlist;
    public StoreRecyclerAdapter(List<Store> list,Context context)
    {
        mlist=new ArrayList<>();
        for (int i = 0 ; i<list.size();i++)
            mlist.add(list.get(i)) ;

        this.context=context;
    }
    @Override
    public StoreCardView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_card, parent, false);
        StoreCardView storeCardView=new StoreCardView(v);
        return storeCardView;
    }

    @Override
    public void onBindViewHolder(final StoreCardView holder, int position) {
        holder.store_name.setText(mlist.get(position).getName());
        holder.store_address.setText(mlist.get(position).getCity());
        holder.ratings.setText(""+mlist.get(position).getRating());
        if(mlist.get(position).getThumbnail()!=null && mlist.get(position).getThumbnail()!="") {
            Picasso.with(context).load(mlist.get(position).getThumbnail()).fit().centerCrop().into(holder.img_background);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Start Sneha's intent
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return mlist.size();
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
        LinearLayout linear_ratings;
        TextView ratings;
        ImageView star;

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
            linear_ratings=(LinearLayout)itemView.findViewById(R.id.linear_ratings);
            ratings=(TextView)itemView.findViewById(R.id.ratings);
            star=(ImageView)itemView.findViewById(R.id.star);
        }
    }
}
