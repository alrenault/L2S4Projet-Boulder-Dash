package entite;

import java.util.List;

/**
 * Interface contenant la methode qui fera l'ensemble des actions d'une IA
 * @author PITROU Adrien
 * @author RENAULT Alexis
 * @author LEVEQUE Quentin
 */
public interface IA {
	/**
	 * Methode faisant l'ensemble des actions de l'IA
	 * @return Retourne la liste des touches pour former le chemin cree par l'IA
	 */
	public List<Character> actionList();
}
