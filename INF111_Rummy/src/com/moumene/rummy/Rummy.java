package com.moumene.rummy;

import java.util.Random;
import java.util.Scanner;

import com.moumene.util.Util;

/**
 * @author atoudeft
 *
 */
public class Rummy {

	// La pioche :
	public static Pioche pioche = new Pioche();

	// Combinaisons qui sont sur la table :
	public static Piece[][] tableDeJeu = new Piece[Constantes.MAX_COMBINAISONS][Constantes.LONGUEUR_MAX_COMBINAISON];

	// Indique le nombre effectif de combinaisons sur la table :
	public static int nombreCombinaisonsSurLaTable = 0;

	// Indique le nombre de pieces dans chaque combinaison :
	public static int[] nombresPieces = new int[Constantes.MAX_COMBINAISONS];

	// Pour les saisies au clavier en mode console:
	public static Scanner clavier = new Scanner(System.in);

	public static Joueur joueur1 = new Joueur(), joueur2 = new Joueur(), joueurActif;

	public static void main(String[] args) {
		
		System.out.print("Nom premier joueur : ");
		joueur1.nom = clavier.next();
		System.out.print("Nom deuxième joueur : ");
		joueur2.nom = clavier.next();

		initialiserPioche(pioche);

		melangerPioche(pioche);
		distribuerMain(pioche, joueur1, Constantes.TAILLE_MANNE_DEPART);
		distribuerMain(pioche, joueur2, Constantes.TAILLE_MANNE_DEPART);

		joueurActif = joueur1;
		while (!mainVide(joueur1) && !mainVide(joueur2)) {
			faireJouer(joueurActif);
			passerAuSuivant();
		}
		if (mainVide(joueur1)) {
			System.out.println("Partie terminée. Le gagnant est " + joueur1.nom);
		} else {
			System.out.println("Partie terminée. Le gagnant est " + joueur2.nom);
		}

	}

	/***** Méthodes de déroulement du jeu *****/

	/**
	 * Donne le tour au joueur inactif, qui devient le joueur actif
	 */
	public static void passerAuSuivant() {

		joueurActif = joueurActif == joueur1 ? joueur2 : joueur1;

		return;
	}

	/**
	 * Fait jouer un tour au joueur.
	 * 
	 * @param joueur
	 */
	public static void faireJouer(Joueur joueur) {
		String pieceChoisie;
		Piece[] tabPieces; 
		Piece[] pieceAEchanger;
		Piece pieceARemplacer;//après aléatoire
		Piece piocheJoueur;
		boolean saisieCorrecteBool = true;
		boolean repEnregist;// Pour utiliser les réponses de nos enregistrement
		boolean trouve = false;// pour la fouille lors de l'échange
		int reponseJoueur;
		int i;
		
		piocheJoueur = piocher(pioche);
		repEnregist = ajouterPiece(joueur,piocheJoueur);
		if(repEnregist)
			System.out.println("Vous avez piocher une carte!");
		else
			System.out.println("Votre main est pleine, vous ne pouvez plus piger de carte.");

		
		afficherTable();
		afficherMain(joueur);
		
		while(saisieCorrecteBool)
		{
			System.out.println("Quelles pieces voulez vous placer?: ");
			pieceChoisie = clavier.next();
			saisieCorrecteBool = saisieCorrecte(pieceChoisie);
			
			
			tabPieces = extrairePieces(pieceChoisie);
			if(saisieCorrecteBool)
			{
				
				if(estUneCombinaison(tabPieces))
				{
					if(valide(joueur,tabPieces))
					{
						System.out.println("Voulez vous faire une nouvelle combinaison?[1] Ajouter a la table?[2]");
						reponseJoueur = clavier.nextInt();
						if(reponseJoueur == 1)
						{
							repEnregist = ajouterNouvelleCombinaisonALaTable(tabPieces); 
							if(!repEnregist)
								System.out.println("Il y a eu un erreur");
						}
						else if(reponseJoueur ==2)
						{
							System.out.println("À quel combinaison voulez vous le rajouter?");
							reponseJoueur = clavier.nextInt(); 
							
							repEnregist = ajouterPiecesALaCombinaison(tabPieces,reponseJoueur);
							
							if(!repEnregist)
								System.out.println("Vous ne pouvez pas jouer à cette endroit.");
						}
						
						if(repEnregist)
						{
							
						}
					}
				}
				else
				{
					if(valide(joueur,tabPieces))
					{
						afficherTable();
						System.out.println("À quel combinaison voulez vous le rajouter?");
						reponseJoueur = clavier.nextInt(); 
						
						repEnregist = ajouterPiecesALaCombinaison(tabPieces,reponseJoueur);
						
						if(!repEnregist)
							System.out.println("Vous ne pouvez pas jouer à cette endroit.");
					}
				}
			
				
			}
			
			afficherTable();
			afficherMain(joueur);
			
			do
			{
				System.out.println("Quel pieces de votre main voulez vous échanger?");
				pieceChoisie = clavier.next();
				
				pieceAEchanger = extrairePieces(pieceChoisie);
				repEnregist = valide(joueur,pieceAEchanger);
			}while(!repEnregist);
			
			
			pieceARemplacer = echanger(pioche,pieceAEchanger[0]);
		//// erreur pieceARemplacer pointe a la même case que pieceAEchanger!!!!!!!!!!
			for(i=0; i< joueur.nombrePieces && !trouve; i++)
			{
				if(pieceAEchanger[0]== joueur.manne[i])
				{
					joueur.manne[i] = pieceARemplacer;
					trouve = true;
				}
			}
			
		}
		
		return;
	}

