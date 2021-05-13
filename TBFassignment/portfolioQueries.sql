 -- Name: Savan Patel
-- CSCE 156H
-- Date: March 13, 2020
-- This SQL sheet performs different queries
 
 -- use savansurep;

-- Answer 1
select p.personId, p.personCode, p.brokerType, p.firstName, p.lastname, 
	a.street, a.city, a.state, a.zip, a.country from Person p 
    join Address a on p.addressId = a.addressId;
    
-- Answer 2
select p.personId, p.personCode, p.firstName, p.lastName, e.emailAddress from Email e left join Person p on p.personId = e.personId where p.personId = 5;

-- Answer 3
insert into Email(personId, emailAddress) values
		((select personId from Person where personCode = "FI4897536774356076"), "jiff123@gmail.com");
    
-- Answer 4
update Email set emailAddress = "sunnyh10@outlook.com" where emailId = 4;

-- Answer 5
-- Person " Joy Iffland has been deleted and also deletes the portfolio owned by him"
delete from PortfolioAsset where portfolioId = 3;
delete from PortfolioPerson where personId = 1; 
delete from Portfolio where portfolioId = 3;
delete from Email where personId = 1;
delete from Person where personId = 1;

-- Answer 6
-- Adds the person Record of Joy Iffland (FI4897536774356076;;Iffland,Joy;63 Iowa,Harrisburg,Pennsylvania,17110,United States)
insert into Person (personCode, brokerType, firstName, lastName, addressId) values
	("DG4O975677367746076", null, "Roy", "Puffland", (select addressId from Address where street = "63 Iowa" and zip = 17110));

-- Answer 7
select a.assetCode from Asset a 
	left join PortfolioAsset pa on a.assetId = pa.assetId where portfolioId = 4;
    
-- Answer 8
-- joins the Asset, PortfolioAsset, Portfolio, PortfolioPerson and Person table
select ps.personCode, p.portfolioCode, a.assetID, a.assetCode, a.assetType from Person ps 
	join PortfolioPerson pp on ps.personId = pp.personId
    join Portfolio p on p.portfolioId = pp.portfolioId
    join PortfolioAsset pa on p.portfolioId = pa.portfolioId 
    join Asset a on pa.assetId = a.assetId    
    where ps.personCode = "ME853522222020444214601";

-- Answer 9
-- inserts a new private investment asset
insert into Asset (assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue) values
	("NR295X0P6HJIO9ON5OCYYBQ", "P", "Boring", 1500.5, 9.39, 0.75, 2510100.0);
    
-- Answer 10
insert into Portfolio (portfolioCode, ownerCode, managerCode, beneficiaryCode) values
	("PCB003", (select personCode from Person where firstName = "Glennis" and lastName = "Elia"), (select personCode from Person where firstName = "Jillie" and lastName = "Beeckx"), (select personCode from Person where firstName = "Sunny" and lastName = "Hume"));
    
 -- Answer 11
 -- Associates Asset "Boring" to Portfolio "PCB003"
 insert into PortfolioAsset (assetId, portfolioId, stakePercentage) values
	((select assetId from Asset where assetCode = "NR295X0P6HJIO9ON5OCYYBQ"), (select portfolioId from Portfolio where portfolioCode = "PCB003"), 101);

-- Answer 12
select count(p.portfolioId), ps.personCode, ps.personId, ps.firstName, ps.lastName from Portfolio p join Person ps on p.ownerCode = ps.personCode group by ps.personCode;

-- Answer 13
select count(p.portfolioId), ps.personCode, ps.personId, ps.firstName, ps.lastName from Portfolio p join Person ps on p.managerCode = ps.personCode group by ps.personCode;

-- Answer 14
-- gives total value of stocks in a portfolio
select p.portfolioId, p.portfolioCode, round(sum(pa.sharesOwned * a.sharePrice), 2) from Portfolio p
	join PortfolioAsset pa on p.portfolioId = pa.portfolioId
    join Asset a on pa.assetId = a.assetId where a.assetType = "S" group by p.portfolioId;

-- Answer 15
select a.assetCode, sum(pa.stakePercentage) from PortfolioAsset pa 
	join Asset a on pa.assetId = a.assetId
    where a.assetType = "P"
    group by a.assetId
    having sum(pa.stakePercentage) > 100;