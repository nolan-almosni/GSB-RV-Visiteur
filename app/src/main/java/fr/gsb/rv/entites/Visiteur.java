package fr.gsb.rv.entites;

public class Visiteur {

    private String Matricule;
    private String Nom;
    private String Prenom;
    private String Mdp;

    public Visiteur(String matricule, String nom, String prenom, String Mdp) {
        this.Matricule = matricule;
        this.Nom = nom;
        this.Prenom = prenom;
        this.Mdp = Mdp;
    }

    public Visiteur(){
    }

    public Visiteur(String matricule, String mdp) {
        this.Matricule = matricule;
        this.Mdp = mdp;
    }

    public String getMatricule() {
        return Matricule;
    }

    public void setMatricule(String matricule) {
        Matricule = matricule;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getMdp() {
        return Mdp;
    }

    public void setMdp(String mdp) {
        Mdp = mdp;
    }
}