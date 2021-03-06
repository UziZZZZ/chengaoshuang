package cn.itcast.smsmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SmsManagerDBHelper extends SQLiteOpenHelper {

	private static SQLiteOpenHelper mInstance;
	private final static String NAME = "smsmanager.db";
	
	private SmsManagerDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public synchronized static SQLiteOpenHelper getInstance(Context context){
		if(mInstance == null){
			mInstance = new SmsManagerDBHelper(context, NAME, null, 1);
		}
		return mInstance;
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
         db.execSQL("CREATE TABLE groups(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
         		"group_name TEXT)");
         db.execSQL("CREATE TABLE thread_groups(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
         		"thread_id INTEGER," +
         		"group_id INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
