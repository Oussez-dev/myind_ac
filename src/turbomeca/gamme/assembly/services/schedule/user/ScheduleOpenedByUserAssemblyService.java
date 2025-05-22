package turbomeca.gamme.assembly.services.schedule.user;

import java.io.StringReader;
import java.util.List;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import turbomeca.gamme.ecran.services.schedule.bo.BoInstanciatedScheduleOpenedByUserAssembly;
import turbomeca.gamme.ecran.services.schedule.dao.DaoInstanciatedScheduleOpenedByUserAssembly;
import turbomeca.gamme.ecran.services.schedule.user.IScheduleOpenedByUserService;

/**
 * Service to check if a user has already instanciated an Assembly published
 * schedule using instanciation parameters: {PN, article, version} for the first
 * time. If not, it registers this opening in a specific DB table.
 * 
 * @author ademartin
 *
 */
@WebService(serviceName = "ScheduleOpenedByUserServiceService",
targetNamespace = "http://impl.service.runtime.services.ecran.gamme.turbomeca/",
endpointInterface = "turbomeca.gamme.ecran.services.schedule.user.IScheduleOpenedByUserService"
)
@Service(value = "scheduleOpenedByUserAssemblyService")
public class ScheduleOpenedByUserAssemblyService implements IScheduleOpenedByUserService {
	
	/** Logger */
	private static Logger logger = Logger.getLogger(ScheduleOpenedByUserAssemblyService.class);
	
	/** XPATH to find schedule PN */
	private static final String XPATH_FIND_SCHEDULE_PN = "/*/instanciation/pn/text()";
	
	/** XPATH to find schedule context */
	private static final String XPATH_FIND_SCHEDULE_CONTEXT = "/*/instanciation/context/text()";
	
	/** XPATH to find schedule article when in new context (must read schedule VA) */
	private static final String XPATH_FIND_SCHEDULE_ARTICLE_NEW = "/*/instanciation/article/text()";
	
	/** XPATH to find schedule article when in repaired context (must read schedule TUs)*/
	private static final String XPATH_FIND_SCHEDULE_ARTICLE_REPAIRED = "/*/instanciation/modifications/text()";
	
	/** Value for schedule repaired context */
	private static final String SCHEDULE_CONTEXT_REPAIRED_VALUE = "repaired";
	
	/** Value for schedule new context */
	private static final String SCHEDULE_CONTEXT_NEW_VALUE = "new";
	
	/**
	 * Service used to find if a user instanciates a published schedule for the first time or not
	 */
	@Autowired
	private DaoInstanciatedScheduleOpenedByUserAssembly daoScheduleOpenedByUser;
	
	@Override
	public Boolean registerScheduleIsOpenedByUser(String userLogin, String schedule, String scheduleVersion) {
		
		String pn = "";
		String article = "";
		String context = "";
		
		Document scheduleDoc = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(schedule));
			scheduleDoc = builder.parse(is);
		} catch (Exception e) {
			logger.debug("Unable to parse schedule stream: end process as if it is the first opening!"); 
			return false;
		}
		
		// Read effectivities from schedule stream
		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpathSearchPn = xPathFactory.newXPath();
			XPath xpathSearchContext = xPathFactory.newXPath();
			XPathExpression exprSearchPn = xpathSearchPn.compile(XPATH_FIND_SCHEDULE_PN);
			XPathExpression exprSearchContext = xpathSearchContext.compile(XPATH_FIND_SCHEDULE_CONTEXT);
			
			pn = exprSearchPn.evaluate(scheduleDoc);
			context = exprSearchContext.evaluate(scheduleDoc);
			
			if(SCHEDULE_CONTEXT_NEW_VALUE.equals(context)) {
				XPath xpathSearchArticle = xPathFactory.newXPath();
				XPathExpression exprArticle = xpathSearchArticle.compile(XPATH_FIND_SCHEDULE_ARTICLE_NEW);
				article = exprArticle.evaluate(scheduleDoc);
			} else if(SCHEDULE_CONTEXT_REPAIRED_VALUE.equals(context)) {
				XPath xpathSearchArticle = xPathFactory.newXPath();
				XPathExpression exprArticle = xpathSearchArticle.compile(XPATH_FIND_SCHEDULE_ARTICLE_REPAIRED);
				article = exprArticle.evaluate(scheduleDoc);
			} else {
				logger.debug("Unable to get schedule context: end process as if it is the first opening!");
				return false;
			}
		
		} 
		catch(Exception e) {
			logger.debug("Unable to parse schedule stream to retrieve instanciation parameters: end process as if it is the first opening!"); 
			return false;
		}
		
		// Check, register (if needed) and return first opening information
		logger.debug("Check schedule already opened using [userLogin=" + userLogin + ", context=" + context
				+ ", schedulePn=" + pn + ", scheduleArticle=" + article + ", scheduleVersion=" + scheduleVersion +"]");
		
		if(isAlreadyRegistered(userLogin, pn, article, scheduleVersion)) {
			return true;
		}
		
		BoInstanciatedScheduleOpenedByUserAssembly boRegister = new BoInstanciatedScheduleOpenedByUserAssembly();
		boRegister.setUserLogin(userLogin);
		boRegister.setSchedulePn(pn);
		boRegister.setScheduleArticle(article);
		boRegister.setScheduleVersion(scheduleVersion);
		
		daoScheduleOpenedByUser.save(boRegister);
		
		return false;
	}

	private boolean isAlreadyRegistered(String userLogin, String schedulePn, String scheduleArticle, String scheduleVersion) {
		
		List<BoInstanciatedScheduleOpenedByUserAssembly> bos = daoScheduleOpenedByUser.findByUserLoginAndSchedulePnAndScheduleArticleAndScheduleVersion(
				userLogin, schedulePn, scheduleArticle, scheduleVersion);
	
		if(bos == null || bos.isEmpty()) {
			return false;
		}
		
		return true;
	}
}
