package piyushjohnson.mytyre.util;

import android.text.TextUtils;

public class Filters {
    private String sortBy = null;
    private String vehicleType = null;
    private String[] tyreParameters = null;

    public Filters() {

    }

    public static Filters getDefault() {
        Filters filters = new Filters();
        filters.setVehicleType("lastmile");
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
}
