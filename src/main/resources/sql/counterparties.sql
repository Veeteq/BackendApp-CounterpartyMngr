insert into counterparties(cprt_id, cprt_name_tx, cprt_shrt_name_tx, addr_stre_tx, addr_city_tx, crea_dt, updt_dt, vers_nm) values(1, 'Apple Inc', 'Apple', '1 Apple Park Way', 'Cupertino', current_timestamp(), current_timestamp(), 0);
insert into counterparties(cprt_id, cprt_name_tx, cprt_shrt_name_tx, addr_stre_tx, addr_city_tx, crea_dt, updt_dt, vers_nm) values(2, 'Google Inc', 'Google', '1600 Amphitheatre Parkway', 'Mountain View', current_timestamp(), current_timestamp(), 0);

insert into counterparty_tags(cprt_id, cprt_ctag_tx) values(1, 'Apple');
insert into counterparty_tags(cprt_id, cprt_ctag_tx) values(1, 'Steve');
insert into counterparty_tags(cprt_id, cprt_ctag_tx) values(1, 'Jobs');

insert into counterparty_tags(cprt_id, cprt_ctag_tx) values(2, 'Google');
insert into counterparty_tags(cprt_id, cprt_ctag_tx) values(2, 'Android');
insert into counterparty_tags(cprt_id, cprt_ctag_tx) values(2, 'gmail');