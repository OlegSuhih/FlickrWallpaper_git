package ua.ho.webdiz.flickr;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FlickrItem.class}, version = 1, exportSchema = false)
public abstract class FlickrDatabase extends RoomDatabase {

    public static final String DB_NAME = "favourite_pics.db";
    private static final Object LOCK = new Object();
    private static FlickrDatabase database;

    public static FlickrDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                database = Room.databaseBuilder(context, FlickrDatabase.class, DB_NAME).build();
            }
        }
        return database;
    }

    public abstract FlickrDao flickrDao();
}
