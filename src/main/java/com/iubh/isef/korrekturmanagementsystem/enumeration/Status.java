package com.iubh.isef.korrekturmanagementsystem.enumeration;

public enum Status {
    ANGELEGT("Angelegt"),
    IN_BEARBEITUNG("In Bearbeitung"),
    TEST("Test"),
    ERLEDIGT("Erledigt"),
    ZURUECKGESTELLT("Zurückgestellt");

    private String beschreibung;

    Status(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
