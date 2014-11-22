package app.akeorcist.deviceinformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.Camera.Parameters;
import android.location.LocationManager;
import android.media.CamcorderProfile;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.view.Display;

public class InfoManager {
	public InfoManager() { }
	
	public static class HardwareInfo {
		public static String getVersionCode(int sdkInt) {
			if(sdkInt == Build.VERSION_CODES.BASE) 
				return "Apple Pie";
			else if(sdkInt == Build.VERSION_CODES.BASE_1_1) 
				return "Banana Bread";
			else if(sdkInt == Build.VERSION_CODES.CUPCAKE) 
				return "Cupcake";
			else if(sdkInt == Build.VERSION_CODES.DONUT) 
				return "Donut";
			else if(sdkInt == Build.VERSION_CODES.ECLAIR) 
				return "Eclair";
			else if(sdkInt == Build.VERSION_CODES.ECLAIR_0_1) 
				return "Eclair";
			else if(sdkInt == Build.VERSION_CODES.ECLAIR_MR1) 
				return "Eclair";
			else if(sdkInt == Build.VERSION_CODES.FROYO) 
				return "Froyo";
			else if(sdkInt == Build.VERSION_CODES.CUPCAKE) 
				return "Cupcake";
			else if(sdkInt == Build.VERSION_CODES.GINGERBREAD) 
				return "Gingerbread";
			else if(sdkInt == Build.VERSION_CODES.GINGERBREAD_MR1) 
				return "Gingerbread";
			else if(sdkInt == Build.VERSION_CODES.HONEYCOMB) 
				return "Honeycomb";
			else if(sdkInt == Build.VERSION_CODES.HONEYCOMB_MR1) 
				return "Honeycomb";
			else if(sdkInt == Build.VERSION_CODES.HONEYCOMB_MR2) 
				return "Honeycomb";
			else if(sdkInt == Build.VERSION_CODES.ICE_CREAM_SANDWICH) 
				return "Ice Cream Sandwich";
			else if(sdkInt == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) 
				return "Ice Cream Sandwich";
			else if(sdkInt == Build.VERSION_CODES.JELLY_BEAN) 
				return "Jelly Bean";
			else if(sdkInt == Build.VERSION_CODES.JELLY_BEAN_MR1) 
				return "Jelly Bean";
			else if(sdkInt == Build.VERSION_CODES.JELLY_BEAN_MR2) 
				return "Jelly Bean";
			else if(sdkInt == Build.VERSION_CODES.KITKAT) 
				return "KitKat";
			return "unknown";
		}
				
		public static String getChar() {
			try {
		        Process proc = Runtime.getRuntime().exec("getprop ro.build.characteristics");
		        InputStream is = proc.getInputStream();
		        String size = getStringFromInputStream(is);
		        if(!size.equals("\n"))
		        	return size.trim();
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    return "unknown";
	    }
		
		public static String getHeapSize() {
			try {
		        Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapsize");
		        InputStream is = proc.getInputStream();
		        String size = getStringFromInputStream(is);
		        if(!size.equals("\n"))
		        	return size;
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    return "unknown";
	    }
		
		public static String getHeapStartSize() {
			try {
		        Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapstartsize");
		        InputStream is = proc.getInputStream();
		        String size = getStringFromInputStream(is);
		        if(!size.equals("\n"))
		        	return size;
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    return "unknown";
	    }
		
		public static String getHeapGrowthLimit() {
			try {
		        Process proc = Runtime.getRuntime().exec("getprop dalvik.vm.heapgrowthlimit");
		        InputStream is = proc.getInputStream();
		        String size = getStringFromInputStream(is);
		        if(!size.equals("\n"))
		        	return size;
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    return "unknown";
	    }

		private static String getStringFromInputStream(InputStream is) {
		    StringBuilder sb = new StringBuilder();
		    BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    String line = null;

		    try {
		        while((line = br.readLine()) != null) {
		            sb.append(line);
		            sb.append("\n");
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally {
		        if(br != null) {
		            try {
		                br.close();
		            } catch (IOException e) {
		    	        e.printStackTrace();
		            }
		        }
		    }  
		    return sb.toString();
		}
		
		public static String hasCellular(Activity activity) {
			ConnectivityManager manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			try {
				manager.getNetworkInfo(0).getState();
				return "yes";
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return "no";
		}
		
		public static String hasWiFi(Activity activity) {
			ConnectivityManager manager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			try {
				manager.getNetworkInfo(1).getState();
				return "yes";
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			return "no";
		}
		
		public static String hasGps(Activity activity) {
			if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
				return "yes";
			}
			return "no";
		}
		
		public static String hasTelephony(Activity activity) {
			if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
				return "yes";
			}
			return "no";
		}

		public static String hasNFC(Activity activity) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD 
					&& activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
				return "yes";
			}
			return "no";
		}
		
		public static String hasNFCHost(Activity activity) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT 
					&& activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)) {
				return "yes";
			}
			return "no";
		}
		
		public static String hasBluetooth(Activity activity) {
			TypedArray arr_name = activity.getResources().obtainTypedArray(R.array.device_not_supported_bluetooth);
			for(int i = 0 ; i < arr_name.length() ; i++) {
				if(Build.MODEL.equals(arr_name.getString(i))) {
					arr_name.recycle();
					return "no";
				}
			}
			arr_name.recycle();
			
			if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2  
						&& activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) 
					return "yes (Bluetooth 4.0)";
				return "yes";
			}
			return "no";
		}
		
