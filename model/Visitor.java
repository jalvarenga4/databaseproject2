package model;

import java.util.Set;

import jakarta.persistence.*;

@Entity(name = "visitors")
public class Visitor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "visitor_id")
    private int visitorId;

    @Column(length = 100, unique = true)
    private String name;

    // Many-to-many with Museums, via the assocation (junction) class MuseumVisit.
    @OneToMany(mappedBy = "visitor")
    private Set<MuseumVisit> visits;

    // Many-to-many with Museums, with no association class.
    @ManyToMany(mappedBy = "members")
    private Set<Museum> memberships;

    public Visitor() {
    }

    public Visitor(int visitorId, String name) {
        this.visitorId = visitorId;
        this.name = name;
    }
    @Override
    public String toString() {
        return "Visitor " + name + " (ID " + visitorId + ")";
    }


    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Museum> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<Museum> memberships) {
        this.memberships = memberships;
    }

    public Set<MuseumVisit> getVisits() {
        return visits;
    }

    public void setVisits(Set<MuseumVisit> visits) {
        this.visits = visits;
    }

}
