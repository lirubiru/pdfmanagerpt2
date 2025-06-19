package persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.EntryCollection;

public class CollectionPersistence {
  private static final String COLLECTION_DIR = ".collections";

  public static void saveCollection(String libraryPath, EntryCollection collection) throws IOException {
        Path colDir = Paths.get(libraryPath, COLLECTION_DIR);
        if (!Files.exists(colDir)) {
            Files.createDirectories(colDir);
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(colDir.resolve(collection.getId() + ".col").toFile()))) {
            
            oos.writeObject(collection);
        }
    }

  public static List<EntryCollection> loadCollections(String libraryPath) {
    List<EntryCollection> collections = new ArrayList<>();
    Path colDir = Paths.get(libraryPath, COLLECTION_DIR);

    if (Files.exists(colDir)) {
      try {
        Files.list(colDir)
            .filter(path -> path.toString().endsWith(".col"))
            .forEach(path -> {
              try (ObjectInputStream ois = new ObjectInputStream(
                  new FileInputStream(path.toFile()))) {

                collections.add((EntryCollection) ois.readObject());
              } catch (Exception e) {
                System.err.println("Error loading collection: " + e.getMessage());
              }
            });
      } catch (IOException e) {
        System.err.println("Error reading collections: " + e.getMessage());
      }
    }
    return collections;
  }

  public static void deleteCollection(String libraryPath, UUID collectionId) {
    Path colPath = Paths.get(libraryPath, COLLECTION_DIR, collectionId + ".col");
    try {
      Files.deleteIfExists(colPath);
    } catch (IOException e) {
      System.err.println("Error deleting collection: " + e.getMessage());
    }
  }
}