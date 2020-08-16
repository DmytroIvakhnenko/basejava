package ru.javawebinar.basejava.model;

public enum ContactType {
  TELEPHONE("Тел."),
  SKYPE("Skype"),
  EMAIL("Почта"),
  LINKEDIN("LinkedIn"),
  GITHUB("GitHUB"),
  STACKOVERFLOW("StackOverflow"),
  HOMEPAGE("Домашняя страница");

  private final String title;

  ContactType(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
