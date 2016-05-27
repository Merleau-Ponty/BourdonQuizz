USE bourdonroad;
# -----------------------------------------------------------------------------
#       TABLE : JOUEUR
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS JOUEUR
 (
   ID_JOUEUR INTEGER(3) NOT NULL AUTO_INCREMENT ,
   NOM VARCHAR(128) NULL  ,
   PRENOM VARCHAR(128) NULL  ,
   TELEPHONE VARCHAR(128) NULL  ,
   EMAIL VARCHAR(128) NULL  ,
   SERVICE VARCHAR(128) NULL  ,
   LOGIN VARCHAR(128) NULL  ,
   MOT_DE_PASSE VARCHAR(128) NULL  ,
   TYPE_UTILISATEUR VARCHAR(128) NULL  
   , PRIMARY KEY (ID_JOUEUR) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : PROPOSITION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS PROPOSITION
 (
   ID_QUESTION INTEGER(3) NOT NULL  ,
   ID_PROP INTEGER(2) NOT NULL  ,
   ENONCE VARCHAR(128) NULL  ,
   VALIDE BOOL NULL  
   , PRIMARY KEY (ID_QUESTION,ID_PROP) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : TENTATIVE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS TENTATIVE
 (
   ID_JOUEUR INTEGER(3) NOT NULL  ,
   ID_TENTATIVE INTEGER(2) NOT NULL ,
   ID_QUIZZ INTEGER(2) NOT NULL  ,
   DATE_TENTATIVE DATETIME NULL  ,
   SCORE INTEGER(2) NULL  
   , PRIMARY KEY (ID_JOUEUR,ID_TENTATIVE) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : QUIZZ
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS QUIZZ
 (
   ID_QUIZZ INTEGER(2) NOT NULL AUTO_INCREMENT 
   , PRIMARY KEY (ID_QUIZZ) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : QUESTION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS QUESTION
 (
   ID_QUESTION INTEGER(3) NOT NULL AUTO_INCREMENT ,
   ENONCE VARCHAR(128) NULL  ,
   PHOTO VARCHAR(128) NULL  ,
   CORRIGE VARCHAR(128) NULL  
   , PRIMARY KEY (ID_QUESTION) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : REPONSE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS REPONSE
 (
   ID_REP INTEGER(2) NOT NULL AUTO_INCREMENT ,
   ID_QUESTION INTEGER(3) NOT NULL  ,
   ID_JOUEUR INTEGER(3) NOT NULL  ,
   ID_TENTATIVE INTEGER(2) NOT NULL  ,
   VALEUR BOOL NULL  
   , PRIMARY KEY (ID_REP) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : CONTENIR
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS CONTENIR
 (
   ID_QUESTION INTEGER(3) NOT NULL  ,
   ID_QUIZZ INTEGER(2) NOT NULL  
   , PRIMARY KEY (ID_QUESTION,ID_QUIZZ) 
 ) 
 comment = "";


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE PROPOSITION 
  ADD FOREIGN KEY FK_PROPOSITION_QUESTION (ID_QUESTION)
      REFERENCES QUESTION (ID_QUESTION) ;


ALTER TABLE TENTATIVE 
  ADD FOREIGN KEY FK_TENTATIVE_JOUEUR (ID_JOUEUR)
      REFERENCES JOUEUR (ID_JOUEUR) ;


ALTER TABLE TENTATIVE 
  ADD FOREIGN KEY FK_TENTATIVE_QUIZZ (ID_QUIZZ)
      REFERENCES QUIZZ (ID_QUIZZ) ;


ALTER TABLE REPONSE 
  ADD FOREIGN KEY FK_REPONSE_QUESTION (ID_QUESTION)
      REFERENCES QUESTION (ID_QUESTION) ;


ALTER TABLE REPONSE 
  ADD FOREIGN KEY FK_REPONSE_TENTATIVE (ID_JOUEUR,ID_TENTATIVE)
      REFERENCES TENTATIVE (ID_JOUEUR,ID_TENTATIVE) ;


ALTER TABLE CONTENIR 
  ADD FOREIGN KEY FK_CONTENIR_QUESTION (ID_QUESTION)
      REFERENCES QUESTION (ID_QUESTION) ;


ALTER TABLE CONTENIR 
  ADD FOREIGN KEY FK_CONTENIR_QUIZZ (ID_QUIZZ)
      REFERENCES QUIZZ (ID_QUIZZ) ;

