package simulation;

import java.text.DecimalFormat;
import java.util.Vector;

public class Main {


	public static void main(String[] args) {

		double quantile = 1.960;
		
		Vector<ResultatSimulation> resultats = new Vector<ResultatSimulation>();
		int nbTests = 10000;

		int n = 10, nt = 1, nt_max = 6;
		while(nbTests != 0)
		{
			Simulateur simulateur = new Simulateur(n, nt, nt_max);
			simulateur.insertNewEvent(new DebutSimulation(0));
			while(!simulateur.isFinished())
				simulateur = simulateur.doNextEvent();
			resultats.add(simulateur.resultat);
			nbTests --;
		}
		
		nbTests = resultats.size();

		// calcul moyenne
		
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

		courriels_non_traites /= nbTests;
		appels_non_traites /= nbTests;
		tps_attente_moyen /= nbTests;
		tps_reponse_moyen /= nbTests;
		taux_occupation_teleconseillers /= nbTests;
		taux_occupation_postes /= nbTests;

		// calcul de l'estimateur non biaisé de la variance

		double var_courriels_non_traites = 0.;
		double var_appels_non_traites = 0.;
		double var_tps_attente_moyen = 0.;
		double var_tps_reponse_moyen = 0.;
		double var_taux_occupation_teleconseillers = 0.;
		double var_taux_occupation_postes = 0.;
		
		for(int i = 0; i < resultats.size(); i++)
		{
			ResultatSimulation res = resultats.get(i);
			var_courriels_non_traites += Math.pow(res.courriels_non_traites-courriels_non_traites,2);
			var_appels_non_traites += Math.pow(res.appels_non_traites-appels_non_traites,2);
			var_tps_attente_moyen += Math.pow(res.tps_attente_moyen-tps_attente_moyen,2);
			var_tps_reponse_moyen += Math.pow(res.tps_reponse_moyen-tps_reponse_moyen,2);
			var_taux_occupation_teleconseillers += Math.pow(res.taux_occupation_teleconseillers-taux_occupation_teleconseillers,2);
			var_taux_occupation_postes += Math.pow(res.taux_occupation_postes-taux_occupation_postes,2);
		}
		
		var_courriels_non_traites /= nbTests-1;
		var_appels_non_traites /= nbTests-1;
		var_tps_attente_moyen /= nbTests-1;
		var_tps_reponse_moyen /= nbTests-1;
		var_taux_occupation_teleconseillers /= nbTests-1;
		var_taux_occupation_postes /= nbTests-1;
		
		//calcul des intervalles de confiances

		double min_courriels_non_traites = courriels_non_traites - quantile*Math.sqrt(var_courriels_non_traites/nbTests);
		double max_courriels_non_traites = courriels_non_traites + quantile*Math.sqrt(var_courriels_non_traites/nbTests);
		
		double min_appels_non_traites = appels_non_traites - quantile*Math.sqrt(var_appels_non_traites/nbTests);
		double max_appels_non_traites = appels_non_traites + quantile*Math.sqrt(var_appels_non_traites/nbTests);
		
		double min_tps_attente_moyen = tps_attente_moyen - quantile*Math.sqrt(var_tps_attente_moyen/nbTests);
		double max_tps_attente_moyen = tps_attente_moyen + quantile*Math.sqrt(var_tps_attente_moyen/nbTests);
		
		double min_tps_reponse_moyen = tps_reponse_moyen - quantile*Math.sqrt(var_tps_reponse_moyen/nbTests);
		double max_tps_reponse_moyen = tps_reponse_moyen + quantile*Math.sqrt(var_tps_reponse_moyen/nbTests);
		
		double min_taux_occupation_teleconseillers = taux_occupation_teleconseillers - quantile*Math.sqrt(var_taux_occupation_teleconseillers/nbTests);
		double max_taux_occupation_teleconseillers = taux_occupation_teleconseillers + quantile*Math.sqrt(var_taux_occupation_teleconseillers/nbTests);
		
		double min_taux_occupation_postes = taux_occupation_postes - quantile*Math.sqrt(var_taux_occupation_postes/nbTests);
		double max_taux_occupation_postes = taux_occupation_postes + quantile*Math.sqrt(var_taux_occupation_postes/nbTests);
		
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);
		
		System.out.println("Résultats simulation (N="+n+",Nt="+nt+",Nt_max="+nt_max+") exécutée "+resultats.size()+" fois :");
		System.out.println("Intervalles de confiance à 95% pour la moyenne de : ");
		System.out.println(" - nombre de courriels non traités : ["+df.format(min_courriels_non_traites)+";"+df.format(max_courriels_non_traites)+"]");
		System.out.println(" - nombre d'appels non traités : ["+df.format(min_appels_non_traites)+";"+df.format(max_appels_non_traites)+"]");
		System.out.println(" - temps d'attente moyen pour les appels (en min) : ["+df.format(min_tps_attente_moyen/60)+";"+df.format(max_tps_attente_moyen/60)+"]");
		System.out.println(" - temps de réponse moyen pour les courriels (en min) : ["+df.format(min_tps_reponse_moyen/60)+";"+df.format(max_tps_reponse_moyen/60)+"]");
		System.out.println(" - taux d'occupation des téléconseillers (%) : ["+df.format(min_taux_occupation_teleconseillers*100)+";"+df.format(max_taux_occupation_teleconseillers*100)+"]");
		System.out.println(" - taux d'occupation des postes téléphoniques (%) : ["+df.format(min_taux_occupation_postes*100)+";"+df.format(max_taux_occupation_postes*100)+"]");
	}

}
