package com.example.undine.project_ooad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String title,
                date,
                location,
                description;
        double rate;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                title= null;
                description= null;
                date = null;
                location = null;
                rate = 0;
            } else {
                title = extras.getString("EVENT_TITLE");
                date = extras.getString("EVENT_DATE");
                description = extras.getString("EVENT_DES");
                location = extras.getString("EVENT_LOCATE");
                rate = extras.getDouble("EVENT_RATE");
            }
        } else {
            title= (String)savedInstanceState.getSerializable("EVENT_TITLE");
            description= (String) savedInstanceState.getSerializable("EVENT_DES");
            date= (String) savedInstanceState.getSerializable("EVENT_DATE");
            location = (String) savedInstanceState.getSerializable("EVENT_LOCATE");
            rate = (Double) savedInstanceState.getSerializable("EVENT_RATE");

        }
        //Toast.makeText(getApplicationContext(),title+date+description+location+rate,Toast.LENGTH_SHORT).show();
        TextView textTitle = (TextView)findViewById(R.id.textTitle);
        TextView textDate = (TextView)findViewById(R.id.textDate);
        TextView textLocate = (TextView)findViewById(R.id.DesLocate);
        TextView textDes = (TextView)findViewById(R.id.DesDes);
        TextView textRate = (TextView)findViewById(R.id.textRate);
        RatingBar ratebar = (RatingBar)findViewById(R.id.ratingBar);
        textTitle.setText(title);
        textDate.setText(date);
        textLocate.setText(location);
        textDes.setText(description);
        textRate.setText(Double.toString(rate));
        ratebar.setRating((float) rate);


    }
    private static class ViewHolder{
        ImageView thumbnail;
        TextView title;
        TextView date;
        TextView location;
        TextView description;
        TextView rate;
    }

}
