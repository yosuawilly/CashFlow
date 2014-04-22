package com.cash.flow.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;

import com.cash.flow.R;
import com.cash.flow.activity.base.BaseActivity;
import com.cash.flow.util.Functional;

public class ActivationActivity extends BaseActivity implements Functional, OnCheckedChangeListener, AnimationListener{
	
	private CheckBox checkOtherQuestion;
	private EditText otherQuestionEdit;
	
	private LayoutParams editLayout;
	private Animation showOtherQuestionAnim;
	private Animation hideOtherQuestionAnim;
	
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
		otherQuestionEdit = (EditText) findViewById(R.id.otherQuestionEdit);
		checkOtherQuestion = (CheckBox) findViewById(R.id.checkOtherQuestion);
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
		editLayout = (LayoutParams) otherQuestionEdit.getLayoutParams();
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean isChecked) {
		if (isChecked) {
			otherQuestionEdit.startAnimation(showOtherQuestionAnim);
		} else {
			otherQuestionEdit.startAnimation(hideOtherQuestionAnim);
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation animation) {
		if(animation == showOtherQuestionAnim) {
			editLayout.topMargin = 0;
			otherQuestionEdit.setVisibility(View.INVISIBLE);
			otherQuestionEdit.setLayoutParams(editLayout);
			otherQuestionEdit.setVisibility(View.VISIBLE);
		}
		if(animation == hideOtherQuestionAnim) {
			editLayout.topMargin = -45;
			otherQuestionEdit.setLayoutParams(editLayout);
			otherQuestionEdit.setVisibility(View.INVISIBLE);
			otherQuestionEdit.requestLayout();
		}
	}

}
