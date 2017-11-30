package com.movisol.questionwizard.views.controls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.tools.HelperUtils;

public class QuestionTipView extends RelativeLayout implements OnClickListener, ScreenViewable {

	private String tipString;
	ApplicationController ac = ApplicationController.getInstance();
	public QuestionTipView(Context context) {
		super(context);
		
		String infService = Context.LAYOUT_INFLATER_SERVICE;
	    LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
	    li.inflate(R.layout.questiontip, this, true);
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", getContext()));
	}

	@Override
	public void initializeControls() {
		
		
	}

	@SuppressLint("NewApi") public void initialize()
	{
		tipString = ac.getActualQuestion().getQuestionTip();
		
		if(ac.getActualQuestion().getBackground() != null)
		{
			setBackgroundResource(HelperUtils.getDrawableResource(ac.getActualQuestion().getBackground(), getContext()));
		}
		
		getBackground().setDither(true);
		WebView tipTv = (WebView) findViewById(R.id.wbTip);
		tipTv.setBackgroundColor(Color.TRANSPARENT);
	//	tipTv.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body><head>");
		sb.append(ac.getLiteralsValueByKey("styleCSS")+"</head>");
		sb.append("<div id='postQuestionTipBox'>");
		
		sb.append(tipString);
		sb.append("</div>");
		sb.append("</body></html>");
		tipTv.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8",null);
				
		LinearLayout tipLayout = (LinearLayout) findViewById(R.id.tipLayout);
		//Background for the tip 
		try {
			tipLayout.setBackgroundResource(HelperUtils.getDrawableResource("postit", getContext()));
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
