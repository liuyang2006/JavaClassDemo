/* Save and run the entire script in one file */
/* Student information database tables
   Run this from the root */

# drop database javabook;
# create database javabook;
# CREATE USER 'scott'@'localhost' IDENTIFIED BY 'tiger';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, CREATE VIEW, DROP,
EXECUTE, REFERENCES ON javabook.* TO 'scott'@'localhost';

USE javabook;

DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS College;
DROP TABLE IF EXISTS Subject;
DROP TABLE IF EXISTS Department;
DROP TABLE IF EXISTS Enrollment;
DROP TABLE IF EXISTS TaughtBy;
DROP TABLE IF EXISTS Faculty;

CREATE TABLE Department (
  deptId    CHAR(4) NOT NULL,
  name      VARCHAR(25) UNIQUE, /* works in MYSQL */
  chairId   VARCHAR(9),
  collegeId VARCHAR(4),
  CONSTRAINT pkDepartment PRIMARY KEY (deptId)
);

CREATE TABLE Enrollment (
  ssn            CHAR(9) NOT NULL,
  courseId       CHAR(5) NOT NULL,
  dateRegistered DATE,
  grade          CHAR(1),
  CONSTRAINT pkEnrollment PRIMARY KEY (ssn, courseId)
);

CREATE TABLE TaughtBy (
  courseId CHAR(5),
  ssn      CHAR(9)
);

CREATE TABLE Faculty (
  ssn       CHAR(9) NOT NULL,
  firstName VARCHAR(25),
  mi        CHAR(1),
  lastName  VARCHAR(25),
  phone     CHAR(10),
  email     VARCHAR(50),
  office    VARCHAR(15),
  startTime DATE,
  rank      VARCHAR(20),
  salary    DOUBLE,
  deptId    CHAR(4),
  CONSTRAINT pkFaculty PRIMARY KEY (ssn)
);

CREATE TABLE Subject (
  subjectId CHAR(4) NOT NULL,
  name      VARCHAR(25),
  startTime DATE,
  deptId    CHAR(4),
  CONSTRAINT pkSubject PRIMARY KEY (subjectId)
);

CREATE TABLE Student (
  ssn       CHAR(9) NOT NULL,
  firstName VARCHAR(25),
  mi        CHAR(1),
  lastName  VARCHAR(25),
  phone     CHAR(11),
  birthDate DATE,
  street    VARCHAR(100),
  zipCode   CHAR(5),
  deptId    CHAR(4),
  CONSTRAINT pkStudent PRIMARY KEY (ssn),
  CONSTRAINT fkDeptId FOREIGN KEY (deptId)
  REFERENCES Department (deptId)
);

CREATE TABLE Course (
  courseId     CHAR(5),
  subjectId    CHAR(4)     NOT NULL,
  courseNumber INTEGER,
  title        VARCHAR(50) NOT NULL,
  numOfCredits INTEGER,
  CONSTRAINT pkCourse PRIMARY KEY (courseId),
  CONSTRAINT fkSubjectId FOREIGN KEY (subjectId)
  REFERENCES Subject (subjectId)
);

CREATE TABLE College (
  collegeId CHAR(4)     NOT NULL,
  name      VARCHAR(25) NOT NULL,
  since     DATE,
  deanId    CHAR(9),
  CONSTRAINT pkCollege PRIMARY KEY (collegeId),
  CONSTRAINT fkDeanId FOREIGN KEY (deanId)
  REFERENCES Faculty (ssn)
);

INSERT INTO Department VALUES (
  'CS', 'Computer Science', '111221115', 'SC');
INSERT INTO Department VALUES (
  'MATH', 'Mathematics', '111221116', 'SC');
INSERT INTO Department VALUES (
  'CHEM', 'Chemistry', '111225555', 'SC');
INSERT INTO Department VALUES (
  'EDUC', 'Education', '333114444', 'EDUC');
INSERT INTO Department VALUES (
  'ACCT', 'Accounting', '333115555', 'BUSS');
INSERT INTO Department VALUES (
  'BIOL', 'Biology', '111225555', 'SC');


INSERT INTO Enrollment VALUES (
  '444111110', '11111', now(), 'A');
/* In MS Access, replace now() by date() */
INSERT INTO Enrollment VALUES (
  '444111110', '11112', now(), 'B');
INSERT INTO Enrollment VALUES (
  '444111110', '11113', now(), 'C');
INSERT INTO Enrollment VALUES (
  '444111111', '11111', now(), 'D');
