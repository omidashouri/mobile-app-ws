INSERT INTO photo_app.tbl_user
    (email,email_verification_status,
     email_verification_token,
     encrypted_password,first_name,last_name,
     user_public_id
--       , address_id
     )
     VALUES
     ('omidashouri@gmail.com',1,
      NULL,
      '202cb962ac59075b964b07152d234b70','omid','ashouri',
      'aLIRVt88hdQ858q5AMURm1QI6DC3Je'
--        ,NULL
      )
    ,
    ('omidashouri1@gmail.com',1,
     NULL,
     '202cb962ac59075b964b07152d234b70','omid1','ashouri1',
     'a170JWYiLUVviIh7CjW3ftojaZMMQR'
--       ,NULL
     )
    ,
    ('omidashouri2@gmail.com',0,
     'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJJU1N3OEtsY2xMc2hjWGJqZm02S09kU3RFNEJjOFAiLCJleHAiOjE1NzkyNTgzNzB9.GusVFUtthTVsc04EjfPs1xzDiPdQulFplj3YwDjwav0Vu-tClkAgNaD7PAgjx5DNtRYE9YS1q-0zwJ5IawoKzg',
     '202cb962ac59075b964b07152d234b70','omid2','ashouri2',
     'XKch1CblSMJUsgUwHf3gX27revOsFB'
--       ,NULL
     )
    ;

INSERT INTO photo_app.tbl_address
(address_public_id, city, country,
 postal_code, street_name, type, user_id)
values
('S3THX2dSeuusRCQXcbdjT17QFGe5vk','Tehran','IRAN',
 '123 123','123 ABC Street','billing',1);

INSERT INTO photo_app.tbl_address
(address_public_id, city, country,
 postal_code, street_name, type, user_id)
values
('KO5hRxxRy9dZotMfbcVydotYWtClgZ','Gilan','Rasht',
 '456 456','456 DEF Street','cash',1);

INSERT INTO photo_app.tbl_address
(address_public_id, city, country,
 postal_code, street_name, type, user_id)
values
('JEapmze7HAuivvLyhWmxrIior9MM0w','Khorasan','Mashhad',
 '789 789','789 GHI Street','check',1);

INSERT INTO photo_app.tbl_address
(address_public_id, city, country,
 postal_code, street_name, type, user_id)
values
('xKzV7TBAQGz27WrepGa5Ituw7MkC3b','Sistan','Zahedan',
 '1011 1011','101112 JKL Street','loan',1);


/*
  202cb962ac59075b964b07152d234b70

  $2a$10$6gsSBzIaSKGcCO195NUk6eHdZs2owSe6pZ384WplZsGDil3fOzrCK
*/

