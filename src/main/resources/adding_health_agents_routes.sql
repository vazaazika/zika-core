-- Adding routes to resource and giving permissions in role_resource tables to make health_agent routes work

INSERT INTO resource(id,open,operation,uri) 
VALUES (47, true ,'PUT','/user/device');

INSERT INTO resource(id,open,operation,uri) 
VALUES (48, false ,'PUT','/poi/\d+/status-to-treated');

INSERT INTO resource(id,open,operation,uri) 
VALUES (49, false ,'PUT','/poi/\d+/status-to-in-analysis');

INSERT INTO resource(id,open,operation,uri)
VALUES (48, false ,'POST','/user/health-agent');

INSERT INTO role (id,authority,parent_id) VALUES (4, 'HEALTH_AGENT', null);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (26,47,4);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (24,44,4);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (25,45,4);


INSERT INTO role_resource(id,resource_id,role_id)
VALUES (26,48,4);

