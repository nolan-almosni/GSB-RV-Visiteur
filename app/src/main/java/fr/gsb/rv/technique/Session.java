package fr.gsb.rv.technique;


import fr.gsb.rv.entites.Visiteur;

final class Session {
    private static Session session;
    private static fr.gsb.rv.entites.Visiteur visiteur;

    private Session(fr.gsb.rv.entites.Visiteur visiteur){
        this.visiteur = visiteur;
    }

    public static Session ouvrir(fr.gsb.rv.entites.Visiteur visiteur) {
        if (session == null){
            session = new Session(visiteur);

        }
        return session;
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
