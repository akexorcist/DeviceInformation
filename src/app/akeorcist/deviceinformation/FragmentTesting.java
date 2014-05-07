package app.akeorcist.deviceinformation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import app.akeorcist.deviceinformation.InfoManager.FeatureInfo;
import app.akeorcist.deviceinformation.InfoManager.HardwareInfo;
import app.akeorcist.deviceinformation.InfoManager.ScreenInfo;
import app.akeorcist.deviceinformation.InfoManager.SensorInfo;
import app.akeorcist.deviceinformation.InfoManager.CameraInfo;

public class FragmentTesting extends Fragment {	
	private final String PREFERENCE_NAME = "Preference";
	
	TextView textTesting;
	Button buttonRunTesting;
	
	GLSurfaceView glSurfaceView = null;
    
	public static FragmentTesting newInstance() {
		FragmentTesting fragment = new FragmentTesting();
		return fragment;
	}

	public FragmentTesting() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_testing, container,
				false);
		
		textTesting = (TextView)rootView.findViewById(R.id.textTesting);
				
		buttonRunTesting = (Button)rootView.findViewById(R.id.buttonRunTesting);
		buttonRunTesting.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
		        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		        dialog.setContentView(R.layout.dialog_loading);
		        dialog.setCancelable(false);
		        
		        final TextView text1 = (TextView)dialog.findViewById(R.id.text1);
		        glSurfaceView = (GLSurfaceView)dialog.findViewById(R.id.glSurfaceView);
			    glSurfaceView.setRenderer(new ClearRenderer(getActivity()));
		        
		        new Handler().postDelayed(new Runnable() {
					public void run() {
						new TestingTask(text1, dialog).execute(getActivity());
					}
		        }, 500);
				
				dialog.show();
			}
		});
        
		SharedPreferences settings = getActivity().getSharedPreferences(PREFERENCE_NAME, 0);
	    boolean hasRunTesting = settings.getBoolean("hasRunTesting", false);
	    if(hasRunTesting) {
	    	textTesting.setText(R.string.testing_thanks);
	    	buttonRunTesting.setVisibility(View.GONE);
	    }
		
		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	
	public class TestingTask extends AsyncTask<Activity, String, String> {
		TextView tv = null;
		Dialog dialog = null;
		boolean hasSent = false;
		
		public TestingTask(TextView tv, Dialog dialog) {
			this.tv = tv;
			this.dialog = dialog;
		}
		
		@SuppressWarnings("deprecation")
		@SuppressLint("NewApi")
		protected String doInBackground(Activity... activity) {  
			ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnected()) {
		        try {
		    		onProgressUpdate("Check internet connection");
		            URL pingUrl = new URL("http://www.google.com");
		            HttpURLConnection urlc = (HttpURLConnection)pingUrl.openConnection();
		            urlc.setConnectTimeout(3000);
		            urlc.connect();
		            if (urlc.getResponseCode() == 200) {
			    		onProgressUpdate("Build Information");
			    		
						String strWrite = "";
			
						strWrite += "Board : " + Build.BOARD + "\n";
						strWrite += "Bootloader : " + Build.BOOTLOADER + "\n";
						strWrite += "Brand : " + Build.BRAND + "\n";
						strWrite += "Characteristic : " + HardwareInfo.getChar() + "\n";
						strWrite += "CPU ABI : " + Build.CPU_ABI + "\n";
						strWrite += "CPU ABI 2 : " + Build.CPU_ABI2 + "\n";
						strWrite += "Device : " + Build.DEVICE + "\n";
						strWrite += "Display : " + Build.DISPLAY + "\n";
						strWrite += "Fingerprint : " + Build.FINGERPRINT + "\n";
						strWrite += "Hardware : " + Build.HARDWARE + "\n";
						strWrite += "Host : " + Build.HOST + "\n";
						strWrite += "ID : " + Build.ID + "\n";
						strWrite += "Manufacturer : " + Build.MANUFACTURER + "\n";
						strWrite += "Model : " + Build.MODEL + "\n";
						strWrite += "Product : " + Build.PRODUCT + "\n";
						if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) { 
			    			strWrite += "Radio : " + Build.RADIO + "\n";
			    		} else {
			    			String radio = Build.getRadioVersion();
			    			if(radio == null || radio.equals("")) 
			    				radio = "unknown";
			    			strWrite += "Radio : " + radio + "\n";
			    		}
			    		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) 
			    			strWrite += "Serial : " + Build.SERIAL + "\n";
			    		else 
			    			strWrite += "Serial : Unknown\n";
			    			
						strWrite += "Tags : " + Build.TAGS + "\n";
						strWrite += "Time : " + Build.TIME + "\n";
						strWrite += "Type : " + Build.TYPE + "\n";
						strWrite += "User : " + Build.USER;   		
			    		
			    		writeToFile(strWrite, "build_info.txt",  activity[0]);
			    		
			    		/*******************************************************/
			
			    		onProgressUpdate("Android Information");
			    		
			    		strWrite = "";
			
						strWrite += "Android Version : " + Build.VERSION.RELEASE + "\n";
						strWrite += "Version Code : " + HardwareInfo.getVersionCode(Build.VERSION.SDK_INT) + "\n";
						strWrite += "API Version : " + Build.VERSION.SDK_INT + "\n";
						strWrite += "Incremental : " + Build.VERSION.INCREMENTAL + "\n";
						strWrite += "Codename : " + Build.VERSION.CODENAME;
			    		
			    		writeToFile(strWrite, "android_info.txt",  activity[0]);
			    		
			    		/*******************************************************/
			
			    		onProgressUpdate("CPU Information");
			    		
			    		strWrite = "";
			    		final String[] cpuInfo = HardwareInfo.getCpuInfo().split("\n");
			    		
			    		for(int i = 0 ; i < cpuInfo.length ; i++) {
			    			if(cpuInfo[i].length() > 0) {
			    				strWrite += cpuInfo[i] +  "\n";
			    			}
			    		}
			    		
			    		writeToFile(strWrite, "cpu_info.txt",  activity[0]);
			
			    		/*******************************************************/
			
			    		onProgressUpdate("Memory Information");
			    		
			    		strWrite = "";
			
						strWrite += "Total Memory : " + HardwareInfo.getMemory() + "\n";
						strWrite += "Heap Size : " + HardwareInfo.getHeapSize().trim() + "\n";
						strWrite += "Heap Start Size : " + HardwareInfo.getHeapStartSize().trim() + "\n";
						strWrite += "Heap Growth Limit : " + HardwareInfo.getHeapGrowthLimit().trim();
			    		
			    		writeToFile(strWrite, "memory_info.txt",  activity[0]);
			
			    		/*******************************************************/
			
			    		onProgressUpdate("Storage Information");
			    		
			    		strWrite = "";
			
						strWrite += "Internal Storage : " + HardwareInfo.getTotalInternalStorage() + "\n";
						strWrite += "External Storage : " + HardwareInfo.getTotalExternalStorage() + "\n";
						strWrite += "SD Card Supported : " + HardwareInfo.isSDSupported(getActivity());
			
			    		writeToFile(strWrite, "storage_info.txt",  activity[0]);
			
			    		/*******************************************************/
			
			    		onProgressUpdate("Connection Information");
			    		
			    		strWrite = "";
			
						strWrite += "Telephony : " + HardwareInfo.hasTelephony(getActivity()) + "\n";
						strWrite += "Cellular : " + HardwareInfo.hasCellular(getActivity()) + "\n";
						strWrite += "Bluetooth : " + HardwareInfo.hasBluetooth(getActivity()) + "\n";
						strWrite += "WiFi : " + HardwareInfo.hasWiFi(getActivity()) + "\n";
						strWrite += "WiFi Direct : " + HardwareInfo.hasWiFiDirect(getActivity()) + "\n";
						strWrite += "USB OTG : " + HardwareInfo.hasOTG(getActivity()) + "\n";
						strWrite += "USB Accessory : " + HardwareInfo.hasAOA(getActivity()) + "\n";
						strWrite += "NFC : " + HardwareInfo.hasNFC(getActivity()) + "\n";
						strWrite += "NFC HCE : " + HardwareInfo.hasNFCHost(getActivity());
			
			    		writeToFile(strWrite, "connection_info.txt",  activity[0]);    	
			
			    		/*******************************************************/
			
			    		onProgressUpdate("Sensor Information");
			    		
			    		strWrite = "";
			    		
			    		SensorManager mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
			    		List<Sensor> listSensor = mSensorManager.getSensorList(Sensor.TYPE_ALL);
			    		for(int i = 0 ; i < listSensor.size() ; i++) {
			    			Sensor sensor = listSensor.get(i);
			    			strWrite += "Name : " + SensorInfo.getName(sensor) + "\n";
			    			strWrite += "Vendor : " + SensorInfo.getVendor(sensor) + "\n";
			    			strWrite += "Type : " + SensorInfo.getType(sensor) + "\n";
			    			strWrite += "Version : " + SensorInfo.getVersion(sensor) + "\n";
			    			strWrite += "Power : " + SensorInfo.getPower(sensor) + "\n";
			    			strWrite += "Max Range : " + SensorInfo.getMaximumRange(sensor) + "\n";
			    			strWrite += "Resolution : " + SensorInfo.getResolution(sensor) + "\n";
			    			strWrite += "Min Delay : " + SensorInfo.getMinDelay(sensor) + "\n";
			    			strWrite += "FIFO Reserved Event : " + SensorInfo.getFifoReservedEventCount(sensor) + "\n";
			    			strWrite += "FIFO Max Event : " + SensorInfo.getFifoMaxEventCount(sensor);
			    			if(i < listSensor.size() - 1)
			    				strWrite += "\n***********\n";
			    		}
			
			    		writeToFile(strWrite, "sensor_info.txt",  activity[0]);    	
			
			    		/*******************************************************/
			
			    		onProgressUpdate("Screen Information");
			    		
			    		strWrite = "";
			    		strWrite += "Resolution (PX) : " + ScreenInfo.getResolutionPX(getActivity()) + "\n";
			    		strWrite += "Resolution (DP) : " + ScreenInfo.getResolutionDP(getActivity()) + "\n";
			    		strWrite += "DPI (X) : " + ScreenInfo.getDpiX(getActivity()) + "\n";
			    		strWrite += "DPI (Y) : " + ScreenInfo.getDpiY(getActivity()) + "\n";
			    		strWrite += "DPI : " + ScreenInfo.getDpi(getActivity()) + "\n";
			    		strWrite += "Size : " + ScreenInfo.getScreenSize(getActivity()) + "\n";
			    		strWrite += "Density : " + ScreenInfo.getDensity(getActivity()) + "\n";
			    		strWrite += "Multitouch : " + ScreenInfo.getMultitouch(getActivity());
			
			    		writeToFile(strWrite, "screen_info.txt",  activity[0]);  
			
			    		/*******************************************************/  
			
			    		onProgressUpdate("Camera Information");
			
			    		strWrite = "";
			    		
			    		int cameraCount = 0;
			    		
			    		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
							cameraCount = Camera.getNumberOfCameras();
						} else {
							Camera cam = Camera.open();
							if(cam == null) 
								cameraCount = 0;
							else 
								cameraCount = 1;
						}	
			    		for(int i = 0 ; i < cameraCount ; i++) {			
							if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
								Camera.CameraInfo ci = new Camera.CameraInfo();
								Camera.getCameraInfo(i, ci);
								Camera mCamera = Camera.open(i);
								Camera.Parameters params = mCamera.getParameters();
								mCamera.release();
								strWrite += "ID : " + i + "\n";
								strWrite += "Antibanding : " + CameraInfo.getSupportedAntibanding(params) + "\n";
								strWrite += "Shutter Sound : " + CameraInfo.canDisableShutterSound(ci) + "\n";
								strWrite += "Color Effect : " + CameraInfo.getSupportedColorEffects(params) + "\n";
								strWrite += "Facing : " + CameraInfo.getCameraFacing(ci) + "\n";
								strWrite += "Flash Mode : " + CameraInfo.getSupportedFlashModes(params) + "\n";
								strWrite += "Focus Mode : " + CameraInfo.getSupportedFocusModes(params) + "\n";
								strWrite += "JPEG Thumbnail Size : " + CameraInfo.getSupportedJpegThumbnailSizes(params) + "\n";
								strWrite += "Image Orientation : " + CameraInfo.getImageOrientation(ci) + "\n";
								strWrite += "Picture Format : " + CameraInfo.getSupportedPictureFormats(params) + "\n";
								strWrite += "Preview Format : " + CameraInfo.getSupportedPreviewFormats(params) + "\n";
								strWrite += "Preview Frame Rate : " + CameraInfo.getSupportedPreviewFrameRates(params) + "\n";
								strWrite += "Picture Size : " + CameraInfo.getSupportedPictureSizes(params) + "\n";
								strWrite += "Preview Size : " + CameraInfo.getSupportedPreviewSizes(params) + "\n";
								strWrite += "Preview FPS Range : " + CameraInfo.getSupportedPreviewFpsRange(params) + "\n";
								strWrite += "Scene Mode : " + CameraInfo.getSupportedSceneModes(params) + "\n";
								strWrite += "Video Quality : " + CameraInfo.getQualityProfile(params, i) + "\n";
								strWrite += "Time Lapse Quality : " + CameraInfo.getQualityTimeLapseProfile(params, i) + "\n";
								strWrite += "Video Size : " + CameraInfo.getSupportedVideoSizes(params) + "\n";
								strWrite += "White Balance : " + CameraInfo.getSupportedWhiteBalance(params);
							} else {
								Camera mCamera = Camera.open();
								Camera.Parameters params = mCamera.getParameters();
								mCamera.release();
								strWrite += "ID : " + i + "\n";
								strWrite += "Antibanding : " + CameraInfo.getSupportedAntibanding(params) + "\n";
								strWrite += "Shutter Sound : null\n";
								strWrite += "Color Effect : " + CameraInfo.getSupportedColorEffects(params) + "\n";
								strWrite += "Facing : null\n";
								strWrite += "Flash Mode : " + CameraInfo.getSupportedFlashModes(params) + "\n";
								strWrite += "Focus Mode : " + CameraInfo.getSupportedFocusModes(params) + "\n";
								strWrite += "JPEG Thumbnail Size : " + CameraInfo.getSupportedJpegThumbnailSizes(params) + "\n";
								strWrite += "Image Orientation : null\n";
								strWrite += "Picture Format : " + CameraInfo.getSupportedPictureFormats(params) + "\n";
								strWrite += "Preview Format : " + CameraInfo.getSupportedPreviewFormats(params) + "\n";
								strWrite += "Preview Frame Rate : " + CameraInfo.getSupportedPreviewFrameRates(params) + "\n";
								strWrite += "Picture Size : " + CameraInfo.getSupportedPictureSizes(params) + "\n";
								strWrite += "Preview Size : " + CameraInfo.getSupportedPreviewSizes(params) + "\n";
								strWrite += "Preview FPS Range : " + CameraInfo.getSupportedPreviewFpsRange(params) + "\n";
								strWrite += "Scene Mode : " + CameraInfo.getSupportedSceneModes(params) + "\n";
								strWrite += "Video Quality : " + CameraInfo.getQualityProfile(params, i) + "\n";
								strWrite += "Time Lapse Quality : " + CameraInfo.getQualityTimeLapseProfile(params, i) + "\n";
								strWrite += "Video Size : " + CameraInfo.getSupportedVideoSizes(params) + "\n";
								strWrite += "White Balance : " + CameraInfo.getSupportedWhiteBalance(params);
							}
			    			if(i < cameraCount - 1)
			    				strWrite += "\n***********\n";
			    		}
			
			    		writeToFile(strWrite, "camera_info.txt",  activity[0]);  
			
			    		/*******************************************************/  
			
			    		onProgressUpdate("Feature Information");
			    		
			    		strWrite = "";
						
			    		strWrite += "App Widget : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_APP_WIDGETS, Build.VERSION_CODES.JELLY_BEAN_MR2) + "\n";
			    		strWrite += "Audio Low Latency : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_AUDIO_LOW_LATENCY, Build.VERSION_CODES.GINGERBREAD) + "\n";
			    		strWrite += "Bluetooth : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_BLUETOOTH, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "Bluetooth Low Energy : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_BLUETOOTH_LE, Build.VERSION_CODES.JELLY_BEAN_MR2) + "\n";
			    		strWrite += "Camera : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Camera Any : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_ANY, Build.VERSION_CODES.JELLY_BEAN_MR1) + "\n";
			    		strWrite += "Camera Autofocus : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_AUTOFOCUS, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Camera Flash : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_FLASH, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Camera Front : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_FRONT, Build.VERSION_CODES.GINGERBREAD) + "\n";
			    		strWrite += "Consumer IR : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CONSUMER_IR, Build.VERSION_CODES.KITKAT) + "\n";
			    		strWrite += "Device Admin : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_DEVICE_ADMIN, Build.VERSION_CODES.KITKAT) + "\n";
			    		strWrite += "Faketouch : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH, Build.VERSION_CODES.HONEYCOMB) + "\n";
			    		strWrite += "Faketouch Multitouch Distinct : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT, Build.VERSION_CODES.HONEYCOMB_MR2) + "\n";
			    		strWrite += "Faketouch Multitouch Jazzhand : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.HONEYCOMB_MR2) + "\n";
			    		strWrite += "Home Screen : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_HOME_SCREEN, Build.VERSION_CODES.JELLY_BEAN_MR2) + "\n";
			    		strWrite += "Input Methods : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_INPUT_METHODS, Build.VERSION_CODES.JELLY_BEAN_MR2) + "\n";
			    		strWrite += "Live Wallpaper : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LIVE_WALLPAPER, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Location : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "Location GPS : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION_GPS, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "Location Network : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION_NETWORK, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "Microphone : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_MICROPHONE, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "NFC : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_NFC, Build.VERSION_CODES.GINGERBREAD) + "\n";
			    		strWrite += "NFC Host Card Emulation : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_NFC_HOST_CARD_EMULATION, Build.VERSION_CODES.KITKAT) + "\n";
			    		strWrite += "Screen Landscape : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SCREEN_LANDSCAPE, Build.VERSION_CODES.HONEYCOMB_MR2) + "\n";
			    		strWrite += "Screen Portrait : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SCREEN_PORTRAIT, Build.VERSION_CODES.HONEYCOMB_MR2) + "\n";
			    		strWrite += "Sensor Accelerometer : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_ACCELEROMETER, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "Sensor Barometer : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_BAROMETER, Build.VERSION_CODES.GINGERBREAD) + "\n";
			    		strWrite += "Sensor Compass : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_COMPASS, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "Sensor Gyroscope : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_GYROSCOPE, Build.VERSION_CODES.GINGERBREAD) + "\n";
			    		strWrite += "Sensor Light : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_LIGHT, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Sensor Proximity : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_PROXIMITY, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Sensor Step Counter : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_STEP_COUNTER, Build.VERSION_CODES.KITKAT) + "\n";
			    		strWrite += "Sensor Step Detector : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_STEP_DETECTOR, Build.VERSION_CODES.KITKAT) + "\n";
			    		strWrite += "SIP : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SIP, Build.VERSION_CODES.GINGERBREAD) + "\n";
			    		strWrite += "SIP VOIP : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SIP_VOIP, Build.VERSION_CODES.GINGERBREAD) + "\n";
			    		strWrite += "Telephony : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Telephony CDMA : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY_CDMA, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Telephony GSM : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY_GSM, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Television : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEVISION, Build.VERSION_CODES.JELLY_BEAN) + "\n";
			    		strWrite += "Touchscreen : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "Touchscreen Multitouch : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Touchscreen Multitouch Distinct : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT, Build.VERSION_CODES.ECLAIR_MR1) + "\n";
			    		strWrite += "Touchscreen Multitouch Jazzhand : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "USB Accessory : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_USB_ACCESSORY, Build.VERSION_CODES.HONEYCOMB_MR1) + "\n";
			    		strWrite += "USB Host : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_USB_HOST, Build.VERSION_CODES.HONEYCOMB_MR1) + "\n";
			    		strWrite += "WiFi : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_WIFI, Build.VERSION_CODES.FROYO) + "\n";
			    		strWrite += "WiFi Direct : " + FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_WIFI_DIRECT, Build.VERSION_CODES.ICE_CREAM_SANDWICH);
			
			    		writeToFile(strWrite, "feature_info.txt",  activity[0]);  
			
			    		/*******************************************************/  
			
			    		onProgressUpdate("Get ID from server");
			    		
			    		strWrite = getDbId();
			
			    		/*******************************************************/  
			    		
			    		ArrayList<String> arr = readFile("android_info.txt");
			    		
			    		String url = "http://myandroidserver.url.ph/android/set_android.php";
			    		
			    		List<NameValuePair> params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("version", arr.get(0)));
			            params.add(new BasicNameValuePair("code", arr.get(1)));
			            params.add(new BasicNameValuePair("api", arr.get(2)));
			            params.add(new BasicNameValuePair("incremental", arr.get(3)));
			            params.add(new BasicNameValuePair("codename", arr.get(4)));
			
			    		onProgressUpdate("Send android info");
			    		
			    		getHttpPost(url, params);
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("build_info.txt");
			    		
			    		url = "http://myandroidserver.url.ph/android/set_build.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("board", arr.get(0)));
			            params.add(new BasicNameValuePair("brand", arr.get(1)));
			            params.add(new BasicNameValuePair("characteristic", arr.get(2)));
			            params.add(new BasicNameValuePair("cpu_abi", arr.get(3)));
			            params.add(new BasicNameValuePair("cpu_abi2", arr.get(4)));
			            params.add(new BasicNameValuePair("device", arr.get(5)));
			            params.add(new BasicNameValuePair("display", arr.get(6)));
			            params.add(new BasicNameValuePair("fingerprint", arr.get(7)));
			            params.add(new BasicNameValuePair("hardware", arr.get(8)));
			            params.add(new BasicNameValuePair("host", arr.get(9)));
			            params.add(new BasicNameValuePair("id", arr.get(10)));
			            params.add(new BasicNameValuePair("manufacturer", arr.get(11)));
			            params.add(new BasicNameValuePair("model", arr.get(12)));
			            params.add(new BasicNameValuePair("product", arr.get(13)));
			            params.add(new BasicNameValuePair("radio", arr.get(14)));
			            params.add(new BasicNameValuePair("serial", arr.get(15)));
			            params.add(new BasicNameValuePair("tags", arr.get(16)));
			            params.add(new BasicNameValuePair("time", arr.get(17)));
			            params.add(new BasicNameValuePair("type", arr.get(18)));
			            params.add(new BasicNameValuePair("user", arr.get(19)));
			
			    		onProgressUpdate("Send build info");
			    		
			    		getHttpPost(url, params);
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("camera_info.txt");
			    		
			    		url = "http://myandroidserver.url.ph/android/set_camera.php";
				
				    	onProgressUpdate("Send camera info");
			    		
			    		for(int i = 0 ; i < arr.size() / 20 ; i++) {
				    		params = new ArrayList<NameValuePair>();
				            params.add(new BasicNameValuePair("_id", strWrite));
				            params.add(new BasicNameValuePair("camera_id", "" + i));
				            params.add(new BasicNameValuePair("antibanding", arr.get((i * 20) + 1)));
				            params.add(new BasicNameValuePair("shutter_sound", arr.get((i * 20) + 2)));
				            params.add(new BasicNameValuePair("color_effect", arr.get((i * 20) + 3)));
				            params.add(new BasicNameValuePair("facing", arr.get((i * 20) + 4)));
				            params.add(new BasicNameValuePair("flash_mode", arr.get((i * 20) + 5)));
				            params.add(new BasicNameValuePair("focus_mode", arr.get((i * 20) + 6)));
				            params.add(new BasicNameValuePair("jpeg_thumbnail", arr.get((i * 20) + 7)));
				            params.add(new BasicNameValuePair("image_orientation", arr.get((i * 20) + 8)));
				            params.add(new BasicNameValuePair("picture_format", arr.get((i * 20) + 9)));
				            params.add(new BasicNameValuePair("preview_format", arr.get((i * 20) + 10)));
				            params.add(new BasicNameValuePair("preview_framerate", arr.get((i * 20) + 11)));
				            params.add(new BasicNameValuePair("picture_size", arr.get((i * 20) + 12)));
				            params.add(new BasicNameValuePair("preview_size", arr.get((i * 20) + 13)));
				            params.add(new BasicNameValuePair("preview_fps_range", arr.get((i * 20) + 14)));
				            params.add(new BasicNameValuePair("scene_mode", arr.get((i * 20) + 15)));
				            params.add(new BasicNameValuePair("video_quality", arr.get((i * 20) + 16)));
				            params.add(new BasicNameValuePair("timelapse_quality", arr.get((i * 20) + 17)));
				            params.add(new BasicNameValuePair("video_size", arr.get((i * 20) + 18)));
				            params.add(new BasicNameValuePair("white_balance", arr.get((i * 20) + 19)));
				    		
				    		getHttpPost(url, params);
			    		}
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("screen_info.txt");
			    		
			    		url = "http://myandroidserver.url.ph/android/set_screen.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("resolution_px", arr.get(0)));
			            params.add(new BasicNameValuePair("resolution_dp", arr.get(1)));
			            params.add(new BasicNameValuePair("dpi_x", arr.get(2)));
			            params.add(new BasicNameValuePair("dpi_y", arr.get(3)));
			            params.add(new BasicNameValuePair("dpi", arr.get(4)));
			            params.add(new BasicNameValuePair("size", arr.get(5)));
			            params.add(new BasicNameValuePair("density", arr.get(6)));
			            params.add(new BasicNameValuePair("multitouch", arr.get(7)));
			
			    		onProgressUpdate("Send screen info");
			    		
			    		getHttpPost(url, params);
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("connection_info.txt");
			    		
			    		url = "http://myandroidserver.url.ph/android/set_connection.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("telephony", arr.get(0)));
			            params.add(new BasicNameValuePair("cellular", arr.get(1)));
			            params.add(new BasicNameValuePair("bluetooth", arr.get(2)));
			            params.add(new BasicNameValuePair("wifi", arr.get(3)));
			            params.add(new BasicNameValuePair("wifi_direct", arr.get(4)));
			            params.add(new BasicNameValuePair("usb_otg", arr.get(5)));
			            params.add(new BasicNameValuePair("usb_accessory", arr.get(6)));
			            params.add(new BasicNameValuePair("nfc", arr.get(7)));
			            params.add(new BasicNameValuePair("nfc_hce", arr.get(8)));
			
			    		onProgressUpdate("Send connection info");
			    		
			    		getHttpPost(url, params);
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("feature_info.txt");
			    		
			    		url = "http://myandroidserver.url.ph/android/set_feature.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("app_widget", arr.get(0)));
			            params.add(new BasicNameValuePair("audio_low_latency", arr.get(1)));
			            params.add(new BasicNameValuePair("bluetooth", arr.get(2)));
			            params.add(new BasicNameValuePair("bluetooth_low_energy", arr.get(3)));
			            params.add(new BasicNameValuePair("camera", arr.get(4)));
			            params.add(new BasicNameValuePair("camera_any", arr.get(5)));
			            params.add(new BasicNameValuePair("camera_autofocus", arr.get(6)));
			            params.add(new BasicNameValuePair("camera_flash", arr.get(7)));
			            params.add(new BasicNameValuePair("camera_front", arr.get(8)));
			            params.add(new BasicNameValuePair("consumer_ir", arr.get(9)));
			            params.add(new BasicNameValuePair("device_admin", arr.get(10)));
			            params.add(new BasicNameValuePair("faketouch", arr.get(11)));
			            params.add(new BasicNameValuePair("faketouch_multitouch_distinct", arr.get(12)));
			            params.add(new BasicNameValuePair("faketouch_multitouch_jazzhand", arr.get(13)));
			            params.add(new BasicNameValuePair("home_screen", arr.get(14)));
			            params.add(new BasicNameValuePair("input_methods", arr.get(15)));
			            params.add(new BasicNameValuePair("live_wallpaper", arr.get(16)));
			            params.add(new BasicNameValuePair("location", arr.get(17)));
			            params.add(new BasicNameValuePair("location_gps", arr.get(18)));
			            params.add(new BasicNameValuePair("location_network", arr.get(19)));
			            params.add(new BasicNameValuePair("microphone", arr.get(20)));
			            params.add(new BasicNameValuePair("nfc", arr.get(21)));
			            params.add(new BasicNameValuePair("nfc_host_card_emulation", arr.get(22)));
			            params.add(new BasicNameValuePair("screen_landscape", arr.get(23)));
			            params.add(new BasicNameValuePair("screen_portrait", arr.get(24)));
			            params.add(new BasicNameValuePair("sensor_accelerometer", arr.get(25)));
			            params.add(new BasicNameValuePair("sensor_barometer", arr.get(26)));
			            params.add(new BasicNameValuePair("sensor_compass", arr.get(27)));
			            params.add(new BasicNameValuePair("sensor_gyroscope", arr.get(28)));
			            params.add(new BasicNameValuePair("sensor_light", arr.get(29)));
			            params.add(new BasicNameValuePair("sensor_proximity", arr.get(30)));
			            params.add(new BasicNameValuePair("sensor_step_counter", arr.get(31)));
			            params.add(new BasicNameValuePair("sensor_step_detector", arr.get(32)));
			            params.add(new BasicNameValuePair("sip", arr.get(33)));
			            params.add(new BasicNameValuePair("sip_voip", arr.get(34)));
			            params.add(new BasicNameValuePair("telephony", arr.get(35)));
			            params.add(new BasicNameValuePair("telephony_cdma", arr.get(36)));
			            params.add(new BasicNameValuePair("telephony_gsm", arr.get(37)));
			            params.add(new BasicNameValuePair("television", arr.get(38)));
			            params.add(new BasicNameValuePair("touchscreen", arr.get(39)));
			            params.add(new BasicNameValuePair("touchscreen_multitouch", arr.get(40)));
			            params.add(new BasicNameValuePair("touchscreen_multitouch_distinct", arr.get(41)));
			            params.add(new BasicNameValuePair("touchscreen_multitouch_jazzhand", arr.get(42)));
			            params.add(new BasicNameValuePair("usb_accessory", arr.get(43)));
			            params.add(new BasicNameValuePair("usb_host", arr.get(44)));
			            params.add(new BasicNameValuePair("wifi", arr.get(45)));
			            params.add(new BasicNameValuePair("wifi_direct", arr.get(46)));
			
			    		onProgressUpdate("Send feature info");
			    		
			    		getHttpPost(url, params);
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("gpu_info.txt");
			
			    		url = "http://myandroidserver.url.ph/android/set_gpu.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("name", arr.get(0)));
			            params.add(new BasicNameValuePair("vendor", arr.get(1)));
			            params.add(new BasicNameValuePair("gl_version", arr.get(2)));
			            params.add(new BasicNameValuePair("gl_extension", arr.get(3)));
			
			    		onProgressUpdate("Send gpu info");
			    		
			    		getHttpPost(url, params);

			    		/*******************************************************/  
			    		
			    		arr = readFile("storage_info.txt");
			
			    		url = "http://myandroidserver.url.ph/android/set_storage.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("internal", arr.get(0)));
			            params.add(new BasicNameValuePair("external", arr.get(1)));
			            params.add(new BasicNameValuePair("sdcard", arr.get(2)));
			
			    		onProgressUpdate("Send storage info");
			    		
			    		getHttpPost(url, params);
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("memory_info.txt");
			
			    		url = "http://myandroidserver.url.ph/android/set_memory.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("total_memory", arr.get(0)));
			            params.add(new BasicNameValuePair("heap_size", arr.get(1)));
			            params.add(new BasicNameValuePair("heap_start_size", arr.get(2)));
			            params.add(new BasicNameValuePair("heap_growth_limit", arr.get(3)));
			
			    		onProgressUpdate("Send memory info");
			    		
			    		getHttpPost(url, params);
			
			    		/*******************************************************/  
			    		
			    		arr = readFile("sensor_info.txt");
			    		
			    		url = "http://myandroidserver.url.ph/android/set_sensor.php";
			    		
			    		onProgressUpdate("Send sensor info");
			    		
			    		for(int i = 0 ; i < arr.size() / 10 ; i++) {
				    		params = new ArrayList<NameValuePair>();
				            params.add(new BasicNameValuePair("_id", strWrite));
				            params.add(new BasicNameValuePair("name", arr.get(i * 10)));
				            params.add(new BasicNameValuePair("vendor", arr.get((i * 10) + 1)));
				            params.add(new BasicNameValuePair("type", arr.get((i * 10) + 2)));
				            params.add(new BasicNameValuePair("version", arr.get((i * 10) + 3)));
				            params.add(new BasicNameValuePair("power", arr.get((i * 10) + 4)));
				            params.add(new BasicNameValuePair("max_range", arr.get((i * 10) + 5)));
				            params.add(new BasicNameValuePair("resolution", arr.get((i * 10) + 6)));
				            params.add(new BasicNameValuePair("min_delay", arr.get((i * 10) + 7)));
				            params.add(new BasicNameValuePair("fifo_reserved", arr.get((i * 10) + 8)));
				            params.add(new BasicNameValuePair("fifo_max", arr.get((i * 10) + 9)));
				
				    		getHttpPost(url, params);
			    		}
			
			    		/*******************************************************/  
			    		
			    		arr = readCPUFile("cpu_info.txt");
			
			    		url = "http://myandroidserver.url.ph/android/set_cpu.php";
			    		
			    		params = new ArrayList<NameValuePair>();
			            params.add(new BasicNameValuePair("_id", strWrite));
			            params.add(new BasicNameValuePair("processor", arr.get(0)));
			            params.add(new BasicNameValuePair("processor_0", arr.get(1)));
			            params.add(new BasicNameValuePair("bogo_mips_0", arr.get(2)));
			            params.add(new BasicNameValuePair("processor_1", arr.get(3)));
			            params.add(new BasicNameValuePair("bogo_mips_1", arr.get(4)));
			            params.add(new BasicNameValuePair("processor_2", arr.get(5)));
			            params.add(new BasicNameValuePair("bogo_mips_2", arr.get(6)));
			            params.add(new BasicNameValuePair("processor_3", arr.get(7)));
			            params.add(new BasicNameValuePair("bogo_mips_3", arr.get(8)));
			            params.add(new BasicNameValuePair("processor_4", arr.get(9)));
			            params.add(new BasicNameValuePair("bogo_mips_4", arr.get(10)));
			            params.add(new BasicNameValuePair("processor_5", arr.get(11)));
			            params.add(new BasicNameValuePair("bogo_mips_5", arr.get(12)));
			            params.add(new BasicNameValuePair("processor_6", arr.get(13)));
			            params.add(new BasicNameValuePair("bogo_mips_6", arr.get(14)));
			            params.add(new BasicNameValuePair("processor_7", arr.get(15)));
			            params.add(new BasicNameValuePair("bogo_mips_7", arr.get(16)));
			            params.add(new BasicNameValuePair("features", arr.get(17)));
			            params.add(new BasicNameValuePair("cpu_implementer", arr.get(18)));
			            params.add(new BasicNameValuePair("cpu_architecture", arr.get(19)));
			            params.add(new BasicNameValuePair("cpu_variant", arr.get(20)));
			            params.add(new BasicNameValuePair("cpu_part", arr.get(21)));
			            params.add(new BasicNameValuePair("cpu_revision", arr.get(22)));
			            params.add(new BasicNameValuePair("hardware", arr.get(23)));
			            params.add(new BasicNameValuePair("revision", arr.get(24)));
			            params.add(new BasicNameValuePair("serial", arr.get(25)));
			            params.add(new BasicNameValuePair("device", arr.get(26)));
			            params.add(new BasicNameValuePair("radio", arr.get(27)));
			
			    		onProgressUpdate("Send cpu info");
			    		
			    		getHttpPost(url, params);
			    		
			    		hasSent = true;
		            }
		        } catch (MalformedURLException e1) {
		            e1.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (IllegalArgumentException e) {
		            e.printStackTrace();
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }
		    }
			return null;
		}
		
		protected void onPostExecute(String result) {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if(hasSent) {
						tv.setText("Finish!!");
	
						SharedPreferences settings = getActivity().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = settings.edit();
						editor.putBoolean("hasRunTesting", true);
						editor.commit();
	
				    	textTesting.setText(R.string.testing_thanks);
				    	buttonRunTesting.setVisibility(View.GONE);
						
						new Handler().postDelayed(new Runnable() {
							public void run() {
								dialog.cancel();
							}
						}, 1000);
					} else {
						dialog.cancel();
						Toast.makeText(getActivity()
								, getResources().getString(R.string.network_unavilable)
								, Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		
		protected void onProgressUpdate(String... values) {
			final String str = values[0];
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					tv.setText(str);
				}
			});
	    }
	}
	
	public class ClearRenderer implements GLSurfaceView.Renderer {
		Activity activity;
		
		public ClearRenderer(Activity activity) {
			this.activity = activity;
		}
		
	    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    		String strWrite = "";
    		
    		strWrite += "Name : " + gl.glGetString(GL10.GL_RENDERER) + "\n";
    		strWrite += "Vendor : " + gl.glGetString(GL10.GL_VENDOR) + "\n";
    		strWrite += "GL Version : " + gl.glGetString(GL10.GL_VERSION) + "\n";
    		strWrite += "GL Extensions :" + gl.glGetString(GL10.GL_EXTENSIONS);
    		
    		writeToFile(strWrite, "gpu_info.txt", getActivity());   
	    }

	    public void onSurfaceChanged(GL10 gl, int w, int h) { }

	    public void onDrawFrame(GL10 gl) {
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);         
	    }
	}
	
	private void writeToFile(String data, String filename, Activity activity) {
		try {
			FileOutputStream fos = activity.openFileOutput(filename, Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> readFile(String filename) {
		ArrayList<String> arr_list = new ArrayList<String>();
		try {
			FileInputStream fis = getActivity().openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);
			String line;
			
			while ((line = bufferedReader.readLine()) != null) {
				if(!line.contains("***********")) {
					String[] str = line.split(" : ");
					try {
						arr_list.add(str[1]);
					} catch(ArrayIndexOutOfBoundsException e) {
						arr_list.add("unknown");
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arr_list;
	}
	
	public ArrayList<String> readCPUFile(String filename) {
		ArrayList<String> arr_list = new ArrayList<String>();
		try {
			FileInputStream fis = getActivity().openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bufferedReader = new BufferedReader(isr);

			arr_list.add(bufferedReader.readLine().split(":")[1].trim());	// Processor
			arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // processor 0
			arr_list.add(bufferedReader.readLine().split(":")[1].trim()); 
			String cpu = bufferedReader.readLine();	// get processor 1
			if(cpu.contains("processor") && cpu.contains("1")) {
				arr_list.add(cpu.split(":")[1].trim());	// processor 1
				arr_list.add(bufferedReader.readLine().split(": ")[1].trim());	// bogomips 1
				cpu = bufferedReader.readLine();	// get processor 2
				if(cpu.contains("processor") && cpu.contains("2")) {
					arr_list.add(cpu.split(":")[1].trim());	// processor 2
					arr_list.add(bufferedReader.readLine().split(": ")[1].trim());	// bogomips 2
					cpu = bufferedReader.readLine();	// get processor 3
					if(cpu.contains("processor") && cpu.contains("3")) {
						arr_list.add(cpu.split(":")[1].trim());	// processor 3
						arr_list.add(bufferedReader.readLine().split(": ")[1].trim());	// bogomips 3
						cpu = bufferedReader.readLine();	// get processor 4
						if(cpu.contains("processor") && cpu.contains("4")) {
							arr_list.add(cpu.split(":")[1].trim());	// processor 4
							arr_list.add(bufferedReader.readLine().split(": ")[1].trim());	// bogomips 4
							cpu = bufferedReader.readLine();	// get processor 5
							if(cpu.contains("processor") && cpu.contains("5")) {
								arr_list.add(cpu.split(":")[1].trim());	// processor 5
								arr_list.add(bufferedReader.readLine().split(": ")[1].trim());	// bogomips 5
								cpu = bufferedReader.readLine();	// get processor 6
								if(cpu.contains("processor") && cpu.contains("5")) {
									arr_list.add(cpu.split(":")[1].trim());	// processor 6
									arr_list.add(bufferedReader.readLine().split(": ")[1].trim());	// bogomips 6
									cpu = bufferedReader.readLine();	// get processor 7
									if(cpu.contains("processor") && cpu.contains("5")) {
										arr_list.add(cpu.split(":")[1].trim());	// processor 7
										arr_list.add(bufferedReader.readLine().split(": ")[1].trim());	// bogomips 7
									} else {
										arr_list.add("none");	// processor 7
										arr_list.add("none");
									}
									cpu = "";
								} else {
									arr_list.add("none");	// processor 6
									arr_list.add("none");
									arr_list.add("none");	// processor 7
									arr_list.add("none");
								}								
							} else {
								arr_list.add("none");	// processor 5
								arr_list.add("none");
								arr_list.add("none");	// processor 6
								arr_list.add("none");
								arr_list.add("none");	// processor 7
								arr_list.add("none");
							}
						} else {
							arr_list.add("none");	// processor 4
							arr_list.add("none");
							arr_list.add("none");	// processor 5
							arr_list.add("none");
							arr_list.add("none");	// processor 6
							arr_list.add("none");
							arr_list.add("none");	// processor 7
							arr_list.add("none");
						}
					} else {
						arr_list.add("none");	// processor 3
						arr_list.add("none");
						arr_list.add("none");	// processor 4
						arr_list.add("none");
						arr_list.add("none");	// processor 5
						arr_list.add("none");
						arr_list.add("none");	// processor 6
						arr_list.add("none");
						arr_list.add("none");	// processor 7
						arr_list.add("none");
					}
				} else {
					arr_list.add("none");	// processor 2
					arr_list.add("none");
					arr_list.add("none");	// processor 3
					arr_list.add("none");
					arr_list.add("none");	// processor 4
					arr_list.add("none");
					arr_list.add("none");	// processor 5
					arr_list.add("none");
					arr_list.add("none");	// processor 6
					arr_list.add("none");
					arr_list.add("none");	// processor 7
					arr_list.add("none");
				}
			} else {
				arr_list.add("none");	// processor 1
				arr_list.add("none");	// bogomips 1
				arr_list.add("none");	// processor 2
				arr_list.add("none");
				arr_list.add("none");	// processor 3
				arr_list.add("none");
				arr_list.add("none");	// processor 4
				arr_list.add("none");
				arr_list.add("none");	// processor 5
				arr_list.add("none");
				arr_list.add("none");	// processor 6
				arr_list.add("none");
				arr_list.add("none");	// processor 7
				arr_list.add("none");
			}
			if(cpu.equals("")) {
				arr_list.add(bufferedReader.readLine().split(":")[1].trim());	// Feature
			} else {
				arr_list.add(cpu.split(":")[1].trim());	// Feature
			}		
			
			arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // CPU Implementer
			arr_list.add(bufferedReader.readLine().split(":")[1].trim());	// CPU Architecture
			arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // CPU Variant
			arr_list.add(bufferedReader.readLine().split(":")[1].trim());	// CPU Part
			arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // CPU Revision
			arr_list.add(bufferedReader.readLine().split(":")[1].trim());	// Hardware
			arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // Revision

			try {
				arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // Serial
			} catch (NullPointerException e){
				arr_list.add("none");
			}

			try {
				arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // Device
			} catch (NullPointerException e){
				arr_list.add("none");
			}
			try {
				arr_list.add(bufferedReader.readLine().split(":")[1].trim()); // Radio
			} catch (NullPointerException e){
				arr_list.add("none");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arr_list;
	}
	
	public String getDbId() {
		String url = "http://myandroidserver.url.ph/android/get_id.php";
		return getHttpPost(url, null);
	}
	
	public String getHttpPost(String url, List<NameValuePair> params) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			if(params != null)
				httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Status OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}
}
