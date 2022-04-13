package com.djq.estate_management.Common;

import tk.mybatis.mapper.util.StringUtil;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {



	/**
	 * yyyyMMdd hhmmssSSS
	 * 日期对象转字符串
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));//**TimeZone时区，
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}



}
