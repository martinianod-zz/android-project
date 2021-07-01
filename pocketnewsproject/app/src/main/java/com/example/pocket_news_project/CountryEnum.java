package com.example.pocket_news_project;

public enum CountryEnum {

    Argentina("ar"),
    Australia("au"),
            Austria("at"),
    Belgium("be"),
            Brazil("br"),
    Bulgaria("bg"),
            Canada("ca"),
    China("cn"),
            Colombia("co"),
    Egypt("eg"),
            France("fr"),
    Germany("de"),
            Greece("gr"),
    Hungary("hu"),
            India("in"),
    Indonesia("id"),
            Ireland("ie"),
    Israel("il"),
            Italy("it"),
    Japan("jp"),
            Mexico("mx"),
    Netherlands("nl"),
            Norway("no"),
    Portugal("pt"),
            Switzerland("ch"),
    United_Kingdom("gb"),
            United_State("us");

    private String shortValue;

    private CountryEnum(String shortValue) {
        this.shortValue = shortValue;
    }

    @Override
    public String toString() {
        return shortValue;
    }
    }
