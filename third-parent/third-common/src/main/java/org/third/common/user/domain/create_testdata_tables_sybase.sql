--------------------------------------
IF EXISTS (SELECT 1 FROM sysobjects WHERE type='U' AND name='td_address' )
   DROP TABLE td_address
go
CREATE TABLE td_address (  
  id int IDENTITY NOT NULL ,
  city varchar(45) DEFAULT 'Shanghai' NULL,
  country varchar(45) DEFAULT 'China' NULL,
  PRIMARY KEY (id)  
)
go
INSERT INTO td_address(city,country) VALUES('China','Shanghai')
go
INSERT INTO td_address(city,country) VALUES('China','LanZhou')
go
IF EXISTS (SELECT 1 FROM sysobjects WHERE type='U' AND name='td_user' )
   DROP TABLE td_user
go
 CREATE TABLE td_user (  
  id int IDENTITY NOT NULL ,
  name varchar(45) NOT NULL,
  password varchar(45) NOT NULL,    
  birthday datetime DEFAULT getdate(),  
  graduation datetime DEFAULT getdate(),  
  sex tinyint DEFAULT NULL,  
  weight float DEFAULT 55.4,  
  height float DEFAULT 169.10,  
  address_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY(address_id) REFERENCES td_address(id)
)
go
INSERT INTO td_user(name,password,birthday, graduation,sex,weight,height,address_id)
  VALUES('test01', 'password01', '2013-12-11 10:34:55', getdate(),1, 12.44, 13.44, 1)
go
INSERT INTO td_user(name,password,birthday,graduation,sex,weight,height,address_id)
  VALUES('test02', 'password02', getdate(), getdate(),1, 12.44, 13.44, 1)
go
INSERT INTO td_user(name,password,birthday,graduation,sex,weight,height,address_id)
  VALUES('test03', 'password03', getdate(), getdate(),1, 12.44, 13.44, 2)
