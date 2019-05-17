package piyushjohnson.mytyre.util;

import android.text.TextUtils;

import java.util.Map;

public class Filters {
    private String sortBy = null;
    private String vehicleType = null;
    private Map<String, String> tyreParameters = null;
    private String tyreName = null;

    public Filters() {

    }

    public static Filters getDefault() {
        Filters filters = new Filters();
        filters.setVehicleType("car");
        return filters;
    }

    public boolean hasSortBy() {
        return !(TextUtils.isEmpty(sortBy));
    }

    public boolean hasVehicleType() {
        return !(TextUtils.isEmpty(vehicleType));
    }

    public boolean hasTyreParameters() {
        if (tyreParameters != null)
            return tyreParameters.size() > 0;
        else
            return false;
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

    public Map<String, String> getTyreParameters() {
        return tyreParameters;
    }

    public void setTyreParameters(Map<String, String> tyreParameters) {
        this.tyreParameters = tyreParameters;
    }

    public String getTyreName() {
        return tyreName;
    }

    public void setTyreName(String tyreName) {
        this.tyreName = tyreName;
    }
}