	/***** Méthodes de manipulation de pièces *****/

	/**
	 * Retourne la valeur d'une piece.
	 * 
	 * @param piece la pièce dont on retourne la valeur
	 * @return la valeur de la pièce
	 */
	public static int getValeur(Piece piece) {
		return piece.numero;
	}

	/**
	 * Retourne une chaine de caractère décrivant une piece.
	 * 
	 * @param piece la pièce dont on retourne la représentation
	 * @return la chaine décrivant la pièce
	 */
	public static String toString(Piece piece) {
		return "[" + piece.numero + "" + piece.couleur + "]";
	}

	/**
	 * Retourne un tableau contenant les pièces décrites dans la saisie. Cette
	 * méthode suppose que la saisie a été vérifiée.
	 * 
	 * @param saisie chaine de caractères décrivant une liste de pièces. Exemple :
	 *               3V12J5V
	 * @return tableau contenant les pièces décrites dans la saisie.
	 */
	public static Piece[] extrairePieces(String saisie) {
		// Création
		int i, j = Constantes.VIDE;
		char[] stringToChar;
		String[] pieceNumero;
		Piece[] piece;

		// Initialisation
		pieceNumero = saisie.split("[J,V,B,R,N]");
		stringToChar = saisie.toCharArray();
		// pieceNumero.length est la référence car elle représente
		// la grandeur exacte de pieces
		piece = new Piece[pieceNumero.length];

		// On rentre les valeur des numéro des pieces
		// contenu dans pieceNumero grace a la commande "split"
		for (i = 0; i < pieceNumero.length; i++) {
			piece[i] = new Piece();
			piece[i].numero = Integer.parseInt(pieceNumero[i]);
		}

		// Le compteur j indiquera la case dans le tableau @piece correspondant au bon
		// numero
		for (i = 0; i < stringToChar.length; i++) {
			if (stringToChar[i] == 'J' || stringToChar[i] == 'V' || stringToChar[i] == 'B' || stringToChar[i] == 'R'
					|| stringToChar[i] == 'N') {
				piece[j].couleur = stringToChar[i];
				j++;
			}
		}

		return piece;
	}

	/**
	 * Ajoute une pièce à la main d'un joueur.
	 * 
	 * @param joueur le joueur qui recevra la pièce dans sa main
	 * @param piece  la pièce à ajouter
	 * @return true si la pièce a été ajoutée, false si la pièce n'a pas été ajoutée
	 *         car la main est pleine.
	 */
	public static boolean ajouterPiece(Joueur joueur, Piece piece) {

		boolean ajoutReussi;

		if (joueur.nombrePieces >= Constantes.LONGUEUR_MAX_MAIN)
		{
			ajoutReussi = false;
		}
		else 
		{

			joueur.manne[joueur.nombrePieces] = piece;
			

			piece.couleur = '\0';
			piece.numero = Constantes.VIDE;

			joueur.nombrePieces++;

			ajoutReussi = true;
		}

		return ajoutReussi;
	}

