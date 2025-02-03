import java.io.*;
import java.util.Scanner;
public class Niveau {
    private final int nombreDeLignesNiveauDePlusQuePlateau = 2;
    private boolean niveauValide = true;
    private Piece[] pieces;
    private Plateau plateau;
    private String nomFichier;
    private int compteurPieces;
    private int compteurPlateau;
    private String[] tableau;

    public Niveau(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public boolean isNiveauValide() {
        return niveauValide;
    }

    /**
     * Méthode qui compte le nombre de pièces et de plateau contenu dans un niveau.
     */
    public void compterNombreDePiecesEtPlateauFichier() {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(nomFichier))) {
            String ligne = lecteur.readLine();
            while (ligne != null) {
                if (!ligne.equals("")) { // si la ligne n'est pas vide, on la lit
                    if (ligne.charAt(0) == 'P' || ligne.charAt(0) == 'p') {
                        compteurPieces++;
                    } else if (ligne.charAt(0) == 'g' || ligne.charAt(0) == 'G') {
                        compteurPlateau++;
                    } else if (ligne.charAt(0) != 'P' || ligne.charAt(0) != 'p' || ligne.charAt(0) != 'g' || ligne.charAt(0) != 'G') {
                        // Si le premier caractère d'une ligne n'est pas P/p ou G/g, le niveau est invalide.
                        System.err.println("Erreur dans le type d'objet: " + ligne);
                        niveauValide = false;
                    }
                }
                ligne = lecteur.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier " + nomFichier + " n'existe pas!");
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier " + nomFichier);
        }
    }


