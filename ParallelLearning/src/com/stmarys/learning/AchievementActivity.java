package com.stmarys.learning;

/* 
* File Name:            AchievementActivity.java  
*
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/
//Girish Kontham
import com.stmarys.learning.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class AchievementActivity extends Activity {
    private TextView totalScoreTv,badgeTv, pointsTopic0,pointsTopic1,pointsTopic2,pointsTopic3,pointsTopic4,pointsTopic5,pointsTopic6,pointsTopic7,pointsTopic8,pointsTopic9,pointsTopic10,pointsTopic11,pointsTopic12;
	private ImageView badgeImage;
    private int totalScore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_achievement);
		initialize();
	}
	public void initialize()
	{
		SharedPreferences sharePref = getApplicationContext().getSharedPreferences("pointsSP",Context.MODE_PRIVATE);
		pointsTopic0 = (TextView)findViewById(R.id.pointsTopic0);
		pointsTopic0.setText(Integer.toString(sharePref.getInt("shared0",0)));
		pointsTopic1 = (TextView)findViewById(R.id.pointsTopic1);
		pointsTopic1.setText(Integer.toString(sharePref.getInt("shared1",0)));
		pointsTopic2 = (TextView)findViewById(R.id.pointsTopic2);
		pointsTopic2.setText(Integer.toString(sharePref.getInt("shared2",0)));
		pointsTopic3 = (TextView)findViewById(R.id.pointsTopic3);
		pointsTopic3.setText(Integer.toString(sharePref.getInt("shared3",0)));
		pointsTopic4 = (TextView)findViewById(R.id.pointsTopic4);
		pointsTopic4.setText(Integer.toString(sharePref.getInt("shared4",0)));
		pointsTopic5 = (TextView)findViewById(R.id.pointsTopic5);
		pointsTopic5.setText(Integer.toString(sharePref.getInt("shared5",0)));
		pointsTopic6 = (TextView)findViewById(R.id.pointsTopic6);
		pointsTopic6.setText(Integer.toString(sharePref.getInt("shared6",0)));
		pointsTopic7 = (TextView)findViewById(R.id.pointsTopic7);
		pointsTopic7.setText(Integer.toString(sharePref.getInt("shared7",0)));
		pointsTopic8 = (TextView)findViewById(R.id.pointsTopic8);
		pointsTopic8.setText(Integer.toString(sharePref.getInt("shared8",0)));
		pointsTopic9 = (TextView)findViewById(R.id.pointsTopic9);
		pointsTopic9.setText(Integer.toString(sharePref.getInt("shared9",0)));
		pointsTopic10 = (TextView)findViewById(R.id.pointsTopic10);
		pointsTopic10.setText(Integer.toString(sharePref.getInt("shared10",0)));
		pointsTopic11 = (TextView)findViewById(R.id.pointsTopic11);
		pointsTopic11.setText(Integer.toString(sharePref.getInt("shared11",0)));
		pointsTopic12 = (TextView)findViewById(R.id.pointsTopic12);
		pointsTopic12.setText(Integer.toString(sharePref.getInt("shared12",0)));
		
		totalScoreTv = (TextView)findViewById(R.id.totalScoreTv);
		badgeTv = (TextView)findViewById(R.id.badgeTv);
		badgeImage = (ImageView)findViewById(R.id.badgeImage);
		
		totalScore = sharePref.getInt("shared0",0) + sharePref.getInt("shared1",0)+sharePref.getInt("shared2",0)+sharePref.getInt("shared3",0)+sharePref.getInt("shared4",0)+sharePref.getInt("shared5",0)+sharePref.getInt("shared6",0)+sharePref.getInt("shared7",0)+sharePref.getInt("shared8",0)+sharePref.getInt("shared9",0)+sharePref.getInt("shared10",0)+sharePref.getInt("shared11",0)+sharePref.getInt("shared12",0);
		totalScoreTv.setText(Integer.toString(totalScore));
		if(totalScore >=13 && totalScore<=26)
		{	
			badgeTv.setText("Congratulations!! You have won a bronze medal");
			badgeImage.setBackgroundResource(R.drawable.medalbronze);
		}else if (totalScore >=27 && totalScore<=39)
		{	
			badgeTv.setText("Congratulations!! You have won a silver medal");
			badgeImage.setBackgroundResource(R.drawable.medalsilver);
		}else if(totalScore>=39)
		{
			badgeTv.setText("Congratulations!! You have won a gold medal");
			badgeImage.setBackgroundResource(R.drawable.medalgold);
		}else
		{
			badgeTv.setText("You have not won any medal yet");
			badgeImage.setBackgroundResource(R.drawable.medalno);
		}
			
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 this.finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
}
