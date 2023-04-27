package com.veeteq.finance.counterparty.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
		"spring.profiles.active=default",
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.sql.init.mode=embedded",
        "spring.sql.init.schema-locations=classpath:sql/schema.sql",
        "spring.sql.init.data-locations=classpath:sql/counterparties.sql"
})
class ModelTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void testAllDataLoaded() {
        Long count = entityManager
                .getEntityManager().createQuery("SELECT COUNT(*) FROM Counterparty c", Long.class)
                .getSingleResult();

        assertThat(count).isEqualTo(2);
    }

    @Test
    void testSingleDataLoaded() {
        Counterparty counterparty = entityManager.find(Counterparty.class, 1L);
        assertNotNull(counterparty);
    }
    
    @Test
    void testSaveExistingCounterparty_ThrowsException() {
        Counterparty cprt = new Counterparty().setId(1L).setFullName("Sample Counterparty");
        
        EntityTransaction transaction = entityManager.getEntityManager().getTransaction();
        entityManager.persist(cprt);
        RollbackException exception = assertThrows(RollbackException.class, () -> transaction.commit());
        assertNotNull(exception);
    }

    @Test
    void testSaveNewCounterparty() {
        String SAMPLE_COUNTERPARTY = "Sample Counterparty";
        
        Counterparty cprt = new Counterparty().setId(3L).setFullName(SAMPLE_COUNTERPARTY);
        
        EntityTransaction transaction = entityManager.getEntityManager().getTransaction();
        Counterparty saved = entityManager.persist(cprt);
        transaction.commit();
        
        assertThat(saved.getId()).isEqualTo(3);
        assertThat(saved.getFullName()).isEqualTo(SAMPLE_COUNTERPARTY);        
        
    }
}
