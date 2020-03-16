Create database purch_db;
\c purch_db;

Create table customers (
	id int generated always as identity,
	firstName text not null,
	lastName text not null,
	primary key (id)
);

create table merch(
	id int generated always as identity,
	title text not null,
	price float(10) not null,
	primary key (id)
);

create table purch(
	id int generated always as identity,
	customerId int references customers (id) not null,
	merchId int references merch (id) not null,
	timeDate date not null,
	primary key (id)
);

INSERT INTO customers (firstName,lastName) VALUES ('Аркадий', 'Беспалов');
INSERT INTO customers (firstName,lastName) VALUES ('Иван','Иванов');
INSERT INTO customers (firstName,lastName) VALUES ('Олег','Иванов');

INSERT INTO merch (title, price) VALUES ('box',10 );
INSERT INTO merch (title, price) VALUES ('car',7.3 );
INSERT INTO merch (title, price) VALUES ('stuff',11.2);

INSERT INTO purch (customerId, merchId, timeDate) VALUES (1, 1, '2006-12-30');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 1, '2007-12-30');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (3, 1, '2007-01-01');

INSERT INTO purch (customerId, merchId, timeDate) VALUES (1, 2, '2006-12-01');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 2, '2006-12-02');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 2, '2006-12-03');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (3, 2, '2006-12-04');

INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 2, '2006-12-07');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 2, '2006-12-07');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 2, '2006-12-07');

INSERT INTO purch (customerId, merchId, timeDate) VALUES (1, 3, '2006-12-05');

INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 3, '2006-12-06');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 3, '2006-12-07');

INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 3, '2006-12-08');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (2, 3, '2006-12-08');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (3, 3, '2006-12-08');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (3, 3, '2006-12-08');

INSERT INTO purch (customerId, merchId, timeDate) VALUES (3, 3, '2006-12-09');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (3, 3, '2006-12-10');
INSERT INTO purch (customerId, merchId, timeDate) VALUES (3, 3, '2006-12-11');
