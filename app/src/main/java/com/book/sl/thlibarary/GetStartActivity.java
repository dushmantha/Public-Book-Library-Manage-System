package com.book.sl.thlibarary;

import android.content.Intent;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import Utitlity.Event;
public class GetStartActivity extends AhoyOnboarderActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("TH Library", "Library staff are available during opening hours to assist all People to make the best use of the library's resources.", R.mipmap.libarary);
		AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Administror", "Administor has deferant account manage all libarary resource.", R.mipmap.admin);
		AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Member", "Memeber has an account it use for the borrow book.", R.mipmap.member);


		ahoyOnboarderCard1.setBackgroundColor(R.color.white);
		ahoyOnboarderCard2.setBackgroundColor(R.color.white);
		ahoyOnboarderCard3.setBackgroundColor(R.color.white);

		List<AhoyOnboarderCard> pages = new ArrayList<>();

		pages.add(ahoyOnboarderCard1);
		pages.add(ahoyOnboarderCard2);
		pages.add(ahoyOnboarderCard3);

		for (AhoyOnboarderCard page : pages) {
			page.setTitleColor(R.color.black);
			page.setDescriptionColor(R.color.grey_600);
		}

		setFinishButtonTitle("Done");
		showNavigationControls(false);

		List<Integer> colorList = new ArrayList<>();
		colorList.add(R.color.solid_one);
		colorList.add(R.color.solid_two);
		colorList.add(R.color.solid_three);

		setColorBackground(colorList);

//		Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
//		setFont(face);

		setOnboardPages(pages);

		EventBus.getDefault().register(this);
		//EventBus.getDefault().register(this);

	}
	@Override
	public void onFinishButtonPressed() {

		EventBus.getDefault()
				.postSticky(new Event.HomeViewActivity(
						""));
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Subscribe(sticky = true) public void onEvent(
			Event.HomeViewActivity eventName) {

	}

	@Override
	public void onPointerCaptureChanged(boolean hasCapture) {

	}

	@Override
	protected void onStop() {
		super.onStop();
		EventBus.getDefault().unregister(this);
	}
}
