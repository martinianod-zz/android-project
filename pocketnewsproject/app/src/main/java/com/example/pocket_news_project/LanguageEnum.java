package com.example.pocket_news_project;

public enum LanguageEnum {

    English("en"),
    Spanish( "es");

    private String shortValue;

    private LanguageEnum( String value) {
        shortValue = value;
    }

    @Override
    public String toString() {
        return shortValue;
    }

}
