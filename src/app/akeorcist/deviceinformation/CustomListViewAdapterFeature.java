package app.akeorcist.deviceinformation;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewAdapterFeature extends ArrayAdapter<String> {
	ArrayList<String> arr_list; 
	LayoutInflater INFLATER;
	Context mContext;
    
	public CustomListViewAdapterFeature(Context context, int textViewResourceId, ArrayList<String> objects) {
		super(context, textViewResourceId, objects);
		mContext = context;
		INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arr_list = objects;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = null;
		if(arr_list.get(position).contains("header")) {
			row = INFLATER.inflate(R.layout.listview_row_header_feature, parent, false);
			
			TextView textView = (TextView) row.findViewById(R.id.textFeature);
			
			if(arr_list.get(position).equals("header1"))
				textView.setText("Supported Features");
			else if(arr_list.get(position).equals("header2")) 
				textView.setText("Unsupported Features");
				
		} else {
			row = INFLATER.inflate(R.layout.listview_row_feature, parent, false);
			
			TextView textView = (TextView) row.findViewById(R.id.textFeature);
			textView.setText(arr_list.get(position));
		}
		return row;
	}
	
	public String getString(int position) {
		return arr_list.get(position);
	}
}