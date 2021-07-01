package com.example.pocket_news_project;

public enum LanguageEnum {

    English("en"),
    Spanish("es"),
    Arabic("ar"),
    German("de"),
    French("fr"),
    Hebrew("he"),
    Italian("it"),
    Dutch("nl"),
    Norwegian("no"),
    Portuguese("pt"),
    Russian("ru"),
    Swedish("se"),
    Chinese("zh");

    private String shortValue;

    private LanguageEnum(String value) {
        shortValue = value;
    }

    @Override
    public String toString() {
        return shortValue;
    }

}
