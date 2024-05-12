package za.com.cocamzansi.format;

/**
 * Common ISO date time format patterns.
 */
public enum ISO {

	/**
	 * The most common ISO Date Format {@code yyyy-MM-dd},
	 * e.g. "2000-10-31".
	 */
	DATE("yyyy-MM-dd"),

	/**
	 * The most common ISO Time Format {@code HH:mm:ss.SSSZ},
	 * e.g. "01:30:00.000-05:00".
	 */
	TIME("HH:mm:ss.SSSZ"),

	/**
	 * The most common ISO DateTime Format {@code yyyy-MM-dd'T'HH:mm:ss.SSSZ},
	 * e.g. "2000-10-31 01:30:00.000-05:00".
	 * <p>This is the default if no annotation value is specified.
	 */
	DATE_TIME("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	private String format;
	
	private ISO(final String format){
		this.format = format;
	}
	
	public String getFormat(){
		return format;
	}
	

	
}
