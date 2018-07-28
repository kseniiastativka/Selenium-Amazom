package com.stativka.selenium.amazon.tests;

import com.stativka.selenium.amazon.models.SearchItem;
import com.tngtech.java.junit.dataprovider.DataProvider;

import java.util.List;

import static java.util.Arrays.asList;

public class DataProviders {

  @DataProvider
  public static List<List<List<SearchItem>>> validSearchItems() {
    return prepareData(asList(
      new SearchItem("MacBook Pro 15", "16GB"),
      new SearchItem("Surface Pro", "8GB"),
      new SearchItem("Google Pixelbook", "8GB")
    ));
  }

  private static <T> List<List<T>> prepareData(T list) {
    return asList(asList(list));
  }
}
