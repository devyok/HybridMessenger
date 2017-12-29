package com.devyok.bybrid.messenger.sample;

import android.app.Application;

import com.devyok.web.Configuration;
import com.devyok.web.HybridMessenger;
import com.devyok.web.android.AndroidHybridMessageHandlerThread;
import com.devyok.web.android.HybridMessageReceiveClient;

public class SampleApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		
		boolean isUseDefault = false;
		Configuration configuration = new Configuration.Builder()
													   .setDriver(HybridMessageReceiveClient.class)
													   .setThread(AndroidHybridMessageHandlerThread.class)
													   .build();
		
		configuration = isUseDefault ? Configuration.DEFAULT : configuration;
		HybridMessenger.getMessenger().config(configuration);
		
	}

	
	
}
