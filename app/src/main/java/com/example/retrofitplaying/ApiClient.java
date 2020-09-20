package com.example.retrofitplaying;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.retrofitplaying.remotedatabase.ServiceGenerator;
import com.example.retrofitplaying.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiClient {
    private static ApiClient instance;
    private WeatherApi weatherApi = ServiceGenerator.getWeatherApi();
    private MutableLiveData<String> cityName, currentWeather, description;

    public static ApiClient getInstance(){
        if (instance == null){
            instance = new ApiClient();
        }
        return instance;
    }

    private ApiClient(){
        cityName = new MutableLiveData<>();
        currentWeather = new MutableLiveData<>();
        description = new MutableLiveData<>();
        getCurrent();
    }


    public LiveData<String> getCityName() {
        return cityName;
    }

    public LiveData<String> getCurrentWeather() {
        return currentWeather;
    }

    public LiveData<String> getDescription() {
        return description;
    }


    public void getCurrent(){
        Call<Weather> call = weatherApi.getWeather("Tbilisi", Constants.apiKey, "metric");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (!response.isSuccessful()){
                    cityName.setValue(response.message());
                    return;
                }
                Weather weather = response.body();
                assert weather != null;
                cityName.setValue(weather.getName());
                currentWeather.setValue(String.valueOf((int) weather.getMain().getTemp()));
                String raw = weather.getWeatherSubclass().get(0).getDescription();
                String[] strings = raw.split(" ");
                StringBuilder builder = new StringBuilder();
                for (String string : strings){
                    builder.append(string.substring(0, 1).toUpperCase());
                    builder.append(string.substring(1));
                    builder.append(" ");
                }
                String str = builder.toString();

                description.setValue(str);


//                Glide.with(icon)
//                        .load("https://openweathermap.org/img/wn/" +
//                                weather.getWeatherSubclass().get(0).getIcon() + "@2x.png")
//                        .into(icon);

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                if (t.getMessage().equals("Unable to resolve host \"api.openweathermap.org\": No address associated with hostname"))
                    cityName.setValue("Check your internet connection");

            }
        });
    }
}
