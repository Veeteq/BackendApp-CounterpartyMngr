package com.veeteq.finance.counterparty.fileutils;

import com.google.common.base.Function;
import com.veeteq.finance.counterparty.fileutils.export.CounterpartyCsvBean;

public enum ColumnPositionsEnum {

    ID(ColumnPositions.ID_POSITION, "Id", row -> row.getId()),
    FULL_NAME(ColumnPositions.FULL_NAME_POSITION, "Full name", row -> row.getFullName()),
    SHORT_NAME(ColumnPositions.SHORT_NAME_POSITION, "Short name", row -> row.getShortName()),
    NIP(ColumnPositions.TAX_ID_POSITION, "NIP", row -> row.getTaxId()),
    IBAN(ColumnPositions.IBAN_POSITION, "IBAN", row -> row.getIban()),
    STREET(ColumnPositions.STREET_POSITION, "Street ", row -> row.getStreet()),
    POSTAL_CODE(ColumnPositions.POSTAL_CODE_POSITION, "Postal code", row -> row.getPostalCode()),
    CITY(ColumnPositions.CITY_POSITION, "City", row -> row.getCity()),
    COUNTRY(ColumnPositions.COUNTRY_POSITION, "Country", row -> row.getCountry());

    private final int position;
    private final String header;
    private final Function<CounterpartyCsvBean, Object> f;

    private ColumnPositionsEnum(int position, String header, Function<CounterpartyCsvBean, Object> f) {
        this.position = position;
        this.header = header;
        this.f = f;
    }

    public int position() {
        return position;
    }

    public String header() {
        return header;
    }

    public Object value(CounterpartyCsvBean row) {
        return f.apply(row);
    }

}
