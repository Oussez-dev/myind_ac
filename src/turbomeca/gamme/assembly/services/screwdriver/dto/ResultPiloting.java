package turbomeca.gamme.assembly.services.screwdriver.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pilotingResult")  
public class ResultPiloting {
	private String value;
	private String message;
	private double torqueMin;
	private double torqueMax;
	private double torqueExpected;
	
	
	@XmlElement
	public String getMessage() {
		return message;
	}

	@XmlElement
	public String getValue() {
		return value;
	}

	@XmlElement
	public double getTorqueMin() {
		return torqueMin;
	}

	@XmlElement
	public double getTorqueMax() {
		return torqueMax;
	}

	@XmlElement(name="torqueExpected")
	public double getTorqueExpected() {
		return torqueExpected;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setTorqueMin(double torqueMin) {
		this.torqueMin = torqueMin;
	}

	public void setTorqueMax(double torqueMax) {
		this.torqueMax = torqueMax;
	}

	public void setTorqueExpected(double torqueExpected) {
		this.torqueExpected = torqueExpected;
	}

}
