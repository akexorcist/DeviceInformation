package app.akeorcist.deviceinformation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class FragmentScreenSub2 extends Fragment {
	RelativeLayout layoutScreenMeasurement;
	View decorView;

	int fullscreen = -1;
	boolean isFullscreen = true;
	
	long currentTime = 0;
	
	public static FragmentScreenSub2 newInstance() {
		FragmentScreenSub2 fragment = new FragmentScreenSub2();
		return fragment;
	}

	@SuppressLint("InlinedApi")
	public FragmentScreenSub2() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) 
			fullscreen = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
		else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) 
			fullscreen = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
	}

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_screen_sub2, container,
				false);
		
		layoutScreenMeasurement = (RelativeLayout)rootView.findViewById(R.id.layoutScreenMeasurement);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			decorView = getActivity().getWindow().getDecorView();
			decorView.setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
				public void onSystemUiVisibilityChange(int visibility) {
					if(visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
						currentTime = System.currentTimeMillis();
					}
				}
			});
			
			new Handler().postDelayed(new Runnable() {
				public void run() {
					layoutScreenMeasurement.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							if(System.currentTimeMillis() - currentTime > 1000) {
								decorView.setSystemUiVisibility(fullscreen);
								currentTime = System.currentTimeMillis();
							}
						}
					});
				}
			}, 1000);
		} else {
			getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
					, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			layoutScreenMeasurement.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Window window = getActivity().getWindow();
					if(isFullscreen) {
						window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
						isFullscreen = false;
					} else {
						window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
								, WindowManager.LayoutParams.FLAG_FULLSCREEN);
						isFullscreen = true;
					}
					
				}
			});
		}
		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
}
