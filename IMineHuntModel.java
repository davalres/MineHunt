package s02;

public interface IMineHuntModel {
  int nbMinesAutour(int i, int j);
  boolean estCoinHautGauche(int i, int j);
  boolean estCoinHautDroite(int i, int j);
  boolean estCoinBasDroite(int i, int j);
  boolean estCoinBasGauche(int i, int j);
  boolean estBordHaut(int i, int j);
  boolean estBordDroite(int i, int j);
  boolean estBordBas(int i, int j);
  boolean estBordGauche(int i, int j);
  boolean estUneMine(int i, int j);
  void afficherTableau();
  void initialiser();
}
