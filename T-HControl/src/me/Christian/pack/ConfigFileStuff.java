package me.Christian.pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigFileStuff extends Activity{
	private static EditText smIp, smPort, smUsername, smPassword;
	public static String path;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_options);
	    init();
	}
	
    
    public void init(){
    	smIp = (EditText) findViewById(R.id.saveIp);
    	smPort = (EditText) findViewById(R.id.savePort);
    	smUsername = (EditText) findViewById(R.id.saveUsername);
    	smPassword = (EditText) findViewById(R.id.savePassword);
    	path = Environment.getExternalStorageDirectory().toString() + "/HomeControl/TH-ControlConfig.properties" ;
    }
    
    public void OnSave(View view){
    	save();
		Toast.makeText(getApplicationContext(), "Saved", 
				Toast.LENGTH_LONG).show();
    }
    
	public static void save(){
		Properties prop = new Properties();
		File file = new File(path);
		OutputStream output = null;
		if(!file.exists()){
			File file2 = new File(path.toString().replace("/TH-ControlConfig.properties", ""));
			file2.mkdirs();
			try {
				file.createNewFile();
				output = new FileOutputStream(path);

				prop.setProperty("ServerIP", smIp.getText().toString());
				prop.setProperty("ServerPort", smPort.getText().toString());
				prop.setProperty("Username", smUsername.getText().toString());
				prop.setProperty("Password", smPassword.getText().toString());

				prop.store(output, null);

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			load();
		}else{
			try{
				file.delete();
				file.createNewFile();
				output = new FileOutputStream(path);

				prop.setProperty("ServerIP", smIp.getText().toString());
				prop.setProperty("ServerPort", smPort.getText().toString());
				prop.setProperty("Username", smUsername.getText().toString());
				prop.setProperty("Password", smPassword.getText().toString());

				prop.store(output, null);

			} catch (IOException ios) {
				ios.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			load();
		}
	}

	public static void load(){
		Properties prop = new Properties();
		File file = new File( Environment.getExternalStorageDirectory().toString() + "/HomeControl/TH-ControlConfig.properties");
		if(file.exists()){
			InputStream input = null;
			try {
				input = new FileInputStream( Environment.getExternalStorageDirectory().toString() + "/HomeControl/TH-ControlConfig.properties");

				prop.load(input);

				MainActivity.bIp = prop.getProperty("ServerIP");
				try{
					MainActivity.bPort = Integer.valueOf(prop.getProperty("ServerPort"));
				} catch (Exception e){
					file.delete();
				}
				MainActivity.bUsername = prop.getProperty("Username");
				MainActivity.bPassword = prop.getProperty("Password");

			} catch (IOException ex) {
				file.delete();
				save();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
