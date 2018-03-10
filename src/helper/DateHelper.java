package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHelper {

	public static Calendar fetchCalendarDate(String dateFormat, String date){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			System.err.println("ERROR parsing: " + date + " with format: " + dateFormat);
		}
		
		return cal;

	}
	
	public static String fetchStringDate(String dateFormat, Calendar date){

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date.getTime());
		
	}
	
	public static String getLottomaticaFormat(){
		return "yyyy/MM/dd";
	}
	
	public static String getOurFormat(){
		return "dd-MM-yyyy";
	}
}
