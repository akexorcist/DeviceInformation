package app.akeorcist.deviceinformation;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import app.akeorcist.deviceinformation.InfoManager.FeatureInfo;

public class FragmentFeature extends Fragment {	
	CustomListViewAdapterFeature adapter;
	ListView listView;
	LinearLayout layoutLoading;
	
	public static FragmentFeature newInstance() {
		FragmentFeature fragment = new FragmentFeature();
		return fragment;
	}

	public FragmentFeature() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_feature, container,
				false);
		
		ArrayList<String> arr_list = new ArrayList<String>();
		alignSupported(arr_list);
		
		adapter = new CustomListViewAdapterFeature(getActivity().getApplicationContext(), android.R.id.text1, arr_list);

		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		
		listView = (ListView)rootView.findViewById(R.id.listFeature);
		
		listView = (ListView)rootView.findViewById(R.id.listFeature);
		listView.setVisibility(View.GONE);
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				try {
					listView.setAdapter(adapter);
			        listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
							String[] str = adapter.getString(arg2).split(" : ");
							if(str[0].equals("App Widget")) {
								showDetails("android.software.app_widgets", R.string.detail_app_widget);
							} else if(str[0].equals("Audio Low Latency")) {
								showDetails("android.hardware.audio.low_latency", R.string.detail_audio_low_latency);
							} else if(str[0].equals("Bluetooth")) {
								showDetails("android.hardware.bluetooth", R.string.detail_bluetooth);
							} else if(str[0].equals("Bluetooth Low Energy")) {
								showDetails("android.hardware.bluetooth_le", R.string.detail_bluetooth_low_energy);
							} else if(str[0].equals("Camera")) {
								showDetails("android.hardware.camera", R.string.detail_camera);
							} else if(str[0].equals("Camera Any")) {
								showDetails("android.hardware.camera.any", R.string.detail_camera_any);
							} else if(str[0].equals("Camera Autofocus")) {
								showDetails("android.hardware.camera.autofocus", R.string.detail_camera_autofocus);
							} else if(str[0].equals("Camera Flash")) {
								showDetails("android.hardware.camera.flash", R.string.detail_camera_flash);
							} else if(str[0].equals("Camera Front")) {
								showDetails("android.hardware.camera.front", R.string.detail_camera_front);
							} else if(str[0].equals("Consumer IR")) {
								showDetails("android.hardware.consumerir", R.string.detail_comsumer_ir);
							} else if(str[0].equals("Device Admin")) {
								showDetails("android.hardware.device_admin", R.string.detail_device_admin);
							} else if(str[0].equals("Faketouch")) {
								showDetails("android.hardware.faketouch", R.string.detail_faketouch);
							} else if(str[0].equals("Faketouch Multitouch Distinct")) {
								showDetails("android.hardware.faketouch.multitouch.distinct", R.string.detail_faketouch_multitouch_distinct);
							} else if(str[0].equals("Faketouch Multitouch Jazzhand")) {
								showDetails("android.hardware.faketouch.multitouch.jazzhand", R.string.detail_faketouch_multitouch_jazzhand);
							} else if(str[0].equals("Home Screen")) {
								showDetails("android.hardware.home_screen", R.string.detail_home_screen);
							} else if(str[0].equals("Input Methods")) {
								showDetails("android.hardware.input_methods", R.string.detail_input_methods);
							} else if(str[0].equals("Live Wallpaper")) {
								showDetails("android.hardware.live_wallpaper", R.string.detail_live_wallpaper);
							} else if(str[0].equals("Location")) {
								showDetails("android.hardware.location", R.string.detail_location);
							} else if(str[0].equals("Location GPS")) {
								showDetails("android.hardware.location.gps", R.string.detail_location_gps);
							} else if(str[0].equals("Location Network")) {
								showDetails("android.hardware.location.network", R.string.detail_location_network);
							} else if(str[0].equals("Microphone")) {
								showDetails("android.hardware.microphone", R.string.detail_microphone);
							} else if(str[0].equals("NFC")) {
								showDetails("android.hardware.nfc", R.string.detail_nfc);
							} else if(str[0].equals("NFC Host Card Emulation")) {
								showDetails("android.hardware.nfc.hce", R.string.detail_nfc_host_card_emulation);
							} else if(str[0].equals("Screen Landscape")) {
								showDetails("android.hardware.screen.landscape", R.string.detail_screen_landscape);
							} else if(str[0].equals("Screen Portrait")) {
								showDetails("android.hardware.screen.portrait", R.string.detail_screen_portrait);
							} else if(str[0].equals("Sensor Accelerometer")) {
								showDetails("android.hardware.sensor.accelerometer", R.string.detail_sensor_accelerometer);
							} else if(str[0].equals("Sensor Barometer")) {
								showDetails("android.hardware.sensor.barometer", R.string.detail_sensor_barometer);
							} else if(str[0].equals("Sensor Compass")) {
								showDetails("android.hardware.sensor.compass", R.string.detail_sensor_compass);
							} else if(str[0].equals("Sensor Gyroscope")) {
								showDetails("android.hardware.sensor.gyroscope", R.string.detail_sensor_gyroscope);
							} else if(str[0].equals("Sensor Light")) {
								showDetails("android.hardware.sensor.light", R.string.detail_sensor_light);
							} else if(str[0].equals("Sensor Proximity")) {
								showDetails("android.hardware.sensor.proximity", R.string.detail_sensor_proximity);
							} else if(str[0].equals("Sensor Step Counter")) {
								showDetails("android.hardware.sensor.stepcounter", R.string.detail_sensor_step_counter);
							} else if(str[0].equals("Sensor Step Detector")) {
								showDetails("android.hardware.sensor.stepdetector", R.string.detail_sensor_step_detector);
							} else if(str[0].equals("SIP")) {
								showDetails("android.hardware.sip", R.string.detail_sip);
							} else if(str[0].equals("SIP VOIP")) {
								showDetails("android.hardware.sip.voip", R.string.detail_sip_voip);
							} else if(str[0].equals("Telephony")) {
								showDetails("android.hardware.telephony", R.string.detail_telephony);
							} else if(str[0].equals("Telephony CDMA")) {
								showDetails("android.hardware.telephony.cdma", R.string.detail_telephony_cdma);
							} else if(str[0].equals("Telephony GSM")) {
								showDetails("android.hardware.telephony.gsm", R.string.detail_telephony_gsm);
							} else if(str[0].equals("Television")) {
								showDetails("android.hardware.type.television", R.string.detail_television);
							} else if(str[0].equals("Touchscreen")) {
								showDetails("android.hardware.touchscreen", R.string.detail_touchscreen);
							} else if(str[0].equals("Touchscreen Multitouch")) {
								showDetails("android.hardware.touchscreen.multitouch", R.string.detail_touchscreen_multitouch);
							} else if(str[0].equals("Touchscreen Multitouch Distinct")) {
								showDetails("android.hardware.touchscreen.multitouch.distinct", R.string.detail_touchscreen_multitouch_distinct);
							} else if(str[0].equals("Touchscreen Multitouch Jazzhand")) {
								showDetails("android.hardware.touchscreen.multitouch.jazzhand", R.string.detail_touchscreen_multitouch_jazzhand);
							} else if(str[0].equals("USB Accessory")) {
								showDetails("android.hardware.usb.accessory", R.string.detail_usb_accessory);
							} else if(str[0].equals("USB Host")) {
								showDetails("android.hardware.usb.host", R.string.detail_usb_host);
							} else if(str[0].equals("WiFi")) {
								showDetails("android.hardware.wifi", R.string.detail_wifi);
							} else if(str[0].equals("WiFi Direct")) {
								showDetails("android.hardware.wifi.direct", R.string.detail_wifi_direct);
							}	
						}
			        });
	
					layoutLoading.setVisibility(View.GONE);
					listView.setVisibility(View.VISIBLE);
				} catch(NullPointerException e) {
					e.printStackTrace();
				}
			}
		}, 500);
		
        
		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	public void showDetails(String title, int detail) {
		Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feature_detail);
        dialog.setCancelable(true);
        
        TextView text1 = (TextView)dialog.findViewById(R.id.text1);
        text1.setText(title);
        
        TextView text2 = (TextView)dialog.findViewById(R.id.text2);
        text2.setText(detail);

        dialog.show();
	}
	
	@SuppressLint("InlinedApi")
	public void alignSupported(ArrayList<String> arr_list) {
		arr_list.add("header1");
		
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_APP_WIDGETS, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("Yes"))
			arr_list.add("App Widget");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_AUDIO_LOW_LATENCY, Build.VERSION_CODES.GINGERBREAD).equals("Yes"))
			arr_list.add("Audio Low Latency");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_BLUETOOTH, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Bluetooth");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_BLUETOOTH_LE, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("Yes"))
			arr_list.add("Bluetooth Low Energy");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Camera");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_ANY, Build.VERSION_CODES.JELLY_BEAN_MR1).equals("Yes"))
			arr_list.add("Camera Any");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_AUTOFOCUS, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Camera Autofocus");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_FLASH, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Camera Flash");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_FRONT, Build.VERSION_CODES.GINGERBREAD).equals("Yes"))
			arr_list.add("Camera Front");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CONSUMER_IR, Build.VERSION_CODES.KITKAT).equals("Yes"))
			arr_list.add("Consumer IR");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_DEVICE_ADMIN, Build.VERSION_CODES.KITKAT).equals("Yes"))
			arr_list.add("Device Admin");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH, Build.VERSION_CODES.HONEYCOMB).equals("Yes"))
			arr_list.add("Faketouch");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT, Build.VERSION_CODES.HONEYCOMB_MR2).equals("Yes"))
			arr_list.add("Faketouch Multitouch Distinct");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.HONEYCOMB_MR2).equals("Yes"))
			arr_list.add("Faketouch Multitouch Jazzhand");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_HOME_SCREEN, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("Yes"))
			arr_list.add("Home Screen");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_INPUT_METHODS, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("Yes"))
			arr_list.add("Input Methods");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LIVE_WALLPAPER, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Live Wallpaper");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Location");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION_GPS, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Location GPS");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION_NETWORK, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Location Network");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_MICROPHONE, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Microphone");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_NFC, Build.VERSION_CODES.GINGERBREAD).equals("Yes"))
			arr_list.add("NFC");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_NFC_HOST_CARD_EMULATION, Build.VERSION_CODES.KITKAT).equals("Yes"))
			arr_list.add("NFC Host Card Emulation");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SCREEN_LANDSCAPE, Build.VERSION_CODES.HONEYCOMB_MR2).equals("Yes"))
			arr_list.add("Screen Landscape");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SCREEN_PORTRAIT, Build.VERSION_CODES.HONEYCOMB_MR2).equals("Yes"))
			arr_list.add("Screen Portrait");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_ACCELEROMETER, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Sensor Accelerometer");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_BAROMETER, Build.VERSION_CODES.GINGERBREAD).equals("Yes"))
			arr_list.add("Sensor Barometer");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_COMPASS, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Sensor Compass");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_GYROSCOPE, Build.VERSION_CODES.GINGERBREAD).equals("Yes"))
			arr_list.add("Sensor Gyroscope");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_LIGHT, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Sensor Light");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_PROXIMITY, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Sensor Proximity");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_STEP_COUNTER, Build.VERSION_CODES.KITKAT).equals("Yes"))
			arr_list.add("Sensor Step Counter");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_STEP_DETECTOR, Build.VERSION_CODES.KITKAT).equals("Yes"))
			arr_list.add("Sensor Step Detector");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SIP, Build.VERSION_CODES.GINGERBREAD).equals("Yes"))
			arr_list.add("SIP");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SIP_VOIP, Build.VERSION_CODES.GINGERBREAD).equals("Yes"))
			arr_list.add("SIP VOIP");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Telephony");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY_CDMA, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Telephony CDMA");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY_GSM, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Telephony GSM");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEVISION, Build.VERSION_CODES.JELLY_BEAN).equals("Yes"))
			arr_list.add("Television");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Touchscreen");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Touchscreen Multitouch");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT, Build.VERSION_CODES.ECLAIR_MR1).equals("Yes"))
			arr_list.add("Touchscreen Multitouch Distinct");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("Touchscreen Multitouch Jazzhand");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_USB_ACCESSORY, Build.VERSION_CODES.HONEYCOMB_MR1).equals("Yes"))
			arr_list.add("USB Accessory");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_USB_HOST, Build.VERSION_CODES.HONEYCOMB_MR1).equals("Yes"))
			arr_list.add("USB Host");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_WIFI, Build.VERSION_CODES.FROYO).equals("Yes"))
			arr_list.add("WiFi");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_WIFI_DIRECT, Build.VERSION_CODES.ICE_CREAM_SANDWICH).equals("Yes"))
			arr_list.add("WiFi Direct");
		
		arr_list.add("header2");
		
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_APP_WIDGETS, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("No"))
			arr_list.add("App Widget");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_AUDIO_LOW_LATENCY, Build.VERSION_CODES.GINGERBREAD).equals("No"))
			arr_list.add("Audio Low Latency");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_BLUETOOTH, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Bluetooth");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_BLUETOOTH_LE, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("No"))
			arr_list.add("Bluetooth Low Energy");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Camera");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_ANY, Build.VERSION_CODES.JELLY_BEAN_MR1).equals("No"))
			arr_list.add("Camera Any");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_AUTOFOCUS, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Camera Autofocus");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_FLASH, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Camera Flash");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CAMERA_FRONT, Build.VERSION_CODES.GINGERBREAD).equals("No"))
			arr_list.add("Camera Front");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_CONSUMER_IR, Build.VERSION_CODES.KITKAT).equals("No"))
			arr_list.add("Consumer IR");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_DEVICE_ADMIN, Build.VERSION_CODES.KITKAT).equals("No"))
			arr_list.add("Device Admin");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH, Build.VERSION_CODES.HONEYCOMB).equals("No"))
			arr_list.add("Faketouch");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT, Build.VERSION_CODES.HONEYCOMB_MR2).equals("No"))
			arr_list.add("Faketouch Multitouch Distinct");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.HONEYCOMB_MR2).equals("No"))
			arr_list.add("Faketouch Multitouch Jazzhand");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_HOME_SCREEN, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("No"))
			arr_list.add("Home Screen");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_INPUT_METHODS, Build.VERSION_CODES.JELLY_BEAN_MR2).equals("No"))
			arr_list.add("Input Methods");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LIVE_WALLPAPER, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Live Wallpaper");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Location");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION_GPS, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Location GPS");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_LOCATION_NETWORK, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Location Network");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_MICROPHONE, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Microphone");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_NFC, Build.VERSION_CODES.GINGERBREAD).equals("No"))
			arr_list.add("NFC");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_NFC_HOST_CARD_EMULATION, Build.VERSION_CODES.KITKAT).equals("No"))
			arr_list.add("NFC Host Card Emulation");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SCREEN_LANDSCAPE, Build.VERSION_CODES.HONEYCOMB_MR2).equals("No"))
			arr_list.add("Screen Landscape");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SCREEN_PORTRAIT, Build.VERSION_CODES.HONEYCOMB_MR2).equals("No"))
			arr_list.add("Screen Portrait");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_ACCELEROMETER, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Sensor Accelerometer");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_BAROMETER, Build.VERSION_CODES.GINGERBREAD).equals("No"))
			arr_list.add("Sensor Barometer");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_COMPASS, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Sensor Compass");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_GYROSCOPE, Build.VERSION_CODES.GINGERBREAD).equals("No"))
			arr_list.add("Sensor Gyroscope");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_LIGHT, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Sensor Light");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_PROXIMITY, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Sensor Proximity");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_STEP_COUNTER, Build.VERSION_CODES.KITKAT).equals("No"))
			arr_list.add("Sensor Step Counter");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SENSOR_STEP_DETECTOR, Build.VERSION_CODES.KITKAT).equals("No"))
			arr_list.add("Sensor Step Detector");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SIP, Build.VERSION_CODES.GINGERBREAD).equals("No"))
			arr_list.add("SIP");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_SIP_VOIP, Build.VERSION_CODES.GINGERBREAD).equals("No"))
			arr_list.add("SIP VOIP");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Telephony");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY_CDMA, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Telephony CDMA");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEPHONY_GSM, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Telephony GSM");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TELEVISION, Build.VERSION_CODES.JELLY_BEAN).equals("No"))
			arr_list.add("Television");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Touchscreen");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT, Build.VERSION_CODES.ECLAIR_MR1).equals("No"))
			arr_list.add("Touchscreen Multitouch Distinct");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("Touchscreen Multitouch Jazzhand");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_USB_ACCESSORY, Build.VERSION_CODES.HONEYCOMB_MR1).equals("No"))
			arr_list.add("USB Accessory");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_USB_HOST, Build.VERSION_CODES.HONEYCOMB_MR1).equals("No"))
			arr_list.add("USB Host");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_WIFI, Build.VERSION_CODES.FROYO).equals("No"))
			arr_list.add("WiFi");
		if(FeatureInfo.hasFeature(getActivity(), PackageManager.FEATURE_WIFI_DIRECT, Build.VERSION_CODES.ICE_CREAM_SANDWICH).equals("No"))
			arr_list.add("WiFi Direct");
	}
}
