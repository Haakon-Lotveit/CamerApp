package no.uib.CamerApp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import no.uib.CamerApp.tasks.SFTPTask;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MakePhotoActivity extends Activity {
	protected final static String DEBUG_TAG = "MakePhotoActivity";
	private Camera camera;
	private int cameraId = 0;
	private ImageView imageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
		this.imageView = (ImageView)this.findViewById(R.id.imageView1);
		// Test to see if GL_INVALID_oPERATION goes away
		//    imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		//		camera = Camera.open();
		// do we have a camera?
		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
			.show();
		} else {
			//			cameraId = findBackFacingCamera();
			Toast.makeText(this, "You got cameraaaaaas", Toast.LENGTH_LONG)
			.show();
			if (cameraId < 0) {
				Toast.makeText(this, "No front facing camera found.",
						Toast.LENGTH_LONG).show();
			} else {
				//        camera = Camera.open(cameraId);
				//        camera.release();
			}
		}
	}

	public void onClick(View view) {
		//	  camera.startPreview();

		//	  try {
		//		    Thread.sleep(1000);
		//		} catch(InterruptedException ex) {
		//		    Thread.currentThread().interrupt();
		//		}
		//	 camera = Camera.open();
		//		PhotoHandler pHandler = new PhotoHandler(getApplicationContext(), this.imageView);
		//		camera.takePicture(null, null,
		//				pHandler);
		//    camera.release();
		//    camera.stopPreview();
		//		camera.takePicture(shutter, raw, jpeg);
		//		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		//	        startActivityForResult(takePictureIntent, 1);
		//	    }
		dispatchTakePictureIntent();

		//		File[] listFiles = this.getFilesDir().listFiles();
		File[] listFiles = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles();
		Toast.makeText(this, "numer of files: " + listFiles.length, Toast.LENGTH_LONG)
		.show();
		if( listFiles.length > 0 ) {
			for(int i = 0 ; i < listFiles.length ; i++ ) {
				new SFTPTask().execute(listFiles[i]);
				//				listFiles[i].delete();
			}
		} else {
			Toast.makeText(this, "files folder empty",
					Toast.LENGTH_LONG).show();
		}

	}

	private int findBackFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
				Log.d(DEBUG_TAG, "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	@Override
	protected void onResume() {
		super.onResume();

		//			camera = Camera.open();

	}

	@Override
	protected void onPause() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
		super.onPause();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//		new SFTPTask().execute(listFiles[i]);
		//		new PhotoWriteTask().execute(data, this);

		InputStream stream = null;
		Bitmap bitmap = null;
		if (requestCode == 1 && resultCode == Activity.RESULT_OK)
			try {
				//				 Bundle extras = data.getExtras();
				//			        Bitmap imageBitmap = (Bitmap) extras.get("data");
				//			        mImageView.setImageBitmap(imageBitmap);
				// recyle unused bitmaps
				if (bitmap != null) {
					bitmap.recycle();
				}
				//				stream = getContentResolver().openInputStream(data.getData());
				//				bitmap = BitmapFactory.decodeStream(stream);
				if(data != null) {
					Bundle extras = data.getExtras();
					bitmap = (Bitmap) extras.get("data");
					imageView.setImageBitmap(bitmap);
				}
			} finally {

				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

	}

	static final int REQUEST_TAKE_PHOTO = 1;
	private void dispatchTakePictureIntent() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
				ex.printStackTrace();
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
				//				startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
			}
		}
	}

	private File createImageFile() throws IOException {
		//		String mCurrentPhotoPath;
		// Create an image file name
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		//		DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.DEFAULT);
		String date = dateFormat.format(new Date());
		String imageFileName = "Picture_" + date;
		//	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		//	    String imageFileName = "JPEG_" + timeStamp + "_";
		//	    File storageDir = Environment.getExternalStoragePublicDirectory(
		//	            Environment.DIRECTORY_PICTURES);
		//		ile storageDir = this.getFilesDir();
		File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
				);
		//		image.createNewFile();
		// Save a file: path for use with ACTION_VIEW intents
		//	    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}
}