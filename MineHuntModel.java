package s02;

import java.util.Random;

public class MineHuntModel implements IMineHuntModel {
	private boolean[][] terrain;
	private boolean[][] dejaClique;
	private boolean[][] flagged;
	private int nbClicks;
	private int nbErreurs;
	private int pourcentDeMines;
	private int nbMines;
	
	public MineHuntModel() {
		terrain = null;
		dejaClique = null;
		flagged = null;
		nbClicks = 0;
		nbErreurs = 0;
		this.pourcentDeMines = 0;
	}

	/**
	 * Initialise le tableau de mines
	 */
	@Override
	public void initialiser() {
		nbMines = 0;
		// On initialise que si le tableau existe
		if (terrain != null) {
			int ligIndex = getNbLines();
			int colIndex = getNbCol();
			// On parcourt toutes les cases une par une
			for (int i = 0; i < ligIndex; i++) {
				for (int j = 0; j < colIndex; j++) {
					placerMineAleatoirement(i, j);
				}
			}
			afficherTableau();
			System.out.println("Terrain initialisé");
		} else {
			System.out.println("Terrain non-initialisé, terrain = null");
		}
		System.out.println(nbMines + " mines présentes sur le terrain");
	}

	/**
	 * Crée un terrain 
	 * @param ligIndex
	 * @param colIndex
	 */
	public void setTerrain(int ligIndex, int colIndex) {
		if (colIndex <= 0 || ligIndex <= 0)
			throw new IllegalArgumentException("Le tableau doit contenir au moins une case !");
		terrain = new boolean[ligIndex][colIndex];
		System.out.print("Nouveau terrain créé");
	}

	/**
	 * Crée le tableau de cases dejaClique
	 * @param ligIndex
	 * @param colIndex
	 */
	public void setDejaClique(int ligIndex, int colIndex) {
		if (colIndex <= 0 || ligIndex <= 0)
			throw new IllegalArgumentException("Le tableau doit contenir au moins une case !");
		dejaClique = new boolean[ligIndex][colIndex];
	}

	/**
	 * Change la valeur d'une case dans le tableau dejaClique
	 * @param ligIndex
	 * @param colIndex
	 * @param dejaClique true/false
	 */
	public void setUneCaseDejaClique(int ligIndex, int colIndex, boolean dejaClique) {
		this.dejaClique[ligIndex][colIndex] = dejaClique;
	}

	/**
	 * Crée le tableau de cases avec drapeau
	 * @param ligIndex
	 * @param colIndex
	 */
	public void setFlagged(int ligIndex, int colIndex) {
		if (ligIndex <= 0 || colIndex <= 0)
			throw new IllegalArgumentException("Le tableau doit contenir au moins une case !");
		flagged = new boolean[ligIndex][colIndex];
	}

	/**
	 * Modifie une case dans le tableau flagged
	 * @param ligIndex
	 * @param colIndex
	 * @param flagged true/false
	 */
	public void setUneCaseFlagged(int ligIndex, int colIndex, boolean flagged) {
		this.flagged[ligIndex][colIndex] = flagged;
	}

	/**
	 * Modifie la variable nbClick
	 * @param nbClicks
	 */
	public void setNbClicks(int nbClicks) {
		if (nbClicks < 0)
			throw new IllegalArgumentException("Le nombre de clicks ne peut pas être négatif");
		this.nbClicks = nbClicks;
	}

	/**
	 * Modiife la variable nbErreurs
	 * @param nbErreurs
	 */
	public void setNbErreurs(int nbErreurs) {
		if (nbErreurs < 0)
			throw new IllegalArgumentException("Le nombre d'erreurs ne peut pas être négatif");
		this.nbErreurs = nbErreurs;
	}

	/**
	 * Modifie la variable pourcentMines
	 * @param pourcentage
	 */
	public void setPourcentMines(int pourcentage) {
		if (pourcentage < 0 || pourcentage > 100)
			throw new IllegalArgumentException("Le pourcentage doit être compris entre 1 et 100");
		pourcentDeMines = pourcentage;
	}

