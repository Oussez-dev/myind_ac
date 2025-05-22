package turbomeca.gamme.assembly.services.notification;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.springframework.stereotype.Service;

import turbomeca.gamme.ecran.services.common.bean.ContextRange;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.utils.xml.XmlDocumentEditor;
import turbomeca.gamme.ecran.services.notification.bean.BufferNotification;
import turbomeca.gamme.ecran.services.notification.service.impl.AElectronicNotificationService;

@Service
public class AssemblyElectronicNotificationService extends AElectronicNotificationService {

	public AssemblyElectronicNotificationService() {

	}

	/**{@inheritDoc} */
	@Override
	protected String getRole(String type) {
        String role = null;
        if (type.equals(NOTIFICATION_TYPE_QUALITY)) {
            role = ROLE_NOTIFICATION_QUALITY;
        } else {
            role = ROLE_NOTIFICATION_METHOD;
        }
        return role;
    }
	
	@Override
	protected List<Element> getUserNotificationAutorized(ContextRange contextRange, String xmlUsersFlow,
			ContextUser contextUser, BufferNotification notification) throws Exception {
		String xpath = "users/user[electronicNotification[not(context)" + " or context[     @context = '%s'"
				+ " and (not(family) or family[@family='%s' and (not(site) or site='%s')])]]"
				+ " and roles[role = '%s']]";

		StringReader string = new StringReader(xmlUsersFlow);
		XmlDocumentEditor xmlDocumentEditorUser = new XmlDocumentEditor(string);
		String xpathFormat = String.format(xpath, contextRange.getType(), contextRange.getFamily(),
				contextUser.getWorkingSite(), getRole(notification.getType()));
		return xmlDocumentEditorUser.finds(xpathFormat);
	}

	@Override
	public List<String> getBodyInformations(ContextRange contextRange, ContextUser contextUser, 
			File schedule, BufferNotification notificatio) {
		List<String> bodyInformations = new ArrayList<String>();
		bodyInformations.add(contextUser.getWorkingSite());
		bodyInformations.add(contextRange.getFamily());
		bodyInformations.add(contextRange.getVariant());
		bodyInformations.add(contextRange.getMaterial());
		bodyInformations.add(contextRange.getOrder());
		return bodyInformations;
	}

}
