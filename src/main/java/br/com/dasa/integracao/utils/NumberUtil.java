package br.com.dasa.integracao.utils;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public final class NumberUtil {
	
	private static final DecimalFormat FORMAT_INTEGER_DEFAULT = new DecimalFormat("#, ###, ###, ##0");
	
	private NumberUtil() {}
	
	public static long startWatch() {
		return System.nanoTime();
	}
	
	public static String stopWatch(long timestamp) {
		long nanoWatch = System.nanoTime() - timestamp;
		
		long minutes = TimeUnit.NANOSECONDS.toMinutes(nanoWatch);
		long seconds = TimeUnit.NANOSECONDS.toSeconds(nanoWatch);
		long milliseconds = TimeUnit.NANOSECONDS.toMillis(nanoWatch);
		
		milliseconds = milliseconds - (seconds * 1000);
		seconds = seconds - (milliseconds * 60);
		
		return formatInteger(minutes) + "min, " + seconds + " seg, " + milliseconds + " mls.";
	}

	private static String formatInteger(Number number) {
		if(number == null)
			return "";
		
		return FORMAT_INTEGER_DEFAULT.format(number);
	}
}
