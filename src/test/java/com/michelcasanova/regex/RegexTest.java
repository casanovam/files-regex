package com.michelcasanova.regex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
			
		} catch (IOException e) {

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
