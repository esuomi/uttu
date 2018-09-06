package no.entur.uttu.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "service_journey_unique_name_constraint", columnNames = {"provider_pk", "name"})})
public class ServiceJourney extends GroupOfEntities_VersionStructure {

    private String publicCode;

    private String operatorRef;

    @OneToOne(cascade = CascadeType.ALL)
    private BookingArrangement bookingArrangement;

    @NotNull
    @ManyToOne
    private JourneyPattern journeyPattern;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    private List<DayType> dayTypes;


    @OneToMany(mappedBy = "serviceJourney", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    @OrderBy("order")
    private final List<TimetabledPassingTime> passingTimes = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Notice> notices;


    public JourneyPattern getJourneyPattern() {
        return journeyPattern;
    }

    public void setJourneyPattern(JourneyPattern journeyPattern) {
        this.journeyPattern = journeyPattern;
    }

    public List<TimetabledPassingTime> getPassingTimes() {
        return passingTimes;
    }

    public void setPassingTimes(List<TimetabledPassingTime> passingTimes) {
        this.passingTimes.clear();
        if (passingTimes != null) {
            int i = 1;
            for (TimetabledPassingTime passingTime : passingTimes) {
                passingTime.setOrder(i++);
                passingTime.setServiceJourney(this);
            }
            this.passingTimes.addAll(passingTimes);
        }
    }

    public String getPublicCode() {
        return publicCode;
    }

    public void setPublicCode(String publicCode) {
        this.publicCode = publicCode;
    }

    public String getOperatorRef() {
        return operatorRef;
    }

    public void setOperatorRef(String operatorRef) {
        this.operatorRef = operatorRef;
    }

    public BookingArrangement getBookingArrangement() {
        return bookingArrangement;
    }

    public void setBookingArrangement(BookingArrangement bookingArrangement) {
        this.bookingArrangement = bookingArrangement;
    }

    public List<DayType> getDayTypes() {
        return dayTypes;
    }

    public void setDayTypes(List<DayType> dayTypes) {
        this.dayTypes = dayTypes;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }
}
