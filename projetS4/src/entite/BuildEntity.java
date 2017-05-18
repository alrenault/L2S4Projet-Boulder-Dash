package entite;

import moteurJeu.MoteurJeu;
/**
 * Classe construisant toutes les entites
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public class BuildEntity{

	/**
	 * Permet de construire les entites en fonction de apparence passe en argument
	 * @param moteur Reference vers le moteur de jeu
	 * @param apparence Apparence de l'entite a creer
	 * @return Retourne l'entite creee
	 */
	public Entite buildEntity(MoteurJeu moteur, char apparence){
		
		Entite j;
		
		switch(apparence){
		
		//CASES UNIQUES
		case 'P' : j = new Joueur(); break;  //Rockford
		case 'X' : j = new Exit(); break;  //Exit
		
		//CASES MULTIPLES
		case ' ' : j = new Espace() ; break;  //Espace
		case '.' : j = new Poussiere(); break;  //Poussiere
		case 'r' : j = new Roc(); break;  //Roc
		case 'd' : j = new Diamant(); break;  //Diamant
		
		//LES MURS
		case 'w' : j = new MurBasique(); break;  //Mur
		case 'W' : j = new MurTitane(); break;  //Mur de Titane
		case 'M' : j = new MurMagique(); break;  //Mur Magique
		
		
		//LES ENNEMIS
		case 'a' : j = new Amibe(moteur); break;  //Amibe
		
		//L'EXPLOSION
		
		case 'E' : j = new Explosion(moteur); break; //Explosion
		
		default :
			if (apparence=='F'||apparence=='o'||apparence=='O'||apparence=='q'||apparence=='Q'){
				j = new Luciole(moteur); break ;  //Luciole
			}else if (apparence=='b'||apparence=='B'||apparence=='c'||apparence=='C'){
				j = new Libellule(moteur); break ;  //Libellule
			}
			
			// -------- CARACTERE NON RECONNU -------- //
			else{ j = new Espace() ; break ;}

		}
		return j;
	}
}
