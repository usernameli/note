package com.li.notebook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.li.add.ChangePublishedActivity;
import com.li.add.PublishedActivity;
import com.li.notebook.sqlite.DataBaseHelper;
import com.li.notebook.sqlite.NoteCache;
import com.li.notebook.view.DeletePd;
import com.li.notebook.view.DeletePd.DeletedPdListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnLongClickListener,
		OnClickListener {
	private ImageView add;
	private GridLayout grid1;
	private GridLayout grid2;
	private TextView Titel;
	private TextView Time;
	private TextView text;
	private ImageView locationView;
	private int width;
	private int height;
	private int length;
	private int page;
	private int grad1row;
	private int grad2row;
	private String diaryText[];
	private String diaryTimeText[];
	private String diaryImg[];
	private String diaryTitel[];
	private ViewGroup _root;
	private int _xDelta;
	private SQLiteDatabase readDb;
	private int _yDelta;
	private HashMap<String, List<String>> map;
	Bundle savedInstanceState;
	Date nowTime;
	String retStrFormatNowDate;
	SimpleDateFormat sdFormatter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		savedInstanceState = this.savedInstanceState;
		setContentView(R.layout.activity_main);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels; // �õ����
		height = dm.heightPixels; // �õ��߶�
		initDb();
		// changeTime();
		init();
	}

	private void initDb() {

		com.li.notebook.sqlite.DataBaseHelper baseHelper = new com.li.notebook.sqlite.DataBaseHelper(
				MainActivity.this, "diary_dbs");
		SQLiteDatabase dbs = baseHelper.getReadableDatabase();
		Cursor cursor = dbs.query("diarys", new String[] { "diaryimg",
				"diarytime", "diarytext", "diarytitle" }, null, null, null,
				null, null);
		length = cursor.getCount();
		page = length / 5;
		Toast.makeText(MainActivity.this, "page" + page + "length" + length, 0)
				.show();
		diaryImg = new String[length];
		diaryText = new String[length];
		diaryTimeText = new String[length];
		diaryTitel = new String[length];
		int count = 0;
		while (cursor.moveToNext()) {
			List<String> list = new ArrayList<String>();
			diaryText[count] = cursor.getString(cursor
					.getColumnIndex("diarytext"));
			diaryImg[count] = cursor.getString(cursor
					.getColumnIndex("diaryimg"));
			System.out.println("img" + diaryImg[count]);
			diaryTimeText[count] = cursor.getString(cursor
					.getColumnIndex("diarytime"));
			diaryTitel[count] = cursor.getString(cursor
					.getColumnIndex("diarytitle"));
			count++;

		}
		// Toast.makeText(MainActivity.this, diaryText[length - 1], 0).show();
	}

	@SuppressLint("ResourceAsColor")
	private void init() {
		add = (ImageView) findViewById(R.id.iv_add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, PublishedActivity.class);
				startActivity(intent);
			}
		});
		grid1 = (GridLayout) findViewById(R.id.grid1);
		grid2 = (GridLayout) findViewById(R.id.grid2);
		int count = 1;
		LinearLayout view;
		System.out.println("shuzu++++" + diaryImg.length);
		if (diaryTimeText.length <= 3) {
			if (diaryTimeText.length == 0) {
				grad1row = 0;
				grad2row = 0;
			} else {
				grad1row = 1;
				grad2row = diaryTimeText.length - grad1row;
			}
		} else {
			if (length % 5 == 0) {
				grad1row = diaryText.length / 5 * 2;
				grad2row = diaryTimeText.length - grad1row;

			} else {
				if (length % 5 <= 2) {
					grad1row = diaryText.length / 5 * 2 + 1;
					grad2row = diaryTimeText.length - grad1row;
				} else {
					grad1row = diaryText.length / 5 * 2 + 2;
					grad2row = diaryTimeText.length - grad1row;
				}
			}
		}
		grid1.setRowCount(grad1row);
		System.out.println("grid1     " + grad1row + "grid2    " + grad2row);
		grid1.setColumnCount(1);
		int temp = 0;// 用来记录页数
		for (int j = 0; j < grad1row; j++) {
			GridLayout.LayoutParams params;
			if (j % 2 == 0) {
				// nowTime = new Date(Long.parseLong(diaryTimeText[i]));
				// sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
				// retStrFormatNowDate = sdFormatter.format(nowTime);
				System.out.println("count:" + count);
				view = (LinearLayout) LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.fragement_childview_image, null);
				view.setTag(5 * temp);
				view.setOnLongClickListener(this);
				view.setOnClickListener(this);
				Time = (TextView) view.findViewById(R.id.fc_time);
				text = (TextView) view.findViewById(R.id.fc_text);
				Titel = (TextView) view.findViewById(R.id.fc_titel);
				Time.setText(changeTime(diaryTimeText[5 * temp]));
				text.setText(diaryText[5 * temp]);
				Titel.setText(diaryTitel[5 * temp]);
				view.setLayoutParams(new LayoutParams(200, height * 10 / 25));
				// view.setLayoutParams(new LayoutParams(0, 0));
				view.setBackgroundResource(R.drawable.gd_bg);
				count++;
				GridLayout.Spec rowSpec = GridLayout.spec(j); // ���������к���
				GridLayout.Spec columnSpec = GridLayout.spec(0);
				params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				params.width = width / 2;
				params.height = (int) (height * 4.7 / 10 - height * 3 / 100);

			} else {
				if (grad1row % 5 == 1)
					break;
				System.out.println("count:" + count);
				view = (LinearLayout) LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.fragement_childview_image, null);
				view.setTag(5 * temp + 3);
				view.setOnLongClickListener(this);
				view.setOnClickListener(this);
				Time = (TextView) view.findViewById(R.id.fc_time);
				text = (TextView) view.findViewById(R.id.fc_text);
				Titel = (TextView) view.findViewById(R.id.fc_titel);
				Time.setText(changeTime(diaryTimeText[5 * temp + 3]));
				text.setText(diaryText[5 * temp + 3]);
				Titel.setText(diaryTitel[5 * temp + 3]);
				view.setLayoutParams(new LayoutParams(200, height * 10 / 25));
				// view.setLayoutParams(new LayoutParams(0, 0));
				view.setBackgroundResource(R.drawable.gd_bg);
				count++;
				GridLayout.Spec rowSpec = GridLayout.spec(j); // ���������к���
				GridLayout.Spec columnSpec = GridLayout.spec(0);
				params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				params.width = width / 2;
				params.height = (int) (height * 4.0 / 10 - height * 3 / 100);
				temp++;
			}
			params.setMargins(width / 30, height * 3 / 100, 0, 0);
			grid1.addView(view, params);

		}
		// grad2row = length - grad1row;
		System.out.println("grid2    " + grad2row);
		grid2.setRowCount(grad2row);
		grid2.setColumnCount(1);
		temp = 0;// 用来记录页数
		for (int j = 0; j < grad2row; j++) {
			// System.out.println("count:" + count);
			// view = (LinearLayout) LayoutInflater.from(MainActivity.this)
			// .inflate(R.layout.fragement_childview, null);
			//
			// view.setLayoutParams(new LayoutParams(200, height * 10 / 25));
			// view.setBackgroundResource(R.drawable.gd_bg);
			// view.setOnLongClickListener(this);
			// count++;
			// GridLayout.Spec rowSpec = GridLayout.spec(j); // ���������к���
			// GridLayout.Spec columnSpec = GridLayout.spec(0);
			// GridLayout.LayoutParams params = new GridLayout.LayoutParams(
			// rowSpec, columnSpec);
			// params.setGravity(Gravity.FILL);
			GridLayout.LayoutParams params;
			if (j % 3 == 0) {
				if (grad2row < 1) {
					break;
				}
				view = (LinearLayout) LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.fragement_childview, null);
				Time = (TextView) view.findViewById(R.id.fragment_time_text);
				Titel = (TextView) view.findViewById(R.id.fragment_titel_text);
				Time.setText(changeTime(diaryTimeText[5 * temp + 1]));
				Titel.setText(diaryTitel[5 * temp + 1]);
				view.setLayoutParams(new LayoutParams(200, height * 10 / 25));
				view.setBackgroundResource(R.drawable.gd_bg);
				view.setTag(5 * temp + 1);
				view.setOnClickListener(this);
				view.setOnLongClickListener(this);
				count++;
				GridLayout.Spec rowSpec = GridLayout.spec(j); // ���������к���
				GridLayout.Spec columnSpec = GridLayout.spec(0);
				params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				params.height = (int) (height * 1.8 / 10 - height * 3 / 100);
				params.width = width / 2;
			} else if (j % 3 == 1) {
				if (grad2row < 2) {
					break;
				}
				view = (LinearLayout) LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.fragement_childview_image, null);
				Time = (TextView) view.findViewById(R.id.fc_time);
				text = (TextView) view.findViewById(R.id.fc_text);
				Titel = (TextView) view.findViewById(R.id.fc_titel);
				Time.setText(changeTime(diaryTimeText[5 * temp + 2]));
				text.setText(diaryText[5 * temp + 2]);
				Titel.setText(diaryTitel[5 * temp + 2]);
				view.setLayoutParams(new LayoutParams(200, height * 10 / 25));
				view.setBackgroundResource(R.drawable.gd_bg);
				view.setOnLongClickListener(this);
				view.setTag(5 * temp + 2);
				view.setOnClickListener(this);
				count++;
				GridLayout.Spec rowSpec = GridLayout.spec(j); // ���������к���
				GridLayout.Spec columnSpec = GridLayout.spec(0);
				params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				params.width = width / 2;
				params.height = (int) (height * 4.7 / 10 - height * 3 / 100);
			} else {
				if (grad2row < 3) {
					break;

				}
				view = (LinearLayout) LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.fragement_childview, null);
				Time = (TextView) view.findViewById(R.id.fragment_time_text);
				Titel = (TextView) view.findViewById(R.id.fragment_titel_text);
				Time.setText(changeTime(diaryTimeText[5 * temp + 4]));
				Titel.setText(diaryTitel[5 * temp + 4]);
				view.setLayoutParams(new LayoutParams(200, height * 10 / 25));
				view.setBackgroundResource(R.drawable.gd_bg);
				view.setTag(5 * temp + 4);
				view.setOnClickListener(this);
				view.setOnLongClickListener(this);
				count++;
				GridLayout.Spec rowSpec = GridLayout.spec(j); // ���������к���
				GridLayout.Spec columnSpec = GridLayout.spec(0);
				params = new GridLayout.LayoutParams(rowSpec, columnSpec);
				params.setGravity(Gravity.FILL);
				params.width = width / 2;
				params.height = (int) (height * 2.2 / 10 - height * 3 / 100);
				temp++;
			}

			params.setMargins(width / 30, height * 3 / 100, 0, 0);
			grid2.addView(view, params);

		}
	}

	public void mine(View view) {
		startActivity(new Intent().setClass(MainActivity.this,
				MineActivity.class));
	}

	@Override
	public boolean onLongClick(final View v) {
		// TODO �Զ����ɵķ������
		final DeletePd deletePd = new DeletePd(MainActivity.this);
		deletePd.setDeletedPdListener(new DeletedPdListener() {

			@Override
			public void delete() {
				deletePd.dismiss();
				System.out.println("tag++++++" + v.getTag());
				Toast.makeText(MainActivity.this, "点击删除" + v.getTag(), 2000)
						.show();

				deleteNote(diaryTimeText[(Integer) v.getTag()]);
			}

			private void deleteNote(String time) {
				// TODO 自动生成的方法存根
				DataBaseHelper dbHelper = new DataBaseHelper(MainActivity.this,
						"diary_dbs", null, 1);
				// 得到一个可写的数据库
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				String whereClauses = "diarytime=?";
				String[] whereArgs = { String.valueOf(time) };
				// 调用delete方法，删除数据
				db.delete("diarys", whereClauses, whereArgs);
				onCreate(savedInstanceState);
			}
		});
		deletePd.show();
		return false;
	}

	@SuppressLint("SimpleDateFormat")
	public String changeTime(String TimeText) {
		Date nowTime;
		String retStrFormatNowDate;
		SimpleDateFormat sdFormatter;
		nowTime = new Date(Long.parseLong(TimeText));
		sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;

	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		NoteCache.setNoteImg(diaryImg[(Integer) v.getTag()]);
		NoteCache.setNoteText(diaryText[(Integer) v.getTag()]);
		NoteCache.setNoteTime(diaryTimeText[(Integer) v.getTag()]);
		NoteCache.setNoteTitle(diaryTitel[(Integer) v.getTag()]);
		startActivity(new Intent().setClass(MainActivity.this,
				ChangePublishedActivity.class));
	}
}
