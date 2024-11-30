package com.mprog.negative;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class NegativeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${negative.sleep}")
    private Integer sleep;

    @Transactional
    public void update(String serviceName) {
        Integer count = (Integer) entityManager.createNativeQuery("select count from request where name = :name")
                .setParameter("name", serviceName)
                .getSingleResult();


        Integer result = (Integer) entityManager.createNativeQuery("select * from sleepAndSelect(:sleep, :count, :name);")
                .setParameter("sleep", sleep)
                .setParameter("count", count + 1)
                .setParameter("name", serviceName)
                .getSingleResult();
    }
}
