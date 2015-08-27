package com.conso_mag.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DateTime implements Serializable {
	int jour, mois, annee;
	int heure, minute, seconde;

	String regex_DateYMD = "^((?:19|20)[0-9][0-9])[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$";
	String regex_DateTime = "^((?:19|20)[0-9][0-9])[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])"
							+ "[ ]"
							+ "([0-9][0-9]:[0-9][0-9]:[0-9][0-9])$";
	
	
	public DateTime(String date) {
		if ( date.matches(regex_DateYMD) ){
			annee = Integer.parseInt(date.substring(0, 4));
			mois = Integer.parseInt(date.substring(5, 7));
			jour = Integer.parseInt(date.substring(8, 10));
		}
		
		if ( date.matches(regex_DateTime) ){
			annee = Integer.parseInt(date.substring(0, 4));
			mois = Integer.parseInt(date.substring(5, 7));
			jour = Integer.parseInt(date.substring(8, 10));
		}
	}

	public String afficher() {
		return jour +" "+ getAlphaMonth() + " " +annee;
	}
	
	public String getAlphaMonth(){
		String m = "";
		switch( mois ){
			case 1: m = "janvier";	break;
			case 2: m = "février";	break;
			case 3: m = "mars";		break;
			case 4: m = "avril";	break;
			case 5: m = "mai";		break;
			case 6: m = "juin";		break;
			case 7:	m = "juillet";	break;
			case 8:	m = "août";		break;
			case 9:	m = "septembre";break;
			case 10:m = "octobre";	break;
			case 11:m = "novembre";	break;
			case 12:m = "décembre";	break;
		}
		return m;
	}

}
