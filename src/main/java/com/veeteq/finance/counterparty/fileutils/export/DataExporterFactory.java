package com.veeteq.finance.counterparty.fileutils.export;

public class DataExporterFactory {

    public static DataExporter getExporter() {
        return new CsvDataExporter();
    }


}