	/**
	 * Ajouter une liste de pièces à une combinaison de la table de jeu.
	 * 
	 * @param pieces            tableau contenant les pièces à ajouter
	 * @param numeroCombinaison le numéro de la combinaison sur la table à laquelle
	 *                          les pièces vont être ajoutées. La première
	 *                          combinaison porte le numéro 1.
	 * @return true si toutes le pièces ont été ajoutées, false sinon.
	 */
	public static boolean ajouterPiecesALaCombinaison(Piece[] pieces, int numeroCombinaison) {
		int i, j=0, longeurRestant =0;
		boolean rep = false, estUneCombin = false;
		int ptDepart;
		Piece[] combinATester = new Piece[tableDeJeu[numeroCombinaison -1].length];
		//Remplir le @combinATester des valeur de la combine de la table
		for(i = 0; i< tableDeJeu[numeroCombinaison -1].length; i++)
		{
			combinATester[i].numero = tableDeJeu[numeroCombinaison -1][i].numero;
			combinATester[i].couleur = tableDeJeu[numeroCombinaison -1][i].couleur;
		}
		
		//On imbrique les pieces dans notre tableau a tester
		for(i = 0; i< tableDeJeu[numeroCombinaison-1].length; i++)
			if(pieces[i] == null)
				longeurRestant++;
		
		if(longeurRestant >= pieces.length)
		{
			ptDepart = tableDeJeu[numeroCombinaison-1].length - longeurRestant -1;
			for(i=ptDepart; i<pieces.length;i++)
			{
				combinATester[i] = pieces[j];
				j++;
			}
			estUneCombin = estUneCombinaison(combinATester);
		}
		// On teste
		
		
		if(estUneCombin)
		{
			j = 0;
			longeurRestant = 0;
			for(i = 0; i< tableDeJeu[numeroCombinaison-1].length; i++)
				if(pieces[i] == null)
					longeurRestant++;
			
			if(longeurRestant >= pieces.length)
			{
				ptDepart = tableDeJeu[numeroCombinaison-1].length - longeurRestant -1;
				for(i=ptDepart; i<pieces.length;i++)
				{
					tableDeJeu[numeroCombinaison-1][i] = pieces[j];
					j++;
				}
				rep = true;
			}
			
		}
		
		
		return rep;
	}

	/**
	 * Ajouter une liste de pièces dans une nouvelle combinaison de la table de jeu.
	 * 
	 * @param pieces tableau contenant les pièces composant la combinaison.
	 * @return true si la nouvelle combinaison a été ajoutée, false sinon.
	 */
	public static boolean ajouterNouvelleCombinaisonALaTable(Piece[] pieces) {
		int j, i = 0;
		boolean rep = false;
		
		while(tableDeJeu[i] == null)
			i++;
		
		if(pieces.length <= Constantes.LONGUEUR_MAX_COMBINAISON)
		{
			for(j=0; j< pieces.length; j++)
				tableDeJeu[i][j]= pieces[j];
			
			rep = true;
		}
			
		
		return rep;
	}

	/***** Méthodes de vérification *****/

	/**
	 * Vérifie si la main d'un joueur est vide ou non.
	 * 
	 * @param joueur le joueur
	 * @return true si la main du joueur est vide, false sinon.
	 */
	public static boolean mainVide(Joueur joueur) {
		boolean reponse = true;
		int i;

		for (i = 0; i < joueur.manne.length; i++)
			if (joueur.manne[i] != null && joueur.manne[i].numero != Constantes.VIDE)
				reponse = false;

		return reponse;
	}

	/**
	 * Vérifie si un caractère correspond à une couleur du jeu.
	 * 
	 * @param caractere
	 * @return true si le caractère est une couleur valide, false sinon.
	 */
	public static boolean estUneCouleurValide(char caractere) {

		boolean reponse;

		switch (caractere) {

		case Constantes.VERT:
		case Constantes.ROUGE:
		case Constantes.BLEU:
		case Constantes.JAUNE:
		case Constantes.NOIR:
			reponse = true;
			break;
		default:
			reponse = false;
			break;
		}

		return reponse;
	}

