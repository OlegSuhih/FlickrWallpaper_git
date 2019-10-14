package ua.ho.webdiz.flickr;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "favourite")
public class FlickrItem {

    @PrimaryKey
    @NonNull
    private String id;
    private String uri_s;
    private String uri_z;

    public FlickrItem(String id, String uri_s, String uri_z) {

        this.id = id;
        this.uri_s = uri_s;
        this.uri_z = uri_z;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri_s() {
        return uri_s;
    }

    public void setUri_s(String uri_s) {
        this.uri_s = uri_s;
    }

    public String getUri_z() {
        return uri_z;
    }

    public void setUri_z(String uri_z) {
        this.uri_z = uri_z;
    }
}
