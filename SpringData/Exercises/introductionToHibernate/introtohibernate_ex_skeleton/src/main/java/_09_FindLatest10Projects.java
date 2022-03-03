import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class _09_FindLatest10Projects {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");



        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        List<Project> projectList = entityManager.createQuery("select p from Project p  order by p.endDate asc, p.name asc ", Project.class)
                .setMaxResults(10)
                .getResultList();

        StringBuilder output = new StringBuilder();
        projectList.stream()
                .sorted(Comparator.comparing(Project::getName))
                        .forEach(p -> {
                            output.append(String.format("Project name: %s%n\tProject Description: %s%n\tProject Start date: %s%n\tProject End Date: null%n",
                                    p.getName(),
                                    p.getDescription(),
                                    p.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));
                        });

        System.out.println(output);

        entityManager.getTransaction().commit();
    }
}
