package GringotsDB;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class _01_Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("Gringots").createEntityManager();

    }
}
