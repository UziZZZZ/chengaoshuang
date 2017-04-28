package cn.itcast.smsmanager.domain;

import android.net.Uri;

public class Groups {

	
	public final static String _ID = "_id";
	public final static String GROUP_NAME = "group_name";
	
	public final static Uri CONTENT_URI = Uri.parse("content://cn.itcast.smsmanager.SmsManagerProvider/groups");
}
