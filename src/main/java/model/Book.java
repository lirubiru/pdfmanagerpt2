package model;

import java.util.List;

public class Book extends LibraryEntry {
  private String subtitle;
  private String knowledgeArea;
  private int publicationYear;
  private String publisher;
  private Integer pageCount;

  public Book(String title, List<Author> authors, String pdfPath,
      String knowledgeArea, int publicationYear) {
    super(title, authors, pdfPath);
    this.knowledgeArea = knowledgeArea;
    this.publicationYear = publicationYear;
  }

  @Override
  public EntryType getType() {
    return EntryType.BOOK;
  }

  @Override
  public String getCategory() {
    return knowledgeArea;
  }

  // Getters e setters adicionais
  public String getSubtitle() {
    return subtitle;
  }

  public String getKnowledgeArea() {
    return knowledgeArea;
  }

  public int getPublicationYear() {
    return publicationYear;
  }

  public String getPublisher() {
    return publisher;
  }

  public Integer getPageCount() {
    return pageCount;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public void setKnowledgeArea(String knowledgeArea) {
    this.knowledgeArea = knowledgeArea;
  }

  public void setPublicationYear(int publicationYear) {
    this.publicationYear = publicationYear;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }
}