	/**
	 * Retourne le terrain de mines
	 * @return
	 */
	public boolean[][] getTerrain() {
		return terrain;
	}

	/**
	 * Retourne le nombre de lignes du terrain
	 * @return
	 */
	public int getNbLines() {
		return terrain.length;
	}

	/**
	 * Retourne le nombre de colonnes du terrain
	 * @return
	 */
	public int getNbCol() {
		return terrain[0].length;
	}

	/**
	 * Retourne le tableau de cases dejaClique
	 * @return
	 */
	public boolean[][] getDejaClique() {
		return dejaClique;
	}
	
	/**
	 * Retourne la valeur d'une case dans le tableau dejaClique
	 * @param ligIndex
	 * @param colIndex
	 * @return
	 */
	public boolean getUneCaseDejaClique(int ligIndex, int colIndex) {
		return dejaClique[ligIndex][colIndex];
	}

	/**
	 * Retourne la valeur d'une case dans le tableau flagged
	 * @param ligIndex
	 * @param colIndex
	 * @return
	 */
	public boolean getUneCaseFlagged(int ligIndex, int colIndex) {
		return flagged[ligIndex][colIndex];
	}

	/**
	 * Retourne le tableau 2D flagged
	 * @return
	 */
	public boolean[][] getFlagged() {
		return flagged;
	}

	/**
	 * Retourne le pourcentage de mines
	 * @return
	 */
	public int getPourcentMines() {
		return pourcentDeMines;
	}

	/**
	 * Retourne le nombre de clicks
	 * @return
	 */
	public int getNbClicks() {
		return nbClicks;
	}

	/**
	 * Retourne le nombre d'erreurs
	 * @return
	 */
	public int getNbErrors() {
		return nbErreurs;
	}

	/**
	 * Place (peut-être) une mine avev pourcentMine pourcent de chance
	 * @param i
	 * @param j
	 */
	private void placerMineAleatoirement(int i, int j) {
		Random r = new Random();
		int rand = r.nextInt(100);
		if (rand < pourcentDeMines) {
			terrain[i][j] = true;
			System.out.print("une mine en plus");
			nbMines++;
		}
	}

	/**
	 * Retourne true si la partie est terminée, sinon false
	 * @return true/false
	 */
	public boolean estTermine() {
		// Si le nombre de clicks = nombre de cases sans bombes
		// alors la partie est terminée
		return (nbClicks >= (terrain.length * terrain[0].length) - nbMines);

	}

	@Override
	public boolean estBordHaut(int i, int j) {
		return i == 0;
	}

	@Override
	public boolean estBordDroite(int i, int j) {
		return j == terrain[0].length - 1;
	}

	@Override
	public boolean estBordBas(int i, int j) {
		return i == terrain.length - 1;
	}

	@Override
	public boolean estBordGauche(int i, int j) {
		return j == 0;
	}

	@Override
	public boolean estCoinHautGauche(int i, int j) {
		return i == 0 && j == 0;
	}

	@Override
	public boolean estCoinHautDroite(int i, int j) {
		return i == 0 && j == terrain[0].length - 1;
	}

	@Override
	public boolean estCoinBasDroite(int i, int j) {
		return i == terrain.length - 1 && j == terrain[0].length - 1;
	}

	@Override
	public boolean estCoinBasGauche(int i, int j) {
		return i == terrain.length - 1 && j == 0;
	}

