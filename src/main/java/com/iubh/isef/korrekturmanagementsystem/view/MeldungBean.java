package com.iubh.isef.korrekturmanagementsystem.view;

import com.iubh.isef.korrekturmanagementsystem.entity.Kommentar;
import com.iubh.isef.korrekturmanagementsystem.entity.Meldung;
import com.iubh.isef.korrekturmanagementsystem.entity.User;
import com.iubh.isef.korrekturmanagementsystem.enumeration.Kategorie;
import com.iubh.isef.korrekturmanagementsystem.enumeration.Lehrmittel;
import com.iubh.isef.korrekturmanagementsystem.enumeration.Prioritaet;
import com.iubh.isef.korrekturmanagementsystem.enumeration.Status;
import com.iubh.isef.korrekturmanagementsystem.repository.KurseRepository;
import com.iubh.isef.korrekturmanagementsystem.repository.MeldungRepository;
import com.iubh.isef.korrekturmanagementsystem.service.LoggedInUserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Scope("view")
@ManagedBean
@NoArgsConstructor
public class MeldungBean {

    @Autowired
    private MeldungRepository meldungRepository;

    @Autowired
    private KurseRepository kurseRepository;

    @Autowired
    private LoggedInUserService loggedInUserService;

    private boolean createMode = false;
    private boolean detailMode = false;

    private Meldung meldungToCreate;

    private Prioritaet[] prioritaeten = Prioritaet.values();
    private Kategorie[] kategorien = Kategorie.values();
    private Status[] statuses = Status.values();

    private Lehrmittel[] lehrmittel = Lehrmittel.values();

    private String kursName;

    private String kommentarToCreate;

    public Meldung getMeldungToCreate() {
        return meldungToCreate;
    }

    public void setMeldungToCreate(Meldung meldungToCreate) {
        this.meldungToCreate = meldungToCreate;
    }

    public Prioritaet[] getPrioritaeten() {
        return prioritaeten;
    }

    public void setPrioritaeten(Prioritaet[] prioritaeten) {
        this.prioritaeten = prioritaeten;
    }


    public Lehrmittel[] getLehrmittel() {
        return lehrmittel;
    }

    public void setLehrmittel(Lehrmittel[] lehrmittel) {
        this.lehrmittel = lehrmittel;
    }

    public boolean isDetailMode() {
        return detailMode;
    }

    public void setDetailMode(boolean detailMode) {
        this.detailMode = detailMode;
    }

    public String getKommentarToCreate() {
        return kommentarToCreate;
    }

    public void setKommentarToCreate(String kommentarToCreate) {
        this.kommentarToCreate = kommentarToCreate;
    }

    public String getKursName() {
        return kursName;
    }

    public void setKursName(String kursName) {
        this.kursName = kursName;
    }

    public Status[] getStatuses() {
        return statuses;
    }

    public void setStatuses(Status[] statuses) {
        this.statuses = statuses;
    }

    public Kategorie[] getKategorien() {
        return kategorien;
    }

    public void setKategorien(Kategorie[] kategorien) {
        this.kategorien = kategorien;
    }

    public User getLoggedInUser() {
        return loggedInUserService.getLoggedInUser();
    }

    public void setCreateMode(boolean createMode) {
        if (detailMode && !createMode) {
            meldungRepository.save(meldungToCreate);
        }
        meldungToCreate = new Meldung();
        this.createMode = createMode;
        if (createMode) {
            detailMode = false;
        }
    }

    public boolean isCreateMode() {
        return createMode;
    }

    public void saveMeldung() {
        meldungToCreate.setKurs(kurseRepository.findByName(kursName));
        meldungToCreate.setCreator(loggedInUserService.getLoggedInUser());
        meldungRepository.save(meldungToCreate);
        createMode = false;
        detailMode = false;
    }

    public List<Meldung> getAllMeldungen() {
        return StreamSupport.stream(this.meldungRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteMeldung(Meldung meldung) {
        meldungRepository.delete(meldung);
    }

    public void editMeldung(Meldung meldung) {
        setCreateMode(true);
        setDetailMode(true);
        meldungToCreate = meldungRepository.findById(meldung.getId()).orElse(null);
    }

    public String getFormattedDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss"));
    }

    public void saveComment() {
        saveComment(false);
    }

    public void saveComment(boolean rueckfrage) {
        if (kommentarToCreate != null && !kommentarToCreate.equals("")) {
            List<Kommentar> kommentare = meldungToCreate.getKommentare();
            Kommentar kommentar = new Kommentar();
            kommentar.setInhalt(
                    loggedInUserService.getLoggedInUser().getVorname() + " "
                    + loggedInUserService.getLoggedInUser().getNachname()
                    + " (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")) + "): "
                    +
                    kommentarToCreate);
            kommentarToCreate = "";
            kommentare.add(kommentar);
        }
    }

    public void takeOver() {
        this.meldungToCreate.setEditor(loggedInUserService.getLoggedInUser());
        this.meldungToCreate.setStatus(Status.IN_BEARBEITUNG);
    }

    public void toTest() {
        this.meldungToCreate.setEditor(meldungToCreate.getCreator());
        this.meldungToCreate.setStatus(Status.TEST);
    }

    public void toDone() {
        this.meldungToCreate.setEditor(null);
        this.meldungToCreate.setStatus(Status.ERLEDIGT);
    }

    public String getKommentarSize(Meldung meldung) {
        if (meldung.getKommentare() == null) {
            return "-";
        }
        int size = meldung.getKommentare().size();
        if (size == 0) {
            return "-";
        }
        return size + "";
    }
//
//    private void createRueckfrage(String text) {
//        this.meldungToCreate.setEditor(meldungToCreate.getCreator());
//        this.meldungToCreate.setStatus(Status.ZURUECKGESTELLT);
//    }
//
//    private void createRueckfrage() {
//        this.meldungToCreate.setEditor(meldungToCreate.getCreator());
//        this.meldungToCreate.setStatus(Status.ZURUECKGESTELLT);
//    }
}
