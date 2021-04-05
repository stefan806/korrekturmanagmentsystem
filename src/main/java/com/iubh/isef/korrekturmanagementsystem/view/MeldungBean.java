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
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@ApplicationScoped
@NoArgsConstructor
@Named
@ManagedBean
@Configurable
@ComponentScan
public class MeldungBean {

    private MeldungRepository meldungRepository;

    private KurseRepository kurseRepository;

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

    /**
     * Constructor for MeldungBean
     *
     * @param meldungRepository
     * @param kurseRepository
     * @param loggedInUserService
     */
    @Autowired
    public MeldungBean(MeldungRepository meldungRepository, KurseRepository kurseRepository, LoggedInUserService loggedInUserService) {
        this.meldungRepository = meldungRepository;
        this.kurseRepository = kurseRepository;
        this.loggedInUserService = loggedInUserService;
    }

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

    /**
     * used to set view for report site
     *
     * @param createMode
     */
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
        saveComment(false, false);
    }

    /**
     * saves given comment to report. If rueckfrage is true, the comment will be added and the status of the report is set to 'Zurückgestellt / Rückfrage'.
     *
     * @param rueckfrage boolean
     */
    public void saveComment(boolean rueckfrage, boolean antwort) {
        if (kommentarToCreate != null && !kommentarToCreate.equals("")) {
            List<Kommentar> kommentare = meldungToCreate.getKommentare();
            Kommentar kommentar = new Kommentar();
            kommentar.setInhalt((rueckfrage ? "Rückfrage von " : "") + (antwort ? "Antwort von " : "") +
                    loggedInUserService.getLoggedInUser().getVorname() + " "
                    + loggedInUserService.getLoggedInUser().getNachname()
                    + " (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm:ss")) + "): "
                    +
                    kommentarToCreate);
            kommentarToCreate = "";
            kommentare.add(kommentar);
            if (meldungToCreate.getStatus() == Status.ZURUECKGESTELLT) {
                meldungToCreate.setStatus(Status.ANGELEGT);
                meldungToCreate.setEditor(null);
            }
            if (rueckfrage) {
                meldungToCreate.setStatus(Status.ZURUECKGESTELLT);
                meldungToCreate.setEditor(meldungToCreate.getCreator());
            }
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

    public List<String> getComments() {
        List<String> comments = meldungToCreate.getKommentare().stream().map(Kommentar::getInhalt).collect(Collectors.toList());
        List<String> results = new ArrayList<>();
        for (String comment : comments) {
            boolean contains = false;
            for (String result : results) {
                if (result.substring(0, comment.indexOf("):")).equals(comment.substring(0, comment.indexOf("):")))) {
                    contains = true;
                }
            }
            if (!contains) {
                results.add(comment);
            }
        }
        return results;

    }
}
