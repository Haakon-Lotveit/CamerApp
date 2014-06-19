package no.uib.CamerApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InitActivity extends Activity {

	private EditText  userID=null;

	private Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// If a userID is registered. Redirect to MakePhotoActivity
		// Get userID from sharedpreferences
		SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
		String user = settings.getString("userID", "-1");
		if(!user.equals("-1")) {
			Intent photo = new Intent(this, MakePhotoActivity.class);
			startActivity(photo);
		} else { 

			setContentView(R.layout.activity_main);
			userID = (EditText)findViewById(R.id.editText1);
			login = (Button)findViewById(R.id.button1);
		}
	}

	public void login(View view){
		String id = userID.getText().toString();
		if(id.length() > 0 ){
			Toast.makeText(getApplicationContext(), "Redirecting...", 
					Toast.LENGTH_SHORT).show();
			SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("userID", id);
			editor.commit();

			Intent photo = new Intent(this, MakePhotoActivity.class);
			startActivity(photo);
		}	
		else{
			Toast.makeText(getApplicationContext(), "ID can not be empty",
					Toast.LENGTH_SHORT).show();


		}

	}
}
