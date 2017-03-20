package com.article;

import android.app.Application;
import android.os.Environment;

import com.z.CrashHandler;
import com.z.utils.LogUtils;

/**
 * @ClassName:     SystemApp.java
 * @author         zcs
 * @version        V1.0  
 * @Date           2017年3月9日 下午6:19:03 
 * @Description:   
 */
public class SystemApp extends Application{
	@Override
	public void onCreate() {
		LogUtils.filePath = Environment.getExternalStorageDirectory()+"/article.log";
		//系统崩溃监听
		CrashHandler instance = CrashHandler.getInstance(null);//null，默认log输入
		instance.collectDeviceInfo(this);
		super.onCreate();
	}

}
