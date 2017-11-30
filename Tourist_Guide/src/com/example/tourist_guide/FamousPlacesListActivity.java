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
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

class FamousPlaceAdapter extends ArrayAdapter<FamousPlace>
{
	private ArrayList<FamousPlace> array;
	private Context context;
	
	public FamousPlaceAdapter(Context context, ArrayList<FamousPlace> array) {
		super(context, android.R.layout.simple_list_item_1);
		this.array = array;
		this.context = context;
		}
	public int getCount(){
		return this.array.size();
	}

	public View getView(int position , View convertView, ViewGroup parent){
		LinearLayout layout = null;
		if(convertView == null){
			Activity activity = (Activity) this.context;
			LayoutInflater inflater = activity.getLayoutInflater();
			layout = (LinearLayout)inflater.inflate(R.layout.activity_famous_place_item, null);
		}else{
			
			layout = (LinearLayout) convertView;
		}
		TextView textFamousPlaceName = (TextView) layout.findViewById(R.id.famousPlaceName);
		TextView textLocationName = (TextView) layout.findViewById(R.id.locationName);
		ImageView imageViewFamousPlace = (ImageView) layout.findViewById(R.id.famousImageView);
		FamousPlace famousPlaceObj1= array.get(position);
        textFamousPlaceName.setText(famousPlaceObj1.famousPlace);
        textLocationName.setText(famousPlaceObj1.location);
        
 //imageViewCar.setImageResource(car.imageId);
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("redfort"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.red_icon);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("segovia_icon.png"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.aqueduct_of);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("taj"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.taj_icon);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("tiru"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.tiru_icon);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("charmi"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.char_icon);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("kanya"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.kanya_icon);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("amrit"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.amritsar_icon);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("escorial_icon.png"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.escorial);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("escorial_icon.png"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.escorial);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("escorial_icon.png"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.escorial);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("eiffeltower"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.eiffeltower);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("mont_saint_france"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.mont_saint_france);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("neuschwanstein_castle"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.neuschwanstein_castle);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("schwerin_castle"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.schwerin_castle);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("colosseum_italy"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.colosseum_italy);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("santamoria_italyplace"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.santamoria_italyplace);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("grand_canyon_usa"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.grand_canyon_usa);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("nigara_usa"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.nigara_usa);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("bruj_alarab_uae"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.bruj_alarab_uae);
        }
        if(famousPlaceObj1.placeImage.equalsIgnoreCase("burj_khalifa_uae"))
        {
     		imageViewFamousPlace.setImageResource(R.drawable.burj_khalifa_uae);
        }
	
		return layout;
	}		
	}
	
	


public class FamousPlacesListActivity extends Activity implements OnItemClickListener 
{

	class GetFamousPlaceTask extends AsyncTask<Void, Void, InputStream> 
	{


		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			dialog = new ProgressDialog(FamousPlacesListActivity.this);
			dialog.setTitle("Please wait...");
			dialog.setMessage("Downloading FamousPlace details");
			dialog.show();
		}
		
		@Override
		protected InputStream doInBackground(Void... params) 
		{
			String stringURL = "http://192.168.0.5:9090/Travel_Guide/FamousPlaces?id="+countryId;
			try
			{
				URL url = new URL(stringURL);
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
			Log.e("FamousPlaceActivity", jsonString);
			
			try {
				JSONObject rootObject = new JSONObject(jsonString);
				JSONArray jsonArray = rootObject.getJSONArray("touristplace");				
				for (int index = 0; index < jsonArray.length(); index++) {
					JSONObject object = (JSONObject) jsonArray.get(index);
					FamousPlace fpObject = new FamousPlace();
					fpObject.id = object.getInt("id");
					fpObject.famousPlace = object.getString("famousPlace");
					fpObject.location = object.getString("location");					
					fpObject.placeImage = object.getString("placeImage");
					
					array.add(fpObject);
					}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			adapter.notifyDataSetChanged();
		}
		
	}
	
	private ArrayList<FamousPlace> array;
	private FamousPlaceAdapter adapter;
	private ListView listView;
	private String countryId;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famous_places_list);
        
        Intent famousIntent = getIntent();
        countryId= famousIntent.getStringExtra("CountryID");
        
        
        Toast.makeText(this, "Famous Selected is " + famousIntent.getStringExtra("CountryID"), Toast.LENGTH_SHORT).show();
        
        
        array = new ArrayList<FamousPlace>();
        listView = (ListView) findViewById(R.id.famousPlacelistView);
        listView.setOnItemClickListener(this);
        adapter = new FamousPlaceAdapter(this, array);
        
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().detectAll().build());
		new GetFamousPlaceTask().execute();
    	
    }
    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
 // 		if (item.getTitle().equals("Parse")) {
 //   			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().detectAll().build());
 //   			new GetFamousPlaceTask().execute();
 //   		}
//    		if (item.getTitle().equals("PlaceDescription")) {
//    			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().detectAll().build());
//    			Intent intent = new Intent(this, PlaceDescription.class);
//    			startActivity(intent);
//    		

//    		}
   
    		return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    		menu.add("Parse");
    		menu.add("PlaceDescription");
        return true;
    }
    
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		
		FamousPlace famPlace = array.get(position);
		
		Log.e("FamousPlaceActivity",famPlace.famousPlace);
		Toast.makeText(this, "Country Selected is " + famPlace.famousPlace, Toast.LENGTH_SHORT).show();
		
		Intent countryIntent = new Intent(this,DiscriptionActivity.class );
		countryIntent.putExtra("FamPlace",famPlace.famousPlace);
		startActivity(countryIntent);		
	}
    
} 