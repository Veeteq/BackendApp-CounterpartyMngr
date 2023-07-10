package com.veeteq.finance.counterparty.service;

import com.veeteq.finance.counterparty.model.Counterparty;

import java.util.List;
import java.util.Map;

public interface CounterpartyFinder {

  List<Long> getRecords(Map<String, String> searchCriteria);

  List<Counterparty> searchByCriteria(Map<String, String> searchCriteria);
}
