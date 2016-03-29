package com.shopons.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

import java.util.Comparator;
import java.util.List;

public interface IListViewAdapter<T> {

    /**
     * adapter event listener interface
     */
    interface AdapterEventListener {
        void onSelectedCountChangeListener(final int selectedCount);
    }


    View getView(final int position, View convertView, ViewGroup parent);

    long getItemId(final int position);

    void appendList(final List<T> list);

    void appendListAtFront(final List<T> list);

    void addItem(final T item);

    void addItem(final int position, final T item);

    void addItemToFront(final T item);

    void updateItemAt(final int position, final T item);

    void updateItem(final long id, final T item);

    void updateItemAndSortList(final long id, final T item,
                               Comparator<T> comparator);

    void sort(final Comparator<T> comparator);

    void setItemArrayList(final List<T> items);

    void clearList();

    int getCount();

    T getItem(final int position);

    boolean contains(final T item);

    void remove(final T item);

    void remove(final int position);

    boolean containsId(final long id);

    int getPositionOf(final T item);

    int getPositionOf(final long id);

    T getItemWithId(final long id);

    boolean hasStableIds();

    boolean areAllItemsEnabled();

    View getDropDownView(int position, View convertView, ViewGroup parent);

    int getItemViewType(int position);

    int getViewTypeCount();

    boolean isEmpty();

    boolean isEnabled(int position);

    void notifyDataSetChanged();

    void notifyDataSetInvalidated();

    void registerDataSetObserver(DataSetObserver observer);

    void unregisterDataSetObserver(DataSetObserver observer);

    void runOnUiThread(final Runnable runnable);

    void setAdapterEventListener(final AdapterEventListener adapterEventListener);

    int getWidthOfElements();
}