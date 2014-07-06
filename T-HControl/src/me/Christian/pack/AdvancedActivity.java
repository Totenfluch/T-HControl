package me.Christian.pack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class AdvancedActivity extends Activity {
	public Switch pwswitch;
	public EditText masterpw;
	public Button buttonshutown, buttonreboot;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acivity_advanced);
		MainActivity.activities.add(this);
		pwswitch = (Switch) findViewById(R.id.pwlock);
		masterpw = (EditText) findViewById(R.id.rootpassword);
		buttonreboot = (Button) findViewById(R.id.reboot);
		buttonshutown = (Button) findViewById(R.id.shutdown);
    	masterpw.setEnabled(true);
    	buttonreboot.setEnabled(false);
    	buttonshutown.setEnabled(false);
		pwswitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		        if(isChecked){
		        	masterpw.setEnabled(false);
		        	buttonreboot.setEnabled(true);
		        	buttonshutown.setEnabled(true);
		        }else{
		        	masterpw.setEnabled(true);
		        	buttonreboot.setEnabled(false);
		        	buttonshutown.setEnabled(false);
		        }
		    }
		});
	}
	
    public void onDestroy()
    {
        super.onDestroy();
        MainActivity.activities.remove(this);
    }

	public void adv_lockall(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " lock_all");
	}

	public void adv_unlockall(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " unlock_all");
	}

	public void adv_enableall(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " enable_all");
	}

	public void adv_disableall(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " disable_all");
	}
	
	public void shutdown(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " shutdown@"+masterpw.getText().toString());
	}
	
	public void reboot(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " reboot@"+masterpw.getText().toString());
	}
	
	public void OnToggleMid(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " toggle");
	}
	

}
