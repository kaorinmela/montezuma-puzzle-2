package ca.qc.bdeb.sim202;

public class Plateau {
    private final int contourMatrice = 3;
    private int ligne;
    private int colonne;
    private String construction;
    private String[][] matrice;

    public Plateau(int nbreLignesTableau, int nbreColonnesTableau, String construction) {
        this.ligne = nbreLignesTableau;
        this.colonne = nbreColonnesTableau;
        this.construction = construction;
    }

    public String[][] getMatrice() {
        return matrice;
    }

    public int getColonne() {
        return colonne;
    }

    public int getLigne() {
        return ligne;
    }

    public String getConstruction() {
        return construction;
    }


    /**
     * Méthode qui crée la matrice de plateau à partir de son nombre de colonnes, de lignes et de sa construction.
     */
    public void creerMatrice() {
        int compteur2 = 0;
        int compteur = 1;
        char lettre = 'A';

        // Pour avoir un encadrement autour de mon plateau, il faut ajouter 3 lignes et colonnes de plus qu'à l'origine.
        matrice = new String[ligne + contourMatrice][colonne + contourMatrice];

        // Ce for initialise la première de la matrice du plateau (avec des lettres).
        for (int i = 0; i < matrice[0].length; i++) {
            if (i < 2 || i == matrice[0].length - 1) {
                // Puisqu'il y a un contour, on met des espaces vides aux positions correspondantes aux contours
                matrice[0][i] = " ";
            } else {
                // on nomme alors les colonnes de la matrice par des lettres
                matrice[0][i] = String.valueOf(lettre);
                lettre += 1;
            }
        }

        // Ce for initialise la deuxième et la dernière ligne de la matrice du plateau
        for (int i = 0; i < matrice[0].length; i++) {
            if (i == 0) { // puisqu'il y a une colonne (0) consacrée aux noms des lignes, on ne remplit pas cette colonne
                matrice[1][i] = " ";
                matrice[matrice.length - 1][i] = " ";
            } else {
                matrice[1][i] = "█";
                matrice[matrice.length - 1][i] = "█";
            }
        }

        // Ce for remplit le reste du tableau
        for (int i = 2; i < matrice.length - 1; i++) {
            // on ne remplit pas les lignes 0, 1,et la dernière, qu'on a initialisées juste en haut
            for (int j = 0; j < matrice[0].length; j++) {
                if (j == 0) { // la première colonne de la matrice du plateau numérote les lignes du plateau de jeu
                    matrice[i][j] = String.valueOf(compteur);
                    compteur++;
                } else if (j == 1 || j == matrice[0].length - 1) { // ces colonnes correspondent aux contours du plateau
                    matrice[i][j] = "█";
                } else { // on remplit les cases de la matrice du plateau à l'aide de sa construction
                    if (String.valueOf(construction.charAt(compteur2)).equals("1")) {
                        matrice[i][j] = "█";
                    } else {
                        matrice[i][j] = " ";
                    }
                    compteur2++;
                }
            }
        }
    }


    /**
     * Cette méthode vérifie si la pièce à placer est plaçable, c'est-à-dire que la taille de la matrice de la pièce
     * ne dépasse pas les bornes de la matrice de plateau et qu'une partie solide de la pièce ne se mette pas sur un
     * obstacle ou sur une autre pièce. Si c'est le cas, la pièce n'est pas plaçable et donc, son boolean estPlace = false.
     *
     * @param piece      La pièce à placer
     * @param posLigne   Il s'agit de la ligne de la matrice de plateau ou l'utilisateur veut mettre le coin supérieur gauche de la pièce.
     * @param posColonne Il s'agit de la colonne de la matrice de plateau ou l'utilisateur veut mettre le coin supérieur gauche de la pièce.
     */
    public void verifierPieceEstPlacable(Piece piece, int posLigne, int posColonne) {
        piece.setEstPlace(true);
        for (int ligne = 0; ligne < piece.getMatrice().length; ligne++) {
            for (int colonne = 0; colonne < piece.getMatrice()[0].length; colonne++) {
                if ((posLigne) == 0 || (posColonne) == 0) {
                    piece.setEstPlace(false);
                } else if ((ligne + posLigne) > matrice.length ||
                        (colonne + posColonne > matrice[0].length)) {
                    piece.setEstPlace(false);
                } else if ((ligne + posLigne) < matrice.length &&
                        (colonne + posColonne < matrice[0].length)) {
                    if ((!piece.getMatrice()[ligne][colonne].equals("░")
                            && !matrice[ligne + posLigne][colonne + posColonne].equals(" "))) {
                        piece.setEstPlace(false);
                    }
                }
            }
        }
    }

