package com.moumene.rummy;

import java.util.Random;
import java.util.Scanner;

import com.moumene.util.Util;

/**
 * @author atoudeft
 *
 */
public class Rummy {

	//La pioche :
	public static Pioche pioche = new Pioche();

	//Combinaisons qui sont sur la table :
	public static Piece[][] tableDeJeu = 
		new	Piece[Constantes.MAX_COMBINAISONS][Constantes.LONGUEUR_MAX_COMBINAISON];
	
	//Indique le nombre effectif de combinaisons sur la table :
	public static int nombreCombinaisonsSurLaTable = 0;
	
	//Indique le nombre de pieces dans chaque combinaison :
	public static int[] nombresPieces = new int[Constantes.MAX_COMBINAISONS];	
	
	// Pour les saisies au clavier en mode console:
	public static Scanner clavier = new Scanner(System.in);
	
	public static Joueur joueur1 = new Joueur(),
						 joueur2 = new Joueur(),
						 joueurActif;

	public static void main(String[] args) {

		vider(pioche);
		System.out.println("jsdoiasjdoiasd");
		
		/*
		System.out.print("Nom premier joueur : ");
		joueur1.nom = clavier.next();
		System.out.print("Nom deuxième joueur : ");
		joueur2.nom = clavier.next();
		
		initialiserPioche(pioche);

		
		melangerPioche(pioche);
		distribuerMain(pioche,joueur1,Constantes.TAILLE_MANNE_DEPART);
		distribuerMain(pioche,joueur2,Constantes.TAILLE_MANNE_DEPART);

		joueurActif = joueur1;
		while (!mainVide(joueur1) && !mainVide(joueur2)) {
			faireJouer(joueurActif);
			passerAuSuivant();
		}
		if (mainVide(joueur1)) {
			System.out.println("Partie terminée. Le gagnant est "+joueur1.nom);
		} else {
			System.out.println("Partie terminée. Le gagnant est "+joueur2.nom);
		}
		*/
	}
	
/***** Méthodes de déroulement du jeu *****/
	
	/**
	 * Donne le tour au joueur inactif, qui devient le joueur actif
	 */
	public static void passerAuSuivant() {
		
	}
	
	/**
	 * Fait jouer un tour au joueur.
	 * @param joueur
	 */
	public static void faireJouer(Joueur joueur) {

	}
	
/***** Méthodes de manipulation de pièces *****/	
	
	/**
	 * Retourne la valeur d'une piece.
	 * @param piece la pièce dont on retourne la valeur
	 * @return la valeur de la pièce
	 */
	public static int getValeur(Piece piece) {
		return piece.numero;
	}
	
	/**
	 * Retourne une chaine de caractère décrivant une piece.
	 * @param piece la pièce dont on retourne la représentation
	 * @return la chaine décrivant la pièce
	 */
	public static String toString(Piece piece) {
		return "["+piece.numero+""+piece.couleur+"]";
	}
	
	/**
	 * Retourne un tableau contenant les pièces décrites dans la saisie.
	 * Cette méthode suppose que la saisie a été vérifiée.
	 * 
	 * @param saisie chaine de caractères décrivant une liste de pièces.
	 * 		  Exemple : 3V12J5V
	 * @return tableau contenant les pièces décrites dans la saisie.
	 */
	public static Piece[] extrairePieces(String saisie) {

		return null;
	}
	
	/**
	 * Ajoute une pièce à la main d'un joueur.
	 * @param joueur le joueur qui recevra la pièce dans sa main
	 * @param piece la pièce à ajouter
	 * @return true si la pièce a été ajoutée, false si la pièce n'a pas été
	 * 		   ajoutée car la main est pleine.	
	 */
	public static boolean ajouterPiece(Joueur joueur, Piece piece) {

		return false;
	}	
		
	/**
	 * Ajouter une liste de pièces à une combinaison de la table de jeu.
	 * @param pieces tableau contenant les pièces à ajouter
	 * @param numeroCombinaison le numéro de la combinaison sur la table à
	 * 							laquelle les pièces vont être ajoutées. La 
	 * 							première combinaison porte le numéro 1.
	 * @return true si toutes le pièces ont été ajoutées, false sinon.	
	 */
	public static boolean ajouterPiecesALaCombinaison(Piece[] pieces,
														int numeroCombinaison) {

		return false;
	}

