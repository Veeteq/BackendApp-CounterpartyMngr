package com.veeteq.finance.counterparty.fileutils.export;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.opencsv.bean.CsvBindByPosition;
import com.veeteq.finance.counterparty.fileutils.ColumnPositions;
import com.veeteq.finance.counterparty.fileutils.ColumnPositionsEnum;
import com.veeteq.finance.counterparty.fileutils.FileTemplate;

public class CounterpartyCsvBean extends BaseCsvBean implements FileTemplate {

  @CsvBindByPosition(position = ColumnPositions.ID_POSITION)
  private Long id;

  @CsvBindByPosition(position = ColumnPositions.FULL_NAME_POSITION)
  private String fullName;

  @CsvBindByPosition(position = ColumnPositions.SHORT_NAME_POSITION)
  private String shortName;

  @CsvBindByPosition(position = ColumnPositions.TAX_ID_POSITION)
  private String taxId;

  @CsvBindByPosition(position = ColumnPositions.IBAN_POSITION)
  private String iban;

  @CsvBindByPosition(position = ColumnPositions.STREET_POSITION)
  private String street;

  @CsvBindByPosition(position = ColumnPositions.POSTAL_CODE_POSITION)
  private String postalCode;

  @CsvBindByPosition(position = ColumnPositions.CITY_POSITION)
  private String city;

  @CsvBindByPosition(position = ColumnPositions.COUNTRY_POSITION)
  private String country;

  public Long getId() {
      return id;
  }

  public CounterpartyCsvBean setId(Long id) {
      this.id = id;
      return this;
  }

  public String getFullName() {
      return fullName;
  }

  public CounterpartyCsvBean setFullName(String fullName) {
      this.fullName = fullName;
      return this;
  }

  public String getShortName() {
      return shortName;
  }

  public CounterpartyCsvBean setShortName(String shortName) {
      this.shortName = shortName;
      return this;
  }

  public String getTaxId() {
      return taxId;
  }

  public CounterpartyCsvBean setTaxId(String taxId) {
    this.taxId = taxId;
    return this;
  }

  public String getIban() {
    return iban;
  }

  public CounterpartyCsvBean setIban(String iban) {
    this.iban = iban;
    return this;
  }

  public String getStreet() {
    return street;
  }

  public CounterpartyCsvBean setStreet(String street) {
    this.street = street;
    return this;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public CounterpartyCsvBean setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public String getCity() {
    return city;
  }

  public CounterpartyCsvBean setCity(String city) {
    this.city = city;
    return this;
  }

  public String getCountry() {
    return country;
  }

  public CounterpartyCsvBean setCountry(String country) {
    this.country = country;
    return this;
  }

  @Override
  public String[] getSheetHeaders() {
    List<String> headers = new ArrayList<>();
    headers.add(ColumnPositionsEnum.ID.position(), ColumnPositionsEnum.ID.header());
    return headers.toArray(new String[0]);
  }

  @Override
  public Object[] getSheetRowData() {
    List<Object> data = new ArrayList<>();
    Stream.of(ColumnPositionsEnum.values()).forEach(el -> {
      data.add(el.value(this));
    });
    return data.toArray();
  }

}
