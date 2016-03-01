package com.li.notebook.sqlite;

public class NoteCache {
	private static String NoteTitle;
	private static String NoteText;
	private static String NoteTime;
	private static String NoteImg;

	public static String getNoteTitle() {
		return NoteTitle;
	}

	public static void setNoteTitle(String noteTitle) {
		NoteTitle = noteTitle;
	}

	public static String getNoteText() {
		return NoteText;
	}

	public static void setNoteText(String noteText) {
		NoteText = noteText;
	}

	public static String getNoteTime() {
		return NoteTime;
	}

	public static void setNoteTime(String noteTime) {
		NoteTime = noteTime;
	}

	public static String getNoteImg() {
		return NoteImg;
	}

	public static void setNoteImg(String noteImg) {
		NoteImg = noteImg;
	}

	public static void setToEmpty() {
		NoteImg = "";
		NoteText = "";
		NoteTime = "";
		NoteTitle = "";
	}

}
