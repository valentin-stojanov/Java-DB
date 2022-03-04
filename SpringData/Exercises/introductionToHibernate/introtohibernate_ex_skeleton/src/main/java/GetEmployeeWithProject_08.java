import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Comparator;
import java.util.Scanner;

public class GetEmployeeWithProject_08 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager =
                Persistence.createEntityManagerFactory("PU_Name")
                        .createEntityManager();
        int id = Integer.parseInt(scanner.nextLine());

        Employee employee = entityManager.find(Employee.class, id);

        System.out.printf("%s %s - %s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p ->
                        System.out.printf("%s%n", p.getName()));
    }
}
