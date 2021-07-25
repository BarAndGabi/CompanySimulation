package application.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UIEventListener {
	void save() throws FileNotFoundException, IOException;

}
