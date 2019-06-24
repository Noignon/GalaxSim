# GalaxSim

Lien utiles :

* [Javadoc](https://noignon.github.io/GalaxSim/index.html)
* [Instructions de compilation](INSTALL.md)
* [Diagrammes UML](DESIGN.md)

GalaxSim est un logiciel qui permet de visualiser le mouvement des galaxies
qui nous entourent en simulant leurs déplacements.<br>
La visualisation est centrée sur notre galaxie

![Capture d'écran du logiciel](galaxsim.png)

## Contrôle de la caméra

La caméra de la simulation peut être déplacée afin de visualiser les éléments 
sous un autre angle de vue :

* Molette : contrôle du zoom
* Maintient du clique droit : déplacement latéral
* Maintient du clique molette : rotation de la caméra

## Filtrage des éléments

Le nombre d'amas et de galaxies peut être diminué à l'aide des filtres suivants. 
Il n'est pas nécessaire de les remplir : en cas de valeur vide, une valeur par défaut
pourra être appliquée dans certains cas (voir la description de chaque filtre). 

**Distance maximale** : par défaut, seuls les éléments situés à une distance inférieure à 100 MPC. Cette distance
peut être diminuée (mais pas augmentée) grâce au filtre de distance : celui-ci accepte
un nombre entier compris entre 1 et 100 inclus.

**Masse minimale** : chaque élément possède une masse, il est possible de les filtrer
en définissant une masse minimale. 
Ce filtre requiert un nombre entier supérieur à 0.


**Masquage de coordonnées** : en cliquant sur le menu déroulant, vous aurez accès à 9 champs
représentant les coordonnées minimales et maximales sur les axes X, Y et Z. Ce filtre permet
par exemple d'isoler les éléments sur un plan pour comparer les résultats avec des
graphiques en 2d.<br>
Aucun champ n'est obligatoire mais la valeur doit être un nombre entier compris entre
-100 et 100 inclus.

## Simulation

La partie simulation de l'application permet de visualiser le mouvement des galaxies...

La duréee de l'animation peut être définie à l'aide du champ situé sur la partie gauche
de l'application. La modification de la durée entrainera un changement dans la vitesse
de déplacement des objets.

Il est possible d'afficher des informations sur un amas ou une galaxie en cliquant
sur l'élément : celui-ci devrait apparaitre avec une couleur bleue et une fenêtre
s'ouvrira sur la partie droite de l'application.

## Ressources

* Icones venant du pack [Material Design](https://material.io/tools/icons/?style=baseline) de Google