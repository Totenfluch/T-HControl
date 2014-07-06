package me.Christian.pack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class OutputActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_outputs);
	    MainActivity.activities.add(this);
	}
	
    public void onDestroy()
    {
        super.onDestroy();
        MainActivity.activities.remove(this);
    }
	
	public void LockOutput0(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@0");
	}
	public void LockOutput1(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@1");
	}
	public void LockOutput2(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@2");
	}
	public void LockOutput3(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@3");
	}
	public void LockOutput4(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@4");
	}
	public void LockOutput5(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@5");
	}
	public void LockOutput6(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@6");
	}
	public void LockOutput7(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Lock@7");
	}
	
	/////////////////////////////////////
	
	public void UnlockOutput0(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@0");
	}
	public void UnlockOutput1(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@1");
	}
	public void UnlockOutput2(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@2");
	}
	public void UnlockOutput3(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@3");
	}
	public void UnlockOutput4(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@4");
	}
	public void UnlockOutput5(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@5");
	}
	public void UnlockOutput6(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@6");
	}
	public void UnlockOutput7(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Unlock@7");
	}
	
	/////////////////////////////////////
	
	public void ToggleOutput0(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@0");
	}
	public void ToggleOutput1(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@1");
	}
	public void ToggleOutput2(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@2");
	}
	public void ToggleOutput3(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@3");
	}
	public void ToggleOutput4(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@4");
	}
	public void ToggleOutput5(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@5");
	}
	public void ToggleOutput6(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@6");
	}
	public void ToggleOutput7(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Toggle@Output@7");
	}

}
