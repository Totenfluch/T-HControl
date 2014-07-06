package me.Christian.pack;


public class GetServerMessages{
	public static String newestreply = null;


	public static void CheckServerMessages(String message){
		if(message.startsWith("/PrivateKey")){
			String[] temp = message.split(" "); 
			//MainActivity.PrivateKeybox.setText("key: " + temp[1]);
			MainActivity.PrivateKey = temp[1];
		}
	}

}

