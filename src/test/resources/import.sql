INSERT INTO system_user (id,enabled,locked,name,password,username, opt_lock) VALUES (1,1,0,'Usuario Teste','25a5c0abc72c1c39ec05d2119f95b8f7','username@hotmail.com', 0);
INSERT INTO system_user (id,enabled,locked,name,password,username, opt_lock) VALUES (2,1,0,'Open Graph Test User','79cf49f79aff73f103ff7470fa7a7d65','open_jcgmfer_user@tfbnw.net', 0);

INSERT INTO role (id,authority,parent_id) VALUES (1, 'MASTER', null);
INSERT INTO user_role (id, role_id, user_id) VALUES (1, 1, 1);

INSERT INTO game_player (level,nickname,xp,user_id) VALUES (1,'bob',0,1), (0,'alice',0,2);

INSERT INTO resource(id,open,operation,uri) VALUES (8,1,'POST','/poi');
INSERT INTO resource(id,open,operation,uri) VALUES (6,1,'POST','/user');
INSERT INTO resource(id,open,operation,uri) VALUES (7,0,'GET','/feed');

INSERT INTO role_resource(id,resource_id,role_id) VALUES (1,8,1);

INSERT INTO game_task(id,givenxp,resource_id) VALUES (1,10,8); --create poi task
INSERT INTO game_task(id,givenxp,resource_id) VALUES (2,5,6);  --create user task

INSERT INTO game_badge(id,description,imageurl,name) VALUES (1,E'poi creator!',E'fakepath',E'Poi Creator');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (2,E'user creator!',E'fakepath',E'User Creator');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (3,E'user and poi creator',E'fakepath',E'User and Poi Creator');
INSERT INTO game_badge(id,description,imageurl,name) VALUES (4,E'testing creation of task group',E'fakepath',E'test');

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (1,10,1); -- create poi task group
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (1,1,1); -- associando a task de criação de poi ao grupo

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (2,5,2); -- create user task group
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (2,2,2);-- associando a task de criação de user ao grupo

INSERT INTO game_task_group(id,givenxp,badge_id) VALUES (3,20,3); -- task group that combines create user and create poi
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (3,1,3);
INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (4,2,3); 

INSERT INTO game_quest(task_group_id,minLevel,name) VALUES (1,10,'Quest Test');

INSERT INTO game_challenge(task_group_id,name,description) VALUES (1,'Challenge Test','Testing a challenge task');


-- progressos do bob
--NSERT INTO game_assignment_progression(id,complete,progress,player_id,task_assignment_id,COMPLETEDWORK)  VALUES (1,0,0.5,1,1,1);
--INSERT INTO game_assignment_progression(id,complete,progress,player_id,task_assignment_id,COMPLETEDWORK) VALUES (2,1,1,1,2,1);
--INSERT INTO game_assignment_progression(id,complete,progress,player_id,task_assignment_id,COMPLETEDWORK) VALUES (3,1,1,1,3,1);

-- testando constrainsts

INSERT INTO game_task_assignment(id,task_id,task_group_id) VALUES (5,1,1); 
INSERT INTO game_assignment_constraint(id,task_assignment_id,description) VALUES (1,5,'Assignment Quantity test');
INSERT INTO game_assignment_constraint_quantity(constraint_id,quantity) VALUES (1,5);


-- testando reports
INSERT INTO field_type(id,name) VALUES (1,'FIELD_TYPE');
INSERT INTO field(id,name,helptext,required,type_id) VALUES (1,'FIELD','FIELD_TEST',FALSE,1);