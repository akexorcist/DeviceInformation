package app.akeorcist.deviceinformation;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentCamera extends Fragment {
	SensorPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	List<Sensor> listSensor;
	TextView btnCameraNext, btnCameraPrev;
	LinearLayout layoutLoading;
	
	int cameraCount = 0;
	
	public static FragmentCamera newInstance() {
		FragmentCamera fragment = new FragmentCamera();
		return fragment;
	}

	public FragmentCamera() { }

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_camera, container, false);

		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		
		mViewPager = (ViewPager)rootView.findViewById(R.id.pagerCamera);
		mViewPager.setVisibility(View.GONE);
		
		btnCameraNext = (TextView)rootView.findViewById(R.id.btnCameraNext);
		btnCameraNext.setVisibility(View.GONE);
		
		btnCameraPrev = (TextView)rootView.findViewById(R.id.btnCameraPrev);
		btnCameraPrev.setVisibility(View.GONE);
		
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				try {
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
						cameraCount = Camera.getNumberOfCameras();
					} else {
						Camera cam = Camera.open();
						if(cam == null) 
							cameraCount = 0;
						else 
							cameraCount = 1;
					}	
					
					mSectionsPagerAdapter = new SensorPagerAdapter(getChildFragmentManager());
					
					mViewPager = (ViewPager)rootView.findViewById(R.id.pagerCamera);
					mViewPager.setAdapter(mSectionsPagerAdapter);
					mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
						public void onPageSelected(int arg0) {
							if(arg0 == 0)
								btnCameraPrev.setVisibility(View.GONE);
							else 
								btnCameraPrev.setVisibility(View.VISIBLE);
							
							if(arg0 == mSectionsPagerAdapter.getCount() - 1)
								btnCameraNext.setVisibility(View.GONE);
							else 
								btnCameraNext.setVisibility(View.VISIBLE);
						}
						
						public void onPageScrolled(int arg0, float arg1, int arg2) { }
						
						public void onPageScrollStateChanged(int arg0) { }
					});
	
					if(mSectionsPagerAdapter.getCount() < 2) 
						btnCameraNext.setVisibility(View.GONE);
					else
						btnCameraNext.setVisibility(View.VISIBLE);
					btnCameraNext.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
						} 
					});
					
					btnCameraPrev.setVisibility(View.GONE);
					btnCameraPrev.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
						} 
					});
					
					mViewPager.setVisibility(View.VISIBLE);
					layoutLoading.setVisibility(View.GONE);
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

	public class SensorPagerAdapter extends FragmentPagerAdapter {
		public SensorPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@SuppressLint("NewApi")
		public Fragment getItem(int position) {
			return FragmentCameraSub.newInstance(position);
		}

		public int getCount() {
			return cameraCount;
		}

		public CharSequence getPageTitle(int position) {
			return null;
		}
	}
}
