package com.example.tourist_guide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class WriteYourReviewActivity extends Activity {

	class AddRecord extends AsyncTask<Void, Void, InputStream>
	{

		private ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
		
			dialog = new ProgressDialog(WriteYourReviewActivity.this);
			dialog.setTitle("Please Wait");
			dialog.setMessage("Saving Your Review");
			dialog.setCancelable(false);
			dialog.show();
			super.onPreExecute();
		}
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
			
			
			String stringURL = Utils.createURLforServlet("ClientReviewInsert");
			
			String clientName = editName.getText().toString();
			String emailId = editEmail.getText().toString();
			String comment = editComment.getText().toString();
			Float rate = (ratingBar1.getRating());
			String rating = rate.toString();
			
			String finalURL = stringURL + "?clientName=" + clientName + "&mailId=" + emailId + "&comment=" + comment + "&rating=" + rating + "&famousPlace=" + part;
			
			try {
				URL url = new URL(finalURL);
				URLConnection connection = url.openConnection();
				InputStream stream = connection.getInputStream();
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
			dialog.cancel();
			//finish();
			
			Toast.makeText(WriteYourReviewActivity.this, "Review Save Successfully", Toast.LENGTH_SHORT).show();
			super.onPostExecute(result);
		}
		
	}
	
	private EditText editName;
	private EditText editEmail;
	private EditText editComment;
	private RatingBar ratingBar1;
	private String famousPlaceString1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_your_review);
		
		Intent famousPlaceIntent1 = getIntent();
		 famousPlaceString1 = famousPlaceIntent1.getStringExtra("FamPlace1");
		Toast.makeText(this, "Famous Selected is " + famousPlaceString1, Toast.LENGTH_SHORT).show();
		
		editName = (EditText)findViewById(R.id.editName);
		editEmail = (EditText)findViewById(R.id.editEmail);
		editComment = (EditText)findViewById(R.id.editComment);
		ratingBar1 =(RatingBar)findViewById(R.id.ratingBar1);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_your_review, menu);
		return true;
	}
	
	public void submitComment(View V)
	{
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().detectAll().build());
		
		AddRecord tsk = new AddRecord();
		tsk.execute();
	}

}
