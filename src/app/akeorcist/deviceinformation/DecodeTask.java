package app.akeorcist.deviceinformation;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DecodeTask extends AsyncTask<String, Void, Drawable> {
	Context mContext;
	ImageView v;
	String pgk;
	
	public DecodeTask(Context context, ImageView iv, String pgk) {
		mContext = context;
	    v = iv;
	    this.pgk = pgk;
	}

	protected Drawable doInBackground(String... params) {
	    try {
			return mContext.getPackageManager().getApplicationIcon(pgk);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected void onPostExecute(Drawable result) {
		v.setBackground(result);
	}
}	