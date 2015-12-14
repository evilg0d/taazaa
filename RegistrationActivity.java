package com.taazaa.registerationapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taazaa.registerationapp.utils.CustomTypeFace;
import com.taazaa.registerationapp.utils.LayoutParams;
import com.taazaa.registerationapp.utils.Users;

public class RegistrationActivity extends Activity implements OnClickListener{

	private static final int GALLERY_REQUEST = 1;
	private static final int CAMERA_REQUEST = 2;

	private EditText userNameField;
	private EditText firstNameField;
	private EditText lastNameField;
	private EditText rollNumField;
	private EditText cityField;
	private TextView titleText;
	private TextView pickImgText;
	private Button registerButton;
	private Button cameraButton;
	private Button galleryButton;

	private ImageView imgView;

	private String userImage = "";
	private boolean bool = false;

	private Users users;
	private DatabaseManager databaseManager;
	private RegistrationUltils utils;
	
	private CustomTypeFace customTypeFace;
	private LayoutParams layoutParams;

	private Intent intent;
	private Bitmap photo;
	private ArrayList<String> fields;
	private ScaleDrawable scaleDrawable;
	private Drawable drawable;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		databaseManager = new DatabaseManager(this);

		utils = new RegistrationUltils();
		users = new Users();

		fields = new ArrayList<String>();
		
		customTypeFace 	= new CustomTypeFace(this);
		layoutParams 	= new LayoutParams(this.getResources().getDisplayMetrics());

		initUI();

		if(savedInstanceState != null) {

			photo = savedInstanceState.getParcelable("selectedImage");
			imgView.setImageBitmap(photo);

			bool = true;

		}

		titleText.setText(Html.fromHtml("<u>" + getString(R.string.registration_title) + "</u>"));
		
