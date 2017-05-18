package moteurJeu;

import java.util.List;

import com.sun.glass.events.KeyEvent;

public class Convertisseur {

	//Convertisseur.convertirggg_vers_fff('g');
	//ininstanciable
	private Convertisseur(){}
	
	//VK - DGHB
	
	/**
	 * Convertit des caracteres au format VK vers le format DGHB
	 * @param vk
	 * @return dghb
	 */
	public static char convertirVK_vers_DGHB(char vk){
		switch(vk){
		case KeyEvent.VK_RIGHT :return 'd';
		case KeyEvent.VK_LEFT :return 'g';
		case KeyEvent.VK_UP :return 'h';
		case KeyEvent.VK_DOWN :return 'b';
		default:return 'i';
		}
	}
	
	/**
	 * Convertit des caracteres au format DGHB vers le format VK
	 * @param dghb
	 * @return vk
	 */
	public static char convertirDGHB_vers_VK(char dghb){
		switch(dghb){
		case 'd' :return KeyEvent.VK_RIGHT;
		case 'g' :return KeyEvent.VK_LEFT;
		case 'h' :return KeyEvent.VK_UP;
		case 'b' :return KeyEvent.VK_DOWN;
		default:return KeyEvent.VK_0;
		}
	}
	
	/**
	 * Convertit un tableau de VK vers un tableau de DGHB
	 * @param tabChara
	 */
	public static void convTabVK_vers_DGHB(char[] tabChara){
		for(int i=0;i<tabChara.length;i++){
			tabChara[i]=Convertisseur.convertirVK_vers_DGHB(tabChara[i]);
		}
	}
	
	/**
	 * Convertit un tableau de DGHB vers un tableau de VK
	 * @param tabChara
	 */
	public static void convTabDGHB_vers_VK(char[] tabChara){
		for(int i=0;i<tabChara.length;i++){
			tabChara[i] = Convertisseur.convertirDGHB_vers_VK(tabChara[i]);
		}
	}
	
	public static void convTabDGHB_vers_VK(List<Character> listChara){
		for(int i=0;i<listChara.size();i++){
			listChara.set(i, Convertisseur.convertirDGHB_vers_VK(listChara.get(i)));
		}
	}
	
	//VK - 6482
	
	/**
	 * Convertit des caracteres au format vk vers le format 6482
	 * @param vk
	 * @return n6482
	 */
	public static char convertirVK_vers_6482(char vk){
		switch(vk){
		case KeyEvent.VK_RIGHT :return '6';
		case KeyEvent.VK_LEFT :return '4';
		case KeyEvent.VK_UP :return '8';
		case KeyEvent.VK_DOWN :return '2';
		default:return '5';
		}
	}
	
	/**
	 * Convertit des caracteres au format 6482 vers le format VK
	 * @param n6482
	 * @return vk
	 */
	public static char convertir6482_vers_VK(char n6482){
		switch(n6482){
		case '6' :return KeyEvent.VK_RIGHT;
		case '4' :return KeyEvent.VK_LEFT;
		case '8' :return KeyEvent.VK_UP;
		case '2' :return KeyEvent.VK_DOWN;
		default:return KeyEvent.VK_0;
		}
	}
	
	/**
	 * Convertit un tableau de VK vers un tableau de 6482
	 * @param tabChara
	 */
	public static void convTabVK_vers_6482(char[] tabChara){
		for(int i=0;i<tabChara.length;i++){
			tabChara[i]=Convertisseur.convertirVK_vers_6482(tabChara[i]);
		}
	}
	
	public static void convTabVK_vers_6482(List<Character> listChara){
		for(int i=0;i<listChara.size();i++){
			listChara.set(i, Convertisseur.convertirVK_vers_6482(listChara.get(i)));
		}
	}
	
	/**
	 * Convertit un tableau de 6482 vers un tableau de VK
	 * @param tabChara
	 */
	public static void convTab6482_vers_VK(char[] tabChara){
		for(int i=0;i<tabChara.length;i++){
			tabChara[i]=Convertisseur.convertir6482_vers_VK(tabChara[i]);
		}
	}
	
	//DGHB - 6482
	
	/**
	 * Convertit des caracteres au format DGHB vers le format 6482
	 * @param dghb
	 * @return n6482
	 */
	public static char convertirDGHB_vers_6482(char dghb){
		switch(dghb){
		case 'd' :return '6';
		case 'g' :return '4';
		case 'h' :return '8';
		case 'b' :return '2';
		default:return '5';
		}
	}
	
	/**
	 * Convertit des caracteres au format 6482 vers le format DGHB
	 * @param n6482
	 * @return dghb
	 */
	public static char convertir6482_vers_DGHB(char n6482){
		switch(n6482){
		case '6' :return 'd';
		case '4' :return 'g';
		case '8' :return 'h';
		case '2' :return 'b';
		default:return 'i';
		}
	}
	
	/**
	 * Convertit un tableau de DGHB vers un tableau de 6482
	 * @param tabChara
	 */
	public static void convTabDGHB_vers_6482(char[] tabChara){
		for(int i=0;i<tabChara.length;i++){
			tabChara[i]=Convertisseur.convertirDGHB_vers_6482(tabChara[i]);
		}
	}
	
	/**
	 * Convertit un tableau de 6482 vers un tableau de DGHB
	 * @param tabChara
	 */
	public static void convTab6482_vers_DGHB(char[] tabChara){
		for(int i=0;i<tabChara.length;i++){
			tabChara[i]=Convertisseur.convertir6482_vers_DGHB(tabChara[i]);
		}
	}
}
