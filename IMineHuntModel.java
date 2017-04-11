package s02;

import javafx.scene.control.Button;

public interface IMineHuntModel {
  // Attributs : 
  // tableau en 2d contenant les cases
  // nombre de clics
  // nombre d'erreurs 
  // Place aléatoirement les mines
  
  
  
  
  
  // Nombre de mines autour de la case cliquée
  int nbMinesAutour(int i, int j);
  boolean estCoinHautGauche(int i, int j);
  boolean estCoinHautDroite(int i, int j);
  boolean estCoinBasDroite(int i, int j);
  boolean estCoinBasGauche(int i, int j);
  boolean estBordHaut(int i, int j);
  boolean estBordDroite(int i, int j);
  boolean estBordBas(int i, int j);
  boolean estBordGauche(int i, int j);
  
  // Retourne vrai si c'est une mine
  boolean estUneMine(int i, int j);
  
  // Démasquer le bouton : afficher le nombre si mine autour ou la mine
  
  
  void afficherTableau();
  
  
  
  
  
  // Termine le jeu et affiche une boite de dialogue
  // le jeu se termine lorsqu'on clique sur toutes
  // les case non-minées



  void initialiser();

 
}
