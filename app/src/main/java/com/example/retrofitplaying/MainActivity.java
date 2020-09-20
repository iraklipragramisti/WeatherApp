package com.example.retrofitplaying;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.retrofitplaying.remotedatabase.ServiceGenerator;
import com.example.retrofitplaying.remotedatabase.responses.ForecastListResponse;
import com.example.retrofitplaying.util.Constants;
import com.example.retrofitplaying.util.TimeConverter;
import com.example.retrofitplaying.util.UnitConverter;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView cityName, currentWeather, description;
    TextView hourOne, hourTwo, hourThree, hourFour, hourFive, hourOneTemp, hourTwoTemp,
            hourThreeTemp, hourFourTemp, hourFiveTemp;
    TextView dailyTimeOne, dailyTimeTwo, dailyTimeThree, dailyTimeFour, dailyTimeFive,
            dayTimeOne, dayTimeTwo, dayTimeThree, dayTimeFour, dayTimeFive;
    ImageView icon, location, hourIconOne, hourIconTwo, hourIconThree, hourIconFour, hourIconFive,
            dailyIconOne, dailyIconTwo, dailyIconThree, dailyIconFour, dailyIconFive;
    WeatherApi weatherApi;
    SearchView searchView;
    RadioGroup toggle;
    RadioButton hourly, daily;
    LinearLayout dailyLayout, hourlyLayout;
    RelativeLayout mainLayout;
    FusedLocationProviderClient locationProviderClient;