    /**
     * Cette méthode initialise le tableau de pièces et le plateau du niveau en lisant chaque ligne du fichier.
     * Elle vérifie aussi que le niveau est valide.
     */
    public void creerTableauDePiecesEtLePlateau() {
        pieces = new Piece[compteurPieces];
        char lettre = 'Z';
        int compteurPieces = 0;
        try (BufferedReader lecteur = new BufferedReader(new FileReader(nomFichier))) {
            String chaine = lecteur.readLine();
            while (chaine != null) {
                if (!chaine.equals("")) { // si la chaine n'est pas vide, on la lit
                    String[] tableau = chaine.split("\\|");
                    if (tableau.length == 3) {
                        // Puisqu'il devrait y avoir 2 barres dans une ligne, son tableau devrait être de taille 3
                        int ligne = Integer.parseInt(String.valueOf(tableau[1].charAt(0)));
                        int colonne = Integer.parseInt(String.valueOf(tableau[1].charAt(2)));
                        if (tableau[1].length() == 3 && tableau[1].charAt(1) == ',' && ligne > 0 && colonne > 0 && tableau[0].length() == 1) {
                            // On initialise le tableau de pièces et le plateau seulement si les conditions ci-haut sont respectées.
                            // Dans le cas contraire, le niveau est invalide
                            if (tableau[0].equals("P") || tableau[0].equals("p")) { // on crée une pièce
                                pieces[compteurPieces] = new Piece(ligne, colonne, tableau[2], lettre, false);
                                compteurPieces++;
                                lettre -= 1;
                            } else { // on crée le plateau
                                plateau = new Plateau(ligne, colonne, tableau[2]);
                            }
                        } else {
                            System.err.println("Erreur dans le niveau, c'est-à-dire dans le type d'objet et/ou les dimensions de l'objet: " + chaine);
                            niveauValide = false;
                        }
                    } else {
                        System.err.println("Erreur dans le niveau. Une ou des lignes du fichier n'est pas séparée en 2 barres: " + chaine);
                        niveauValide = false;
                    }
                }
                chaine = lecteur.readLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier " + nomFichier + " n'existe pas!");
            niveauValide = false;
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier " + nomFichier);
            niveauValide = false;
        } catch (NumberFormatException e) {
            System.err.println("Erreur dans les dimensions");
            niveauValide = false;
        }
    }


    /**
     * Cette méthode calcule le nombre de 1 contenues dans la construction de toutes les pièces du tableau de pièces.
     * Elle vérifie aussi la construction de chaque pièce.
     *
     * @return le nombre de 1 contenues dans la construction des pièces confondues
     */
    public int verifierTableauDePiècesEtCompterPartiesSolides() {
        int nbre1Piece = 0;
        for (Piece piece : pieces) { /* on vérifie que le nombre de colonnes * lignes de la matrice de chaque pièce est égale
            à la taille du String de construction d'une pièce*/
            if (piece.getColonne() * piece.getLigne() != piece.getConstruction().length()) {
                System.err.println("Erreur dans la construction d'une pièce.\n" +
                                "Ligne: " + piece.getLigne() + "\nColonne: " + piece.getColonne() +
                        "\nConstruction: " + piece.getConstruction());
                niveauValide = false;
            }
            for (int j = 0; j < piece.getConstruction().length(); j++) { // on compte le nombre de places solides occupées par les pièces
                if (piece.getConstruction().charAt(j) == '1') {
                    nbre1Piece++;
                } else if (piece.getConstruction().charAt(j) != '0') {
                    // si la construction contient une donnée qui n'est pas 1 ou 0, le niveau est invalide
                    System.err.println("Erreur dans la construction d'une pièce, qui n'est pas composée que de 1 et de 0." +
                            "\nConstruction: " + piece.getConstruction() + "\nDonnée erronée: " +piece.getConstruction().charAt(j));
                    niveauValide = false;
                }
            }
        }
        return nbre1Piece;
    }


    /**
     * Cette méthode vérifie le nombre de 0 (places vides) contenues dans la construction de mon plateau
     *
     * @return le nombre de 0 contenues dans la construction de mon plateau
     */
    public int verifierPlateauEtCompterPartiesVides() {
        int nbre0Plateau = 0;
        for (int j = 0; j < plateau.getConstruction().length(); j++) {
            if (plateau.getConstruction().charAt(j) == '0') {
                nbre0Plateau++;

            } else if (plateau.getConstruction().charAt(j) != '1') {
                // si la construction contient une donnée qui n'est pas 1 ou 0, le niveau est invalide
                System.err.println("Erreur la construction du plateau, qui n'est pas composée que de 1 et de 0."+
                        "\nConstruction: " + plateau.getConstruction() + "\nDonnée erronée: " +plateau.getConstruction().charAt(j));
                niveauValide = false;
            }
        }
        return nbre0Plateau;
    }


    /**
     * Grâce aux méthodes présentées en haut, on vérifie dans la méthode actuelle que le niveau est bien valide.
     */
    public void verifierNiveauEstValide() {
        compterNombreDePiecesEtPlateauFichier();
        if (compteurPieces >= 1 && compteurPlateau == 1) {
            // s'il y a au moins une pièce et un seul plateau, on crée le tableau de pièces et le plateau du niveau.
            creerTableauDePiecesEtLePlateau();
        } else {
            System.err.println("Erreur dans le nombre de pièces ou de plateaux.");
            niveauValide = false;
        }
        if (niveauValide) {
            /* Si après avoir créé les attributs du niveau, le niveau est encore valide, on vérifie le contenu de
            la construction des pièces et du plateau.*/
            int nbre1Piece = verifierTableauDePiècesEtCompterPartiesSolides();
            int nbre0Plateau = verifierPlateauEtCompterPartiesVides();
            if (niveauValide) {/* Si après avoir vérifié le contenu, le niveau est encore valide, on vérifie qu'il y a
            autant d'espaces vides dans le plateau que d'espaces solides occupées par les pièces du tableau de pièces.*/
                if (nbre1Piece != nbre0Plateau) {
                    System.err.println("Erreur dans le niveau. Le nombre d'espaces vides dans le plateau ne correspond " +
                            "pas aux nombres de parties solides qu'occupent les pièces du tableau de pièces.");
                    niveauValide = false;
                } else if (plateau.getColonne() * plateau.getLigne() != plateau.getConstruction().length()) {
                    // vérifier que la taille de la matrice du plateau est égale à la taille de sa construction.
                    System.err.println("Erreur dans la construction du plateau.\n" +
                            "Ligne: " + plateau.getLigne() + "\nColonne: " + plateau.getColonne() +
                            "\nConstruction: " + plateau.getConstruction());
                    niveauValide = false;
                }
            }
        }
    }


    /**
     * Cette méthode remplit la première et dernière ligne du tableau de String de niveau
     *
     * @param compteur indique à quel niveau on est rendus
     */
    public void initialiserPremiereLigneEtDerniereLigneDeTableau(int compteur) {
        tableau[0] = "~~~ Montezuma+ ~~~ N" + compteur;
        tableau[tableau.length - 1] = "(! pour quitter)>>>";
    }


    /**
     * Cette méthode remplit la deuxième ligne du tableau de String de niveau avec la première ligne de la matrice du plateau
     */
    public void initiliaserDeuxiemeLigneMatrice() {
        tableau[1] = "";
        for (int colonne = 0; colonne < plateau.getMatrice()[0].length; colonne++) {
            tableau[1] += plateau.getMatrice()[0][colonne];
        }
    }


    /**
     * Cette méthode initialise la troisième ligne du tableau de String de niveau avec la deuxième ligne de la matrice du plateau
     */
    public void initiliaserTroisiemeLigneMatrice() {
        tableau[2] = "";
        for (int colonne = 0; colonne < plateau.getMatrice()[0].length; colonne++) {
            tableau[2] += plateau.getMatrice()[1][colonne];
        }
        tableau[2] += "  Pièces à placer:";
    }


    /**
     * Cette méthode initialise la troisième jusqu'à l'avant-dernière ligne du tableau de String de niveau avec la matrice
     * de plateau et la matrice de chaque pièce du tableau de pièces.
     */
    public void initiliaserTableauAvecMatricePlateauEtPieces() {
        for (int ligne = 3; ligne < tableau.length - 1; ligne++) {
            // on remplit chaque ligne avec une ligne de la matrice du plateau
            tableau[ligne] = "";
            for (int colonne = 0; colonne < plateau.getMatrice()[0].length; colonne++) {
                tableau[ligne] += plateau.getMatrice()[ligne - 1][colonne];
                // ligne matrice plateau = ligne tableau niveau - 1
            }

            for (Piece piece : pieces) {
                // on parcourt le tableau de pièces pour mettre une ligne de la matrice de chaque pièce dans la ligne du tableau de niveau
                piece.creerTableau(); // on crée la pièce
                if (ligne - 3 < piece.getMatrice().length) {
                    tableau[ligne] += "  ";
                    for (int k = 0; k < piece.getMatrice()[0].length; k++) {
                        if (!piece.isEstPlace()) { // si la pièce n'est pas placée, on la met dans les pièces à placer
                            tableau[ligne] += piece.getMatrice()[ligne - 3][k];
                            // la ligne de ma matrice pièces correspond à la ligne du String du tableau - 3
                        } else {
                            tableau[ligne] += " ";
                        }
                    }
                } else { // si on a dépassé le nombre de lignes qu'une matrice de pièce a, on remplit la ligne de niveau avec des espaces vides
                    tableau[ligne] += "  ";
                    for (int k = 0; k < piece.getMatrice()[0].length; k++) {
                        tableau[ligne] += " ";
                    }
                }
            }
        }
    }


    /**
     * On crée le tableau de String de niveau avec les méthodes créées en haut
     *
     * @param compteur indique à quel niveau on est rendus, pour remplir la première ligne du tableau de String
     */
    public void creerTableauJeu(int compteur) {
        verifierNiveauEstValide(); // on vérifie que le niveau est valide
        if (niveauValide) {
            plateau.creerMatrice(); // on construit la matrice de niveau seulement si celui-ci est valide
            tableau = new String[plateau.getMatrice().length + nombreDeLignesNiveauDePlusQuePlateau];
            initialiserPremiereLigneEtDerniereLigneDeTableau(compteur);
            initiliaserDeuxiemeLigneMatrice();
            initiliaserTroisiemeLigneMatrice();
            initiliaserTableauAvecMatricePlateauEtPieces();
        }
    }


    /**
     * Cette méthode affiche le tableau de niveau
     */
    public void afficherTableau() {
        for (String s : tableau) {
            System.out.println(s);
        }
    }


    /**
     * Cette méthode vérifie que, lorsque l'utilisateur rentre une commande pour placer ou retirer la pièce, la pièce
     * existe.
     *
     * @param reponse Le String de la commande entrée par l'utilisateur pour placer ou retirer une pièce
     * @return la pièce à placer ou retirer
     */
    public Piece verifierPieceAPlacerExiste(String reponse) {
        Piece piece;
        for (Piece value : pieces) {
            if (String.valueOf(value.getLettre()).equalsIgnoreCase(String.valueOf(reponse.charAt(0)))) {
                piece = value;
                return piece;
            }
        }
        return null;
    }


    /**
     * Méthode qui vérifie que toutes les pièces du tableau de pièces de niveau ont été placées.
     *
     * @return true si toutes les pièces ont été placées.
     */
    public boolean verifierPiecesPlacees() {
        for (Piece piece : pieces) {
            if (!piece.isEstPlace()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Cette méthode nous permet de vérifier, tout d'abord, que la pièce qu'il faut placer ou retirer existe.
     * Si elle existe, on vérifie la taille de la réponse de l'utilisateur. Si la taille de cette réponse est de 3,
     * on place la pièce. Si la taille est de 1, on l'enlève.
     *
     * @param reponse c'est la commande de l'utilisateur
     */
    public void decoderCode(String reponse) {
        Piece piece = verifierPieceAPlacerExiste(reponse);
        if (piece != null) {
            if (reponse.length() == 3) {
                plateau.placerPiece(piece, reponse);
            } else if (reponse.length() == 1) {
                plateau.retirerPiece(piece);
                // on réinitialise le contenu du tableau de niveau, après avoir modifié la matrice de plateau
            } else {
                System.err.println("La commande " + reponse + " ne peut pas être exécutée");
            }
            initiliaserTableauAvecMatricePlateauEtPieces(); // on reinitialise le contenu du tableau de niveau
        } else {
            System.err.println("La commande " + reponse + " ne peut pas être exécutée");
        }
    }


    /**
     * Cette méthode permet de placer ou retirer des pièces à partir d'un fichier de commande.
     * Le code a été inspiré du site : digitalOcean : https://www.digitalocean.com/community/tutorials/scanner-class-in-java
     *
     * @param nomFichierCommande le nom du fichier de commande.
     */
    public void lireFichierDeCommande(String nomFichierCommande) {  // site digitalOcean
        try {
            Scanner clavier = new Scanner(new File(nomFichierCommande));
            while (clavier.hasNextLine() && !verifierPiecesPlacees()) { // tant que les pièces n'ont pas toutes été placées.
                decoderCode(clavier.nextLine()); // méthode juste au-dessus de celle-ci

            }
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier " + nomFichierCommande + " n'existe pas!");
        }
    }
}
