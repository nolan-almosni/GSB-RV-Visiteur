package fr.gsb.rv.technique;


import fr.gsb.rv.entites.Visiteur;

public class Session {
    private static Session session = null;
    private static Visiteur visiteur;

    private Session(Visiteur visiteur){
        this.visiteur = visiteur;
    }

    public static void ouvrir(Visiteur visiteur) {
        if (session == null){
            session = new Session(visiteur);
        }
    }

    public static void fermer(){
        visiteur = null;
        session = null;
    }

    public static Session getSession() {
        return session;
    }

    public Visiteur getVisiteur() {
        return visiteur;
    }

    public boolean estOuverte() {
        if(session != null){
            return true;
        }else{
            return false;
        }
    }
}