		findViewById(R.id.registerButton).setOnClickListener(this);
		findViewById(R.id.selectImageButton).setOnClickListener(this);
		findViewById(R.id.selectCameraButton).setOnClickListener(this);

	}
	
	private void initUI(){
		
		titleText		= (TextView)findViewById(R.id.registrationTitle);
		pickImgText 	= (TextView) findViewById(R.id.pickImageText);
		
		userNameField 	= (EditText) findViewById(R.id.usernameIdField); 
		firstNameField 	= (EditText) findViewById(R.id.firstNameField);
		lastNameField 	= (EditText) findViewById(R.id.lastNameField);
		rollNumField 	= (EditText) findViewById(R.id.rollNumberField);
		cityField	 	= (EditText) findViewById(R.id.cityField);	
		
		imgView 		= (ImageView) findViewById(R.id.userImageUpload);
		
		registerButton	= (Button) findViewById(R.id.registerButton);
		cameraButton    = (Button) findViewById(R.id.selectCameraButton);
		galleryButton	= (Button) findViewById(R.id.selectImageButton); 
		
		setViewParams(titleText, 7, 15, 17, customTypeFace.gothamRoundedMedium, 1);
		setViewParams(pickImgText, 0, 5, 8, customTypeFace.gothamRoundedMedium, 1);
		
		setViewParams(userNameField, 	7, 8, 12, customTypeFace.gothamRoundedBookItalic, 0);
		setViewParams(firstNameField, 	7, 8, 12, customTypeFace.gothamRoundedBookItalic, 0);
		setViewParams(lastNameField, 	7, 8, 12, customTypeFace.gothamRoundedBookItalic, 0);
		setViewParams(rollNumField, 	7, 8, 12, customTypeFace.gothamRoundedBookItalic, 0);
		setViewParams(cityField, 		7, 8, 12, customTypeFace.gothamRoundedBookItalic, 0);
		
		setViewParams(registerButton,7, 11, 15, customTypeFace.gothamRoundedMedium, 1);
		setViewParams(cameraButton,	 7, 8, 15, customTypeFace.gothamRoundedMedium, 1);
		setViewParams(galleryButton, 7, 8, 15, customTypeFace.gothamRoundedMedium, 1);	
		
		setViewParams(imgView, 0, 8, 0, null, 0);
		
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

		case R.id.selectCameraButton:
			drawable = getResources().getDrawable(R.drawable.camera, getTheme());
			drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.4), (int)(drawable.getIntrinsicHeight()*0.4));
			scaleDrawable = new ScaleDrawable(drawable, 10, ((Button)view).getWidth(), ((Button)view).getHeight());
			((Button)view).setCompoundDrawables(null, null, scaleDrawable.getDrawable(), null);
			break;

		case R.id.selectImageButton:
			drawable = getResources().getDrawable(R.drawable.gallery, getTheme());
			drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.4), (int)(drawable.getIntrinsicHeight()*0.4));
			scaleDrawable = new ScaleDrawable(drawable, 10, ((Button)view).getWidth(), ((Button)view).getHeight());
			((Button)view).setCompoundDrawables(null, null, scaleDrawable.getDrawable(), null);
			break;
			
		default:
			break;
		}

	}
	
	@Override
	public void onClick(View view) {

		switch(view.getId()){
		case R.id.registerButton:

			if(!(userNameField.getText().toString().equals(null))){

				if(validateAll()){

					users.setFirstName (firstNameField.getText().toString());
					users.setLastName(lastNameField.getText().toString());
					users.setRollNum(rollNumField.getText().toString());
					users.setUserName(userNameField.getText().toString());
					users.setCity(cityField.getText().toString());
					users.setPassword(userNameField.getText().toString());

					if(bool){
						users.setUserImage(utils.bitmapToBase64(photo));
					}else{
						users.setUserImage(userImage);
					}

					databaseManager.registerUser(users);
					intent=new Intent(RegistrationActivity.this, UserHomeActivity.class);

					intent.putExtra("USER_NAME",userNameField.getText().toString());

					startActivity(intent);

					finish();
				}
				else{
					String str = "";
					for(String s: fields){
						str = str + s;
					}
					str = str + " are empty";
					
					Toast.makeText(this, str, Toast.LENGTH_LONG).show();
				}
			}

			break;

		case R.id.selectImageButton:

			findViewById(R.id.registerButton).setClickable(false);

			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, GALLERY_REQUEST);

			break;

		case R.id.selectCameraButton:

			findViewById(R.id.registerButton).setClickable(false);

			intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CAMERA_REQUEST);

			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {

			if (resultCode == RESULT_OK && null != data) { 

				Uri selectedImage = data.getData();

				String[] filePathColumn = {MediaStore.Images.Media.DATA};

				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

				String imgDecodableString = cursor.getString(columnIndex);
				cursor.close();

				photo = BitmapFactory.decodeFile(imgDecodableString);

				imgView.setImageBitmap(photo);

				userImage = utils.bitmapToBase64(photo);

				findViewById(R.id.registerButton).setClickable(true);

			}  else {
				Toast.makeText(this, R.string.picked_image_warning , Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			Toast.makeText(this, R.string.exception_warning, Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {

		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putParcelable("selectedImage", photo);
		savedInstanceState.putString("imageString", userImage);
	}



	private boolean validateAll() {
		
		fields.clear();

		if((userNameField.getText().toString()).trim().length() == 0){ 
			fields.add("User Name Field, "); 
		}
		if((firstNameField.getText().toString()).trim().length() == 0){ 
			fields.add("First Name Field, ");
		}
		if((lastNameField.getText().toString()).trim().length() == 0){ 
			fields.add("Last Name Field, ");
		}
		if((rollNumField.getText().toString()).trim().length() == 0){ 
			fields.add("Roll number Field, ");
		}
		if((cityField.getText().toString()).trim().length() == 0){ 
			fields.add("City Field, ");
		}
		if(!(fields.size()>0)){
			return true;
		}

		return false;
	}

}
