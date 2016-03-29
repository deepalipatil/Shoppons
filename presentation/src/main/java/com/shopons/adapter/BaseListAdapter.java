package com.shopons.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is base class of activity_application_details_category_all the list adapters which covers activity_application_details_category_all the basic
 * functionality of an adapter
 * Author : Akshay Deo
 * Date   : 11/8/12
 * Time   : 2:28 PM
 */
public abstract class BaseListAdapter<T> extends BaseAdapter implements IListViewAdapter<T> {
    /**
     * Constant to be used while logging
     */
    public static final String TAG = "###BaseListAdapter###";
    /**
     * Buffer one
     */
    private List<T> mItems;
    /**
     * Concurrency lock
     */
    private final Object mLock = new Object();
    /**
     * Context from which it is being called
     */
    protected Context mContext;
    /**
     * Abs list view
     */
    protected AbsListView mAbsListView;
    /**
     * Selection count change event listener
     */
    protected AdapterEventListener mAdapterEventListener;

    public void setAdapterEventListener(final AdapterEventListener adapterEventListener) {
        mAdapterEventListener = adapterEventListener;
    }


    /**
     * Method that renders items view at asked position in convertView
     *
     * @param position    position of the item
     * @param convertView view
     * @param parent      parent view
     * @return
     */
    @Override
    public abstract View getView(final int position, View convertView, ViewGroup parent);

    /**
     * Method to get id of the item at asked position
     *
     * @param position position of the item
     * @return id of the item
     */
    @Override
    public abstract long getItemId(final int position);

    /**
     * Constructor
     *
     * @param context
     * @param listView
     */
    public BaseListAdapter(final Context context, final AbsListView listView) {
        Log.d(TAG, "BaseListAdapter constructor thread name : " + Thread.currentThread().getName());
        mContext = context;
        mAbsListView = listView;
    }


