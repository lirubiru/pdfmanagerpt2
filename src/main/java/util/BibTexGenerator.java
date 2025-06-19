package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import model.Book;
import model.EntryCollection;
import model.Library;
import model.LibraryEntry;

public class BibTexGenerator {
  public static void generateBibTex(EntryCollection collection, Library library, String outputPath) throws IOException {
    List<LibraryEntry> entries = library.resolveCollectionEntries(collection);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
      writer.write("@book{Collection_" + collection.getId().toString().replace("-", "") + ",\n");
      writer.write("  title = {" + collection.getName() + "},\n");
      writer.write("  author = {" + collection.getAuthor() + "},\n");
      writer.write("  year = {" + java.time.Year.now() + "},\n");
      writer.write("  entries = {\n");

      for (int i = 0; i < entries.size(); i++) {
        LibraryEntry entry = entries.get(i);
        writer.write("    " + (i + 1) + ". ");

        if (entry instanceof Book book) {
          writer.write(book.getTitle() + " (" + book.getPublicationYear() + ")");
        } else {
          writer.write(entry.getTitle());
        }

        if (i < entries.size() - 1)
          writer.write(",\n");
      }

      writer.write("\n  }\n}");
    }
  }
}