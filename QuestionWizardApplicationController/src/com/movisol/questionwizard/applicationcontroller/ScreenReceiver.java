package com.movisol.questionwizard.applicationcontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver {

	protected ApplicationController ac = ApplicationController.getInstance();

	public ScreenReceiver() {
		ac.setScreenOn(true);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			ac.setScreenOn(false);
		}
		else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT))

		{
			ac.setScreenOn(true);

			if (ac.isAppVisible()) {
				
				
				/*if (ac.getUsageClient() != null)
					ac.getUsageClient();*/

				/*if (ac.getActualBannerViewer() != null) {
					ac.getActualBannerViewer().resumeLoadingAds();
				}*/
			}
		}
	}

}
