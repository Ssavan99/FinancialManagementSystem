-- Name: Savan Patel
-- CSCE 156H
-- Date: March 13, 2020
-- This SQL sheet creates the table and connection 
-- and inserts data into it

 -- use savansurep;

-- dropping table if already exists
drop table if exists PortfolioPerson;
drop table if exists PortfolioAsset;
drop table if exists Email;
drop table if exists Portfolio;
drop table if exists Asset;
drop table if exists Person;
drop table if exists Address;


create table Address (
	addressId int not null primary key auto_increment,
    street varchar(100) not null,
    city varchar(100) not null,
    state varchar(100) not null,
    zip varchar(50) not null,
    country varchar(100) not null
    );
    
create table Person (
	personId int not null primary key auto_increment,
    personCode varchar(100) unique key not null,
    brokerType varchar(50) check (brokerType = "E" or brokerType = "J"),
    secId varchar(50),
    firstName varchar(100),
    lastname varchar(100),
    addressId int not null,
    foreign key (addressId) references Address(addressId)
    );
    
create table Email (
	emailId int not null primary key auto_increment,
    personId int not null,
    emailAddress varchar(150),
    foreign key (personId) references Person(personId)
    );
    
create table Asset (
	assetId int not null primary key auto_increment,
    assetCode varchar(100) unique key not null,
    assetType varchar(50) check (assetType = "D" or assetType = "S" or assetType = "P"),
    label varchar(100) not null,
    apr double,
    quarterlyDividend double,
    baseRateOfReturn double,
    betaMeasure double,
    stockSymbol varchar(50),
    sharePrice double,
    baseOmegaMeasure double,
    totalValue double
    );
    
create table Portfolio (
	portfolioId int not null primary key auto_increment,
    portfolioCode varchar(100) unique key not null,
    ownerCode varchar(100) not null,
    managerCode varchar(100) not null,
    beneficiaryCode varchar(100)
    );
    
create table PortfolioPerson (
	portfolioPersonId int not null primary key auto_increment,
    personId int not null,
    portfolioId int not null,
    foreign key (personId) references Person(personId),
    foreign key (portfolioId) references Portfolio(portfolioId),
    -- prevent duplicate entries
    constraint uniquePortfolio unique index (personId, portfolioId)
    );
    
create table PortfolioAsset (
	portfolioAssetId int not null primary key auto_increment,
    assetId int not null,
    portfolioId int not null,
    assetValue double,
    foreign key (assetId) references Asset(assetId),
    foreign key (portfolioId) references Portfolio(portfolioId),
    -- prevent duplicate entries
    constraint uniqueList unique index (assetid, portfolioId)
    );
    
-- Deposit Asset
insert into Asset (assetCode, assetType, label, apr) values 
	("LU02704JUO6JDTTL4CI5", "D", "Ailane", 10.2),
    ("FO5628342216244629", "D", "Trilia", 10.99);

-- Stock Asset    
insert into Asset (assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice) values
	("CH3820107GAZLFHXL2EEX", "S", "Thoughtmix", 2.92, 7.56, 0.83, "THM", 209.19),
    ("NO5050466383237", "S", "Quimm", 1.98, 9.41, -0.88, "QM", 811.41);

-- Private Investment Asset    
insert into Asset (assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue) values
	("MD211W0O6HHJIO9ONDOCYYBQ", "P", "Tambee", 1112.22, 7.39, 0.91, 1918103.0);
    

insert into Address (street, city, state, zip, country) values
	("63 Iowa", "Harrisburg", "Pennsylvania", "17110", "United States"),
    ("7 Anzinger", "Washington", "District of Columbia", "20260", "United States"),
    ("1502 Troy", "Lynchburg", "Virginia", "24515", "United States"),
    ("75499 Debs", "Portland", "Oregon", "97211", "United States"),
    ("38959 Roxbury", "Memphis", "Tennessee", "38161", "United States"),
    ("2243 East P Street", "Lincoln", "Nebraska", "68515", "United States");
    
