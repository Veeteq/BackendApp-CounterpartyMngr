package com.veeteq.finance.counterparty.service.jpa;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veeteq.finance.counterparty.model.Counterparty;
import com.veeteq.finance.counterparty.service.CounterpartyFinder;

/**
 * https://betterprogramming.pub/how-to-create-dynamic-queries-in-spring-data-355ff69e81d0
 * https://en.wikibooks.org/wiki/Java_Persistence/Criteria
 * https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-part-four-jpa-criteria-queries/
 * https://persistence.blazebit.com/documentation/1.4/core/manual/en_US/index.html
 * https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/criteria-api-tuple.html
 */
@Service
public class BasicCounterpartyFinder implements CounterpartyFinder {

  private final EntityManager entityManager;

  @Autowired
  public BasicCounterpartyFinder(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Long> getRecords(Map<String, String> searchCriteria) {
    if (searchCriteria == null || searchCriteria.isEmpty()) {
      return Collections.<Long>emptyList();
    }

    String tag = searchCriteria.get("title");
    String bankAccountNumber = searchCriteria.get("accountNumber");
    String counterpartyName = searchCriteria.get("counterpartyName");
    String counterpartyAddress = searchCriteria.get("counterpartyAddress");

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Counterparty> criteriaQuery = criteriaBuilder.createQuery(Counterparty.class);
    Root<Counterparty> counterparty = criteriaQuery.from(Counterparty.class);
    Join<Counterparty, String> tags = counterparty.join("tags", JoinType.LEFT);

    Predicate where = criteriaBuilder.conjunction();
    if (bankAccountNumber != null) {
      where = criteriaBuilder.and(where, criteriaBuilder.equal(counterparty.get("bankAccountNumber"), bankAccountNumber));
    }
    if (counterpartyName != null) {
      where = criteriaBuilder.and(where, criteriaBuilder.like(criteriaBuilder.lower(counterparty.<String>get("shortName")), "%" + counterpartyName.toLowerCase() + "%"));
    }
    if (counterpartyAddress != null) {
      where = criteriaBuilder.and(where, criteriaBuilder.like(criteriaBuilder.lower(counterparty.<String>get("address").get("street")), "%" + counterpartyAddress.toLowerCase() + "%"));
    }
    if (tag != null) {
      where = criteriaBuilder.and(where, criteriaBuilder.equal(tags, tag));
    }
    criteriaQuery.where(where);

    criteriaQuery.select(counterparty.get("id")).distinct(true);
    Query query = entityManager.createQuery(criteriaQuery);

    @SuppressWarnings("unchecked")
	List<Long> resultList = query.getResultList();
    entityManager.close();

    return resultList;
  }
}