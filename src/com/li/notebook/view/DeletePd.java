package com.li.notebook.view;

import com.li.notebook.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DeletePd extends AlertDialog {
	Context context;
	private DeletedPdListener mDeletedPdListener;

	public DeletePd(Context context) {
		super(context);
		this.context = context;
		// TODO �Զ����ɵĹ��캯�����
	}

	public interface DeletedPdListener {
		void delete();
	}

	public void setDeletedPdListener(DeletedPdListener deletedPdListener) {
		this.mDeletedPdListener = deletedPdListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_delete);
		findViewById(R.id.deletepd_delete).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mDeletedPdListener.delete();
					}
				});
		findViewById(R.id.deletepd_back).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dismiss();
					}
				});

	}
}
