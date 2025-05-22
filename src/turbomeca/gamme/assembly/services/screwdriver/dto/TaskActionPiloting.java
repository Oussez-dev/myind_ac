package turbomeca.gamme.assembly.services.screwdriver.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "TaskActionPiloting")
public class TaskActionPiloting {
	private String operator;

	private List<ResultPiloting> pilotingResults;
	
	private String status;
	
    @XmlElement
	public String getOperator() {
		return operator;
	}

	@XmlElementWrapper(name = "pilotingResults")
    @XmlElement(name="pilotingResult")
	public List<ResultPiloting> getPilotingResults() {
		return pilotingResults;
	}

	public void setOperator(String loginOperator) {
		this.operator = loginOperator;
	}

	public void setPilotingResults(List<ResultPiloting> screws) {
		this.pilotingResults = screws;
	}

    @XmlElement
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
