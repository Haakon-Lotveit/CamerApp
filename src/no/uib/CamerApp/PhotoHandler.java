package no.uib.CamerApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import no.uib.CamerApp.tasks.PhotoWriteTask;
import no.uib.CamerApp.tasks.SFTPTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {

	private final Context context;
	private ImageView imageView;

	public PhotoHandler(Context context, ImageView imageView) {
		this.context = context;
		this.imageView = imageView;
		//    File f = getDir();
		//    f.mkdir();
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {

		//    File pictureFileDir = getDir();
		//
		//    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
		//
		//      Log.d(MakePhotoActivity.DEBUG_TAG, "Can't create directory to save image.");
		//      Toast.makeText(context, "Can't create directory to save image.",
		//          Toast.LENGTH_LONG).show();
		//      return;
		//
		//    }

		//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		//    String date = dateFormat.format(new Date());
		//    String photoFile = "Picture_" + date + ".jpg";
		//
		//    String filename = pictureFileDir.getPath() + File.separator + photoFile;
		//    File pictureFile = new File(filename);

		// PhotoWriter thread
		//    new PhotoWriteTask().execute(pictureFileDir, data, context);
		//		try {
		//		new PhotoWriteTask().execute(data, context);
		//		Toast.makeText(context, "phtohandler woiks",
		//				Toast.LENGTH_LONG).show();

		//		new Thread(new PhotoRunnable(data, context)).start();

		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//    try {
		//      FileOutputStream fos = new FileOutputStream(pictureFile);
		//      fos.write(data);
		//      fos.close();
		//      Toast.makeText(context, "New Image saved:" + photoFile,
		//          Toast.LENGTH_LONG).show();

		// Trying to show image.
		//      Bitmap bmp;
		//      BitmapFactory.Options options = new BitmapFactory.Options();
		//      options.outWidth = 600;
		//      options.inMutable = true;
		//      bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		//      ImageView imageView = new ImageView(context);

		//      imageView.setImageBitmap(bmp);




		//SCP to server
		// Need new thread. Or else NetworkOnMainThreadException
		//		try {
//				File[] listFiles = context.getFilesDir().listFiles();
//				if( listFiles.length > 0 ) {
//					for(int i = 0 ; i < listFiles.length ; i++ ) {
//						new SFTPTask().execute(listFiles[i]);
//					}
//				} else {
//					Toast.makeText(context, "files folder empty",
//							Toast.LENGTH_LONG).show();
//				}
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
		//      new Thread(new Runnable() {
		//          public void run() {
		//        	  sendToServer(pictureFile);
		//          }
		//      }).start();

		//    } catch (Exception error) {
		//      Log.d(MakePhotoActivity.DEBUG_TAG, "File" + filename + "not saved: "
		//          + error.getMessage());
		//      Toast.makeText(context, "Image could not be saved.",
		//          Toast.LENGTH_LONG).show();
		//    }


		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String date = dateFormat.format(new Date());
		String photoFile = "Picture_" + date + ".jpg";

		//	    String filename = pictureFileDir.getPath() + File.separator + photoFile;
		//	    File pictureFile = new File(filename);

		//	    File file = new File(context.getFilesDir(), photoFile);
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(photoFile, Context.MODE_PRIVATE);
			fos.write(data);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException r) {
			r.printStackTrace();
		}
		finally {
			if(fos != null) {

				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Trying to show image.
//		Bitmap bmp;
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.outWidth = 600;
//		options.inMutable = true;
//		bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);
////		ImageView imageView = new ImageView(context);
//		imageView.setImageBitmap(bmp);



		//		Toast.makeText(context, "New Image saved:" + pictureFile.getName(),
		//				Toast.LENGTH_LONG).show();
		Toast.makeText(context, "Success!",
				Toast.LENGTH_LONG).show();

	}



	private File getDir() {
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		Toast.makeText(context, sdDir.getAbsolutePath(),
				Toast.LENGTH_LONG).show();
		return new File(sdDir, "CameraAPIDemo");
	}
}