package model;

import java.time.*;
import jakarta.persistence.*;

@Entity(name = "museumvisits")
public class MuseumVisit {
    // This class has a three-column PK: the two foreign keys, plus the date of the
    // visit.
    @Id
    @JoinColumn(name = "museum_id")
    @ManyToOne
    private Museum museum;

    @Id
    @JoinColumn(name = "visitor_id")
    @ManyToOne
    private Visitor visitor;

    @Id
    @Column(name = "visit_date")
    private LocalDate visitDate;

    public MuseumVisit() {
    }

    public MuseumVisit(Museum museum, Visitor visitor, LocalDate visitDate) {
        this.museum = museum;
        this.visitor = visitor;
        this.visitDate = visitDate;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

}