	/**
	 * Retourne un tableau contenant les index des cases autour de la case dont
	 * les index sont donnés en paramètre
	 * @param i
	 * @param j
	 * @return
	 */
	public int[][] casesAutour(int i, int j) {
		int[][] r = new int[8][2];
		// Initialisation avec des valeurs qu'on ne peut
		// pas trouver dans le champs de mine
		for (int k = 0; k < r.length; k++) {
			for (int l = 0; l < r[0].length; l++) {
				r[k][l] = -1;
			}
		}
		try { // haut gauche
			r[0][0] = i - 1;
			r[0][1] = j - 1;
		} catch (Exception e) {
			// Ne rien faire
		}
		try { // haut
			r[1][0] = i - 1;
			r[1][1] = j;
		} catch (Exception e) {
			// Ne rien faire
		}
		try { // haut droite
			r[2][0] = i - 1;
			r[2][1] = j + 1;
		} catch (Exception e) {
			// Ne rien faire
		}
		try { // droite
			r[3][0] = i;
			r[3][1] = j + 1;
		} catch (Exception e) {
			// Ne rien faire
		}
		try { // bas droite
			r[4][0] = i + 1;
			r[4][1] = j + 1;
		} catch (Exception e) {
			// Ne rien faire
		}
		try { // bas
			r[5][0] = i + 1;
			r[5][1] = j;
		} catch (Exception e) {
			// Ne rien faire
		}
		try { // bas gauche
			r[6][0] = i + 1;
			r[6][1] = j - 1;
		} catch (Exception e) {
			// Ne rien faire
		}
		try { // gauche
			r[7][0] = i;
			r[7][1] = j - 1;
		} catch (Exception e) {
			// Ne rien faire
		}
		return r;
	}

	@Override
	public boolean estUneMine(int i, int j) {
		return terrain[i][j];
	}

	/**
	 * Compte le nombre de mines autour de la mine dont l'index est donné en paramètre
	 */
	@Override
	public int nbMinesAutour(int i, int j) {
		int nbMine = 0;
		if (estCoinHautGauche(i, j)) {
			nbMine = estUneMine(i, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j) ? nbMine + 1 : nbMine;
		} else if (estCoinHautDroite(i, j)) {
			nbMine = estUneMine(i, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j) ? nbMine + 1 : nbMine;
		} else if (estCoinBasDroite(i, j)) {
			nbMine = estUneMine(i - 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j - 1) ? nbMine + 1 : nbMine;
		} else if (estCoinBasGauche(i, j)) {
			nbMine = estUneMine(i - 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j + 1) ? nbMine + 1 : nbMine;
		} else if (estBordHaut(i, j)) {
			nbMine = estUneMine(i, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j - 1) ? nbMine + 1 : nbMine;
		} else if (estBordDroite(i, j)) {
			nbMine = estUneMine(i, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j) ? nbMine + 1 : nbMine;
		} else if (estBordBas(i, j)) {
			nbMine = estUneMine(i - 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j + 1) ? nbMine + 1 : nbMine;
		} else if (estBordGauche(i, j)) {
			nbMine = estUneMine(i - 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j + 1) ? nbMine + 1 : nbMine;
		} else {
			nbMine = estUneMine(i - 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j + 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i, j - 1) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i + 1, j) ? nbMine + 1 : nbMine;
			nbMine = estUneMine(i - 1, j - 1) ? nbMine + 1 : nbMine;
		}
		return nbMine;
	}

	/**
	 * Affiche dans la console le terrain de mines
	 */
	@Override
	public void afficherTableau() {
		String c;
		for (int i = 0; i < terrain.length; i++) {
			for (int j = 0; j < terrain[0].length; j++) {
				c = terrain[i][j] == false ? Integer.toString(nbMinesAutour(i, j)) : " ";
				System.out.print("[" + c + "]");
			}
			System.out.println();
		}
	}

	/**
	 * Affiche le tableau de cases dejaClique
	 */
	public void afficherDejaClique() {
		String c;
		for (int i = 0; i < dejaClique.length; i++) {
			for (int j = 0; j < dejaClique[0].length; j++) {
				c = dejaClique[i][j] == false ? " " : "D";
				// c = dejaClique[i][j];
				System.out.print("[" + c + "]");
			}
			System.out.println();
		}
	}	
}
