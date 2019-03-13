package piyushjohnson.mytyre.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class TyreParams extends Model {

    List<Param> model;
    List<Param> size;

    public TyreParams() {
    }

    public TyreParams(List<Param> model, List<Param> size) {
        this.model = model;
        this.size = size;
    }

    public List<Param> getModel() {
        return model;
    }

    public void setModel(List<Param> model) {
        this.model = model;
    }

    public List<Param> getSize() {
        return size;
    }

    public void setSize(List<Param> size) {
        this.size = size;
    }
}
