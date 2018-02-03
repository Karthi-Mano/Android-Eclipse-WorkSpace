package com.stmarys.learning;

/* 
* File Name:            TopicsListActivity.java  
*
* Product Name:         ||Learning
* Developer Name:     Girish Kumar Kontham
* Date:		            06/28/2017
 * Language:                Java
*/

import com.stmarys.learning.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TopicsListActivity extends Activity implements OnClickListener{
	private boolean topicLocked[];
	private Button btnTopic[];//,btnTopic2,btnTopic3,btnTopic4,btnTopic5,btnTopic6,btnTopic7,btnTopic8,btnTopic9,btnTopic10,btnTopic11,btnTopic12,btnTopic13;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topics_list);
		initialization();
		lockingAndUnlocking();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		lockingAndUnlocking();
	}
	public void initialization()
	{
		topicLocked = new boolean[13]; 
		btnTopic = new Button[13];

		btnTopic[0] = (Button)findViewById(R.id.topicButton0);
		btnTopic[0].setOnClickListener(this);
		btnTopic[1] = (Button)findViewById(R.id.topicButton1);
		btnTopic[1].setOnClickListener(this);
		btnTopic[2]= (Button)findViewById(R.id.topicButton2);
		btnTopic[2].setOnClickListener(this);
		btnTopic[3]= (Button)findViewById(R.id.topicButton3);
		btnTopic[3].setOnClickListener(this);
		btnTopic[4] = (Button)findViewById(R.id.topicButton4);
		btnTopic[4].setOnClickListener(this);
		btnTopic[5] = (Button)findViewById(R.id.topicButton5);
		btnTopic[5].setOnClickListener(this);
		btnTopic[6] = (Button)findViewById(R.id.topicButton6);
		btnTopic[6].setOnClickListener(this);
		btnTopic[7] = (Button)findViewById(R.id.topicButton7);
		btnTopic[7].setOnClickListener(this);
		btnTopic[8] = (Button)findViewById(R.id.topicButton8);
		btnTopic[8].setOnClickListener(this);
		btnTopic[9] = (Button)findViewById(R.id.topicButton9);
		btnTopic[9].setOnClickListener(this);
		btnTopic[10] = (Button)findViewById(R.id.topicButton10);
		btnTopic[10].setOnClickListener(this);
		btnTopic[11] = (Button)findViewById(R.id.topicButton11);
		btnTopic[11].setOnClickListener(this);
		btnTopic[12] = (Button)findViewById(R.id.topicButton12);
		btnTopic[12].setOnClickListener(this);

	}
	//to check if topic is locked or unlocked.
	public void lockingAndUnlocking()
	{	
		SharedPreferences lockSharePref = getApplicationContext().getSharedPreferences("levelSP",Context.MODE_PRIVATE);
		//String lock =
		String level;

		for(int i=1; i<=12; i++)
		{
			level = Integer.toString(i);
			topicLocked[i] = lockSharePref.getBoolean(level,false);

			if(topicLocked[i] == true){			

				btnTopic[i].setBackgroundResource(R.drawable.topicunlocked);
				btnTopic[i].setClickable(true);
			}else{
				btnTopic[i].setBackgroundResource(R.drawable.topiclocked);
				btnTopic[i].setClickable(false);
			}

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		    Intent	intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, TutorialScreenActivity.class);

		switch(v.getId()){

		case R.id.topicButton0 :
			intent.putExtra("topicNumber", "0");
			break;
		case R.id.topicButton1 :
			intent.putExtra("topicNumber", "1");
			break;
		case R.id.topicButton2 :
			intent.putExtra("topicNumber", "2");
			break;
		case R.id.topicButton3 :
			intent.putExtra("topicNumber", "3");
			break;
		case R.id.topicButton4 :
			intent.putExtra("topicNumber", "4");
			break;
		case R.id.topicButton5 :
			intent.putExtra("topicNumber", "5");
			break;
		case R.id.topicButton6 :
			intent.putExtra("topicNumber", "6");
			break;
		case R.id.topicButton7 :
			intent.putExtra("topicNumber", "7");
			break;
		case R.id.topicButton8 :
			intent.putExtra("topicNumber", "8");
			break;
		case R.id.topicButton9 :
			intent.putExtra("topicNumber", "9");
			break;
		case R.id.topicButton10 :
			intent.putExtra("topicNumber", "10");
			break;
		case R.id.topicButton11 :
			intent.putExtra("topicNumber", "11");
			break;
		case R.id.topicButton12 :
			intent.putExtra("topicNumber", "12");
			break;
		default:
			break;

		}
		startActivity(intent);	
		this.finish();
	}

}
