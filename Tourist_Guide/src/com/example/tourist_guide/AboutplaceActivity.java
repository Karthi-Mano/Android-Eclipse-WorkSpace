package com.example.tourist_guide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutplaceActivity extends Activity {

	class GetPlaceDescriptionTask extends AsyncTask<Void, Void, InputStream>{

		private String aboutImagename;
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
			
			
		//	famousPlaceString1.replaceAll("\\s+", "%20");
		//	String stringURL ="http://172.0.3.20:9090/Travel_Guide/PlaceDescriptionTableServlet?famousPlace=Taj Mahal";
		String stringURL ="http://192.168.0.5:9090/Travel_Guide/PlaceDescriptionTableServlet?famousPlace="+part;
			
			//stringURL.replace(" ", "%20");
			Log.e("url", "url="+stringURL);
			try {
				URL url =new URL(stringURL);
				Log.e("sfvsfv",""+url);
				return url.openConnection().getInputStream();
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
		Log.e("sfvsfv",""+reader);
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
		Log.e("PlaceDescription", jsonString);
		try {
			JSONObject rootobject = new JSONObject(jsonString);
			JSONArray jsonArray= rootobject.getJSONArray("placedescriptiontable");
			for(int index=0; index< jsonArray.length();index++){
				
				JSONObject object =(JSONObject) jsonArray.get(index);
			//	AboutPlaceClass placeDescObj= new AboutPlaceClass();
			
				 description = object.getString("placeDescription");
				 
				 aboutImagename = object.getString("placeImage");
				 if(aboutImagename.equalsIgnoreCase("amritsar"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.amrity_col);
			        }
				 if(aboutImagename.equalsIgnoreCase("adequeat"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.aqueduct_of);
			        }
				 if(aboutImagename.equalsIgnoreCase("escorial"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.escorial);
			        }
				 if(aboutImagename.equalsIgnoreCase("redoo"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.red_col);
			        }
				 if(aboutImagename.equalsIgnoreCase("tirumala"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.tiru_col);
			        }
				 if(aboutImagename.equalsIgnoreCase("char"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.gas);
			        }
				 if(aboutImagename.equalsIgnoreCase("kanya"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.kanya);
			        }
				 if(aboutImagename.equalsIgnoreCase("tajdis"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.taj_col);
			        }
				 if(aboutImagename.equalsIgnoreCase("burj_alarah_uae"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.bruj_alarab_uae);
			        }
				 if(aboutImagename.equalsIgnoreCase("burj_khalifa_uae"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.burj_khalifa_uae);
			        }
				 if(aboutImagename.equalsIgnoreCase("colosseum_italy"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.colosseum_italy);
			        }
				 if(aboutImagename.equalsIgnoreCase("grand_canyon_usa"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.grand_canyon_usa);
			        }

				 if(aboutImagename.equalsIgnoreCase("eiffeltower"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.eiffeltower);
			        }
				 if(aboutImagename.equalsIgnoreCase("mont_saint_france"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.mont_saint_france);
			        }

				 if(aboutImagename.equalsIgnoreCase("neuschwanstein_castle"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.neuschwanstein_castle);
			        }

				 if(aboutImagename.equalsIgnoreCase("nigara_usa"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.nigara_usa);
			        }
				 if(aboutImagename.equalsIgnoreCase("palacioreal"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.palacioreal);
			        }
				 if(aboutImagename.equalsIgnoreCase("santamoria_italyplace"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.santamoria_italyplace);
			        }
				 if(aboutImagename.equalsIgnoreCase("schwerin_castle"))
			        {
			     		imageViewPlace.setImageResource(R.drawable.schwerin_castle);
			        }
					placeDescriptionTextView.setText(description);
			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	TextView placeDescriptionTextView;
	ImageView imageViewPlace;
	String description;
	private String famousPlaceString1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_place);
		
		Intent famousPlaceIntent1 = getIntent();
		famousPlaceString1 = famousPlaceIntent1.getStringExtra("FamPlace1");
		Toast.makeText(this, "Famous Selected is " + famousPlaceString1, Toast.LENGTH_SHORT).show();
		
		placeDescriptionTextView = (TextView) findViewById(R.id.aboutTextView);
		placeDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
		 imageViewPlace = (ImageView) findViewById(R.id.aboutImageView);
		 
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().detectAll().build());
		new GetPlaceDescriptionTask().execute();
	}
	@SuppressLint("NewApi") @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getTitle().equals("Parse")) {
		//-StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().detectAll().build());
		//new GetPlaceDescriptionTask().execute();
		
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
