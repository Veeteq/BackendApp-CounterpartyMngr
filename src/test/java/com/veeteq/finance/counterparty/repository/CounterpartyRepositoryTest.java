package com.veeteq.finance.counterparty.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.common.collect.Sets;
import com.veeteq.finance.counterparty.model.Counterparty;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.profiles.active=default",

        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",

        "spring.sql.init.mode=embedded",
        "spring.sql.init.schema-locations=classpath:sql/schema.sql",
        "spring.sql.init.data-locations=classpath:sql/counterparties.sql"
})
class CounterpartyRepositoryTest {

    @Autowired
    private CounterpartyRepository repository;

    @Test
    void testSaveNewCounterparty() {
        String fullName = "Korben Dallas";
        Counterparty counterparty = createCounterparty(fullName);
        String taxId = counterparty.getTaxId();
        String iban = counterparty.getIban();

        Counterparty saved = repository.save(counterparty);

        assertEquals(3, saved.getId());
        assertEquals(fullName, saved.getFullName());
        assertEquals(taxId, saved.getTaxId());
        assertEquals(iban, saved.getIban());
    }

    @Test
    void testUpdateExistingCounterparty() {
        Long idToUpdate = 1L;
        String fullName = "Korben Dallas";
        Counterparty counterparty = createCounterparty(fullName);
        String nip = counterparty.getTaxId();
        String iban = counterparty.getIban();

        Counterparty reference = repository.getReferenceById(idToUpdate);

        Counterparty saved = null;
        if(reference != null) {
            Counterparty toUpdate = reference.updateWith(counterparty);
            saved = repository.save(toUpdate);
        } else {
            saved = repository.save(counterparty);
        }

        assertEquals(idToUpdate, saved.getId());
        assertEquals(fullName, saved.getFullName());
        assertEquals(nip, saved.getTaxId());
        assertEquals(iban, saved.getIban());
    }

    @Test
    void testSaveCounterpartyWithTags() {
        String fullName = "Lilu Dallas";
        Counterparty counterparty = createCounterparty(fullName);
        String nip = counterparty.getTaxId();
        String iban = counterparty.getIban();

        Counterparty saved = repository.save(counterparty);

        assertEquals(3, saved.getId());
        assertEquals(fullName, saved.getFullName());
        assertEquals(nip, saved.getTaxId());
        assertEquals(iban, saved.getIban());
        assertEquals(4, saved.getTags().size());
    }

    @Test
    void testFindCounterpartyByTag() {
        String fullName = "Lilu Dallas";
        Counterparty counterparty = createCounterparty(fullName);

        Counterparty saved = repository.save(counterparty);
        assertEquals(3, saved.getId());

        List<Counterparty> results = repository.findByTag("5th Element");

        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testFindCounterpartyByIban() {
        String fullName = "Lilu Dallas";
        Counterparty counterparty = createCounterparty(fullName);
        String iban = counterparty.getIban();

        Counterparty saved = repository.save(counterparty);
        assertEquals(3, saved.getId());

        List<Counterparty> results = repository.findByIban(iban);

        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testFindCounterpartyByFullNameContainingIgnoreCase() {
        Counterparty counterparty1 = createCounterparty(11L, "Lilu Dallas");
        Counterparty counterparty2 = createCounterparty(12L, "Korben Dallas");

        List<Counterparty> saved = repository.saveAll(List.of(counterparty1, counterparty2));

        assertEquals(2, saved.size());

        List<Counterparty> results = repository.findByFullNameContainingIgnoreCase("dalla");

        assertNotNull(results);
        assertEquals(2, results.size());
    }

    private Counterparty createCounterparty(String fullName) {
        final long count = repository.count();
        return createCounterparty(count + 1, fullName);
    }

    private Counterparty createCounterparty(Long id, String fullName) {
        String[] uuid = UUID.randomUUID().toString().split("-");
        String nip = uuid[0];
        String iban = uuid[4];

        Counterparty counterparty = new Counterparty()
                .setId(id)
                .setFullName(fullName)
                .setShortName(fullName.split(" ")[0])
                .setTaxId(nip)
                .setIban(iban)
                .setTags(Sets.newHashSet("Korben", "Lilu", "Dallas", "5th Element"));
        return counterparty;
    }
}
