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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FestivalsActivity extends Activity {

	class GetFestivaltask extends AsyncTask<Void, Void, InputStream>{

		private String festivalImagename;
		@Override
		protected InputStream doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			String pattern="[\\s]";
			String replace="%20";
			String part = famousPlaceString1;
			Pattern p=Pattern.compile(pattern);
			Matcher m=p.matcher(part);
			part=m.replaceAll(replace);
			System.out.println(part);
			String stringURL = "http://192.168.0.5:9090/Travel_Guide/FestivalTableServlet?famousPlace="+part;
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
		Log.e("Festival", jsonString);
		try {
			JSONObject rootobject = new JSONObject(jsonString);
			JSONArray jsonArray= rootobject.getJSONArray("festivaltable");
			for(int index=0; index< jsonArray.length();index++){
				
				JSONObject object =(JSONObject) jsonArray.get(index);
				
			
				 description = object.getString("festivalDescription");
				 festivalImagename = object.getString("festivalImage");
				 
				 if(festivalImagename.equalsIgnoreCase("carnival_florence_italy"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.carnival_florence_italy);
			        }
				 if(festivalImagename.equalsIgnoreCase("drachenbootfestival_schwerin_german"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.drachenbootfestival_schwerin_german);
			        }
				 if(festivalImagename.equalsIgnoreCase("duabai_fashion_fest_burj_khalifa"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.duabai_fashion_fest_burj_khalifa);
			        }
				 if(festivalImagename.equalsIgnoreCase("duabai_shopping_burj_al_arab"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.duabai_shopping_burj_al_arab);
			        }
				 if(festivalImagename.equalsIgnoreCase("fete_des_remparts_mont_france"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.fete_des_remparts_mont_france);
			        }
				 if(festivalImagename.equalsIgnoreCase("gladiator_colosseum_italy"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.gladiator_colosseum_italy);
			        }
				 if(festivalImagename.equalsIgnoreCase("grand_canyon_usa_fest"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.grand_canyon_usa_fest);
			        }
				 if(festivalImagename.equalsIgnoreCase("meistertrunk_neuschwanstein_german"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.meistertrunk_neuschwanstein_german);
			        }
				 if(festivalImagename.equalsIgnoreCase("neuschwanstein_castle"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.neuschwanstein_castle);
			        }
				 if(festivalImagename.equalsIgnoreCase("paris_marathon_france"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.paris_marathon_france);
			        }
				 if(festivalImagename.equalsIgnoreCase("puccni_pissa_italy"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.puccni_pissa_italy);
			        }
				 if(festivalImagename.equalsIgnoreCase("winter_festival"))
			        {
			     		imageViewFestival.setImageResource(R.drawable.winter_festival);
			        }
				 textFestivalDescription.setText(description);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	TextView textFestivalDescription;
	ImageView imageViewFestival;
	String description;
	private String famousPlaceString1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_festivals);
		Intent famousPlaceIntent1 = getIntent();
		 famousPlaceString1 = famousPlaceIntent1.getStringExtra("FamPlace1");
		Toast.makeText(this, "Famous Selected is " + famousPlaceString1, Toast.LENGTH_SHORT).show();
		
		textFestivalDescription = (TextView) findViewById(R.id.festivalsTextView);
		
		 imageViewFestival = (ImageView) findViewById(R.id.festivalimageView1);
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().detectAll().build());
		new GetFestivaltask().execute();
		
		
	}
	@SuppressLint("NewApi") @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getTitle().equals("Parse")) {
//		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().detectAll().build());
//		new GetFestivaltask().execute();
		
		 
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
