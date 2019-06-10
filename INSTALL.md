# Guide d'installation du logiciel GalaxSim

Une version compilée du logiciel est disponible [ici](https://github.com/Noignon/GalaxSim/releases).
Si vous souhaitez le compiler vous-même, merci de suivre les recommendations
de la section [Compilation][partie_compilation]

## Utilisation du logiciel

Après avoir lancé le logiciel, vous devez charger un jeu de données en cliquant sur le bouton
"Parcourir". Vous avez ensuite la possibilité de définir des filtres dont la description est
disponible dans le fichier [README.md](README.md).<br>

En cliquant sur le bouton "Valider", vous lancerez l'extraction des éléments contenus dans
le fichier sélectionné puis le calcul des coordonnées. Une barre de chargement apparaîtra sur
en bas de la fenêtre pour vous indiquer l'avancement des calculs.

Une fois le chargement terminé, vous devriez voir apparaitre des sphères dans un cube.
Les sphères vertes représentent les amas, les sphères rouges représentent les galaxies et le cube
représente l'univers. Selon les valeurs données à chaque filtre, il peut ne pas y avoir d'éléments
à afficher : un message apparaîtra sur la partie gauche de l'écran pour indiquer le nombre
d'éléments trouvés.

Vous pouvez lancer l'animation en cliquant sur le bouton de lecture. La vitesse de l'animation
dépend de la durée : par défaut elle dure 30 secondes. Vous pouvez la modifier en
mettant un nombre entier (représentant des secondes) strictement supérieur à 0.

[partie_compilation](#compilation)

## Compilation du projet

### Logiciels requis

Nous conseillons d'utiliser une version récente de Java ainsi que de JavaFX pour ne pas
avoir de problèmes de compatibilité. Il est toute fois possible de compiler le logiciel
avec une version antérieure mais nous ne guarantissons pas le bon fonctionnement du programme.

* Java JDK 11 ou supérieure
* [JavaFX 11](https://openjfx.io/) ou supérieure
* Librairie [FXyz](https://github.com/FXyz/FXyz) (donne accès à des fonctionnalités supplémentaires pour JavaFX)

La version compilée de cette librairie est fournie avec les sources du logiciel, il suffit juste
d'importer le fichier jar avant la compilation.

### Ajout de JavaFX à la compilation

Il faut ajouter les options suivantes à la configuration de la machine virtuelle java :
```
--module-path <chemin_vers_le_dossier_lib_de_javafx>
--add-modules javafx.controls,javafx.graphics,javafx.fxml
```

Voir [ce tutoriel](https://openjfx.io/openjfx-docs/) pour plus d'informations

### Jeux de données

Une archive contenant deux jeux de données peut être téléchargée ici: [CosmicFlow3.zip](https://www.dropbox.com/s/yfya6ou8jk2z8fg/CosmicFlow3.zip?dl=1).
Un fichier ReadMe (en anglais) présent dans l'archive explique en détail le contenu de chaque table.

* *table2.dat* : contient les principaux amas ainsi que des galaxies individuelles
* *table3.dat* : contient uniquement les galaxies individuelles