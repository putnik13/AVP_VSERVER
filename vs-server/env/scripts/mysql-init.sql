---- Server Fine tuning
---- my.ini  [mysqld] section
---- # this adds 35% speed increase
---- innodb_flush_log_at_trx_commit=2
---- # To store big blobs
---- max_allowed_packet=200M
---- transaction-isolation = READ-COMMITTED

-- initialization
DROP USER 'vserver';
CREATE USER 'vserver' IDENTIFIED BY 'vserver';
DROP DATABASE vserver;
CREATE DATABASE vserver DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON vserver.* TO 'vserver'@'%';

--  if you get error:
--  ERROR 1396 (HY000) at line 3: Operation CREATE USER failed for 'vserver'@'%'
--  then run the following commands:
-- DROP USER 'vserver'@'localhost'
-- delete from mysql.user where user='vserver';
-- delete from mysql.db where user='vserver';
-- FLUSH PRIVILEGES;
