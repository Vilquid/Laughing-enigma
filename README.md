# Laughing-enigma

## Objectif

Réaliser une application (avec le langage Kotlin) permettant de consulter des articles de presses, avec comme source de donnée le site : https://newsapi.org.

## Architecture

L’architecture et de type MVC afin d’offrir une séparation entre l’interface graphique et l’algorithmie de l’application.

Le projet utilise Gradle.

L’interface graphique est faite en Swing.

## Fonctionalités

Différentes fonctionalités sont présentes :
- Lister les articles du moment dans une fenêtre
- La langue permet de trier les articles
- Afficher le détail d'un article dans une autre fenêtre
- Navigation par thème
- Le mode sombre est natif

L'export d'un article en PDF sera disponible dans la V2.0

## Lancement de l'application

1. Télécharger le .zip du projet
2. Télécharger IntelliJ IDEA Community ou Ultimate (https://www.jetbrains.com/fr-fr/idea/download/#section=windows)
3. Ouvrir IntelliJ IDEA
4. Cliquer su "Open"
5. Double-cliquer sur le fichier build.gradle.kts
6. Cliquer sur "Ouvrir en temps que projet"
7. Cliquer en haut à droite de la fenêtre sur "Run 'Appkt'"

La recherche d'article est un peu longue. Cependant, si au bout de 30 secondes rien ne s'affiche, alors il n'y a pas d'articles disponnibles ou la requête s'est mal exécutée et il faut rappuyer sur le bouton "Rechercher".