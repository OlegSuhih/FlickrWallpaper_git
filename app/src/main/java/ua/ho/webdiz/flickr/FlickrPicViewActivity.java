package ua.ho.webdiz.flickr;

import android.app.WallpaperManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class FlickrPicViewActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageView imageViewStar;
    private String big_uri;
    private String small_uri;
    private String id;
    private FlickrViewModel viewModel;
    private FlickrItem pic;
    private FlickrItem findPic;
    private Button button_wallpaper;
    WallpaperManager myWallpaperManager;
    int height;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_pic_view);

        viewModel = ViewModelProviders.of(this).get(FlickrViewModel.class);

        imageView = findViewById(R.id.imageView_big);
        imageViewStar = findViewById(R.id.imageView);
        button_wallpaper = findViewById(R.id.button_wallpaper);
        myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            big_uri = extras.getString("bigUri");
            small_uri = extras.getString("smallUri");
            id = extras.getString("id");
        } else {
            finish();
        }

        pic = new FlickrItem(id, small_uri, big_uri);
        Picasso.get().load(big_uri).into(imageView);
        findPic = viewModel.findById(id);
        if (findPic != null) {
            imageViewStar.setImageResource(android.R.drawable.btn_star_big_on);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
        width = metrics.widthPixels;
    }

    public void onClickAddToFavourite(View view) {
        findPic = viewModel.findById(id);
        if (findPic == null) {
            viewModel.insertFlickrPic(pic);
            imageViewStar.setImageResource(android.R.drawable.btn_star_big_on);
            Toast.makeText(this, "Добавлено", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.deleteFlickrPic(pic);
            imageViewStar.setImageResource(android.R.drawable.btn_star_big_off);
            Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickBack(View view) {
        finish();
    }

    public void setWallpaper(View view) {
        Picasso.get().load(big_uri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    myWallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }
}
