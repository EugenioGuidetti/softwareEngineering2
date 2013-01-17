drop database swimv2;

#creazione della base dati
create database swimv2;

#selezionare database creato
use swimv2;

#creazione dell'utente che può modificare il database (ASSICURARSI CHE NON SIA GIA' PRESENTE UN UTENTE "admin")

#create USER 'admin'@'localhost' IDENTIFIED BY 'admin';
#grant all on swimv2.* to 'admin'@'localhost';

#########################
#creazione delle tabelle#
#########################

#tabella profilo
create table profilo (
	nickname varchar(20),
	password varchar(20) not null,
	email varchar(255)  not null,
	nome varchar(20) not null,
	cognome varchar(20) not null,
	avatar varchar(255),
	citta varchar(50),
	sesso enum('Maschio', 'Femmina'),
	anno_nascita int unsigned,
	ruolo enum('admin', 'user') default 'user',
	primary key(nickname)
);

#tabella abilita
create table abilita (
	id int unsigned auto_increment,
	nome varchar(20) not null, 
	icona varchar(255),
	descrizione varchar(140) not null,
	primary key (id)
);

#tabella data
create table data_completa (
	timestamp bigint unsigned,
	anno smallint(4) unsigned not null,
	mese smallint(2) unsigned not null,
	giorno smallint(2) unsigned not null,
	ora smallint(2) unsigned not null,
	minuto smallint(2) unsigned not null,
	secondo smallint(2) unsigned not null,
	primary key (timestamp)
);

#tabella proposta_abilita
create table proposta_abilita (
	id int unsigned auto_increment,
	nome varchar(20) not null,
	descrizione varchar(140) not null,
	stato_richiesta enum('visionata', 'nonVisionata') default 'nonVisionata' not null,
	user_proponente varchar(20) not null,
	primary key (id),
	foreign key(user_proponente) references profilo(nickname)
		#se elimino (aggiorno) un utente allora elimino (aggiorno) anche le sue proposte di nuove abilita
		on delete cascade
		on update cascade
);

#tabella dichiarazione
create table dichiarazione(
	id int unsigned auto_increment,	
	user varchar(20)not null,
	id_abilita int unsigned not null,
	numero_feedback int unsigned default 0 not null,
	media_valutazioni int unsigned default 0 not null,
	primary key (id),
	constraint dichiarazione_id unique (user, id_abilita),
	#se elimino (aggiorno) un utente allora elimino (aggiorno) anche le sue dichiarazioni di abilita
	foreign key(user) references profilo(nickname)
		on delete cascade
		on update cascade,
	#non e' possibile eliminare un'abilita che e' dichiarata da qualche utente (le operazioni di eliminazione e aggiornamento sono respinte)
	foreign key(id_abilita) references abilita(id)
		on delete restrict
		on update restrict
);

#tabella amicizia
create table amicizia (
	id int unsigned auto_increment,
	user_richiedente varchar(20) not null,
	user_destinatario varchar(20) not null,
	momento_richiesta bigint unsigned not null,
	momento_accettazione bigint unsigned,
	primary key (id),
	#se elimino (aggiorno) lo user richiedente allora tutte le sue richieste di amicizia devono essere eliminate(aggiornate)
	foreign key(user_richiedente) references profilo(nickname)
		on delete cascade
		on update cascade,
	#se elimino (aggiorno) lo user destiatario allora tutte le sue richieste di amicizia devono essere eliminate(aggiornate)
	foreign key(user_destinatario) references profilo(nickname)
		on delete cascade
		on update cascade,
	#non e' possibile eliminare (aggiornare) una data associata al momento di richiesta di un'amicizia
	foreign key(momento_richiesta) references data_completa(timestamp)
		on delete restrict
		on update restrict,
	#non e' possibile eliminare (aggiornare) una data associata al momento di accettazione di un'amicizia
	foreign key(momento_accettazione) references data_completa(timestamp)
		on delete restrict
		on update restrict
);

#tabella aiuto
create table aiuto (
	id int unsigned auto_increment,
	descrizione varchar(140) not null,
	user_richiedente varchar(20) not null,
	user_destinatario varchar(20) not null,
	id_abilita int unsigned not null,
	momento_richiesta bigint unsigned not null,
	momento_accettazione bigint unsigned,
	primary key (id),
	#se elimino (aggiorno) lo user richiedente allora tutte le sue richieste di aiuto devono essere eliminate(aggiornate)
	foreign key(user_richiedente) references profilo(nickname)
		on delete cascade
		on update cascade,
	#se elimino (aggiorno) lo user destiatario allora tutte le sue richieste di aiuto devono essere eliminate(aggiornate)
	foreign key(user_destinatario) references profilo(nickname)
		on delete cascade
		on update cascade,
	#non e' possibile eliminare (aggiornare) l'abilita oggetto di una richiesta di aiuto
	foreign key(id_abilita) references abilita(id)
		on delete restrict
		on update restrict,
	#non e' possibile eliminare (aggiornare) una data associata al momento di richiesta di un aiuto
	foreign key(momento_richiesta) references data_completa(timestamp)
		on delete restrict
		on update restrict,
	#non e' possibile eliminare (aggiornare) una data associata al momento di accettazione di un aiuto
	foreign key(momento_accettazione) references data_completa(timestamp)
		on delete restrict
		on update restrict
);

#tabella feedback
create table feedback (
	id_aiuto int unsigned,
	valutazione_numerica int unsigned not null,
	valutazione_estesa varchar(140) not null,
	momento_rilascio bigint unsigned not null,
	primary key (id_aiuto),
	#se elimino (aggiorno) l'aiuto allora elimino (aggiorno) anche il feedback ad esso associato
	foreign key(id_aiuto) references aiuto(id)
		on delete cascade
		on update cascade,
	#non e' possibile eliminare (aggiornare) una data associata al momento di rilascio di un feedback
	foreign key(momento_rilascio) references data_completa(timestamp)
		on delete restrict
		on update restrict
);

#Creazione dell'utente amministratore
insert into profilo values ('admin', 'admin', 'inserisci la tua mail', 
							'inserisci nome', 'inserisci cognome',
							null, null, null, null, 'admin');

#Creazione dell'abilità di default
insert into abilita values (default, 'abilità generica', 'icona default', 'inserisci questa in attesa di proporne una');

#Creazione della data utilizzata convenzionalmente per indicare il momentoAccettazione null
#insert into data_completa values( '9999-12-31 23:59:59',9999, 12, 31, 23, 59, 59);