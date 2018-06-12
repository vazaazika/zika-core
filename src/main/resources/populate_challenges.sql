-- inserting tasks
INSERT INTO game_task(id,givenxp,resource_id,description) VALUES (2,100,19,'Verificação de notificação');
INSERT INTO game_task(id,givenxp,resource_id,description) VALUES (3,10,23,'Compartilhar VazaZika nas redes sociais');
INSERT INTO game_task(id,givenxp,resource_id,description) VALUES (4,10,23,'Convidar amigo por email');
INSERT INTO game_task(id,givenxp,resource_id,description) VALUES (5,10,23,'Criar desafio');

-- inserting the badges
INSERT INTO game_badge(id,description,imageurl,name) VALUES (3,'Usuário recebe esse badge quando ele realiza a primeira notificação.','fa fa-trophy','Relator');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (4,'Usuário recebe esse badge quando ele realiza a primeira verificação de foco.','fa fa-trophy','On Top');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (5,'Usuário recebe esse badge quando ele compartilha em sua rede social a plataforma VazaZika mais de 25 vezes.','fa fa-trophy','Disseminador Júnior');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (6,'Usuário srecebe esse badge quando mais de 10 amigos aceitam o seu convite.','fa fa-trophy','Popular');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (7,'Usuário recebe esse badge quando ele cria um desafio pela primeira vez.','fa fa-trophy','Catequizador');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (8,'Usuário recebe esse badge quando ele completa o desafio Strike Mosquito pela primeira vez.','fa fa-trophy','Striker');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (9,'Usuário recebe esse badge quando o time a qual pertence ganha o desafio On Top.','fa fa-trophy','On Top');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (10,'Usuário recebe esse badge quando ele completa o desafio Fight! pela primeira vez.','fa fa-trophy','Lutador');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (11,'Usuário recebe esse badge quando o seu time completa o desafio Team Up! pela primeira vez.','fa fa-trophy','Trabalho em Equipe');


-- inserting task groups
INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (3,100,3); -- realiza a primeira notificação de foco do mosquito
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (3,1,3);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (4,100,4); -- verificar o primeiro foco do mosquito
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (4,2,4);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (5,25,5); -- compartilha na rede social
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (5,3,5);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (6,10,6); -- convida amigo por email
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (6,4,6);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (7,10,7); -- criar desafio
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (7,5,7);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (8,3000,8); -- strike mosquito
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (8,1,8);
INSERT INTO game_assignment_constraint(id,task_assignment_id,description) VALUES (2,8,'Restrição de quantidade de notificações');
INSERT INTO game_assignment_constraint_quantity(constraint_id,quantity) VALUES (2,10);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (9,1000,9); -- on top
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (9,1,9);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (10,500,10); -- fight
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (10,1,10);

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (11,500,11); -- fight
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (11,1,11);


-- inserting challenges
INSERT INTO game_challenge(task_group_id,name,description,type) VALUES (8,'Strike Mosquito','O objetivo desse desafio é reportar 10 focos do mosquito no período de uma semana.','individual');
INSERT INTO game_challenge(task_group_id,name,description,type) VALUES (9,'On Top','O objetivo desse desafio é se tornar o time com a maior pontuação (XP) de uma cidade. Logo para cada cidade haverá apenas um time (atualmente) vencedor desse desafio.','time');
INSERT INTO game_challenge(task_group_id,name,description,type) VALUES (10,'Fight!','O objetivo é desafiar um amigo para verificar quem notifica mais focos de mosquito nas próximas 24 horas. O usuário vencedor é aquele que acumula mais XP com notificações de foco após as 24 horas.','individual');
INSERT INTO game_challenge(task_group_id,name,description,type) VALUES (11,'Team Up!','O objetivo é desafiar um time para verificar quem notifica mais focos de mosquito em um tempo estipulado pelos times. O time vencedor é aquele cujo os membros do time acumulem mais XP com notificações de foco após se passar o tempo estipulado.','time');


--OBSERVACAO: FOR EACH NEW RESOURCE IS NEEDED TO UPDATE THE TABLE ROLE_RESOURCE
--insert new resource to get player info
INSERT INTO RESOURCE(id,open,operation,uri) VALUES(37,false,'GET','/game/player/self');

--insert new resource to share the vazazika
INSERT INTO RESOURCE(id,open,operation,uri) VALUES(38,false,'GET','/game/share');

--insert new resource to invite new users
INSERT INTO RESOURCE(id,open,operation,uri) VALUES(39,false,'POST','/game/player/invite');

--insert new resource to get and post challenges
INSERT INTO RESOURCE(id,open,operation,uri) VALUES(40,false,'GET','/game/challenge');

INSERT INTO RESOURCE(id,open,operation,uri) VALUES(41,false,'POST','/game/challenge');
