package com.co.nightowl.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rizvan Hawaldar on 06/12/16.
 */

public class PlacesDataModel implements Serializable{
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    /**
     *
     * @return
     * The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }


    public class Location implements Serializable{

        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lng")
        @Expose
        private Double lng;

        /**
         *
         * @return
         * The lat
         */
        public Double getLat() {
            return lat;
        }

        /**
         *
         * @param lat
         * The lat
         */
        public void setLat(Double lat) {
            this.lat = lat;
        }

        /**
         *
         * @return
         * The lng
         */
        public Double getLng() {
            return lng;
        }

        /**
         *
         * @param lng
         * The lng
         */
        public void setLng(Double lng) {
            this.lng = lng;
        }

    }


    public class Result implements Serializable{

        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        /**
         *
         * @return
         * The location
         */
        public Location getLocation() {
            return location;
        }

        /**
         *
         * @param location
         * The location
         */
        public void setLocation(Location location) {
            this.location = location;
        }

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

    }
}
