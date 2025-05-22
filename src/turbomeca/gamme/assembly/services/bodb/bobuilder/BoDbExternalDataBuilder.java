package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import turbomeca.gamme.assembly.services.model.data.DocumentJoined;
import turbomeca.gamme.assembly.services.model.data.DocumentsJoined;
import turbomeca.gamme.ecran.services.bodb.bo.BoExternalData;
import turbomeca.gamme.ecran.services.bodb.bo.BoSubPhase;
import turbomeca.gamme.ecran.services.common.constants.GlobalConstants;
import turbomeca.gamme.ecran.services.common.utils.file.FileServerUtils;
import turbomeca.gamme.ecran.services.common.utils.file.FilesUtil;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;

public class BoDbExternalDataBuilder {
	
	private BoDbExternalDataBuilder() {
		throw new IllegalStateException("Utility class"); //$NON-NLS-1$
	}
	
	public static List<BoExternalData> buildExternalData(DocumentsJoined documentsJoined, 
			ContextSynchronizeBoData contextSynchroBOData, BoSubPhase boSubPhase) {
		List<BoExternalData> externalData = new ArrayList<BoExternalData>();
		BoExternalData externalDataBo = null;
		if (documentsJoined != null) {
			for (DocumentJoined docJoined : documentsJoined.getDocumentJoined()) {
				externalDataBo = new BoExternalData();
				externalDataBo.setName(docJoined.getName());
				externalDataBo.setSubPhase(boSubPhase);
				String pathDocument = "";
				if (contextSynchroBOData.getScheduleName() != null && !contextSynchroBOData.getScheduleName().isEmpty()
						&& contextSynchroBOData.getInputDocumentPath() != null && !contextSynchroBOData.getInputDocumentPath().isEmpty()) {
					pathDocument = FileServerUtils.getRepositoryImages(contextSynchroBOData.getScheduleName(), 
							contextSynchroBOData.getInputDocumentPath()) + GlobalConstants.SEPARATOR_SLASH + docJoined.getId();
				}
				File fileDocument = new File(pathDocument);
				if(fileDocument.exists()) {
					byte[] docStream = FilesUtil.fileToByteArray(new File(pathDocument));
					externalDataBo.setDocument(docStream);
					externalData.add(externalDataBo);	
				}
			}
		}
		return externalData;
	}
	
}
