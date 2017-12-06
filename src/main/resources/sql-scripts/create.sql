--CREATE DATABASE stepik_web_service
--WITH ENCODING = 'UTF8';

CREATE SCHEMA IF NOT EXISTS sws;

CREATE TABLE IF NOT EXISTS  sws.user (
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_login VARCHAR (256) UNIQUE NOT NULL,
  user_password VARCHAR (256) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_user_login
ON sws.user (user_login);


