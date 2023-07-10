package com.veeteq.finance.counterparty.fileutils;

public interface FileTemplate {

    String[] getSheetHeaders();
    Object[] getSheetRowData();

}
