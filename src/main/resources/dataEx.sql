-- ROLES
INSERT INTO roles (id, name)
VALUES
(1, 'Role_ADMIN'),
(2, 'Role_USER');

-------------
-- USERS
------------

-- Password : admin123
INSERT INTO users
(id, name, email, password, role_id, created_at)
VALUES
(
1,
'Admin name',
'Admin@gmail.com',
'$2a$10$hRF2g72pEngATxYjbRvIHOBFGioQIejHm3XxCLLwB',
1,
CURRENT_TIMESTAMP
);


-- Password : user123
INSERT INTO users
(id, name, email, password, role_id, created_at)
VALUES
(
2,
'user1 name',
'user1@gmail.com',
'$2a$10$TjaAUz6sMBXPiXq7FD5GYO.wZe6MUTMVJMCOQicUIKXyHqH7NfnI7',
2,
CURRENT_TIMESTAMP
);

--Password : Test1234
INSERT INTO users
(id, name, email, password, role_id, created_at)
VALUES
(
3,    
'Test USer',
'TestUsr@gmail.com',
'$2a$10$5t26ThlnPmAZoME.GeSHdO6SWm1ZMUrFqN1zOXUxhyWdFm9NHRvrc',
2,
CURRENT_TIMESTAMP
);