package s02;

import java.util.Arrays;
import java.util.Random;

import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Button;

public class MineHuntModel implements IMineHuntModel {
	// Attributs
	private boolean[][] terrain;
	private boolean[][] dejaClique;
	private boolean[][] flagged;
	private int nbClicks;
	private int nbErreurs;
	private int pourcentDeMines;
	private int nbMines;
	
	// Constructeur pour l'initialisation
	public MineHuntModel() {
		terrain = null;
		dejaClique = null;
		flagged = null;
		nbClicks = 0;
		nbErreurs = 0;
		this.pourcentDeMines = 0;
	}

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

	public void setTerrain(int ligIndex, int colIndex) {
		if (colIndex <= 0 || ligIndex <= 0)
			throw new IllegalArgumentException("Le tableau doit contenir au moins une case !");
		terrain = new boolean[ligIndex][colIndex];
	}

	public void setDejaClique(int ligIndex, int colIndex) {
		if (colIndex <= 0 || ligIndex <= 0)
			throw new IllegalArgumentException("Le tableau doit contenir au moins une case !");
		dejaClique = new boolean[ligIndex][colIndex];
	}

	public void setUneCaseDejaClique(int ligIndex, int colIndex, boolean dejaClique) {
		this.dejaClique[ligIndex][colIndex] = dejaClique;
	}

	public void setFlagged(int ligIndex, int colIndex) {
		if (ligIndex <= 0 || colIndex <= 0)
			throw new IllegalArgumentException("Le tableau doit contenir au moins une case !");
		flagged = new boolean[ligIndex][colIndex];
	}

	public void setUneCaseFlagged(int ligIndex, int colIndex, boolean flagged) {
		this.flagged[ligIndex][colIndex] = flagged;
	}

	public void setNbClicks(int nbClicks) {
		if (nbClicks < 0)
			throw new IllegalArgumentException("Le nombre de clicks ne peut pas être négatif");
		this.nbClicks = nbClicks;
	}

	public void setNbErreurs(int nbErreurs) {
		if (nbErreurs < 0)
			throw new IllegalArgumentException("Le nombre d'erreurs ne peut pas être négatif");
		this.nbErreurs = nbErreurs;
	}

	public void setPourcentMines(int pourcentage) {
		if (pourcentage < 0 || pourcentage > 100)
			throw new IllegalArgumentException("Le pourcentage doit être compris entre 1 et 100");
		pourcentDeMines = pourcentage;
	}

	public boolean[][] getTerrain() {
		return terrain;
	}

	public int getNbLines() {
		return terrain.length;
	}

	public int getNbCol() {
		return terrain[0].length;
	}

	public boolean[][] getDejaClique() {
		return dejaClique;
	}

	public boolean getUneCaseDejaClique(int ligIndex, int colIndex) {
		return dejaClique[ligIndex][colIndex];
	}

	public boolean getUneCaseFlagged(int ligIndex, int colIndex) {
		return flagged[ligIndex][colIndex];
	}

	public boolean[][] getFlagged() {
		return flagged;
	}

	public int getPourcentMines() {
		return pourcentDeMines;
	}

	public int getNbClicks() {
		return nbClicks;
	}

	public int getNbErrors() {
		return nbErreurs;
	}

	@Override
	public void placerMineAleatoirement(int i, int j) {
		Random r = new Random();
		int rand = r.nextInt(100);
		if (rand < pourcentDeMines) {
			terrain[i][j] = true;
			System.out.print("une mine en plus");
			nbMines++;
		}
	}

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
			// Dans le cas normal, vérifier toutes les cases autour
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

	public static void main(int[] args) {
		
	}
	
	
}
