package com.webdigitalcompany.swarajya;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RanaPratapDescriptionActivity extends Activity {

	private TextView rapTextView;
	private Button rapBackButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rana_pratap_description);
		rapTextView = (TextView)findViewById(R.id.rapTextView);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/rana.ttf");
        rapTextView.setTypeface(face);
        rapTextView.setText(Html.fromHtml(getResources().getString(R.string.ranadescription)));
       // rapBackButton=(Button)findViewById(R.id.rapBackButton);
      //  rapBackButton.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 this.finish();
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	/*@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.rapBackButton :
			 this.finish();
		break;
		
		default:
			break;
	}
		
	}*/
}
