# HNBATCH
ceci est un tuto sur un retour d'expériences sur des traitements en lots différées. En utilisant queleques stack technique.

# Presentation
Dans cette architecture, nous utilisons Spring Batch pour partitionner les données en plusieurs lots pour un traitement plus efficace. 
Ces lots sont ensuite envoyés à Spring Integration, qui les achemine vers Apache Kafka pour un traitement.
 Cette approche permet de gérer de grandes quantités de données de manière efficace et scalable.
# Prerequis
Spring Batch
Spring Integration
Apache Kafka
Docker-Compose
Mysql Database
Liquibase
Lombock
Java 17
# Programme
## Master
Pour exécuter le master: -Dspring.profiles.active=master
## Woker
Pour exécuter le woker: -Dspring.profiles.active=woker
On lance plusieurs fois le woker via differents invite de commande.
## Docker
Pour lancer le docker compose afin d'avoir zookeeper, kafka et mysql: 
  * docker-compose up -d
  * docker-compose down pour arreter les containers
Une fois les containers sont up on peux lancer la commande suivant:
  * mysql -uroot -proot
  * show databases ;
  * use db ;
  * show tables ;
  * select * from TRANSACTDTO ;

 