	/**
	 * Ajouter une liste de pièces dans une nouvelle combinaison de la table de 
	 * jeu.
	 * @param pieces tableau contenant les pièces composant la combinaison.
	 * @return true si la nouvelle combinaison a été ajoutée, false sinon.	
	 */
	public static boolean ajouterNouvelleCombinaisonALaTable(Piece[] pieces) {

		return false;
	}
	
/***** Méthodes de vérification *****/	
	
	/**
	 * Vérifie si la main d'un joueur est vide ou non.
	 * @param joueur le joueur
	 * @return true si la main du joueur est vide, false sinon.
	 */
	public static boolean mainVide(Joueur joueur) {
		
		return false;
	}
	
	/**
	 * Vérifie si un caractère correspond à une couleur du jeu.
	 * @param caractere
	 * @return true si le caractère est une couleur valide, false sinon.
	 */
	public static boolean estUneCouleurValide(char caractere) {

		return false;
	}	

	/**
	 * Vérifie si une chaine de caractères décrit correctement une liste de
	 * pièces.
	 * @param chaine la chaine de caractères à vérifier
	 * @return true, si la chaine est une description correcte d'une liste de
	 * 			pièces, false sinon.
	 */
	public static boolean saisieCorrecte(String chaine) {

		return false;
	}
	
	/**
	 * Vérifie si une liste de pièces fait partie de la main d'un joueur.
	 * @param joueur le joueur
	 * @param pieces la liste des pièces
	 * @return true si toutes les pièces de la liste sont dans la main du 
	 * 		   joueur, false sinon.
	 */
	public static boolean valide(Joueur joueur, Piece[] pieces) {

		return false;
	}
	
	/**
	 * Vérifie si une liste de pièces constitue une combinaison (suite ou série)
	 * @param pieces la liste des pièces
	 * @return true si la liste est une combinaison, false sinon.
	 */
	public static boolean estUneCombinaison(Piece[] pieces) {

		return false;
	}




/***** Méthodes de manipulation de la pioche *****/	
	
	/**
	 * Retire des pièces d'une pioche et les place dans la main d'un joueur.
	 * @param pioche la pioche
	 * @param joueur le joueur
	 * @param nombrePieces le nombre de pièces à extraire de la pioche
	 */
	public static void distribuerMain(Pioche pioche, Joueur joueur,	int nombrePieces) {

		int i;
		
		for(i = 0; i < nombrePieces && pioche.nombrePieces!=0 ; i++ ) {
			
			joueur.manne[joueur.nombrePieces] = pioche.pieces[105-(106-pioche.nombrePieces)];
			pioche.pieces[105-(106-pioche.nombrePieces)] = new Piece();
			pioche.nombrePieces--;
			joueur.nombrePieces++;
		}
		
		return;
	}
	
	/**
	 * Extrait une pièce d'une pioche. Le choix de la pièce dépend de
	 * l'implémentation.
	 * @param pioche la pioche
	 * @return la pièce extraite
	 */
	public static Piece piocher(Pioche pioche) {

		return null;
	}
	
	/**
	 * Remplace une pièce d'une pioche par une autre pièce. La pièce 
	 * remplacée est retournée. La stratégie de choix de la pièce à retirer
	 * dépend de l'implémentation.
	 * @param pioche La pioche d'où la pièce va être retirée.
	 * @param piece La pièce à placer dans la pioche.
	 * @return La pièce retirée de la pioche.
	 */
	public static Piece echanger(Pioche pioche, Piece piece) {

		return null;
	}
	
