package com.iubh.isef.korrekturmanagementsystem.enumeration;

public enum Kategorie {
    FEHLER("Fehler"),
    VERBESSERUNGSVORSCHLAG("Verbesserungsvorschlag"),
    ERGAENZUNG("Ergänzung");

    private String beschreibung;

    Kategorie(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
