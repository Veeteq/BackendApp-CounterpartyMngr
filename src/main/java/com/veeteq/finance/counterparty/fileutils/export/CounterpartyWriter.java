package com.veeteq.finance.counterparty.fileutils.export;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

import com.opencsv.CSVWriter;
import com.veeteq.finance.counterparty.fileutils.FileFormatter;
import com.veeteq.finance.counterparty.fileutils.FileTemplate;

public class CounterpartyWriter implements CsvWriter {

    @Override
    public void writeToFile(List<? extends FileTemplate> data, String fileName) {

        try (Writer writer = new FileWriter(fileName, Charset.defaultCharset());
             CSVWriter csvWriter = new CSVWriter(writer, ';', Character.MIN_VALUE, '\\', "\n")) {

            FileFormatter fileFormatter = new FileFormatter();
            fileFormatter.formatData(data).forEach((k, v) -> {
                csvWriter.writeNext(mapToStringArray(v));
            });
            csvWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] mapToStringArray(Object[] source) {
        String[] result = new String[source.length];

        int i = 0;
        for (Object obj : source) {
            result[i++] = String.valueOf(obj);
        }

        return result;
    }

}
