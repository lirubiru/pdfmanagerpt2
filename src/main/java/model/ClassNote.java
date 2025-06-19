package model;

import java.util.List;

public class ClassNote extends LibraryEntry {
  private String subtitle;
  private String discipline;
  private String institution;
  private Integer pageCount;

  public ClassNote(String title, List<Author> authors, String pdfPath,
      String discipline) {
    super(title, authors, pdfPath);
    this.discipline = discipline;
  }

  @Override
  public EntryType getType() {
    return EntryType.CLASS_NOTE;
  }

  @Override
  public String getCategory() {
    return discipline;
  }

  // Getters e setters adicionais
  public String getSubtitle() {
    return subtitle;
  }

  public String getDiscipline() {
    return discipline;
  }

  public String getInstitution() {
    return institution;
  }

  public Integer getPageCount() {
    return pageCount;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public void setDiscipline(String discipline) {
    this.discipline = discipline;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public void setPageCount(Integer pageCount) {
    this.pageCount = pageCount;
  }
}