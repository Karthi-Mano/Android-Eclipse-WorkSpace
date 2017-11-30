package com.movisol.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class HelperUtils {

	/**@author Daniel 
	 * @param resourcename nombre del recurso a obtener
	 * @param context contexto de la aplicación
	 * @return el id de un recurso de tipo drawable
	 */
	

    private static SoundPool soundPool = null;
    private static HashMap<Integer, Integer> soundsMap = null;
    public final static int PAGEFLIP = 1;
    public final static int BELL = 2;
    
	public static int getDrawableResource (String resourcename,Context context){
		
		return context.getResources().getIdentifier(resourcename, "drawable",context.getPackageName());
		
	}
	public static int getStyleResource (String resourcename,Context context){
		return context.getResources().getIdentifier(resourcename, "style",context.getPackageName());
	
	}
	public static int getLayoutResource (String resourcename,Context context){
		return context.getResources().getIdentifier(resourcename, "layout",context.getPackageName());
	
	}
	
	public static int getRawResource (String resourcename,Context context){
		return context.getResources().getIdentifier(resourcename, "raw",context.getPackageName());
	
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);
	
	    final int color = 0xff424242;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	    final float roundPx = pixels;
	
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	
	    return output;
	}


	public static boolean createExternalStoragePrivatelFile(String filename,String resourcename,Context context){
			
			
			File f = new File (Environment.getExternalStorageDirectory(),filename);
			
			if(f != null)
	
		    try {
		    	
		    	if(HelperUtils.getDrawableResource(resourcename, context) != 0)
		    	{
			        InputStream is = context.getResources().openRawResource(HelperUtils.getDrawableResource(resourcename, context));
			        OutputStream os = new FileOutputStream(f);
			        byte[] data = new byte[is.available()];
			        is.read(data);
			        os.write(data);
			        is.close();
			        os.close();
			        return true;
		    	}
	
		    } catch (IOException e) {
		    	return false;
		        // Unable to create file, likely because external storage is
		        // not currently mounted.
		        //Log.w("ExternalStorage", "Error writing " + file, e);
		    }
		    return false;
	}
	public static void deleteExternalStoragePrivateFile(Context context,String filename) {
	    // Get path for the file on external storage.  If external
	    // storage is not currently mounted this will fail.
	    File file = new File(Environment.getExternalStorageDirectory(), filename);
	    if (file != null) {
	        file.delete();
	    }
	}
	public static boolean hasExternalStoragePrivateFile(Context context,String filename) {
	        // Get path for the file on external storage.  If external
	        // storage is not currently mounted this will fail.
	        File file = new File(Environment.getExternalStorageDirectory(), filename);
	        if (file != null) {
	            return file.exists();
	        }
	        return false;
	    }
	
	public static void initSoundPool(Context context, int sound1, int sound2)
	{
		
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        soundsMap = new HashMap<Integer, Integer>();
        soundsMap.put(PAGEFLIP, soundPool.load(context, sound1, 1));
        soundsMap.put(BELL, soundPool.load(context, sound2, 1));
		
	}
	
	public static void playsound (Context context, int sound){
		
//		MediaPlayer mp = MediaPlayer.create(context, resourceid);   
//	
//		if(mp != null)
//		{
//	//		mp.setVolume(100,100);
//			mp.start();
//		    
//		    /*
//		    while (mp.isPlaying()) {
//		        // donothing
//		     };
//		     mp.stop();
//		     mp.reset();
//		   */
//		    mp.setOnCompletionListener(new OnCompletionListener() {
//		
//		        @Override
//		        public void onCompletion(MediaPlayer mp) {
//		            // TODO Auto-generated method stub
//		        	mp.release();
//		        }
//		    });
//		}
		
		AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;  
		soundPool.play(soundsMap.get(sound), volume, volume, 1, 0, 1);
	   
	}

	public static String getDeviceLanguage()
	{
		return Locale.getDefault().getLanguage();
	}
	
	public static String getUiLanguage()
	{
		return Locale.getDefault().getDisplayLanguage();
	}
	
	public static String getDeviceCountry()
	{
		return Locale.getDefault().getCountry();
	}
	
	public static String getDeviceId(Context context)
	{
		//Obtenemos el deviceID 		

		TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID;
		String androidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

		deviceID = androidID;
		
		if(tManager.getDeviceId() != null)
			deviceID += tManager.getDeviceId();
		
		MessageDigest digest;
		try {
		    digest = MessageDigest.getInstance("MD5");
		    digest.reset();
		    digest.update(deviceID.getBytes());
		    byte[] a = digest.digest();
		    int len = a.length;
		    StringBuilder sb = new StringBuilder(len << 1);
		    for (int i = 0; i < len; i++) {
		        sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
		        sb.append(Character.forDigit(a[i] & 0x0f, 16));
		    }
		    return sb.toString();
		}
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace(); 
		}
		
		return null;
	}
	public static String getApplicationVersion (Context context){
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
			return String.valueOf(packageInfo.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		}
		
	}
	public static String getConfigParam (String key,Context context){
	
	try {
		InputStream xmlFileInputStream = context.getAssets().open("settings.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		// Reads the whole xml file
		Document dom = builder.parse(xmlFileInputStream);

		// Retrieves the root element
		Element root = dom.getDocumentElement();

		//Gets all the <setting> tags
		NodeList nlSettings = root.getElementsByTagName("setting");
		
		for(int i = 0; i < nlSettings.getLength(); i++)
		{
			Node nSetting = nlSettings.item(i);
			NamedNodeMap settingAttr = nSetting.getAttributes();
			
			if (settingAttr.getNamedItem("key").getNodeValue().equalsIgnoreCase(key)){
				return nSetting.getFirstChild().getNodeValue();
			}
		}
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	return null;
	}
	public static String getNodeText(Node data)
    {
        StringBuilder texto = new StringBuilder();
        NodeList fragmentos = data.getChildNodes();
 
        for (int k=0;k<fragmentos.getLength();k++)
        {
            texto.append(fragmentos.item(k).getNodeValue());
        }
 
        return texto.toString();
    }
	public static void cacheObjectToFile(Context context,String filename,Object object){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = context.openFileOutput(filename,Context.MODE_PRIVATE);
			out = new ObjectOutputStream(fos);
			out.writeObject(object);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// Hay que cerrrar siempre los streams
				if (fos != null)
					fos.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static Object readCachedFile (Context context,String filename){
		FileInputStream fis;
		try {
			fis = context.openFileInput(filename);
			ObjectInputStream in = new ObjectInputStream(fis);
			// Recuperamos el objeto cache
			return in.readObject();
		} catch (FileNotFoundException e) {
			return null;
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		}

	@SuppressWarnings("rawtypes")
	public static String objectToXML (Object object){
	
		XmlSerializer serializer = Xml.newSerializer();
		 
		StringWriter writer = new StringWriter();
		
		try {
			
			serializer.setOutput(writer);
			
			//Open the document
			//serializer.startDocument("UTF-8", true);
		
			HelperUtils.classToXML(object, writer, serializer);
			
			//Close the document
			serializer.endDocument();
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return writer.toString();
		
		
	}
	@SuppressWarnings("rawtypes")
	private static void  classToXML(Object obj,StringWriter wrt,XmlSerializer ser){
	
	try {
			
		 Class cls = obj.getClass();
	    
		 ser.startTag("", cls.getSimpleName());
	    
	     Field[] fieldlist = cls.getDeclaredFields();
		 
	     TreeMap<String, Field> fieldTreeMap = new TreeMap<String, Field>();
	     
	     //Put fields in a key sorting structure
	     for(Field fld: fieldlist){
	    	 fieldTreeMap.put(fld.getName(), fld);
	     }
	   
	     for (String key : fieldTreeMap.keySet()) {
	         
	    	 //System.out.println("key/value: " + key + "/"+fieldTreeMap.get(key));
	     
	         Field currField=fieldTreeMap.get(key);
	     
	     	 ser.startTag("",  currField.getName());
			
			
			 String className=currField.getType().getSimpleName();
				
			 currField.setAccessible(true);
			 
			Object data=null;
			
			try {
				data = currField.get(obj);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			if (data!=null && !data.toString().equalsIgnoreCase("0")){
			
				if (className.equalsIgnoreCase("List")){
				
					List lst = (List) data;
					
					Iterator it = lst.iterator();
					
					while (it.hasNext()){
						
					Object item = it.next();
					
						HelperUtils.classToXML(item, wrt, ser);
					
					}
					
					
			
				}else
					
					{
					if (className.equalsIgnoreCase("Date")){
						Date date=(Date) data;
						String formatedDate=DateFormat.format("yyyy-MM-dd hh:mm:ss", date.getTime()).toString();
						ser.text(formatedDate);
						
					}else if(className.equalsIgnoreCase("Hashtable")){
					
						@SuppressWarnings("unchecked") //Solo esta implementado el parser para hashmaps de clave/valor tipo   strings
						HashMap<String,String> customMap=(HashMap<String, String>) data;
						
						Iterator customIterator = customMap.entrySet().iterator();
						while (customIterator.hasNext()){
							
							Entry entry = (Entry) customIterator.next();
							
							ser.startTag("",currField.getName()+"Item");
								ser.startTag("","Key");
									ser.text(entry.getKey().toString());
								ser.endTag("","Key");
								ser.startTag("","Value");
									ser.text(entry.getValue().toString());
								ser.endTag("","Value");
							ser.endTag("",currField.getName()+"Item");
							
						}
						
						
						
					}
						
					else{
						
						ser.text(data.toString());
						}
				
					}
				ser.endTag("", currField.getName());
				}else//Si el campo no tiene datos o tiene valor 0 ponemos una <EndTag/>
					{
					ser.endTag("", currField.getName());
					}
		
	     	}
			//Close the class tag
			 ser.endTag("",cls.getSimpleName());
	     	
			 
		    } catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	 }
	public static String getDeviceMode(){
		return android.os.Build.MODEL;
	}
	public static String getOSVersionName(){
		switch( android.os.Build.VERSION.SDK_INT)
		{
		case 4:
			return "Donut";
		case 5:
			return "Eclair";
		case 6:
			return "Eclair_0_1";
		case 7:
			return "Eclair_MR1";
		case 8:
			return "Froyo";
		case 9:
			return "Gingerbread";
		case 10:
			return "Gingerbread_MR1";
		case 11:
			return "Honeycomb";
		case 12:
			return "Honeycomb_MR1";
		case 13:
			return "HONEYCOMB_MR2";
		case 14:
			return "Ice_Cream_Sandwich";
		case 15:
			return "Ice_Cream_Sandwich_MR1";
		case 16:
			return "Jelly_Bean";
		case 17:
			return "Jelly_Bean_MR1";
		default:
			return "Unkwonwn";
		}
	}
	public static String getOSVersionNumber(){
		return android.os.Build.VERSION.RELEASE;
	
	}
	public static String getConnectionType(Context context){
		
		ConnectivityManager connectivityMgr = (ConnectivityManager)	context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if(connectivityMgr.getActiveNetworkInfo() != null)
			return connectivityMgr.getActiveNetworkInfo().getTypeName();
		else
			return null;
		//	int netType = info.getType();
		//	int netSubtype = info.getSubtype();
		//if (netType == ConnectivityManager.TYPE_WIFI) {
	
		
	}
	/**
	 * Guarda log (mode append) en el fichero Debug.log 
	 * @param context
	 * @param log
	 */
	public static void logToFile (Context context,String log)
	{
	FileOutputStream fos = null;
	ObjectOutputStream out = null;
	
		try {
			fos = context.openFileOutput("Debug.log",Context.MODE_APPEND);
			out = new ObjectOutputStream(fos);
			out.writeObject(log+"\n");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}


	/**
	 * Devuelve un View con las propiedades cargadas a partir de un recurso dado
	 * @param context
	 * @param resourceName nombre del recurso xml a cargar
	 * @return
	 */
	public static View inflateViewFromResource(Context context,String resourceName){
		LayoutInflater lit = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		return lit.inflate(HelperUtils.getLayoutResource(resourceName, context), null);
	}
	
	
	public static boolean checkConnectivity(Context context) {
		ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	
		if ( (conMgr.getNetworkInfo(0) != null && conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED) 
		    ||  (conMgr.getNetworkInfo(1) != null && conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED  )) 
		{
			return true;
		    //notify user you are online
			
		}
		else if ( (conMgr.getNetworkInfo(0) != null && conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED )
		    ||  (conMgr.getNetworkInfo(1) != null && conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED))
		{
		    //notify user you are not online
			return false;
	
		}
		return false;
	}
	
	public static void showToastMessage(Context context, String customMessage, int duration )
	{
		Toast.makeText(context, customMessage, duration).show();
	}
	
	@SuppressWarnings("rawtypes")
	public static void LogE(Class clase, String optionalParams)
	{
		Log.e("MoviChat", "Error in " + clase.toString() + (optionalParams != null ?  ":" + optionalParams : ""));
	}
	
	public static int dpToPixels(int dp, float scale)
	{
		return (int)(dp*scale+0.5f);
		
	}
	
	public static HashMap<String, String> replaceLiterals(HashMap<String, String> lista, Context context)
	{
		Set<String> keySet =  lista.keySet();		
		Iterator<String> it = keySet.iterator();

		while(it.hasNext())
		{
			String key = it.next();
			String value = lista.get(key);
		
			if(value.indexOf("[[SKU]]") != -1)
			{
				if(key.equalsIgnoreCase("fbImageSrc"))
					value = value.replace("[[SKU]]", getConfigParam("SKU", context).substring(0, getConfigParam("SKU", context).length()-2));
				else
					value = value.replace("[[SKU]]", getConfigParam("SKU", context));
			}
			if(value.indexOf("[[LANGUAGE]]") != -1)
				value = value.replace("[[LANGUAGE]]", getDeviceLanguage());
			if(value.indexOf("[[APPNAME]]") != -1 && lista.get("ApplicationName") != null)
				value = value.replace("[[APPNAME]]", lista.get("ApplicationName"));
			if(value.indexOf("[[APPSLOGAN]]") != -1 && lista.get("ApplicationSlogan") != null)
				value = value.replace("[[APPSLOGAN]]", lista.get("ApplicationSlogan"));
			if(value.indexOf("[[BUNDLEVERSION]]") != -1)
				value = value.replace("[[BUNDLEVERSION]]", getApplicationVersion(context));
			if(value.indexOf("[[UILANGUAGE]]") != -1)
				value = value.replace("[[UILANGUAGE]]", getUiLanguage());
			if(value.indexOf("[[UDID]]") != -1)
				value = value.replace("[[UDID]]", getDeviceId(context));
			if(value.indexOf("[[COUNTRYCODE]]") != -1)
				value = value.replace("[[COUNTRYCODE]]", getDeviceCountry());
			if(value.indexOf("[[APPADSCOLOR]]") != -1 && lista.get("appAdsColor") != null)
				value = value.replace("[[APPADSCOLOR]]", lista.get("appAdsColor"));
			if(value.indexOf("[[APPSTOREURL]]") != -1 && lista.get("appStoreUrl") != null)
				value = value.replace("[[APPSTOREURL]]", lista.get("appStoreUrl"));
			if(value.indexOf("[[MARKETPLACEURL]]") != -1 && lista.get("marketPlaceUrl") != null)
				value = value.replace("[[MARKETPLACEURL]]", lista.get("marketPlaceUrl"));
			
			lista.put(key, value);
		}
		return lista;
	}
}