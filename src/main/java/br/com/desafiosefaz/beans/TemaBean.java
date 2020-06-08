package br.com.desafiosefaz.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "temaBean")
public class TemaBean {

	private String tema = "bootstrap";

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}
	
	public String[] getThemes() {
		return new String[] {
				"afterdark",
				"afternoon",
				"afterwork",
				"black-tie",
				"blitzer",
				"bluesky",
				"bootstrap",
				"casablanca",
				"cruze",
				"cupertino",
				"dark-hive",
				"dot-luv",
				"eggplant",
				"excite-bike",
				"flick",
				"glass-x",
				"home",
				"hot-sneaks",
				"humanity",
				"le-frog",
				"midnight",
				"mint-choc",
				"overcast",
				"pepper-grinder",
				"redmond",
				"rocket",
				"sam",
				"smoothness",
				"south-street",
				"start",
				"sunny",
				"swanky-purse",
				"trontastic",
				"ui-darkness",
				"ui-lightness",
				"vader"
		};
	}
}
