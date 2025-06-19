package persistence;

import model.LibraryEntry;
import model.Book;
import model.ClassNote;
import model.Slide;
import model.EntryType;
import model.Author;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntryPersistence {
  private static final String METADATA_DIR = ".metadata";

  public static void saveEntry(String libraryPath, LibraryEntry entry) throws IOException {
        Path metaDir = Paths.get(libraryPath, METADATA_DIR);
        if (!Files.exists(metaDir)) {
            Files.createDirectories(metaDir);
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(metaDir.resolve(entry.getId().toString() + ".entry").toFile()))) {
            
            oos.writeObject(entry);
        }
    }

  public static List<LibraryEntry> loadEntries(String libraryPath) {
    List<LibraryEntry> entries = new ArrayList<>();
    Path metaDir = Paths.get(libraryPath, METADATA_DIR);

    if (Files.exists(metaDir)) {
      try {
        Files.list(metaDir)
            .filter(path -> path.toString().endsWith(".entry"))
            .forEach(path -> {
              try (ObjectInputStream ois = new ObjectInputStream(
                  new FileInputStream(path.toFile()))) {

                entries.add((LibraryEntry) ois.readObject());
              } catch (Exception e) {
                System.err.println("Error loading entry: " + e.getMessage());
              }
            });
      } catch (IOException e) {
        System.err.println("Error reading metadata: " + e.getMessage());
      }
    }
    return entries;
  }

  public static void deleteEntry(String libraryPath, UUID id) {
    Path entryPath = Paths.get(libraryPath, METADATA_DIR, id.toString() + ".entry");
    try {
      Files.deleteIfExists(entryPath);
    } catch (IOException e) {
      System.err.println("Error deleting entry: " + e.getMessage());
    }
  }

  public static void deleteAllEntries(String libraryPath) {
    Path metaDir = Paths.get(libraryPath, METADATA_DIR);
    if (Files.exists(metaDir)) {
      try {
        Files.walk(metaDir)
            .filter(path -> !path.equals(metaDir))
            .forEach(path -> {
              try {
                Files.delete(path);
              } catch (IOException e) {
                System.err.println("Error deleting metadata: " + e.getMessage());
              }
            });
        Files.delete(metaDir);
      } catch (IOException e) {
        System.err.println("Error deleting metadata directory: " + e.getMessage());
      }
    }
  }
}