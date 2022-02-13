package com.moumene.rummy;
//commentaire
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
		System.out.print("Nom deuxi�me joueur : ");
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
			System.out.println("Partie termin�e. Le gagnant est " + joueur1.nom);
		} else {
			System.out.println("Partie termin�e. Le gagnant est " + joueur2.nom);
		}

	}
	
	/*
	public static void main(String[] args) {
		
		Piece myPiece = new Piece();
		
		myPiece.couleur = 'X';
		myPiece.numero = 9999999;
		
		initialiserPioche(pioche);
		
		distribuerMain(pioche, joueur1, Constantes.TAILLE_MANNE_DEPART);
		
		afficherPieces(pioche.pieces, 106);
		
		
		ajouterPiece(joueur1, myPiece);
		
		afficherPieces(joueur1.manne, 14);
		
		
			Piece myPice = new Piece();
		myPice.numero = 25;
		myPice.couleur= 'V';
		Piece myPice2 = new Piece();
		myPice2.numero = 1;
		myPice2.couleur= 'V';
		Piece myPice3 = new Piece();
		myPice3.numero =  4;
		myPice3.couleur= 'V';
		
		Piece[] mesPiesses = new Piece[3];
		
		mesPiesses[0] = myPice;
		mesPiesses[1] = myPice2;
		mesPiesses[2] = myPice3;
		
		afficherPieces(mesPiesses, 3);

		testEstUneComb = estUneCombinaison(mesPiesses);
				
		if(testEstUneComb)
			System.out.println("TRUE");
		else
			System.out.println("FALSE");
		
		
	}*/

	/***** M�thodes de d�roulement du jeu *****/
	
	
	/**
	 * Donne le tour au joueur inactif, qui devient le joueur actif
	 */
	public static void passerAuSuivant() {
		//FONCTION GOOD
		joueurActif = joueurActif == joueur1 ? joueur2 : joueur1;

		return;
	}

	/**
	 * Fait jouer un tour au joueur.
	 * 
	 * @param joueur
	 */
	public static void faireJouer(Joueur joueur) {
		String repJoueurS;
		Piece repJoueurP = new Piece();
		Piece[] repJoueurPS = new Piece[Constantes.LONGUEUR_MAX_COMBINAISON];	
		int repJoueurI;
		int pieceI = 0;
		int i;
		boolean verification;
		char pieceC = '\0';
		
		afficherTable();
		afficherMain(joueur);
		
		System.out.print("Quest sont les pieces que vous voulez jouer? Si vous n'en avez pas faites ENTREE: ");
		repJoueurS = clavier.nextLine();
		repJoueurS = clavier.nextLine();//ne marche pas si on met juste 1 nextLine() donc 2 fois
		
		while(!(repJoueurS.isEmpty() || repJoueurS.equals("") || repJoueurS.equals(" ")))//Tant que la saisie n'est pas vide
		{
			
			if(saisieCorrecte(repJoueurS))// Si les caract�res ont de l'allure
			{
				repJoueurPS = extrairePieces(repJoueurS);// Une fois la saisie v�rifier, 
														 // il faut la convertire en tableau pour voir
														 // si elle est valide avec la main du joueur
				
				if(valide(joueur, repJoueurPS))//recoit pieces et regarde dans main du joueur si il a ces pieces
				{
					if(estUneCombinaison(repJoueurPS))//Si cest une combinaison
					{
						System.out.println("Voulez vous faire une nouvelle combinaison? [0]" //
								+ "\nOu sinon, voulez vous ajouter a une autre combinaison d�j� plac�e? Veuillez choisir son num�ro[1..]");
						repJoueurI = clavier.nextInt();  //demande au user cest quoi sa reponse
						
						if(repJoueurI == 0) // Si le user a choisi dajouter dajouter une nouvelle combinaison
						{
							verification = ajouterNouvelleCombinaisonALaTable(repJoueurPS); //
							if(!verification)
								System.out.println("!ERREUR! Aucune nouvelle combinaison n'a �t� cr�er !ERREUR!");//jamais supposer etre vu par joeur
						}
						else if(repJoueurI >=1) //Si le user a choisi une combinaison deja placee
						{
							verification = ajouterPiecesALaCombinaison(repJoueurPS,repJoueurI);
							if(!verification)
									System.out.println("Il n'est pas possible de rajouter ces pieces � cette combinaison.");
						}
						else
							System.out.println("Votre saisie n'est pas valide.");//Pas vrm supposer etre vu par le joueur a moins derreur			
					}
					else if(tableDeJeu[0][0] != null) //Si deja carte sur la table, tu peux rajouter des combinaisons
					{
						System.out.println("� quel combinaison voulez vous rajouter vos pi�ces?");
						repJoueurI = clavier.nextInt();
						verification = ajouterPiecesALaCombinaison(repJoueurPS,repJoueurI);
						if(!verification)
									System.out.println("Il n'est pas possible de rajouter ces pieces � cette combinaison.");
					}
					
					else //Si rien sur la table, (pas encore de de combinaisons deja placees) 
						System.out.println("Ce n'est pas une bonne combinaison"); //Deux possibilites de voir cette phrase: Soit le joueur essaye de mettre une/des cartes sur table vide
				}																//ou bien joueur saisit mauvais combinaison
				else //ces cartes ne sont pas dans ses mains
					System.out.println("Entr�e non valide.");
			}
			else { //si invalide
				System.out.println("Les caract�res entr�s ne sont pas valides");
			}
			
			afficherTable(); //afficherTable
			afficherMain(joueur);
			
			System.out.println("\nQuel est votre prochain jeu? Si vous n'en avez pas faites ENTREE");
			repJoueurS = clavier.nextLine();
			repJoueurS = clavier.nextLine();//ne marche pas si on met juste 1 nextLine() donc 2 fois
			
		} 
		
		verification = false; //Afin d'initialiser ma condition while
		while(!verification)
		{
			System.out.println("Quel carte d�sirez vous �changer?");
			repJoueurS = clavier.next();
			
			if(saisieCorrecte(repJoueurS)) //Est ce que c'est les bons caract�res qui ont etes saisis
			{
				repJoueurPS = extrairePieces(repJoueurS);// On le met en tableau on change le String saisi en piece dans un tableau
				if(valide(joueur,repJoueurPS) && repJoueurPS.length == 1)// Il faut s'assurer que le joueur poss�de la carte et qu'il n'en as pas s�lectionner plus de une
					verification = true;
				else
					System.out.println("Entr�e invalide.");
			}
		}
		
		//Objectif d'annuler les r�f�rence et que ce sois par valeur
		pieceC =repJoueurPS[0].couleur ;
		pieceI =repJoueurPS[0].numero ;
		
		repJoueurP.couleur = pieceC;
		repJoueurP.numero = pieceI;
		
		repJoueurPS[0] = echanger(pioche, repJoueurPS[0]);
		// A cet instant repJoueurPS[0] d�tient la nouvelle valeur et repJoueurP est la vieille que le joueur a choisis
		verification = false;
		for(i = 0; i< joueur.manne.length && !verification; i++)
			if(joueur.manne[i].numero == repJoueurP.numero && joueur.manne[i].couleur == repJoueurP.couleur)
			{
				joueur.manne[i] = repJoueurPS[0];
				verification = true;
			}
		
	}

	/***** M�thodes de manipulation de pi�ces *****/

	/**
	 * Retourne la valeur d'une piece.
	 * 
	 * @param piece la pi�ce dont on retourne la valeur
	 * @return la valeur de la pi�ce
	 */
	public static int getValeur(Piece piece) {
		 //FONCTION GOOD
		return piece.numero;
	}
		
	/**
	 * Retourne une chaine de caract�re d�crivant une piece.
	 * 
	 * @param piece la pi�ce dont on retourne la repr�sentation
	 * @return la chaine d�crivant la pi�ce
	 */
	public static String toString(Piece piece) {
		//FONCTION GOOD
		return "[" + piece.numero + "" + piece.couleur + "]";
		
	}
		
	/**
	 * Retourne un tableau contenant les pi�ces d�crites dans la saisie. Cette
	 * m�thode suppose que la saisie a �t� v�rifi�e.
	 * 
	 * @param saisie chaine de caract�res d�crivant une liste de pi�ces. Exemple :
	 *               3V12J5V
	 * @return tableau contenant les pi�ces d�crites dans la saisie.
	 */
	public static Piece[] extrairePieces(String saisie) {
		
		//FONCTION GOOD
		
		// Cr�ation
		int i, j = Constantes.VIDE;
		char[] stringToChar;
		String[] pieceNumero;
		Piece[] piece;

		// Initialisation
		pieceNumero = saisie.split("[J,V,B,R,N]");
		stringToChar = saisie.toCharArray();
		// pieceNumero.length est la r�f�rence car elle repr�sente
		// la grandeur exacte de pieces
		piece = new Piece[pieceNumero.length];

		// On rentre les valeur des num�ro des pieces
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
	 * Ajoute une pi�ce � la main d'un joueur.
	 * 
	 * @param joueur le joueur qui recevra la pi�ce dans sa main
	 * @param piece  la pi�ce � ajouter
	 * @return true si la pi�ce a �t� ajout�e, false si la pi�ce n'a pas �t� ajout�e
	 *         car la main est pleine.
	 */
	public static boolean ajouterPiece(Joueur joueur, Piece piece) {
		//FONCTION GOOD
		boolean ajoutReussi;
		int remplacer;
		char charRemp;
		

		if (joueur.nombrePieces >= Constantes.LONGUEUR_MAX_MAIN)
		{
			ajoutReussi = false;
		}
		else 
		{
			remplacer = piece.numero;
			charRemp = piece.couleur;
		
			joueur.manne[joueur.nombrePieces] = new Piece();
					
			joueur.manne[joueur.nombrePieces].numero = remplacer;
			joueur.manne[joueur.nombrePieces].couleur = charRemp;
			

			piece.couleur = '\0';
			piece.numero = Constantes.VIDE;

			joueur.nombrePieces++;

			ajoutReussi = true;
		}

		return ajoutReussi;
	
	}

	/**
	 * Ajouter une liste de pi�ces � une combinaison de la table de jeu.
	 * 
	 * @param pieces            tableau contenant les pi�ces � ajouter
	 * @param numeroCombinaison le num�ro de la combinaison sur la table � laquelle
	 *                          les pi�ces vont �tre ajout�es. La premi�re
	 *                          combinaison porte le num�ro 1.
	 * @return true si toutes le pi�ces ont �t� ajout�es, false sinon.
	 */
	public static boolean ajouterPiecesALaCombinaison(Piece[] pieces, int numeroCombinaison) {
		int i, j=0, longeurRestant =0;
		boolean rep = false, estUneCombin = false;
		int ptDepart;
		Piece[] combinATester = new Piece[tableDeJeu[numeroCombinaison -1].length];
	
		//Remplir le @combinATester des valeur de la combine de la table
		for(i = 0; i< tableDeJeu[numeroCombinaison -1].length; i++)
		{
			if(tableDeJeu[numeroCombinaison -1][i] != null)
			{
				combinATester[i] = new Piece();
				combinATester[i] = tableDeJeu[numeroCombinaison -1][i];
				
			}
			
		}
		
		//On imbrique les pieces dans notre tableau a tester
		for(i = 0; i< tableDeJeu[numeroCombinaison-1].length; i++)
			if(tableDeJeu[numeroCombinaison-1][i] == null)
				longeurRestant++;
		
		if(longeurRestant >= pieces.length)
		{
			j=0;
			ptDepart = tableDeJeu[numeroCombinaison-1].length - longeurRestant;
			for(i=ptDepart; i< ptDepart +pieces.length;i++)
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
			//longeurRestant = 0;
			//for(i = 0; i< tableDeJeu[numeroCombinaison-1].length; i++)
			//	if(pieces[i] == null)
			//		longeurRestant++;
			
			if(longeurRestant >= pieces.length)
			{
				ptDepart = tableDeJeu[numeroCombinaison-1].length - longeurRestant;
				for(i=ptDepart; i<ptDepart + pieces.length;i++)
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
	 * Ajouter une liste de pi�ces dans une nouvelle combinaison de la table de jeu.
	 * 
	 * @param pieces tableau contenant les pi�ces composant la combinaison.
	 * @return true si la nouvelle combinaison a �t� ajout�e, false sinon.
	 */
	public static boolean ajouterNouvelleCombinaisonALaTable(Piece[] pieces) {
		int j, i = 0;
		boolean rep = false;
		
		if(tableDeJeu[i][0] == null)
		{
			
		}
		else
		{
			while(tableDeJeu[i][0] != null)
			{
				i++;
			}
		}
		
			
		
		if(pieces.length <= Constantes.LONGUEUR_MAX_COMBINAISON)
		{
			for(j=0; j< pieces.length; j++)
				tableDeJeu[i][j]= pieces[j];
			
			rep = true;
		}
		
		
		return rep;
	}

	/***** M�thodes de v�rification *****/

	/**
	 * V�rifie si la main d'un joueur est vide ou non.
	 * 
	 * @param joueur le joueur
	 * @return true si la main du joueur est vide, false sinon.
	 */
	public static boolean mainVide(Joueur joueur) {
		
		//Fonction pas vrm possible a tester faq va falloir croiser les doigts
		boolean reponse = true;
		int i;

		for (i = 0; i < joueur.manne.length; i++)
			if (joueur.manne[i] != null && joueur.manne[i].numero != Constantes.VIDE)
				reponse = false;

		return reponse;
	}

	/**
	 * V�rifie si un caract�re correspond � une couleur du jeu.
	 * 
	 * @param caractere
	 * @return true si le caract�re est une couleur valide, false sinon.
	 */
	public static boolean estUneCouleurValide(char caractere) {

		//FONCTION GOOD
		
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
	 * V�rifie si une chaine de caract�res d�crit correctement une liste de pi�ces.
	 * 
	 * @param chaine la chaine de caract�res � v�rifier
	 * @return true, si la chaine est une description correcte d'une liste de
	 *         pi�ces, false sinon.
	 */
	public static boolean saisieCorrecte(String chaine) {

		//FONCTION GOOD
		
		boolean rep = true;
		Piece[] aVerifier;
		int i;

		// Il faut v�rifier si pas de lettre cons�cutif && qu'on reste dans couleurs
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
	 * V�rifie si une liste de pi�ces fait partie de la main d'un joueur.
	 * 
	 * @param joueur le joueur
	 * @param pieces la liste des pi�ces
	 * @return true si toutes les pi�ces de la liste sont dans la main du joueur,
	 *         false sinon.
	 */
	public static boolean valide(Joueur joueur, Piece[] pieces) {	
		//recoit pieces et regarde dans main du joueur si il a ces pieces
		
		//FONCTION GOOD
		boolean estValide=false;
		int i, j, cmpt = 0;
		boolean indexInvalide[] = new boolean[joueur.nombrePieces];// Pour noter les case d�j� utiliser
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
	 * V�rifie si une liste de pi�ces constitue une combinaison (suite ou s�rie)
	 * 
	 * @param pieces la liste des pi�ces
	 * @return true si la liste est une combinaison, false sinon.
	 */
	public static boolean estUneCombinaison(Piece[] pieces) {

		//Fonction a v�rifier, lorsque l'on rajoute une valeur ou plusieur qui sont mauvaise a une combinaison
		// On compare des pareilles dans la section des suite et pour x raison, les nouvelles pieces sont consid�rer comme null
		
		boolean reponse = true;// par d�fault on dira que c'est vrais
		int i, j;
		int validation = 0;
		int validationNumber = 0;
		int lengthUse =0;
		
		//Utilit� ou il y a des pieces null dans le tableau et qui fausse la r�alit� de pieces.length
		for(i=0; i< pieces.length; i++)
			if(pieces[i] != null)
				lengthUse++;

		if (lengthUse >= Constantes.TROIS) 
		{
			if (lengthUse <= Constantes.QUATRE) ////Il y a eu un bug ici! 8B8J8V et rajout 25N
			{
				validation = 0;
				for (i = 0; i < lengthUse - Constantes.UN; i++) 
				{
					for (j = i + Constantes.UN; j < lengthUse; j++) 
					{
						if (pieces[i].numero == pieces[j].numero && pieces[i].couleur != pieces[j].couleur || pieces[j].numero == Constantes.VINGT_CINQ)
						{
							validationNumber++;
							validation++;
						} 
						else if (pieces[i].numero != Constantes.VINGT_CINQ)
							validationNumber++;
					}
				}
				// Pas exactement, mais on essayera de d�montrer que c'est peut �tre une suite
				// pour changer d'avis
				if (validation != validationNumber)
					reponse = false;

			} 
			else // Si longeur est plus grande que 4, pour rentrer dans reste code il faut false
				reponse = false;

			if (reponse == false)// Si c'est vrais, on ne veux pas modifier la r�ponse
			{
				validationNumber = 0;
				validation = 0;// Remise a z�ro de l'�valution
				for (i = 0; i < (lengthUse-1); i++) {// Moins un de plus car on v�rifie avec le suivant 
					if(pieces[i] != null)
					{
						if (pieces[i].numero + Constantes.UN == pieces[i + Constantes.UN].numero && pieces[i].couleur == pieces[i + Constantes.UN].couleur
							|| pieces[i + Constantes.UN].numero == Constantes.VINGT_CINQ) {
						validation++;
						validationNumber++;
						} 
						else if (pieces[i].numero != Constantes.VINGT_CINQ)
							validationNumber++;
					}
					else
						validationNumber++;
					
				}

				if (validation == validationNumber)
					reponse = true;
			}
		} else
			reponse = false;

		return reponse;
	}

	/***** M�thodes de manipulation de la pioche *****/

	/**
	 * Retire des pi�ces d'une pioche et les place dans la main d'un joueur.
	 * 
	 * @param pioche       la pioche
	 * @param joueur       le joueur
	 * @param nombrePieces le nombre de pi�ces � extraire de la pioche
	 */
	public static void distribuerMain(Pioche pioche, Joueur joueur, int nombrePieces) {

		//fonction good 1/2
		
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
	 * Extrait une pi�ce d'une pioche. Le choix de la pi�ce d�pend de
	 * l'impl�mentation.
	 * 
	 * @param pioche la pioche
	 * @return la pi�ce extraite
	 */
	public static Piece piocher(Pioche pioche) {

		//FONCTION GOOD
		
		Piece piger;
		int i;
		// On prend en compte que l'on pige au d�but du tableau. Il faut donc d�caler le
		// restant des pieces pour remplir le trou
		if(pioche.nombrePieces > 0)
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
	 * Remplace une pi�ce d'une pioche par une autre pi�ce. La pi�ce remplac�e est
	 * retourn�e. La strat�gie de choix de la pi�ce � retirer d�pend de
	 * l'impl�mentation.
	 * 
	 * @param pioche La pioche d'o� la pi�ce va �tre retir�e.
	 * @param piece  La pi�ce � placer dans la pioche.
	 * @return La pi�ce retir�e de la pioche.
	 */
	public static Piece echanger(Pioche pioche, Piece piece) {

		//FONCTION GOOD
		
		Random aleatoire = new Random();
		int nombreAleatoire;
		int temp, tempPioche;
		char tempC, tempPiocheC;
		

		// inclus 0, exclus la limite pioche.nombrePieces
		nombreAleatoire = aleatoire.nextInt(pioche.nombrePieces);

		temp = pioche.pieces[nombreAleatoire].numero;
		tempC = pioche.pieces[nombreAleatoire].couleur;
		tempPioche = piece.numero;
		tempPiocheC = piece.couleur;
		pioche.pieces[nombreAleatoire].numero = tempPioche;
		pioche.pieces[nombreAleatoire].couleur = tempPiocheC;
		piece.numero = temp;
		piece.couleur = tempC;

		return piece;
	}

	/**
	 * G�n�re les 106 pi�ces du jeu et les place dans une pioche.
	 * 
	 * @param pioche La pioche o� les pi�ces vont �tre plac�es.
	 */
	// Place les 106 pieces dans la pioche (incluant les 2 jokers) :
	public static void initialiserPioche(Pioche pioche) {
		
		//FONCTION GOOD
		
		int i, j;

		vider(pioche);
		// Premi�re 52 pi�ces
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
	 * Vide une pioche et y retirant toutes les pi�ces.
	 * 
	 * @param pioche La pioche � vider
	 */
	public static void vider(Pioche pioche) {

		//FONCTION GOOD
		int i;

		for (i = 0; i < (Constantes.NOMBRE_TOTAL_PIECES); i++) {
			pioche.pieces[i] = new Piece();
			pioche.pieces[i].couleur = '\0';
			pioche.pieces[i].numero = Constantes.VIDE;
		}
		pioche.nombrePieces = 0;
		return;

	}

	/**
	 * Ajoute une pi�ce � une pioche.
	 * 
	 * @param pioche La pioche o� la pi�ce va �tre ajout�e
	 * @param pioche La pi�ce � ajouter
	 * @return true si l'ajout a r�ussi, false sinon (faute de place)
	 */
	public static boolean ajouterPiece(Pioche pioche, Piece piece) {

		//FONCTION GOOD
		boolean ajoutReussi;
		int remplacer;
		char charRemp;

		if (pioche.nombrePieces >= Constantes.NOMBRE_TOTAL_PIECES)
			ajoutReussi = false;

		else {

			pioche.pieces[pioche.nombrePieces] = new Piece();
			charRemp = piece.couleur;
			remplacer = piece.numero;
			pioche.pieces[pioche.nombrePieces].numero = remplacer;
			pioche.pieces[pioche.nombrePieces].couleur = charRemp;
			
			piece.couleur = '\0';
			piece.numero = Constantes.VIDE;

			pioche.nombrePieces++;

			ajoutReussi = true;
		}

		return ajoutReussi;
	}

	/**
	 * M�lange al�atoirement toutes les pieces de la pioche.
	 * 
	 * @param pioche La pioche
	 */
	public static void melangerPioche(Pioche pioche) {

		//FONTION GOOD
		
		
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

	/***** M�thodes d'affichage *****/

	/**
	 * Affiche � l'�cran les premi�res pi�ces d'une liste.
	 * 
	 * @param pieces La liste des pi�ces
	 * @param nombre Le nombre de pi�ces de la liste � prendre en consid�ration.
	 */
	public static void afficherPieces(Piece[] pieces, int nombre) {

		//FONCTION GOOD
		
		int i;
		String aPrint;

		for (i = 0; i < nombre && i < pieces.length; i++) {
			
			aPrint = toString(pieces[i]);
			System.out.print(aPrint);
		}

		System.out.println("");
		return;
	}

	/**
	 * Affiche � l'�cran la main d'un joueur.
	 * 
	 * @param joueur Le joueur
	 */
	public static void afficherMain(Joueur joueur) {

		// FONCTION GOOD
		int i;

		// imprime nom du joueur
		System.out.println(joueur.nom + ": ");

		// boucle qui va jusquau nombre de pieces du joueur
		for (i = 0; i < joueur.nombrePieces; i++) {
			System.out.print(joueur.manne[i].numero);
			// imprime le numero de la piece

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
			if(tableDeJeu[i][0] != null)
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

	public static void enleverPieceDeMain(Joueur joueur, Piece[] pieces) {
		
		int i, j;
		boolean trouvePiece;
		Piece temp;
		
		//Choisir l'index du tableau de Piece
		
		
		for(i = 0; i < pieces.length; i++) {
		//Fouiller le tableau pour trouver cette Piece
			
			trouvePiece = false;
		
			for(j = 0; j < joueur.manne.length && !trouvePiece; j++) {  //TROUVE LA PIECE ET LA MET 0
				
				if(joueur.manne[j].couleur == pieces[i].couleur 
												&& joueur.manne[j].numero == pieces[i].numero)
				{	
					trouvePiece = true;
					pieces[i].couleur = '\0';
					pieces[i].numero = Constantes.VIDE;
					joueur.nombrePieces--;
				}
			}
			//switcharoooo
			temp = joueur.manne[joueur.nombrePieces-1];
			joueur.manne[joueur.nombrePieces-1] = joueur.manne[j];;
			joueur.manne[j] = temp;
			
			
			return;
		}
		
		
		
	}
}

