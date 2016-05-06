package com.shopons.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shopons.R;
import com.shopons.adapter.SearchAdapter;
import com.shopons.domain.Location;
import com.shopons.domain.Store;
import com.shopons.domain.StoreDetails;
import com.shopons.domain.User;
import com.shopons.presenter.LocationPresenter;
import com.shopons.presenter.StorePresenter;
import com.shopons.view.activity.shop_info;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by komal on 29/3/16.
 */
public class NewSearchFragment extends BaseFragment {
    public static final String TAG = "##SearchFragment##";
    private int mPageNo;
    StorePresenter mStorePresenter;
    LocationPresenter mLocationPresenter;
    //public static final int LOCATION_SELECTED = 1;
    public static final int RESTAURANT_SELECTED = 2;

    ArrayList<String> temp_array;

    @Bind(R.id.search)
    EditText mSearch;

    @Bind (R.id.progress)
    ProgressBar mProgress;

    @Bind (R.id.list)
    ListView mList;

    @Bind (R.id.card_view)
    CardView mCardView;

    private View mFooterLoadingView;
    private SearchAdapter mAdapter;

    public NewSearchFragment() {
        // Required empty public constructor
    }

    public static NewSearchFragment GetInstance() {
        return new NewSearchFragment();
    }

    @OnClick(R.id.back)
    void back() {
        final Intent resultIntent = new Intent();
        //resultIntent.putExtra(Constants.LOCATION, "null");
        getActivity().setResult(0, resultIntent);
        getActivity().finish();
    }

    @OnClick(R.id.cancel_search)
    void cancle(){
        mSearch.setText("");
        mCardView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        Log.d("#####NewSearchFragment","Here!!!!");
        temp_array=new ArrayList<String>();
        temp_array.add("Baner");
        temp_array.add("Kothrud");
        temp_array.add("Kolhapur");

        mPageNo = 0;
        mStorePresenter=new StorePresenter();
        mProgress.getIndeterminateDrawable().setColorFilter(getActivity().getResources()
                .getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        mAdapter = new SearchAdapter(getActivity(), mList);
        mFooterLoadingView = new LinearLayout(getActivity());
        ((LinearLayout) mFooterLoadingView).setGravity(Gravity.CENTER);
        final View footerProgressView = new ProgressBar(getActivity());
        ((LinearLayout) mFooterLoadingView).addView(footerProgressView);
        //mFooterLoadingView.setPadding(0, 0, 0, padding);
        mFooterLoadingView.setVisibility(View.INVISIBLE);
        mList.addFooterView(mFooterLoadingView);
       // mList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,temp_array));
        //mAdapter = new SearchAdapter(getActivity(), mList);
        mList.setAdapter(mAdapter);
        mAdapter.setSearchAdapterListener(new SearchAdapter.ISearchAdapterListener() {
            @Override
            public void onRestaurantSelected(StoreDetails store) {
                Log.d(TAG, "store selected" + store.getName());
                final Intent resultIntent = new Intent();
                Intent intent=new Intent(getActivity(),shop_info.class);
                intent.putExtra("store_id",store.getId());
                startActivity(intent);
                // resultIntent.putExtra(Constants.NAME, (Parcelable) stoar);
               // getActivity().setResult(RESTAURANT_SELECTED, resultIntent);
                getActivity().finish();
            }


        });
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive(mSearch))
                    imm.hideSoftInputFromWindow(mSearch.getWindowToken(), 0);
            }

            @Override
            public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                                 final int totalItemCount) {
                if ((firstVisibleItem + visibleItemCount) >= (mAdapter.getCount() + 1)) {
                    if (mSearch.getText().toString().trim().isEmpty()) {
                        return;
                    }
                    getSearchResults(mSearch.getText().toString().trim());
                }
            }
        });
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, s.toString());
                mList.setVisibility(View.INVISIBLE);
                mCardView.setVisibility(View.INVISIBLE);
                mAdapter.clearList();
                mProgress.setVisibility(View.VISIBLE);
                mPageNo = 0;
                getSearchResults(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //getPopularLocations();
        return view;
    }


    private void getSearchResults(final String query) {
        if(mPageNo == -1) {
            mFooterLoadingView.setVisibility(View.GONE);
            return;
        }
        if (mPageNo == 0) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mFooterLoadingView.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "Fetching restaurants");



        mStorePresenter.searchResults(query,mPageNo,new Subscriber<List<StoreDetails>>() {
            @Override
            public void onCompleted() {
                //Log.d()

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<StoreDetails> storeDetailses) {
                Log.d(TAG,"Inside on Next");

                if (!query.equals(mSearch.getText().toString().trim())) {
                    mFooterLoadingView.setVisibility(View.GONE);
                    return;
                }
                mCardView.setVisibility(View.VISIBLE);
                mList.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.INVISIBLE);
                if (storeDetailses==null && mPageNo == 0 ) {
                    mPageNo = -1;

                    Toast.makeText(getActivity(), "No match found",
                            Toast.LENGTH_LONG).show();
                    mList.setVisibility(View.INVISIBLE);
                    mCardView.setVisibility(View.INVISIBLE);
                    mFooterLoadingView.setVisibility(View.GONE);
                    return;
                }
                if (mPageNo == 0) {
                    mAdapter.clearList();
                    mAdapter.setItemArrayList(storeDetailses);
                } else if (mPageNo > 0) {
                    mAdapter.appendList(storeDetailses);
                } else {
                    mFooterLoadingView.setVisibility(View.GONE);
                    return;
                }
                if (storeDetailses.size() < 10) {
                    mPageNo = -1;
                } else {
                    mPageNo += 1;
                }
                mFooterLoadingView.setVisibility(View.GONE);

            }
        });
            }



}
