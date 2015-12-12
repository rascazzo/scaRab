/*
* Schema sql ERbase 20150120
* utenti
* emilio.rascazzo@gmail.com
*/



create table user(
    username varchar(32) not null,
    password varchar(32) not null,
    user_id int unsigned auto_increment not null primary key,
    email varchar(255) not null,
    modify_date timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP)
engine = InnoDB;

CREATE unique INDEX user_idx on user (username,password)  using btree;

create table role(
    user_id int unsigned not null,
    role varchar(32) not null,
    foreign key (user_id) references user (user_id) on delete cascade on update cascade)
engine = InnoDB;

alter table user add column create_date timestamp not null;

create table param(
   name varchar(32) not null,
   value varchar(128) not null,
   type varchar(32) not null,
   primary key(name,value),
   unique index (name,value) using btree)
engine = MyIsam;

insert into param values ('WebContentPath','/usr/share/nginx/www/ERbase','String');

create table site(
    idsite varchar(32) not null primary key,
    domain varchar(255) not null,
    title varchar(255) not null,
    subtitle varchar(255) not null,
    xsltpath varchar(255) not null,
    adminxsltpath varchar(255) not null,
    webcontentpath varchar(255) not null,
    mainlang varchar(5) not null,
    mixedlang boolean not null)
engine = InnoDB;

alter table user add column mainsuperusersite varchar(32);
alter table user add foreign key (mainsuperusersite) references site (idsite) on delete no action on update cascade;
alter table site modify column mixedlang boolean default 0;
alter table role drop foreign key role_ibfk_1;
alter table user drop user_id;
alter table user add user_id varchar(40) primary key;
alter table role modify user_id varchar(40);
alter table role add foreign key (user_id) references user (user_id) on delete cascade on update cascade; 

create table userdetails(
	iduserdetail int unsigned auto_increment not null primary key,
	user_id	varchar(40) not null,
	foreign key (user_id) references user (user_id) on delete cascade on update cascade,
	firstname varchar(60),
	surname varchar(80),
	dayofbird int unsigned,
	monthofbird int unsigned,
	yearofbird int unsigned,
	cf varchar(60),
	piva varchar(60),
	address varchar(255),
	country varchar(120),
	state varchar(120),
	telephone varchar(120),
	smartphone varchar(120),
	flagprivacy boolean,
	flagprivacy_third boolean
)
engine = InnoDB;
alter table user add column privacy boolean default 0 not null;
    
create table tag_attribut(id int unsigned auto_increment not null primary key, name varchar(255) not null, value varchar(255) not null) engine = InnoDB;
create unique index idx_attrib on tag_attribut (name,value) using btree;
create table tag_name(id int unsigned auto_increment not null primary key, name varchar(64) not null) engine = InnoDb;
create table tag_son(idtagnameFK int unsigned, foreign key (idtagnameFK) references tag_name (id) on delete cascade on update cascade,idtagsonFK int unsigned, foreign key (idtagsonFK) references tag_name(id) on delete cascade on update cascade, primary key(idtagnameFK,idtagsonFK)) engine = InnoDb;
create table tag_map(id int unsigned auto_increment not null primary key, branch varchar(64) not null, idtagnameFK int unsigned, foreign key (idtagnameFK) references tag_name(id) on delete cascade on update cascade) engine = InnoDb;
alter table tag_name add active boolean default 1;
create table tag_name_attr(idtagname int unsigned,idattribut int unsigned,foreign key (idtagname) references tag_name (id) on delete cascade on update cascade, foreign key (idattribut) references tag_attribut (id) on delete cascade on update cascade, primary key (idattribut,idtagname)) engine = InnoDb;
create table tag_order(id int unsigned auto_increment not null primary key, idtagname int unsigned not null, numorder int(3) unsigned not null, foreign key (idtagname) references tag_name(id) on delete cascade on update cascade) engine = innodb;
alter table tag_name add textvalue varchar(255);

alter table tag_name add column tag_siteid varchar(32) not null;
alter table tag_name add foreign key fk_tag_siteid (tag_siteid) references site (idsite) on update cascade on delete no action;

create unique index idx_tag_name on tag_name (name,tag_siteid) using btree;

create table tag_name_value(id_tag_value int unsigned auto_increment not null primary key,id_tag_name int unsigned not null,name_value varchar(255),lang varchar(4),foreign key fk_id_tag_name_value (id_tag_name) references tag_name (id) on update cascade on delete no action)engine=InnoDB;
create unique index idx_tag_name_value on tag_name_value (lang,id_tag_name) using btree;


