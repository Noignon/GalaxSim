# GalaxSim

Logiciel de simulation des mouvements des galaxies

## Compilation du projet

Il faut ajouter les options suivantes à la configuration de la machine virtuelle java :
```
--module-path <chemin_vers_le_dossier_lib_de_javafx>
--add-modules javafx.controls,javafx.graphics,javafx.fxml
```

Voir [ce tutoriel](https://openjfx.io/openjfx-docs/) pour plus d'informations

## Contrôle de la caméra

En mode caméra libre, celle-ci peut être déplacée à l'aide de la souris :

* Molette : zoom
* Maintient du clique droit : déplacement latéral de la vue sur l'axe X
* Maintient du clique molette : changement de l'angle de vue sur les axes X et Y

## Logiciels requis

* Java JDK 11 ou plus
* [JavaFX 11](https://openjfx.io/) ou plus
* Librairie [FXyz](https://github.com/FXyz/FXyz) (donne accès à des fonctionnalités supplémentaires pour JavaFX)

## Ressources

* Icones venant du pack [Material Design](https://material.io/tools/icons/?style=baseline) de Google