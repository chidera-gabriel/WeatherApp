package com.example.weatherapp.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.models.ForecastDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private ForecastDay[] forecastDays;
    private View view;
    private Context context;

    // Constructor
    public ForecastAdapter(ForecastDay[] forecastDays, View view, Context context) {
        this.forecastDays = forecastDays;
        this.view = view;
        this.context = context;
    }

    // ViewHolder contains all the views inside the layout_forecast
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMaxTemp;
        private final TextView textViewMinTemp;
        private final ImageView imageView;
        private final TextView textViewDate;
        private final TextView textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMaxTemp = itemView.findViewById(R.id.textViewForecastMaxTemp);
            textViewMinTemp = itemView.findViewById(R.id.textViewForecastMinTemp);
            imageView = itemView.findViewById(R.id.imageViewForecast);
            textViewDate = itemView.findViewById(R.id.textViewForecastDate);
            textViewDescription = itemView.findViewById(R.id.textViewForecastDescription);
        }

        public TextView getTextViewMinTemp() {
            return textViewMinTemp;
        }

        public TextView getTextViewMaxTemp() {
            return textViewMaxTemp;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        public TextView getTextViewDescription() {
            return textViewDescription;
        }
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_forecast, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        ForecastDay forecastDay = this.forecastDays[position];

        // Max Temp
        float maxTemp = forecastDay.getDay().getMaxTemp();
        String strMaxTemp = Math.round(maxTemp) + "°C High";
        holder.getTextViewMaxTemp().setText(strMaxTemp);

        // Min Temp
        float minTemp = forecastDay.getDay().getMaxTemp();
        String strMinTemp = Math.round(minTemp) + "°C Low";
        holder.getTextViewMinTemp().setText(strMinTemp);

        // Icon image
        String imgUrl = "https://" + forecastDay.getDay().getCondition().getIcon();
        ImageView imgView = holder.getImageView();
        Glide.with(view).load(imgUrl).into(imgView);

        // Date
        String dateStr = forecastDay.getDate();
        Date date = Date.valueOf(dateStr);
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM", Locale.CANADA);
        holder.getTextViewDate().setText(dateFormat.format(date));

        // Description
        String description = forecastDay.getDay().getCondition().getText() + ".";
        int chanceRain = forecastDay.getDay().getChanceRain();
        float amountRain = forecastDay.getDay().getChanceRain();
        int chanceSnow = forecastDay.getDay().getChanceSnow();
        float amountSnow = forecastDay.getDay().getAmountSnow();

        if(chanceRain > 0) {
            description += " Chance of rain " + chanceRain + "%. Amount " + Math.round(amountRain) + "mm.";
        }

        if(chanceSnow > 0) {
            description += " Chance of snow " + chanceSnow + "%. Amount " + Math.round(amountSnow) + "cm.";
        }

        description += " Maximum winds " + forecastDay.getDay().getMaxWind() + ". Humidity " + forecastDay.getDay().getHumidity() + "%.";
        holder.getTextViewDescription().setText(description);
    }

    @Override
    public int getItemCount() {
        return forecastDays.length;
    }
}