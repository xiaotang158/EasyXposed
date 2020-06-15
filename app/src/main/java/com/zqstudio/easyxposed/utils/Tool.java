package com.zqstudio.easyxposed.utils;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;

/**
 * CreateDate：2020/6/12 9:31
 * author：ShiYong.Z
 * version：1.0
 * Description：	常用的工具类。
 * 	1、获取类：传入类名即可。
 * 	2、输出堆栈：可以辅助分析。
 * 	3、打印所有的的参数。
 * 	4、自动打印日志：根据字符串长度，自动分配日志输出。
 */
public final class Tool {

	private static String TAG = "zqsLog";

	public static ClassLoader classLoader = null;

	static Class<?> clazzForName(String strClazz){
		Class<?> result = null;
		try {
			result = Class.forName(strClazz, false, classLoader);
		} catch (ClassNotFoundException e) {
			myLog("ClassNotFoundException = " + strClazz);
		}
		return result;
	}

	public static void showStack(){
		Log.e(TAG, Log.getStackTraceString(new Throwable()));
	}

	public static void myLog(String msg){
		Log.w(TAG, msg);
	}

	public static void showAllParams(XC_MethodHook.MethodHookParam param){
		StringBuilder str = new StringBuilder();
		for (int i = 0, len = param.args.length; i < len; ++i){
			str.append(" | ").append(param.args[i]);
		}
		Log.i(TAG, str.toString());
	}

	public static void outputMsg(String str){
		int len = str.length();
		int nFileLength = 512 * 7;
		if (len > nFileLength){
			myLog("Long Data = " + len);
			println(str);
		}else{
			myLog(str);
		}
	}

	public static boolean appFilter(String strName){
		// 需要过滤的进程名
		String[] strContain = {"com.google", "com.android"};
		for (String str : strContain){
			if (strName.startsWith(str)){
				return true;
			}
		}
		return false;
	}

	private static void println(String longMsg){
		String buf;
		int start = 0, end, nSize = 3096, len = longMsg.length();
		while (start < len) {
			end = (start + nSize) < len ? start + nSize : len;
			buf = longMsg.substring(start, end);
			start += nSize;
			Log.d(TAG, buf);
		}
	}
}
