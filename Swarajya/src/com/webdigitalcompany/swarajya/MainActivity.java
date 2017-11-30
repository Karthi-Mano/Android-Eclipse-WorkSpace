package com.webdigitalcompany.swarajya;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	private Button shivajiButton;
	private Button ranaPratapButton;
	private Button appExitButton;
	private Button creditButton;
	private Button shivajiGallery;
	private Button ranaPratapGallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		shivajiButton =(Button)findViewById(R.id.shivajiButton);
		shivajiButton.setOnClickListener(this);
		
		ranaPratapButton =(Button)findViewById(R.id.ranaPratapButton);
		ranaPratapButton.setOnClickListener(this);
		
		appExitButton =(Button)findViewById(R.id.appExitButton);
		appExitButton.setOnClickListener(this);

		creditButton =(Button)findViewById(R.id.creditButton);
		creditButton.setOnClickListener(this);
		
		shivajiGallery =(Button)findViewById(R.id.shivajiGallery);
		shivajiGallery.setOnClickListener(this);
		
		ranaPratapGallery =(Button)findViewById(R.id.ranaPratapGallery);
		ranaPratapGallery.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent ;
		switch(v.getId()){
			case R.id.shivajiButton :
				intent	= new Intent(this, ShivajiDescriptionActivity.class);
				startActivity(intent);
			break;
			
			case R.id.ranaPratapButton :
				intent	= new Intent(this, RanaPratapDescriptionActivity.class);
				startActivity(intent);
			break;
			case R.id.creditButton :
				intent	= new Intent(this, CreditsActivity.class);
				startActivity(intent);
			break;
			case R.id.shivajiGallery :
				intent	= new Intent(this, ShivajiGalleryActivity.class);
				startActivity(intent);
			break;
			case R.id.ranaPratapGallery :
				intent	= new Intent(this, RanaGalleryActivity.class);
				startActivity(intent);
			break;
			case R.id.appExitButton :
					System.exit(0);
			break;
			default:
				break;
		}
		
	}

	
}
