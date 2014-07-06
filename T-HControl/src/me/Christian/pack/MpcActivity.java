package me.Christian.pack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MpcActivity extends Activity {

	/** Called when the activity is first created. */
	public static Button ssetvolume;
	private int volume;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mpc);
		SeekBar sbar = (SeekBar)findViewById(R.id.seekBar1); 
		ssetvolume = (Button) findViewById(R.id.setvolume);
		MainActivity.activities.add(this);
		sbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

			@Override 
			public void onProgressChanged(SeekBar seekBar, int progress, 
					boolean fromUser) { 
				ssetvolume.setText("Set Volume ("+progress+")");
				volume = progress;
			} 

			@Override 
			public void onStartTrackingTouch(SeekBar seekBar) { 
			} 

			@Override 
			public void onStopTrackingTouch(SeekBar seekBar) { 
			} 
		}); 
	} 
	
    public void onDestroy()
    {
        super.onDestroy();
        MainActivity.activities.remove(this);
    }


	public void Mpc_play(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Music@play");
	}
	public void Mpc_pause(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Music@pause");
	}
	public void Mpc_next(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Music@next");
	}
	public void Mpc_prev(View view){
		Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Music@prev");
	}
	public void onSetVolume(View view){
		if(ssetvolume.getText().toString().contains("(")){
			Client.processMessage("/AuthAction " + MainActivity.PrivateKey + " Music@volume@"+volume);
		}
	}

}
