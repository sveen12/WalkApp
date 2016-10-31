package co.edu.udea.compumovil.gr01.walkapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Pick on 30/10/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItems;

    public CustomAdapter(Context context, List<RowItem> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        ImageView ivRowImage;
        TextView tvRowNombre;
        TextView tvRowUsername;
        TextView tvRowDificultad;
        TextView tvRowClima;
        RatingBar ratingBar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();

            holder.tvRowNombre = (TextView) convertView
                    .findViewById(R.id.tvRowNombre);
            holder.ivRowImage = (ImageView) convertView
                    .findViewById(R.id.ivRowImage);
            holder.tvRowUsername = (TextView) convertView.findViewById(R.id.tvRowUsername);
            holder.tvRowDificultad = (TextView) convertView
                    .findViewById(R.id.tvRowDificultad);
            holder.tvRowClima = (TextView) convertView
                    .findViewById(R.id.tvRowClima);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            RowItem row_pos = rowItems.get(position);

            holder.ivRowImage.setImageResource(R.drawable.image1);
            holder.tvRowUsername.setText("Por: " + row_pos.getUserName());
            holder.tvRowNombre.setText(row_pos.getRouteName());
            holder.tvRowDificultad.setText("Dificultad: " + row_pos.getDificultty());
            holder.tvRowClima.setText("Clima: " + row_pos.getWeather());
            holder.ratingBar.setRating(row_pos.getRating());


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}