	/**
	 * Vérifie si une chaine de caractères décrit correctement une liste de pièces.
	 * 
	 * @param chaine la chaine de caractères à vérifier
	 * @return true, si la chaine est une description correcte d'une liste de
	 *         pièces, false sinon.
	 */
	public static boolean saisieCorrecte(String chaine) {

		boolean rep = true;
		Piece[] aVerifier;
		int i;

		// Il faut vérifier si pas de lettre consécutif && qu'on reste dans couleurs
		// dispo
		for (i = 0; i < chaine.length(); i++)
		{
			
			switch(chaine.charAt(i))
			{
			case Constantes.BLEU:
			case Constantes.ROUGE:
			case Constantes.VERT:
			case Constantes.NOIR:
			case Constantes.JAUNE:
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				break;
			default:
				rep = false;
				break;
			}
		}
		
		
		if(rep==true)
		{
			aVerifier = extrairePieces(chaine);
			for (i = 0; i < aVerifier.length; i++) 
			{
				if(aVerifier[i].numero == 25 && aVerifier[i].couleur != Constantes.NOIR)
					rep = false;
				if(aVerifier[i].numero != 25 && aVerifier[i].couleur == Constantes.NOIR
						|| aVerifier[i].numero < 1 || aVerifier[i].numero > 13 && aVerifier[i].numero != 25)
					rep = false;
			}
		}
			
		

		return rep;
		
	}

	/**
	 * Vérifie si une liste de pièces fait partie de la main d'un joueur.
	 * 
	 * @param joueur le joueur
	 * @param pieces la liste des pièces
	 * @return true si toutes les pièces de la liste sont dans la main du joueur,
	 *         false sinon.
	 */
	public static boolean valide(Joueur joueur, Piece[] pieces) {	
		//recoit pieces et regarde dans main du joueur si il a ces pieces
		
		boolean estValide=false;
		int i, j, cmpt = 0;
		boolean indexInvalide[] = new boolean[joueur.nombrePieces];// Pour noter les case déjà utiliser
		boolean trouve =false;
		
		
		for(i = 0; i < pieces.length; i++) {
			trouve = false;
			for(j = 0;  j < joueur.nombrePieces && !trouve ; j++) 
			{
				
				if(pieces[i].numero == joueur.manne[j].numero && pieces[i].couleur == joueur.manne[j].couleur && !indexInvalide[j])
				{
					cmpt++;
					indexInvalide[j] = true;
					trouve = true;
				}
					
			}
		}
		
		if(cmpt == pieces.length)
			estValide = true;
		

		
		// recoit pieces et regarde dans main du joueur si il a ces pieces
		return estValide;
	}

	/**
	 * Vérifie si une liste de pièces constitue une combinaison (suite ou série)
	 * 
	 * @param pieces la liste des pièces
	 * @return true si la liste est une combinaison, false sinon.
	 */
	public static boolean estUneCombinaison(Piece[] pieces) {

		boolean reponse = true;// par défault on dira que c'est vrais
		int i, j;
		int validation = 0;
		int validationNumber = 0;

		if (pieces.length >= 3) 
		{
			if (pieces.length <= 4) 
			{
				validation = 0;
				for (i = 0; i < pieces.length - 1; i++) 
				{
					for (j = i + 1; j < pieces.length; j++) 
					{
						if (pieces[i].numero == pieces[j].numero && pieces[i].couleur != pieces[j].couleur || pieces[j].numero == 25)
						{
							validationNumber++;
							validation++;
						} 
						else if (pieces[i].numero != 25)
							validationNumber++;
					}
				}
				// Pas exactement, mais on essayera de démontrer que c'est peut être une suite
				// pour changer d'avis
				if (validation != validationNumber)
					reponse = false;

			} 
			else // Si longeur est plus grande que 4, pour rentrer dans reste code il faut false
				reponse = false;

			if (reponse == false)// Si c'est vrais, on ne veux pas modifier la réponse
			{
				validationNumber = 0;
				validation = 0;// Remise a zéro de l'évalution
				for (i = 0; i < pieces.length - 1; i++) {
					if (pieces[i].numero + 1 == pieces[i + 1].numero && pieces[i].couleur == pieces[i + 1].couleur
							|| pieces[i + 1].numero == 25) {
						validation++;
						validationNumber++;
					} else if (pieces[i].numero != 25)
						validationNumber++;
				}

				if (validation == validationNumber)
					reponse = true;
			}
		} else
			reponse = false;

		return reponse;
	}

	/***** Méthodes de manipulation de la pioche *****/