insert into Person (personCode, brokerType, secId, firstName, lastName, addressId) values
	("FI4897536774356076", null, null, "Joy", "Iffland", (select addressId from Address where street = "63 Iowa" and zip = 17110)),
    ("CR6410563482271981544", "J", "sec990", "Jillie", "Beeckx", (select addressId from Address where street = "7 Anzinger" and zip = 20260)),
    ("NL16FPFN4699904093", null, null, "Glennis", "Elia", (select addressId from Address where street = "1502 Troy" and zip = 24515)),
    ("ME85356936018586154601", null, null, "Sunny", "Hume", (select addressId from Address where street = "75499 Debs" and zip = 97211)),
    ("FR526188570377WH5LD6ZF6NE22", "E", "sec908", "Nan", "Bellows", (select addressId from Address where street = "38959 Roxbury" and zip = 38161)),
    ("ME853522222020444214601", null, null, "Chrik", "Balden", (select addressId from Address where street = "2243 East P Street" and zip = 68515));

insert into Email (personId, emailAddress) values
	((select personId from Person where personCode = "FI4897536774356076"), "jiffland0@java.com"),
    ((select personId from Person where personCode = "CR6410563482271981544"), "jbeeckx1@army.mil"),
    ((select personId from Person where personCode = "NL16FPFN4699904093"), "gelia3@rambler.ru"),
    ((select personId from Person where personCode = "ME85356936018586154601"), "shume7@china.com.cn"),
    ((select personId from Person where personCode = "FR526188570377WH5LD6ZF6NE22"), "nbellows8@devhub.com"),
    ((select personId from Person where personCode = "FR526188570377WH5LD6ZF6NE22"), "mspeedin9@digg.com");
    
    
insert into Portfolio (portfolioCode, ownerCode, managerCode, beneficiaryCode) values
	("PDA001", (select personCode from Person where firstName = "Glennis" and lastName = "Elia"), (select personCode from Person where firstName = "Nan" and lastName = "Bellows"), (select personCode from Person where firstName = "Jillie" and lastName = "Beeckx")),
    ("PDB002", (select personCode from Person where firstName = "Sunny" and lastName = "Hume"), (select personCode from Person where firstName = "Jillie" and lastName = "Beeckx"), null),
    ("PSA001", (select personCode from Person where firstName = "Joy" and lastName = "Iffland"), (select personCode from Person where firstName = "Jillie" and lastName = "Beeckx"), (select personCode from Person where firstName = "Sunny" and lastName = "Hume")),
    ("PIC022", (select personCode from Person where firstName = "Chrik" and lastName = "Balden"), (select personCode from Person where firstName = "Nan" and lastName = "Bellows"), (select personCode from Person where firstName = "Glennis" and lastName = "Elia"));
    
insert into PortfolioPerson (personId, portfolioId) values
	((select personId from Person where personCode = "NL16FPFN4699904093"), (select portfolioId from Portfolio where portfolioCode = "PDA001")),
    ((select personId from Person where personCode = "ME85356936018586154601"), (select portfolioId from Portfolio where portfolioCode = "PDB002")),
    ((select personId from Person where personCode = "FI4897536774356076"), (select portfolioId from Portfolio where portfolioCode = "PSA001")),
    ((select personId from Person where personCode = "ME853522222020444214601"), (select portfolioId from Portfolio where portfolioCode = "PIC022"));


-- Deposit Asset    
insert into PortfolioAsset (assetId, portfolioId, assetValue) values
	((select assetId from Asset where assetCode = "FO5628342216244629"), (select portfolioId from Portfolio where portfolioCode = "PDA001"), 12346.9),
	((select assetId from Asset where assetCode = "FO5628342216244629"), (select portfolioId from Portfolio where portfolioCode = "PDB002"), 34009.7),
    ((select assetId from Asset where assetCode = "LU02704JUO6JDTTL4CI5"), (select portfolioId from Portfolio where portfolioCode = "PSA001"), 1982857.2),
    ((select assetId from Asset where assetCode = "LU02704JUO6JDTTL4CI5"), (select portfolioId from Portfolio where portfolioCode = "PIC022"), 999999);

-- Stock Asset    
insert into PortfolioAsset (assetId, portfolioId, assetValue) values
	((select assetId from Asset where assetCode = "NO5050466383237"), (select portfolioId from Portfolio where portfolioCode = "PSA001"), 399),
    ((select assetId from Asset where assetCode = "CH3820107GAZLFHXL2EEX"), (select portfolioId from Portfolio where portfolioCode = "PIC022"), 484);

-- Private Investment Asset    
insert into PortfolioAsset (assetId, portfolioId, assetValue) values
	((select assetId from Asset where assetCode = "MD211W0O6HHJIO9ONDOCYYBQ"), (select portfolioId from Portfolio where portfolioCode = "PIC022"), 75);