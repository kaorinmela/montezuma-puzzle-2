package ca.qc.bdeb.sim202;
/**
 * But : Écrire un programme qui permet à l'utilisateur de résoudre des casse-têtes.
 *
 * @author Wissam Abchiche
 * @since 20230317
 */


import java.util.Scanner;

public class Main {
    public static final int nombreFichiers = 10;

    public static void main(String[] args) {
        executerJeu();
    }

    /**
     * La méthode ci-dessous détermine ce que le code doit exécuter selon la commande entrée par l'utilisateur
     *
     * @param niveau   Niveau où on est rendus.
     * @param commande Commande de l'utilisateur pour placer/retirer/quitter/fichierDeCommandes.
     */
    public static void decortiquerJeu(Niveau niveau, String commande) {
        if (commande.charAt(0) == '<') { // On lit le fichier de commande
            String nomFichierCommande = "cmd\\";
            for (int i = 1; i < commande.length(); i++) {
                nomFichierCommande += commande.charAt(i); // Le fichier de commande n'a pas de < dans son nom
            }
            niveau.lireFichierDeCommande(nomFichierCommande);
        } else if (commande.equals("!")) { // le joueur peut quitter durant la partie
            ecranAbandon();
            System.exit(1);
        } else { // On place ou retire une pièce
            niveau.decoderCode(commande);
        }
    }


    /**
     * Cette méthode exécute le jeu. À l'aide d'une boucle while, on va passer au niveau suivant une fois
     * toutes les pièces d'un niveau placées.
     */
    public static void executerJeu() {
        int compteur = 1;
        Niveau niveau = new Niveau("niveaux\\niveau1.txt"); // on initialise l'objet niveau au niveau 1.
        Scanner clavier = new Scanner(System.in);
        String reponse;
        ecranAffichage(); // on affiche l'écran personnalisé du début de jeu
        while (compteur <= nombreFichiers && niveau.isNiveauValide()) {
            /* tant qu'on n'a pas atteint le nombre maximum de fichiers dans le dossier niveaux et qu'il n'y a pas de niveaux
            erronés, on passe au niveau suivant du dossier une fois un niveau complété*/
            niveau.creerTableauJeu(compteur);
            if (niveau.isNiveauValide()) { // on exécute le jeu seulement si le niveau est valide
                niveau.afficherTableau();
                while (!niveau.verifierPiecesPlacees()) { /* tant que les pièces ne sont pas toutes placées, l'utilisateur
                    rentre des commandes*/
                    reponse = clavier.nextLine();
                    decortiquerJeu(niveau, reponse);
                    niveau.afficherTableau();
                }
                if (compteur < nombreFichiers) { // tant que le joueur n'est pas rendu au dernier niveau
                    System.out.println("Félicitations vous avez solutionné le niveau " + compteur);
                    System.out.println("Appuyez sur entrée pour passer au prochain niveau");
                    reponse = clavier.nextLine();
                    if (!reponse.equals("!")) {
                        compteur++;
                        niveau = new Niveau("niveaux\\niveau" + compteur + ".txt");
                    } else { // le joueur peut quitter après avoir gagné une partie
                        ecranAbandon();
                        System.exit(1);
                    }
                }
            }
        }
        ecranJeuGagne();

    }

    public static void ecranAffichage() {
        Scanner clavier = new Scanner(System.in);
        System.out.println("✧♡(◕‿◕✿)  ✧♡(◕‿◕✿)  ✧♡(◕‿◕✿)  ✧♡(◕‿◕✿)  ✧♡(◕‿◕✿)  ✧♡(◕‿◕✿)  ✧♡(◕‿◕✿) ");
        System.out.println(" ▄▄   ▄▄ ▄▄▄▄▄▄▄ ▄▄    ▄ ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄ ▄▄   ▄▄ ▄▄   ▄▄ ▄▄▄▄▄▄▄ \n" +
                "█  █▄█  █       █  █  █ █       █       █       █  █ █  █  █▄█  █       █\n" +
                "█       █   ▄   █   █▄█ █▄     ▄█    ▄▄▄█▄▄▄▄   █  █ █  █       █   ▄   █\n" +
                "█       █  █ █  █       █ █   █ █   █▄▄▄ ▄▄▄▄█  █  █▄█  █       █  █▄█  █\n" +
                "█       █  █▄█  █  ▄    █ █   █ █    ▄▄▄█ ▄▄▄▄▄▄█       █       █       █\n" +
                "█ ██▄██ █       █ █ █   █ █   █ █   █▄▄▄█ █▄▄▄▄▄█       █ ██▄██ █   ▄   █\n" +
                "█▄█   █▄█▄▄▄▄▄▄▄█▄█  █▄▄█ █▄▄▄█ █▄▄▄▄▄▄▄█▄▄▄▄▄▄▄█▄▄▄▄▄▄▄█▄█   █▄█▄▄█ █▄▄█\n");
        System.out.println("@ 2023 Abchiche Wissam ෴ლ～⊰ +Montezuma+ ෴ლ～⊰");
        System.out.println("Appuyez sur entrée pour lancer le jeu!");
        clavier.nextLine();
    }

    public static void ecranAbandon() {
        System.out.println("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄\n" +
                "██░██░█░▄▄▀█░▄▄███▀▄▄▀█░▄▄▀█▀▄▄▀█▀▄▀█░████░▄▄▀██▄██░▄▄▀█░▄▄███░▄▄█▀▄▄▀██▄██░▄▄███▀▄▄▀█░▄▄█░██░█▄░▄████░▄▄█▄░▄█░▄▄▀█░▄▄\n" +
                "██░██░█░██░█░▄▄███░▀▀░█░▀▀▄█░██░█░█▀█░▄▄░█░▀▀░██░▄█░██░█░▄▄███░▄██░██░██░▄█▄▄▀███░▀▀░█░▄▄█░██░██░██▄▄█░▄▄██░██░▀▀▄█░▄▄\n" +
                "██▄▀▀▄█▄██▄█▄▄▄███░████▄█▄▄██▄▄███▄██▄██▄█▄██▄█▄▄▄█▄██▄█▄▄▄███▄████▄▄██▄▄▄█▄▄▄███░████▄▄▄██▄▄▄██▄█████▄▄▄██▄██▄█▄▄█▄▄▄\n" +
                "▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀\n");
    }

    public static void ecranJeuGagne() {
        System.out.println(" ________________________________________________________ \n" +
                "|  ____________________________________________________  |\n" +
                "| | ✩░▒▓▆▅▃▂▁Bravo! Vous avez complété le jeu▁▂▃▅▆▓▒░✩ | |\n" +
                "| |____________________________________________________| |\n" +
                "|________________________________________________________|");
    }
}


