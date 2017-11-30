package com.movisol.questionwizard.views.controls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.movisol.questionwizard.applicationcontroller.ApplicationController;
import com.movisol.questionwizard.entities.PreQuestionTip;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.R;
import com.movisol.questionwizard.views.listeners.MoveForwardEvent;
import com.movisol.questionwizard.views.listeners.MoveForwardListener;
import com.movisol.tools.HelperUtils;

public class PreQuestionTipView extends RelativeLayout implements OnClickListener, ScreenViewable {

	private PreQuestionTip tip;
	private ProgressBar pbPreTip;
	private CountDownTimer countDown;
	private long millisToFinish;
	protected List<MoveForwardListener> listeners = new ArrayList<MoveForwardListener>();
	ApplicationController ac = ApplicationController.getInstance();
	private boolean canceled = false;
	public PreQuestionTipView(Context context) {
		super(context);
		
		String infService = Context.LAYOUT_INFLATER_SERVICE;
	    LayoutInflater li = (LayoutInflater)getContext().getSystemService(infService);
	    li.inflate(R.layout.prequestiontip, this, true);
		setBackgroundResource(HelperUtils.getDrawableResource("containeropaco", getContext()));
	}

	@Override
	public void initializeControls() {
		
		
	}
	
	public synchronized void addMoveForwardListener(MoveForwardListener listener){
		listeners.add(listener);
	}
	public synchronized void removeMoveForwardListener(MoveForwardListener listener){
		listeners.remove(listener);
	}
	
	public void fireTimeExpiredEvent(){
		MoveForwardEvent mfe = null;
		mfe = new MoveForwardEvent(this, "move_forward");
		Iterator<MoveForwardListener> it = listeners.listIterator();
		while (it.hasNext()){
			 it.next().onTimeExpired(mfe);
		}
	}
	
	@SuppressLint("NewApi") public void initialize()
	{
		tip = ac.getActualQuestion().getPreQuestionTip();
		if(tip.getTitle().contains("[[AnswerTime]]"))
		{
			tip.setTitle(tip.getTitle().replace("[[AnswerTime]]", String.valueOf(ac.getActualQuestion().getAnswerTime())));
		}
		
		if(ac.getActualQuestion().getBackground() != null)
		{
			setBackgroundResource(HelperUtils.getDrawableResource(ac.getActualQuestion().getBackground(), getContext()));
		}
		getBackground().setDither(true);
		
		WebView tipTv = (WebView) findViewById(R.id.wbPreTip);
	
		tipTv.setBackgroundColor(Color.TRANSPARENT);
	//	tipTv.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body><head>");
		sb.append(ac.getLiteralsValueByKey("styleCSS")+"</head>");
		sb.append("<div id='preQuestionTipBox'>");
		
		String titleText = tip.getTitle();
		int height = 0;
		int width = 0;
		switch(ac.getDpi()){
	     case DisplayMetrics.DENSITY_LOW:
	    	 height = 62;
	 		 width = 62;
            break;
	     case DisplayMetrics.DENSITY_MEDIUM:
	    	 height = 62;
	 		 width = 62;
             break;
	     case DisplayMetrics.DENSITY_HIGH:
	    	 height = 62;
	 		 width = 62;
             break;
	     default :
	    	 height = 62;
	 		 width = 62;
	    	 break;
		}	
		titleText = titleText.replace("<img", "<img height='"+height+"' width='"+width+"'");
		sb.append(titleText);
		sb.append("</div>");
		sb.append("</body></html>");
		tipTv.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8",null);
		
		pbPreTip = (ProgressBar) findViewById(R.id.pbPreTip);
		
		if(tip.getTime() > 0)
		{
			pbPreTip.setVisibility(View.VISIBLE);
			pbPreTip.setProgressDrawable(ac.getContext().getResources().getDrawable(HelperUtils.getDrawableResource("progressbar_color", ac.getContext())));
			//Convert to msecs
			pbPreTip.setMax(tip.getTime()*1000);
			pbPreTip.setProgress(0);
			millisToFinish = tip.getTime()*1000L;
			newCountDown();
		}	
				
		LinearLayout tipLayout = (LinearLayout) findViewById(R.id.preTipLayout);
		//Background for the tip 
		tipLayout.setBackgroundResource(HelperUtils.getDrawableResource("postitpre", getContext()));
        
	}
	
	private void newCountDown() {
		countDown = new CountDownTimer(millisToFinish, 1) {

		     public void onTick(long millisUntilFinished) {
		    	 millisToFinish = millisUntilFinished; 
		         pbPreTip.setProgress((int)(tip.getTime()*1000-millisUntilFinished));
		     }

		     public void onFinish() {
		    	 if(!canceled)
		    		 fireTimeExpiredEvent();
		     }
		  };
		
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		switch(visibility)
		{
		case View.VISIBLE:
			if(pbPreTip.getVisibility() == View.VISIBLE)
			{
				if(countDown == null)
					newCountDown();
				canceled = false;
				countDown.start();
			}
			
		break;
		
		case View.GONE:
			if(pbPreTip.getVisibility() == View.VISIBLE)
			{
				countDown.cancel();
				canceled = true;
				countDown = null;
			}
		break;
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public void cancelTimer() {
		if(countDown != null)
			countDown.cancel();
		canceled  = true;
		
	}

}
