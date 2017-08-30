package com.jackson_siro.mbible.bibles;

public class HolyBible {

	private int id;
	private String testament, book, chapters, english, abbrevs, swahili;
	
	public HolyBible(){}
	
	public HolyBible(String testament, String book, String chapters, String english, String abbrevs, String swahili) {
		super();
		this.testament = testament;
		this.book = book;
		this.chapters = chapters;
		this.english = english;
		this.abbrevs = abbrevs;
		this.swahili = swahili;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTestament() {
		return testament;
	}
	public void setTestament(String testament) {
		this.testament = testament;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getChapters() {
		return chapters;
	}
	public void setChapters(String chapters) {
		this.chapters = chapters;
	}

	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getAbbrevs() {
		return abbrevs;
	}
	public void setAbbrevs(String abbrevs) {
		this.abbrevs = abbrevs;
	}
	public String getSwahili() {
		return swahili;
	}
	public void setSwahili(String swahili) {
		this.swahili = swahili;
	}
	
	@Override
	public String toString() {
		return "HolyBible [id=" + id + ", testament=" + testament + ", book=" + book + ", chapters=" + chapters +
				"english=" + english + ", abbrevs=" + abbrevs + ", swahili=" + swahili +"]";
	}
	
	
	
}
