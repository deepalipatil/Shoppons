package com.shopons.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shopons.R;
import com.shopons.model.DrawerMenu;
import com.shopons.utils.FontUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by deepali on 29/3/16.
 */
public class DrawerListAdapter extends BaseListAdapter<DrawerMenu> {
    private static final String TAG = "###DrawerListAdapter";

    /**
     * Constructor
     *
     * @param listView
     */
    public DrawerListAdapter(final AbsListView listView) {
        super(listView);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        CachedItem cachedItem;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_menu_list,
                    parent, false);
            cachedItem = new CachedItem(convertView);
            convertView.setTag(cachedItem);
        } else {
            cachedItem = (CachedItem) convertView.getTag();
        }
        cachedItem.mText.setTypeface(FontUtils.getFonts(parent.getContext(), "Sansation_Light.ttf"));
        cachedItem.mText.setText(getItem(position).getText());
        if(getItem(position).isSelected()) {
            cachedItem.mText.setTextColor(parent.getContext().getResources().getColor(R.color.black));
            cachedItem.mIcon.setImageResource(getItem(position).getSelectedIconId());
        } else {
            cachedItem.mText.setTextColor(parent.getContext().getResources().getColor(R.color.gray));
            cachedItem.mIcon.setImageResource(getItem(position).getIconId());
        }
        return convertView;
    }

    public void setSelected(final int position) {
        if(getAllItems() == null){
            return;
        }
        for(int i=0; i<getAllItems().size(); ++i){
            if(i == position) {
                getAllItems().get(position).setIsSelected(true);
            } else {
                getAllItems().get(i).setIsSelected(false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class CachedItem {
        @Bind(R.id.text)
        TextView mText;

        @Bind(R.id.icon)
        ImageView mIcon;

        public CachedItem(final View view)  {
            ButterKnife.bind(this, view);
        }

    }
}
