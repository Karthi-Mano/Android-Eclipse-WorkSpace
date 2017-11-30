package com.example.tourist_guide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

class ReviewAdapter extends ArrayAdapter<Review>
{
	
	private ArrayList<Review> array;
	private Context context;
	
	public ReviewAdapter(Context context, ArrayList<Review> array) {
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
			layout = (LinearLayout)inflater.inflate(R.layout.review_list_item, null);
		}else{
			
			layout = (LinearLayout) convertView;
		}
		TextView visitorName = (TextView) layout.findViewById(R.id.visitorName);
		TextView visitorComment = (TextView) layout.findViewById(R.id.visitorComment);
	
        Review famousPlaceObj1= array.get(position);
        visitorName.setText(famousPlaceObj1.name);
        visitorComment.setText(famousPlaceObj1.comment);
       
        
        return layout;
	}
	
}

public class ReadReviewActivity extends Activity {

	class GetReviewTask extends AsyncTask<Void, Void, InputStream> 
	{


		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			dialog = new ProgressDialog(ReadReviewActivity.this);
			dialog.setTitle("Please wait...");
			dialog.setMessage("Downloading Review details");
			dialog.show();
		}
		
		@Override
		protected InputStream doInBackground(Void... params) 
		{
			String pattern="[\\s]";
			String replace="%20";
			String part = famousPlaceString1;
			Pattern p=Pattern.compile(pattern);
			Matcher m=p.matcher(part);
			part=m.replaceAll(replace);
			System.out.println(part);
			
			String stringURL = "http://192.168.0.5:9090/Travel_Guide/selectReview?famousPlace=" + part;
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
				JSONArray jsonArray = rootObject.getJSONArray("clientreviewtable");				
				for (int index = 0; index < jsonArray.length(); index++) {
					JSONObject object = (JSONObject) jsonArray.get(index);
					Review fpObject = new Review();
				//	 = object.getInt("id");
					fpObject.name = object.getString("clientName");
					fpObject.comment = object.getString("comment");					
			//		fpObject.placeImage = object.getString("placeImage");
					
					array.add(fpObject);
					}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			adapter.notifyDataSetChanged();
		}

	}	
	
	
	
	
	
	private String famousPlaceString1;
	private ListView reviewVistorList;
	private ArrayList<Review> array;
	private ReviewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_review);
		
		Intent famousPlaceIntent1 = getIntent();
		 famousPlaceString1 = famousPlaceIntent1.getStringExtra("FamPlace1");
		Toast.makeText(this, "Famous Selected is " + famousPlaceString1, Toast.LENGTH_SHORT).show();
		
		
		reviewVistorList = (ListView)findViewById(R.id.reviewVisitorslist);
		
		array = new ArrayList<Review>();
        
        //listView.setOnItemClickListener(this);
        adapter = new ReviewAdapter(this, array);
        
        reviewVistorList.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().detectAll().build());
		new GetReviewTask().execute();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_review, menu);
		return true;
	}

}
