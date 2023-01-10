# HNBATCH
ce-ci est un récaputilatif sur un retour d'expériences sur des traitements de lots différés. En utilisant queleques stack techniques.
L'objectif est de comprendre comment utiliser les stacks techniques pour résoudre un probleme de performatence et de gestion des erreurs dans le traitement de lot en masses.
# Presentation
# Image 
![image](https://github.com/Diags/HNBATCH/blob/39d36c675693f8927a7a67dfd7f1c11369c2d0d2/src/main/resources/Capture.PNG)

 Dans cette architecture, nous utilisons Spring Batch pour partitionner les données en plusieurs lots pour un traitement plus efficace. 
Ces lots sont ensuite envoyés à Spring Integration, qui les achemine vers Apache Kafka pour un traitement en parrelle vers des wokers.
 Cette approche permet de gérer de grandes quantités de données de manière efficace et scalable.
# Prerequis
 * Spring Batch
 * Spring Integration
 * Apache Kafka
 * Docker-Compose
 * Mysql Database
 * Liquibase
 * Lombock
 * Java 17
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

 