	/**
	 * Retire des pièces d'une pioche et les place dans la main d'un joueur.
	 * 
	 * @param pioche       la pioche
	 * @param joueur       le joueur
	 * @param nombrePieces le nombre de pièces à extraire de la pioche
	 */
	public static void distribuerMain(Pioche pioche, Joueur joueur, int nombrePieces) {

		int i;

		for (i = 0; i < nombrePieces && pioche.nombrePieces != 0; i++) {

			joueur.manne[joueur.nombrePieces] = pioche.pieces[(Constantes.LONGUEUR_MAX_COMBINAISON - 1)
					- (Constantes.LONGUEUR_MAX_COMBINAISON - pioche.nombrePieces)];
			pioche.pieces[(Constantes.LONGUEUR_MAX_COMBINAISON - 1)
					- (Constantes.LONGUEUR_MAX_COMBINAISON - pioche.nombrePieces)] = new Piece();
			pioche.nombrePieces--;
			joueur.nombrePieces++;
		}

		return;
	}

	/**
	 * Extrait une pièce d'une pioche. Le choix de la pièce dépend de
	 * l'implémentation.
	 * 
	 * @param pioche la pioche
	 * @return la pièce extraite
	 */
	public static Piece piocher(Pioche pioche) {
		Piece piger;
		int i;
		// On prend en compte que l'on pige au début du tableau. Il faut donc décaler le
		// restant des pieces pour remplir le trou
		if(pioche.nombrePieces >0)
		{
			piger = pioche.pieces[0];

			for (i = 0; i < pioche.nombrePieces; i++) 
			{
				pioche.pieces[i] = pioche.pieces[i + 1];
			}

			pioche.nombrePieces--;
		}
		else
			piger = new Piece();// Car sera null
		

		return piger;// Pas tester!!!!
	}

	/**
	 * Remplace une pièce d'une pioche par une autre pièce. La pièce remplacée est
	 * retournée. La stratégie de choix de la pièce à retirer dépend de
	 * l'implémentation.
	 * 
	 * @param pioche La pioche d'où la pièce va être retirée.
	 * @param piece  La pièce à placer dans la pioche.
	 * @return La pièce retirée de la pioche.
	 */
	public static Piece echanger(Pioche pioche, Piece piece) {

		Random aleatoire = new Random();
		int nombreAleatoire;
		Piece temp;

		// inclus 0, exclus la limite pioche.nombrePieces
		nombreAleatoire = aleatoire.nextInt(pioche.nombrePieces);

		temp = pioche.pieces[nombreAleatoire];
		pioche.pieces[nombreAleatoire] = piece;
		piece = temp;

		return piece;// Pas tester !!!
	}

