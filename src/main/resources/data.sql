INSERT INTO users(username, password) VALUES('front_user', '$2a$10$FqFYr/zLhTu2/Sz1tu2/v.m.iHIbINlG1MCIcg2yRP/1vLYvx9XHy');  --> ID: 1
INSERT INTO users(username, password) VALUES('api_user', '$2a$10$FqFYr/zLhTu2/Sz1tu2/v.m.iHIbINlG1MCIcg2yRP/1vLYvx9XHy');   --> ID: 2

INSERT INTO authorities(authority) VALUES('FRONTEND'); --> ID: 1
INSERT INTO authorities(authority) VALUES('API');  --> ID: 2

INSERT INTO users_authorities(user_id, authority_id) VALUES(1,1); --> front_user with FRONTEND role
INSERT INTO users_authorities(user_id, authority_id) VALUES(2,2); --> api_user with API role
