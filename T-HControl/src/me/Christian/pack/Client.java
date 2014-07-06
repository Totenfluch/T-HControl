package me.Christian.pack;

import java.io.*;
import java.net.*;

public class Client implements Runnable
{
	public static boolean IsConnectedToServer = false;
	public static String LatestServerReply = "";
	public boolean waitingforreply = false;
	public boolean running = true;
	public Thread thread = null;

	public String format;
	public Socket socket;
	public DataOutputStream dout;
	public DataInputStream din;

	public Client( String host, int port) {
		try {
			SocketAddress socketaddres = new InetSocketAddress(host, port);
			socket = new Socket();
			socket.connect(socketaddres, 1000);
			if(socket.isConnected()){
				din = new DataInputStream( socket.getInputStream() );
				dout = new DataOutputStream( socket.getOutputStream() );
				IsConnectedToServer = true;
				thread = new Thread( this );
				thread.start();
			}
		}catch( IOException ie ){
			IsConnectedToServer = false;
		}
		if(socket.isConnected()){
			running = true;
		}
	}


	public static void processMessage( String message ) {
		try {
			MainActivity.connection.dout.writeUTF( android.os.Build.MODEL + " " + message );
		} catch( Exception ie ){
			IsConnectedToServer = false;
			MainActivity.timeouthandler.postDelayed(MainActivity.timeoutinstantly, 1);
			ie.printStackTrace();
			System.out.println( ie ); 
		}

	}

	public void run() {
		try {
			while (running) {
				if(IsConnectedToServer == true){
					String message = null;
					try{
						message = din.readUTF();
						LatestServerReply = message;
						GetServerMessages.CheckServerMessages(message);
						waitingforreply = false;
					}catch(Exception e){
						IsConnectedToServer = false;
					}
				}
			}
		}catch( Exception ie ){
			ie.printStackTrace();
			IsConnectedToServer = false;
		} finally {
			try{
				if(dout != null){
					dout.close();
				}
				if(din != null){
					din.close();
				}
				if(socket != null){
					socket.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}