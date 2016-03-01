package com.li.notebook.view;

import com.li.notebook.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class Fragment_ChildView extends View {
	public Fragment_ChildView(Context context) {
		super(context);
		// TODO �Զ����ɵĹ��캯�����
	}

	private View ChildView;
	private TextView MainText;
	private TextView TimeText;
	public void onCreate(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		ChildView =inflater.inflate(R.layout.fragement_childview, null);
	//	MainText=(TextView) ChildView.findViewById(R.id.fragment_main_text);
		TimeText = (TextView) ChildView.findViewById(R.id.fragment_time_text);
	//	return ChildView;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO �Զ����ɵķ������
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}



	public void setText(String valueOf) {
		// TODO �Զ����ɵķ������
		
	}
	public void setSize(int i, int j) {
		// TODO �Զ����ɵķ������
		ChildView.setLayoutParams(new LayoutParams(i, j));
	}

}
