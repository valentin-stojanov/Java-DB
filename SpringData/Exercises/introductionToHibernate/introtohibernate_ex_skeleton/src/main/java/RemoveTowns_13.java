import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class RemoveTowns_13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        EntityManager entityManager =
                Persistence.createEntityManagerFactory("PU_Name")
                        .createEntityManager();
        
        String townInput = scanner.nextLine();

        Town town = entityManager.createQuery("SELECT t FROM Town t " +
                        "WHERE  t.name = :t_name", Town.class)
                .setParameter("t_name", townInput)
                .getSingleResult();

        int affected = getDeleted(entityManager, town);


        entityManager.getTransaction().begin();

        entityManager.remove(town);

        entityManager.getTransaction().commit();

        System.out.printf("%d address in %s deleted",
                affected,
                town);

    }

    private static int getDeleted(EntityManager entityManager, Object town) {

        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
                        "WHERE a.town = :t_name", Address.class)
                .setParameter("t_name", town)
                .getResultList();

        entityManager.getTransaction().begin();
        addresses.forEach(entityManager::remove);
        entityManager.getTransaction().commit();
        return addresses.size();
    }
}
