package com.stativka.selenium.amazon.tests;

import com.stativka.selenium.amazon.models.SearchItem;
import com.tngtech.java.junit.dataprovider.DataProvider;

import java.util.List;

import static java.util.Arrays.asList;

public class DataProviders {
	private static final SearchItem cleanArchitecture = new SearchItem(
		"Clean Architecture",
		"Robert C. Martin",
		2
	);

	private static final SearchItem refactoring = new SearchItem(
		"Refactoring",
		"Improving the Design of Existing Code"
	);

	private static final SearchItem cucumber = new SearchItem(
		"Cucumber book",
		"Java Book"
	);

	@DataProvider
	public static List<List<List<SearchItem>>> searchItem() {
		return prepareData(asList(refactoring));
	}

	@DataProvider
	public static List<List<List<SearchItem>>> searchItemsShortList() {
		return prepareData(asList(refactoring, cucumber));
	}

	@DataProvider
	public static List<List<List<SearchItem>>> searchItemsFullList() {
		return prepareData(asList(cleanArchitecture, refactoring, cucumber));
	}

	private static <T> List<List<T>> prepareData(T list) {
		return asList(asList(list));
	}
}
