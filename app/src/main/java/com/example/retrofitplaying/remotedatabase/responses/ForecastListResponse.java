package com.example.retrofitplaying.remotedatabase.responses;

import com.example.retrofitplaying.Weather;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastListResponse {
    @SerializedName("list")
    @Expose
    private List<Weather> list;

    @SerializedName("city")
    @Expose
    private City city;


    public List<Weather> getList() {return list;}

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ForecastListResponse{" +
                "list=" + list +
                '}';
    }

    //Json City sub-object
    public static class City {
        private int timezone;

        public int getTimezone() {
            return timezone;
        }

        public void setTimezone(int timezone) {
            this.timezone = timezone;
        }
    }
}
