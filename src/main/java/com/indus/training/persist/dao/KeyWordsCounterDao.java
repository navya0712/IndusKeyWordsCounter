package com.indus.training.persist.dao;

import java.io.IOException;
import java.util.Map;

/**
 * Provides methods to save, retrieve, update, and delete keyword counts from a
 * file.
 */
public interface KeyWordsCounterDao {

	/**
	 * Saves Keywords Count of Given Project path in a file
	 * 
	 * @param path the Project path
	 * @return true if save was successful else false
	 * @throws IOException if an error occurs during saving
	 */
	public boolean saveKeyWordsToFile(String path) throws IOException;

	/**
	 * Retrieves keyword counts from the specified project path.
	 *
	 * @param path the project path
	 * @return a map of keywords and their counts
	 * @throws IOException if an error occurs during retrieval
	 */
	public Map<String, Integer> getKeyWords(String path) throws IOException;

	/**
	 * Updates keyword counts in the project path.
	 *
	 * @param path the file path
	 * @return true if the update was successful, false otherwise
	 * @throws IOException if an error occurs during updating
	 */
	public boolean updateKeyWords(String path) throws IOException;

	/**
	 * Deletes keyword counts of the Specified Project Path.
	 *
	 * @param path the project path
	 * @return true if the delete was successful, false otherwise
	 * @throws IOException if an error occurs during deletion
	 */
	public boolean deleteKeyWords(String path) throws IOException;
}
