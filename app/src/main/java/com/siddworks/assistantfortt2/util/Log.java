package com.siddworks.assistantfortt2.util;

import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

	private static final String TAG_PREFIX = "TT_";
	static int count = 0;
	static String message = "";
	static boolean isGlobalLoggingEnabled = Constants.isGlobalLoggingEnabled;
	static boolean isFileLoggingEnabled = true;
	private static boolean loggingEnabled;

	public static void d(String tag, Object message)
	{
		if(isGlobalLoggingEnabled && loggingEnabled)
		{
			logToFile(TAG_PREFIX+tag, message.toString());
			android.util.Log.d(TAG_PREFIX+tag, message.toString());
		}
	}

	public static void i(boolean loggingEnabled, String tag, Object message)
	{
		if(isGlobalLoggingEnabled && loggingEnabled)
		{
			logToFile(TAG_PREFIX+tag, message.toString());
			android.util.Log.i(TAG_PREFIX+tag, message.toString());
		}
	}
	
	public static void e(String tag, Throwable message)
	{
		if (message != null) {
			message.printStackTrace();
//			Crashlytics.logException(message);
		}
		if(isGlobalLoggingEnabled && loggingEnabled)
		{
			if (message != null) {
				logToFile(tag, message.toString());
				android.util.Log.e(tag, message.toString());
			}
		}
	}

	public static void e(boolean loggingEnabled, String tag, String message)
	{
		if(isGlobalLoggingEnabled && loggingEnabled)
		{
			logToFile(TAG_PREFIX+tag, message.toString());
			android.util.Log.e(TAG_PREFIX+tag, message.toString());
		}
	}

	private static void logToFile(String tag, String message2) {
//		android.util.Log.d(TAG, "logToFile() called with " + "tag = [" + tag + "], message2 = [" + message2 + "]");
		if(isFileLoggingEnabled)
		{
			if(count > 10)
			{
				 try {
					 File file = new File(Environment.getExternalStoragePublicDirectory(
					            Environment.DIRECTORY_DOWNLOADS), "TTAssistant2.txt");
			            FileWriter out = new FileWriter(file, true);
//			            out.write(message2+"\n\n");
					 	out.write(message+"\n\n");
			            out.close();
			        } catch (IOException e) {
			        }
				count = 0;
				message = "";
			}
			else
			{
				count ++;
				message += SystemClock.elapsedRealtime() + " : " + tag+" : "+message2+"\n\n";
			}
		}
	}
}
