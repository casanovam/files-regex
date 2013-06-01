package com.michelcasanova.regex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class Regex.
 * 
 * @author Miguel A. Casanova
 * 
 */
public class Regex {
	
	private static final String TEMP_FILE_NAME = "temp.tmp";
	private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
	
	/**
	 * Apply.
	 *
	 * @param path file or directory path. In case of directory the changes are applied to all the files contained in the folder and sub-folders
	 * @param regEx the regular expression that is going to be replaced
	 * @param replacement the replacement
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(String path, String regex, String replacement)throws IOException{
		
		apply(new File(path), regex, replacement);
	}
	
	
	/**
	 * Apply.
	 *
	 * @param file file or directory. In case of directory the changes are applied to all the files contained in the folder and sub-folders
	 * @param regEx the regular expression that is going to be replaced
	 * @param replacement the replacement
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(File file, String regEx, String replacement)throws IOException{
		
		apply(file, regEx, replacement, DEFAULT_ENCODING);
	}

	/**
	 * Apply.
	 *
	 * @param file the file
	 * @param regEx the reg ex
	 * @param replacement the replacement
	 * @param encoding the encoding
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(File file, String regEx, String replacement, Charset encoding)throws IOException{
		
		List<Conversion> convList = new ArrayList<Conversion>();
		convList.add(new Conversion(regEx, replacement));
		
		apply(file, convList, encoding);
		
	}
	
	/**
	 * Apply.
	 *
	 * @param path the path
	 * @param conversions the conversions
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(String path, List<Conversion> conversions)throws IOException{
		
		apply(new File(path), conversions);
	}
	
	/**
	 * Apply.
	 *
	 * @param file the file
	 * @param conversions the conversions
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(File file, List<Conversion> conversions)throws IOException{
		
		apply(file, conversions, DEFAULT_ENCODING);
	}

	
	/**
	 * Apply.
	 *
	 * @param file the file
	 * @param regEx the reg ex
	 * @param replacement the replacement
	 * @param encoding the encoding
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void apply(File file, List<Conversion> conversions, Charset encoding)throws IOException{
		
		
		if(file.isDirectory()){
			
			for(File f : file.listFiles()){
				
				apply(f, conversions, encoding);
			}
		}
		else{

			applyRegexToFile(file, conversions, encoding);
		}
	}
	
	
	/**
	 * Apply a regular expression replacement to a file.
	 *
	 * @param file the file
	 * @param regex the regular expression
	 * @param replacement the replacement
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void applyRegexToFile(final File file, final List<Conversion> conversions, Charset encoding) throws IOException{
		
		final BufferedReader input;
		BufferedWriter output;
		File temp;
		
		temp = new File(file.getParent()+File.separator+TEMP_FILE_NAME);
		temp.createNewFile();
		
		input = Files.newBufferedReader(file.toPath(), encoding);
		output = Files.newBufferedWriter(temp.toPath(), encoding,  new OpenOption[] {StandardOpenOption.TRUNCATE_EXISTING});
		
		applyRegexToFile(conversions, input, output);
		
		input.close();
		output.close();
		
		replaceFile(file, temp);
	}


	/**
	 * Apply regex to file.
	 *
	 * @param regex the regex
	 * @param replacement the replacement
	 * @param input the input
	 * @param output the output
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void applyRegexToFile(List<Conversion> conversions, final BufferedReader input, BufferedWriter output) throws IOException {
		
		String line;
		String lineUpdated;
		
		while( (line = input.readLine()) != null){
			
			lineUpdated = applyAllRegEx(line, conversions);
			
			output.write(lineUpdated+"\n");
		}
	}
	
	/**
	 * Apply all reg ex.
	 *
	 * @param line the line
	 * @param conversions the conversions
	 * @return the string
	 */
	private static String applyAllRegEx(String line, List<Conversion> conversions){
		
		for(Conversion c: conversions){

			line = line.replaceAll(c.getRegex(), c.getReplacement());
		}
		
		return line;
	}
	
	
	/**
	 * Replace Override the file "file" with the content of fileToReplace
	 *
	 * @param file the file
	 * @param fileToReplace the file to replace
	 */
	private static void replaceFile(File file, File fileToReplace){
		
		String path = file.getPath();
		file.delete();
		fileToReplace.renameTo(new File(path));
	}	
}
