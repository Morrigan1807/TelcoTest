DROP DATABASE IF EXISTS TelcoTest;

CREATE DATABASE IF NOT EXISTS TelcoTest;

use TelcoTest;
CREATE TABLE IF NOT EXISTS FILEINFO
(id_record INT PRIMARY KEY AUTO_INCREMENT,
 filename VARCHAR(520) NOT NULL,
 time_of_creation TIMESTAMP NOT NULL)