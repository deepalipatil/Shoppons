package com.shopons.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.domain.Store;
import com.shopons.domain.StoreDetails;
import com.shopons.model.SearchResult;
import com.shopons.utils.Typefaces;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.shopons.R.models.Place;
//import com.shopons.domain.repositories.Store;
//import com.shopons..utils.Typefaces;

/**
 *
 */
public final class SearchAdapter extends BaseListAdapter<StoreDetails> {

    private static final String TAG = "##PlacesAdapter##";
    private ISearchAdapterListener mListener;

    /**
     * Constructor
     *
     * @param
     */

    public interface ISearchAdapterListener {

        void onRestaurantSelected(final StoreDetails stoar);

    }

    /**
     * Constructor
     *
    // * @param context
     //* @param listView
     */
    public SearchAdapter(Context context, AbsListView listView) {
        super(context, listView);
    }


    public void setSearchAdapterListener(final ISearchAdapterListener ISearchAdapterListener) {
        mListener = ISearchAdapterListener;
    }

    //@Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_search_entry,
                    parent, false);
            holder = new Holder(convertView);
           // holder.title.setTypeface(Typefaces.get(Typefaces.Type.PROXIMA_NOVA));
           // holder.type.setTypeface(Typefaces.get(Typefaces.Type.PROXIMA_NOVA_LIGHT));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

      convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

      mListener.onRestaurantSelected(getItem(position));

            }
        });

      if(getItem(position).getLocality()!="")
        holder.title.setText(getItem(position).getName()+" ,"+ getItem(position).getLocality()+" ,"+getItem(position).getCity());
      else
          holder.title.setText(getItem(position).getName());

      holder.type.setText("RESTAURANT");
      return convertView;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    static class Holder {
        @Bind(R.id.result_title)
        TextView title;
       @Bind (R.id.type)
        TextView type;

        //@InjectView(R.id.icontype)
        //ImageView icontype;

        Holder(View view) {
           ButterKnife.bind(this, view);
            //super(itemView);
        }
    }
}
