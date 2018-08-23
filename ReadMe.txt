   _____ _                       _                           _ 
  / ____| |                     | |                         | |
 | (___ | |_ ___  _ __ _ __ ___ | |__   ___  _   _ _ __   __| |
  \___ \| __/ _ \| '__| '_ ` _ \| '_ \ / _ \| | | | '_ \ / _` |
  ____) | || (_) | |  | | | | | | |_) | (_) | |_| | | | | (_| |
 |_____/ \__\___/|_|  |_| |_| |_|_.__/ \___/ \__,_|_| |_|\__,_|

                       ╔════════════════════════╗                    
╔══════════════════════╣ TABLE DES MATIERES     ╠══════════════════════╗
║                      ╚════════════════════════╝                      ║
║                                                                      ║
║ I.    LANCER LE PROGRAMME                                            ║                                                                     
║ II.   ORGANISATION DU PROGRAMME                                      ║
║ III.  CHOIX TECHNIQUES                                               ║
║ IV.   PROBLEMES RENCONTRES                                           ║
║ V.    INFORMATIONS UITLES                                            ║
║                                                                      ║
╚══════════════════════════════════════════════════════════════════════╝

                       ╔════════════════════════╗                    
╔══════════════════════╣ LANCER LE PROGRAMME    ╠══════════════════════╗
║                      ╚════════════════════════╝                      ║
║                                                                      ║
║ Pour lancer le programme il faut executer le fichier controller      ║
║ Chemin : bin/fr/iut/zen/clickygame/SimpleGameController.class        ║
║ Ou alors utiliser eclipse en selectionnant le dossier comme projet   ║
║                                                                      ║
╚══════════════════════════════════════════════════════════════════════╝

                     ╔════════════════════════════╗                    
╔════════════════════╣ ORGANISATION DU PROGRAMME  ╠════════════════════╗
║                    ╚════════════════════════════╝                    ║
║                                                                      ║
║ Le code source du jeu se trouve dans le dossier src                  ║
║ Le projet étant orienter objet l'organisation de celui-ci possède    ║
║ un package board avec la gestion du jeu (builder, collection,...)    ║
║ un package cards pour la gestion des cartes du jeu                   ║
║ un package pour les enumeration, exceptions et les tests             ║
║ et enfin un le package mvc afin de gerer le jeu en lui meme          ║
║                                                                      ║
╚══════════════════════════════════════════════════════════════════════╝

                     ╔════════════════════════════╗                    
╔════════════════════╣ CHOIX TECHNIQUES           ╠════════════════════╗
║                    ╚════════════════════════════╝                    ║
║                                                                      ║
║ Afin d'exploiter au maximum la programmation orientée objet le       ║
║ projet est découpe en plusieurs package. Notamment dans le package   ║
║ cards avec des interfaces mais egalement une classe abstraite pour   ║ 
║ la factorisation de code tout en s'aidant aussi du polymorphisme     ║
║ Quant au corps du programme, celui-ci est concu en pattern mvc       ║
║ afin de separer tout ce qui est vue du programme, donnee et          ║
║ et gestion du programme                                              ║
║                                                                      ║ 
║ Avant le lancement du jeu, une vérification est fait par rapport au  ║
║ fichier tels que les decks ou encore les cartes gagnés. Si le        ║
║ dossier n'est pas trouvé, le jeu est réinitialisé à zéro.            ║
║                                                                      ║
╚══════════════════════════════════════════════════════════════════════╝

                     ╔════════════════════════════╗                    
╔════════════════════╣ PROBLEMES RENCONTRES       ╠════════════════════╗
║                    ╚════════════════════════════╝                    ║
║                                                                      ║
║ Tout d'abord il y a eu le fait qu'il fallait s'adapter au code       ║
║ deja realiser pour utiliser l interface graphique.                   ║
║                                                                      ║
║ Il a fallu réfléchir à comment bien adapter le code pour être le     ║
║ efficace dans l'implémentation des bonus.                            ║
║                                                                      ║
║                                                                      ║
╚══════════════════════════════════════════════════════════════════════╝

                     ╔════════════════════════════╗                    
╔════════════════════╣ INFORMATIONS UTILISES      ╠════════════════════╗
║                    ╚════════════════════════════╝                    ║
║                                                                      ║
║ Pour quitter le jeu :                                                ║
║ -Quand voud êtes sur le menu, appuyez sur le bouton "Quitter"        ║
║ -Quand vous créez un deck, veuillez d'abord cliquer sur "Retour"     ║
║ -Quand vous choississez un deck avant la partie cliquez sur          ║
║   la touche "Q"                                                      ║
║ -Quand vous êtes en train de jouer, de même appuyez sur la           ║
║   touche "Q" pour quitter le jeu                                     ║
║                                                                      ║
║ Quand la partie est finie :                                          ║
║    Si vous avez gagnez ou l'ordinateur a gagné, veuillez appuyer     ║ 
║    sur la touche "Q". Vous serez ramenés au menu.                    ║
║                                                                      ║
║Pour remettre le jeu à 0 :                                            ║
║  	 Si vous voulez tout recommencer, supprimez le dossier             ║
║    "ressources".                                                     ║
║                                                                      ║
║                                                                      ║
╚══════════════════════════════════════════════════════════════════════╝