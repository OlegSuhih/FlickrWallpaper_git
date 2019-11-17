package ua.ho.webdiz.flickr;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private List<FlickrItem> flickrItems = new ArrayList<>();
    private FlickrAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isOnline(getApplication())) {
            Intent intent = new Intent(this, FlickrOffNet.class);
            startActivity(intent);
            finish();
        } else {
            toolbar = findViewById(R.id.toolbar_main);
            setSupportActionBar(toolbar);
            recyclerView = findViewById(R.id.recycller_view);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new FlickrAdapter();
            adapter.setFlickrItemList(FlickrJSON.getListFlickrItems());
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

            adapter.setFlickrPicListener(new FlickrAdapter.FlickrPicListener() {
                @Override
                public void onPicClick(int adapterPosition) {
                    FlickrItem item = adapter.getFlickrItemList().get(adapterPosition);

                    Intent intent = new Intent(MainActivity.this, FlickrPicViewActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("id", item.getId());
                    extras.putString("bigUri", item.getUri_z());
                    extras.putString("smallUri", item.getUri_s());
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.removeItem(viewHolder.getAdapterPosition());

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }



    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
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

    public List<FlickrItem> getFlickrItems() {
        return flickrItems;
    }


}
