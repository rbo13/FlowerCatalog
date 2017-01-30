package com.hanselandpetal.catalog;

import android.app.Activity;
import android.app.ListActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanselandpetal.catalog.model.Flower;
import com.hanselandpetal.catalog.parsers.FlowerJSONParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

	TextView output;
    ProgressBar pb;
    List<MyTask> tasks;

    List<Flower> flowerList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        pb = (ProgressBar)findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_do_task) {
            if(isOnline()){
                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            }else{
                Toast.makeText(MainActivity.this, "Not connected to the internet!", Toast.LENGTH_SHORT).show();
            }

		}
		return false;
	}

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {

        FlowerAdapter adapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
        setListAdapter(adapter);
	}

    protected boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting()) ? true : false;
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
//            updateDisplay("Starting task");

            if(tasks.size() == 0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);

        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.getData(params[0], "feeduser", "feedpassword");
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            tasks.remove(this);
            if(tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if(result == null) {
                Toast.makeText(MainActivity.this, "Cannot connect to web service!", Toast.LENGTH_SHORT).show();
                return;
            }

            flowerList = FlowerJSONParser.parseFeed(result);
            updateDisplay();
        }

    }

}