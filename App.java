import java.util.List;
import java.util.Scanner;

import jakarta.persistence.*;
import model.*;
public class App {
    // These demos show finding, creating, and updating individual objects.
    private static void basicDemos() {
        // In true Java fashion, we can't just create an EntityManager; we have to first
        // create a Factory that can then create the Manager. Don't ask me why.

        // The parameter "demoDb" matches the "name" of our data source, set in
        // src/META-INF/persistence.xml.
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("museumDb");
        EntityManager em = factory.createEntityManager();
        
        // The EntityManager object lets us find, create, update, and delete individual
        // instances of our entity classes.
        
        System.out.println("Example 1: find an entity based on its primary key.");
        Museum firstMuseum = em.find(Museum.class, 4); // parameter 2: the primary key value.
        if (firstMuseum != null) {
            System.out.println("Museum with ID 4: " + firstMuseum);
        }
        else {
            System.out.println("There is no museum with ID 4");
        }


        // The next "if" block will protect me if I run this code multiple times.
        // Otherwise we'll keep trying to create an object with a non-unique primary key,
        // and crash the program.
        if (firstMuseum == null) {
            System.out.println();
            System.out.println("Example 2: creating a new entity.");
            
            // We must begin and later end a transaction when modifying the database.
            em.getTransaction().begin();
            
            Museum newMuseum = new Museum(4, "Metropolitan Museum of Art of New York City", 
                "New York, NY");
            // The previous line just creates an object. It's not in the database yet.
            // The next line tells JPA to "stage" the object
            em.persist(newMuseum);

            // Committing the transaction will push/"flush" the changes to the database.
            em.getTransaction().commit();
            System.out.println("Museum " + newMuseum + " added to database. Go check DataGrip if you don't believe me!");

            // Example 3: updating an entity.
            Museum fromDatabase = em.find(Museum.class, 4);
            em.getTransaction().begin();
            fromDatabase.setLocation("Manhattan, New York, NY");
            // This object is already staged, since it was retrieved from the EntityManager.
            // We just need to flush the change.
            em.getTransaction().commit();
        }

        System.out.println();
        System.out.println("Example #3: retrieving an object without its primary key");

        String jpaQuery = "SELECT m FROM museums m WHERE m.location = " +
            "'Manhattan, New York, NY'";



        Museum molaa = em.createQuery(jpaQuery, Museum.class).getSingleResult();
        System.out.println("MOLAA retrieved: " + molaa);

        // If we want to SAFELY involve user input, we use a TypedQuery.
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter a museum name: ");
        String name = input.nextLine();

        // A TypedQuery is strongly typed; a normal Query would not be.
        var namedMuseum = em.createQuery("SELECT m FROM museums m WHERE "
            + "m.name = ?1", Museum.class);
        namedMuseum.setParameter(1, name);
        try {
            Museum requested = namedMuseum.getSingleResult();
            System.out.println("Your requested museum: " + requested);
        }
        catch (NoResultException ex) {
            System.out.println("Museum with name '" + name + "' not found.");
        }

        System.out.println();
        System.out.println("Example #4: Using JPQL to select all museums");
        // This is simple. Just omit the WHERE, and use .getResultList().
        var museums = em.createQuery("select m from museums m", Museum.class).getResultList();

        for (Museum m : museums) {
            System.out.println(m);
        }
    }

    // These demos show how to navigate associations.
    private static void associationDemos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("museumDb");
        EntityManager em = factory.createEntityManager();
        


        System.out.println();
        System.out.println("Example #5: Navigating a one-to-one association");
        // With the annotations set up, navigating from one object to its associated
        // object is a breeze!
        
        Museum molaa = em.find(Museum.class, 1);
        // Museum has a superintendent field that we can access to follow the association:
        System.out.println("MOLAA's Superintendent: " + molaa.getSuperintendent());

        Superintendent sup = em.find(Superintendent.class, 1);



        System.out.println();
        System.out.println("Example #6: Navigating a one-to-many association");
        
        System.out.println("MOLAA's Buildings:");
        for (Building b : molaa.getBuildings()) {
            System.out.println(b);
        }

        System.out.println();
        // In a bidirectional association, we can also walk from the Many to the One...
        Building bu = em.find(Building.class, 1);
        System.out.println(bu + " is in Museum " + bu.getMuseum());

        // ... or even find the Many objects based on the One they are associated with.
        var buildings = em.createQuery("SELECT b FROM BUILDINGS b " +
            "WHERE b.museum.museumId = 1", Building.class).getResultList();

        System.out.println("MOLAA's Buildings, using a query:");
        for (Building b : buildings) {
            System.out.println(b);
        }

        System.out.println();
        System.out.println("Example #7: Navigating a many-to-many association");
        System.out.println("The Members of " + molaa + ":");
        for (Visitor v : molaa.getMembers()) {
            System.out.println(v);
            
            for (Museum m : v.getMemberships()) {
                System.out.println("\tmember of " + m + " ");
            }
        }

        for (MuseumVisit visit : molaa.getVisits()) {
            System.out.println(visit.getVisitor() + " went to " + visit.getMuseum() 
                + " on " + visit.getVisitDate());
        }

        Visitor neal = em.find(Visitor.class, 1);
        for (MuseumVisit visit : neal.getVisits()) {
            System.out.println(neal + " went to " + visit.getMuseum() 
                + " on " + visit.getVisitDate());
        }

    }

    // These demos show the importance of overriding .equals and .hashCode.

    private static void equalityDemos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("museumDb");
        EntityManager em = factory.createEntityManager();
        
       return;
    }

    public static void main(String[] args) throws Exception {
        basicDemos();
        //associationDemos();
        //equalityDemos();
    }
}
