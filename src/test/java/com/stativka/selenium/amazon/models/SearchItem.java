package com.stativka.selenium.amazon.models;

public class SearchItem {
	public final String searchTerm;
	public final String textInItemLink;
	private int quantity = 1;

	public SearchItem(String searchTerm, String textInItemLink) {
		this.searchTerm = searchTerm;
		this.textInItemLink = textInItemLink;
	}

	public SearchItem(String searchTerm, String textInItemLink, int quantity) {
		this(searchTerm, textInItemLink);
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}
}
