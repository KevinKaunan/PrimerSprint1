package com.example.primersprint.ui.response;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geonames {

    @SerializedName("geonames")
    @Expose
    private List<Geoname> geonames = null;

    public List<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<Geoname> geonames) {
        this.geonames = geonames;
    }

}