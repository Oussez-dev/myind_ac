package turbomeca.gamme.assembly.services.schedule.merge;

import java.util.List;

import org.jdom.Element;

import turbomeca.gamme.ecran.services.common.utils.xml.JDomUtils;
import turbomeca.gamme.ecran.services.model.constants.ModelGlobalConstants;
import turbomeca.gamme.ecran.services.model.utils.ModelUtils;
import turbomeca.gamme.ecran.services.schedule.service.merge.ADefaultModelMerge;

public class AssemblyModelMerge extends ADefaultModelMerge {

	/* *** Assembly Model specific constants **************************** */
	private static final String TAG_ELECTRONIC_NOTIFICATION = "electronicNotification";
	private static final String TAG_ELECTRONIC_NOTIFICATIONS = "electronicNotifications";
	private static final String TAG_DOCUMENTS_JOINED = "documentsJoined";
	private static final String TAG_POSTIT= "electronicPostIt";
	
	private static final String TAG_OPERATIONS = "operations";
	private static final String TAG_OPERATION = "operation";
	private static final String TAG_SUBPHASES = "subPhases";
	private static final String TAG_SUBPHASE = "subPhase";
	
	private static final String STATUS_TODO = "TODO";
	private static final String STATUS_NOT_TODO = "NOT_TODO";
	private static final String STATUS_NOT_APPLICATED = "NOT_APPLICATED";
	private static final String STATUS_NONE = "NONE";
	private static final String STATUS_DONE = "DONE";
	
	
	/** The tag to search in sub-phase for inserting a new electronic notification
	 * Added notification is added after found tag
	 */
	private static final String[] SUBPHASE_TAGS_INSERT_AFTER_ELECTRONIC_NOTIF = 
			new String[] { "historical", "title", "title",  "optionalChoice", "infoSubPhase", "matrix", "unlockComment", ModelGlobalConstants.TAG_STATE};
	
	private static final String[] SUBPHASE_TAGS_INSERT_AFTER_DOCUMENTS_JOINED = 
			new String[] { TAG_ELECTRONIC_NOTIFICATION, "historical", "title", "title",  "optionalChoice", "infoSubPhase", "matrix", "unlockComment", ModelGlobalConstants.TAG_STATE};
	
	private static final String[] OPERATION_TAGS_INSERT_BEFORE_DOCUMENTS_JOINED = { ModelGlobalConstants.TAG_SUBPHASES };
	
	private static final String[] ROOT_TAGS_INSERT_AFTER_POSTIT= { ModelGlobalConstants.TAG_INSTANTIATION};
	
	private static final String[] DEFAULT_NODES = new String [] 
			{ ModelGlobalConstants.TAG_INSTANTIATION + "/" + ModelGlobalConstants.TAG_SN_UPPER_CASE + "/" + ModelGlobalConstants.TAG_TASK_ACTION
			, ModelGlobalConstants.TAG_OPERATIONS + "/" + ModelGlobalConstants.TAG_OPERATION + "/" + ModelGlobalConstants.TAG_SUBPHASES + "/" + ModelGlobalConstants.TAG_SUBPHASE
			, ModelGlobalConstants.TAG_OPERATIONS + "/" + ModelGlobalConstants.TAG_OPERATION + "/" + ModelGlobalConstants.TAG_SUBPHASES + "/" + ModelGlobalConstants.TAG_SUBPHASE_GROUP };
	

	public AssemblyModelMerge() {
		super();
	}

	
	@Override
	public boolean merge(String[] ids, String[] instances) {
		return merge(DEFAULT_NODES, ids, null, InsertionPolicy.AFTER_PREVIOUS_SIBLING);
	}
	

	/***
	 * Remember, this service is called by ADefaultModelMerge at end of each merge 
	 * 
	 * 
	 */
	@Override
	public boolean postMergeProcessing(String[] modifiedNodeNames, String[] modifiedIds, String[] deletedIds) {
		boolean success = true;
		updateOperationsComputeStatus();
		updateComputeStatus();
		
		success &= copyOrDeleteElement(TAG_POSTIT);
		return success;
	}
	
	/**
	 * Compute all operations status according to subphases status
	 * Editions are done on subPhases, so operations status must be updated after merging subPhases
	 * 
	 */
	protected void updateOperationsComputeStatus() {
		List<Element> operations = getDocumentDest().getRootElement().getChild(TAG_OPERATIONS).getChildren(TAG_OPERATION);
		
		for (Element operation : operations) {
			boolean operationDone = true;
			Element operationStatusNode = ModelUtils.getStateStatusChild(operation);
			
			if (operationStatusNode != null ) {
				List<Element> subPhases = operation.getChild(TAG_SUBPHASES).getChildren(TAG_SUBPHASE);
				// Read status of each subPhase
				for (Element subPhase : subPhases) {
					Element subPhaseStatusNode = ModelUtils.getStateStatusChild(subPhase);
					if (subPhaseStatusNode != null ) {
						String subPhaseStatus = subPhaseStatusNode.getTextTrim();
						// Test if subPhase not validated
						if (!subPhaseStatus.equals(STATUS_DONE) && !subPhaseStatus.equals(STATUS_NOT_TODO)
								&& !subPhaseStatus.equals(STATUS_NONE) && !subPhaseStatus.equals(STATUS_NOT_APPLICATED)) {
							operationDone = false;
							break;
						}
					}
				}
				// If all subPhases validated, operation is validated
				if (operationDone) {
					operationStatusNode.setText(STATUS_DONE);
				} else {
					operationStatusNode.setText(STATUS_TODO);
				}
			}
			
		}
		
	}
	
	/***
	 * Assembly specific re-implementation of how to locate tags to insert during merge operation
	 * 
	 */
	@Override
	protected Element findOrCreateDestinationElement(Element srcElement) {
		Element result = null;
		String nodeToInsertName = srcElement.getName();
		
		if(nodeToInsertName.equals(TAG_ELECTRONIC_NOTIFICATIONS)) {
				Element parentSubphase = findOrCreateDestinationParent(srcElement);
				result =  JDomUtils.getOrCreateElementIn(parentSubphase, TAG_ELECTRONIC_NOTIFICATIONS, 
						SUBPHASE_TAGS_INSERT_AFTER_ELECTRONIC_NOTIF, false);
		} else if (nodeToInsertName.equals(TAG_DOCUMENTS_JOINED)) {
			
			Element destParent = findOrCreateDestinationParent(srcElement);
			if(destParent!=null) {
				String parentElementName = destParent.getName();
				if ( parentElementName.equals(ModelGlobalConstants.TAG_OPERATION)) {
					result =  JDomUtils.getOrCreateElementIn(destParent, TAG_DOCUMENTS_JOINED, 
							OPERATION_TAGS_INSERT_BEFORE_DOCUMENTS_JOINED, true);
				} else {
					result =  JDomUtils.getOrCreateElementIn(destParent, TAG_DOCUMENTS_JOINED, 
							SUBPHASE_TAGS_INSERT_AFTER_DOCUMENTS_JOINED, false);
				}
			}
		} else if (nodeToInsertName.equals(TAG_POSTIT)) {
			result = JDomUtils.getOrCreateElementIn(getDocumentDest().getRootElement(), TAG_POSTIT, 
					ROOT_TAGS_INSERT_AFTER_POSTIT, false);
		}

		return result;
	}

	@Override
	public String[] getDefaultNodes() {
		return DEFAULT_NODES;
	}


}
