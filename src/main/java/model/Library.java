package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import persistence.CollectionPersistence;
import persistence.EntryPersistence;

public class Library {
  private String path;
  private List<LibraryEntry> entries = new ArrayList<>();
  private List<EntryCollection> collections = new ArrayList<>();

  public Library(String path) {
    this.path = path;
    new File(path).mkdirs();
    loadData();
  }

  private void loadData() {
    this.entries = EntryPersistence.loadEntries(path);
    this.collections = CollectionPersistence.loadCollections(path);
  }

  private void loadEntries() {
    this.entries = EntryPersistence.loadEntries(path);
  }

  public void addEntry(LibraryEntry entry) throws Exception {
    entry.saveToLibrary(path);
    entries.add(entry);
    EntryPersistence.saveEntry(path, entry);
  }

  public void deleteEntry(UUID id) throws Exception {
    LibraryEntry entry = getEntryById(id);
    if (entry != null) {
      entry.deleteFile();
      entries.remove(entry);
      EntryPersistence.deleteEntry(path, id);
    }
  }

  public void updateEntry(LibraryEntry updatedEntry) throws Exception {
    LibraryEntry existingEntry = getEntryById(updatedEntry.getId());
    if (existingEntry != null) {
      // Atualiza os campos
      existingEntry.setTitle(updatedEntry.getTitle());
      existingEntry.setAuthors(updatedEntry.getAuthors());

      // Atualiza arquivo PDF se necessário
      if (!existingEntry.originalPdfPath.equals(updatedEntry.originalPdfPath)) {
        existingEntry.deleteFile();
        existingEntry.originalPdfPath = updatedEntry.originalPdfPath;
        existingEntry.saveToLibrary(path);
      }

      // Salva alterações
      EntryPersistence.saveEntry(path, existingEntry);
    }
  }

  public LibraryEntry getEntryById(UUID id) {
    return entries.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  public List<LibraryEntry> searchEntries(String query) {
    return entries.stream()
        .filter(e -> e.getTitle().contains(query) ||
            e.getAuthors().stream().anyMatch(a -> a.getName().contains(query)) ||
            e.getCategory().contains(query))
        .collect(Collectors.toList());
  }

  public List<LibraryEntry> getAllEntries() {
    return new ArrayList<>(entries);
  }

  public String getPath() {
    return path;
  }

  public boolean deleteLibrary() {
    EntryPersistence.deleteAllEntries(path);
    return deleteDirectory(new File(path));
  }

  private boolean deleteDirectory(File directory) {
    if (directory.exists()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isDirectory()) {
            deleteDirectory(file);
          } else {
            file.delete();
          }
        }
      }
    }
    return directory.delete();
  }

  public void addCollection(EntryCollection collection) throws Exception {
    collections.add(collection);
    CollectionPersistence.saveCollection(path, collection);
  }

  public void removeCollection(UUID collectionId) {
    collections.removeIf(c -> c.getId().equals(collectionId));
    CollectionPersistence.deleteCollection(path, collectionId);
  }

  public void addEntryToCollection(UUID collectionId, LibraryEntry entry) throws Exception {
    EntryCollection collection = getCollectionById(collectionId);
    if (collection != null) {
      collection.addEntry(entry);
      CollectionPersistence.saveCollection(path, collection);
    }
  }

  public void removeEntryFromCollection(UUID collectionId, UUID entryId) throws Exception {
    EntryCollection collection = getCollectionById(collectionId);
    if (collection != null) {
      collection.removeEntry(entryId);
      if (collection.getSize() == 0) {
        removeCollection(collectionId);
      } else {
        CollectionPersistence.saveCollection(path, collection);
      }
    }
  }

  public List<LibraryEntry> resolveCollectionEntries(EntryCollection collection) {
    return collection.getEntryIds().stream()
        .map(this::getEntryById)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  public List<EntryCollection> getCollectionsByAuthor(String author) {
    return collections.stream()
        .filter(c -> c.getAuthor().equals(author))
        .collect(Collectors.toList());
  }

  public List<EntryCollection> getCollectionsByType(EntryType type) {
    return collections.stream()
        .filter(c -> c.getType() == type)
        .collect(Collectors.toList());
  }

  public EntryCollection getCollectionById(UUID id) {
    return collections.stream()
        .filter(c -> c.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  public List<EntryCollection> getAllCollections() {
    return new ArrayList<>(collections);
  }
}