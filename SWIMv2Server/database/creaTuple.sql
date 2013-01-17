#Inserimento tuple tabella profilo
insert into profilo values ('cla09', 'claudiofratto', 'cla09.com', 'claudio', 'fratto', null, 'sorbo', 'Maschio', 1990, 'user');
insert into profilo values ('euge', 'eugeguid', 'euge.com', 'eugenio', 'guidetti', null, 'ferrara', 'Maschio', '1990', 'user');
insert into profilo values ('kikka', 'kik90', 'kik.com', 'federica', 'garibaldi', null, 'pisa', 'Femmina', 1990, 'user');
insert into profilo values ('franco', 'ciccio2', 'franco.com', 'francesco', 'rizzo', null, 'palermo', 'Maschio', 1990, 'user');
insert into profilo values ('jojo', 'giova', 'gio.com', 'giovanni', 'mio', null, 'cs', 'Maschio', 1980, 'user');

#select * from profilo;

#Inserimento tuple tabella abilita
insert into abilita values(default,'cuoco', null, 'chef per matrimoni');
insert into abilita values(default, 'elettricista', null, 'piccole riparazioni elettriche');
insert into abilita values(default, 'meccanico', null, 'pulizia carburatore auto');

#select * from abilita;

#Inserimento tuple tabella data_completa
insert into data_completa values(1358093818355, 2013, 01, 13, 17, 16, 58);
insert into data_completa values(1358094095242, 2013, 01, 13, 17, 21, 35);
insert into data_completa values(1358094150373, 2013, 01, 13, 17, 22, 30);


#Inserimento tuple tabella proposta abilita
insert into proposta_abilita values(default, 'giocoliere', 'per feste private', default, 'cla09');
insert into proposta_abilita values(default, 'bagnino', 'da spiaggia', default, 'euge');
insert into proposta_abilita values(default, 'baby-sitter', 'nottrurno', 'visionata', 'kikka');

#select * from proposta_abilita;

#Inserimento tuple tabella dichiarazione
insert into dichiarazione values(default, 'cla09', 1, default, default);
insert into dichiarazione values(default,'euge', 1, default, default);
insert into dichiarazione values(default,'kikka', 3, default, default);
insert into dichiarazione values(default,'franco', 2, default, default);

#select * from dichiarazione;

#Inserimento tuple tabella amicizia
insert into amicizia values(default, 'cla09', 'kikka', 1358093818355, 1358094095242);

#select * from amicizia;

#Inserimento tuple tabella aiuto
insert into aiuto values(default, 'sono disperata', 'kikka', 'cla09', 1, 1358093818355, 1358094095242);
insert into aiuto values(default, 'aiutami', 'cla09', 'kikka', 3, 1358093818355, 1358094150373);
insert into aiuto values(default, 'mi serve una mano', 'euge', 'franco', 2, 1358094095242, 1358094150373);
insert into aiuto values(default, 'mi aiuti?', 'euge', 'franco', 2, 1358093818355, null);

#select * from aiuto;

#Inserimento tabella feedback
insert into feedback values(1, 4, 'ottimo lavoro', 1358094150373);

#select * from feedback;