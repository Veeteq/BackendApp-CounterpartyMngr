package com.veeteq.finance.counterparty.fileutils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileFormatter {

    public Map<Integer, Object[]> formatData(List<? extends FileTemplate> data) {
        int rowNr = 0;
        Map<Integer, Object[]> formattedData = new LinkedHashMap<>();

        if (data == null || data.size() == 0) {
            return formattedData;
        }

        formattedData.put(rowNr++, data.get(0).getSheetHeaders());

        for (FileTemplate rec : data) {
            formattedData.put(rowNr++, rec.getSheetRowData());
        }

        return formattedData;
    }

}
