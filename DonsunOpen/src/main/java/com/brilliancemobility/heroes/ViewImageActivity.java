package com.brilliancemobility.heroes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.lang.ref.WeakReference;

/**
 * Created by dongsun on 13/11/16.
 */

public class ViewImageActivity extends FragmentActivity {

    private WeakReference<ImageView> preview;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.fragment_view_image);

        mTextView = (TextView)findViewById(R.id.title);

        if (getIntent() != null) {
            String path = getIntent().getStringExtra("Thumnail");
            String title = getIntent().getStringExtra("Title");

            Glide
            .with(this)
            .load(path)
            .centerCrop()
//          .placeholder(android.R.drawable.loading_spinner)
            .crossFade()
            .into(getNetworkImageView());

            mTextView.setText(title);
        }
    }

    public ImageView getNetworkImageView(){
        if (preview==null) {
            preview = new WeakReference<ImageView>((ImageView)findViewById(R.id.preview));
        }
        return preview.get();
    }

}
