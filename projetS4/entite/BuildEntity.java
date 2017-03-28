package projetS4.entite;


public class BuildEntity{

	public Entite buildEntity(char apparence){
		
		Entite j;
		
		
		switch(apparence){
		
		//CASES UNIQUES
		case 'P' : j = new Joueur();//Rockford
		case 'X' : j = new Exit();//Exit
		
		//CASES MULTIPLES
		case ' ' : j = new Espace() ;//Espace
		case '.' : j = new Poussiere(); //Poussiere
		case 'r' : j = new Roc();//Roc
		case 'd' : j = new Diamant();//Diamant
		
		//LES MURS
		case 'w' : j = new MurBasique();//Mur
		case 'W' : j = new MurTitane();//Mur de Titane
		case 'M' : j = new MurMagique();//Mur Magique
		
		
		//LES ENNEMIS
		case 'a' : j = new Amibe(); break;//Amibe
		
		default :
			if (apparence=='F'||apparence=='o'||apparence=='O'||apparence=='q'||apparence=='Q'){
				j = new Luciole(); break ;
			}else if (apparence=='b'||apparence=='B'||apparence=='c'||apparence=='C'){
				j = new Libellule(); break ;
			}
			
			// -------- CARACTERE NON RECONNU --------
			else{ j = new Espace() ; break ;}

		}
		return j;
	}
	
	


}