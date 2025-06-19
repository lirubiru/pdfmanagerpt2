package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public abstract class LibraryEntry {
  protected UUID id;
  protected String title;
  protected List<Author> authors;
  protected String pdfPath;
  protected String originalPdfPath;

  public LibraryEntry(String title, List<Author> authors, String pdfPath) {
    this.id = UUID.randomUUID();
    this.title = title;
    this.authors = authors;
    this.pdfPath = pdfPath;
    this.originalPdfPath = pdfPath;
  }

  public void saveToLibrary(String libraryPath) throws IOException {
    File source = new File(originalPdfPath);
    String authorDir = authors.get(0).getName().replace(" ", "_");
    File destDir = new File(libraryPath + File.separator + authorDir);

    if (!destDir.exists())
      destDir.mkdirs();

    File destFile = new File(destDir, source.getName());
    Files.copy(source.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    this.pdfPath = destFile.getAbsolutePath();
  }

  public void deleteFile() throws IOException {
    if (!pdfPath.equals(originalPdfPath)) {
      Files.deleteIfExists(new File(pdfPath).toPath());
    }
  }

  public abstract EntryType getType();

  public abstract String getCategory();

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public String getPdfPath() {
    return pdfPath;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public void setPdfPath(String pdfPath) {
    this.pdfPath = pdfPath;
  }
}