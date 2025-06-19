package model;

import java.util.List;

public class Slide extends LibraryEntry {
  private String discipline;
  private String institution;

  public Slide(String title, List<Author> authors, String pdfPath,
      String discipline) {
    super(title, authors, pdfPath);
    this.discipline = discipline;
  }

  @Override
  public EntryType getType() {
    return EntryType.SLIDE;
  }

  @Override
  public String getCategory() {
    return discipline;
  }

  // Getters e setters adicionais
  public String getDiscipline() {
    return discipline;
  }

  public String getInstitution() {
    return institution;
  }

  public void setDiscipline(String discipline) {
    this.discipline = discipline;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }
}