package com.example.tourist_guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;

public class HotelsActivity extends Activity {

	public Hotel hotel;

	class GetHoteltask extends AsyncTask<Void, Void, InputStream>{

		@Override
		protected InputStream doInBackground(Void... params) {
			String pattern="[\\s]";
			String replace="%20";
			String part = famousPlaceString1;
			Pattern p=Pattern.compile(pattern);
			Matcher m=p.matcher(part);
			part=m.replaceAll(replace);
			System.out.println(part);
			
			
		//	famousPlaceString1.replaceAll("\\s+", "%20");
		//	String stringURL ="http://172.0.3.20:9090/Travel_Guide/PlaceDescriptionTableServlet?famousPlace=Taj Mahal";
			String stringURL = "http://192.168.0.5:9090/Travel_Guide/HotelTableServlet?famousPlace="+part;
			
			//stringURL.replace(" ", "%20");
			Log.e("url", "url="+stringURL);
			// TODO Auto-generated method stub
			
			try {
				URL url =new URL(stringURL);
				Log.e("sfvsfv",""+url);
				URLConnection connection = url.openConnection();
				InputStream stream = connection.getInputStream();
				Log.e("svsvs",""+stream);
				return stream;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		
		@Override
		protected void onPostExecute(InputStream result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(result));
		String line = null;
		
		try {
			while((line = reader.readLine())!=null){
				builder.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		String jsonString = builder.toString();
		Log.e("Hotel", jsonString);
		try {
			JSONObject rootobject = new JSONObject(jsonString);
			JSONArray jsonArray= rootobject.getJSONArray("hoteltable");
			for(int index=0; index< jsonArray.length();index++){
				
				JSONObject object =(JSONObject) jsonArray.get(index);
					hotel = new Hotel();			
					description= object.getString("hotelDescription");
					//description = hotel.hotelDescription;
					textHotelDescription.setText(description);
			}
			Log.e("Description",""+ description);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	TextView textHotelDescription;
	String description;
	private String famousPlaceString1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotels);
		
		Intent famousPlaceIntent1 = getIntent();
		 famousPlaceString1 = famousPlaceIntent1.getStringExtra("FamPlace1");
		Toast.makeText(this, "Famous Selected is " + famousPlaceString1, Toast.LENGTH_SHORT).show();
		

		textHotelDescription=(TextView) findViewById(R.id.hotelsTextView);
	}
@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().detectAll().build());
		new GetHoteltask().execute();
		
		
	}
	@SuppressLint("NewApi") @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getTitle().equals("Parse")) {
		
		 //imageViewFestival.setImageBitmap(festival.festivalImage);
		}	
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Parse");
		return true;
	}

}
