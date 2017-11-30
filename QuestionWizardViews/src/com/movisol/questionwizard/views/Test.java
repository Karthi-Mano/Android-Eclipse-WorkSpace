package com.movisol.questionwizard.views;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

//import com.movisol.adsservice.helper.AdsUtil;
import com.movisol.questionwizard.activities.CustomActivity;
import com.movisol.questionwizard.applicationcontroller.NavigationController;
import com.movisol.questionwizard.entities.QuestionType;
import com.movisol.questionwizard.interfaces.ScreenViewable;
import com.movisol.questionwizard.views.controls.ChoiceButtonImageQuestionView;
import com.movisol.questionwizard.views.controls.ChoiceButtonSoundQuestionView;
import com.movisol.questionwizard.views.controls.ChoiceImageQuestionView;
import com.movisol.questionwizard.views.controls.ChoiceImageScrollQuestionView;
import com.movisol.questionwizard.views.controls.ChoicePickerQuestionView;
import com.movisol.questionwizard.views.controls.ChoiceQuestionView;
import com.movisol.questionwizard.views.controls.ChoiceTimeQuestionView;
import com.movisol.questionwizard.views.controls.DateQuestionView;
import com.movisol.questionwizard.views.controls.FlipperUpDown;
import com.movisol.questionwizard.views.controls.PreQuestionTipView;
import com.movisol.questionwizard.views.controls.QuestionTipView;
import com.movisol.questionwizard.views.controls.TextQuestionView;
import com.movisol.questionwizard.views.listeners.CompositeListener;
import com.movisol.questionwizard.views.listeners.MoveForwardEvent;
import com.movisol.questionwizard.views.listeners.MoveForwardListener;
import com.movisol.tools.HelperUtils;
import com.movisol.wheel.OnWheelChangedListener;
import com.movisol.wheel.OnWheelScrollListener;
import com.movisol.wheel.WheelView;

