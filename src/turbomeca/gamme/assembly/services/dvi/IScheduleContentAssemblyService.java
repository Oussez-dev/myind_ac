package turbomeca.gamme.assembly.services.dvi;

import javax.jws.WebMethod;
import javax.jws.WebService;

import turbomeca.gamme.ecran.services.bodvi.bo.BoDviResultDocument;


@WebService(endpointInterface="turbomeca.gamme.assembly.services.dvi.IScheduleContentAssemblyService", name="ScheduleContentService")
public interface IScheduleContentAssemblyService {

	/**
	 * Return DVI document from an intermediate schedule, using an article
	 * <br>
	 * @param articlePn Product Number - Mandatory
	 * @param va the schedule va
	 * @param family the schedule family
	 * @param variant the schedule variant
	 * @param material the schedule material
	 * @param isolated the schedule isolated flag
	 * @param referential <code>0</code> for OFFICIAL, <code>1</code> for TEST, <code>-1</code> for unknown (official then test will be evaluated) 
	 * @param site Safran HE origin site - Mandatory
	 * @param lang TODO
	 * @return A DVI Document
	 */
	@WebMethod
	public BoDviResultDocument getScheduleStructureFromArticle(String articlePn, String va, String family, String variant, String material,  Boolean isolated, String referential, String site, String lang);
	
	/**
	 * Return DVI document from an intermediate schedule, using a geode reference
	 * 
	 * @param geodeRef Geode Reference - Mandatory
	 * @param geodeVersion Geode Version - Mandatory
	 * @param referential <code>0</code> for OFFICIAL, <code>1</code> for TEST, <code>-1</code> for unknown (official then test will be evaluated) 
	 * @return A DVI Document
	 */
	@WebMethod
	public BoDviResultDocument getScheduleStructureFromGeode(String geodeRef, String geodeVersion, String referential);
	
	/**
	 * Return DVI document from an instanciated schedule, using an order reference.
	 * <br>If <code>sn</code> parameter is filled, result will be filtered using this serial number.
	 * 
	 * @param order Order number - Mandatory
	 * @param referential  <code>0</code> for OFFICIAL, <code>1</code> for TEST, <code>-1</code> for unknown (official then test will be evaluated) 
	 * @param sn A serial number to filter
	 * @return  A DVI Document
	 */
	@WebMethod
	public BoDviResultDocument getScheduleContent(String order, String referential, String sn);
	
}
