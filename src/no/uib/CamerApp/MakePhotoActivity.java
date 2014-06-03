package no.uib.CamerApp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    setContentView(R.layout.main);
    this.imageView = (ImageView)this.findViewById(R.id.imageView1);

    // do we have a camera?
    if (!getPackageManager()
        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
      Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
          .show();
    } else {
      cameraId = findBackFacingCamera();
      if (cameraId < 0) {
        Toast.makeText(this, "No front facing camera found.",
            Toast.LENGTH_LONG).show();
      } else {
        camera = Camera.open(cameraId);
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
	  
    camera.takePicture(null, null,
        new PhotoHandler(getApplicationContext(), this.imageView));
//    camera.stopPreview();
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
  protected void onPause() {
    if (camera != null) {
      camera.release();
      camera = null;
    }
    super.onPause();
  }

} 