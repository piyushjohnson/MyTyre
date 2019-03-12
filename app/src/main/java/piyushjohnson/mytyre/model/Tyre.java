package piyushjohnson.mytyre.model;

public class Tyre extends Model {
    public String name;
    public String tagline;
    public String img_uri;

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
