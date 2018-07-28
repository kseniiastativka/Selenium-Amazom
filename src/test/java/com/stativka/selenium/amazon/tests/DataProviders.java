package com.stativka.selenium.amazon.tests;

import com.stativka.selenium.amazon.models.SearchItem;
import com.tngtech.java.junit.dataprovider.DataProvider;

import java.util.List;

import static java.util.Arrays.asList;

public class DataProviders {

	@DataProvider
	public static List<List<List<SearchItem>>> validSearchItems() {
		return prepareData(asList(
			new SearchItem("Clean Architecture", "Robert C. Martin", 2),
			new SearchItem("Refactoring", "Improving the Design of Existing Code"),
			new SearchItem("Cucumber book", "Java Book")
		));
	}

	private static <T> List<List<T>> prepareData(T list) {
		return asList(asList(list));
	}
}
