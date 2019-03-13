package piyushjohnson.mytyre.model;

public class Param {

    String text;
    String value;

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
}
