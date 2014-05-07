package app.akeorcist.deviceinformation;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
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
import app.akeorcist.deviceinformation.InfoManager.SensorInfo;

public class FragmentSensor extends Fragment {
	SensorPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	List<Sensor> listSensor;
	TextView btnSensorNext, btnSensorPrev;
	LinearLayout layoutLoading;
	
	public static FragmentSensor newInstance() {
		FragmentSensor fragment = new FragmentSensor();
		return fragment;
	}

	public FragmentSensor() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SensorManager mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
		listSensor = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		
		View rootView = inflater.inflate(R.layout.fragment_sensor, container, false);

		mSectionsPagerAdapter = new SensorPagerAdapter(getChildFragmentManager());
		
		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		
		mViewPager = (ViewPager)rootView.findViewById(R.id.pagerSensor);
		mViewPager.setVisibility(View.GONE);
		
		btnSensorNext = (TextView)rootView.findViewById(R.id.btnSensorNext);
		btnSensorNext.setVisibility(View.GONE);
		
		btnSensorPrev = (TextView)rootView.findViewById(R.id.btnSensorPrev);
		btnSensorPrev.setVisibility(View.GONE);
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				try {
					mViewPager.setAdapter(mSectionsPagerAdapter);
					mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
						public void onPageSelected(int arg0) {
							if(arg0 == 0)
								btnSensorPrev.setVisibility(View.GONE);
							else 
								btnSensorPrev.setVisibility(View.VISIBLE);
							
							if(arg0 == mSectionsPagerAdapter.getCount() - 1)
								btnSensorNext.setVisibility(View.GONE);
							else 
								btnSensorNext.setVisibility(View.VISIBLE);
						}
						
						public void onPageScrolled(int arg0, float arg1, int arg2) { }
						
						public void onPageScrollStateChanged(int arg0) { }
					});
					
					if(mSectionsPagerAdapter.getCount() < 2) 
						btnSensorNext.setVisibility(View.GONE);
					else 
						btnSensorNext.setVisibility(View.VISIBLE);
					btnSensorNext.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
						} 
					});
					
					btnSensorPrev.setVisibility(View.GONE);
					btnSensorPrev.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
						} 
					});
	
					layoutLoading.setVisibility(View.GONE);
					mViewPager.setVisibility(View.VISIBLE);
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
			Sensor sensor = listSensor.get(position);
			return FragmentSensorSub.newInstance(SensorInfo.getName(sensor)
					, SensorInfo.getVendor(sensor), sensor.getType()
					, SensorInfo.getVersion(sensor), SensorInfo.getPower(sensor)
					, SensorInfo.getMaximumRange(sensor), SensorInfo.getResolution(sensor)
					, SensorInfo.getMinDelay(sensor), SensorInfo.getFifoReservedEventCount(sensor)
					, SensorInfo.getFifoMaxEventCount(sensor)
					);
		}

		public int getCount() {
			return listSensor.size();
		}

		public CharSequence getPageTitle(int position) {
			return null;
		}
	}
}
