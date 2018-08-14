package com.book.sl.thlibarary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import DataBase.User;
import LocalDataSave.LocalDataManager;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		LocalDataManager localDataManager = new LocalDataManager();
		boolean isFirstTime = localDataManager.isCheckFistTime(this);
		if (!isFirstTime){
			localDataManager.saveAfterGetStart(true,  this);
			Intent intent = new Intent(this, GetStartActivity.class);
			startActivity(intent);
		}else {
			User user = localDataManager.getUserObject(this);
			if (user != null){
				if (user.isAdministrator() == false){
					Intent intent = new Intent(this, HomeActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(this, BookAddActivity.class);
					startActivity(intent);
				}
			}
		}

	}

	@OnClick(R.id.create_account_button)
	public void createAc(View view) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);

	}

	@OnClick(R.id.login_member_button)
	public void login(View view) {
		Intent intent = new Intent(this, MemberLoginActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.manage_system_button)
	public void loginAdmin(View view) {
		Intent intent = new Intent(this, AdminLoginActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.not_now_button)
	public void cancel(View view) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

//	@Subscribe(sticky = true)
//	public void onEvent(
//			Event.LoginActivity eventName) {
//		EventBus.getDefault()
//				.postSticky(new Event.LoginActivity(
//						""));
//		Intent intent = new Intent(this, MainActivity.class);
//		startActivity(intent);
//	}

	@Override
	protected void onStop() {
		super.onStop();
		//EventBus.getDefault().unregister(this);
	}
}
