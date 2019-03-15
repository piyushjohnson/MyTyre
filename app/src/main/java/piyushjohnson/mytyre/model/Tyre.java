package piyushjohnson.mytyre.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Tyre extends Model {
    public static String FIELD_VEHICLE_TYPE = "type";
    public static String FIELD_TYRE_SIZE = "size";
    public static String FIELD_TYRE_NAME = "name";
    public static String FIELD_TYRE_TAGLINE = "tagline";
    public static String FIELD_TYRE_FEATURES = "features";
    public static String FIELD_TYRE_IMG_URI = "img_uri";

    private String name;
    private String tagline;
    private String img_uri;

    public Tyre() {
    }

    public Tyre(String name, String tagline, String img_uri) {
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
