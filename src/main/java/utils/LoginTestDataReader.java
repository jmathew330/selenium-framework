package utils;

import java.io.InputStream;
import java.util.Properties;

public class LoginTestDataReader {
	
	
	private static Properties properties;
	
	
	static {
		
		try {
			
			properties = new Properties();
			
			InputStream input = LoginTestDataReader.class.getClassLoader().getResourceAsStream("testdata/login_testdata.properties");
			
			
			if(input == null) {
				throw new RuntimeException("login_testdata.properties not found in classpath");
			}
			
			properties.load(input);
		} catch(Exception e) {
			throw new RuntimeException("Failed to load login_testdata.properties", e);
		}
		
	}
	
	public static String get(String key) {
		return properties.getProperty(key);
	}
	
	public static String[] getArray(String key) {
		String value = get(key);
		return value.split(",");
	}

}
