package com.cash.flow.activity.setting;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseMyActivity;
import com.cash.flow.database.dao.UserDao;
import com.cash.flow.model.User;
import com.cash.flow.util.Utility;

public class ForgotPasswordActivity extends BaseMyActivity implements OnClickListener, AnimationListener{
	
	private LinearLayout panelQuestion;
	private LinearLayout panelPassword;
	
	private TextView hintTv;
	private EditText usernameOrAnswerEdit, passwordEdit;
	
	private Animation showPanelQuestion, hidePanelQuestion;
	private Animation showPanelPassword, hidePanelPassword;
	
	private User user;
	
	private Step step = Step.VALIDASI_USERNAME;
	
	private enum Step {
		VALIDASI_USERNAME,
		QUESTION
	}
	
	@Override
	public void initDesign() {
		super.initDesign();
		
		panelQuestion = (LinearLayout) findViewById(R.id.panelQuestion);
		panelPassword = (LinearLayout) findViewById(R.id.panelCreatePassword);
		
		hintTv = (TextView) panelQuestion.findViewById(R.id.hintTv);
		usernameOrAnswerEdit = (EditText) panelQuestion.findViewById(R.id.usernameEdit);
		passwordEdit = (EditText) panelPassword.findViewById(R.id.passwordEdit);
		
		showPanelQuestion = AnimationUtils.loadAnimation(this, R.anim.slide_anim_form);
		hidePanelQuestion = AnimationUtils.loadAnimation(this, R.anim.slide_hide_anim_form);
		
		showPanelPassword = AnimationUtils.loadAnimation(this, R.anim.slide_anim_form);
		hidePanelPassword = AnimationUtils.loadAnimation(this, R.anim.slide_hide_anim_form);
	}
	
	@Override
	public void initListener() {
		super.initListener();
		
		findViewById(R.id.back_button).setOnClickListener(this);
		findViewById(R.id.button_password).setOnClickListener(this);
		findViewById(R.id.button_question).setOnClickListener(this);
		
		showPanelQuestion.setAnimationListener(this);
		hidePanelQuestion.setAnimationListener(this);
		
		showPanelPassword.setAnimationListener(this);
		hidePanelPassword.setAnimationListener(this);
	}

	@Override
	public int getLayoutId() {
		return R.layout.forgot_layout_page;
	}

	@Override
	public int getIdViewToAppendFromInflate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIdViewToInflate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_button:
			this.finish();
			break;
		case R.id.button_question:
			if(step.equals(Step.VALIDASI_USERNAME)) {
				String username = usernameOrAnswerEdit.getText().toString();
				
				if(username.trim().equals("")) {
					Utility.showMessage(this, "Close", getString(R.string.message_usernameEmpty));
				} else {
					UserDao userDao = UserDao.getInstance(this);
					User user = userDao.findUser(username);
					
					if(user == null) {
						Utility.showMessage(this, "Close", getString(R.string.message_usernameNotFound));
					} else {
						this.user = user;
						hintTv.setText(user.getSequrityQuestion());
						usernameOrAnswerEdit.setText("");
						usernameOrAnswerEdit.setHint("Insert your Answer");
						this.step = Step.QUESTION;
					}
					
					userDao.closeConnection();
				}
				
			} else if(step.equals(Step.QUESTION)) {
				String answer = usernameOrAnswerEdit.getText().toString();
				
				if(answer.trim().equals("")) {
					Utility.showMessage(this, "Close", getString(R.string.message_answerEmpty));
				} else {
					if(!answer.equalsIgnoreCase(user.getAnswer())) {
						Utility.showMessage(this, "Close", getString(R.string.message_answerWrong));
					} else {
						panelQuestion.startAnimation(hidePanelQuestion);
					}
				}
				
			}
			break;
		case R.id.button_password:
			String password = passwordEdit.getText().toString();
			
			if(password.trim().equals("")) {
				Utility.showMessage(this, "Close", getString(R.string.message_passwordEmpty));
			} else {
				user.setPassword(password);
				UserDao userDao = UserDao.getInstance(this);
				userDao.updateUser(user);
				userDao.closeConnection();
				
				this.finish();
			}
			//panelPassword.startAnimation(hidePanelPassword);
			break;
		default:
			break;
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if(animation == hidePanelQuestion) {
			panelQuestion.setVisibility(View.INVISIBLE);
			panelPassword.startAnimation(showPanelPassword);
		}
		
		if(animation == hidePanelPassword) {
			panelPassword.setVisibility(View.INVISIBLE);
			panelQuestion.startAnimation(showPanelQuestion);
		}
		
		if(animation == showPanelPassword) {
			passwordEdit.requestFocus();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
        if(animation == showPanelQuestion) {
			panelQuestion.setVisibility(View.VISIBLE);
		}

		if(animation == showPanelPassword) {
			panelPassword.setVisibility(View.VISIBLE);
		}
	}

}
