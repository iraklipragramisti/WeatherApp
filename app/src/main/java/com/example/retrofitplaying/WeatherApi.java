package com.example.retrofitplaying;

import com.example.retrofitplaying.remotedatabase.responses.ForecastListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/weather")
    Call<Weather> getWeather(
            @Query("q") String city,
            @Query("appid") String appId,
            @Query("units") String metric
    );

    @GET("data/2.5/weather")
    Call<Weather> idGetWeather(
            @Query("id") int id,
            @Query("appid") String appId,
            @Query("units") String metric
    );

    @GET("data/2.5/forecast")
    Call<ForecastListResponse> getForecast(
            @Query("q") String city,
            @Query("appid") String appId,
            @Query("units") String metric
    );

    @GET("data/2.5/forecast")
    Call<ForecastListResponse> idGetForecast(
            @Query("id") int id,
            @Query("appid") String appId,
            @Query("units") String metric
    );
}
