package com.taazaa.registerationapp;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.taazaa.registerationapp.utils.CustomTypeFace;
import com.taazaa.registerationapp.utils.LayoutParams;
import com.taazaa.registerationapp.utils.Users;

public class UserHomeActivity extends Activity {

	private Users users;
	private DatabaseManager dbManager;
	private RegistrationUltils utils;
	
	private CustomTypeFace customTypeFace;
	private LayoutParams layoutParams;
	
	private TextView firstNameValue;
	private TextView lastNameValue;
	private TextView rollNumValue;
	private TextView cityValue;
	private TextView firstNameText;
	private TextView lastNameText;
	private TextView rollNumText;
	private TextView cityText;
	private TextView homeTitle;
	
	private LinearLayout titleLayout;
	private RelativeLayout innerLayout;
	
	private Button logoutButton;
	
	private ImageView profileImage;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		utils = new RegistrationUltils();
		customTypeFace 	= new CustomTypeFace(this);
		layoutParams 	= new LayoutParams(this.getResources().getDisplayMetrics());
		
		initUI();
		
		homeTitle.setText(getString(R.string.login_home_title)+ " "+ getIntent().getExtras().getString("USER_NAME"));

		dbManager = new DatabaseManager(this);
		users = dbManager.getUser(getIntent().getExtras() != null ? getIntent().getExtras().getString("USER_NAME") : "");
		
		displayData();

		profileImage.setImageBitmap( utils.getCircleBitmapFromDB(users));
		
		((LinearLayout.LayoutParams)profileImage.getLayoutParams()).width = layoutParams.getRectViewWidthSize(107);
		((LinearLayout.LayoutParams)profileImage.getLayoutParams()).height = layoutParams.getRectViewWidthSize(107);
		((LinearLayout.LayoutParams)profileImage.getLayoutParams()).setMargins(layoutParams.getSpacing(11), 0, layoutParams.getSpacing(11), layoutParams.getSpacing(11));
	}

private void initUI(){
	
	titleLayout = (LinearLayout)findViewById(R.id.homeTitleLayout);
	innerLayout = (RelativeLayout)findViewById(R.id.RelativeLayout1);
		
	homeTitle =     (TextView)findViewById(R.id.loginHomeTitle);
	logoutButton = 	(Button) findViewById(R.id.logoutButton);
	
	profileImage = (ImageView)findViewById(R.id.userImageShow);
	
	firstNameValue =(TextView)findViewById(R.id.firstNameValue);
	lastNameValue = (TextView)findViewById(R.id.lastNameValue);
	rollNumValue =  (TextView)findViewById(R.id.rollNumValue);
	cityValue = 	(TextView)findViewById(R.id.cityValue);
	firstNameText = (TextView)findViewById(R.id.firstNameText);
	lastNameText =  (TextView)findViewById(R.id.lastNameText);
	rollNumText =   (TextView)findViewById(R.id.rollNumText);
	cityText =      (TextView)findViewById(R.id.cityText);
	
	setViewParams(homeTitle, 		7, 0, 17, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(logoutButton,		7, 0, 15, customTypeFace.gothamRoundedBook, 0);
	
	setViewParams(firstNameValue, 	3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(lastNameValue, 	3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(rollNumValue, 	3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(cityValue, 		3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(firstNameText, 	3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(lastNameText, 	3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(rollNumText, 		3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	setViewParams(cityText,			3, 5,  10, customTypeFace.gothamRoundedMedium, 1);
	
	setViewParams(titleLayout,		0, 10, 0, null, 0);
	setViewParams(innerLayout,		0, 10, 0, null, 0);
	
	
	}

	private void setViewParams(View view, int padding, int margin, int fontSize, Typeface typeface, int fontStyle) {

		if(view instanceof Button) {

			((Button)view).setTypeface(typeface, fontStyle);
			((Button)view).setTextSize(layoutParams.getFontSize(fontSize));
			
			view.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			
		} else if(view instanceof TextView) {

			((TextView)view).setTypeface(typeface, fontStyle);
			((TextView)view).setTextSize(layoutParams.getFontSize(fontSize));

		}
		
		view.setPadding(layoutParams.getSpacing(padding), layoutParams.getSpacing(padding), layoutParams.getSpacing(padding), layoutParams.getSpacing(padding));
		
		if(view.getLayoutParams().getClass() == LinearLayout.LayoutParams.class) {
			((LinearLayout.LayoutParams)view.getLayoutParams()).setMargins(layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin));
		} else if(view.getLayoutParams().getClass() == RelativeLayout.LayoutParams.class){
			((RelativeLayout.LayoutParams)view.getLayoutParams()).setMargins(layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin));
		}else {
			((ScrollView.LayoutParams)view.getLayoutParams()).setMargins(layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin));
		}

	}
	
	void displayData(){

		((TextView)findViewById(R.id.firstNameValue)).setText(users.getFirstName());
		((TextView)findViewById(R.id.lastNameValue)).setText(users.getLastName());
		((TextView)findViewById(R.id.rollNumValue)).setText(users.getRollNum());
		((TextView)findViewById(R.id.cityValue)).setText(users.getCity());

	}
	
	@Override
	public void onBackPressed(){}
}

