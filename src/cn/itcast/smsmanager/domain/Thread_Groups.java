package cn.itcast.smsmanager.domain;

import android.net.Uri;

public class Thread_Groups {

	
	public final static String _ID = "_id";
	public final static String THREAD_ID = "thread_id";
	public final static String GROUP_ID = "group_id";
	
	
	public final static Uri CONTENT_URI = Uri.parse("content://cn.itcast.smsmanager.SmsManagerProvider/thread_groups");
}