		public static String hasWiFiDirect(Activity activity) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH 
					&& activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)) {
				return "yes";
			}
			return "no";
		}
		
		public static String hasOTG(Activity activity) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1 
					&& activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_HOST)) {
				return "yes";
			}
			return "no";
		}
		
		public static String hasAOA(Activity activity) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1 
					&& activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_ACCESSORY)) {
				return "yes";
			}
			return "no";
		}
		
		@SuppressLint("NewApi")
		public static String isSDSupported(Activity activity) {
			TypedArray arr_name = activity.getResources().obtainTypedArray(R.array.device_supported_sd);
			for(int i = 0 ; i < arr_name.length() ; i++) {
				if(Build.MODEL.equals(arr_name.getString(i))) {
					arr_name.recycle();
					return "yes";
				}
			}
			arr_name = activity.getResources().obtainTypedArray(R.array.device_not_supported_sd);
			for(int i = 0 ; i < arr_name.length() ; i++) {
				if(Build.MODEL.equals(arr_name.getString(i))) {
					arr_name.recycle();
					return "no";
				}
			}
			arr_name.recycle();
			
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				if(Environment.isExternalStorageRemovable())
					return "yes";
				else 
					return "no";
			} else {
				return "unknown";
			}
		}

		@SuppressWarnings("deprecation")
		public static String getTotalInternalStorage() {
			StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
			long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
			float megAvailable = bytesAvailable / 1048576000f;
			return String.format(Locale.getDefault(), "%.3f GB", megAvailable);
		}

		@SuppressWarnings("deprecation")
		public static String getTotalExternalStorage() {
			StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
			long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
			float megAvailable = bytesAvailable / 1048576000f;
			
			float internal = megAvailable;
			
			stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
			bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
			megAvailable = bytesAvailable / 1048576000f;
			
			float external = megAvailable;
			if(internal != external && Math.abs(internal - external) != 0.1 && external != 0) 
				return String.format(Locale.getDefault(), "%.3f GB", megAvailable);
			return "not available";
		}
		
		public static String getCpuInfo() {
		    try {
		        Process proc = Runtime.getRuntime().exec("cat /proc/cpuinfo");
		        InputStream is = proc.getInputStream();
		        return getStringFromInputStream(is);
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    return "unknown";
		}
		
		public static String getTotalMemory() {
			try {
		        Process proc = Runtime.getRuntime().exec("cat /proc/meminfo");
		        InputStream is = proc.getInputStream();
		        String[] listMemory = getStringFromInputStream(is).split("\n");
				for(int i = 0 ; i < listMemory.length ; i++) {
					if(listMemory[i].contains("MemTotal"))
						return listMemory[i];
				}
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		    return "unknown";
		}
		
		public static String getMemory() {
			String memory = getTotalMemory().replace("MemTotal:", "").replace(" ", "").replace("kB", "");
			if(!memory.equals("unknown")) {
				float mem = Float.parseFloat(memory) / 1000f;
				return String.format("%.3f", mem) + " MB";
			} else {
				return "unknown";
			}
		}
	}
	
	public static class SensorInfo {
		@SuppressLint("NewApi")
		public static String getSensorInfo(Sensor sensor) {
			String strWrite = "";
			strWrite += "Name : " + sensor.getName() + "\n";
			strWrite += "Vendor : " + sensor.getVendor() + "\n";
			strWrite += "Type : " + getType(sensor.getType()) + "\n";
			strWrite += "Version : " + sensor.getVersion() + "\n";
			strWrite += "Power : " + sensor.getPower() + "\n";
			strWrite += "Max Range : " + sensor.getMaximumRange() + "\n";
			strWrite += "Resolution : " + sensor.getResolution() + "\n";
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				strWrite += "Min Delay : " + sensor.getMinDelay() + "\n";
			} else {
				strWrite += "Min Delay : -\n";
			}
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				strWrite += "FIFO Reserved Event : " + sensor.getFifoReservedEventCount() + "\n";
				strWrite += "FIFO Max Event : " + sensor.getFifoMaxEventCount() + "\n";
			} else {

				strWrite += "FIFO Reserved Event : -\n";
				strWrite += "FIFO Max Event : -\n";
			}
			
			return strWrite;
		}
		
		public static String getName(Sensor sensor) {
			return sensor.getName();
		}
		
		public static String getVendor(Sensor sensor) {
			return sensor.getVendor();
		}
		
		public static String getType(Sensor sensor) {
			return getType(sensor.getType());
		}
		
		public static String getVersion(Sensor sensor) {
			return "" + sensor.getVersion();
		}
		
		public static String getPower(Sensor sensor) {
			return "" + sensor.getPower();
		}
		
		public static String getMaximumRange(Sensor sensor) {
			return "" + sensor.getMaximumRange();
		}
		
		public static String getResolution(Sensor sensor) {
			return "" + sensor.getResolution();
		}
		
		@SuppressLint("NewApi")
		public static String getMinDelay(Sensor sensor) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				return "" + sensor.getMinDelay();
			} else {
				return "-";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getFifoReservedEventCount(Sensor sensor) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				return "" + sensor.getFifoReservedEventCount();
			} else {
				return "-";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getFifoMaxEventCount(Sensor sensor) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				return "" + sensor.getFifoMaxEventCount();
			} else {
				return "-";
			}
		}
		
		@SuppressWarnings("deprecation")
		public static String getType(int type) {
			switch(type) {
			case Sensor.TYPE_ACCELEROMETER:
				return "Accelerometer";
			case Sensor.TYPE_AMBIENT_TEMPERATURE:
				return "Temperature";
			case Sensor.TYPE_GAME_ROTATION_VECTOR:
				return "Game Rotation Vector";
			case Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR:
				return "Geomagnetic Rotation Vector";
			case Sensor.TYPE_GRAVITY:
				return "Gravity";
			case Sensor.TYPE_GYROSCOPE:
				return "Gyroscope";
			case Sensor.TYPE_GYROSCOPE_UNCALIBRATED:
				return "Gyroscope Uncalibrated";
			case Sensor.TYPE_LIGHT:
				return "Light";
			case Sensor.TYPE_LINEAR_ACCELERATION:
				return "Linear Acceleration";
			case Sensor.TYPE_MAGNETIC_FIELD:
				return "Magnetic Field";
			case Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED:
				return "Magnetic Field Uncalibrated";
			case Sensor.TYPE_ORIENTATION:
				return "Orientation";
			case Sensor.TYPE_PRESSURE:
				return "Pressure";
			case Sensor.TYPE_PROXIMITY:
				return "Proximity";
			case Sensor.TYPE_RELATIVE_HUMIDITY:
				return "Humidity";
			case Sensor.TYPE_ROTATION_VECTOR:
				return "Rotation Vector";
			case Sensor.TYPE_SIGNIFICANT_MOTION:
				return "Signigicant Motion";
			case Sensor.TYPE_STEP_COUNTER:
				return "Step Counter";
			case Sensor.TYPE_STEP_DETECTOR:
				return "Step Detector";
			case Sensor.TYPE_TEMPERATURE:
				return "Temperature";
			}
			return "Unknown";
		}
	}
	
	public static class ScreenInfo {		
		public static String getMultitouch(Activity activity) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD 
					&& activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND)) {
				return "5+ Points";
			} else if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT)) {
				return "2-5 Points";
			} else if(activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH)) {
				return "2 Points";
			}
			return "Not supported";
		}
		
		public static String getDensity(Activity activity) {
			String model = Build.MODEL;
			if(model.contains("Ascend G300") || model.contains("Nexus S")
	        		|| model.contains("Lenovo A380")) {
		        return "High";
	        } else if(model.contains("i-mobile i-note WiFi 9")) {
		        return "Low";
	        }
			
	        DisplayMetrics dm = new DisplayMetrics();
	        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			
			if(dm.densityDpi == DisplayMetrics.DENSITY_LOW) 
	        	return "Low";
	        else if(dm.densityDpi == DisplayMetrics.DENSITY_MEDIUM) 
	        	return "Medium";
	        else if(dm.densityDpi == DisplayMetrics.DENSITY_TV) 
	        	return "TV";
	        else if(dm.densityDpi == DisplayMetrics.DENSITY_HIGH) 
	        	return "High";
	        else if(dm.densityDpi == DisplayMetrics.DENSITY_XHIGH) 
	        	return "Extra High";
	        else if(dm.densityDpi == DisplayMetrics.DENSITY_XXHIGH) 
	        	return "Extra Extra High";
	        else if(dm.densityDpi == DisplayMetrics.DENSITY_XXXHIGH) 
	        	return "Extra Extra Extra High";
			
			return "";
		}
		
		public static String getDpi(Activity activity) {
			String model = Build.MODEL;
			if(model.contains("Ascend G300") || model.contains("Nexus S")
	        		|| model.contains("Lenovo A380")) {
		        return "240 dpi";
	        } else if(model.contains("i-mobile i-note WiFi 9")) {
		        return "120 dpi";
	        }
			
	        DisplayMetrics dm = new DisplayMetrics();
	        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			
			return dm.densityDpi + "dpi";
		}
		
		@SuppressLint("NewApi")
		public static String getDpiX(Activity activity) {
			String model = Build.MODEL;
			if(model.contains("Ascend G300") || model.contains("Nexus S")
	        		|| model.contains("Lenovo A380")) {
		        return "233.24 dpi";
	        } else if(model.contains("i-mobile i-note WiFi 9")) {
		        return "103.66 dpi";
	        }
			
			float xdpi = 0;

			Display display = activity.getWindowManager().getDefaultDisplay();    
	        DisplayMetrics dm = new DisplayMetrics();
	        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	        
	        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
	        	xdpi = activity.getResources().getDisplayMetrics().xdpi;
	    	} else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
	    		DisplayMetrics outMetrics = new DisplayMetrics ();
	    		display.getRealMetrics(outMetrics);
	    		xdpi = outMetrics.xdpi;	
	    	}
	        
			return xdpi + " dpi";
		}
		
		@SuppressLint("NewApi")
		public static String getDpiY(Activity activity) {
			String model = Build.MODEL;
			if(model.contains("Ascend G300") || model.contains("Nexus S")
	        		|| model.contains("Lenovo A380")) {
		        return "233.24 dpi";
	        } else if(model.contains("i-mobile i-note WiFi 9")) {
		        return "103.66 dpi";
	        }
			
			float ydpi = 0;

			Display display = activity.getWindowManager().getDefaultDisplay();    
	        DisplayMetrics dm = new DisplayMetrics();
	        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	        
	        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
    	        ydpi = activity.getResources().getDisplayMetrics().ydpi;
	    	} else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
	    		DisplayMetrics outMetrics = new DisplayMetrics ();
	    		display.getRealMetrics(outMetrics);
	    		ydpi = outMetrics.ydpi;	    	
	    	}
	        
			return ydpi + " dpi";
		}
		
		@SuppressWarnings("deprecation")
		@SuppressLint("NewApi")
		public static String getResolutionDP(Activity activity) {
			String model = Build.MODEL;
			if(model.contains("Ascend G300") || model.contains("Nexus S")
	        		|| model.contains("Lenovo A380")) {
		        return "533 x 320 dp";
	        } else if(model.contains("i-mobile i-note WiFi 9")) {
		        return "1066 x 640 dp";
	        }
			
			Display display = activity.getWindowManager().getDefaultDisplay();  
	        DisplayMetrics dm = new DisplayMetrics();
	        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
	        
	        int xres = 0, yres = 0;
	
	        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
	        	try {
	    	        Method mGetRawH = Display.class.getMethod("getRawHeight");
	    	        Method mGetRawW = Display.class.getMethod("getRawWidth");
	    	        xres = (Integer) mGetRawW.invoke(display);
	    	        yres = (Integer) mGetRawH.invoke(display);
	    		} catch (Exception e) {
	    			xres = display.getWidth();
	    			yres = display.getHeight();
	    		}
	    	} else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
	    		DisplayMetrics outMetrics = new DisplayMetrics ();
	    		display.getRealMetrics(outMetrics);
	    		
				xres = outMetrics.widthPixels;
				yres = outMetrics.heightPixels;
	    	}
	        
	        int hdp = (int)(yres * (1f / dm.density));
	        int wdp = (int)(xres * (1f / dm.density));

			return hdp + " x " + wdp + " dp";
		}
		
		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		public static String getResolutionPX(Activity activity) {
			String model = Build.MODEL;
			if(model.contains("Ascend G300") 
					|| model.contains("Nexus S")
	        		|| model.contains("Lenovo A380") 
	        		|| model.contains("i-mobile i-note WiFi 9")) {
		        return "800 x 480 px";
	        }
			
			int xres = 0, yres = 0;
			
			Display display = activity.getWindowManager().getDefaultDisplay();    
	        DisplayMetrics dm = new DisplayMetrics();
	        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			
	        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
	        	try {
	    	        Method mGetRawH = Display.class.getMethod("getRawHeight");
	    	        Method mGetRawW = Display.class.getMethod("getRawWidth");
	    	        xres = (Integer) mGetRawW.invoke(display);
	    	        yres = (Integer) mGetRawH.invoke(display);
	    		} catch (Exception e) {
	    			xres = display.getWidth();
	    			yres = display.getHeight();
	    		}
	    	} else if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
	    		DisplayMetrics outMetrics = new DisplayMetrics ();
	    		display.getRealMetrics(outMetrics);  	
	    		
				xres = outMetrics.widthPixels;
				yres = outMetrics.heightPixels;
	    	}
			
			return yres + " x " + xres + " px";
		}
		
		public static String getScreenSize(Activity activity) {
			String model = Build.MODEL;
			if(model.contains("Ascend G300") || model.contains("Nexus S")
	        		|| model.contains("Lenovo A380")) {
		        return "Normal";
	        } else if(model.contains("i-mobile i-note WiFi 9")) {
		        return "Large";
	        }
			
			int screenSize = activity.getResources().getConfiguration().screenLayout 
	        		& Configuration.SCREENLAYOUT_SIZE_MASK;
	
	        if(screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) 
	        	return "Small";
	        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) 
	        	return "Normal";
	        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) 
	        	return "Large";
	        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) 
	        	return "Extra Large";
			
			return "";
		}	
	}

	@SuppressLint("NewApi")
	public static class CameraInfo {		
		public static String isAutoExposureLockSupported(Parameters params) {
			try {
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					if(params.isAutoExposureLockSupported()) {
						return "yes";
					} else {
						return "no";
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "null";
		}		
		
		public static String isAutoWhiteBalanceLockSupported(Parameters params) {
			try {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				if(params.isAutoWhiteBalanceLockSupported()) {
					return "yes";
				} else {
					return "no";
				}
			}
			} catch(Exception e){
				e.printStackTrace();
			}
			return "null";
		}		
		
		public static String isSmoothZoomSupported(Parameters params) {
			try {
			if(params.isSmoothZoomSupported()) {
				return "yes";
			} else {
				return "no";
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "null";
		}		
		
		public static String isVideoSnapshotSupported(Parameters params) {
			try {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				if(params.isVideoSnapshotSupported()) {
					return "yes";
				} else {
					return "no";
				}
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "null";
		}		
		
		public static String isVideoStabilizationSupported(Parameters params) {
			try {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
				if(params.isVideoStabilizationSupported()) {
					return "yes";
				} else {
					return "no";
				}
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "null";
		}		
		
		public static String isZoomSupported(Parameters params) {
			try {
			if(params.isZoomSupported()) {
				return "yes";
			} else {
				return "no";
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "null";
		}		
		
		public static String getSupportedAntibanding(Parameters params) {
			String str = "";
			List<String> antibanding = params.getSupportedAntibanding();
			if(antibanding != null) {
				for(int i = 0 ; i < antibanding.size() ; i++) 
					str += (i < antibanding.size() - 1) ? antibanding.get(i) + " " : antibanding.get(i);
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedColorEffects(Parameters params) {
			String str = "";
			List<String> colorEffect = params.getSupportedColorEffects();
			if(colorEffect != null) {
				for(int i = 0 ; i < colorEffect.size() ; i++) 
					str += (i < colorEffect.size() - 1) ? colorEffect.get(i) + " " : colorEffect.get(i);
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedFlashModes(Parameters params) {
			String str = "";
			List<String> flashMode = params.getSupportedFlashModes();
			if(flashMode != null) {
				for(int i = 0 ; i < flashMode.size() ; i++) 
					str += (i < flashMode.size() - 1) ? flashMode.get(i) + " " : flashMode.get(i);
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedFocusModes(Parameters params) {
			String str = "";
			List<String> focusMode = params.getSupportedFocusModes();
			if(focusMode != null) {
				for(int i = 0 ; i < focusMode.size() ; i++) 
					str += (i < focusMode.size() - 1) ? focusMode.get(i) + " " : focusMode.get(i);
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedJpegThumbnailSizes(Parameters params) {
			String str = "";
			List<Camera.Size> jpegThumbnailSizes = params.getSupportedJpegThumbnailSizes();
			if(jpegThumbnailSizes != null) {
				for(int i = 0 ; i < jpegThumbnailSizes.size() ; i++) 
					str += (i < jpegThumbnailSizes.size() - 1) 
							? jpegThumbnailSizes.get(i).width + "x" + jpegThumbnailSizes.get(i).height + " " 
							: jpegThumbnailSizes.get(i).width + "x" + jpegThumbnailSizes.get(i).height;
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedPictureFormats(Parameters params) {
			String str = "";
			List<Integer> pictureFormat = params.getSupportedPictureFormats();
			if(pictureFormat != null) {
				for(int i = 0 ; i < pictureFormat.size() ; i++) {
					switch(pictureFormat.get(i)) {
					case ImageFormat.JPEG : 
						str += (i < pictureFormat.size() - 1) ? "JPEG " : "JPEG";
						break;
					case ImageFormat.NV16 : 
						str += (i < pictureFormat.size() - 1) ? "NV16 " : "NV16";
						break;
					case ImageFormat.NV21 : 
						str += (i < pictureFormat.size() - 1) ? "NV21 " : "NV21";
						break;
					case ImageFormat.RGB_565 : 
						str += (i < pictureFormat.size() - 1) ? "RGB_565 " : "RGB_565";
						break;
					case ImageFormat.UNKNOWN : 
						str += (i < pictureFormat.size() - 1) ? "Unknown " : "Unknown";
						break;
					case ImageFormat.YUV_420_888 : 
						str += (i < pictureFormat.size() - 1) ? "YUV_420_888 " : "YUV_420_888";
						break;
					case ImageFormat.YUY2 : 
						str += (i < pictureFormat.size() - 1) ? "YUY2 " : "YUY2";
						break;
					case ImageFormat.YV12 : 
						str += (i < pictureFormat.size() - 1) ? "YV12 " : "YV12";
						break;
					}
				}
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedPreviewFormats(Parameters params) {
			String str = "";
			List<Integer> previewFormat = params.getSupportedPreviewFormats();
			if(previewFormat != null) {
				for(int i = 0 ; i < previewFormat.size() ; i++) {
					switch(previewFormat.get(i)) {
					case ImageFormat.JPEG : 
						str += (i < previewFormat.size() - 1) ? "JPEG " : "JPEG";
						break;
					case ImageFormat.NV16 : 
						str += (i < previewFormat.size() - 1) ? "NV16 " : "NV16";
						break;
					case ImageFormat.NV21 : 
						str += (i < previewFormat.size() - 1) ? "NV21 " : "NV21";
						break;
					case ImageFormat.RGB_565 : 
						str += (i < previewFormat.size() - 1) ? "RGB_565 " : "RGB_565";
						break;
					case ImageFormat.UNKNOWN : 
						str += (i < previewFormat.size() - 1) ? "Unknown " : "Unknown";
						break;
					case ImageFormat.YUV_420_888 : 
						str += (i < previewFormat.size() - 1) ? "YUV_420_888 " : "YUV_420_888";
						break;
					case ImageFormat.YUY2 : 
						str += (i < previewFormat.size() - 1) ? "YUY2 " : "YUY2";
						break;
					case ImageFormat.YV12 : 
						str += (i < previewFormat.size() - 1) ? "YV12 " : "YV12";
						break;
					}
				}
				return str;
			} else {
				return "null";
			}
		}
		
		@SuppressWarnings("deprecation")
		public static String getSupportedPreviewFrameRates(Parameters params) {
			String str = "";
			List<Integer> previewFrameRates = params.getSupportedPreviewFrameRates();
			if(previewFrameRates != null) {
				str = previewFrameRates.get(0) + "-" + previewFrameRates.get(previewFrameRates.size() - 1);
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedPictureSizes(Parameters params) {
			String str = "";
			List<Camera.Size> pictureSizes = params.getSupportedPictureSizes();
			if(pictureSizes != null) {
				for(int i = 0 ; i < pictureSizes.size() ; i++) 
					str += (i < pictureSizes.size() - 1) 
							? pictureSizes.get(i).width + "x" + pictureSizes.get(i).height + " " 
							: pictureSizes.get(i).width + "x" + pictureSizes.get(i).height;
				return str;
			} else {
				return "null";
			}
		}
		
		public static String getSupportedPreviewSizes(Parameters params) {
			String str = "";
			List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();
			if(previewSizes != null) {
				for(int i = 0 ; i < previewSizes.size() ; i++) 
					str += (i < previewSizes.size() - 1) 
							? previewSizes.get(i).width + "x" + previewSizes.get(i).height + " " 
							: previewSizes.get(i).width + "x" + previewSizes.get(i).height;
				return str;
			} else {
				return "null";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getSupportedPreviewFpsRange(Parameters params) {
			String str = "";
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				List<int[]> previewFpsRange = params.getSupportedPreviewFpsRange();
				if(previewFpsRange != null) {
					for(int i = 0 ; i < previewFpsRange.size() ; i++) {
						str += (i < previewFpsRange.size() - 1) 
								? (float)previewFpsRange.get(i)[0] / 1000 + "-" + (float)previewFpsRange.get(i)[previewFpsRange.get(i).length - 1] / 1000 + " " 
								: (float)previewFpsRange.get(i)[0] / 1000 + "-" + (float)previewFpsRange.get(i)[previewFpsRange.get(i).length - 1] / 1000;
					}
					return str;
				} else {
					return "null";
				}
			} else {
				return "null";
			}
		}
		
		public static String getSupportedSceneModes(Parameters params) {
			String str = "";
			List<String> sceneMode = params.getSupportedSceneModes();
			if(sceneMode != null) {
				for(int i = 0 ; i < sceneMode.size() ; i++) 
					str += (i < sceneMode.size() - 1) ? sceneMode.get(i) + " " : sceneMode.get(i);
				return str;
			} else {
				return "null";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getSupportedVideoSizes(Parameters params) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				String str = "";
				List<Camera.Size> videoSizes = params.getSupportedVideoSizes();
				if(videoSizes != null) {
					for(int i = 0 ; i < videoSizes.size() ; i++) 
						str += (i < videoSizes.size() - 1) 
								? videoSizes.get(i).width + "x" + videoSizes.get(i).height + " " 
								: videoSizes.get(i).width + "x" + videoSizes.get(i).height;
					return str;
				} else {
					return null;
				}
			} else {
				return "null";
			}
		}
		
		public static String getSupportedWhiteBalance(Parameters params) {
			String str = "";
			List<String> whiteBalance = params.getSupportedWhiteBalance();
			if(whiteBalance != null) {
				for(int i = 0 ; i < whiteBalance.size() ; i++) 
					str += (i < whiteBalance.size() - 1) ? whiteBalance.get(i) + " " : whiteBalance.get(i);
				return str;
			} else {
				return "null";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getQualityProfile(Parameters params, int cameraId) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				String str = "";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_QCIF)) 
					str += "176x144 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_QVGA)) 
					str += "320x240 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_CIF)) 
					str += "352x288 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_480P)) 
					str += "720x480 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_720P)) 
					str += "1280x720 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_1080P)) 
					str += "1920x1080 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_LOW)) 
					str += "lowest-quality ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_HIGH)) 
					str += "highest-quality ";
				if(CamcorderProfile.hasProfile(cameraId, 0x08)) 
					str += "unknown(0x08) ";
				if(CamcorderProfile.hasProfile(cameraId, 0x09)) 
					str += "unknown(0x09) ";
				if(CamcorderProfile.hasProfile(cameraId, 0x0A)) 
					str += "unknown(0x0A) ";
				if(CamcorderProfile.hasProfile(cameraId, 0x0B)) 
					str += "unknown(0x0B) ";
				return str.trim();
			} else {
				return "null";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getQualityTimeLapseProfile(Parameters params, int cameraId) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				String str = "";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_QCIF)) 
					str += "176x144 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_QVGA)) 
					str += "320x240 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_CIF)) 
					str += "352x288 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_480P)) 
					str += "720x480 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_720P)) 
					str += "1280x720 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_1080P)) 
					str += "1920x1080 ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_LOW)) 
					str += "lowest-quality ";
				if(CamcorderProfile.hasProfile(cameraId, CamcorderProfile.QUALITY_TIME_LAPSE_HIGH)) 
					str += "highest-quality ";
				if(CamcorderProfile.hasProfile(cameraId, 0x3F0)) 
					str += "unknown(0x3F0) ";
				if(CamcorderProfile.hasProfile(cameraId, 0x3F1)) 
					str += "unknown(0x3F1) ";
				if(CamcorderProfile.hasProfile(cameraId, 0x3F2)) 
					str += "unknown(0x3F2) ";
				if(CamcorderProfile.hasProfile(cameraId, 0x3F3)) 
					str += "unknown(0x3F3) ";
				return str;
			} else {
				return "null";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getCameraFacing(Camera.CameraInfo ci) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				int facing = ci.facing;
				if(facing == Camera.CameraInfo.CAMERA_FACING_BACK)
					return "back";
				else if(facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
					return "front";
				else 
					return "unknown";
			} else {
				return "front";
			}
		}
		
		@SuppressLint("NewApi")
		public static String getImageOrientation(Camera.CameraInfo ci) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) 
				return "" + ci.orientation;
			else 
				return "0";
		}
		
		@SuppressLint("NewApi")
		public static String canDisableShutterSound(Camera.CameraInfo ci) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				return "" + ci.canDisableShutterSound;
			} else {
				return "null";
			}
		}
		
	}

	public static class FeatureInfo {				
		private static String isFeatureSupported(Activity activity, String feature) {
			if(activity.getPackageManager().hasSystemFeature(feature)) 
				return "Yes";
			else 
				return "No";
		}
		
		public static String hasFeature(Activity activity, String feature, int minVersion) {
			int version = Build.VERSION.SDK_INT;
			if (version >= minVersion)
				return isFeatureSupported(activity, feature);
			else
				return "No";
		}
	}
}
