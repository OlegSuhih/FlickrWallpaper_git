package ua.ho.webdiz.flickr;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class FlickrFavouriteActivity extends AppCompatActivity {

    private static final String TAG = "FlickrFavouriteActivity";
    private RecyclerView recyclerView;
    private List<FlickrItem> flickrItems;
    private FlickrAdapter adapter;
    private Toolbar toolbar;
    private FlickrViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_favourite);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycller_view_favourite);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new FlickrAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(FlickrViewModel.class);

        LiveData<List<FlickrItem>> favouritesLiveData = viewModel.getItemsLiveData();
        favouritesLiveData.observe(this, new Observer<List<FlickrItem>>() {
            @Override
            public void onChanged(@Nullable List<FlickrItem> favouriteMovies) {
                flickrItems = new ArrayList<>();
                if (favouriteMovies != null) {
                    flickrItems.addAll(favouriteMovies);
                    adapter.setFlickrItemList(flickrItems);
                }
            }
        });

        adapter.setFlickrPicListener(new FlickrAdapter.FlickrPicListener() {
            @Override
            public void onPicClick(int adapterPosition) {
                FlickrItem item = adapter.getFlickrItemList().get(adapterPosition);

                Intent intent = new Intent(FlickrFavouriteActivity.this, FlickrPicViewActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id", item.getId());
                extras.putString("bigUri", item.getUri_z());
                extras.putString("smallUri", item.getUri_s());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_main:
                Intent intent_main = new Intent(this, MainActivity.class);
                startActivity(intent_main);
                finish();
                break;

            case R.id.item_favourite:
                Intent intent_favourite = new Intent(this, FlickrFavouriteActivity.class);
                startActivity(intent_favourite);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
