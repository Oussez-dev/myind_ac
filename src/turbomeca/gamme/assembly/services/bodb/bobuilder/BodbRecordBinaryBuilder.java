package turbomeca.gamme.assembly.services.bodb.bobuilder;

import java.io.File;

import turbomeca.gamme.assembly.services.model.data.InputAction;
import turbomeca.gamme.assembly.services.model.data.InputDocument;
import turbomeca.gamme.ecran.services.bodb.bo.BoRecord;
import turbomeca.gamme.ecran.services.bodb.bo.BoRecordBinary;
import turbomeca.gamme.ecran.services.common.constants.GlobalConstants;
import turbomeca.gamme.ecran.services.common.utils.file.FileServerUtils;
import turbomeca.gamme.ecran.services.common.utils.file.FilesUtil;
import turbomeca.gamme.ecran.services.runtime.bean.ContextSynchronizeBoData;

public class BodbRecordBinaryBuilder {
	
	public static BoRecordBinary buildRecordBinary(InputAction inputAction, ContextSynchronizeBoData contextSynchroBOData, 
			BoRecord boRecord) {
		BoRecordBinary boRecordBinary = null;
		if (inputAction != null) {
			InputDocument inputDocument = (InputDocument) inputAction.getInputActionChoice().getInputDocument();
			if(inputDocument != null) {
				if(inputAction.getInputValue() != null && inputAction.getInputValue().getValue() != null){
					boRecordBinary = new BoRecordBinary();
					boRecordBinary.setBinaryName(inputAction.getInputValue().getValue());
					String pathDocument = "";
					if (contextSynchroBOData.getScheduleName() != null && !contextSynchroBOData.getScheduleName().isEmpty()
							&& contextSynchroBOData.getInputDocumentPath() != null && !contextSynchroBOData.getInputDocumentPath().isEmpty()) {
						pathDocument = FileServerUtils.getRepositoryImages(contextSynchroBOData.getScheduleName(), 
								contextSynchroBOData.getInputDocumentPath()) + GlobalConstants.SEPARATOR_SLASH 
								+ inputAction.getInputValue().getValue();
					}
					File fileDocument = new File(pathDocument);
					if(fileDocument.exists()) {
						byte[] docStream = FilesUtil.fileToByteArray(fileDocument);
						boRecordBinary.setBinary(docStream);
					}
					
				}
			}
			
		}
		return boRecordBinary;
	}
	
	public static BoRecordBinary buildRecordBinary(String idMeasureFile, ContextSynchronizeBoData contextSynchroBOData, BoRecord boRecord) {
		BoRecordBinary boRecordBinary = new BoRecordBinary();
		boRecordBinary.setBinaryName(idMeasureFile);
		String pathDocument = "";
		if (contextSynchroBOData.getScheduleName() != null && !contextSynchroBOData.getScheduleName().isEmpty()
				&& contextSynchroBOData.getInputDocumentPath() != null && !contextSynchroBOData.getInputDocumentPath().isEmpty()) {
			pathDocument = FileServerUtils.getRepositoryImages(contextSynchroBOData.getScheduleName(), 
					contextSynchroBOData.getInputDocumentPath()) + GlobalConstants.SEPARATOR_SLASH 
					+ idMeasureFile;
		}
		File fileDocument = new File(pathDocument);
		if(fileDocument.exists()) {
			byte[] docStream = FilesUtil.fileToByteArray(fileDocument);
			boRecordBinary.setBinary(docStream);
		}
		return boRecordBinary;
	}
	
}
