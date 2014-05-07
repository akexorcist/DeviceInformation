package app.akeorcist.deviceinformation;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import app.akeorcist.deviceinformation.InfoManager.HardwareInfo;

public class FragmentHardware extends Fragment {	
	LinearLayout layoutBuild, layoutOS, layoutCPU, layoutGPU, layoutRam
			, layoutRom, layoutConnection, layoutLoading;
	ScrollView scrollHardware;
	LayoutInflater mInflater;
	GLSurfaceView glSurfaceView;
	
	public static FragmentHardware newInstance() {
		FragmentHardware fragment = new FragmentHardware();
		return fragment;
	}

	public FragmentHardware() { }

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hardware, container,
				false);
		mInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		scrollHardware = (ScrollView)rootView.findViewById(R.id.scrollHardware);
		scrollHardware.setVisibility(View.GONE);
		
		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		layoutBuild = (LinearLayout)rootView.findViewById(R.id.layoutBuild);
		layoutOS = (LinearLayout)rootView.findViewById(R.id.layoutOS);
		layoutCPU = (LinearLayout)rootView.findViewById(R.id.layoutCPU);
		layoutGPU = (LinearLayout)rootView.findViewById(R.id.layoutGPU);
		layoutRam = (LinearLayout)rootView.findViewById(R.id.layoutRam);
		layoutRom = (LinearLayout)rootView.findViewById(R.id.layoutRom);
		layoutConnection = (LinearLayout)rootView.findViewById(R.id.layoutConnection);

	    glSurfaceView = (GLSurfaceView)rootView.findViewById(R.id.glSurfaceView);
		
	    glSurfaceView.setRenderer(new ClearRenderer(layoutGPU, mInflater, getActivity()));
	    
	    new Handler().postDelayed(new Runnable() {
	    	public void run() {
	    		try {
		    		final ArrayList<String> arrBuild = new ArrayList<String>();
		    		arrBuild.add("Board : " + Build.BOARD);
		    		arrBuild.add("Bootloader : " + Build.BOOTLOADER);
		    		arrBuild.add("Brand : " + Build.BRAND);
		    		arrBuild.add("Characteristic : " + HardwareInfo.getChar());
		    		arrBuild.add("CPU ABI : " + Build.CPU_ABI);
		    		arrBuild.add("CPU ABI 2 : " + Build.CPU_ABI2);
		    		arrBuild.add("Device : " + Build.DEVICE);
		    		arrBuild.add("Display : " + Build.DISPLAY);
		    		arrBuild.add("Fingerprint : " + Build.FINGERPRINT);
		    		arrBuild.add("Hardware : " + Build.HARDWARE);
		    		arrBuild.add("Host : " + Build.HOST);
		    		arrBuild.add("ID : " + Build.ID);
		    		arrBuild.add("Manufacturer : " + Build.MANUFACTURER);
		    		arrBuild.add("Model : " + Build.MODEL);
		    		arrBuild.add("Product : " + Build.PRODUCT);
		    		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) { 
		    			arrBuild.add("Radio : " + Build.RADIO);
		    		} else {
		    			String radio = Build.getRadioVersion();
		    			if(radio == null || radio.equals("")) 
		    				radio = "unknown";
		    			arrBuild.add("Radio : " + radio);
		    		}
		    		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) 
		    			arrBuild.add("Serial : " + Build.SERIAL);
		    		else 
		    			arrBuild.add("Serial : Unknown");
		    		
		    		arrBuild.add("Tags : " + Build.TAGS);
		    		arrBuild.add("Time : " + Build.TIME);
		    		arrBuild.add("Type : " + Build.TYPE);
		    		arrBuild.add("User : " + Build.USER);
		    		
		    		final ArrayList<String> arrOS = new ArrayList<String>();
		    		arrOS.add("Android Version : " + Build.VERSION.RELEASE);
		    		arrOS.add("Version Code : " + HardwareInfo.getVersionCode(Build.VERSION.SDK_INT));
		    		arrOS.add("API Version : " + Build.VERSION.SDK_INT);
		    		arrOS.add("Incremental : " + Build.VERSION.INCREMENTAL);
		    		arrOS.add("Codename : " + Build.VERSION.CODENAME);
	
		    		final String[] cpuInfo = HardwareInfo.getCpuInfo().split("\n");
		    				
	
		    		final ArrayList<String> arrRAM = new ArrayList<String>();
		    		arrRAM.add("Total Memory : " + HardwareInfo.getMemory());
		    		arrRAM.add("Heap Size : " + HardwareInfo.getHeapSize().trim());
		    		arrRAM.add("Heap Start Size : " + HardwareInfo.getHeapStartSize().trim());
		    		arrRAM.add("Heap Growth Limit : " + HardwareInfo.getHeapGrowthLimit().trim());
	
		    		final ArrayList<String> arrROM = new ArrayList<String>();
		    	    arrROM.add("Internal Storage : " + HardwareInfo.getTotalInternalStorage());
		    	    arrROM.add("External Storage : " + HardwareInfo.getTotalExternalStorage());
		    	    arrROM.add("SD Card Supported : " + HardwareInfo.isSDSupported(getActivity()));
	
		    	    final ArrayList<String> arrComm = new ArrayList<String>();
		    	    arrComm.add("Telephony : " + HardwareInfo.hasTelephony(getActivity()));
		    	    arrComm.add("Cellular : " + HardwareInfo.hasCellular(getActivity()));
		    	    arrComm.add("Bluetooth : " + HardwareInfo.hasBluetooth(getActivity()));
		    	    arrComm.add("WiFi : " + HardwareInfo.hasWiFi(getActivity()));
		    	    arrComm.add("WiFi Direct : " + HardwareInfo.hasWiFiDirect(getActivity()));
		    	    arrComm.add("USB OTG : " + HardwareInfo.hasOTG(getActivity()));
		    	    arrComm.add("USB Accessory : " + HardwareInfo.hasAOA(getActivity()));
		    	    arrComm.add("NFC : " + HardwareInfo.hasNFC(getActivity()));
		    	    arrComm.add("NFC HCE : " + HardwareInfo.hasNFCHost(getActivity()));
	
		    	    getActivity().runOnUiThread(new Runnable() {
		    	    	public void run() {
		    	    		for(int i = 0 ; i < arrBuild.size() ; i++) {
		    	    			if(arrBuild.get(i).length() > 0) {
		    	    				View view = mInflater.inflate(R.layout.listview_row_hardware, null, false);
		    	    				TextView tv = (TextView)view.findViewById(R.id.textHardwareData);
		    	    				tv.setText(arrBuild.get(i));
		    	    				layoutBuild.addView(view);
		    	    			}
		    	    		}
		    	    		
		    	    		for(int i = 0 ; i < arrOS.size() ; i++) {
		    	    			if(arrOS.get(i).length() > 0) {
		    	    				View view = mInflater.inflate(R.layout.listview_row_hardware, null, false);
		    	    				TextView tv = (TextView)view.findViewById(R.id.textHardwareData);
		    	    				tv.setText(arrOS.get(i));
		    	    				layoutOS.addView(view);
		    	    			}
		    	    		}
		    	    		
		    	    		for(int i = 0 ; i < cpuInfo.length ; i++) {
		    	    			if(cpuInfo[i].length() > 0) {
		    	    				View view = mInflater.inflate(R.layout.listview_row_hardware, null, false);
		    	    				TextView tv = (TextView)view.findViewById(R.id.textHardwareData);
		    	    				tv.setText(cpuInfo[i]);
		    	    				layoutCPU.addView(view);
		    	    			}
		    	    		}
		    	    		
		    	    		for(int i = 0 ; i < arrRAM.size() ; i++) {
		    	    			View view = mInflater.inflate(R.layout.listview_row_hardware, null, false);
		    	    			TextView tv = (TextView)view.findViewById(R.id.textHardwareData);
		    	    			tv.setText(arrRAM.get(i));
		    	    			layoutRam.addView(view);
		    	    		}
		    	    		
		    	    		for(int i = 0 ; i < arrROM.size() ; i++) {
		    	    			View view = mInflater.inflate(R.layout.listview_row_hardware, null, false);
		    	    			TextView tv = (TextView)view.findViewById(R.id.textHardwareData);
		    	    			tv.setText(arrROM.get(i));
		    	    			layoutRom.addView(view);
		    	    		}
		    	    		
		    	    		for(int i = 0 ; i < arrComm.size() ; i++) {
		    	    			View view = mInflater.inflate(R.layout.listview_row_hardware, null, false);
		    	    			TextView tv = (TextView)view.findViewById(R.id.textHardwareData);
		    	    			tv.setText(arrComm.get(i));
		    	    			layoutConnection.addView(view);
		    	    		}
		    	    		
		    	    		layoutLoading.setVisibility(View.GONE);
		    	    		scrollHardware.setVisibility(View.VISIBLE);
		    	    	}
		    	    });
				} catch(NullPointerException e) {
					e.printStackTrace();
				}
	    	}
	    }, 1000);
	    
		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	public static class ClearRenderer implements GLSurfaceView.Renderer {
		LinearLayout layout;
		ArrayList<String> arr;
		LayoutInflater inflater;
		Activity activity;
		
		public ClearRenderer(LinearLayout layout, LayoutInflater inflater, Activity activity) {
			this.activity = activity;
			this.inflater = inflater;
			this.layout = layout;
			this.arr = new ArrayList<String>();
		}
		
	    public void onSurfaceCreated(GL10 gl, EGLConfig config) {	    	
	    	arr.add("Name : " + gl.glGetString(GL10.GL_RENDERER));
	    	arr.add("Vendor : " + gl.glGetString(GL10.GL_VENDOR));
	    	arr.add("GL Version : " + gl.glGetString(GL10.GL_VERSION));
	    	arr.add("GL Extensions : \n\n• " + dividing(gl.glGetString(GL10.GL_EXTENSIONS)));

		    for(int i = 0 ; i < arr.size() ; i++) {
				if(arr.get(i).length() > 0) {
					final int j = i;
					activity.runOnUiThread(new Runnable() {
						public void run() {
							View view = inflater.inflate(R.layout.listview_row_hardware, null, false);
							TextView tv = (TextView)view.findViewById(R.id.textHardwareData);
							tv.setText(arr.get(j));
							layout.addView(view);
						}
					});
				}
			}
	    }

	    public void onSurfaceChanged(GL10 gl, int w, int h) { }

	    public void onDrawFrame(GL10 gl) {
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);         
	    }
	    
	    public String dividing(String str) {
	    	if(str.charAt(str.length() - 1) == '\n' 
	    			|| str.charAt(str.length() - 1) == ' ') {
	    		str = str.substring(0, str.length() - 1);
	    	}
	    	String s = str.replace(" ", "\n• ");
	    	return s;
	    }
	}
}
