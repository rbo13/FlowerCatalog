package com.hanselandpetal.catalog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanselandpetal.catalog.model.Flower;

import java.io.InputStream;
import java.net.URL;
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

        if(flower.getBitmap() != null) {
            ImageView image = (ImageView) view.findViewById(R.id.imageView1);
            image.setImageBitmap(flower.getBitmap());
        }else {
            FlowerAndView container = new FlowerAndView();
            container.flower = flower;
            container.view = view;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);
        }


        return view;
    }

    class FlowerAndView {

        public Flower flower;
        public View view;
        public Bitmap bitmap;
    }

    private class ImageLoader extends AsyncTask<FlowerAndView, Void, FlowerAndView> {

        @Override
        protected FlowerAndView doInBackground(FlowerAndView... params) {

            FlowerAndView container = params[0];
            Flower flower = container.flower;

            try{
                String imageUrl = MainActivity.PHOTOS_BASE_URL + flower.getPhoto();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                flower.setBitmap(bitmap);
                in.close();
                container.bitmap = bitmap;
                return container;
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(FlowerAndView result) {
            ImageView image = (ImageView) result.view.findViewById(R.id.imageView1);
            image.setImageBitmap(result.bitmap);
            result.flower.setBitmap(result.bitmap);
        }
    }
}