go 
---------------------------------------
/*
drop index sp_productCat;
drop index sp_productName;
drop index sp_itemProd;

drop table sp_bannerdata;
drop table sp_profile;
drop table sp_signon;
drop table sp_inventory;
drop table sp_item;
drop table sp_product;
drop table sp_account;
drop table sp_category;
drop table sp_supplier;
drop table sp_sequence;

create table sp_supplier (
    suppid int not null,
    name varchar(80) null,
    status varchar(2) not null,
    addr1 varchar(80) null,
    addr2 varchar(80) null,
    city varchar(80) null,
    state varchar(80) null,
    zip varchar(5) null,
    phone varchar(80) null,
    constraint sp_pk_supplier primary key (suppid)
);

grant all on sp_supplier to public;

create table sp_signon (
    username varchar(25) not null,
    password varchar(25)  not null,
    constraint sp_pk_signon primary key (username)
);

grant all on sp_signon to public;

create table sp_account (
    userid varchar(80) not null,
    email varchar(80) not null,
    firstname varchar(80) not null,
    lastname varchar(80) not null,
    status varchar(2)  null,
    addr1 varchar(80) not null,
    addr2 varchar(40) null,
    city varchar(80) not  null,
    state varchar(80) not null,
    zip varchar(20) not null,
    country varchar(20) not null,
    phone varchar(80) not null,
    constraint sp_pk_account primary key (userid)
);

grant all on sp_account to public;

create table sp_profile (
    userid varchar(80) not null,
    langpref varchar(80) not null,
    favcategory varchar(30),
    mylistopt int,
    banneropt int,
    constraint sp_pk_profile primary key (userid)
);

grant all on sp_profile to public;


create table sp_bannerdata (
    favcategory varchar(80) not null,
    bannername varchar(255)  null,
    constraint sp_pk_bannerdata primary key (favcategory)
);

grant all on sp_bannerdata to public;

create table sp_category (
  catid varchar(10) not null,
  name varchar(80) null,
  descn varchar(255) null,
  constraint sp_pk_category primary key (catid)
);

grant all on sp_category to public;

create table sp_product (
    productid varchar(10) not null,
    category varchar(10) not null,
    name varchar(80) null,
    descn varchar(255) null,
    constraint sp_pk_product primary key (productid),
        constraint sp_fk_product_1 foreign key (category)
        references sp_category (catid)
);

grant all on sp_product to public;
create index sp_productCat on sp_product (category);
create index sp_productName on sp_product (name);

create table sp_item (
    itemid varchar(10) not null,
    productid varchar(10) not null,
    listprice decimal(10,2) null,
    unitcost decimal(10,2) null,
    supplier int null,
    status varchar(2) null,
    attr1 varchar(80) null,
    attr2 varchar(80) null,
    attr3 varchar(80) null,
    attr4 varchar(80) null,
    attr5 varchar(80) null,
    constraint sp_pk_item primary key (itemid),
        constraint sp_fk_item_1 foreign key (productid)
        references sp_product (productid),
        constraint sp_fk_item_2 foreign key (supplier)
        references sp_supplier (suppid)
);

grant all on sp_item to public;
create index sp_itemProd on sp_item (productid);

create table sp_inventory (
    itemid varchar(10) not null,
    qty int not null,
    constraint sp_pk_inventory primary key (itemid)
);

grant all on sp_inventory to public;

create table sp_sequence (
    name varchar(30) not null,
    nextid int not null,
    constraint sp_pk_sequence primary key (name)
);

grant all on sp_sequence to public;


---------------------------------------------------------------
-----------             Init data                    ----------
---------------------------------------------------------------
INSERT INTO sp_sequence VALUES ('ordernum', 1000);
INSERT INTO sp_sequence VALUES ('linenum', 1000);

INSERT INTO sp_signon VALUES ('j2ee','j2ee');
INSERT INTO sp_signon VALUES ('ACID','ACID');

INSERT INTO sp_account VALUES ('j2ee','yourname@yourdomain.com','ABC', 'XYX', 'OK', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA',  '555-555-5555');
INSERT INTO sp_account VALUES ('ACID','acid@yourdomain.com','ABC', 'XYX', 'OK', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA',  '555-555-5555');

INSERT INTO sp_profile VALUES ('j2ee','english','DOGS','1','1');
INSERT INTO sp_profile VALUES ('ACID','english','CATS','1','1');

INSERT INTO sp_bannerdata VALUES ('FISH','<image src="../images/banner_fish.gif">');
INSERT INTO sp_bannerdata VALUES ('CATS','<image src="../images/banner_cats.gif">');
INSERT INTO sp_bannerdata VALUES ('DOGS','<image src="../images/banner_dogs.gif">');
INSERT INTO sp_bannerdata VALUES ('REPTILES','<image src="../images/banner_reptiles.gif">');
INSERT INTO sp_bannerdata VALUES ('BIRDS','<image src="../images/banner_birds.gif">');

INSERT INTO sp_category VALUES ('FISH','Fish','<image src="../images/fish_icon.gif"><font size="5" color="blue"> Fish</font>');
INSERT INTO sp_category VALUES ('DOGS','Dogs','<image src="../images/dogs_icon.gif"><font size="5" color="blue"> Dogs</font>');
INSERT INTO sp_category VALUES ('REPTILES','Reptiles','<image src="../images/reptiles_icon.gif"><font size="5" color="blue"> Reptiles</font>');
INSERT INTO sp_category VALUES ('CATS','Cats','<image src="../images/cats_icon.gif"><font size="5" color="blue"> Cats</font>');
INSERT INTO sp_category VALUES ('BIRDS','Birds','<image src="../images/birds_icon.gif"><font size="5" color="blue"> Birds</font>');

INSERT INTO sp_product VALUES ('FI-SW-01','FISH','Angelfish','<image src="../images/fish1.jpg">Salt Water fish from Australia');
INSERT INTO sp_product VALUES ('FI-SW-02','FISH','Tiger Shark','<image src="../images/fish4.gif">Salt Water fish from Australia');
INSERT INTO sp_product VALUES ('FI-FW-01','FISH', 'Koi','<image src="../images/fish3.gif">Fresh Water fish from Japan');
INSERT INTO sp_product VALUES ('FI-FW-02','FISH', 'Goldfish','<image src="../images/fish2.gif">Fresh Water fish from China');
INSERT INTO sp_product VALUES ('K9-BD-01','DOGS','Bulldog','<image src="../images/dog2.gif">Friendly dog from England');
INSERT INTO sp_product VALUES ('K9-PO-02','DOGS','Poodle','<image src="../images/dog6.gif">Cute dog from France');
INSERT INTO sp_product VALUES ('K9-DL-01','DOGS', 'Dalmation','<image src="../images/dog5.gif">Great dog for a Fire Station');
INSERT INTO sp_product VALUES ('K9-RT-01','DOGS', 'Golden Retriever','<image src="../images/dog1.gif">Great family dog');
INSERT INTO sp_product VALUES ('K9-RT-02','DOGS', 'Labrador Retriever','<image src="../images/dog5.gif">Great hunting dog');
INSERT INTO sp_product VALUES ('K9-CW-01','DOGS', 'Chihuahua','<image src="../images/dog4.gif">Great companion dog');
INSERT INTO sp_product VALUES ('RP-SN-01','REPTILES','Rattlesnake','<image src="../images/lizard3.gif">Doubles as a watch dog');
INSERT INTO sp_product VALUES ('RP-LI-02','REPTILES','Iguana','<image src="../images/lizard2.gif">Friendly green friend');
INSERT INTO sp_product VALUES ('FL-DSH-01','CATS','Manx','<image src="../images/cat3.gif">Great for reducing mouse populations');
INSERT INTO sp_product VALUES ('FL-DLH-02','CATS','Persian','<image src="../images/cat1.gif">Friendly house cat, doubles as a princess');
INSERT INTO sp_product VALUES ('AV-CB-01','BIRDS','Amazon Parrot','<image src="../images/bird4.gif">Great companion for up to 75 years');
INSERT INTO sp_product VALUES ('AV-SB-02','BIRDS','Finch','<image src="../images/bird1.gif">Great stress reliever');

INSERT INTO sp_supplier VALUES (1,'XYZ Pets','AC','600 Avon Way','','Los Angeles','CA','94024','212-947-0797');
INSERT INTO sp_supplier VALUES (2,'ABC Pets','AC','700 Abalone Way','','San Francisco ','CA','94024','415-947-0797');

INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-1','FI-SW-01',16.50,10.00,1,'P','Large');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-2','FI-SW-01',16.50,10.00,1,'P','Small');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-3','FI-SW-02',18.50,12.00,1,'P','Toothless');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-4','FI-FW-01',18.50,12.00,1,'P','Spotted');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-5','FI-FW-01',18.50,12.00,1,'P','Spotless');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-6','K9-BD-01',18.50,12.00,1,'P','Male Adult');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-7','K9-BD-01',18.50,12.00,1,'P','Female Puppy');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-8','K9-PO-02',18.50,12.00,1,'P','Male Puppy');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-9','K9-DL-01',18.50,12.00,1,'P','Spotless Male Puppy');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-10','K9-DL-01',18.50,12.00,1,'P','Spotted Adult Female');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-11','RP-SN-01',18.50,12.00,1,'P','Venomless');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-12','RP-SN-01',18.50,12.00,1,'P','Rattleless');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-13','RP-LI-02',18.50,12.00,1,'P','Green Adult');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-14','FL-DSH-01',58.50,12.00,1,'P','Tailless');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-15','FL-DSH-01',23.50,12.00,1,'P','With tail');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-16','FL-DLH-02',93.50,12.00,1,'P','Adult Female');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-17','FL-DLH-02',93.50,12.00,1,'P','Adult Male');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-18','AV-CB-01',193.50,92.00,1,'P','Adult Male');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-19','AV-SB-02',15.50, 2.00,1,'P','Adult Male');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-20','FI-FW-02',5.50, 2.00,1,'P','Adult Male');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-21','FI-FW-02',5.29, 1.00,1,'P','Adult Female');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-22','K9-RT-02',135.50, 100.00,1,'P','Adult Male');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-23','K9-RT-02',145.49, 100.00,1,'P','Adult Female');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-24','K9-RT-02',255.50, 92.00,1,'P','Adult Male');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-25','K9-RT-02',325.29, 90.00,1,'P','Adult Female');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-26','K9-CW-01',125.50, 92.00,1,'P','Adult Male');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-27','K9-CW-01',155.29, 90.00,1,'P','Adult Female');
INSERT INTO sp_item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES ('EST-28','K9-RT-01',155.29, 90.00,1,'P','Adult Female');

INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-1',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-2',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-3',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-4',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-5',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-6',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-7',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-8',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-9',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-10',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-11',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-12',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-13',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-14',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-15',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-16',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-17',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-18',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-19',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-20',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-21',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-22',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-23',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-24',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-25',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-26',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-27',10000);
INSERT INTO sp_inventory (itemid, qty ) VALUES ('EST-28',10000);
*/