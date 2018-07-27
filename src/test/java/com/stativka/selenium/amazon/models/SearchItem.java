package com.stativka.selenium.amazon.models;

public class SearchItem {
    public final String searchTerm;
    public final String textInItemLink;

    public SearchItem(String searchTerm, String textInItemLink) {
        this.searchTerm = searchTerm;
        this.textInItemLink = textInItemLink;
    }
}
