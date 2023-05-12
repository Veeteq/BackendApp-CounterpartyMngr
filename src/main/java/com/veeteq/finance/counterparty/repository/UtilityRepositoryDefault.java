package com.veeteq.finance.counterparty.repository;

import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile(value = {"default", "dev"})
public class UtilityRepositoryDefault implements UtilityRepository {
    private final int bound = 15;
    
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UtilityRepositoryDefault(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public Long getCounterpartyId() {
        Random rnd = new Random();
        long nextLong = 0;
        long count = 0;
        
        TypedQuery<Long> query = entityManager.createQuery("SELECT Count(c) FROM Counterparty c WHERE c.id = :id", Long.class);               
        do {
            nextLong = rnd.nextInt(bound);
            count = query.setParameter("id", nextLong).getSingleResult();            
        } while(count > 0);
        
        return nextLong;
    }

}
