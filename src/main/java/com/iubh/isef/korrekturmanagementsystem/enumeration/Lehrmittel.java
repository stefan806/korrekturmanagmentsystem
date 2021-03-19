package com.iubh.isef.korrekturmanagementsystem.enumeration;

public enum Lehrmittel {
    SKRIPT("Skript"),
    VODCAST("Vodcast"),
    PODCAST("Podcast"),
    TUTORIUM("Tutorium"),
    MODULHANDBUCH("Modulhandbuch"),
    MUSTERKLAUSUR("Musterklausur"),
    SHORTCAST("Shortcast"),
    SPRINT("Skript"),
    REPETITORIUM("Repetitorium");

    private String beschreibung;

    Lehrmittel(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
