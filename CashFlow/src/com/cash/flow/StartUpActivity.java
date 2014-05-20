package com.cash.flow;

import java.util.List;

import com.cash.flow.activity.ActivationActivity;
import com.cash.flow.activity.LoginActivity;
import com.cash.flow.activity.MainMenuActivity;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.global.GlobalVar;
import com.cash.flow.model.User;
import com.cash.flow.util.Constant;
import com.cash.flow.util.PreferenceHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartUpActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);
        
        UserDao userDao = UserDao.getInstance(this);
        final List<User> users = userDao.findAll();
        userDao.closeConnection();
        
        boolean isLogin = new PreferenceHelper(StartUpActivity.this).getBoolean(Constant.LOGIN_STATUS);
        if(users.size() > 0)
		if(isLogin) {
			User user = users.get(0);
			GlobalVar.getInstance().setUser(user);
			startActivity(new Intent(StartUpActivity.this, MainMenuActivity.class));
			finish();
			return;
		}
        
        Thread timer = new Thread(){
			
			@Override
			public void run() {
				try{
					int time = 0;
					while(time < 1500){
						sleep(100);
						time += 100;
					}
				}catch(Exception ex){
					ex.printStackTrace();
				} finally {
					if(users.size() > 0) {
						startActivity(new Intent(StartUpActivity.this, LoginActivity.class));
					} else {
						startActivity(new Intent(StartUpActivity.this, ActivationActivity.class));
					}
					finish();
				}
			}
			
		};
		
		timer.start();
        
        //startActivity(new Intent(this, LoginActivity.class));
        //finish();
    }
}