    /**
     * Cette méthode remplit la matrice de plateau avec le contenu de la matrice de la pièce à placer
     *
     * @param piece      Pièce à placer
     * @param posLigne   Il s'agit de la ligne de la matrice de plateau ou l'utilisateur veut mettre le coin supérieur gauche de la pièce.
     * @param posColonne Il s'agit de la colonne de la matrice de plateau ou l'utilisateur veut mettre le coin supérieur gauche de la pièce.
     */
    public void remplirMatricePlateauAvecPieces(Piece piece, int posLigne, int posColonne) {
        for (int ligne = 0; ligne < piece.getMatrice().length; ligne++) {
            for (int colonne = 0; colonne < piece.getMatrice()[0].length; colonne++) {
                if (!piece.getMatrice()[ligne][colonne].equals("░")) {
                    // on peut mettre une position de la matrice de la pièce seulement si cette position est vide (ou = ░)
                    matrice[ligne + posLigne][colonne + posColonne] = piece.getMatrice()[ligne][colonne];
                }
            }
        }
    }


    /**
     * Si l'utilisateur souhaite retirer une pièce du plateau, cette méthode sert à remplir les cases ou
     * il y avait la lettre de la pièce à retirer par des espaces vides.
     *
     * @param piece Pièce à retirer
     */
    public void remplirMatricePlateauAvecEspacesVides(Piece piece) {
        for (int ligne = 0; ligne < matrice.length; ligne++) {
            for (int colonne = 0; colonne < matrice[0].length; colonne++) {
                if (matrice[ligne][colonne].equals(String.valueOf(piece.getLettre()))) {
                    matrice[ligne][colonne] = " ";
                }
            }
        }
    }


    /**
     * Lorsque l'utilisateur entre un emplacement pour placer une pièce, il va écrire la lettre correspondant à la colonne ou il
     * veut mettre le coin supérieur gauche de la pièce. Cette méthode sert à transformer cette lettre en une position
     * de colonne de la matrice de plateau.
     *
     * @param commande commande donnée par l'utilisateur pour placer la pièce
     * @return la position de la colonne de la matrice de plateau ou l'utilisateur souhaite mettre le coin supérieur gauche de la pièce
     */
    public int transformerLettreEmplacementJoueurEnColonneMatrice(String commande) {
        int posColonne = 0;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                /* il faut que la commande à la position 1 (qui représente la lettre d'une colonne) soit
                vraiment égal au nom d'une colonne de la matrice de plateau*/
                if (matrice[i][j].equalsIgnoreCase(String.valueOf(commande.charAt(1)))) {
                    posColonne = j;
                }
            }
        }
        return posColonne;
    }


    /**
     * Lorsque l'utilisateur entre un emplacement pour placer une pièce, il va écrire le chiffre correspondant à la ligne ou il
     * veut mettre le coin supérieur gauche de la pièce. Cette méthode sert à transformer ce chiffre en
     * une position de la ligne de la matrice de plateau.
     *
     * @param commande commande donnée par l'utilisateur pour placer la pièce
     * @return la position de la ligne de la matrice de plateau ou l'utilisateur souhaite mettre le coin supérieur gauche de la pièce
     */
    public int transformerChiffreEmplacementJoueurEnLigneMatrice(String commande) {
        int posLigne = 0;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                 /* il faut que la commande à sa position 2 (qui représente le chiffre d'une ligne) soit
                vraiment égal au nom d'une ligne de la matrice de plateau*/
                if (matrice[i][j].equalsIgnoreCase(String.valueOf(commande.charAt(2)))) {
                    posLigne = i;
                }
            }
        }
        return posLigne;
    }


    /**
     * Cette méthode sert à placer la pièce à partir de l'emplacement donné par l'utilisateur.
     *
     * @param piece    Pièce à placer
     * @param commande commande donnée par l'utilisateur pour placer la pièce
     */
    public void placerPiece(Piece piece, String commande) {
        // si la pièce est déjà placée, on va d'abord la retirer
        if (piece.isEstPlace()) {
            retirerPiece(piece);
        }
        int posColonne = transformerLettreEmplacementJoueurEnColonneMatrice(commande);
        int posLigne = transformerChiffreEmplacementJoueurEnLigneMatrice(commande);
        // il faut vérifier que la pièce à placer est plaçable grâce à la méthode créée en haut.
        verifierPieceEstPlacable(piece, posLigne, posColonne);
        //Si la pièce est plaçable, on remplit la matrice de plateau avec la lettre de la pièce
        if (piece.isEstPlace()) {
            remplirMatricePlateauAvecPieces(piece, posLigne, posColonne);
        } else {
            System.err.println("La commande " + commande + " est impossible à exécuter");
        }
    }


    /**
     * Cette méthode sert à retirer une pièce. Elle utilise la méthode remplirTableauAvecVide vue en haut
     *
     * @param piece pièce à retirer
     */
    public void retirerPiece(Piece piece) {
        remplirMatricePlateauAvecEspacesVides(piece);
        piece.setEstPlace(false);
    }
}




