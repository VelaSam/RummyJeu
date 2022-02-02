package com.moumene.rummy;
//ceci est mon commentaire
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

		System.out.print("Nom premier joueur : ");
		joueur1.nom = clavier.next();
		System.out.print("Nom deuxi�me joueur : ");
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
			System.out.println("Partie termin�e. Le gagnant est "+joueur1.nom);
		} else {
			System.out.println("Partie termin�e. Le gagnant est "+joueur2.nom);
		}
		
	}
	
/***** M�thodes de d�roulement du jeu *****/
	
	/**
	 * Donne le tour au joueur inactif, qui devient le joueur actif
	 */
	public static void passerAuSuivant() {
		
		joueurActif = joueurActif==joueur1?joueur2:joueur1; 	
		
		return;
	}
	
	/**
	 * Fait jouer un tour au joueur.
	 * @param joueur
	 */
	public static void faireJouer(Joueur joueur) {

	}
	
/***** M�thodes de manipulation de pi�ces *****/	
	
	/**
	 * Retourne la valeur d'une piece.
	 * @param piece la pi�ce dont on retourne la valeur
	 * @return la valeur de la pi�ce
	 */
	public static int getValeur(Piece piece) {
		
		
		
		
	
		return piece.numero;
	}
	
	/**
	 * Retourne une chaine de caract�re d�crivant une piece.
	 * @param piece la pi�ce dont on retourne la repr�sentation
	 * @return la chaine d�crivant la pi�ce
	 */
	public static String toString(Piece piece) {
		return "["+piece.numero+""+piece.couleur+"]";
	}
	
	/**
	 * Retourne un tableau contenant les pi�ces d�crites dans la saisie.
	 * Cette m�thode suppose que la saisie a �t� v�rifi�e.
	 * 
	 * @param saisie chaine de caract�res d�crivant une liste de pi�ces.
	 * 		  Exemple : 3V12J5V
	 * @return tableau contenant les pi�ces d�crites dans la saisie.
	 */
	public static Piece[] extrairePieces(String saisie) {
		//Cr�ation
		int i,j = Constantes.VIDE;
		char[] stringToChar;
		String[] pieceNumero;
		Piece[] piece;
		
		//Initialisation
		pieceNumero = saisie.split("[J,V,B,R,N]");
		stringToChar = saisie.toCharArray();
			//pieceNumero.length est la r�f�rence car elle repr�sente
			//la grandeur exacte de pieces
		piece = new Piece[pieceNumero.length];
		
		//On rentre les valeur des num�ro des pieces 
		//contenu dans pieceNumero grace a la commande "split"
		for(i = 0; i<pieceNumero.length; i++)
		{
			piece[i] = new Piece();
			piece[i].numero = Integer.parseInt(pieceNumero[i]);
		}
			
		
		//Le compteur j indiquera la case dans le tableau @piece correspondant au bon numero
		for(i = 0; i< stringToChar.length;i++)
		{
			if(stringToChar[i]=='J'||stringToChar[i]=='V'||stringToChar[i]=='B'||
					stringToChar[i]=='R'||stringToChar[i]=='N')
			{
				j++;
				piece[j].couleur = stringToChar[i];
			}		
		}
		
		return piece;
	}
	
	/**
	 * Ajoute une pi�ce � la main d'un joueur.
	 * @param joueur le joueur qui recevra la pi�ce dans sa main
	 * @param piece la pi�ce � ajouter
	 * @return true si la pi�ce a �t� ajout�e, false si la pi�ce n'a pas �t�
	 * 		   ajout�e car la main est pleine.	
	 */
	public static boolean ajouterPiece(Joueur joueur, Piece piece) {

		return false;
	}	
		
	/**
	 * Ajouter une liste de pi�ces � une combinaison de la table de jeu.
	 * @param pieces tableau contenant les pi�ces � ajouter
	 * @param numeroCombinaison le num�ro de la combinaison sur la table �
	 * 							laquelle les pi�ces vont �tre ajout�es. La 
	 * 							premi�re combinaison porte le num�ro 1.
	 * @return true si toutes le pi�ces ont �t� ajout�es, false sinon.	
	 */
	public static boolean ajouterPiecesALaCombinaison(Piece[] pieces,int numeroCombinaison) {

		return false;
	}

	/**
	 * Ajouter une liste de pi�ces dans une nouvelle combinaison de la table de 
	 * jeu.
	 * @param pieces tableau contenant les pi�ces composant la combinaison.
	 * @return true si la nouvelle combinaison a �t� ajout�e, false sinon.	
	 */
	public static boolean ajouterNouvelleCombinaisonALaTable(Piece[] pieces) {

		return false;
	}
	
