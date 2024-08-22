package com.indus.training.persist.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.indus.training.persist.dao.KeyWordsCounterDao;

/**
 * Implementation of the {@link KeyWordsCounterDao} interface. Provides methods
 * to save, retrieve, update, and delete keyword counts in Java source files.
 */
public class KeyWordsCounterImpl implements KeyWordsCounterDao {

	private static final String[] KEYWORDS = { "abstract", "assert", "boolean", "break", "byte", "case", "catch",
			"char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
			"finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
			"native", "new", "null", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
			"volatile", "while" };

	private static String dataSourcePath;

	/**
	 * Constructor that initializes the data source path by loading configuration
	 * properties.
	 */
	public KeyWordsCounterImpl() {
		initializeDataSourcePath();
	}

	/**
	 * Initializes the data source path by loading it from a properties file.
	 */
	public void initializeDataSourcePath() {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			// System.out.println("Initialized dataSourcePath: " + dataSourcePath);
			properties.load(input);
			dataSourcePath = properties.getProperty("dataSourcePath", "");
		} catch (Exception ex) {

		}

	}

	/**
	 * Saves keyword counts from Java files in the specified project directory to a
	 * file.
	 *
	 * @param path the project directory path containing Java files
	 * @return true if the keywords were successfully saved, false otherwise
	 * @throws IOException if an I/O error occurs during saving
	 */
	@Override
	public boolean saveKeyWordsToFile(String path) throws IOException {
		if (path == null || path.trim().isEmpty()) {
			throw new IllegalArgumentException("Path cannot be null or empty.");
		}
		try {
			File directoryPath = new File(path);
			Map<String, Integer> keywordsCount = new HashMap<>();
			for (String keyword : KEYWORDS) {
				keywordsCount.put(keyword, 0);
			}
			processDirectory(directoryPath, keywordsCount);
			File outputFile = new File(dataSourcePath + directoryPath.getName() + "_keywords.txt");
			if (outputFile.exists()) {
				return false;
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
				for (Map.Entry<String, Integer> entry : keywordsCount.entrySet()) {
					if (entry.getValue() != 0) {
						writer.write(entry.getKey() + "=" + entry.getValue());
						writer.newLine();
					}
				}
				return true;
			}
		} catch (Exception e) {
			throw new IOException("Failed to save keywords to file.", e);
		}
	}

	/**
	 * Retrieves keyword counts of a specific project directory from a previously
	 * saved file.
	 *
	 * @param path the directory path for which the keywords need to be retrieved
	 * @return a map of keywords and their counts
	 * @throws IOException if an I/O error occurs during retrieval
	 */
	@Override
	public Map<String, Integer> getKeyWords(String path) throws IOException {
		if (path == null || path.trim().isEmpty()) {
			throw new IllegalArgumentException("Path cannot be null or empty.");
		}

		File directoryPath = new File(path);
		File file = new File(dataSourcePath + directoryPath.getName() + "_keywords.txt");
		Map<String, Integer> keywordCounts = new HashMap<>();

		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] entity = line.split("=");
					String keyword = entity[0].trim();
					Integer count = Integer.parseInt(entity[1].trim());
					keywordCounts.put(keyword, count);
				}
			}
		} else {

			throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
		}

		return keywordCounts;
	}

	/**
	 * Updates the keyword counts in the specified directory by re-processing the
	 * Java files.
	 *
	 * @param path the directory path containing Java files
	 * @return true if the keywords were successfully updated, false otherwise
	 * @throws IOException if an I/O error occurs during updating
	 */
	@Override
	public boolean updateKeyWords(String path) throws IOException {
		if (path == null || path.trim().isEmpty()) {
			throw new IllegalArgumentException("Path cannot be null or empty.");
		}

		File directoryPath = new File(path);
		File file = new File(dataSourcePath + directoryPath.getName() + "_keywords.txt");

		if (file.exists()) {
			if (file.delete()) {
				return saveKeyWordsToFile(path);
			} else {
				throw new IOException("Failed to delete file: " + file.getAbsolutePath());
			}
		} else {
			throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
		}
	}

	/**
	 * Deletes the file containing keyword counts of specific project directory.
	 *
	 * @param path the directory path of the project
	 * @return true if the file was successfully deleted, false otherwise
	 * @throws IOException if an I/O error occurs during deletion
	 */
	@Override
	public boolean deleteKeyWords(String path) throws IOException {
		if (path == null || path.trim().isEmpty()) {
			throw new IllegalArgumentException("Path cannot be null or empty.");
		}

		File directoryPath = new File(path);
		File file = new File(dataSourcePath + directoryPath.getName() + "_keywords.txt");
		if (file.exists()) {
			if (file.delete()) {
				return true;
			} else {
				throw new IOException("Failed to delete file: " + file.getAbsolutePath());
			}
		} else {
			return false;
		}

	}

	/**
	 * Processes a directory and counts the occurrences of keywords in all Java
	 * files.
	 *
	 * @param directory     the directory to process
	 * @param keywordCounts a map to store the keyword counts
	 * @throws IOException if an I/O error occurs during processing
	 */
	private static void processDirectory(File directory, Map<String, Integer> keywordCounts) throws IOException {
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					processDirectory(file, keywordCounts);
				} else if (file.isFile() && file.getName().endsWith(".java")) {
					processFile(file, keywordCounts);
				}
			}
		}
	}

	/**
	 * 
	 * @param file          the java file that need to be processed
	 * @param keywordCounts the map to store keywords
	 * @throws IOException if an I/O error occurs during processing
	 */
	private static void processFile(File file, Map<String, Integer> keywordCounts) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder code = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				code.append(line).append("\n");
			}

			String codeWithoutComments = removeComments(code.toString());
			countOccurrences(codeWithoutComments, keywordCounts);
		}
	}

	/**
	 * To remove comments from the code
	 * 
	 * @param code the code from which the comments should be removed
	 * @return the code without comments
	 */
	private static String removeComments(String code) {
		code = code.replaceAll("(?s)/\\*.*?\\*/", "");
		code = code.replaceAll("//.*", "");
		return code;
	}

	/**
	 * Counts the occurrences of keywords in a String.
	 *
	 * @param text          the text to process
	 * @param keywordCounts a map to store the keyword counts
	 * @throws IOException if an I/O error occurs during processing
	 */
	private static void countOccurrences(String text, Map<String, Integer> keywordCounts) {
		for (String keyword : KEYWORDS) {
			int count = 0;
			int index = 0;
			while ((index = text.indexOf(keyword, index)) != -1) {
				if ((index == 0 || !Character.isJavaIdentifierPart(text.charAt(index - 1)))
						&& (index + keyword.length() == text.length()
								|| !Character.isJavaIdentifierPart(text.charAt(index + keyword.length())))) {
					count++;
				}
				index += keyword.length();
			}
			keywordCounts.put(keyword, keywordCounts.get(keyword) + count);
		}
	}

}
