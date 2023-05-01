package model;

import java.util.*;

import jakarta.persistence.*;

// @Entity identifies this class as corresponding to a database table. If the table 
// is not named the same as the class, an optional name= parameter can be used.
@Entity(name = "museums")
public class Museum {
    // The fields of this class are expected to correspond to the columns of the
    // table.
    // JPA will match Java primitive types with SQL types.
    private String name;
    private String location;

    // All tables need a primary key, but Java doesn't know what that is.
    // The @Id attribute will tell it.
    @Id
    @Column(name = "museum_id")
    private int museumId;

    // Association fields.

    // The mappedBy argument specifies the *name of the field in the Superintendent
    // class*
    // that implements the "other side" of this OneToOne.
    // Each Museum has at most one Superintendent.
    @OneToOne(mappedBy = "museum")
    private Superintendent superintendent;

    // One-to-many with Buildings.
    @OneToMany(mappedBy = "museum")
    private List<Building> buildings;

    // Many-to-many with Visitors, representing the museum's Members.
    // One of the two classes in the many-to-many sets up the JoinTable attribute,
    // which specifies the name of the junction table, which column is the FK to
    // **this** (Museum) class, and which column is to the inverse table -- the
    // *other* class (Visitor) in the association.
    // A JoinTable is only needed when there is no association (junction) class.
    @JoinTable(
        name = "members", 
        joinColumns = @JoinColumn(name = "museum_id"), 
        inverseJoinColumns = @JoinColumn(name = "visitor_id")
    )
    @ManyToMany
    private Set<Visitor> members;

    // Many-to-many with Visitors, via the MuseumVisits association (junction) class.
    @OneToMany(mappedBy = "museum")
    private Set<MuseumVisit> visits;

    // An entity class must always have a parameterless constructor.
    // One is provided by Java *only if you don't have any other constructors*.
    // If you define a constructor with parameters, you must also define
    // a parameterless constructor.
    public Museum() {
    }

    public Museum(int museumId, String name, String location) {
        this.museumId = museumId;
        this.name = name;
        this.location = location;
    }

    @Override
    public String toString() {
        return name + " (ID " + museumId + "), " + location;
    }

    public int getMuseumId() {
        return museumId;
    }

    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Superintendent getSuperintendent() {
        return superintendent;
    }

    public void setSuperintendent(Superintendent superintendent) {
        this.superintendent = superintendent;
    }

    public Set<MuseumVisit> getVisits() {
        return visits;
    }

    public void setVisits(Set<MuseumVisit> visits) {
        this.visits = visits;
    }

    public Set<Visitor> getMembers() {
        return members;
    }

    public void setMembers(Set<Visitor> members) {
        this.members = members;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

}