/***** M�thodes de v�rification *****/	
	
	/**
	 * V�rifie si la main d'un joueur est vide ou non.
	 * @param joueur le joueur
	 * @return true si la main du joueur est vide, false sinon.
	 */
	public static boolean mainVide(Joueur joueur) {
		boolean reponse = true;
		int i;
		
		for(i = 0; i< joueur.manne.length;i++)
			if(joueur.manne[i] != null && joueur.manne[i].numero != Constantes.VIDE)
				reponse = false;
		
		return reponse;
	}
	
	/**
	 * V�rifie si un caract�re correspond � une couleur du jeu.
	 * @param caractere
	 * @return true si le caract�re est une couleur valide, false sinon.
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
	 * V�rifie si une chaine de caract�res d�crit correctement une liste de
	 * pi�ces.
	 * @param chaine la chaine de caract�res � v�rifier
	 * @return true, si la chaine est une description correcte d'une liste de
	 * 			pi�ces, false sinon.
	 */
	public static boolean saisieCorrecte(String chaine) {

		return false;
	}
	
	/**
	 * V�rifie si une liste de pi�ces fait partie de la main d'un joueur.
	 * @param joueur le joueur
	 * @param pieces la liste des pi�ces
	 * @return true si toutes les pi�ces de la liste sont dans la main du 
	 * 		   joueur, false sinon.
	 */
	public static boolean valide(Joueur joueur, Piece[] pieces) {

		return false;
	}
	
	/**
	 * V�rifie si une liste de pi�ces constitue une combinaison (suite ou s�rie)
	 * @param pieces la liste des pi�ces
	 * @return true si la liste est une combinaison, false sinon.
	 */
	public static boolean estUneCombinaison(Piece[] pieces) {

		boolean reponse = false;
		
		
		
		
		
		
		return reponse;
	}




/***** M�thodes de manipulation de la pioche *****/	
	
	/**
	 * Retire des pi�ces d'une pioche et les place dans la main d'un joueur.
	 * @param pioche la pioche
	 * @param joueur le joueur
	 * @param nombrePieces le nombre de pi�ces � extraire de la pioche
	 */
	public static void distribuerMain(Pioche pioche, Joueur joueur,	int nombrePieces) {

		int i;
		
		for(i = 0; i < nombrePieces && pioche.nombrePieces!=0 ; i++ ) {
			
			joueur.manne[joueur.nombrePieces] = pioche.pieces[(Constantes.LONGUEUR_MAX_COMBINAISON-1)-(Constantes.LONGUEUR_MAX_COMBINAISON-pioche.nombrePieces)];
			pioche.pieces[(Constantes.LONGUEUR_MAX_COMBINAISON-1)-(Constantes.LONGUEUR_MAX_COMBINAISON-pioche.nombrePieces)] = new Piece();
			pioche.nombrePieces--;
			joueur.nombrePieces++;
		}
		
		return;
	}
	
	/**
	 * Extrait une pi�ce d'une pioche. Le choix de la pi�ce d�pend de
	 * l'impl�mentation.
	 * @param pioche la pioche
	 * @return la pi�ce extraite
	 */
	public static Piece piocher(Pioche pioche) {

		return null;
	}
	
	/**
	 * Remplace une pi�ce d'une pioche par une autre pi�ce. La pi�ce 
	 * remplac�e est retourn�e. La strat�gie de choix de la pi�ce � retirer
	 * d�pend de l'impl�mentation.
	 * @param pioche La pioche d'o� la pi�ce va �tre retir�e.
	 * @param piece La pi�ce � placer dans la pioche.
	 * @return La pi�ce retir�e de la pioche.
	 */
	public static Piece echanger(Pioche pioche, Piece piece) {

		return null;
	}
	
	/**
	 * G�n�re les 106 pi�ces du jeu et les place dans une pioche.
	 * @param pioche La pioche o� les pi�ces vont �tre plac�es.
	 */
	//Place les 106 pieces dans la pioche (incluant les 2 jokers) :
	public static void initialiserPioche(Pioche pioche) {
		int i, j;
		
		vider(pioche);
		//Premi�re 52 pi�ces
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
	
		return; 

	}
	
	/**
	 * Vide une pioche et y retirant toutes les pi�ces.
	 * @param pioche La pioche � vider
	 */
	public static void vider(Pioche pioche) {

		int i;
		
		for(i = 1; i < (Constantes.NOMBRE_TOTAL_PIECES); i++) {
		pioche.pieces[i] = new Piece();
		pioche.pieces[i].couleur = '\0';
		pioche.pieces[i].numero = Constantes.VIDE;
		}
		pioche.nombrePieces = 0;
		return;
		
	}
	
	/**
	 * Ajoute une pi�ce � une pioche.
	 * @param pioche La pioche o� la pi�ce va �tre ajout�e
	 * @param pioche La pi�ce � ajouter
	 * @return true si l'ajout a r�ussi, false sinon (faute de place)
	 */
	public static boolean ajouterPiece(Pioche pioche, Piece piece) {

		return false;
	}
	
	/**
	 * M�lange al�atoirement toutes les pieces de la pioche.
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


/***** M�thodes d'affichage *****/
	
	/**
	 * Affiche � l'�cran les premi�res pi�ces d'une liste.
	 * @param pieces La liste des pi�ces
	 * @param nombre Le nombre de pi�ces de la liste � prendre en consid�ration.
	 */
	public static void afficherPieces(Piece[] pieces, int nombre) {

	}	
	
	/**
	 * Affiche � l'�cran la main d'un joueur.
	 * @param joueur Le joueur
	 */
	public static void afficherMain(Joueur joueur) {

		
		
		//FONCTION PAS ENCORE TESTEE
		int i;
		
		//imprime nom du joueur
		System.out.println(joueur.nom + ": ");
		
		//boucle qui va jusquau nombre de pieces du joueur
		for(i = 0; i < joueur.nombrePieces; i++) {
			
			//imprime le numero de la piece
			System.out.print(joueur.manne[i].numero);
			//imprime la couleur de la piece
			System.out.print(joueur.manne[i].couleur);
			
			//on devrait avoir comme exemple: 13J2V8B25N
		}
		
		return;
	}	
	
	/**
	 * Affiche le contenu de la table de jeu.
	 */
	public static void afficherTable() {
	
	}
}