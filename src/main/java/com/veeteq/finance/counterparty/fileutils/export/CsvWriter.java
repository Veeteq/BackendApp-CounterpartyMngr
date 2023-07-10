package com.veeteq.finance.counterparty.fileutils.export;

import java.util.List;

import com.veeteq.finance.counterparty.fileutils.FileTemplate;

public interface CsvWriter {

    void writeToFile(List<? extends FileTemplate> data, String fileName);

}