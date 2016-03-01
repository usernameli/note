package com.li.notebook;

import java.util.Calendar;
import com.li.notebook.sqlite.DataBaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity implements OnClickListener {
	private EditText ed;
	private ImageView IvBack;
	private TextView TvBack;
	private TextView TvSave;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);

		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// ͸��������
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		ed = (EditText) findViewById(R.id.add_et_et);
		IvBack = (ImageView) findViewById(R.id.add_iv_back);
		IvBack.setOnClickListener(this);
		TvBack = (TextView) findViewById(R.id.add_tv_back);
		TvBack.setOnClickListener(this);
		TvSave = (TextView) findViewById(R.id.add_tv_save);
		TvSave.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		switch (v.getId()) {
		case R.id.add_tv_save: {
			saveDate();
			Toast.makeText(AddActivity.this, "�Ѵ洢", 0).show();
			finish();
		}
			break;
		case R.id.add_iv_back:
			finish();
			break;
		case R.id.add_tv_back:
			finish();
			break;

		default:
			break;
		}
	}

	private void saveDate() {
		// TODO �Զ����ɵķ������
		Calendar c = Calendar.getInstance();
		String Diary = ed.getText().toString();
		String DiaryTime = c.get(Calendar.YEAR) + "." + c.get(Calendar.MONTH)
				+ "." + c.get(Calendar.DAY_OF_MONTH);
		insertDateBase(Diary, DiaryTime);
	}

	private void insertDateBase(String diary, String diaryTime) {
		// TODO �Զ����ɵķ������
		DataBaseHelper dbHelper = new DataBaseHelper(AddActivity.this, "diary_db");
		SQLiteDatabase SqlDB = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("diaryimg", 134);
		values.put("diarytime", 134);
		values.put("diarytext", diary);
		values.put("diarytitle", diaryTime);
		SqlDB.insert("diary", null, values);
		SQLiteDatabase dbs = dbHelper.getReadableDatabase();
		Cursor cursor = dbs.query("diary", new String[] { "diarytext", "diarytitle",
				"diaryimg","diarytime" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex("diary"));
			Toast.makeText(AddActivity.this, name, 0).show();
		}
	}

}
