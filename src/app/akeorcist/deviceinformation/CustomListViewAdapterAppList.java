package app.akeorcist.deviceinformation;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapterAppList extends ArrayAdapter<String> {
	ArrayList<String> arr_name_list, arr_package_list, arr_header; 
	LayoutInflater inflater;
	Context context;
    
	public CustomListViewAdapterAppList(Context context, int textViewResourceId
			, ArrayList<String> name, ArrayList<String> pkg, ArrayList<String> header) {
		super(context, textViewResourceId, name);
		this.context = context;
        arr_name_list = name;
		arr_package_list = pkg;
		arr_header = header;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
 
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = null;
		
		if(arr_header.get(position).equals("")) {
			row = inflater.inflate(R.layout.listview_row_applist, parent, false);
			TextView textView1 = (TextView) row.findViewById(R.id.text1);
			textView1.setText(arr_name_list.get(position));
			
			TextView textView2 = (TextView) row.findViewById(R.id.text2);
			textView2.setText(arr_package_list.get(position));
			try{
				Drawable icon = context.getPackageManager().getApplicationIcon(arr_package_list.get(position));
				ImageView imageView = (ImageView) row.findViewById(R.id.imageView1);
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					imageView.setBackground(icon);
				} else {
					imageView.setBackgroundDrawable(icon);
				}
			} catch (PackageManager.NameNotFoundException e) { }
		} else {
			row = inflater.inflate(R.layout.listview_row_applist_header, parent, false);
			TextView textView1 = (TextView) row.findViewById(R.id.text1);
			textView1.setText(arr_header.get(position));
		}
		
		return row;
	}
}