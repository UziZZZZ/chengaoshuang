package cn.itcast.smsmanager;

import android.app.TabActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	
	private TabHost mTabHost;
	private SharedPreferences sp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题
        setContentView(R.layout.main);
        
        mTabHost = getTabHost();
        
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        
        setupConversationTab();
        setupFolderTab();
        setupGroupTab();
        
        //设置tab切换的监听
        mTabHost.setOnTabChangedListener(new MyOnTabChangedListener());
        
        String tag = sp.getString("tag", "");
        if("".equals(tag)){
        	mTabHost.setCurrentTab(0);
        }else{
            mTabHost.setCurrentTabByTag(tag);
        }
        
        
        //创建快捷图标
        createShortCut();

    }
    
    
    private boolean isExist(){
    	boolean isExist = false;
    	int version = getSdkVersion();
    	Uri uri = null;
    	if(version < 2.0){
    		uri = Uri.parse("content://com.android.launcher.settings/favorites");
    	}else{
    		uri = Uri.parse("content://com.android.launcher2.settings/favorites");
    	}
    	String selection = " title = ?";
    	String[] selectionArgs = new String[]{"短信管理器"};
    	Cursor c = getContentResolver().query(uri, null, selection, selectionArgs, null);
    	if(c.getCount() > 0){
    		isExist = true;
    	}
    	c.close();
    	return isExist;
    }
    
    
    /**
     * 得到当前系统sdk版本
     * @return
     */
    private int getSdkVersion(){
    	return android.os.Build.VERSION.SDK_INT;
    }
    
    private void createShortCut() {
		// TODO Auto-generated method stub
    	//先判断该快捷是否存在
    	if(!isExist()){
    		Intent intent = new Intent();
    		//指定动作名称
    		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
    		//指定快捷方式的图标
    		Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.congsmall);
    		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
    		//指定快捷方式的名称
    		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "短信管理器");
    		
    		//指定快捷图标激活哪个activity
    		Intent i = new Intent();
    		i.setAction(Intent.ACTION_MAIN);
    		i.addCategory(Intent.CATEGORY_LAUNCHER);
    		ComponentName component = new ComponentName(this, MainActivity.class);
    		i.setComponent(component);
    		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
    		sendBroadcast(intent);
    	}
	}


	private final class MyOnTabChangedListener implements OnTabChangeListener{

    	/**
    	 * tabId 就是tab所对应的tag值
    	 */
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			Log.i("i", tabId);
			String tag = mTabHost.getCurrentTabTag();
			Editor editor = sp.edit();
			editor.putString("tag", tag);
			editor.commit();
		}
    	
    }

    /**
     * 添加会话tab
     */
	private void setupConversationTab() {
		// TODO Auto-generated method stub
		TabSpec mTabSpec = mTabHost.newTabSpec("convesation");
		mTabSpec.setIndicator(getString(R.string.conversation), getResources().getDrawable(R.drawable.tab_conversation));
		Intent intent = new Intent(this,ConversationActivity.class);
		mTabSpec.setContent(intent);
		mTabHost.addTab(mTabSpec);
	}

	/**
	 * 添加文件夹tab
	 */
	private void setupFolderTab() {
		// TODO Auto-generated method stub
		TabSpec mTabSpec = mTabHost.newTabSpec("folder");
		mTabSpec.setIndicator(getString(R.string.folder), getResources().getDrawable(R.drawable.tab_folder));
		Intent intent = new Intent(this,FolderActivity.class);
		mTabSpec.setContent(intent);
		mTabHost.addTab(mTabSpec);
	}

	/**
	 * 添加群组tab
	 */
	private void setupGroupTab() {
		// TODO Auto-generated method stub
		TabSpec mTabSpec = mTabHost.newTabSpec("group");
		mTabSpec.setIndicator(getString(R.string.group), getResources().getDrawable(R.drawable.tab_group));
		Intent intent = new Intent(this,GroupActivity.class);
		mTabSpec.setContent(intent);
		mTabHost.addTab(mTabSpec);
	}
}