INSERT INTO Enrollment VALUES (
  '444111111', '11112', now(), 'F');
INSERT INTO Enrollment VALUES (
  '444111111', '11113', now(), 'A');
INSERT INTO Enrollment VALUES (
  '444111112', '11111', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111112', '11112', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111112', '11114', now(), 'B');
INSERT INTO Enrollment VALUES (
  '444111112', '11115', now(), 'C');
INSERT INTO Enrollment VALUES (
  '444111112', '11116', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111113', '11111', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111113', '11113', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111114', '11115', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111115', '11115', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111115', '11116', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111116', '11111', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111117', '11111', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111118', '11111', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111118', '11112', now(), NULL);
INSERT INTO Enrollment VALUES (
  '444111118', '11113', now(), NULL);

INSERT INTO TaughtBy VALUES (
  '11111', '111221111');
INSERT INTO TaughtBy VALUES (
  '11112', '111221111');
INSERT INTO TaughtBy VALUES (
  '11113', '111221111');
INSERT INTO TaughtBy VALUES (
  '11114', '111221115');
INSERT INTO TaughtBy VALUES (
  '11115', '111221110');
INSERT INTO TaughtBy VALUES (
  '11116', '111221115');
INSERT INTO TaughtBy VALUES (
  '11117', '111221116');
INSERT INTO TaughtBy VALUES (
  '11118', '111221112');

INSERT INTO Subject VALUES (
  'CSCI', 'Computer Science', '1980-08-01', 'CS');
INSERT INTO Subject VALUES (
  'ITEC', 'Information Technology', '2002-01-01', 'CS');
INSERT INTO Subject VALUES (
  'MATH', 'Mathematical Science', '1935-08-01', 'MATH');
INSERT INTO Subject VALUES (
  'STAT', 'Statistics', '1980-08-01', 'MATH');
INSERT INTO Subject VALUES (
  'EDUC', 'Education', '1980-08-01', 'EDUC');

INSERT INTO Faculty VALUES (
  111221110, 'Patty', 'R', 'Smith', '9129215555',
             'patty@yahoo.com', 'SC129', '1976-10-11',
             'Full Professor', 60000, 'MATH');
INSERT INTO Faculty VALUES (
  111221111, 'George', 'P', 'Franklin', '9129212525',
             'george@yahoo.com', 'SC130', '1986-10-12',
             'Associate Professor', 65000, 'CS');
INSERT INTO Faculty VALUES (
  111221112, 'Jean', 'D', 'Yao', '9129215556',
             'jean@yahoo.com', 'SC131', '1976-08-13',
             'Full Professor', 65000, 'MATH');
INSERT INTO Faculty VALUES (
  '111221113', 'Frank', 'E', 'Goldman', '9129215557',
               'frank@yahoo.com', 'SC132', '1996-01-14',
               'Assistant Professor', 60000, 'ACCT');
INSERT INTO Faculty VALUES (
  '111221114', 'Steve', 'T', 'Templeton', '9129215558',
               'steve@yahoo.com', 'UH132', '1998-01-01',
               'Assistant Professor', 60000, 'ACCT');
INSERT INTO Faculty VALUES (
  '111221115', 'Alex', 'T', 'Bedat', '9129215559',
               'alex@yahoo.com', 'SC133', '2000-01-01',
               'Full Professor', 95000, 'CS');
INSERT INTO Faculty VALUES (
  '111221116', 'Judy', 'T', 'Woo', '9129215560',
               'judy@yahoo.com', 'SC133', '2000-01-01',
               'Full Professor', 55000, 'MATH');
INSERT INTO Faculty VALUES (
  '111221117', 'Joe', 'R', 'Chang', '9129215561',
               'joe@yahoo.com', 'ED133', '2000-01-01',
               'Full Professor', 55000, 'EDUC');
INSERT INTO Faculty VALUES (
  '111221118', 'Francis', 'R', 'Chin', '9129215562',
               'joe@yahoo.com', 'ED133', '1989-01-01',
               'Full Professor', 75000, 'BIOL');
INSERT INTO Faculty VALUES (
  '111221119', 'Ray', 'R', 'Smith', '9129215563',
               'ray@yahoo.com', 'SC133', '1994-01-01',
               'Full Professor', 85000, 'CHEM');

INSERT INTO Student VALUES (
  '444111110', 'Jacob', 'R', 'Smith', NULL,
  '1985-04-09', '99 Kingston Street', '31435', 'BIOL');
INSERT INTO Student VALUES (
  '444111111', 'John', 'K', 'Stevenson', '9129219434',
  NULL, '100 Main Street', '31411', 'BIOL');
INSERT INTO Student VALUES (
  '444111112', 'George', 'R', 'Heintz', '9129213454',
  '1974-10-10', '1200 Abercorn Street', '31419', 'CS');
INSERT INTO Student VALUES (
  '444111113', 'Frank', 'E', 'Jones', '9125919434',
  '1970-09-09', '100 Main Street', '31411', 'BIOL');
INSERT INTO Student VALUES (
  '444111114', 'Jean', 'K', 'Smith', '9129219434',
  '1970-02-09', '100 Main Street', '31411', 'CHEM');
INSERT INTO Student VALUES (
  '444111115', 'Josh', 'R', 'Woo', '7075989434',
  '1970-02-09', '555 Franklin Street', '31411', 'CHEM');
INSERT INTO Student VALUES (
  '444111116', 'Josh', 'R', 'Smith', '9129219434',
  '1973-02-09', '100 Main Street', '31411', 'BIOL');
INSERT INTO Student VALUES (
  '444111117', 'Joy', 'P', 'Kennedy', '9129229434',
  '1974-03-19', '103 Bay Street', '31412', 'CS');
INSERT INTO Student VALUES (
  '444111118', 'Toni', 'R', 'Peterson', '9129229434',
  '1964-04-29', '103 Bay Street', '31412', 'MATH');
INSERT INTO Student VALUES (
  '444111119', 'Patrick', 'R', 'Stoneman', NULL,
  '1969-04-29', '101 Washington Street', '31435', 'MATH');
INSERT INTO Student VALUES (
  '444111120', 'Rick', 'R', 'Carter', NULL,
  '1986-04-09', '19 West Ford Street', '31411', 'BIOL');

INSERT INTO Course VALUES (
  '11111', 'CSCI', '1301', 'Intro to Java I', 4);
INSERT INTO Course VALUES (
  '11112', 'CSCI', '1302', 'Intro to Java II', 3);
INSERT INTO Course VALUES (
  '11113', 'CSCI', '4720', 'Database Systems', 3);
INSERT INTO Course VALUES (
  '11114', 'CSCI', '4750', 'Rapid Java Application', 3);
INSERT INTO Course VALUES (
  '11115', 'MATH', '2750', 'Calculus I', 5);
INSERT INTO Course VALUES (
  '11116', 'MATH', '3750', 'Calculus II', 5);
INSERT INTO Course VALUES (
  '11117', 'EDUC', '1111', 'Reading', 3);
INSERT INTO Course VALUES (
  '11118', 'ITEC', '1344', 'Database Administration', 3);

INSERT INTO College VALUES (
  'SC', 'Science', '1994-01-01', '111221110');
INSERT INTO College VALUES (
  'NURS', 'Nursing', '1994-01-01', NULL);
INSERT INTO College VALUES (
  'EDUC', 'Education', '1994-01-01', '111221117');
INSERT INTO College VALUES (
  'BUSS', 'Business', '1994-01-01', '111221114');

/* For exercise 34.7 in intro6e  */
DROP TABLE IF EXISTS csci4990;
DROP TABLE IF EXISTS csci1301;
DROP TABLE IF EXISTS csci1302;

CREATE TABLE csci4990 (
  ssn   CHAR(11),
  name  VARCHAR(25),
  score FLOAT
);

INSERT INTO csci4990 VALUES (
  '111-22-3333', 'John F Smith', 85.4);

CREATE TABLE csci1301 (
  ssn   CHAR(11),
  name  VARCHAR(25),
  score FLOAT
);

INSERT INTO csci1301 VALUES (
  '111-22-3333', 'John F Smith', 75.4);

CREATE TABLE csci1302 (
  ssn   CHAR(11),
  name  VARCHAR(25),
  score FLOAT
);

INSERT INTO csci1302 VALUES (
  '111-22-3333', 'John F Smith', 55.4);

/* For exercise 34.8 in intro6e */
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Address;

CREATE TABLE Account (
  username VARCHAR(20),
  password VARCHAR(25),
  name     VARCHAR(20)
);

INSERT INTO Account VALUES (
  'javaman', 'namavaj', 'John F. Smith');

DROP TABLE IF EXISTS Staff;
/* For the Database Programming Chapter in intro7e */
CREATE TABLE Staff (
  id        CHAR(9) NOT NULL,
  lastName  VARCHAR(15),
  firstName VARCHAR(15),
  mi        CHAR(1),
  address   VARCHAR(20),
  city      VARCHAR(20),
  state     CHAR(2),
  telephone CHAR(10),
  email     VARCHAR(40),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS Quiz;

CREATE TABLE Quiz (
  questionId INT,
  question   VARCHAR(4000),
  choicea    VARCHAR(1000),
  choiceb    VARCHAR(1000),
  choicec    VARCHAR(1000),
  choiced    VARCHAR(1000),
  answer     VARCHAR(5)
);


DROP TABLE IF EXISTS Address;
/* For the example in the Web chapters in intro7e */
CREATE TABLE Address (
  firstname VARCHAR(25),
  mi        CHAR(1),
  lastname  VARCHAR(25),
  street    VARCHAR(40),
  city      VARCHAR(20),
  state     VARCHAR(2),
  zip       VARCHAR(5),
  telephone VARCHAR(10),
  email     VARCHAR(30),
  PRIMARY KEY (firstname, mi, lastname)
);

/* For the example in the advanced database chatper in intro7e. Use MySQL version 5*/
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS StateCapital;

CREATE TABLE Person (
  firstName VARCHAR(20),
  mi        CHAR(1),
  lastName  VARCHAR(20)
);

/* For the example in the advanced database chatper. Use MySQL version 5*/
CREATE TABLE StateCapital (
  state   VARCHAR(40),
  capital VARCHAR(40)
);

INSERT INTO StateCapital VALUES (
  'Georgia', 'Atlanta');
INSERT INTO StateCapital VALUES (
  'Texas', 'Austin');
INSERT INTO StateCapital VALUES (
  'Indiana', 'Indianapolis');
INSERT INTO StateCapital VALUES (
  'Illlinois', 'Springfield');
INSERT INTO StateCapital VALUES (
  'California', 'Sacramento');
/*
insert into StateCapital values (
  'Alabama', 'Montgomery');
insert into StateCapital values (
  'Alaska', 'Juneau');
insert into StateCapital values (
  'Arizona', 'Phoenix');
*/

DROP TABLE IF EXISTS Country;

CREATE TABLE Country (
  name        VARCHAR(30),
  flag        BLOB,
  description VARCHAR(255)
);


DROP TABLE IF EXISTS Student1;

CREATE TABLE Student1 (
  username VARCHAR(50)  NOT NULL,
  password VARCHAR(50)  NOT NULL,
  fullname VARCHAR(200) NOT NULL,
  CONSTRAINT pkStudent PRIMARY KEY (username)
);

INSERT INTO Student1 VALUES ('abc1', 'abc', 'John  F Smith');
INSERT INTO Student1 VALUES ('abc2', 'abc', 'Peter R  Smith');
INSERT INTO Student1 VALUES ('abc3', 'abc', 'Jane    Paul');
INSERT INTO Student1 VALUES ('abc4', 'abc', 'George   King');


DROP TABLE IF EXISTS Student2;

CREATE TABLE Student2 (
  username  VARCHAR(50) NOT NULL,
  password  VARCHAR(50) NOT NULL,
  firstname VARCHAR(100),
  lastname  VARCHAR(100),
  CONSTRAINT pkStudent PRIMARY KEY (username)
);


/* For the exercise in the advanced database chatper. Use MySQL version 5*/
DROP TABLE IF EXISTS Temp;
CREATE TABLE Temp (
  num1 DOUBLE,
  num2 DOUBLE,
  num3 DOUBLE
);

/* For the callable statement example. Use MySQL version 5 */
DROP FUNCTION IF EXISTS studentFound;

DELIMITER //

CREATE FUNCTION studentFound(first VARCHAR(20), last VARCHAR(20))
  RETURNS INT
  BEGIN
    DECLARE result INT;

    SELECT count(*)
    INTO result
    FROM Student
    WHERE Student.firstName = first AND
          Student.lastName = last;

    RETURN result;
  END;
//

DELIMITER ;  /* Please note that there is a space between delimiter and ; */


/* For the example in the RMI and Web service */
DROP TABLE IF EXISTS Scores;
CREATE TABLE Scores (
  name       VARCHAR(20),
  score      DOUBLE,
  permission DOUBLE
);

INSERT INTO Scores VALUES ('John', 90.5, 1);
INSERT INTO Scores VALUES ('Michael', 100, 1);
INSERT INTO Scores VALUES ('Michelle', 100, 0);

COMMIT;
