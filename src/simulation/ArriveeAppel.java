package simulation;

public class ArriveeAppel extends Evenement{

	public ArriveeAppel(int date)	{
		date_simulation = date;
	}
	
	@Override
	public Simulateur doEvent(Simulateur simulateur) {
		System.out.println(date_simulation+" : Arrivée appel");
		simulateur.dateSimu = date_simulation;
		simulateur.a++;
		
		if(simulateur.nt_dispo == 0)
			simulateur.qt.add(date_simulation);
		else	{
			simulateur.nt_dispo--;
			simulateur.insertNewEvent(new FinTraitementAppel(date_simulation + Probabilites.uniforme(5, 15)));
		}
		
		int date_suite = date_simulation;
		if(date_simulation < 3600)
			date_suite += Probabilites.exponentielle(300);
		else if(date_simulation < 10800)
			date_suite += Probabilites.exponentielle(60);
		else
			date_suite += Probabilites.exponentielle(600);
		
		simulateur.insertNewEvent(new ArriveeAppel(date_suite));
		
		simulateur.majAires();
		
		return simulateur;
		
	}
	

}
