package piyushjohnson.mytyre.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Param {

    String text;
    String value;
    boolean header;

    public Param() {
    }

    public Param(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }
}
