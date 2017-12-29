package com.devyok.bybrid.messenger.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devyok.web.HmLogger;
import com.devyok.web.HybridMessage;
import com.devyok.web.HybridMessenger;
import com.devyok.web.hybridmessenger.sample.R;


public class TestListActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.h5tonative_list_activity);



		//定义指定模块的消息接收器
		this.findViewById(R.id.test01).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(TestListActivity.this,H5toNativeTest01Activity.class);
				startActivity(intent);
			}
		});

		//定义支持优先级的消息接收器(并可中断消息)
		this.findViewById(R.id.test02).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(TestListActivity.this,H5toNativeTest02Activity.class);
				startActivity(intent);
			}
		});

		//添加全局的消息接收器
		this.findViewById(R.id.test03).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				HybridMessenger.WebMessageInterceptor interceptor = new HybridMessenger.WebMessageInterceptor() {

					@Override
					public boolean intercept(final HybridMessage webMessage) {

						HmLogger.error("TestListActivity", "[HybridMessage native] 拦截到的消息 = " + webMessage);

						return false;
					}

				};

				HybridMessenger.getMessenger().setWebMessageInterceptor(interceptor);

				Toast.makeText(getApplicationContext(),"ok",1).show();


			}
		});

		//发送消息到H5
		this.findViewById(R.id.test04).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(TestListActivity.this,H5toNativeTest04Activity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}



}