    /**
     * Method to append an existing list
     *
     * @param list
     */
    public void appendList(final List<T> list) {
        if (mItems == null){
            mItems = list;
            return;
        }
        Log.d(TAG, "Append list call");
        synchronized (mLock) {
            if (mAbsListView == null)
                return;
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems.addAll(list);
                    if (mAbsListView == null)
                        return;
                    notifyDataSetChanged();
                    list.clear();
                }
            });
        }
    }


    public void appendListAtFront(final List<T> list) {
        Log.d(TAG, "Append list at front");
        synchronized (mLock) {
            if (mAbsListView == null)
                return;
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems.addAll(0, list);
                    if (mAbsListView == null)
                        return;
                    notifyDataSetChanged();
                }
            });

        }
    }

    /**
     * Add an item to the list adapter
     *
     * @param item item to addItem
     */
    public void addItem(final T item) {
        if (mItems == null)
            mItems = new ArrayList<T>();
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems.add(item);
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Method to addItem an item to given position
     *
     * @param position position at which item needs to be added
     * @param item     item to addItem
     */
    public void addItem(final int position, final T item) {
        if (mItems == null)
            mItems = new ArrayList<T>();
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    if (position > mItems.size())
                        return;
                    mItems.add(position, item);
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Method to addItem an item to front
     *
     * @param item item to addItem
     */
    public void addItemToFront(final T item) {
        addItem(0, item);
    }

    /**
     * Method to update item at a position
     *
     * @param position position of the item
     * @param item     updated item
     */
    public void updateItemAt(final int position, final T item) {
        if (position < 0)
            return;
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems.set(position, item);
                    notifyDataSetChanged();
                }
            });
        }

    }

    /**
     * Update an item with given id
     *
     * @param id   id of the item
     * @param item updated item
     */
    public void updateItem(final long id, final T item) {
        updateItemAt(getPositionOf(id), item);
    }

    /**
     * Method to addItem an item and then sort list
     *
     * @param item       item to be added
     * @param comparator comparator with which we need to addItem filter
     */
    public void addItemAndSort(final T item, final Comparator<T> comparator) {
        addItem(item);
        sort(comparator);
    }

    /**
     * Update an item with id and sort the list
     *
     * @param id         id of the item
     * @param item       updated item
     * @param comparator comparator to sort
     */
    public void updateItemAndSortList(final long id, final T item, final Comparator<T> comparator) {
        updateItemAt(getPositionOf(id), item);
        sort(comparator);
    }

    /**
     * Method to sort adapter items
     *
     * @param comparator comparator to be used for sorting
     */
    public void sort(final Comparator<T> comparator) {
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    Collections.sort(mItems, comparator);
                    notifyDataSetChanged();
                }
            });
        }
    }


    /**
     * Method to clear adapter
     */
    public void clearList() {
        if (mItems == null)
            return;
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems.clear();
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Assign list of items to the list adapter
     *
     * @param items items array
     */
    public void setItemArrayList(final List<T> items) {
        if (mAbsListView == null)
            return;
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    if(mItems == null) {
                        mItems = items;
                    } else {
                        mItems.clear();
                        mItems.addAll(items);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Method to get list of items being shown by list adapter
     *
     * @return count of items
     */
    @Override
    public int getCount() {

        return mItems == null ? 0 : mItems.size();
    }

    /**
     * Method to get item at a position
     *
     * @param position position of the item
     * @return
     */
    @Override
    public T getItem(final int position) {
        if(mItems == null)
            return null;
        if (position > mItems.size() - 1)
            return null;
        return mItems.get(position);
    }

    /**
     * Method to check if given item is present in the list or not
     *
     * @param item item to check
     * @return
     */
    public boolean contains(final T item) {
        Log.d(TAG, "BaseListAdapter contains thread name : " + Thread.currentThread().getName());

        return mItems.contains(item);

    }

    /**
     * Method to remove an item from list
     *
     * @param item item to be removed
     */
    public void remove(final T item) {
        if (mItems == null)
            return;
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems.remove(item);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public void removeAll() {
        if (mItems == null)
            return;
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems = new ArrayList<T>();
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * Method to check if a list contains an id
     *
     * @param id id to be checked
     * @return
     */
    public boolean containsId(final long id) {
        Log.d(TAG, "BaseListAdapter containsId thread name : " + Thread.currentThread().getName());

        for (int position = 0; position < mItems.size(); ++position)
            if (id == getItemId(position))
                return true;

        return false;

    }

    /**
     * Method to get position of give @item
     *
     * @param item item who's position is to be determined
     * @return position of the give @item
     */
    public int getPositionOf(final T item) {
        Log.d(TAG, "BaseListAdapter getPositionOf thread name : " + Thread.currentThread().getName());

        return mItems.indexOf(item);

    }

    /**
     * Remove item at given position
     *
     * @param position position of the item
     */
    public void remove(final int position) {
        synchronized (mLock) {
            mAbsListView.post(new Runnable() {
                @Override
                public void run() {
                    mItems.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

    }


    /**
     * Method to get position of am item with given @id
     *
     * @param id given id
     * @return position of the item with given @id
     */

    public int getPositionOf(final long id) {
        Log.d(TAG, "BaseListAdapter getPositionOf thread name : " + Thread.currentThread().getName());
        Log.d(TAG, "Finding position of id " + id);
        T item = getItemWithId(id);
        if (item == null)
            return -1;
        return getPositionOf(item);
    }


    public T getItemWithId(final long id) {
        Log.d(TAG, "BaseListAdapter getItemWithId thread name : " + Thread.currentThread().getName());
        if (mItems == null)
            return null;
        for (T item : mItems) {
            if (id == getItemId(mItems.indexOf(item))) {
                Log.d(TAG, "item of id " + id + item);
                return item;
            }
        }
        return null;
    }

    public void runOnUiThread(final Runnable runnable) {
        Log.d(TAG, "BaseListAdapter runOnUiThread thread name : " + Thread.currentThread().getName());
        if (mAbsListView == null)
            return;
        mAbsListView.post(runnable);
    }


    public List<T> getAllItems() {
        return mItems;
    }

    /**
     * Not necessary
     *
     * @return
     */
    public int getWidthOfElements() {
        return mAbsListView.getWidth();
    }
}