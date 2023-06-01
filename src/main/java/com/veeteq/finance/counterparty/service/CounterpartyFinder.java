package com.veeteq.finance.counterparty.service;

import java.util.List;
import java.util.Map;

public interface CounterpartyFinder {

  List<Long> getRecords(Map<String, String> searchCriteria);
}
