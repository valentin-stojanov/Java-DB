
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class _11_FindEmployeeByFirstName {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU_Name");

        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();
        String str = scanner.nextLine();

        List<Employee> employeeList = entityManager.createQuery("select e from Employee e where e.firstName like :str", Employee.class)
                .setParameter("str", str + "%")
                .getResultList();

        StringBuilder output = new StringBuilder();

        for (Employee employee : employeeList) {
            output.append(String.format("%s %s - %s - ($%.2f)%n", employee.getFirstName(), employee.getLastName(), employee.getDepartment().getName(), employee.getSalary()));
        }

        entityManager.getTransaction().commit();
        System.out.println(output);
    }
}
