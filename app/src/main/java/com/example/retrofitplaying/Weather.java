package com.example.retrofitplaying;

import com.example.retrofitplaying.remotedatabase.responses.WeatherResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    private String name;
    private Main main;
    @SerializedName("weather")
    @Expose
    private List<WeatherSubclass> weatherSubclass;
    private long dt;

    public Weather() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<WeatherSubclass> getWeatherSubclass() {
        return weatherSubclass;
    }

    public void setWeatherSubclass(List<WeatherSubclass> weatherSubclass) {
        this.weatherSubclass = weatherSubclass;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }



    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", main=" + main +
                ", weatherSubclass=" + weatherSubclass +
                '}';
    }

    //Json Main sub-object
    public static class Main {
        private double temp;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

    //Json Weather sub-object
    public static class WeatherSubclass {
        private String description;
        private String icon;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }


}

