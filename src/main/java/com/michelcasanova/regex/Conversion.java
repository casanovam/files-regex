package com.michelcasanova.regex;


/**
 * The Class Conversion.
 * @author Miguel Casanova
 * 
 */
public class Conversion {
	
	/** The regex. */
	private String regex;
	
	/** The replacement. */
	private String replacement;
	
	
	/**
	 * Instantiates a new conversion.
	 *
	 * @param regex the regex
	 * @param replacement the replacement
	 */
	public Conversion(String regex, String replacement){
		
		this.regex = regex;
		this.replacement = replacement;
	}


	/**
	 * Gets the regex.
	 *
	 * @return the regex
	 */
	public String getRegex() {
		return regex;
	}


	/**
	 * Sets the regex.
	 *
	 * @param regex the new regex
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}


	/**
	 * Gets the replacement.
	 *
	 * @return the replacement
	 */
	public String getReplacement() {
		return replacement;
	}


	/**
	 * Sets the replacement.
	 *
	 * @param replacement the new replacement
	 */
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	

}
