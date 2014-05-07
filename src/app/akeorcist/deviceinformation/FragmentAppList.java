package app.akeorcist.deviceinformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FragmentAppList extends Fragment {
	final int TYPE_SYSTEM_APP = 1;
	final int TYPE_DOWNLOAD_APP = 0;
		
	ArrayList<String> name_list;
	ArrayList<String> package_list;
	ArrayList<String> header_list;
	
	ListView listView;
	LinearLayout layoutLoading;
	
	public static FragmentAppList newInstance() {
		FragmentAppList fragment = new FragmentAppList();
		return fragment;
	}

	public FragmentAppList() { }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_applist, container,
				false);
		
		listView = (ListView)rootView.findViewById(R.id.listApp);
		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		
		name_list = new ArrayList<String>();
		package_list = new ArrayList<String>();
		header_list = new ArrayList<String>();

		name_list.add("");
		package_list.add("");
		header_list.add("Downloaded Apps");
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
				try {
					getAppList(TYPE_DOWNLOAD_APP);
					name_list.add("");
					package_list.add("");
					header_list.add("System Apps");
					getAppList(TYPE_SYSTEM_APP);
					
					getActivity().runOnUiThread(new Runnable() {
						public void run() {
							try {
								layoutLoading.setVisibility(View.GONE);
								CustomListViewAdapterAppList adapter = new CustomListViewAdapterAppList(
										getActivity().getApplicationContext(), android.R.id.text1
										, name_list, package_list, header_list);
						        listView.setAdapter(adapter);
							} catch (NullPointerException e){ 
								e.printStackTrace();
							}
						}
					});
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
	
	public void getAppList(int TYPE) {		
		PackageManager pm = getActivity().getPackageManager();
		List<ApplicationInfo> apps = pm.getInstalledApplications(0);
		
		ArrayList<String> app_name_list = new ArrayList<String>();
		ArrayList<String> app_package_list = new ArrayList<String>();

		for(ApplicationInfo appInfo : apps) {
		    try {
		    	if((appInfo.flags  & ApplicationInfo.FLAG_SYSTEM) == TYPE) {
			        String package_name = appInfo.packageName;
			        String app_name = null;
			        try{
				        app_name = (String)pm.getApplicationLabel(
				        		pm.getApplicationInfo(package_name
				        		, PackageManager.GET_META_DATA));
			        } catch(NotFoundException e) {
			        	e.printStackTrace();
			        	app_name = package_name;
			        }
			        boolean same = false;
			        for(int i = 0 ; i < app_name_list.size() ; i++) {
			        	if(package_name.equals(app_package_list.get(i)))
			        		same = true;
			        }
			        if(!same) {
				        app_name_list.add(app_name);
				        app_package_list.add(package_name);
			        }
		    	}
		    } catch(Exception e) { 
		    	e.printStackTrace();
		    }
		}
		

		ArrayList<String> app_name_temp = new ArrayList<String>();
		ArrayList<String> app_package_temp = new ArrayList<String>();
		
		for(int i = 0 ; i < app_name_list.size() ; i++) {
			if(i == 0) {
				app_name_temp.add(app_name_list.get(i));
				app_package_temp.add(app_package_list.get(i));
			} else {
				String str1 = app_name_list.get(i).toLowerCase(Locale.getDefault());
				for(int j = 0 ; j < app_name_temp.size() ; j++) {
					String str2 = app_name_temp.get(j).toLowerCase(Locale.getDefault());
					if(str1.compareTo(str2) < 0) {
						app_name_temp.add(j, app_name_list.get(i));
						app_package_temp.add(j, app_package_list.get(i));
						break;
					} else if(j == app_name_temp.size() - 1) {
						app_name_temp.add(app_name_list.get(i));
						app_package_temp.add(app_package_list.get(i));
						break;
					}
				}
			}
		}
		
		for(int i = 0 ; i < app_name_temp.size() ; i++) {
			name_list.add(app_name_temp.get(i));
			package_list.add(app_package_temp.get(i));
			header_list.add("");
		}
	}
}
