package piyushjohnson.mytyre.util;

import android.text.TextUtils;

public class Filters {
    private String sortBy = null;
    private String vehicleType = null;
    private String[] tyreParameters = null;
    private String tyreName = null;

    public Filters() {

    }

    public static Filters getDefault() {
        Filters filters = new Filters();
        return filters;
    }

    public boolean hasSortBy() {
        return !(TextUtils.isEmpty(sortBy));
    }

    public boolean hasVehicleType() {
        return !(TextUtils.isEmpty(vehicleType));
    }

    public boolean hasTyreParameters() {
        return tyreParameters.length > 0;
    }

    public boolean hasTyreName() {
        return tyreName == null;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String[] getTyreParameters() {
        return tyreParameters;
    }

    public void setTyreParameters(String[] tyreParameters) {
        this.tyreParameters = tyreParameters;
    }

    public String getTyreName() {
        return tyreName;
    }

    public void setTyreName(String tyreName) {
        this.tyreName = tyreName;
    }
}
