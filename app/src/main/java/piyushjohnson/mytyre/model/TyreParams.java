package piyushjohnson.mytyre.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class TyreParams extends Model {

    List<Param> vehicle;
    List<Param> size;

    public TyreParams() {
    }

    public TyreParams(List<Param> vehicle, List<Param> size) {
        this.vehicle = vehicle;
        this.size = size;
    }

    public List<Param> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<Param> vehicle) {
        this.vehicle = vehicle;
    }

    public List<Param> getSize() {
        return size;
    }

    public void setSize(List<Param> size) {
        this.size = size;
    }
}
