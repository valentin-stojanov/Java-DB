import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.Scanner;

public class _08_GetEmployeeWithProjects {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");



        EntityManager entityManager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());

        entityManager.getTransaction().begin();

        Employee employee = entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();


        StringBuilder projects = new StringBuilder(String.format("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getDepartment().getName()));

        employee.getProjects()
                .stream()
                .sorted((p1, p2) -> p1.getName().compareTo(p2.getName()))
                .forEach(p -> projects.append(String.format("\t%s%n", p.getName())));

        System.out.println(projects);

        entityManager.getTransaction().commit();
    }
}
