package app.akeorcist.deviceinformation;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import app.akeorcist.deviceinformation.InfoManager.SensorInfo;

@SuppressLint("NewApi")
public class FragmentSensorSub extends Fragment {
	private static final String BUNDLE_NAME = "name";
	private static final String BUNDLE_VENDOR = "vendor";
	private static final String BUNDLE_TYPE = "type";
	private static final String BUNDLE_VERSION = "version";
	private static final String BUNDLE_POWER = "power";
	private static final String BUNDLE_MAX_RANGE = "max_range";
	private static final String BUNDLE_RESOLUTION = "resolution";
	private static final String BUNDLE_MIN_DELAY = "min_delay";
	private static final String BUNDLE_FIFO_MAX = "fifo_max";
	private static final String BUNDLE_FIFO_RESERVED = "fifo_reserved";
	
	SensorManager sensorManager;
	Sensor sensor;
	
	TextView text2;
	
	public static FragmentSensorSub newInstance(String name, String vendor, int type, String version
			, String power, String max_range, String resolution, String min_delay, String fifo_max, String fifo_reserved) {
		FragmentSensorSub fragment = new FragmentSensorSub();
		Bundle args = new Bundle();
		args.putString(BUNDLE_NAME, name);
		args.putString(BUNDLE_VENDOR, vendor);
		args.putInt(BUNDLE_TYPE, type);
		args.putString(BUNDLE_VERSION, version);
		args.putString(BUNDLE_POWER, power);
		args.putString(BUNDLE_MAX_RANGE, max_range);
		args.putString(BUNDLE_RESOLUTION, resolution);
		args.putString(BUNDLE_MIN_DELAY, min_delay);
		args.putString(BUNDLE_FIFO_RESERVED, fifo_reserved);
		args.putString(BUNDLE_FIFO_MAX, fifo_max);
		
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentSensorSub() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(getArguments().getInt(BUNDLE_TYPE));
		
		View rootView = inflater.inflate(R.layout.fragment_sensor_sub, container, false);

		TextView textName = (TextView)rootView.findViewById(R.id.textSensorName);
		textName.setText(getArguments().getString(BUNDLE_NAME));
		textName.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(SensorInfo.getType(getArguments().getInt(BUNDLE_TYPE))
						, getArguments().getInt(BUNDLE_TYPE));
			}
		});
		
		TextView textVendor = (TextView)rootView.findViewById(R.id.textSensorVendor);
		textVendor.setText(getArguments().getString(BUNDLE_VENDOR));

		TextView textType = (TextView)rootView.findViewById(R.id.textSensorType);
		textType.setText(SensorInfo.getType(getArguments().getInt(BUNDLE_TYPE)));

		TextView textVersion = (TextView)rootView.findViewById(R.id.textSensorVersion);
		textVersion.setText("" + getArguments().getString(BUNDLE_VERSION));

		TextView textPower = (TextView)rootView.findViewById(R.id.textSensorPower);
		textPower.setText("" + getArguments().getString(BUNDLE_POWER));
		
		TextView textMaxRange = (TextView)rootView.findViewById(R.id.textSensorMaxRange);
		textMaxRange.setText("" + getArguments().getString(BUNDLE_MAX_RANGE));
		
		TextView textResolution = (TextView)rootView.findViewById(R.id.textSensorResolution);
		textResolution.setText("" + getArguments().getString(BUNDLE_RESOLUTION));

		TextView textMinDelay = (TextView)rootView.findViewById(R.id.textSensorMinDelay);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
			textMinDelay.setText("" + getArguments().getString(BUNDLE_MIN_DELAY));
		else 
			textMinDelay.setText("-");

		TextView textSensorFifoReserved = (TextView)rootView.findViewById(R.id.textSensorFifoReserved);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			textSensorFifoReserved.setText("" + getArguments().getString(BUNDLE_FIFO_RESERVED));
		else 
			textSensorFifoReserved.setText("-");

		TextView textSensorFifoMax = (TextView)rootView.findViewById(R.id.textSensorFifoMax);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
			textSensorFifoMax.setText("" + getArguments().getString(BUNDLE_FIFO_MAX));
		else 
			textSensorFifoMax.setText("-");
				
		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@SuppressWarnings("deprecation")
	public void showDialog(String title, int type) {		
		Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sensor_detail);
        dialog.setCancelable(true);
        
        TextView text1 = (TextView)dialog.findViewById(R.id.text1);
        text1.setText(title);

        text2 = (TextView)dialog.findViewById(R.id.text2);
        
        TextView text3 = (TextView)dialog.findViewById(R.id.text3);
        
		if(type == Sensor.TYPE_ACCELEROMETER) {
			text3.setText(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
			text3.append("\n");
			text3.append(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
			text3.append("\n");
			text3.append(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
		} else if(type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
			text3.setText("°C");
		} else if(type == Sensor.TYPE_GAME_ROTATION_VECTOR) {
			//text3.setText("");
		} else if(type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
			//text3.setText("");
		} else if(type == Sensor.TYPE_GRAVITY) {
			text3.setText(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
			text3.append("\n");
			text3.append(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
			text3.append("\n");
			text3.append(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
		} else if(type == Sensor.TYPE_GYROSCOPE) {
			text3.setText("rad/s\nrad/s\nrad/s");
		} else if(type == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
			text3.setText("rad/s\nrad/s\nrad/s\nrad/s\nrad/s");
		} else if(type == Sensor.TYPE_LIGHT) {
			text3.setText("lx");
		} else if(type == Sensor.TYPE_LINEAR_ACCELERATION) {
			text3.setText(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
			text3.append("\n");
			text3.append(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
			text3.append("\n");
			text3.append(Html.fromHtml("m/s<sup><small><small>2</small></small></sup>"));
		} else if(type == Sensor.TYPE_MAGNETIC_FIELD) {
			text3.setText("μT\nμT\nμT");
		} else if(type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
			text3.setText("μT\nμT\nμT\nμT\nμT\nμT");
		} else if(type == Sensor.TYPE_ORIENTATION) {
			//text3.setText("");
		} else if(type == Sensor.TYPE_PRESSURE) {
			text3.setText("hPa");
		} else if(type == Sensor.TYPE_PROXIMITY) {
			//text3.setText("");
		} else if(type == Sensor.TYPE_RELATIVE_HUMIDITY) {
			text3.setText("%");
		} else if(type == Sensor.TYPE_ROTATION_VECTOR) {
			//text3.setText("");
		} else if(type == Sensor.TYPE_SIGNIFICANT_MOTION) {
			//text3.setText("");
			text2.setText("Not available");
		} else if(type == Sensor.TYPE_STEP_COUNTER) {
			//text3.setText("");
		} else if(type == Sensor.TYPE_TEMPERATURE) {
			text3.setText("°C");
		}
        
        if(type != Sensor.TYPE_SIGNIFICANT_MOTION) {
			sensorManager.registerListener(listener, sensor
					, SensorManager.SENSOR_DELAY_NORMAL);
        }
        
        dialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				sensorManager.unregisterListener(listener);
			}
		});
        dialog.show();
	}
	
	SensorEventListener listener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) { }
		@SuppressWarnings("deprecation")
		public void onSensorChanged(SensorEvent event) {
			float x, y, z;
			int type = event.sensor.getType();
			if(type == Sensor.TYPE_ACCELEROMETER
					|| type == Sensor.TYPE_GAME_ROTATION_VECTOR
					|| type == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR
					|| type == Sensor.TYPE_GRAVITY
					|| type == Sensor.TYPE_GYROSCOPE
					|| type == Sensor.TYPE_GYROSCOPE_UNCALIBRATED
					|| type == Sensor.TYPE_LINEAR_ACCELERATION
					|| type == Sensor.TYPE_MAGNETIC_FIELD
					|| type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED
					|| type == Sensor.TYPE_ORIENTATION
					|| type == Sensor.TYPE_ROTATION_VECTOR) {
				x = event.values[0];
				y = event.values[1];
				z = event.values[2];

				if(type == Sensor.TYPE_ORIENTATION) {
					text2.setText("AZI  " + String.format(Locale.getDefault(), "%.3f", x) + "\n"
							+ "PIT  " + String.format(Locale.getDefault(), "%.3f", y) + "\n"
							+ "ROL  " + String.format(Locale.getDefault(), "%.3f", z));
				} else {
					text2.setText("X  " + String.format(Locale.getDefault(), "%.5f", x) + "\n"
							+ "Y  " + String.format(Locale.getDefault(), "%.5f", y) + "\n"
							+ "Z  " + String.format(Locale.getDefault(), "%.5f", z));
				}
				
				if(type == Sensor.TYPE_ROTATION_VECTOR 
						&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
					float a = event.values[3];
					float b = event.values[4];
					text2.append("\nA  " + String.format(Locale.getDefault(), "%.5f", a) + "\n" 
							+ "B  " +  String.format(Locale.getDefault(), "%.5f", b));
				} else if(type == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
					float a = event.values[3];
					float b = event.values[4];
					float c = event.values[5];
					text2.append("Diff X  " + String.format(Locale.getDefault(), "%.5f", a) + "\n"
							+ "Diff Y  " + String.format(Locale.getDefault(), "%.5f", b) + "\n"
							+ "Diff Z  " + String.format(Locale.getDefault(), "%.5f", c));
				} else if(type == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
					float a = event.values[3];
					float b = event.values[4];
					float c = event.values[5];
					text2.append("Bias X  " + String.format(Locale.getDefault(), "%.5f", a) + "\n"
							+ "Bias Y  " + String.format(Locale.getDefault(), "%.5f", b) + "\n"
							+ "Bias Z  " + String.format(Locale.getDefault(), "%.5f", c));
				}
				
			} else if(type == Sensor.TYPE_AMBIENT_TEMPERATURE
					|| type == Sensor.TYPE_LIGHT
					|| type == Sensor.TYPE_PRESSURE
					|| type == Sensor.TYPE_PROXIMITY
					|| type == Sensor.TYPE_RELATIVE_HUMIDITY
					|| type == Sensor.TYPE_SIGNIFICANT_MOTION
					|| type == Sensor.TYPE_STEP_COUNTER
					|| type == Sensor.TYPE_STEP_DETECTOR
					|| type == Sensor.TYPE_TEMPERATURE) {
				x = event.values[0];
				if(type == Sensor.TYPE_PROXIMITY 
						|| type == Sensor.TYPE_LIGHT
						|| type == Sensor.TYPE_STEP_COUNTER) {
					text2.setText(String.format(Locale.getDefault(), "%.0f", x));
				} else if(type == Sensor.TYPE_STEP_DETECTOR) {
					if(x == 1) 
						text2.setText("Step Detected");
				} else if(type == Sensor.TYPE_TEMPERATURE
						|| type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
					text2.setText(String.format(Locale.getDefault(), "%.1f", x));
				} else {
					text2.setText(String.format(Locale.getDefault(), "%.5f", x));
				}
			} else {
				x = event.values[0];
				text2.setText(String.format(Locale.getDefault(), "%.0f", x));
			}
			
			
			//text2.setText("X : " + x + "\nY : " + y + "\nZ : " + z);
		}
	};

	@Override
	public void onPause() {
		super.onPause();
		if(getArguments().getInt(BUNDLE_TYPE) != Sensor.TYPE_SIGNIFICANT_MOTION) {
			sensorManager.unregisterListener(listener);
		}
	}
}
