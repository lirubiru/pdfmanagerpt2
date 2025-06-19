package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import exception.CollectionException;

public class EntryCollection implements Serializable {
  private final UUID id;
  private final String name;
  private final String author;
  private final EntryType type;
  private final int maxSize;
  private final List<UUID> entryIds = new ArrayList<>();

  public EntryCollection(String name, String author, EntryType type, int maxSize) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.author = author;
    this.type = type;
    this.maxSize = maxSize;
  }

  public void addEntry(LibraryEntry entry) throws CollectionException {
    if (entryIds.size() >= maxSize) {
      throw new CollectionException("Coleção atingiu capacidade máxima");
    }
    if (entry.getType() != type) {
      throw new CollectionException("Tipo de entrada incompatível com a coleção");
    }
    if (!entry.getAuthors().stream().anyMatch(a -> a.getName().equals(author))) {
      throw new CollectionException("Autor da entrada não corresponde ao autor da coleção");
    }
    if (!entryIds.contains(entry.getId())) {
      entryIds.add(entry.getId());
    }
  }

  public void removeEntry(UUID entryId) {
    entryIds.remove(entryId);
  }

  public List<UUID> getEntryIds() {
    return new ArrayList<>(entryIds);
  }

  // Getters
  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAuthor() {
    return author;
  }

  public EntryType getType() {
    return type;
  }

  public int getSize() {
    return entryIds.size();
  }

  public int getMaxSize() {
    return maxSize;
  }
}