//    ViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);

        location = findViewById(R.id.location);

        toggle = findViewById(R.id.toggle);
        hourly = findViewById(R.id.hourly);
        daily = findViewById(R.id.daily);

        hourlyLayout = findViewById(R.id.hourlyLayout);
        dailyLayout = findViewById(R.id.dailyLayout);



        weatherApi = ServiceGenerator.getWeatherApi();


        searchView = findViewById(R.id.searchView);

        cityName = findViewById(R.id.cityName);
        currentWeather = findViewById(R.id.currentWeather);
        description = findViewById(R.id.description);
        icon = findViewById(R.id.icon);

        hourOne = findViewById(R.id.hourOne);
        hourTwo = findViewById(R.id.hourTwo);
        hourThree = findViewById(R.id.hourThree);
        hourFour = findViewById(R.id.hourFour);
        hourFive = findViewById(R.id.hourFive);

        hourIconOne = findViewById(R.id.hourIconOne);
        hourIconTwo = findViewById(R.id.hourIconTwo);
        hourIconThree = findViewById(R.id.hourIconThree);
        hourIconFour = findViewById(R.id.hourIconFour);
        hourIconFive = findViewById(R.id.hourIconFive);

        hourOneTemp = findViewById(R.id.hourOneTemp);
        hourTwoTemp = findViewById(R.id.hourTwoTemp);
        hourThreeTemp = findViewById(R.id.hourThreeTemp);
        hourFourTemp = findViewById(R.id.hourFourTemp);
        hourFiveTemp = findViewById(R.id.hourFiveTemp);

        dailyTimeOne = findViewById(R.id.dailyTimeOne);
        dailyTimeTwo = findViewById(R.id.dailyTimeTwo);
        dailyTimeThree = findViewById(R.id.dailyTimeThree);
        dailyTimeFour = findViewById(R.id.dailyTimeFour);
        dailyTimeFive = findViewById(R.id.dailyTimeFive);

        dayTimeOne = findViewById(R.id.dayTimeOne);
        dayTimeTwo = findViewById(R.id.dayTimeTwo);
        dayTimeThree = findViewById(R.id.dayTimeThree);
        dayTimeFour = findViewById(R.id.dayTimeFour);
        dayTimeFive = findViewById(R.id.dayTimeFive);

        dailyIconOne = findViewById(R.id.dailyIconOne);
        dailyIconTwo = findViewById(R.id.dailyIconTwo);
        dailyIconThree = findViewById(R.id.dailyIconThree);
        dailyIconFour = findViewById(R.id.dailyIconFour);
        dailyIconFive = findViewById(R.id.dailyIconFive);


        locationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        getLocation();


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });


        toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (daily.isChecked()){
                    hourly.setTextColor(getResources().getColor(R.color.white));
                    daily.setTextColor(getResources().getColor(R.color.lightBlue));
                    hourlyLayout.setVisibility(View.GONE);
                    dailyLayout.setVisibility(View.VISIBLE);
                }
                if (hourly.isChecked()){
                    hourly.setTextColor(getResources().getColor(R.color.lightBlue));
                    daily.setTextColor(getResources().getColor(R.color.white));
                    hourlyLayout.setVisibility(View.VISIBLE);
                    dailyLayout.setVisibility(View.GONE);
                }


            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchCurrent(s);
                setHourlyForecast(s);
                setDailyForecast(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }


    private void searchCurrent(String s){
        Call<Weather> call = weatherApi.getWeather(s, Constants.apiKey, "metric");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Weather weather = response.body();
                assert weather != null;
                cityName.setText(weather.getName());
                currentWeather.setText(String.valueOf((int) weather.getMain().getTemp()) + "°");
                String raw = weather.getWeatherSubclass().get(0).getDescription();
                String[] strings = raw.split(" ");
                StringBuilder builder = new StringBuilder();
                for (String string : strings){
                    builder.append(string.substring(0, 1).toUpperCase());
                    builder.append(string.substring(1));
                    builder.append(" ");
                }
                String str = builder.toString();

                description.setText(str);


                Log.d(TAG, "Icon response check: " + weather.getWeatherSubclass().get(0).getIcon());


                Glide.with(icon)
                        .load("https://openweathermap.org/img/wn/" +
                                weather.getWeatherSubclass().get(0).getIcon() + "@2x.png")
                        .into(icon);

                assert response.body() != null;
                Log.d(TAG, "onResponse: " + response.body().toString());

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                if (t.getMessage().equals("Unable to resolve host \"api.openweathermap.org\": No address associated with hostname"))
                    cityName.setText("Check your internet connection");

            }
        });
    }


    private void getCurrent(String s){
        Call<Weather> call = weatherApi.getWeather(s, Constants.apiKey, "metric");

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "City not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                Weather weather = response.body();
                assert weather != null;
                cityName.setText(weather.getName());
                currentWeather.setText(String.valueOf((int) weather.getMain().getTemp()) + "°");
                String raw = weather.getWeatherSubclass().get(0).getDescription();
                String[] strings = raw.split(" ");
                StringBuilder builder = new StringBuilder();
                for (String string : strings){
                    builder.append(string.substring(0, 1).toUpperCase());
                    builder.append(string.substring(1));
                    builder.append(" ");
                }
                String str = builder.toString();



                description.setText(str);


                Glide.with(icon)
                        .load("https://openweathermap.org/img/wn/" +
                                weather.getWeatherSubclass().get(0).getIcon() + "@2x.png")
                        .into(icon);


            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                if (t.getMessage().equals("Unable to resolve host \"api.openweathermap.org\": No address associated with hostname"))
                    Toast.makeText(MainActivity.this, "City Not Found", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setHourlyForecast(String s){
        Call<ForecastListResponse> listCall = weatherApi.getForecast(s, Constants.apiKey, "metric");
        listCall.enqueue(new Callback<ForecastListResponse>() {
            @Override
            public void onResponse(Call<ForecastListResponse> call, Response<ForecastListResponse> response) {
                if (response.isSuccessful()) {
                    List<Weather> list = response.body().getList();
                    int timezone = response.body().getCity().getTimezone();
                    String hour1 = TimeConverter.getHour(list.get(0).getDt(), timezone);
                    String hour2 = TimeConverter.getHour(list.get(1).getDt(), timezone);
                    String hour3 = TimeConverter.getHour(list.get(2).getDt(), timezone);
                    String hour4 = TimeConverter.getHour(list.get(3).getDt(), timezone);
                    String hour5 = TimeConverter.getHour(list.get(4).getDt(), timezone);
                    hourOne.setText(hour1);
                    hourTwo.setText(hour2);
                    hourThree.setText(hour3);
                    hourFour.setText(hour4);
                    hourFive.setText(hour5);

                    String str1 = Integer.toString((int) list.get(0).getMain().getTemp()) + "°";
                    String str2 = Integer.toString((int) list.get(1).getMain().getTemp()) + "°";
                    String str3 = Integer.toString((int) list.get(2).getMain().getTemp()) + "°";
                    String str4 = Integer.toString((int) list.get(3).getMain().getTemp()) + "°";
                    String str5 = Integer.toString((int) list.get(4).getMain().getTemp()) + "°";
                    hourOneTemp.setText(str1);
                    hourTwoTemp.setText(str2);
                    hourThreeTemp.setText(str3);
                    hourFourTemp.setText(str4);
                    hourFiveTemp.setText(str5);

                    Glide.with(hourIconOne)
                            .load("https://openweathermap.org/img/wn/" +
                                    list.get(0).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(hourIconOne);

                    Glide.with(hourIconTwo)
                            .load("https://openweathermap.org/img/wn/" +
                                    list.get(1).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(hourIconTwo);

                    Glide.with(hourIconThree)
                            .load("https://openweathermap.org/img/wn/" +
                                    list.get(2).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(hourIconThree);

                    Glide.with(hourIconFour)
                            .load("https://openweathermap.org/img/wn/" +
                                    list.get(3).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(hourIconFour);

                    Glide.with(hourIconFive)
                            .load("https://openweathermap.org/img/wn/" +
                                    list.get(4).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(hourIconFive);

                }
            }

            @Override
            public void onFailure(Call<ForecastListResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
}


    private void setDailyForecast(String s){
        Call<ForecastListResponse> list = weatherApi.getForecast(s, Constants.apiKey, "metric");

        list.enqueue(new Callback<ForecastListResponse>() {
            @Override
            public void onResponse(Call<ForecastListResponse> call, Response<ForecastListResponse> response) {
                if (response.isSuccessful()) {
                    List<Weather> list = response.body().getList();
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
                    int timezone = response.body().getCity().getTimezone();
                    List<Weather> threeHoursList = new ArrayList<>();
                    for (int i = 7; i < list.size(); i += 8) {
                        Date date = new Date();
                        date.setTime((list.get(i).getDt() + timezone) * 1000);
                        Calendar calendar = new GregorianCalendar();
                        calendar.setTime(date);
                        threeHoursList.add(list.get(i));

                    }
                    String str1 = Integer.toString((int) threeHoursList.get(0).getMain().getTemp()) + "°";
                    String str2 = Integer.toString((int) threeHoursList.get(1).getMain().getTemp()) + "°";
                    String str3 = Integer.toString((int) threeHoursList.get(2).getMain().getTemp()) + "°";
                    String str4 = Integer.toString((int) threeHoursList.get(3).getMain().getTemp()) + "°";
                    String str5 = Integer.toString((int) threeHoursList.get(4).getMain().getTemp()) + "°";
                    dailyTimeOne.setText(TimeConverter.getWeekday(threeHoursList.get(0).getDt(), timezone));
                    dayTimeOne.setText(str1);
                    dailyTimeTwo.setText(TimeConverter.getWeekday(threeHoursList.get(1).getDt(), timezone));
                    dayTimeTwo.setText(str2);
                    dailyTimeThree.setText(TimeConverter.getWeekday(threeHoursList.get(2).getDt(), timezone));
                    dayTimeThree.setText(str3);
                    dailyTimeFour.setText(TimeConverter.getWeekday(threeHoursList.get(3).getDt(), timezone));
                    dayTimeFour.setText(str4);
                    dailyTimeFive.setText(TimeConverter.getWeekday(threeHoursList.get(4).getDt(), timezone));
                    dayTimeFive.setText(str5);

                    Glide.with(dailyIconOne)
                            .load("https://openweathermap.org/img/wn/" +
                                    threeHoursList.get(0).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(dailyIconOne);

                    Glide.with(dailyIconTwo)
                            .load("https://openweathermap.org/img/wn/" +
                                    threeHoursList.get(1).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(dailyIconTwo);

                    Glide.with(dailyIconThree)
                            .load("https://openweathermap.org/img/wn/" +
                                    threeHoursList.get(2).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(dailyIconThree);

                    Glide.with(dailyIconFour)
                            .load("https://openweathermap.org/img/wn/" +
                                    threeHoursList.get(3).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(dailyIconFour);

                    Glide.with(dailyIconFive)
                            .load("https://openweathermap.org/img/wn/" +
                                    threeHoursList.get(4).getWeatherSubclass().get(0).getIcon() + "@2x.png")
                            .into(dailyIconFive);
                }
            }

            @Override
            public void onFailure(Call<ForecastListResponse> call, Throwable t) {

            }
        });
    }

    private void getLocation(){
        locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                            location.getLongitude(), 1);
                    String lat = Double.toString(location.getLatitude());
                    String lon = Double.toString(location.getLongitude());
                    String city = doCityCleanup(addresses.get(0).getLocality());
                    getCurrent(city);
                    setHourlyForecast(city);
                    setDailyForecast(city);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private String doCityCleanup(String s){
        StringBuilder stringBuilder = new StringBuilder(s.toLowerCase());
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == '\''){
                stringBuilder.deleteCharAt(i);
            }
        }
        Log.d(TAG, "cityCleanup: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void setMyBackground(String s){
        if (s.trim().toLowerCase().equals("clearsky")){
            ContextCompat.getDrawable(mainLayout.getContext(), R.drawable.grey);
        }
    }

}



