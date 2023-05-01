package model;

import jakarta.persistence.*;

@Entity(name = "superintendents")
public class Superintendent {
    // The Superindendents table is the one with the foreign key,
    // so it gets the JoinColumn attribute.
    @OneToOne
    @JoinColumn(name = "museum_id")
    private Museum museum;

    @Id
    @Column(name = "super_id")
    private int superintendentId;

    private String name;

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public int getSuperintendentId() {
        return superintendentId;
    }

    public void setSuperintendentId(int superintendentId) {
        this.superintendentId = superintendentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Superintendent " + name + " (ID " + superintendentId + ")";
    }

}
