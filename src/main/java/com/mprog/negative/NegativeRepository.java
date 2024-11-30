package com.mprog.negative;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Repository
public class NegativeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${negative.sleep}")
    private Integer sleep;

    @Transactional
    public void update(String serviceName) {
        Integer count = (Integer) entityManager.createNativeQuery("select count from request where name = :name for update")
                .setParameter("name", serviceName)
                .getSingleResult();


        Integer result = (Integer) entityManager.createNativeQuery("select * from sleepAndSelect(:sleep, :count, :name);")
                .setParameter("sleep", sleep)
                .setParameter("count", count + 1)
                .setParameter("name", serviceName)
                .getSingleResult();
    }


    @Autowired
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;

    @PostConstruct
    private void init() throws UnknownHostException {
        String serviceName = InetAddress.getLocalHost().getHostName();
        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                entityManager.createNativeQuery("insert into request (name, count)\n" +
                                "values (:name, 0)")
                        .setParameter("name", serviceName)
                        .executeUpdate();
            }
        });
    }
}
