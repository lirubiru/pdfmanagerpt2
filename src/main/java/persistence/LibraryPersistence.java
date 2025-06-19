package persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LibraryPersistence {
  private static final String CONFIG_FILE = "library.config";
  private static final String LIBRARIES_FILE = "libraries.config";

  public static void saveCurrentLibrary(String path) throws IOException {
    Files.write(Paths.get(CONFIG_FILE), path.getBytes());
  }

  public static String loadCurrentLibrary() {
    try {
      return new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
    } catch (IOException e) {
      return null;
    }
  }

  public static void addLibrary(String path) throws IOException {
    List<String> libraries = getAllLibraries();
    if (!libraries.contains(path)) {
      libraries.add(path);
      saveAllLibraries(libraries);
    }
  }

  public static void removeLibrary(String path) throws IOException {
    List<String> libraries = getAllLibraries();
    libraries.remove(path);
    saveAllLibraries(libraries);

    if (path.equals(loadCurrentLibrary())) {
      new File(CONFIG_FILE).delete();
    }
  }

  public static List<String> getAllLibraries() {
    try {
      return Files.readAllLines(Paths.get(LIBRARIES_FILE));
    } catch (IOException e) {
      return new ArrayList<>();
    }
  }

  private static void saveAllLibraries(List<String> libraries) throws IOException {
    Files.write(Paths.get(LIBRARIES_FILE), libraries);
  }
}