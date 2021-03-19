package com.iubh.isef.korrekturmanagementsystem.enumeration;

public enum Prioritaet {
    PRIORITAET_1("Priorität 1"),
    PRIORITAET_2("Priorität 2"),
    PRIORITAET_3("Priorität 3");

    private String beschreibung;

    Prioritaet(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
