package com.li.notebook;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MineActivity extends Activity implements OnClickListener {
	ImageView back;
	LinearLayout UserFeedBack;
	LinearLayout AboutUs;
	@TargetApi(19)
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// ͸��������
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		back = (ImageView) findViewById(R.id.mine_back);
		back.setOnClickListener(this);
		UserFeedBack = (LinearLayout) findViewById(R.id.user_feedback);
		UserFeedBack.setOnClickListener(this);
		AboutUs = (LinearLayout) findViewById(R.id.about_us);
		AboutUs.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId()) {
		case R.id.mine_back:
			finish();
			break;
		case R.id.about_us:
			startActivity(new Intent().setClass(MineActivity.this,
					AboutUsActivity.class));
			break;
		case R.id.user_feedback:
			startActivity(new Intent().setClass(MineActivity.this,
					UserFeedBackActivity.class));
			break;
		default:
			break;
		}
	}

	public void back(View view) {
		finish();
	}
}
