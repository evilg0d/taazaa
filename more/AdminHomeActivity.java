package com.taazaa.registerationapp;

import java.util.ArrayList;

import com.taazaa.registerationapp.utils.Users;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdminHomeActivity extends Activity implements OnClickListener {

	private LinearLayout linearLayout;
	private CheckBox checkBox;
	private TextView textView;
	private String userData = "";

	private ArrayList<Users> allUsersDataFromDB ;
	private ArrayList<Users> temp ;

	private DatabaseManager dbManager;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_home);

		linearLayout = (LinearLayout) findViewById(R.id.adminHomeLayout);

		dbManager = new DatabaseManager(this);

		allUsersDataFromDB = dbManager.getAllUsersData();
		temp = allUsersDataFromDB;

		setAllUserData(allUsersDataFromDB);

		findViewById(R.id.deleteData).setOnClickListener(this);
	}


	private void setAllUserData(ArrayList<Users> allUsersData) {

		linearLayout.removeAllViews();

		for(Users user: allUsersData){

			userData = "User Name: "+user.getUserName()+
					"/ First Name: "+user.getFirstName()+
					"/ Last Name: "+user.getLastName()+
					"/ Roll Number: "+user.getRollNum()+
					"/ City: "+user.getCity()+"/";

			if(user.getUserImage().length() == 0){
				userData = userData+"IMAGE NO";
			}else{
				userData = userData+"IMAGE YES";
			}

			checkBox = new CheckBox(this);
			checkBox.setText(String.valueOf(user.getUserId()));
			linearLayout.addView(checkBox);

			textView = new TextView(this);
			textView.setText(userData+"\n");
			linearLayout.addView(textView);

		}

	}

	@Override
	public void onClick(View view) {

		CheckBox cb;

		String idArray = "";

		for(int i = 0 ; i < linearLayout.getChildCount() ; i+=2){

			cb = (CheckBox)linearLayout.getChildAt(i);
			if(cb.isChecked())
			{
				idArray += ((String) cb.getText()+", ");
				temp.remove(i/2);
			}

		}

		idArray = idArray.substring(0, idArray.length()-2);

		if(dbManager.deleteUsers(idArray)){

			Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show();
			
			allUsersDataFromDB.retainAll(temp);
			setAllUserData(allUsersDataFromDB);
		}

	}

}
