package fr.gsb.rv.entites;

public class Visiteur {

    private String matricule;
    private String nom;
    private String prenom;
    private String mdp;

    public Visiteur(String matricule, String nom, String prenom, String mdp) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }

    public Visiteur(){
    }

    public Visiteur(String matricule, String mdp) {
        this.matricule = matricule;
        this.mdp = mdp;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String toString() {
        return nom +" "+ prenom + " ("+ matricule +")";
    }
}