	/**
	 * Génére les 106 pièces du jeu et les place dans une pioche.
	 * @param pioche La pioche où les pièces vont être placées.
	 */
	//Place les 106 pieces dans la pioche (incluant les 2 jokers) :
	public static void initialiserPioche(Pioche pioche) {
		int i, j;
		
		vider(pioche);
		//Première 52 pièces
		for(j = 0; j < Constantes.COULEURS.length; j++)
		{
						
			for(i=0+(Constantes.TREIZE*j); i < Constantes.TREIZE+(Constantes.TREIZE*j); i++)
			{
			
				pioche.pieces[i] = new Piece();
				
				
				pioche.pieces[i].numero = i-(Constantes.TREIZE*j)+1; 
				pioche.pieces[i].couleur = Constantes.COULEURS[j];
				
				
				pioche.nombrePieces++;
			}
		}
		//2e moitier du paquet jusqu'a 104
		for(j = 0; j < Constantes.COULEURS.length; j++)
		{
						
			for(i=(((Constantes.NOMBRE_TOTAL_PIECES-2)/2)+(Constantes.TREIZE*j)); i < (((Constantes.NOMBRE_TOTAL_PIECES-2)/2)+Constantes.TREIZE+(Constantes.TREIZE*j)); i++)
			{
			
				pioche.pieces[i] = new Piece();
				
				pioche.pieces[i].numero = i-((Constantes.NOMBRE_TOTAL_PIECES-2)/2)-(Constantes.TREIZE*j)+1; 
				pioche.pieces[i].couleur = Constantes.COULEURS[j];
				
				
				pioche.nombrePieces++;
			}
		}
		
		//Initialiser joker1
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES-2] =  new Piece();
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES-2].numero = Constantes.VINGT_CINQ;
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES-2].couleur = Constantes.NOIR;
		pioche.nombrePieces++;
		
		
		//Initialiser joker2
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES-1] =  new Piece();
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES-1].numero = Constantes.VINGT_CINQ;
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES-1].couleur = Constantes.NOIR;
		pioche.nombrePieces++;
		//allo:D XXXDDDD
		return; 

	}
	
	/**
	 * Vide une pioche et y retirant toutes les pièces.
	 * @param pioche La pioche à vider
	 */
	public static void vider(Pioche pioche) {

		int i;
		
		for(i = 0; i < (Constantes.NOMBRE_TOTAL_PIECES-1); i++) {
		pioche.pieces[i].couleur = '\0';
		pioche.pieces[i].numero = Constantes.VIDE;
		}
		
		System.out.println("sdasdsadasdads");
		
		return;
		
	}
	
	/**
	 * Ajoute une pièce à une pioche.
	 * @param pioche La pioche où la pièce va être ajoutée
	 * @param pioche La pièce à ajouter
	 * @return true si l'ajout a réussi, false sinon (faute de place)
	 */
	public static boolean ajouterPiece(Pioche pioche, Piece piece) {

		return false;
	}
	
	/**
	 * Mélange aléatoirement toutes les pieces de la pioche.
	 * @param pioche La pioche
	 */
	public static void melangerPioche(Pioche pioche) {

		Random aleatoire = new Random();
		Piece temp;
		
		int i, j, z;
		
		
		for(i = 0; i < Constantes.TRES_GRAND; i++) {
			
			//Generer deux nombres aleatoires 
			do {
				
			j=aleatoire.nextInt(pioche.nombrePieces);
			z=aleatoire.nextInt(pioche.nombrePieces);
			
			}while(j==z);
			
			//SWAP :D
			temp = pioche.pieces[j];
			pioche.pieces[j]=pioche.pieces[z];
			pioche.pieces[z]=temp;
		}
		
		return;
	}


/***** Méthodes d'affichage *****/
	
	/**
	 * Affiche à l'écran les premières pièces d'une liste.
	 * @param pieces La liste des pièces
	 * @param nombre Le nombre de pièces de la liste à prendre en considération.
	 */
	public static void afficherPieces(Piece[] pieces, int nombre) {

	}	
	
	/**
	 * Affiche à l'écran la main d'un joueur.
	 * @param joueur Le joueur
	 */
	public static void afficherMain(Joueur joueur) {

	}	
	
	/**
	 * Affiche le contenu de la table de jeu.
	 */
	public static void afficherTable() {
	
	}
}