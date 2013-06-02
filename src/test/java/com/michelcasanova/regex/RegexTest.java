package com.michelcasanova.regex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RegexTest {
	
	private Path path;
	private String regex;
	private String replacement;
	
	@Before
	public void setUp(){
		
		try {
			
			path = Files.createTempFile(null, null);
			Files.write(path, createMockFileContent(), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			regex = "^A";
			replacement = "0";
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testApplyRegex(){
		
		try {
			
			Regex.apply(path.toString(), regex, replacement);
			Assert.assertEquals("0AAAAAAAAAAA", Files.readAllLines(path, StandardCharsets.UTF_8).get(0));
		} 
		catch (IOException e) {
			
			Assert.assertTrue("Exception: "+e.toString(), false);
		}
	}
	
	@Test
	public void testApplyRegexList(){
		
		try {
			
			List<Conversion> regexList = new ArrayList<Conversion>();
			Conversion conversion = new Conversion(null, null);
			conversion.setRegex(regex);
			conversion.setReplacement(replacement);
			regexList.add(conversion);
			Regex.apply(path.toString(), regexList);
			Assert.assertEquals("0AAAAAAAAAAA", Files.readAllLines(path, StandardCharsets.UTF_8).get(0));
		} 
		catch (IOException e) {
			
			Assert.assertTrue("Exception: "+e.toString(), false);
		}
	}
	
	@Test
	@Ignore
	public void testApplyRegexToFolder(){
		
		try {
			
			Regex.apply(path.getParent().toString(), regex, replacement);
		} 
		catch (IOException e) {
			
			e.printStackTrace();
			Assert.assertTrue("Exception: "+e.toString(), false);
		}
	}
	
	private List<String> createMockFileContent(){
		
		List<String> content = new ArrayList<String>();
		
		content.add("AAAAAAAAAAAA");
		content.add("BBBBBBBBBBBB");
		content.add("CCCCCCCCCCCC");
		content.add("DDDDDDDDDDDD");
		content.add("EEEEEEEEEEEE");
		content.add("FFFFFFFFFFFF");
		content.add("GGGGGGGGGGGG");
		
		return content;
	}
}
