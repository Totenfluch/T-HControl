package me.Christian.pack;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static Client connection = null;
	static EditText serverip;
	static EditText serverport, sUsername, sPassword;
	static Button connect, toMpc, toAdv, toOutput;
	public static TextView PrivateKeybox;
	public static Handler handler = new Handler();
	public static Handler timeouthandler = new Handler();
	public static Runnable refresh, timeout, timeoutinstantly;
	public static String bIp = "";
	public static int bPort = 66656;
	public static String bUsername = "";
	public static String bPassword = "";
	public static String PrivateKey;
	public static CheckBox connectbox, sconnected_adv, sconnected_mpc, sconnected_outputs;
	public static android.content.ClipboardManager clipboard;

	private static final String TAG=MainActivity.class.getName();
    static ArrayList<Activity> activities=new ArrayList<Activity>();
    public final Activity Main = this;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.ThreadPolicy policy = 
				new StrictMode.ThreadPolicy.Builder().permitAll().build();      
		StrictMode.setThreadPolicy(policy);
		System.out.println(TAG);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public static void refreshafteroptions(){
		serverip.setText(bIp);
		serverport.setText(String.valueOf(bPort));
		if(serverport.getText().equals("0")){
			serverport.setText("");
		}
		sUsername.setText(bUsername);
		sPassword.setText(bPassword);
	}
	
	public void OnRefresh(View view){
		ConfigFileStuff.load();
		serverip.setText(bIp);
		serverport.setText(String.valueOf(bPort));
		if(serverport.getText().equals("0")){
			serverport.setText("");
		}
		sUsername.setText(bUsername);
		sPassword.setText(bPassword);
	}

	public void init(){
		PrivateKeybox = (TextView) findViewById(R.id.PrivateKeyText);
		serverip = (EditText) findViewById(R.id.ServerIp);
		serverport = (EditText) findViewById(R.id.ServerPort);
		connect = (Button) findViewById(R.id.doLogin);

		connectbox = (CheckBox) findViewById(R.id.isConnected);
		sconnected_adv = (CheckBox) findViewById(R.id.connected_adv);
		sconnected_mpc = (CheckBox) findViewById(R.id.mpc_connected);
		sconnected_outputs = (CheckBox) findViewById(R.id.connected_output);

		sUsername = (EditText) findViewById(R.id.Username);
		sPassword = (EditText) findViewById(R.id.Password);
		
		
		toMpc = (Button) findViewById(R.id.toMpc);
		toAdv = (Button) findViewById(R.id.toAdvanced);
		toOutput = (Button) findViewById(R.id.toOutputs);
		toMpc.setEnabled(false);
		toAdv.setEnabled(false);
		toOutput.setEnabled(false);
		
		ConfigFileStuff.load();
		
		serverip.setText(bIp);
		serverport.setText(String.valueOf(bPort));
		if(serverport.getText().equals("0")){
			serverport.setText("");
		}
		sUsername.setText(bUsername);
		sPassword.setText(bPassword);
		/*
		serverip.setText("192.168.178.38");
		serverport.setText("9977");
		sUsername.setText("Totenfluch");
		sPassword.setText("s123C");
		*/

		refresh = new Runnable() {
			public void run() {
				refresh();
				handler.postDelayed(refresh, 500);
			}
		};
		handler.post(refresh);
		timeout = new Runnable() {
			public void run() {
				if(!Client.IsConnectedToServer){
					connection = null;
					DisconnectFromServer("timeout");
				}
			}
		};
		
		timeoutinstantly = new Runnable(){
			public void run(){
				DisconnectFromServer("host unreachable");
			}
		};
	}

	public void refresh(){
		if(!Client.IsConnectedToServer && connection != null){
			DisconnectFromServer("timeout");
			System.out.println(OtherStuff.TheNormalTime()+" CLIENT: Lost connection / Host unreachable");
		}
	}

	public void OnLoginPress(View view){
		if(!Client.IsConnectedToServer){
			try{
				if(Integer.valueOf(serverport.getText().toString()) > 0 && Integer.valueOf(serverport.getText().toString()) < 65565){
					bPort = Integer.valueOf(serverport.getText().toString());
					bIp = serverip.getText().toString();
					ConnectToServer(bIp, bPort);
				}
			}catch(Exception ex){
				System.out.println(OtherStuff.TheNormalTime() + " CLIENT: Invalid Port or IP");
			}
		}else if(Client.IsConnectedToServer){
			DisconnectFromServer("by Request");
			Client.IsConnectedToServer = false;
		}
	}

	public void setconnected(boolean s){
		toMpc.setEnabled(s);
		toAdv.setEnabled(s);
		toOutput.setEnabled(s);
		connectbox.setChecked(s);
	}

	public void DisconnectFromServer(String reason){
		finishall();
		Toast.makeText(getApplicationContext(), "Disconnected - "+reason, 
				Toast.LENGTH_LONG).show();
		System.out.println("Disconnected by *Disconnected from Server*");	
		setconnected(false);
		connect.setText("Connect");
		serverip.setEnabled(true);
		serverport.setEnabled(true);
		sUsername.setEnabled(true);
		sPassword.setEnabled(true);
		Client.IsConnectedToServer = false;
		try {
			connection.din.close();
			connection.dout.close();
			connection.socket.close();
		}catch (Exception e) {}
		try{connection.socket = null;}catch(Exception e){}
		try{connection.din = null;}catch(Exception e){}
		try{connection.dout = null;}catch(Exception e){}
		try{connection.thread = null;}catch(Exception e){}
		try{connection.running = false;}catch(Exception e){}
		try{connection = null;}catch(Exception e){}
	}
	
	public void finishall(){
        for(Activity activity:activities){
        	if(activity != Main){
        		activity.finish();
        	}
        }
	}

	public boolean ConnectToServer(String ip, int port){
		handler.postDelayed(MainActivity.timeout, 2500);
		try{
			connection = new Client(ip, port);
			Client.processMessage("/login " + sUsername.getText() + " " + sPassword.getText());
		}catch(Exception e){
			DisconnectFromServer("failed connect");
			e.printStackTrace();
		}
		serverip.setEnabled(false);
		serverport.setEnabled(false);
		sUsername.setEnabled(false);
		sPassword.setEnabled(false);
		connect.setText("Disconnect");
		Client.IsConnectedToServer = true;
		setconnected(true);

		return true;
	}

	public void switchToOutputs(View view){
		Intent launchactivity= new Intent(MainActivity.this,OutputActivity.class);                               
		startActivity(launchactivity);
	}

	public void switchToAdvanced(View view){
		Intent launchactivity= new Intent(MainActivity.this,AdvancedActivity.class);                               
		startActivity(launchactivity);
	}

	public void switchToMpc(View view){
		Intent launchactivity= new Intent(MainActivity.this,MpcActivity.class);                               
		startActivity(launchactivity);
	}
	
	public void switchToOptions(View view){ 
		Intent launchactivity= new Intent(MainActivity.this,ConfigFileStuff.class);                               
		startActivity(launchactivity);
	}
	

}
