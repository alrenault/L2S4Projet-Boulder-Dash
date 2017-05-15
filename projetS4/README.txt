

12/05 -- IA Directive + Alexis/Master
- IA directive fonctionne
- Merge principalement du moteur (explosion, restructuration de la méthode tombe, IA directive)
- Rajout du mode DEBUG

------------------------------------------------------------------------------



28/04/2017 -- Version du jeu suffisante

- Mécaniques de Jeu :
  Les ennemis tuent, se déplacent, les rochers tombent, les diamants sont récupérables, murs magiques opérationnels, on peut mourrir ou gagner et accéder au niveau supérieur, etc.

  --> Il manque : les explosions, quelques détails pour les ennemis, ...

- Interface Graphique Opérationnelle, compteur de points, de diamants, ...

- IA :
1) Random - OK
2) Génétique - En cours
3) Directive - En cours
4) Directive Evoluée - Nom commencée
5) Parfaite - Non commencée




------------------------------------------------------------------------------






Merge du 18/04 -- Version en vigueur / Version d'Adrien

Ecrit/Lu par : Quentin
Non lu : Alexis, Adrien, (Nicolas ?)

Merge : 1

- Ajout des options et de MainTest_v2 :
>> Pour faire fonctionner MainTest_v2, il faut penser à rajouter des options !
>> Clic Droit sur Maintest_v2 -> Run As.. --> Run Configurations --> Modifier les arguments
-joue pour jouer
-name pour les noms
...

- Ajout du sujet en .pdf, du dossier ressources d'Adrien

Merge : 2
- Ajout de ce petit fichier pour faciliter la communication/les explications lors de commit, push..
Abusez-en si il y a quelque chose d'important à dire.

- MAJ du Menu d'Adrien (+ Coloré ; + Compteurs), ajout de Sortie2.png

Merge : 3
- Déplacement des ennemis implémentés : Fonctionne sans problème MAIS
1) MoteurJeu.espace (les cases vides) --> est maintenant public et static
2) Deplacable.deplacer() --> Deplacable.deplacer(Entite[][] carte)
Cela pourrait nous poser quelques problèmes, faites y attention

Merge : 4 
- Implémentation de la dernière version du package affichage

>>La version de chargerImage() pour le .jar ne fonctionne pas, j'ai repris l'ancienne en prenant le soin de garder celle qui ne marche pas bien

>> Découverte d'un bug : Certains diamants ne sont pas ramassés du premier coup
--> Problème de HashSet ?
--> Problème avec public static espace ?


----- Fin du Merge