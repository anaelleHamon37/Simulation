package simulation;

import java.util.Vector;

public class Main {


	public static void main(String[] args) {

		Vector<ResultatSimulation> resultats = new Vector<ResultatSimulation>();
		int nbTests = 10000;

		while(nbTests != 0)
		{
			int n = 10, nt = 5, nt_max = 10;
			Simulateur simulateur = new Simulateur(n, nt, nt_max);
			simulateur.insertNewEvent(new DebutSimulation(0));
			while(!simulateur.isFinished())
				simulateur = simulateur.doNextEvent();
			resultats.add(simulateur.resultat);
			nbTests --;
		}

		double courriels_non_traites = 0.;
		double appels_non_traites = 0.;
		double tps_attente_moyen = 0.;
		double tps_reponse_moyen = 0.;
		double taux_occupation_teleconseillers = 0.;
		double taux_occupation_postes = 0.;
		
		for(int i = 0; i < resultats.size(); i++)
		{
			ResultatSimulation res = resultats.get(i);
			courriels_non_traites += res.courriels_non_traites;
			appels_non_traites += res.appels_non_traites;
			tps_attente_moyen += res.tps_attente_moyen;
			tps_reponse_moyen += res.tps_reponse_moyen;
			taux_occupation_teleconseillers += res.taux_occupation_teleconseillers;
			taux_occupation_postes += res.taux_occupation_postes;
			
		}

		courriels_non_traites /= resultats.size();
		appels_non_traites /= resultats.size();
		tps_attente_moyen /= resultats.size();
		tps_reponse_moyen /= resultats.size();
		taux_occupation_teleconseillers /= resultats.size();
		taux_occupation_postes /= resultats.size();
		
		ResultatSimulation resultat = new ResultatSimulation(courriels_non_traites, appels_non_traites,
				tps_attente_moyen, tps_reponse_moyen, 
				taux_occupation_teleconseillers, taux_occupation_postes);
		System.out.println("Résultats simulation exécutée "+resultats.size()+" fois :");
		resultat.afficheResultats();
	}

}
