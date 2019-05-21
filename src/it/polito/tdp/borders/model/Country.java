package it.polito.tdp.borders.model;

public class Country {
	
	private int CCode;
	private String stateAbb;
	private String stateNme;
	
	
	
	
	
	public Country(int cCode, String stateAbb, String stateNme) {
		super();
		CCode = cCode;
		this.stateAbb = stateAbb;
		this.stateNme = stateNme;
	}
	public int getCCode() {
		return CCode;
	}
	public void setCCode(int cCode) {
		CCode = cCode;
	}
	public String getStateAbb() {
		return stateAbb;
	}
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}
	public String getStateNme() {
		return stateNme;
	}
	public void setStateNme(String stateNme) {
		this.stateNme = stateNme;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CCode;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (CCode != other.CCode)
			return false;
		return true;
	}
	
	
	

}
