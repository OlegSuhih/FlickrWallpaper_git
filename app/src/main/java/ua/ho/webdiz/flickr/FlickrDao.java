package ua.ho.webdiz.flickr;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FlickrDao {
    @Query("SELECT * FROM favourite")
    LiveData<List<FlickrItem>> getAllPics();

    @Query("DELETE FROM favourite")
    void deleteAllPics();

    @Query("SELECT * FROM favourite WHERE id == :id")
    FlickrItem getPicById(String id);

    @Query("SELECT * FROM favourite WHERE id LIKE :id LIMIT 1")
    FlickrItem findById(String id);

    @Insert
    void insertPic(FlickrItem pic);

    @Delete
    void deletePic(FlickrItem pic);



}
