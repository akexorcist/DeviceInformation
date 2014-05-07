package app.akeorcist.deviceinformation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import app.akeorcist.deviceinformation.InfoManager.ScreenInfo;

public class FragmentScreenSub1 extends Fragment {	
	ScrollView scrollScreen;
	LinearLayout layoutLoading;
	
	public static FragmentScreenSub1 newInstance() {
		FragmentScreenSub1 fragment = new FragmentScreenSub1();
		return fragment;
	}

	public FragmentScreenSub1() { }

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_screen_sub1, container,
				false);

		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		
		scrollScreen = (ScrollView)rootView.findViewById(R.id.scrollScreen);
		scrollScreen.setVisibility(View.GONE);
		
        TextView txtRes = (TextView)rootView.findViewById(R.id.txtRes);
        TextView txtResDp = (TextView)rootView.findViewById(R.id.txtResDp);
        TextView txtDPI = (TextView)rootView.findViewById(R.id.txtDPI);
        TextView txtDPIX = (TextView)rootView.findViewById(R.id.txtDPIX);
        TextView txtDPIY = (TextView)rootView.findViewById(R.id.txtDPIY);
        TextView txtSize = (TextView)rootView.findViewById(R.id.txtSize);
        TextView txtDensity = (TextView)rootView.findViewById(R.id.txtDensity);
        TextView txtMultitouch = (TextView)rootView.findViewById(R.id.txtMultitouch);
        
        String model = android.os.Build.MODEL;
        if(model.contains("Ascend G300") || model.contains("Nexus S")
        		|| model.contains("Lenovo A380")) {
	        txtRes.setText("800 x 480 px");
	        txtResDp.setText("533 x 320 dp");
			txtDPIX.setText("233.24 dpi");
			txtDPIY.setText("233.24 dpi");
			txtDPI.setText("240 dpi");
        	txtSize.setText("Normal");
        	txtDensity.setText("High");
        } else if(model.contains("i-mobile i-note WiFi 9")) {
	        txtRes.setText("800 x 480 px");
	        txtResDp.setText("1066 x 640 dp");
			txtDPIX.setText("103.66 dpi");
			txtDPIY.setText("103.66 dpi");
			txtDPI.setText("120 dpi");
        	txtSize.setText("Large");
        	txtDensity.setText("Low");
        } else {	        
	        txtRes.setText(ScreenInfo.getResolutionPX(getActivity()));
	        txtResDp.setText(ScreenInfo.getResolutionDP(getActivity()));
			txtDPI.setText(ScreenInfo.getDpi(getActivity()));
			txtDPIX.setText(ScreenInfo.getDpiX(getActivity()));
			txtDPIY.setText(ScreenInfo.getDpiY(getActivity()));
        	txtDensity.setText(ScreenInfo.getDensity(getActivity()));
        	txtSize.setText(ScreenInfo.getScreenSize(getActivity()));
        }
        
        txtMultitouch.setText(ScreenInfo.getMultitouch(getActivity()));

		layoutLoading.setVisibility(View.GONE);
		scrollScreen.setVisibility(View.VISIBLE);
		
		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

    public double trimDecimal(double val, int point) {
    	return (double)((int)(val * Math.pow(10, point))) / Math.pow(10, point);
    }
}
