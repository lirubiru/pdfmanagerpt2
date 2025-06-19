package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import model.EntryCollection;
import model.Library;
import model.LibraryEntry;

public class CollectionZipper {
  public static void zipCollection(EntryCollection collection, Library library, String outputPath) throws IOException {
    List<LibraryEntry> entries = library.resolveCollectionEntries(collection);
    Path zipPath = Paths.get(outputPath);

    try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()))) {
      for (LibraryEntry entry : entries) {
        File file = new File(entry.getPdfPath());
        if (file.exists()) {
          addToZip(file, zos);
        }
      }
    }
  }

  private static void addToZip(File file, ZipOutputStream zos) throws IOException {
    try (FileInputStream fis = new FileInputStream(file)) {
      ZipEntry zipEntry = new ZipEntry(file.getName());
      zos.putNextEntry(zipEntry);

      byte[] buffer = new byte[1024];
      int length;
      while ((length = fis.read(buffer)) > 0) {
        zos.write(buffer, 0, length);
      }
      zos.closeEntry();
    }
  }
}