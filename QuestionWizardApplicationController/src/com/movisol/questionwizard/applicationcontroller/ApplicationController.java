package com.movisol.questionwizard.applicationcontroller;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

//import com.movisol.adsservice.client.AdsManager;
//import com.movisol.adsservice.client.BannerViewer;
import com.movisol.questionwizard.dataio.LiteralsXmlParserDom;
import com.movisol.questionwizard.dataio.XmlParserDom;
import com.movisol.questionwizard.entities.Choice;
import com.movisol.questionwizard.entities.ChoiceButtonImageQuestion;
import com.movisol.questionwizard.entities.ChoiceButtonSoundQuestion;
import com.movisol.questionwizard.entities.ChoiceImageQuestion;
import com.movisol.questionwizard.entities.ChoiceImageScrollQuestion;
import com.movisol.questionwizard.entities.ChoicePickerQuestion;
import com.movisol.questionwizard.entities.ChoiceQuestion;
import com.movisol.questionwizard.entities.DateQuestion;
import com.movisol.questionwizard.entities.Literals;
import com.movisol.questionwizard.entities.Question;
import com.movisol.questionwizard.entities.QuestionType;
import com.movisol.questionwizard.entities.TextQuestion;
import com.movisol.tools.HelperUtils;


public class ApplicationController{
	
	private static ApplicationController INSTANCE = null;
	private int numberOfQuestions;
	private int progressBarMaximumQuestions = 0;
	private int progressBarIndex = 1;
	private int questionsResourceId;
	private int literalsResourceId;
	private int commonsLiteralsResourceId;
	private List<Integer> questionIndexPath;
	private List<Question> questions;
	private List<Question> allQuestions;
	private List<Literals> literals;
	private List<Literals> commonsLiterals;
	private boolean isLoaded = false;
	private HashMap<String, String> lista;
	private Context context;
	private String resultClasName;
	private boolean randomQuestions;
	private boolean freeQuestionsFilter;
	private boolean qWRandomizeQuestionsPerCategory;
	private int qWRandomizeQuestionsPerCategoryCount;
	private boolean qWDailyQuestionsEnabled;
	private boolean qWRandomizeChoices;
	private boolean lastQuestion = false;
	private boolean isAppVisible = false;
	private boolean exit = true;
	private ScreenReceiver mReceiver;
	private IntentFilter filter;
	private boolean receiverRegistered = false;
	private boolean isScreenOn;
	private boolean isSplashing;
	private boolean isMetric;
	private DisplayMetrics displayMetrics;
	private String lastLanguageUsed;
	private boolean needAppToReboot;

	//private UsageClient usageClient=new UsageClient();
	
	private String sku;
	private String language;
	private String appName;
	private String appSlogan;
	private String bundleVersion;
	private String uiLanguage;
	private String udid;
	private String countryCode;
	private String appAdsColor;
	private String appStoreUrl;
	private String marketplaceUrl;
	private int dpi;
	private String resultId;
	private boolean imagePerCategory;
	private int rightAnswers = 0;
	private int currentPage = 0;
	//private BannerViewer actualBannerViewer;
	
	//protected AdsManager am = AdsManager.getInstance();
	
	public static final String usageTrackingCachefileName = "cacheusertracking";
	
	
	private int acumulatedRuntimeCount=0;
	private int runCount=0;
	private String tmpVariableForResult;
	public static final String PREFS_NAME = "stdPrefsFile";
	public static final String DAILY_QUESTIONS_PREFS = "dQprefs";
	public String topicCode;
	
	
	//It does not allow to create a new instance of the class
	private ApplicationController()
	{
		
	}
	

	public static ApplicationController getInstance()
	{
		if( INSTANCE == null)
			INSTANCE =  new ApplicationController();
		
		return INSTANCE;
	}
	