	/**
	 * Génére les 106 pièces du jeu et les place dans une pioche.
	 * 
	 * @param pioche La pioche où les pièces vont être placées.
	 */
	// Place les 106 pieces dans la pioche (incluant les 2 jokers) :
	public static void initialiserPioche(Pioche pioche) {
		int i, j;

		vider(pioche);
		// Première 52 pièces
		for (j = 0; j < Constantes.COULEURS.length; j++) {

			for (i = 0 + (Constantes.TREIZE * j); i < Constantes.TREIZE + (Constantes.TREIZE * j); i++) {

				pioche.pieces[i] = new Piece();

				pioche.pieces[i].numero = i - (Constantes.TREIZE * j) + 1;
				pioche.pieces[i].couleur = Constantes.COULEURS[j];

				pioche.nombrePieces++;
			}
		}
		// 2e moitier du paquet jusqu'a 104
		for (j = 0; j < Constantes.COULEURS.length; j++) {

			for (i = (((Constantes.NOMBRE_TOTAL_PIECES - 2) / 2)
					+ (Constantes.TREIZE * j)); i < (((Constantes.NOMBRE_TOTAL_PIECES - 2) / 2) + Constantes.TREIZE
							+ (Constantes.TREIZE * j)); i++) {

				pioche.pieces[i] = new Piece();

				pioche.pieces[i].numero = i - ((Constantes.NOMBRE_TOTAL_PIECES - 2) / 2) - (Constantes.TREIZE * j) + 1;
				pioche.pieces[i].couleur = Constantes.COULEURS[j];

				pioche.nombrePieces++;
			}
		}

		// Initialiser joker1
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES - 2] = new Piece();
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES - 2].numero = Constantes.VINGT_CINQ;
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES - 2].couleur = Constantes.NOIR;
		pioche.nombrePieces++;

		// Initialiser joker2
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES - 1] = new Piece();
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES - 1].numero = Constantes.VINGT_CINQ;
		pioche.pieces[Constantes.NOMBRE_TOTAL_PIECES - 1].couleur = Constantes.NOIR;
		pioche.nombrePieces++;

		return;

	}

	/**
	 * Vide une pioche et y retirant toutes les pièces.
	 * 
	 * @param pioche La pioche à vider
	 */
	public static void vider(Pioche pioche) {

		int i;

		for (i = 1; i < (Constantes.NOMBRE_TOTAL_PIECES); i++) {
			pioche.pieces[i] = new Piece();
			pioche.pieces[i].couleur = '\0';
			pioche.pieces[i].numero = Constantes.VIDE;
		}
		pioche.nombrePieces = 0;
		return;

	}

	/**
	 * Ajoute une pièce à une pioche.
	 * 
	 * @param pioche La pioche où la pièce va être ajoutée
	 * @param pioche La pièce à ajouter
	 * @return true si l'ajout a réussi, false sinon (faute de place)
	 */
	public static boolean ajouterPiece(Pioche pioche, Piece piece) {

		boolean ajoutReussi;


		if (pioche.nombrePieces >= Constantes.NOMBRE_TOTAL_PIECES)
			ajoutReussi = false;

		else {

			pioche.pieces[pioche.nombrePieces] = piece;
			;

			piece.couleur = '\0';
			piece.numero = Constantes.VIDE;

			pioche.nombrePieces++;

			ajoutReussi = true;
		}

		return ajoutReussi;
	}

	/**
	 * Mélange aléatoirement toutes les pieces de la pioche.
	 * 
	 * @param pioche La pioche
	 */
	public static void melangerPioche(Pioche pioche) {

		Random aleatoire = new Random();
		Piece temp;

		int i, j, z;

		for (i = 0; i < Constantes.TRES_GRAND; i++) {

			// Generer deux nombres aleatoires
			do {

				j = aleatoire.nextInt(pioche.nombrePieces);
				z = aleatoire.nextInt(pioche.nombrePieces);

			} while (j == z);

			// SWAP :D
			temp = pioche.pieces[j];
			pioche.pieces[j] = pioche.pieces[z];
			pioche.pieces[z] = temp;
		}

		return;
	}

	/***** Méthodes d'affichage *****/

	/**
	 * Affiche à l'écran les premières pièces d'une liste.
	 * 
	 * @param pieces La liste des pièces
	 * @param nombre Le nombre de pièces de la liste à prendre en considération.
	 */
	public static void afficherPieces(Piece[] pieces, int nombre) {

		int i;

		for (i = 0; i < nombre && i < pieces.length; i++) {

			System.out.print(pieces[i].numero + pieces[i].couleur + " ");
		}

		System.out.println("");
		return;
	}

	/**
	 * Affiche à l'écran la main d'un joueur.
	 * 
	 * @param joueur Le joueur
	 */
	public static void afficherMain(Joueur joueur) {

		// FONCTION PAS ENCORE TESTEE
		int i;

		// imprime nom du joueur
		System.out.println(joueur.nom + ": ");

		// boucle qui va jusquau nombre de pieces du joueur
		for (i = 0; i < joueur.nombrePieces -1; i++) {

			// imprime le numero de la piece
			System.out.print(joueur.manne[i].numero);
			// imprime la couleur de la piece
			System.out.print(joueur.manne[i].couleur + " ");

			// on devrait avoir comme exemple: 13J 2V 8B 25N
		}
		System.out.println("");
		return;
	}

	/**
	 * Affiche le contenu de la table de jeu.
	 */
	public static void afficherTable() {
		int i,j;
		
		for(i=0; i<Constantes.MAX_COMBINAISONS; i++)
		{
			if(tableDeJeu[i] == null)
			{
				System.out.println("Combinaison num"+ (i+1) +": ");
				for(j=0; j < Constantes.LONGUEUR_MAX_COMBINAISON;j++)
					if(tableDeJeu[i][j] != null)
						System.out.println(toString(tableDeJeu[i][j]));
				
				System.out.println("");
			}
			
		}
		
		return;
	}
}