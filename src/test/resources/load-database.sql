INSERT INTO person (name, cpf) VALUES ('Iago', '86730543540');
INSERT INTO person (name, cpf) VALUES ('Pedro', '55565893569');
INSERT INTO person (name, cpf) VALUES ('Cauê', '38767897100');
INSERT INTO person (name, cpf) VALUES ('Breno', '78673781620');
INSERT INTO person (name, cpf) VALUES ('Thiago', '72788740417');

INSERT INTO phone (ddd, number_phone, id_person) VALUES ('41', '999570146', (SELECT id FROM person WHERE cpf = '86730543540'));
INSERT INTO phone (ddd, number_phone, id_person) VALUES ('82', '39945903', (SELECT id FROM person WHERE cpf = '55565893569'));
INSERT INTO phone (ddd, number_phone, id_person) VALUES ('86', '35006330', (SELECT id FROM person WHERE cpf = '38767897100'));
INSERT INTO phone (ddd, number_phone, id_person) VALUES ('21', '997538804', (SELECT id FROM person WHERE cpf = '78673781620'));
INSERT INTO phone (ddd, number_phone, id_person) VALUES ('95', '38416516', (SELECT id FROM person WHERE cpf = '72788740417'));

INSERT INTO address (street, number_address, complement, neighborhood, city, state, id_person) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM person WHERE cpf = '86730543540'));
INSERT INTO address (street, number_address, complement, neighborhood, city, state, id_person) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM person WHERE cpf = '55565893569'));
INSERT INTO address (street, number_address, complement, neighborhood, city, state, id_person) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM person WHERE cpf = '38767897100'));
INSERT INTO address (street, number_address, complement, neighborhood, city, state, id_person) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM person WHERE cpf = '78673781620'));
INSERT INTO address (street, number_address, complement, neighborhood, city, state, id_person) VALUES ('Rua dos Gerânios', 497, 'XXXX', 'Pricumã', 'Boa Vista', 'RR', (SELECT id FROM person WHERE cpf = '72788740417'));