import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Comparator;

public class FindLatest10Projects_09 {
    public static void main(String[] args) {

        EntityManager entityManager =
                Persistence.createEntityManagerFactory("PU_Name")
                        .createEntityManager();

        entityManager.createQuery("SELECT p FROM Project p " +
                "ORDER BY p.startDate DESC ", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p ->
                        System.out.printf("Project name: %s%n" +
                                "       Project Description: %s%n" +
                                "       Project Start Date: %s%n" +
                                "       Project End Date: %s%n",
                                p.getName(),
                                p.getDescription(),
                                p.getStartDate(),
                                p.getEndDate() == null ? "null" : p.getEndDate()));
    }
}
