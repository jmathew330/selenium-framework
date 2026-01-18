package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	private static Properties properties;
	
	
	static {
		
		try {
			
			properties = new Properties();
			InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");
			
			if(input == null) {
				throw new RuntimeException("config.properties not found in classpath");
			}
			
			properties.load(input);
			
		} catch(Exception e) {
			throw new RuntimeException("Failed to load config.properties", e);
		}	
		
	}
	
	public static String getKey(String key) {
		return properties.getProperty(key).trim();
	}
	
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(properties.getProperty(key).trim());
	}
	
	public static int getInt(String key) {
		return Integer.parseInt(properties.getProperty(key).trim());
	}

}
