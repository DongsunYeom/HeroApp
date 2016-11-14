package com.brilliancemobility.heroes;

import java.util.ArrayList;
import java.util.Locale;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.brilliancemobility.heroes.net.TestEnum;
import com.brilliancemobility.heroes.net.Marvel;
import com.brilliancemobility.heroes.net.TestData;
import com.brilliancemobility.heroes.net.TestPhotos;
import com.brilliancemobility.heroes.net.TestResponse;
import com.brilliancemobility.heroes.net.RetrofitFactory;
import com.bumptech.glide.Glide;

/**
 * Created by dongsun on 13/11/16.
 */

public class HeroesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    private GridAdapter mAdapter;
    private View mContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new GridAdapter();
    }


    public void reset(String order, String term) {
        mAdapter.clear();
        startTask(order, term);
    }

    private void startTask(String order, String term) {
        Marvel service = RetrofitFactory.getRetrofit().create(Marvel.class);
        Observable<TestResponse> githubObservable = service.getSearch(order,term);
        githubObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TestResponse>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("", e.getMessage());
                    }

                    @Override
                    public void onNext(TestResponse testResponse) {
                        if (testResponse != null && testResponse.photos != null) {
                            TestData<TestData> data = testResponse.photos;
                            if (data != null) {
                                for (TestPhotos testPhotos : data.photo) {
                                    try {
                                        mAdapter.add(testPhotos);
                                    } catch (Exception e) {
                                        Log.e("callback", "getphoto", e);
                                    }
                                }
                            }
                        }
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_main, null);

        mGridView = (GridView) mContentView.findViewById(R.id.list);
        mGridView.setOnItemClickListener(this);
        mGridView.setAdapter(mAdapter);

        return mContentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getAdapter() == mAdapter) {
            TestPhotos mc = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), ViewImageActivity.class);
            intent.putExtra("Thumnail", mc.getFlickrImgUrl(TestEnum.ImageSize.LARGE_SQUARE));
            intent.putExtra("Title", mc.getTitle());
            getActivity().startActivity(intent);
        }
    }

    class GridAdapter extends ArrayAdapter<TestPhotos> {
        LayoutInflater layoutInflater;
        Resources resources;
        Locale locale;

        public GridAdapter() {
            super(getActivity(), R.layout.list_item, new ArrayList<TestPhotos>());

            layoutInflater = LayoutInflater.from(getActivity());
            resources = getResources();
            locale = Locale.getDefault();
        }

        private class ViewHolder {
            ImageView imageView;
            TextView title;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
                holder.title = (TextView) convertView.findViewById(R.id.title);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                TestPhotos record = getItem(position);
                Glide
                .with(getContext())
                .load(record.getFlickrImgUrl(TestEnum.ImageSize.MEDIUM))
                .centerCrop()
                .crossFade()
                .into(holder.imageView);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }

    }

}
