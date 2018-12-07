-- Adding routes to resource and giving permissions in role_resource tables to make health_agent routes work

INSERT INTO resource(id,open,operation,uri) 
VALUES (47, true ,'PUT','/user/device');

INSERT INTO resource(id,open,operation,uri) 
VALUES (48, false ,'PUT','/poi/\d+/status-to-treated');

INSERT INTO resource(id,open,operation,uri) 
VALUES (49, false ,'PUT','/poi/\d+/status-to-in-analysis');

INSERT INTO resource(id,open,operation,uri)
VALUES (50, false ,'POST','/user/health-agent');

INSERT INTO resource(id,open,operation,uri)
VALUES (51, true ,'GET','/dashboard-health-agent/dashboard');



INSERT INTO role (id,authority,parent_id) VALUES (4, 'HEALTH_AGENT', null);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (26,47,4);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (24,48,4);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (25,49,4);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (27,50,4);

INSERT INTO role_resource(id,resource_id,role_id)
VALUES (28,51,4);

-- adding access to agent to game/feed route
INSERT INTO "public"."role_resource"("id", "resource_id", "role_id") VALUES('29', 42, 4);

