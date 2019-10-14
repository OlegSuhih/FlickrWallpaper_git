package ua.ho.webdiz.flickr;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FlickrViewModel extends AndroidViewModel {

    private static FlickrDatabase database;
    private LiveData<List<FlickrItem>> itemsLiveData;

    public FlickrViewModel(@NonNull Application application) {
        super(application);

        database = FlickrDatabase.getInstance(getApplication());
        itemsLiveData = database.flickrDao().getAllPics();
    }

//    public FlickrDatabase getDatabase() {
//        return database;
//    }

    public LiveData<List<FlickrItem>> getItemsLiveData() {
        return itemsLiveData;
    }


    //Delete All
    public void deleteAllFlickrPics() {
        new DeleteAllFlickrPicsTask().execute();
    }

    public static class DeleteAllFlickrPicsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.flickrDao().deleteAllPics();
            return null;
        }
    }

    //Insert pic
    public void insertFlickrPic(FlickrItem pic) {
        new InsertFlickrPicTask().execute(pic);
    }

    public static class InsertFlickrPicTask extends AsyncTask<FlickrItem, Void, Void> {

        @Override
        protected Void doInBackground(FlickrItem... flickrItems) {
            database.flickrDao().insertPic(flickrItems[0]);
            return null;
        }
    }

    //Delete pic
    public void deleteFlickrPic(FlickrItem pic) {
        new DeleteFlickrPicTask().execute(pic);
    }

    public static class DeleteFlickrPicTask extends AsyncTask<FlickrItem, Void, Void> {
        @Override
        protected Void doInBackground(FlickrItem... flickrItems) {
            database.flickrDao().deletePic(flickrItems[0]);
            return null;
        }
    }

    //Get pic by id
    public FlickrItem getPicById(String id) {
        try {
            return new GetFlickrPicByIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class GetFlickrPicByIdTask extends AsyncTask<String, Void, FlickrItem> {
        @Override
        protected FlickrItem doInBackground(String... strings) {
            if (strings != null) {
                return database.flickrDao().getPicById(strings[0]);
            }
            return null;
        }
    }

    //Find pic by id
    public FlickrItem findById(String id) {
        try {
            return new FindFlickrPicByIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static class FindFlickrPicByIdTask extends AsyncTask<String, Void, FlickrItem> {
        @Override
        protected FlickrItem doInBackground(String... strings) {
            if (strings != null) {
                return database.flickrDao().findById(strings[0]);
            }
            return null;
        }
    }
}