public class Test extends CustomActivity implements MoveForwardListener,
		OnClickListener, OnWheelChangedListener, OnWheelScrollListener,
		OnDateChangedListener, OnEditorActionListener, ScreenViewable {

	private NavigationController nc = new NavigationController();
	private static final int REQUEST_RESULTS = 0;
	private static final int RESULT_TEST = 1;
	private static final int RESULT_START = 2;

	private int currentView;
	private int currentQuestionView;

	RelativeLayout layout;
	private Button btnBack;
	private Button btnNext;
	private ProgressBar progressBar;
	private CompositeListener compositeListener;
	private ChoiceQuestionView currentChQview;
	private ChoiceTimeQuestionView currentChTimeQview;
	private ChoiceImageQuestionView currentChImageQview;
	private ChoiceButtonSoundQuestionView currentChButtonSoundQview;
	private ChoiceImageScrollQuestionView currentChImageScrollQview;
	private DateQuestionView currentDateQuestionView;
	private ChoicePickerQuestionView currentChPickerQview;
	private ChoiceButtonImageQuestionView currentChImageButtonQuestionView;
	private TextQuestionView currentTextQuestionView;

	private FlipperUpDown flipperupdownXML;
	private PreQuestionTipView currentPQTipView;
	private QuestionTipView currentQtipView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		setContentView(R.layout.test);

		// TODO esto se hace por el error reportado en varias apps, que da fallo
		// por NullPointer en
		// el constructor de FlipperUpDown ¿?
		if (context == null)
			context = super.getApplicationContext();

		flipperupdownXML = new FlipperUpDown(context);

		// Set flipper sounds
		flipperupdownXML.setPageFlipSound(R.raw.pageflip);
		flipperupdownXML.setPageFinishSound(R.raw.bell);
		flipperupdownXML.initSoundPool();

		//Added try catch to fix the multi tasking issue
		
		try {
			initializeControls();
			ac.getQuestionIndexPath().clear();
			ac.addIndexToPath(0);

			// Loads the first question and shows it
			loadQuestion();
			showFirstQuestion();
			updateProgressBar();
			enableUI();
		} catch (Exception e) {
		System.out.println("ERROR::"+ e);
			exit();
		}
			
		

	}

	@Override
	protected void onResume() {
	
		super.onResume();
		if (ac.isNeedAppToReboot()) {
			exit();
		}
	}

	private void showFirstQuestion() {

		if (nc.currenQuestionHasPreTip()) {
			currentView = QuestionType.PREQUESTIONTIP;
			showQuestion(currentView, FlipperUpDown.TYPE_COME);
		} else {
			showQuestion(currentQuestionView, FlipperUpDown.TYPE_COME);
		}
	}

	@Override
	public void onClick(View v) {
		// Disables the back button temporarily
		btnBack.setEnabled(false);
		btnNext.setEnabled(false);

		if (v.getId() == R.id.btnTestPageBack) {
			moveBackward();
		} else {
			if (ac.getActualQuestion().isAnswered()
					|| currentView == QuestionType.PREQUESTIONTIP
					|| currentView == QuestionType.QUESTIONTIP) {
				switch (currentView) {
				case QuestionType.PREQUESTIONTIP:
					currentPQTipView.cancelTimer();
					break;
				case QuestionType.CHOICEIMAGE:
					currentChImageQview.cancelTimer();
					break;
				}

				moveForward();
			} else {
				updateNextButton();
			}
		}

		// Update the state of the progress bar
		updateProgressBar();
		updateTextButtons();
		enableUI();
	}

	private void updateTextButtons() {
		// Changes the text of the previous button depending on the question
		// index and if the current screen is showing a tip
		if (ac.getActualIndexFromPath() == 0) {

			btnBack.setText(ac.getLiteralsValueByKey("menu"));
			btnBack.setBackgroundResource(HelperUtils.getDrawableResource(
					"btn_menu", context));

			if (nc.currenQuestionHasPreTip()
					&& !(flipperupdownXML.getCurrentView() instanceof PreQuestionTipView)) {
				btnBack.setText(ac.getLiteralsValueByKey("previous"));
				btnBack.setBackgroundResource(HelperUtils.getDrawableResource(
						"btn_previous", context));
			}

			if (nc.currentQuestionHasTip()
					&& flipperupdownXML.getCurrentView() instanceof QuestionTipView) {
				btnBack.setText(ac.getLiteralsValueByKey("previous"));
				btnBack.setBackgroundResource(HelperUtils.getDrawableResource(
						"btn_previous", context));
			}

		} else {
			btnBack.setText(ac.getLiteralsValueByKey("previous"));
			btnBack.setBackgroundResource(HelperUtils.getDrawableResource(
					"btn_previous", context));
		}

		// Changes the button text depending on the screen we are viewing
		if (ac.isLastQuestion()) {
			btnNext.setText(ac.getLiteralsValueByKey("result"));

			if (nc.currenQuestionHasPreTip()
					&& (flipperupdownXML.getCurrentView() instanceof PreQuestionTipView))
				btnNext.setText(ac.getLiteralsValueByKey("next"));

			if (nc.currentQuestionHasTip()
					&& !(flipperupdownXML.getCurrentView() instanceof QuestionTipView))
				btnNext.setText(ac.getLiteralsValueByKey("next"));

		} else {
			btnNext.setText(ac.getLiteralsValueByKey("next"));
		}

	}

	private void selectNext() {
		if (nc.currentQuestionHasTip()) {
			currentView = QuestionType.QUESTIONTIP;
			showQuestion(currentView, FlipperUpDown.TYPE_COME);
		} else if (nc.nextQuestion()) {
			loadQuestion();
			if (nc.currenQuestionHasPreTip()) {
				currentView = QuestionType.PREQUESTIONTIP;
				showQuestion(currentView, FlipperUpDown.TYPE_COME);
			} else
				showQuestion(currentQuestionView, FlipperUpDown.TYPE_COME);
		} else
			showResults();
	}

	private void moveForward() {
		switch (currentView) {
		case QuestionType.PREQUESTIONTIP:
			currentView = currentQuestionView;
			showQuestion(currentView, FlipperUpDown.TYPE_COME);
			break;
		case QuestionType.QUESTIONTIP:
			if (nc.nextQuestion()) {
				loadQuestion();
				if (nc.currenQuestionHasPreTip()) {
					currentView = QuestionType.PREQUESTIONTIP;
					showQuestion(currentView, FlipperUpDown.TYPE_COME);
				} else
					showQuestion(currentQuestionView, FlipperUpDown.TYPE_COME);
			} else
				showResults();
			break;
		default:
			selectNext();
			break;
		}
	}

	private void selectPrevious() {
		if (nc.currenQuestionHasPreTip()) {
			currentView = QuestionType.PREQUESTIONTIP;
			showQuestion(currentView, FlipperUpDown.TYPE_GONE);
		} else if (nc.previousQuestion()) {
			loadQuestion();
			if (nc.currentQuestionHasTip()) {
				currentView = QuestionType.QUESTIONTIP;
				showQuestion(currentView, FlipperUpDown.TYPE_GONE);
			} else
				showQuestion(currentQuestionView, FlipperUpDown.TYPE_GONE);
		} else {
			ac.setExit(false);
			exit();
		}
	}

	private void moveBackward() {
		switch (currentView) {
		case QuestionType.PREQUESTIONTIP:
			if (nc.previousQuestion()) {
				loadQuestion();
				if (nc.currentQuestionHasTip()) {
					currentView = QuestionType.QUESTIONTIP;
					showQuestion(currentView, FlipperUpDown.TYPE_GONE);
				} else
					showQuestion(currentQuestionView, FlipperUpDown.TYPE_GONE);
			} else {
				ac.setExit(false);
				exit();
			}
			break;
		case QuestionType.QUESTIONTIP:
			currentView = currentQuestionView;
			showQuestion(currentQuestionView, FlipperUpDown.TYPE_GONE);
			break;
		default:
			selectPrevious();
			break;

		}
	}

	private void updateProgressBar() {
		progressBar.setProgress(ac.getProgressBarIndex());
	}

	private void showResults() {
		// If we have reached the last question the Result screen is
		// showed in the next click of the next button
		ac.setExit(false);
		Intent resultsView = new Intent(this, Results.class);
		startActivityForResult(resultsView, REQUEST_RESULTS);

		flipperupdownXML.playFinishPageSound();
	}

	private void loadQuestion() {
		// Si tiene prequestion la cargamos
		if (nc.currenQuestionHasPreTip()) {
			currentPQTipView = new PreQuestionTipView(context);
			currentPQTipView.initialize();
			currentPQTipView.addMoveForwardListener(this);
		} else if (currentPQTipView != null) {
			currentPQTipView.removeMoveForwardListener(this);
			currentPQTipView = null;
		}

		// Creamos la vista del tipo de Choicequestion que de momento es la
		// única que hay
		compositeListener = new CompositeListener();

		switch (ac.getActualQuestion().getType().getType()) {
		case QuestionType.DATE:
			currentDateQuestionView = new DateQuestionView(context);
			currentDateQuestionView.initialize(compositeListener);
			currentView = QuestionType.DATE;
			currentQuestionView = QuestionType.DATE;
			compositeListener.addListener((OnDateChangedListener) this);
			break;

		case QuestionType.CHOICE:
			if (ac.getActualQuestion().getAnswerTime() != -1
					&& Boolean.parseBoolean(HelperUtils.getConfigParam(
							"QWQuestionTimeEnabled", context))) {
				currentChTimeQview = new ChoiceTimeQuestionView(context);
				currentChTimeQview.initialize(compositeListener);
				currentView = QuestionType.CHOICETIME;
				currentQuestionView = QuestionType.CHOICETIME;
				currentChTimeQview.addMoveForwardListener(this);
			} else {
				currentChQview = new ChoiceQuestionView(context);
				currentChQview.initialize(compositeListener);
				currentView = QuestionType.CHOICE;
				currentQuestionView = QuestionType.CHOICE;
			}

			break;

		case QuestionType.CHOICEIMAGE:
			currentChImageQview = new ChoiceImageQuestionView(context);
			currentChImageQview.initialize(compositeListener);
			currentView = QuestionType.CHOICEIMAGE;
			currentQuestionView = QuestionType.CHOICEIMAGE;
			currentChImageQview.addMoveForwardListener(this);
			break;
		case QuestionType.CHOICEBUTTONSOUND:
			currentChButtonSoundQview = new ChoiceButtonSoundQuestionView(context);
			currentChButtonSoundQview.initialize(compositeListener);
			currentView = QuestionType.CHOICEBUTTONSOUND;
			currentQuestionView = QuestionType.CHOICEBUTTONSOUND;
			currentChButtonSoundQview.addMoveForwardListener(this);
			break;
			
		case QuestionType.CHOICEIMAGESCROLL:
			currentChImageScrollQview = new ChoiceImageScrollQuestionView(
					context);
			currentChImageScrollQview.initialize(compositeListener);
			currentView = QuestionType.CHOICEIMAGESCROLL;
			currentQuestionView = QuestionType.CHOICEIMAGESCROLL;
			break;

		case QuestionType.CHOICEBUTTONIMAGE:
			currentChImageButtonQuestionView = new ChoiceButtonImageQuestionView(
					context);
			currentChImageButtonQuestionView.initialize(compositeListener);
			currentView = QuestionType.CHOICEBUTTONIMAGE;
			currentQuestionView = QuestionType.CHOICEBUTTONIMAGE;
			break;
		case QuestionType.TEXT:
			currentTextQuestionView = new TextQuestionView(context);
			currentTextQuestionView.initialize(compositeListener);
			currentView = QuestionType.TEXT;
			currentQuestionView = QuestionType.TEXT;
			compositeListener.addListener((OnEditorActionListener) this);
			break;

		case QuestionType.CHOICEPICKER:
			currentChPickerQview = new ChoicePickerQuestionView(context);
			currentChPickerQview.initialize(compositeListener);
			currentView = QuestionType.CHOICEPICKER;
			currentQuestionView = QuestionType.CHOICEPICKER;
			compositeListener.addListener((OnWheelChangedListener) this);
			compositeListener.addListener((OnWheelScrollListener) this);

			break;
		}

		// The first listener to be added is the parent listeners. This way,
		// this will be the last listener fired
		compositeListener.addListener((OnClickListener) this);

	}

	public void showQuestion(int questionType, int animType) {
		flipperupdownXML.setAnimationType(animType);
		switch (questionType) {
		case QuestionType.PREQUESTIONTIP:
			flipperupdownXML.addView(currentPQTipView);
			break;

		case QuestionType.CHOICE:
			flipperupdownXML.addView(currentChQview);
			ac.getActualQuestion().setLoaded(true);
			break;

		case QuestionType.CHOICETIME:
			flipperupdownXML.addView(currentChTimeQview);
			ac.getActualQuestion().setLoaded(true);
			break;
		case QuestionType.TEXT:
			flipperupdownXML.addView(currentTextQuestionView);
			ac.getActualQuestion().setLoaded(true);
			break;
		case QuestionType.CHOICEPICKER:
			flipperupdownXML.addView(currentChPickerQview);
			ac.getActualQuestion().setLoaded(true);
			break;

		case QuestionType.DATE:
			flipperupdownXML.addView(currentDateQuestionView);
			ac.getActualQuestion().setLoaded(true);
			break;

		case QuestionType.CHOICEBUTTONIMAGE:
			flipperupdownXML.addView(currentChImageButtonQuestionView);
			ac.getActualQuestion().setLoaded(true);
			break;

		case QuestionType.CHOICEIMAGE:
			flipperupdownXML.addView(currentChImageQview);
			ac.getActualQuestion().setLoaded(true);
			break;
		case QuestionType.CHOICEBUTTONSOUND:
			flipperupdownXML.addView(currentChButtonSoundQview);
			ac.getActualQuestion().setLoaded(true);
			break;
		case QuestionType.CHOICEIMAGESCROLL:
			flipperupdownXML.addView(currentChImageScrollQview);
			ac.getActualQuestion().setLoaded(true);
			break;

		case QuestionType.QUESTIONTIP:
			currentQtipView = new QuestionTipView(getApplicationContext());
			currentQtipView.initialize();
			flipperupdownXML.addView(currentQtipView);
			break;
		}

		flipperupdownXML.showNext();

	}

	@Override
	public void initializeControls() {
		// Loads the specific GroupView
		layout = (RelativeLayout) findViewById(R.id.viewGroupContainer);
		layout.addView(flipperupdownXML, LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);

		// Initialize adBanner
		LinearLayout adLayout = (LinearLayout) findViewById(R.id.testPageAdLayout);
		adLayout.removeAllViews();
		if (!Boolean.parseBoolean(HelperUtils
				.getConfigParam("hideAds", context))) {
			//bw = AdsUtil.getBannerViewerForTest(context, ac.getSku(), this);
			//adLayout.addView(bw);
		} else {
			ImageView logoBannerImageView = new ImageView(context);
			logoBannerImageView.setImageResource(HelperUtils
					.getDrawableResource("logobanner", context));
			adLayout.addView(logoBannerImageView);
		}
		// Initializes the navigation bar background
		RelativeLayout rLayoutBottom = (RelativeLayout) findViewById(R.id.relativeLayout4);
		rLayoutBottom.setBackgroundResource(HelperUtils.getDrawableResource(
				"bottom", context));
		// Initializes the main page background
		RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.testPageMainLayout);
		rLayout.setBackgroundResource(HelperUtils.getDrawableResource(
				"background", context));
		// Initializes the progressBar and sets the maximum value to the total
		// amount of questions
		progressBar = (ProgressBar) findViewById(R.id.pgbTestPage);
		progressBar.setMax(ac.getMaximumQuestions());
		// Initializes the Previous Button
		btnBack = (Button) findViewById(R.id.btnTestPageBack);
		btnBack.setText(ac.getLiteralsValueByKey("menu"));
		btnBack.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_menu", context));
		btnBack.setTextAppearance(context,
				HelperUtils.getStyleResource("btnBack", context));
		btnBack.setOnClickListener(this);

		if (HelperUtils.getConfigParam("QWNavigationDisabled ", context) != null) {
			if (Boolean.valueOf(HelperUtils.getConfigParam(
					"QWNavigationDisabled ", context)))
				btnBack.setVisibility(View.INVISIBLE);
		}

		// Initializes the Next Button
		btnNext = (Button) findViewById(R.id.btnTestPageNext);
		btnNext.setText(ac.getLiteralsValueByKey("next"));
		btnNext.setBackgroundResource(HelperUtils.getDrawableResource(
				"btn_next", context));
		btnNext.setTextAppearance(context,
				HelperUtils.getStyleResource("btnNextDisabled", context));
		btnNext.setEnabled(false);
		btnNext.setOnClickListener(this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {

			if (HelperUtils.getConfigParam("QWNavigationDisabled ", context) != null) {
				if (!Boolean.valueOf(HelperUtils.getConfigParam(
						"QWNavigationDisabled ", context))) {
					moveBackward();
					updateProgressBar();
					updateTextButtons();
					enableUI();
				} else // En caso de que la navegaci—n estŽ deshabilitado,
						// volvemos directamente a la Mainpage
				{
					ac.setExit(false);
					exit();
				}
			} else {
				moveBackward();
				updateProgressBar();
				updateTextButtons();
				enableUI();
			}

			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	private void enableUI() {
		@TargetApi(3)
		class AsyncWait extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... params) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				switch (currentView) {
				case QuestionType.CHOICE:
					currentChQview.addButtonsListener(compositeListener);
					break;
				case QuestionType.CHOICETIME:
					currentChTimeQview.addButtonsListener(compositeListener);
					break;
				case QuestionType.CHOICEBUTTONIMAGE:
					currentChImageButtonQuestionView
							.addButtonsListener(compositeListener);
					break;
				case QuestionType.CHOICEIMAGE:
					currentChImageQview.addButtonsListener(compositeListener);
					currentChImageQview
							.addImageButtonsListener(compositeListener);
					break;
				case QuestionType.CHOICEBUTTONSOUND:
					currentChButtonSoundQview.addButtonsListener(compositeListener);
					currentChButtonSoundQview
							.addImageButtonsListener(compositeListener);
					break;
				case QuestionType.CHOICEIMAGESCROLL:
					currentChImageScrollQview
							.addButtonsListener(compositeListener);
					break;
				}

				updateNextButton();
				btnBack.setEnabled(true);

			}
		}
		new AsyncWait().execute();

	}

	private void updateNextButton() {

		// Enables or disables the next button if we have answered the current
		// question or we have not
		if (ac.getActualQuestion().isAnswered()
				|| currentView == QuestionType.PREQUESTIONTIP
				|| currentView == QuestionType.QUESTIONTIP
				|| currentView == QuestionType.CHOICEPICKER
				|| currentView == QuestionType.DATE) {
			btnNext.setEnabled(true);
			btnNext.setTextAppearance(context,
					HelperUtils.getStyleResource("btnNextEnabled", context));

			if (currentView == QuestionType.CHOICEPICKER) {
				if (!currentChPickerQview.isCorrectValues()
						|| currentChPickerQview.isWheelScrolling()) {
					btnNext.setEnabled(false);
					btnNext.setTextAppearance(context, HelperUtils
							.getStyleResource("btnNextDisabled", context));
				}
			}

			if (currentView == QuestionType.DATE) {
				if (!currentDateQuestionView.isCorrectDate()) {
					btnNext.setEnabled(false);
					btnNext.setTextAppearance(context, HelperUtils
							.getStyleResource("btnNextDisabled", context));
				}
			}

		} else {
			btnNext.setEnabled(false);
			btnNext.setTextAppearance(context,
					HelperUtils.getStyleResource("btnNextDisabled", context));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {
		case RESULT_START:
			ac.setExit(false);
			// ac.registerReceiver();
			exit();
			break;
		case RESULT_TEST:
			break;
		}

	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		updateNextButton();

	}

	@Override
	public void onScrollingStarted(WheelView wheel) {
		updateNextButton();
	}

	@Override
	public void onScrollingFinished(WheelView wheel) {
		updateNextButton();
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		updateNextButton();

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		updateNextButton();
		return false;
	}

	@Override
	public void onTimeExpired(MoveForwardEvent evt) {
		if (evt.getData().equalsIgnoreCase("move_forward")) {
			moveForward();
			updateProgressBar();
			updateTextButtons();
			enableUI();
		}
	}

}
