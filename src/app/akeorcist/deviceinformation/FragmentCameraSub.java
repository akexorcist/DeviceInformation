package app.akeorcist.deviceinformation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class FragmentCameraSub extends Fragment {

	private static final String BUNDLE_CAMERA_ID = "camera_id";
	
	TextView txtCameraId, txtAntibanding, txtCandisableShutterSound, txtColorEffect
			, txtFacing, txtFlashMode, txtFocusMode, txtJpegThumbnailSize
			, txtPictureFormat, txtOrientation, txtPreviewFormat, txtPreviewFrameRate
			, txtPictureSize, txtPreviewSize, txtPreviewFpsRange, txtQualityProfile
			, txtTimelapseQualityProfile, txtSceneMode, txtVideoSize, txtWhiteBalance;
		
	ScrollView scrollCamera;
	
	int cameraId = -1;

	Camera.Parameters cameraParams = null;
	
	public static FragmentCameraSub newInstance(int cameraId) {
		FragmentCameraSub fragment = new FragmentCameraSub();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_CAMERA_ID, cameraId);		
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentCameraSub() { }

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		cameraId = getArguments().getInt(BUNDLE_CAMERA_ID);

		View rootView = inflater.inflate(R.layout.fragment_camera_sub, container, false);
		
		scrollCamera = (ScrollView)rootView.findViewById(R.id.scrollCamera);
		
		txtCameraId = (TextView)rootView.findViewById(R.id.txtCameraId);
		txtAntibanding = (TextView)rootView.findViewById(R.id.txtAntibanding);
		txtCandisableShutterSound = (TextView)rootView.findViewById(R.id.txtCanDisableShutterSound);
		txtColorEffect = (TextView)rootView.findViewById(R.id.txtColorEffect);
		txtFacing = (TextView)rootView.findViewById(R.id.txtFacing);
		txtFlashMode = (TextView)rootView.findViewById(R.id.txtFlashMode);
		txtFocusMode = (TextView)rootView.findViewById(R.id.txtFocusMode);
		txtJpegThumbnailSize = (TextView)rootView.findViewById(R.id.txtJpegThumbnailSize);
		txtOrientation = (TextView)rootView.findViewById(R.id.txtOrientation);
		txtPictureFormat = (TextView)rootView.findViewById(R.id.txtPictureFormat);
		txtPreviewFormat = (TextView)rootView.findViewById(R.id.txtPreviewFormat);
		txtPreviewFrameRate = (TextView)rootView.findViewById(R.id.txtPreviewFrameRate);
		txtPictureSize = (TextView)rootView.findViewById(R.id.txtPictureSize);
		txtPreviewSize = (TextView)rootView.findViewById(R.id.txtPreviewSize);
		txtPreviewFpsRange = (TextView)rootView.findViewById(R.id.txtPreviewFpsRange);
		txtQualityProfile = (TextView)rootView.findViewById(R.id.txtQualityProfile);
		txtTimelapseQualityProfile = (TextView)rootView.findViewById(R.id.txtTimelapseQualityProfile);
		txtSceneMode = (TextView)rootView.findViewById(R.id.txtSceneMode);
		txtVideoSize = (TextView)rootView.findViewById(R.id.txtVideoSize);
		txtWhiteBalance = (TextView)rootView.findViewById(R.id.txtWhiteBalance);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			Camera mCamera = Camera.open(cameraId);
			cameraParams = mCamera.getParameters();
			mCamera.release();
		} else {
			Camera mCamera = Camera.open();
			cameraParams = mCamera.getParameters();
			mCamera.release();
		}
		
		txtCameraId.setText("• " + cameraId);
		
		String str = InfoManager.CameraInfo.getSupportedAntibanding(cameraParams);
		txtAntibanding.setText(str);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			CameraInfo ci = new CameraInfo();
			Camera.getCameraInfo(cameraId, ci);
			str = InfoManager.CameraInfo.canDisableShutterSound(ci);
		} else {
			str = "null";
		}
		txtCandisableShutterSound.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedColorEffects(cameraParams);
		txtColorEffect.setText(str);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			CameraInfo ci = new CameraInfo();
			Camera.getCameraInfo(cameraId, ci);
			str = InfoManager.CameraInfo.getCameraFacing(ci);
		} else {
			str = "null";
		}
		txtFacing.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedFlashModes(cameraParams);
		txtFlashMode.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedFocusModes(cameraParams);
		txtFocusMode.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedJpegThumbnailSizes(cameraParams);
		txtJpegThumbnailSize.setText(str);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			CameraInfo ci = new CameraInfo();
			Camera.getCameraInfo(cameraId, ci);
			str = InfoManager.CameraInfo.getImageOrientation(ci);
		} else {
			str = "null";
		}
		txtOrientation.setText(str);

		str = InfoManager.CameraInfo.getSupportedPictureFormats(cameraParams);
		txtPictureFormat.setText(str);

		str = InfoManager.CameraInfo.getSupportedPreviewFormats(cameraParams);
		txtPreviewFormat.setText(str);

		str = InfoManager.CameraInfo.getSupportedPreviewFrameRates(cameraParams);
		txtPreviewFrameRate.setText(str);

		str = InfoManager.CameraInfo.getSupportedPictureSizes(cameraParams);
		txtPictureSize.setText(str);

		str = InfoManager.CameraInfo.getSupportedPreviewSizes(cameraParams);
		txtPreviewSize.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedPreviewFpsRange(cameraParams);
		txtPreviewFpsRange.setText(str);
		
		str = InfoManager.CameraInfo.getQualityProfile(cameraParams, cameraId);
		txtQualityProfile.setText(str);

		str = InfoManager.CameraInfo.getQualityTimeLapseProfile(cameraParams, cameraId);
		txtTimelapseQualityProfile.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedSceneModes(cameraParams);
		txtSceneMode.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedVideoSizes(cameraParams);			
		txtVideoSize.setText(str);
		
		str = InfoManager.CameraInfo.getSupportedWhiteBalance(cameraParams);
		txtWhiteBalance.setText(str);
				
		return rootView;
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
}
