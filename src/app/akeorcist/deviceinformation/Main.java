package app.akeorcist.deviceinformation;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.support.v4.widget.DrawerLayout;

public class Main extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private final String PREFERENCE_NAME = "Preference";
	private final String KEYWORD_NAME = "isFirstRun130";
	
	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;
	
	private int current_position = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
				
		ActionBar ab = getSupportActionBar();
		String strColor = getResources().getString(R.color.action_bar_bg);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor(strColor)));

		String model = android.os.Build.MODEL;
        if(!model.contains("Nokia_X") || !model.contains("Nokia_XL")) {
	        SharedPreferences settings = getSharedPreferences(PREFERENCE_NAME, 0);
		    boolean isFirstRun = settings.getBoolean(KEYWORD_NAME, true);
		    if(isFirstRun) {
		    	final Dialog dialog = new Dialog(Main.this);
		        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		        dialog.setContentView(R.layout.dialog_whatsnew);
		        dialog.setCancelable(false);
		        dialog.setOnDismissListener(new OnDismissListener() {
					public void onDismiss(DialogInterface dialog) {
						SharedPreferences settings = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = settings.edit();
						editor.putBoolean(KEYWORD_NAME, false);
						editor.commit();
					}
				});
		        
		        TextView textWhatnewOK = (TextView)dialog.findViewById(R.id.textWhatnewOK);
		        textWhatnewOK.setOnClickListener(new OnClickListener() {
		        	public void onClick(View v) {
		        		dialog.dismiss();
		        	}
		        });
		        
				dialog.show();
		    }
        }
        
		mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

        if(model.contains("Nokia_X") || model.contains("Nokia_XL")) {
        	Fragment fragment = FragmentHardware.newInstance();
    		FragmentManager fragmentManager = getSupportFragmentManager();
    		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else {
        	Fragment fragment = FragmentTesting.newInstance();
    		FragmentManager fragmentManager = getSupportFragmentManager();
    		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
		onSectionAttached(0);
		
		mNavigationDrawerFragment.showNavigationDrawer();  
		 

		
	    
	}

	@SuppressLint("InlinedApi")
	public void onNavigationDrawerItemSelected(int position) {
		Fragment fragment = null;
		if(current_position != position) {
			String model = android.os.Build.MODEL;
	        if(model.contains("Nokia_X") || model.contains("Nokia_XL")) {
				if(position != 6) {
				        if(position == 0) {
							fragment = FragmentHardware.newInstance();
						} else if(position == 1) {
							fragment = FragmentSensor.newInstance();
						} else if(position == 2) {
							fragment = FragmentScreen.newInstance();
						} else if(position == 3) {
							fragment = FragmentCamera.newInstance();
						} else if(position == 4) {
							fragment = FragmentFeature.newInstance();
						} else if(position == 5) {
							fragment = FragmentAppList.newInstance();
						}
					
					if(!getSupportActionBar().isShowing())
						getSupportActionBar().show();
					FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
					current_position = position;
					onSectionAttached(position);	
				} else {
					finish();
				}
			} else {
				if(position != 7) {
					if(position == 0) {
						fragment = FragmentTesting.newInstance();
					} else if(position == 1) {
						fragment = FragmentHardware.newInstance();
					} else if(position == 2) {
						fragment = FragmentSensor.newInstance();
					} else if(position == 3) {
						fragment = FragmentScreen.newInstance();
					} else if(position == 4) {
						fragment = FragmentCamera.newInstance();
					} else if(position == 5) {
						fragment = FragmentFeature.newInstance();
					} else if(position == 6) {
						fragment = FragmentAppList.newInstance();
					}
				
					if(!getSupportActionBar().isShowing())
						getSupportActionBar().show();
					FragmentManager fragmentManager = getSupportFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
					current_position = position;
					onSectionAttached(position);	
				} else {
					finish();
				}
			}
		}
	}

	public void onSectionAttached(int number) {

		String model = android.os.Build.MODEL;
        if(model.contains("Nokia_X") || model.contains("Nokia_XL")) {
			switch (number) {
			case 0: mTitle = getString(R.string.menu_hardware); break;
			case 1: mTitle = getString(R.string.menu_sensor); break;
			case 2: mTitle = getString(R.string.menu_screen); break;
			case 3: mTitle = getString(R.string.menu_camera); break;
			case 4: mTitle = getString(R.string.menu_features); break;
			case 5: mTitle = getString(R.string.menu_applist); break;
			case 6: mTitle = getString(R.string.menu_exit); break;
			}
        } else {
			switch (number) {
			case 0: mTitle = getString(R.string.menu_testing); break;
			case 1: mTitle = getString(R.string.menu_hardware); break;
			case 2: mTitle = getString(R.string.menu_sensor); break;
			case 3: mTitle = getString(R.string.menu_screen); break;
			case 4: mTitle = getString(R.string.menu_camera); break;
			case 5: mTitle = getString(R.string.menu_features); break;
			case 6: mTitle = getString(R.string.menu_applist); break;
			case 7: mTitle = getString(R.string.menu_exit); break;
			}
        }
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		int color = getResources().getColor(R.color.action_bar_bg);
		ColorDrawable colorDrawable = new ColorDrawable(color);
		actionBar.setBackgroundDrawable(colorDrawable);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	public void onBackPressed() {		
		if(mNavigationDrawerFragment.isVisible()) {
			mNavigationDrawerFragment.hideNavigationDrawer();   
		} else {
			super.onBackPressed();
		}
	}
}
