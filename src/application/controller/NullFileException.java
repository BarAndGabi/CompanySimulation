package application.controller;

import java.io.FileNotFoundException;

public class NullFileException extends FileNotFoundException {
	public NullFileException() {
		super("There is no file");
	}
}
