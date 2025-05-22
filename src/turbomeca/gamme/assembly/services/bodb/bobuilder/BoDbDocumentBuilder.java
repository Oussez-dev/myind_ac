package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.assembly.services.bodb.utils.BoDbUtil;
import turbomeca.gamme.assembly.services.model.data.Document;
import turbomeca.gamme.assembly.services.model.data.Documents;
import turbomeca.gamme.ecran.services.bodb.bo.BoDocument;

public class BoDbDocumentBuilder {

	private BoDbDocumentBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}
	
	public static List<BoDocument> buildDocuments(Documents documents) {
		List<BoDocument> result = new ArrayList<BoDocument>();
		if (documents != null) {
			for (Document doc : documents.getDocument()) {
				result.add(buildDocument(doc));
			}
		}
		return result;
	}

	public static BoDocument buildDocument(Document document) {
		BoDocument boDocument = null;
		if(document != null) {
			boDocument = new BoDocument();
			boDocument.setUfiId(document.getId());
			boDocument.setReference(BoDbUtil.buildReference(document.getRef()));
			if (document.getDesignation() != null) {
				boDocument.setDesignation(BoDbUtil.buildTextFromParaList(document.getDesignation().getPara()));
			}
			boDocument.setType(document.getType());
			boDocument.setVersion(document.getVersion());
			if (document.getDate() != null) {
				boDocument.setDate(document.getDate().toDate());
			}
//			boDocument.setIsAlternative(document.I); FIXME TBD Alternative
		}
		
		return boDocument;
	}


}
