package com.example.tourist_guide;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

class CountryAdapter extends ArrayAdapter<Country>
{
	private ArrayList<Country> array;
	private Context context;
	
	public CountryAdapter(Context context, ArrayList<Country> array) {
		super(context, android.R.layout.simple_list_item_1);
		this.array = array;
		this.context = context;
		}
	public int getCount(){
		return this.array.size();
	}

	public View getView(int position , View convertView, ViewGroup parent){
		AnimationSet set=new AnimationSet(true);
		Animation animation =new AlphaAnimation(0.0f, 0.0f);
		animation.setDuration(1400);
		set.addAnimation(animation);
		animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,1.0f);
		animation.setDuration(1500);
		set.addAnimation(animation);
		
		
		LinearLayout layout = null;
		if(convertView == null){
			Activity activity = (Activity) this.context;
			LayoutInflater inflater = activity.getLayoutInflater();
			layout = (LinearLayout)inflater.inflate(R.layout.activity_country_list_item, null);
		}else{
			
			layout = (LinearLayout) convertView;
			layout.startAnimation(set);
			
		}
		TextView countryName = (TextView) layout.findViewById(R.id.countryName);
		TextView languageName = (TextView) layout.findViewById(R.id.language);
		TextView currency = (TextView) layout.findViewById(R.id.currency);
		TextView capital = (TextView) layout.findViewById(R.id.capital);
		ImageView imageViewPlace = (ImageView) layout.findViewById(R.id.countryImageView);

        Country famousPlaceObj1= array.get(position);
        countryName.setText(famousPlaceObj1.countries);
        languageName.setText(famousPlaceObj1.language);
        currency.setText(famousPlaceObj1.currency);
        capital.setText(famousPlaceObj1.capital);

        if(famousPlaceObj1.flagName.equalsIgnoreCase("brazil_icon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.brazil_icon);
        }
        if(famousPlaceObj1.flagName.equalsIgnoreCase("germany_icon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.germany_icon);
        }
        if(famousPlaceObj1.flagName.equalsIgnoreCase("france_icon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.france_icon);
        }
        if(famousPlaceObj1.flagName.equalsIgnoreCase("india_icon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.india_icon);
        }
        if(famousPlaceObj1.flagName.equalsIgnoreCase("italy_icon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.italy_icon);
        }
        if(famousPlaceObj1.flagName.equalsIgnoreCase("united_statesicon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.united_statesicon);
        }
        if(famousPlaceObj1.flagName.equalsIgnoreCase("unitedemirates_icon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.unitedemirates_icon);
        }
        if(famousPlaceObj1.flagName.equalsIgnoreCase("spain_icon.png"))
        {
     		imageViewPlace.setImageResource(R.drawable.spain_icon);
        }
		return layout;
	}		
	}
	
	


public class CountryListActivity extends Activity implements OnItemClickListener 
{

	class CountryTask extends AsyncTask<Void, Void, InputStream> 
	{


		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			dialog = new ProgressDialog(CountryListActivity.this);
			dialog.setTitle("Please wait...");
			dialog.setMessage("Downloading Country details");
			dialog.show();
		}
		
		@Override
		protected InputStream doInBackground(Void... params) 
		{
			String stringURL = "http://192.168.0.7:8080/TravelGuide/Country";
			Log.e("data",""+stringURL);
			
			try
			{
				URL url = new URL(stringURL);
				Log.e("Url",""+url);
					return url.openConnection().getInputStream();
			} catch (MalformedURLException e) 
			{
				e.printStackTrace();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(InputStream result)
		{
			super.onPostExecute(result);
			dialog.cancel();
			
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(result));
			Log.e("reader",""+reader);	
			String line = null;
			try
			{
				while((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			array.clear();
	
			String jsonString = builder.toString();
			Log.e("CountryActivity", jsonString);
			
			try {
				JSONObject rootObject = new JSONObject(jsonString);
				JSONArray jsonArray = rootObject.getJSONArray("countrytable");				
				for (int index = 0; index < jsonArray.length(); index++) {
					JSONObject object = (JSONObject) jsonArray.get(index);
					Country fpObject = new Country();
					fpObject.countryId = object.getString("id");
					fpObject.countries = object.getString("countries");
					fpObject.language = object.getString("language");					
					fpObject.capital = object.getString("capital");
					fpObject.currency = object.getString("currency");
					fpObject.flagName = object.getString("flagName");
					
					array.add(fpObject);
					}
				Log.e("Array",""+array);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			adapter.notifyDataSetChanged();
		}
		
	}
	
	private ArrayList<Country> array;
	private CountryAdapter adapter;
	private ListView listView;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);
        
        
		array = new ArrayList<Country>();
        listView = (ListView) findViewById(R.id.countryListView);
        listView.setOnItemClickListener(this);
        adapter = new CountryAdapter(this, array);
        listView.setAdapter(adapter);
        new CountryTask().execute();
        
    }
    
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().detectAll().build());
		//new CountryTask().execute();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	Toast.makeText(this, "BEfore Parse--"+array.size(), Toast.LENGTH_LONG).show();
    		if (item.getTitle().equals("Parse")) {
    			//StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().detectAll().build());
    			//new CountryTask().execute();
    		}
       		return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    		menu.add("Parse");
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		
		Country country = array.get(position);
		
		Log.e("Activity", country.countries);
		Toast.makeText(this, "Country Selected is " + country.countryId + " " + country.countries, Toast.LENGTH_SHORT).show();
		
		Intent countryIntent = new Intent(this,FamousPlacesListActivity.class );
		countryIntent.putExtra("CountryID",country.countryId);
		startActivity(countryIntent);		
	}
    
} 