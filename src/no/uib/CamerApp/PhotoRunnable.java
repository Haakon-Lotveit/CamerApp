package no.uib.CamerApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;

public class PhotoRunnable implements Runnable {

	private byte[] data;
	private Context context;

	public PhotoRunnable(byte[] data, Context context) {
		this.data = data;
		this.context = context;
		}
	
	@Override
	public void run() {
//		File pictureFileDir = (File) params[0];
//		byte[] data = (byte[]) params[0];
//		Context context = (Context) params[1];
		
		Toast.makeText(context, "CRAAAAAAP!",
				Toast.LENGTH_LONG).show();
		
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

//		Toast.makeText(context, "New Image saved:" + pictureFile.getName(),
//				Toast.LENGTH_LONG).show();
		Toast.makeText(context, "Success!",
				Toast.LENGTH_LONG).show();
		
//		return null;		


	}

}
