package ua.ho.webdiz.flickr;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FlickrAdapter extends RecyclerView.Adapter<FlickrAdapter.FlickrHolder> {


    private List<FlickrItem> flickrItemList = new ArrayList<>();

    public void setFlickrItemList(List<FlickrItem> flickrItemList) {
        this.flickrItemList = flickrItemList;
        notifyDataSetChanged();
    }
    public List<FlickrItem> getFlickrItemList() {
        return flickrItemList;
    }

    class FlickrHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;

        public FlickrHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageView_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flickrPicListener != null) {
                        flickrPicListener.onPicClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public FlickrHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.flickr_item, viewGroup, false);
        return new FlickrHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrHolder flickrHolder, int i) {
        FlickrItem item = flickrItemList.get(i);
//        Log.d("MyTag", item.getUri_s());
        Picasso.get().load(item.getUri_s()).into(flickrHolder.imageViewItem);
    }

    @Override
    public int getItemCount() {
//        String s = Integer.toString(flickrItemList.size());
//        Log.d("MyTag", s);
        return flickrItemList.size();
    }

    public interface FlickrPicListener {
        void onPicClick(int adapterPosition);
    }

    private FlickrPicListener flickrPicListener;

    public void setFlickrPicListener(FlickrPicListener flickrPicListener) {
        this.flickrPicListener = flickrPicListener;
    }
}
