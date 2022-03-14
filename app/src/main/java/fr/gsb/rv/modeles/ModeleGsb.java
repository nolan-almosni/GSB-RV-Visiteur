package fr.gsb.rv.modeles;

import fr.gsb.rv.entites.Visiteur;

public class ModeleGsb {

    private static ModeleGsb modelesGsb = null;

    private void ModeleGsb() {
        modelesGsb = new ModeleGsb();
    }

    public static ModeleGsb getInstance() {
        return modelesGsb;
    }

    private void peupler(){

    }

    public Visiteur seConnecter(String matricule, String mdp){

        String matriculetest = "test";
        String mdptest = "azerty";

        if(matricule.equals(matriculetest) && mdp.equals(mdptest)){
            return new Visiteur(matricule,mdp);
        }
        return null;
    }

}
