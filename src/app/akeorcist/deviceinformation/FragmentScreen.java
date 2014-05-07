package app.akeorcist.deviceinformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentScreen extends Fragment {
	ScreenPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	LinearLayout layoutLoading;
	TextView btnScreenNext, btnScreenPrev;
	
	public static FragmentScreen newInstance() {
		FragmentScreen fragment = new FragmentScreen();
		return fragment;
	}

	public FragmentScreen() { }

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_screen, container,
				false);

		mSectionsPagerAdapter = new ScreenPagerAdapter(getChildFragmentManager());

		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		
		mViewPager = (ViewPager)rootView.findViewById(R.id.pagerScreen);
		mViewPager.setVisibility(View.GONE);

		btnScreenNext = (TextView)rootView.findViewById(R.id.btnScreenNext);
		btnScreenNext.setVisibility(View.GONE);
		
		btnScreenPrev = (TextView)rootView.findViewById(R.id.btnScreenPrev);
		btnScreenPrev.setVisibility(View.GONE);		
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				try {
					mViewPager.setAdapter(mSectionsPagerAdapter);
					mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
						public void onPageSelected(int arg0) {
							if(arg0 == 0) {
								((ActionBarActivity)getActivity()).getSupportActionBar().show();
								btnScreenPrev.setVisibility(View.GONE);
								btnScreenNext.setVisibility(View.VISIBLE);
							} else if(arg0 == 1) {
								btnScreenNext.setVisibility(View.GONE);
								btnScreenPrev.setVisibility(View.VISIBLE);					
								
								((ActionBarActivity)getActivity()).getSupportActionBar().hide();
								
								if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
									int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
									View decorView = getActivity().getWindow().getDecorView();
									decorView.setSystemUiVisibility(uiOptions);
								}
								
								Button layoutScreenMeasurement = (Button)rootView.findViewById(R.id.buttonScreenShare);
								layoutScreenMeasurement.setOnClickListener(new OnClickListener() {
									public void onClick(View v) {
										File file = new File(Environment.getExternalStorageDirectory() 
												+ "/Device_Information/screen_measurement.png");
										Intent intent = new Intent(Intent.ACTION_SEND);
							            String mimeType = MimeTypeMap.getSingleton()
							                    .getMimeTypeFromExtension(MimeTypeMap
							                            .getFileExtensionFromUrl(file.getPath()));
							            intent.setType(mimeType);
							            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
							            startActivity(Intent.createChooser(intent, "Share file via"));
									}
								});
								
								File file = new File(Environment.getExternalStorageDirectory() 
										+ "/Device_Information/screen_measurement.png");
								if(!file.exists()) {
									new Handler().postDelayed(new Runnable() {
										public void run() {
											View v = rootView.findViewById(R.id.layoutScreenMeasurement);
											v.setDrawingCacheEnabled(true);
											Bitmap bm = Bitmap.createBitmap(v.getDrawingCache());
											v.setDrawingCacheEnabled(false);
											
											File dir = new File(Environment.getExternalStorageDirectory() 
													+ "/Device_Information");
											dir.mkdirs();
											File file = new File(dir.getPath() + "/screen_measurement.png");
											
											FileOutputStream out;
											try {
												out = new FileOutputStream(file);
												ByteArrayOutputStream bos = new ByteArrayOutputStream();
												bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
												out.write(bos.toByteArray());
												out.close();
											} catch (FileNotFoundException e) {
												e.printStackTrace();
											} catch (IOException e) {
												e.printStackTrace();
											}
										}
									}, 500);
								}
							}
						}
						
						public void onPageScrolled(int arg0, float arg1, int arg2) { }
						
						public void onPageScrollStateChanged(int arg0) { }
					});
	
					if(mSectionsPagerAdapter.getCount() < 2) 
						btnScreenNext.setVisibility(View.GONE);
					else 
						btnScreenNext.setVisibility(View.VISIBLE);
						
					btnScreenNext.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
						} 
					});
	
					btnScreenPrev.setVisibility(View.GONE);
					btnScreenPrev.setOnClickListener(new OnClickListener() {
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
	
	public class ScreenPagerAdapter extends FragmentPagerAdapter {

		public ScreenPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@SuppressLint("NewApi")
		public Fragment getItem(int position) {
			if(position == 0)
				return FragmentScreenSub1.newInstance();
			else if(position == 1)
				return FragmentScreenSub2.newInstance();
			return null;
		}

		public int getCount() {
			return 2;
		}

		public CharSequence getPageTitle(int position) {
			return null;
		}
	}
}
