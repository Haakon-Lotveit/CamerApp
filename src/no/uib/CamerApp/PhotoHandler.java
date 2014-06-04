package no.uib.CamerApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class PhotoHandler implements PictureCallback {

  private final Context context;
private ImageView imageView;

  public PhotoHandler(Context context, ImageView imageView) {
    this.context = context;
    this.imageView = imageView;
  }

  @Override
  public void onPictureTaken(byte[] data, Camera camera) {

    File pictureFileDir = getDir();

    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

      Log.d(MakePhotoActivity.DEBUG_TAG, "Can't create directory to save image.");
      Toast.makeText(context, "Can't create directory to save image.",
          Toast.LENGTH_LONG).show();
      return;

    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
    String date = dateFormat.format(new Date());
    String photoFile = "Picture_" + date + ".jpg";

    String filename = pictureFileDir.getPath() + File.separator + photoFile;

    File pictureFile = new File(filename);

    try {
      FileOutputStream fos = new FileOutputStream(pictureFile);
      fos.write(data);
      fos.close();
      Toast.makeText(context, "New Image saved:" + photoFile,
          Toast.LENGTH_LONG).show();
      
      // Trying to show image.
      Bitmap bmp;
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inMutable = true;
      bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);
//      ImageView imageView = new ImageView(context);  
      imageView.setImageBitmap(bmp);

      
      
      
      //SCP to server
      // Need new thread. Or else NetworkOnMainThreadException
      new SFTPTask().execute(pictureFile);
      
//      new Thread(new Runnable() {
//          public void run() {
//        	  sendToServer(pictureFile);
//          }
//      }).start();
      
    } catch (Exception error) {
      Log.d(MakePhotoActivity.DEBUG_TAG, "File" + filename + "not saved: "
          + error.getMessage());
      Toast.makeText(context, "Image could not be saved.",
          Toast.LENGTH_LONG).show();
    }
  }



private File getDir() {
    File sdDir = Environment
      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    Toast.makeText(context, sdDir.getAbsolutePath(),
            Toast.LENGTH_LONG).show();
    return new File(sdDir, "CameraAPIDemo");
  }
}