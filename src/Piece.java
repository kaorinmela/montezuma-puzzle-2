public class Piece {
    private int ligne;
    private int colonne;
    private String construction;
    private char lettre;
    private boolean estPlace;
    private String[][] matrice;
    public Piece(int ligne, int colonne, String construction, char lettre, boolean estPlace) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.construction = construction;
        this.lettre = lettre;
        this.estPlace = estPlace;
    }
    public int getColonne() {
        return colonne;
    }
    public int getLigne() {
        return ligne;
    }
    public String[][] getMatrice() {
        return matrice;
    }
    public char getLettre() {
        return lettre;
    }
    public void setEstPlace(boolean estPlace) {
        this.estPlace = estPlace;
    }
    public boolean isEstPlace() {
        return estPlace;
    }
    public String getConstruction() {
        return construction;
    }


    /**
     * Méthode qui crée la matrice d'une pièce à partir du String de sa construction, et du nombre de lignes et colonnes de la matrice
     */
    public void creerTableau() {
        matrice = new String[ligne][colonne];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                if (construction.charAt(i * matrice[i].length + j) == '0') {
                    matrice[i][j] = "░";
                } else {
                    matrice[i][j] = String.valueOf(lettre);
                }
            }
        }
    }
}

