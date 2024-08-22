package com.indus.training.persist.dao;

import java.io.IOException;
import java.util.Map;

import com.indus.training.persist.impl.KeyWordsCounterImpl;

import junit.framework.TestCase;

public class TestKeyWordsCounterImpl extends TestCase {

	private KeyWordsCounterImpl keyWordCtImpObj = null;

	/**
	 * Sets Up the Test Environment
	 */
	protected void setUp() throws Exception {
		keyWordCtImpObj = new KeyWordsCounterImpl();
	}

	/**
	 * Tears Down the Test Environment
	 */
	protected void tearDown() throws Exception {
		keyWordCtImpObj = null;
	}

	/**
	 * Test case for saveKeyWordsToFile method where provided path exists
	 */
	public void testSaveKeyWordsToFileScenario1() {
		System.out.println("InSaveS1");
		String path = "C:\\IndusTraining\\Assignments\\IndusAssignment1";
		try {
			System.out.println(keyWordCtImpObj.saveKeyWordsToFile(path));
		} catch (IOException e) {
			System.err.println(e.getMessage());

		}

	}

	/**
	 * Test case for saveKeyWordsToFile method where provided path does not exist
	 */
	public void testSaveKeyWordsToFileScenario2() {
		System.out.println("InSaveS2");
		String path = "C:\\IndusTraining\\Assignments\\IndusAssignment1";
		try {
			System.out.println(keyWordCtImpObj.saveKeyWordsToFile(path));
		} catch (IOException e) {
			System.err.println(e.getMessage());

		}

	}

	/**
	 * Test case for getKeywords method where Keywords for provided path are generated
	 */
	public void testGetKeyWordsScenario1() {
		System.out.println("InGetS1");
		String path = "C:\\IndusTraining\\Assignments\\IndusAssignment1";
		Map<String, Integer> keyWordsCount;
		try {
			keyWordsCount = keyWordCtImpObj.getKeyWords(path);
			System.out.println(keyWordsCount);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Test case for getKeywords method where Keywords for provided path are not generated
	 */
	public void testGetKeyWordsScenario2() {
		System.out.println("InGetS2");
		String path = "C:\\IndusTraining\\Assignments\\IndusAssignment2";
		Map<String, Integer> keyWordsCount;
		try {
			keyWordsCount = keyWordCtImpObj.getKeyWords(path);
			System.out.println(keyWordsCount);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Test case for updateKeywords method 
	 */
	public void testUpdateKeyWords() {
		System.out.println("InUpdate");
		String path = "C:\\IndusTraining\\Assignments\\IndusAssignment2";
		Map<String, Integer> keyWordsCount;
		try {
			keyWordsCount = keyWordCtImpObj.getKeyWords(path);
			System.out.println(keyWordsCount);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Test case for deleteKeywords method where provided path exists
	 */
	public void testDeleteKeyWordsScenario1() {
		System.out.println("InDelete");
		String path = "C:\\IndusTraining\\Assignments\\IndusAssignment1";
		try {
			System.out.println(keyWordCtImpObj.deleteKeyWords(path));
		} catch (IOException e) {
			System.err.println(e.getMessage());

		}

	}
	/**
	 * Test case for deleteKeywords method where provided path does not exists
	 */
	public void testDeleteKeyWordsScenario2() {
		System.out.println("InDelete");
		String path = "C:\\IndusTraining\\Assignments\\IndusAssignment1";
		try {
			System.out.println(keyWordCtImpObj.deleteKeyWords(path));
		} catch (IOException e) {
			System.err.println(e.getMessage());

		}

	}

}
