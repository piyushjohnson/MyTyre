package piyushjohnson.mytyre.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CartTyre {

    @PrimaryKey
    @NonNull
    private String name;
    private String tagline;
    private String img_uri;

    public CartTyre(String name, String tagline, String img_uri) {
        this.name = name;
        this.tagline = tagline;
        this.img_uri = img_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getImg_uri() {
        return img_uri;
    }

    public void setImg_uri(String img_uri) {
        this.img_uri = img_uri;
    }
}