	//Blocks the clone method
	@Override
	protected Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException("Clone is not allowed");
	}
	
	
	public void start()
	{
		progressBarIndex = 1;
		randomQuestions = Boolean.parseBoolean(HelperUtils.getConfigParam("QWRandomizeQuestions", context));
		progressBarMaximumQuestions = Integer.parseInt(HelperUtils.getConfigParam("QWProgressBarMaximum", context));

		questions = new ArrayList<Question>();
		questionIndexPath = new ArrayList<Integer>();
		
		freeQuestionsFilter = Boolean.parseBoolean(HelperUtils.getConfigParam("QWFreeQuestionFilterEnabled", context));
		qWRandomizeQuestionsPerCategory = Boolean.parseBoolean(HelperUtils.getConfigParam("QWRandomizeQuestionsPerCategory", context));
		qWDailyQuestionsEnabled = Boolean.parseBoolean(HelperUtils.getConfigParam("QWDailyQuestionsEnabled", context));
		qWRandomizeChoices = Boolean.parseBoolean(HelperUtils.getConfigParam("QWRandomizeChoices", context));
		questions = allQuestions;
	
		if(freeQuestionsFilter)
		{
			for(int i = 0; i < questions.size(); i++)
			{
				if (questions.get(i).getFilter() != null && questions.get(i).getFilter().equalsIgnoreCase("freeQuestion=NO"))
				{
					questions.remove(i);
					i--;
				}
			}
		}
		
		numberOfQuestions = questions.size();
		Log.e("Number OF Questions","One"+numberOfQuestions);
		if(qWRandomizeQuestionsPerCategory)
		{
			qWRandomizeQuestionsPerCategoryCount = Integer.parseInt(HelperUtils.getConfigParam("QWRandomizeQuestionsPerCategoryCount", context));
			randomizeQuestionsPerCategory();
			numberOfQuestions = questions.size();
		}
		
		if(randomQuestions)
		{
			numberOfQuestions = Integer.parseInt(HelperUtils.getConfigParam("QWRandomizeQuestionCount", context));
			if(numberOfQuestions == -1)
				numberOfQuestions = questions.size();
			Log.e("Number OF Questions","RondomQuestions"+numberOfQuestions);
			randomizeQuestions();
		}
		
		if(qWDailyQuestionsEnabled)
		{
			SharedPreferences settings = context.getSharedPreferences(DAILY_QUESTIONS_PREFS+"_"+language, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			Date now = new Date();
			
			Date savedTime = new Date();
			savedTime.setTime(settings.getLong("savedTime",0));
			
			//First time saving
			if(savedTime.getTime() == 0)
			{
				editor.putLong("savedTime", now.getTime());
				
				for(int i = 0; i < numberOfQuestions; i++)
				{
					editor.putString("q"+(i+1), questions.get(i).getKey());
				}
				
				editor.commit();
			}
			else //If not, check if we are in the same day of the saving moment
			{
				Date maxDate = new Date();
				maxDate.setTime(0);				
				maxDate.setDate(savedTime.getDate());
				maxDate.setMonth(savedTime.getMonth());
				maxDate.setYear(savedTime.getYear());
				maxDate.setHours(23);
				maxDate.setMinutes(59);
				maxDate.setSeconds(59);
				
				//If we still are in the same day
				if(now.getTime() < maxDate.getTime())
				{
					for(int i = 0; i < numberOfQuestions; i++)
					{
						questions.set(i, findQuestionByKey(settings.getString("q"+(i+1), questions.get(i).getKey())));
					}
				}
				else//If not, take the new questions and save the time
				{
					editor.clear();
					editor.putLong("savedTime", now.getTime());
					
					for(int i = 0; i < numberOfQuestions; i++)
					{
						editor.putString("q"+(i+1), questions.get(i).getKey());
					}
					
					editor.commit();
				}
			}

		}
		
		if(getSku().equals("edadmental"))
		{
			//if(questions.remomo)
			Question datepick=findQuestionByKey("Birthdate");
			questions.remove(datepick);
			questions.add(0,datepick);
			numberOfQuestions=questions.size();
		}
		//Resets the answers
		for(int i = questions.size()-1; i >= 0; --i)
		{
			if (questions.get(i)!=null)
			{
				questions.get(i).setAnswered(false);
				questions.get(i).setLoaded(false);
				
				switch(questions.get(i).getType().getType())
				{
				case QuestionType.CHOICEPICKER:
					for(int j = 0; j < ((ChoicePickerQuestion)questions.get(i)).getOptions().size(); j++)
					{
						((ChoicePickerQuestion)questions.get(i)).getOptions().get(j).setSelectedValue(null);
					}
					break;
					
				case QuestionType.DATE:
					((DateQuestion)questions.get(i)).setSelectedDate(null);
					break;
					
				case QuestionType.TEXT:
					((TextQuestion)questions.get(i)).setSelectedValue(null);
					break;
				}
				
				if(qWRandomizeChoices)
				{
					List<Choice> choicesList = null;
					switch(questions.get(i).getType().getType())
					{
					case QuestionType.CHOICE:
						choicesList = ((ChoiceQuestion)questions.get(i)).getChoices();
						((ChoiceQuestion)questions.get(i)).setChoices(randomizeChoicesList(choicesList));
					break;
						
					case QuestionType.CHOICEIMAGE:
						choicesList = ((ChoiceImageQuestion)questions.get(i)).getChoices();
						((ChoiceImageQuestion)questions.get(i)).setChoices(randomizeChoicesList(choicesList));
					break;
						
					case QuestionType.CHOICEIMAGESCROLL:
						choicesList = ((ChoiceImageScrollQuestion)questions.get(i)).getChoices();
						((ChoiceImageScrollQuestion)questions.get(i)).setChoices(randomizeChoicesList(choicesList));
					break;
					
					case QuestionType.CHOICEBUTTONIMAGE:
						choicesList = ((ChoiceButtonImageQuestion)questions.get(i)).getChoices();
						((ChoiceButtonImageQuestion)questions.get(i)).setChoices(randomizeChoicesList(choicesList));
					break;
					
					case QuestionType.CHOICEBUTTONSOUND:
						choicesList = ((ChoiceButtonSoundQuestion)questions.get(i)).getChoices();
						((ChoiceButtonSoundQuestion)questions.get(i)).setChoices(randomizeChoicesList(choicesList));
					break;
					}
				}
					
			}
		}
	}
	
	private List<Choice> randomizeChoicesList(List<Choice> choicesList) {
		
		Random r = new Random();		
		List<Choice> tmp = new ArrayList<Choice>();
		
		int random;
		int cont = 0;

		while(cont < choicesList.size())
		{
			random = Math.abs(r.nextInt()%choicesList.size());
			tmp.add(choicesList.get(random));
			choicesList.remove(random);			
		}
		
		return tmp;
	}


	private Question findQuestionByKey(String key) 
	{
		Question question = null;	
		for(int i = allQuestions.size()-1; i >= 0; --i)
		{
			if(allQuestions.get(i).getKey().equalsIgnoreCase(key))
			{
				question = allQuestions.get(i);
				break;
			}
		}
		
		return question;
	}


	//Selects 'numberOfQuestions' randomized questions
	public void randomizeQuestions()
	{
		Random r = new Random();		
		List<Question> tmp = new ArrayList<Question>();
		List<Question> tmp2 = new ArrayList<Question>();
		//Just creates a copy of the questions list in a temporal list
		for(int i = questions.size()-1; i >= 0; --i)
		{
			if (questions.get(i)!=null && questions.get(i).getTitle() != null)// && !questions.get(i).getTitle().equals(""))
			{
				tmp.add(questions.get(i));
			}
		}
		int random;
		int cont = 0;
		//Basically it picks a random question among all the questions
		//Adds it to the selected questions 
		//And then it removes the current selected questions from the allQuestions list (this way we do not have repeated questions)
		while(cont < numberOfQuestions)
		{
			Log.e("Number OF Questions","One"+numberOfQuestions);
			random = Math.abs(r.nextInt()%tmp.size());
			tmp2.add(tmp.get(random));
			tmp.remove(random);
			cont++;
		}
		questions = tmp2;
	}
	
	public void randomizeQuestionsPerCategory()
	{
		List<Question> tmp = new ArrayList<Question>();
		HashMap<String, Integer> categories = new HashMap<String, Integer>();
		
		randomizeQuestions();
		
		for(int i = questions.size()-1; i >= 0; --i)
		{
			//If we have no stored this category yet, put it and store it with value 1 (the first)
			if(categories.get(questions.get(i).getCategory()) == null)
			{
				categories.put(questions.get(i).getCategory(), 1);
				tmp.add(questions.get(i));
			}
			else//Otherwise put another question into his category, and increment the number
			{
				if(categories.get(questions.get(i).getCategory()) < qWRandomizeQuestionsPerCategoryCount)
				{
					tmp.add(questions.get(i));
					categories.put(questions.get(i).getCategory(), categories.get(questions.get(i).getCategory())+1);
				}
			}

		}
		questions = tmp;
	}
	
	public int getMaximumQuestions()
	{
		if(progressBarMaximumQuestions != -1)
		{
			return progressBarMaximumQuestions;
		}
		else
		{
			return getQuestions().size();
		}
	}
	
	
	public void initialize()
	{
		//Estas llamadas son asíncronas para que se carguen los XML mientras se muestra el splash
		//Si ya estaban cargados,no volvemos a leer los xml 
		ResetAllValues();
		if(allQuestions == null || allQuestions.size() == 0)
		{
			allQuestions = new ArrayList<Question>();
			readQuestions();
		}
		
		if(commonsLiterals == null || commonsLiterals.size() == 0)
		{
			commonsLiterals = new ArrayList<Literals>();
			readCommonsLiterals();
		}
		
		if(literals == null || literals.size() == 0)
		{
			literals = new ArrayList<Literals>();
			readLiterals();
		}
		if(!Boolean.parseBoolean(HelperUtils.getConfigParam("hideAds", context)))
			readAds();
		
		if(lista == null || lista.size() == 0)
		{
			lista = new HashMap<String, String>();
			
			//Generates only once the Hash Map in order to get an easy way to access to the values of the literals
			for(int i = 0; i < commonsLiterals.size();i++)
			{
				lista.put(commonsLiterals.get(i).getKey(), commonsLiterals.get(i).getValue());
				if(commonsLiterals.get(i).getKey().equalsIgnoreCase("ApplicationName") && commonsLiterals.get(i).getValue() != null)
					this.appName = commonsLiterals.get(i).getValue();
			}
			
			for(int i = 0; i < literals.size();i++)
			{
				lista.put(literals.get(i).getKey(), literals.get(i).getValue());
				if(literals.get(i).getKey().equalsIgnoreCase("ApplicationName") && literals.get(i).getValue() != null)
					this.appName = literals.get(i).getValue();
				if(literals.get(i).getKey().equalsIgnoreCase("ApplicationSlogan") && literals.get(i).getValue() != null)
					this.appSlogan = literals.get(i).getValue();
			}
			
			if(getLiteralsValueByKey("AppStoreUrl") != null)
				this.appStoreUrl =  getLiteralsValueByKey("AppStoreUrl");
			else
				this.appStoreUrl =  "";
			
			if(getLiteralsValueByKey("MarketplaceUrl") != null)
				this.marketplaceUrl =  getLiteralsValueByKey("MarketplaceUrl");
			else
				this.marketplaceUrl =  "";
			
			Set<String> keySet =  lista.keySet();		
			Iterator<String> it = keySet.iterator();
	
			while(it.hasNext())
			{
				String key = it.next();
				String value = lista.get(key);
			
				if(value.indexOf("[[SKU]]") != -1)
				{
					if(key.equalsIgnoreCase("fbImageSrc"))
						value = value.replace("[[SKU]]", sku.substring(0, sku.length()-2));
					else
						value = value.replace("[[SKU]]", sku);
				}
				if(value.indexOf("[[LANGUAGE]]") != -1)
					value = value.replace("[[LANGUAGE]]", language);
				if(value.indexOf("[[APPNAME]]") != -1 && appName != null)
					value = value.replace("[[APPNAME]]", appName);
				if(value.indexOf("[[APPSLOGAN]]") != -1 && appSlogan != null)
					value = value.replace("[[APPSLOGAN]]", appSlogan);
				if(value.indexOf("[[BUNDLEVERSION]]") != -1)
					value = value.replace("[[BUNDLEVERSION]]", bundleVersion);
				if(value.indexOf("[[UILANGUAGE]]") != -1)
					value = value.replace("[[UILANGUAGE]]", uiLanguage);
				if(value.indexOf("[[UDID]]") != -1)
					value = value.replace("[[UDID]]", udid);
				if(value.indexOf("[[COUNTRYCODE]]") != -1)
					value = value.replace("[[COUNTRYCODE]]", countryCode);
				if(value.indexOf("[[APPADSCOLOR]]") != -1)
					value = value.replace("[[APPADSCOLOR]]", appAdsColor);
				if(value.indexOf("[[APPSTOREURL]]") != -1)
					value = value.replace("[[APPSTOREURL]]", appStoreUrl);
				if(value.indexOf("[[MARKETPLACEURL]]") != -1)
					value = value.replace("[[MARKETPLACEURL]]", marketplaceUrl);
				
				lista.put(key, value);
			}		
		}
		isLoaded = true;		
		
		//usageClient.startTracking(context);
		
	}
	
	private void readAds() {
		// TODO Auto-generated method stub
		//am.setContext(context);
		//if(needAppToReboot)
		//	am.deleteCache();
	//	am.loadXML(sku,bundleVersion,language,countryCode,udid);
	}


	public void initializeParameters(Context context, String appAdsColor)
	{
		//TODO conectiontype,model,system,systemversion
		
		this.context = context;
		this.sku = HelperUtils.getConfigParam("SKU",context);
		this.language = HelperUtils.getDeviceLanguage();
		this.bundleVersion = HelperUtils.getApplicationVersion(context);
		this.uiLanguage = HelperUtils.getUiLanguage();
		this.udid = HelperUtils.getDeviceId(context);
		this.countryCode = HelperUtils.getDeviceCountry();
		this.appAdsColor = appAdsColor;
		
		
		 // Restore shared preferences
	     SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	     boolean defaultMetricSystem = true;
	     
	     if(HelperUtils.getDeviceCountry().equalsIgnoreCase("US") || HelperUtils.getDeviceCountry().equalsIgnoreCase("UK") 
	    		 || HelperUtils.getDeviceCountry().equalsIgnoreCase("AU") || HelperUtils.getDeviceCountry().equalsIgnoreCase("IE") )
	    	 defaultMetricSystem = false;
	     
	     isMetric = settings.getBoolean("MetricSystem", defaultMetricSystem);
	  		
	  //   usageClient.initialize(context, sku);
	     
		 
	}
	public void readQuestions()
	{
		if(questionsResourceId != 0)
		{
			InputStream xmlQuestionsFile = context.getResources().openRawResource(questionsResourceId);
			XmlParserDom parser = new XmlParserDom(xmlQuestionsFile);
			allQuestions = parser.parse(isMetric);
		}
	}

	private void readLiterals()
	{
		if(literalsResourceId != 0)
		{
			InputStream xmlLiteralsFile = context.getResources().openRawResource(literalsResourceId);
			LiteralsXmlParserDom parser = new LiteralsXmlParserDom(xmlLiteralsFile);
			literals = parser.parse();
		}
	}
	
	private void readCommonsLiterals()
	{
		if(commonsLiteralsResourceId != 0)
		{
			InputStream xmlLiteralsFile = context.getResources().openRawResource(commonsLiteralsResourceId);
			LiteralsXmlParserDom parser = new LiteralsXmlParserDom(xmlLiteralsFile);
			commonsLiterals = parser.parse();
		}
	}
	
	public String getLiteralsValueByKey(String key)
	{
		return lista.get(key);
	}
	
	public Question getActualQuestion()
	{
		return questions.get(getActualIndexFromPath());
	}
	

	public int getQuestionsResourcesId() {
		return questionsResourceId;
	}

	public void setQuestionsResourcesId(int questionsResourcesId) {
		this.questionsResourceId = questionsResourcesId;
	}

	public int getLiteralsResourcesId() {
		return literalsResourceId;
	}

	public void setLiteralsResourceId(int literalsResourcesId) {
		this.literalsResourceId = literalsResourcesId;
	}

	public void setCommonsLiteralsResourceId(int commonsLiteralsResourceId) {
		this.commonsLiteralsResourceId = commonsLiteralsResourceId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Literals> getLiterals() {
		return literals;
	}

	public void setLiterals(List<Literals> literals) {
		this.literals = literals;
	}
	
	public List<Literals> getCommonsLiterals() {
		return commonsLiterals;
	}

	public void setCommonsLiterals(List<Literals> commonsLiterals) {
		this.commonsLiterals = commonsLiterals;
	}


	public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}


	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}


	public List<Question> getAllQuestions() {
		return allQuestions;
	}


	public void setAllQuestions(List<Question> allQuestions) {
		this.allQuestions = allQuestions;
	}

	public String getTopicCode()
	{
		return topicCode;
	}
	
	public void setTopicCode(String courseCode)
	{
		this.topicCode = courseCode;
	}
	/**
	 * @return the resulTClass
	 */
	@SuppressWarnings("rawtypes")
	public View getResultClass() {
		View view=null;
		try {
			Class rs = Class.forName(resultClasName);
			
			Constructor constructor=rs.getConstructor(Context.class);
			Object obj = constructor.newInstance(context);
			
			if(obj != null && obj instanceof View)
				view=(View) obj;
			else
			{
				Log.e("ApplicationControler","getResultClass: view = null");
				view = null;
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		return view;
	}



	/**
	 * @param resultClasName the resultClasName to set
	 */
	public void setResultClasName(String resultClasName) {
		this.resultClasName = resultClasName;
	}


	public void setRandomQuestions(boolean random) {
		randomQuestions = random;
	}
	
	public int getProgressBarMaximumQuestions() {
		return progressBarMaximumQuestions;
	}


	public void setProgressBarMaximumQuestions(int progressBarMaximumQuestions) {
		this.progressBarMaximumQuestions = progressBarMaximumQuestions;
	}

	public int getProgressBarIndex() {
		return progressBarIndex;
	}


	public void setProgressBarIndex(int progressBarIndex) {
		this.progressBarIndex = progressBarIndex;
	}


	public void addIndexToPath(int index)
	{
		questionIndexPath.add(index);
	}
	
	public void removeLastIndexFromPath()
	{
		questionIndexPath.remove(questionIndexPath.size()-1);
	}
	
	public void removeIndexFromPathById(int id)
	{
		questionIndexPath.remove(id);
	}
	
	public int getActualIndexFromPath()
	{
		//TODO apaño para que en caso de que se salga y vuelva a entrar en el test demasiado rápido
		//no tire una Exception, ya que la tarea asincrona de enableUI elimina el primer elemento '0' que se añadió
		//en el OnCreate de Test
		try {
			return questionIndexPath.get(questionIndexPath.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			questionIndexPath.add(0);
			return questionIndexPath.get(questionIndexPath.size()-1);
		}
	}
	
	public List<Integer> getQuestionIndexPath()
	{
		return questionIndexPath;
	}


	public boolean isLastQuestion() 
	{
		int cont = 0;
		List<Choice> choicesList = null;
		lastQuestion = false;
		
		switch(getActualQuestion().getType().getType())
		{
			case QuestionType.CHOICE:
				ChoiceQuestion choiceQuestion = (ChoiceQuestion)getActualQuestion();
				choicesList = choiceQuestion.getChoices();
			break;
			case QuestionType.CHOICEBUTTONIMAGE:
				ChoiceButtonImageQuestion choiceButtonImageQuestion = (ChoiceButtonImageQuestion)getActualQuestion();
				choicesList = choiceButtonImageQuestion.getChoices();
			break;
			case QuestionType.CHOICEIMAGE:
				ChoiceImageQuestion choiceImageQuestion = (ChoiceImageQuestion)getActualQuestion();
				choicesList = choiceImageQuestion.getChoices();
			break;
			case QuestionType.CHOICEIMAGESCROLL:
				ChoiceImageScrollQuestion choiceImageScrollQuestion = (ChoiceImageScrollQuestion)getActualQuestion();
				choicesList = choiceImageScrollQuestion.getChoices();
			break;
			case QuestionType.CHOICEBUTTONSOUND:
				ChoiceButtonSoundQuestion choiceButtonSoundQuestion = (ChoiceButtonSoundQuestion)getActualQuestion();
				choicesList = choiceButtonSoundQuestion.getChoices();
			break;
		}
		
		
		if(choicesList != null)
		{
			for(int i = 0; i < choicesList.size(); i++)
			{
				if(choicesList.get(i).getNextQuestionKey()!= null && 
						choicesList.get(i).getNextQuestionKey().equalsIgnoreCase("result"))
				{
					cont++;
				}
			}
			
			if(cont == choicesList.size())
			{
					lastQuestion = true;
			}
		}
		
		if(!lastQuestion)
			if(getActualIndexFromPath() == getNumberOfQuestions() - 1)
				lastQuestion = true;
		
		return lastQuestion;
	}


	public int getDpi() {
		return dpi;
	}


	public void setDpi(int dpi) {
		this.dpi = dpi;
	}


	/**
	 * @return the usageClient
	 */
	/*public UsageClient getUsageClient() {
		return usageClient;
	}*/



	public void setResultId(String result) {
		this.resultId = result;
		
	}
	
	public String getResultId()
	{
		return resultId;
	}


	public int getRightAnswers() {
		return rightAnswers;
	}


	public void setRightAnswers(int rightAnswers) {
		this.rightAnswers = rightAnswers;
	}


	public boolean isImagePerCategory() {
		return imagePerCategory;
	}


	public void setImagePerCategory(boolean imagePerCategory) {
		this.imagePerCategory = imagePerCategory;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public boolean isAppVisible() {
		return isAppVisible;
	}


	public void setAppVisible(boolean isAppVisible) {
		this.isAppVisible = isAppVisible;
	}
	
	public void stopApp()
	{
		
	}
	
	/*public void pauseApp()
	{
		if(usageClient != null)
			usageClient.pauseTracking();
		
		if(exit)
		{
			setAppVisible(false);
			if(isScreenOn())
				usageClient.saveSharedPreferences(context);
		}
	}*/
	
//	public void resumeApp()
//	{
//		if (isScreenOn()) 
//		{
//			if(usageClient != null)
//				usageClient.resumeTracking();
//			
//			if (actualBannerViewer != null)
//				actualBannerViewer.resumeLoadingAdds();
//		}
//	}


	public boolean isExit() {
		return exit;
	}


	public void setExit(boolean exit) {
		this.exit = exit;
	}


	/*public BannerViewer getActualBannerViewer() {
		return actualBannerViewer;
	}


	public void setActualBannerViewer(BannerViewer actualBannerViewer) {
		this.actualBannerViewer = actualBannerViewer;
	}
*/

	public boolean isScreenOn() {
		return isScreenOn;
	}


	public void setScreenOn(boolean isScreenOn) {
		this.isScreenOn = isScreenOn;
	}

//girish
	/*public void unregisterReceiver() {
		if(isReceiverRegistered())
			context.unregisterReceiver(mReceiver);
		receiverRegistered = false;
	}
	
	public void registerReceiver() {
		context.registerReceiver(this.mReceiver, this.filter);
		receiverRegistered = true;
	}*/


	public boolean isSplashing() {
		return isSplashing;
	}


	public void setSplashing(boolean isSplashing) {
		this.isSplashing = isSplashing;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}


	public ScreenReceiver getmReceiver() {
		return mReceiver;
	}
//
//
	public void setmReceiver(ScreenReceiver mReceiver) {
		this.mReceiver = mReceiver;
	}


	public IntentFilter getFilter() {
		return filter;
	}


	public void setFilter(IntentFilter filter) {
		this.filter = filter;
	}


	public boolean isReceiverRegistered() {
		return receiverRegistered;
	}


	public void setMetric(boolean b) {
		this.isMetric = b;
	}


	public boolean isMetric() {
		return isMetric;
	}


	public void setDisplayMetrics(DisplayMetrics metrics) {
		this.displayMetrics = metrics;
		this.setDpi(displayMetrics.densityDpi);
		
	}
	
	public DisplayMetrics getDisplayMetrics()
	{
		return displayMetrics;
	}


	public void setTmpVariableForResult(String recommendedWeight) {
		this.tmpVariableForResult = recommendedWeight;
		
	}
	
	public String getTmpVariableForResult() {
		return this.tmpVariableForResult;
		
	}


	public String getSku() {
		return sku;
	}


	public String getLastLanguageUsed() {
		return lastLanguageUsed;
	}


	public void setLastLanguageUsed(String lastLanguageUsed) {
		this.lastLanguageUsed = lastLanguageUsed;
	}


	public boolean isNeedAppToReboot() {
		return needAppToReboot;
	}


	public void setNeedAppToReboot(boolean needAppToReboot) {
		this.needAppToReboot = needAppToReboot;
	}
//Added by Arun For handling runtime locale change 
//call this method in onconfigurationchange of main activity of the project
public void ResetAllValues()
{
	commonsLiterals=null;
	allQuestions=null;
	literals=null;
	lista=null;
}
	
	
	
}
