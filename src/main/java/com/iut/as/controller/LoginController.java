package com.iut.as.controller;

import static org.apache.log4j.Logger.getLogger;

import java.util.List;

import org.apache.log4j.Logger;

import com.iut.as.controller.facade.BankManager;
import com.iut.as.exceptions.BankBusinessException;
import com.iut.as.modele.Client;
import com.iut.as.modele.Compte;
import com.opensymphony.xwork2.ActionSupport;

/***
 * @description : Le 1er controleur de l'application :)
 * 
 * @author stephane.joyeux
 *
 */
public class LoginController extends ActionSupport {

	private static final long serialVersionUID = 5540616014690763867L;

	// Lien vers la facade :
	private BankManager manager;

	private static final Logger logger = getLogger(LoginController.class);

	// Informations de Login :
	private String numeroClient;

	private String userCde;
	private String userPwd;
	private String message;

	// Le client connecté :
	private Client clientConnecte;
	private List<Compte> comptesClient;
	
	//Le compte sélectionné
	private String numeroCompte;

	public String getNumeroCompte() {
		return numeroCompte;
	}
	
	//Les différents montants
	private String montantCredit;
	public String getMontantCredit() {
		return montantCredit;
	}

	public void setMontantCredit(String montantCredit) {
		this.montantCredit = montantCredit;
	}

	private String montantDebit;

	public String getMontantDebit() {
		return montantDebit;
	}

	public void setMontantDebit(String montantDebit) {
		this.montantDebit = montantDebit;
	}

	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public List<Compte> getComptesClient() {
		return comptesClient;
	}

	public void setComptesClient(List<Compte> comptesClient) {
		this.comptesClient = comptesClient;
	}

	
	
	
	private boolean caseMontantCredit;
	
	public boolean isCaseMontantCredit() {
		return caseMontantCredit;
	}
	
private boolean caseMontantDebit;
	
	public boolean isCaseMontantDebit() {
		return caseMontantDebit;
	}
	
	
	
	private Double soldeCompte;
	
	
	public Double getSolde() {
		return soldeCompte;
	}

	public void setSolde(Double solde) {
		this.soldeCompte = solde;
	}

	// On a récupéré la liste des comptes :
	private boolean listeComptesOk;

	public boolean isListeComptesOk() {
		return listeComptesOk;
	}

	public String getNumeroClient() {
		return numeroClient;
	}

	public void setNumeroClient(String numeroClient) {
		this.numeroClient = numeroClient;
	}

	/***
	 * Constructeur principal :
	 * 
	 * @throws Exception
	 */
	public LoginController() throws Exception {
		// On crée une instance de bank manager;
		manager = new BankManager();
		listeComptesOk = false;
	}

	public Client getClientConnecte() {
		return clientConnecte;
	}

	public void setClientConnecte(Client clientConnecte) {
		this.clientConnecte = clientConnecte;
	}

	public String getUserCde() {
		return userCde;
	}

	public void setUserCde(String userCde) {
		this.userCde = userCde;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * Utilisation de la fonction quand click sur le bouton 'submit' de la page
	 * index.jsp
	 */

	public String connection() {
		logger.info("Le paramètre 'userCde' = " + this.userCde);
		logger.info("Le paramètre 'userPwd' = " + this.userPwd);
		try {
			Client client = manager.userIsAllowed(userCde, userPwd);
			setMessage("user est autorisé");
			logger.info(client.toString());
			setClientConnecte(client);
			return ActionSupport.SUCCESS;
		} catch (BankBusinessException e) {
			setMessage("user est non autorisé");
			return ActionSupport.ERROR;
		}
	}

	public String listeComptes() {
		logger.info("Demande de la liste des comptes ... pour le client : " + this.numeroClient);
		setComptesClient(manager.getComptesByClient(numeroClient));
		listeComptesOk = true;
		return ActionSupport.SUCCESS;
	}
	
	public String caseCrediterCompte() {
		logger.info("demande du montant à créditer" + this.numeroClient);
		setNumeroCompte(numeroCompte);
		caseMontantCredit = true;
		return ActionSupport.SUCCESS;
	}
	
	public String crediterCompte() {
		logger.info("mise à jour du montant du compte");
		Compte compte = manager.getCompteById(numeroCompte);
		double montant=0;
		try {
			montant=Double.parseDouble(montantCredit);
		}catch(Exception e) {
			logger.info("Ce n'est pas un montant");
		}
		compte.crediter(montant);
		manager.updateCompte(compte);
		setSolde(compte.getSolde());
		logger.info("test");
		logger.info("nouveau solde : "+ this.soldeCompte);
		return ActionSupport.SUCCESS;
	}
	
	public String caseDebiterCompte() {
		logger.info("demande du montant à débiter" + this.numeroClient);
		setNumeroCompte(numeroCompte);
		caseMontantDebit = true;
		return ActionSupport.SUCCESS;
	}
	
	public String debiterCompte() {
		logger.info("mise à jour du montant du compte");
		Compte compte = manager.getCompteById(numeroCompte);
		double montant=0;
		try {
			montant=Double.parseDouble(montantDebit);
		}catch(Exception e) {
			logger.info("Ce n'est pas un montant");
		}
		compte.debiter(montant);
		manager.updateCompte(compte);
		setSolde(compte.getSolde());
		logger.info("test");
		logger.info("nouveau solde : "+ this.soldeCompte);
		return ActionSupport.SUCCESS;
	}
}
