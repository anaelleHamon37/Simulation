package simulation;

public class ResultatSimulation {

	double courriels_non_traites;
	double appels_non_traites;
	double tps_attente_moyen;
	double tps_reponse_moyen;
	double taux_occupation_teleconseillers;
	double taux_occupation_postes;
	
	public ResultatSimulation(double courriels_non_traites, double appels_non_traites, double tps_attente_moyen, double tps_reponse_moyen,
			double taux_occupation_teleconseillers, double taux_occupation_postes) {
		super();
		this.courriels_non_traites = courriels_non_traites;
		this.appels_non_traites = appels_non_traites;
		this.tps_attente_moyen = tps_attente_moyen;
		this.tps_reponse_moyen = tps_reponse_moyen;
		this.taux_occupation_teleconseillers = taux_occupation_teleconseillers;
		this.taux_occupation_postes = taux_occupation_postes;
	}




	public void afficheResultats()	{
		System.out.println("Nombre de courriels non traités : "+courriels_non_traites);
		System.out.println("Nombre d'appels non traités : "+appels_non_traites);
		System.out.println("Temps d'attente moyen pour les appels : "+(tps_attente_moyen/60)+" minutes");
		System.out.println("Temps de réponse moyen pour les courriels : "+(tps_reponse_moyen/60)+" minutes");
		System.out.println("Taux d'occupation des téléconseillers : "+(taux_occupation_teleconseillers*100)+" %");
		System.out.println("Taux d'occupation des postes téléphoniques : "+(taux_occupation_postes*100)+" %");
	}
}
