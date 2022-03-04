import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Scanner;

public class AddingANewAddress_06 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = Persistence
                .createEntityManagerFactory("PU_Name")
                .createEntityManager();

        String lastName = scanner.nextLine();

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e " +
                         "WHERE e.lastName = :l_name", Employee.class)
                         .setParameter("l_name", lastName)
                         .getSingleResult();

        Address address = createAddress("Vitoshka 15",entityManager);

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();

    }

    private static Address createAddress(String text, EntityManager entityManager) {
        Address address = new Address();
        address.setText(text);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;

    }
}
