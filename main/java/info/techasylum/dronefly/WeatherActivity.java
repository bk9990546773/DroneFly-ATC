package info.techasylum.dronefly;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ahmadnemati.wind.WindView;

public class WeatherActivity extends AppCompatActivity {

    double latitude, longitude;
    TextView des, temp, hum, pre, visi,sectemp;
    ImageView iv;
    WindView windView;
    //FloatingActionButton fab;
    String ic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_weather);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width),(int)(height));


       // getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        latitude = (Double)getIntent().getDoubleExtra("lati",0);
        longitude = (Double)getIntent().getDoubleExtra("longi",0);
        iv = (ImageView) findViewById(R.id.img);
        windView =findViewById(R.id.windview);


       // fab = findViewById(R.id.floatingActionButton);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/


        des =findViewById(R.id.des);
        temp =findViewById(R.id.t1);
        hum =findViewById(R.id.hum);
        pre =findViewById(R.id.pre);
        visi =findViewById(R.id.visi);
        sectemp =findViewById(R.id.t13);

        //speed =findViewById(R.id.wind);
        currentDataMethod();
    }

    public void currentDataMethod() {


        WeatherData.placeIdTask asyncTask =new WeatherData.placeIdTask(new WeatherData.AsyncResponse() {
            @Override
            public void processFinish(String description,String temperature,String humidity,String pressure,String visible,String wspeed, String icon)
            {

                des.setText(description);
                temp.setText(temperature);
                hum.setText(humidity);
                String p = pressure+" hPa";
                pre.setText(p);
                visi.setText(visible);
                sectemp.setText(temperature);
                ic=icon;
                iconSet();

                float p1 = Float.parseFloat(pressure);

                windView.setPressure(p1);
                windView.setPressureUnit(" hPa");

                float s = Float.parseFloat(wspeed);

                windView.setWindSpeed(s);
                windView.setWindSpeedUnit(" km/h");
                windView.start();
            }
        });
        asyncTask.execute(""+latitude,""+longitude);
    }
    public void iconSet(){

        switch (ic){
            case "01d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.day));
                break;
            case "01n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.night));
                break;
            case "02d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.daycloud));
                break;
            case "02n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.nightcloud));
                break;
            case "03d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.cloud));
                break;
            case "03n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.cloud));
                break;
            case "04d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.cloud));
                break;
            case "04n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.cloud));
                break;
            case "09d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.rainycloud));
                break;
            case "09n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.rainycloud));
                break;
            case "10d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.suncloud));
                break;
            case "10n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.mooncloud));
                break;
            case "11d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.thundercloudday));
                break;
            case "11n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.thundercloudnight));
                break;
            case "13d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.snowday));
                break;
            case "13n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.snownight));
                break;
            case "50d":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.haze));
                break;
            case "50n":
                iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.haze));
                break;
            default: iv.setImageDrawable(ContextCompat.getDrawable(WeatherActivity.this,R.drawable.cloud));

        }

    }
}
