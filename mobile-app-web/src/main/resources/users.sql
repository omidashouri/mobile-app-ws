INSERT INTO photo_app.tbl_user
    (email,email_verification_status,email_verification_token,
     encrypted_password,first_name,last_name,
     user_id
--       , address_id
     )
     VALUES
     ('omidashouri@gmail.com',0,NULL,
      '$2a$10$NynK4.g5M1eLYHgSi2qkUu2WMByT12hUa0yyAKAT0W.l1iEP/h.8e','omid','ashouri',
      'aLIRVt88hdQ858q5AMURm1QI6DC3Je'
--        ,NULL
      )
    ,
    ('omidashouri1@gmail.com',0,NULL,
     '$2a$10$z3JNk3dZmErId9JOGZi/K.Uvzeyuz95s8Ynm7yFyF736esJi9upUq','omid1','ashouri1',
     'a170JWYiLUVviIh7CjW3ftojaZMMQR'
--       ,NULL
     )
    ,
    ('omidashouri2@gmail.com',0,NULL,
     '$2a$10$6gsSBzIaSKGcCO195NUk6eHdZs2owSe6pZ384WplZsGDil3fOzrCK','omid2','ashouri2',
     'XKch1CblSMJUsgUwHf3gX27revOsFB'
--       ,NULL
     )
    ;

/*INSERT INTO photo_app.tbl_address
    (address_id,user_id,country,city,
     street_name,postal_code,type)
    VALUES
    ('12',NULL,'IRAN','Tehran',
     '123 ABC Street','123456','billing')
     ,
    ('34',NULL,'IRAN','Tehran',
     '456 DEF Street','123456','cash')
     ,
    ('56',NULL,'GILAN','Rasht',
     '123 ABC Street','123456','billing')
     ,
    ('78',NULL,'GILAN','Rasht',
     '456 DEF Street','123456','cash')
    ,
    ('910',NULL,'KHORASAN','Mashhad',
     '123 ABC Street','123456','billing')
     ,
    ('1112',NULL,'KHORASAN','Mashhad',
     '456 DEF Street','123456','cash')
    ;*/


