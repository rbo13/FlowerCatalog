package com.hanselandpetal.catalog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanselandpetal.catalog.model.Flower;

import java.util.List;

/**
 * Created by b0kn0y on 1/30/2017.
 */

public class FlowerAdapter extends ArrayAdapter<Flower> {

    private Context context;
    private List<Flower> flowerList;

    public FlowerAdapter(Context context, int resource, List<Flower> objects) {
        super(context, resource, objects);
        this.context = context;
        this.flowerList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_flower, parent, false);

        Flower flower = flowerList.get(position);
        TextView textView = (TextView) view.findViewById(R.id.textView1);
        textView.setText(flower.getName());

        ImageView image = (ImageView) view.findViewById(R.id.imageView1);
        image.setImageBitmap(flower.getBitmap());

        return view;
    }
}
