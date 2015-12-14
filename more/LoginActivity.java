package com.taazaa.registerationapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taazaa.registerationapp.utils.CustomTypeFace;
import com.taazaa.registerationapp.utils.LayoutParams;

@SuppressLint("NewApi")
public class LoginActivity extends Activity implements OnClickListener {

	private DatabaseManager databaseManager;
	private CustomTypeFace customTypeFace;
	private LayoutParams layoutParams;

	private TextView appTitleTextView;
	private TextView orText;

	private EditText userNameField;
	private EditText passwordField;

	private Button loginButton; 
	private Button registerButton; 

	private ScaleDrawable scaleDrawable;
	private Drawable drawable;
	private LinearLayout innerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		databaseManager = new DatabaseManager(this);
		customTypeFace 	= new CustomTypeFace(this);
		layoutParams 	= new LayoutParams(this.getResources().getDisplayMetrics());

		initUI();

	}


	private void initUI() {

		//		mainLayout = (LinearLayout)findViewById(R.id.mainLoginLayout);
		innerLayout = (LinearLayout)findViewById(R.id.innerLoginLayout);

		appTitleTextView = (TextView)findViewById(R.id.appTitleTextView);
		appTitleTextView.setText(Html.fromHtml("<u>" + getString(R.string.app_title) + "</u>"));

		orText = (TextView)findViewById(R.id.orText);

		userNameField = (EditText) findViewById(R.id.userNameField);
		passwordField = (EditText) findViewById(R.id.passwordField);

		loginButton 	= (Button) findViewById(R.id.loginButton);
		registerButton 	= (Button) findViewById(R.id.registerButton);

		setViewParams(appTitleTextView, 7, 15, 27, customTypeFace.gothamRoundedMedium, 1);
		setViewParams(orText, 0, 2, 17, customTypeFace.gothamRoundedMedium, 0);

		setViewParams(userNameField, 7, 11, 12, customTypeFace.gothamRoundedBookItalic, 0);
		setViewParams(passwordField, 7, 11, 12, customTypeFace.gothamRoundedBookItalic, 0);

		setViewParams(loginButton, 7, 11, 15, customTypeFace.gothamRoundedMedium, 1);
		setViewParams(registerButton, 7, 11, 15, customTypeFace.gothamRoundedMedium, 1);

		//		setViewParams(mainLayout, 30, 0, 0, null, 0);
		setViewParams(innerLayout, 10, 30, 0, null, 0);
	}

	@SuppressLint("NewApi")
	private void setViewParams(View view, int padding, int margin, int fontSize, Typeface typeface, int fontStyle) {

		if(view instanceof Button) {

			((Button)view).setTypeface(typeface, fontStyle);
			((Button)view).setTextSize(layoutParams.getFontSize(fontSize));

			view.setOnClickListener(this);
		} else if(view instanceof EditText) {

			((EditText)view).setTypeface(typeface, fontStyle);
			((EditText)view).setTextSize(layoutParams.getFontSize(fontSize));

		} else if(view instanceof TextView) {

			((TextView)view).setTypeface(typeface, fontStyle);
			((TextView)view).setTextSize(layoutParams.getFontSize(fontSize));

		} 
		view.setPadding(layoutParams.getSpacing(padding), layoutParams.getSpacing(padding), layoutParams.getSpacing(padding), layoutParams.getSpacing(padding));
		((LinearLayout.LayoutParams)view.getLayoutParams()).setMargins(layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin), layoutParams.getSpacing(margin));

		switch(view.getId()){

		case R.id.loginButton:
			drawable = getResources().getDrawable(R.drawable.login, getTheme());
			drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.4), (int)(drawable.getIntrinsicHeight()*0.4));
			scaleDrawable = new ScaleDrawable(drawable, 0, ((Button)view).getWidth(), ((Button)view).getHeight());
			((Button)view).setCompoundDrawables(scaleDrawable.getDrawable(), null, null, null);
			break;

		case R.id.registerButton:
			drawable = getResources().getDrawable(R.drawable.signup, getTheme());
			drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.4), (int)(drawable.getIntrinsicHeight()*0.4));
			scaleDrawable = new ScaleDrawable(drawable, 0, ((Button)view).getWidth(), ((Button)view).getHeight());
			((Button)view).setCompoundDrawables(scaleDrawable.getDrawable(), null, null, null);
			break;
			
		default:
			break;
		}

	}

	@Override
	public void onClick(View view) {

		switch(view.getId()) {

		case R.id.registerButton:

			startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

			break;

		case R.id.loginButton:

			if(userNameField.getText().toString().trim().length() == 0 || passwordField.getText().toString().trim().length() == 0){

				Toast.makeText(this, R.string.login_warning, Toast.LENGTH_SHORT).show();

				break ;
			}

			if(userNameField.getText().toString().equalsIgnoreCase("admin") || passwordField.getText().toString().equalsIgnoreCase("admin")){

				startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
			}

			else if(databaseManager.loginUser(userNameField.getText().toString(), passwordField.getText().toString())) {

				Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
				intent.putExtra("USER_NAME", userNameField.getText().toString());
				startActivity(intent);

			} else {
				Toast.makeText(this, R.string.invalid_credential_warning, Toast.LENGTH_SHORT).show();
			}

			break;
		}
	}
}

