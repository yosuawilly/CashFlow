package com.cash.flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseActivity;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.model.User;
import com.cash.flow.util.Functional;
import com.cash.flow.util.Utility;

public class ActivationActivity extends BaseActivity implements Functional, OnCheckedChangeListener, AnimationListener, OnClickListener{
	
	private EditText usernameEdit, passwordEdit, answerEdit;
	private Button activasiButton;
	private Spinner spinnerQuestion;
	
	private CheckBox checkOtherQuestion;
	private EditText otherQuestionEdit;
	
	private LayoutParams editLayout;
	private Animation showOtherQuestionAnim;
	private Animation hideOtherQuestionAnim;
	
	ArrayAdapter<String> questionAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activation_layout_page);
		
		initObject();
		initDesign();
		initListener();
	}

	@Override
	public void initBundle() {
		
	}

	@Override
	public void initObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDesign() {
		usernameEdit = (EditText) findViewById(R.id.usernameEdit);
		passwordEdit = (EditText) findViewById(R.id.passwordEdit);
		answerEdit = (EditText) findViewById(R.id.answerEdit);
		
		activasiButton = (Button) findViewById(R.id.button_activasi);
		
		otherQuestionEdit = (EditText) findViewById(R.id.otherQuestionEdit);
		checkOtherQuestion = (CheckBox) findViewById(R.id.checkOtherQuestion);
		
		spinnerQuestion = (Spinner) findViewById(R.id.spinnerQuestion);
		questionAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_component, 
				getResources().getStringArray(R.array.security_questions));
		questionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerQuestion.setAdapter(questionAdapter);
	}

	@Override
	public void initObjectToDesign() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListener() {
		checkOtherQuestion.setOnCheckedChangeListener(this);
		
		showOtherQuestionAnim = AnimationUtils.loadAnimation(this, R.anim.slide_anim_form);
		hideOtherQuestionAnim = AnimationUtils.loadAnimation(this, R.anim.slide_hide_anim_form);
		showOtherQuestionAnim.setAnimationListener(this);
		hideOtherQuestionAnim.setAnimationListener(this);
		editLayout = (LayoutParams) otherQuestionEdit.getLayoutParams();
		
		activasiButton.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		if (isChecked) {
			otherQuestionEdit.requestLayout();
			otherQuestionEdit.postInvalidate();
			otherQuestionEdit.invalidate();
			otherQuestionEdit.setVisibility(View.VISIBLE);
			otherQuestionEdit.startAnimation(showOtherQuestionAnim);
		} else {
			otherQuestionEdit.startAnimation(hideOtherQuestionAnim);
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if(animation == hideOtherQuestionAnim) {
			editLayout.topMargin = -45;
			otherQuestionEdit.setLayoutParams(editLayout);
			otherQuestionEdit.setVisibility(View.INVISIBLE);
		}
		if(animation == showOtherQuestionAnim) {
			
			otherQuestionEdit.requestFocus();
		}
		
		otherQuestionEdit.requestLayout();
		otherQuestionEdit.invalidate();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		if(animation == showOtherQuestionAnim) {
			editLayout.topMargin = 0;
			otherQuestionEdit.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		if(v == activasiButton) {
			String username = usernameEdit.getText().toString();
			String password = passwordEdit.getText().toString();
			String question = otherQuestionEdit.getText().toString();
			String answer = answerEdit.getText().toString();
			
			if(username.trim().equals("")) {
				Utility.showMessage(this, "Close", getString(R.string.message_usernameEmpty));
			} else if(password.trim().equals("")) {
				Utility.showMessage(this, "Close", getString(R.string.message_passwordEmpty));
			} else {
				if(checkOtherQuestion.isChecked()) {
					if(question.trim().equals("")) {
						Utility.showMessage(this, "Close", getString(R.string.message_questionEmpty));
						return;
					}
				} else {
					question = questionAdapter.getItem(spinnerQuestion.getSelectedItemPosition());
				}
				
				if(answer.trim().equals("")) {
					Utility.showMessage(this, "Close", getString(R.string.message_answerEmpty));
					return;
				}
				
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setSequrityQuestion(question);
				user.setAnswer(answer);
				
				UserDao userDao = UserDao.getInstance(this);
				userDao.createData(user);
				userDao.closeConnection();
				
				startActivity(new Intent(this, LoginActivity.class));
				finish();
			}
		}
	}

}
