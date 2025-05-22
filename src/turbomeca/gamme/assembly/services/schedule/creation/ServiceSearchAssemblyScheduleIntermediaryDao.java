package turbomeca.gamme.assembly.services.schedule.creation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.exception.CreateScheduleAssemblyException;
import turbomeca.gamme.assembly.services.schedule.creation.utils.XpathConstantsUtils;
import turbomeca.gamme.ecran.services.common.utils.xml.BinderUtils;
import turbomeca.gamme.ecran.services.model.constants.ModelGlobalConstants;
import turbomeca.gamme.ecran.services.publication.utils.VersionManager;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.model.dao.ScheduleLoader2;
import turbomeca.gamme.ecran.services.schedule.service.creation.IServiceScheduleIntermediaryDao;
import turbomeca.gamme.ecran.services.schedule.service.creation.ScheduleCreationDTO;

@Deprecated  // To be used for XML Synthesis ?? Do not forget to also remove ScheduleLoader2
public class ServiceSearchAssemblyScheduleIntermediaryDao implements IServiceScheduleIntermediaryDao {

	private static Logger logger = Logger.getLogger(ServiceSearchAssemblyScheduleIntermediaryDao.class);

	private static final String SCHEDULE_ASSEMBLY_TYPE = "assembly";
	private static final String SCHEDULE_NEW_CONTEXT = "new";

	@Override
	public ScheduleCreationDTO searchScheduleIntermediary(
			ScheduleCreationDTO scheduleCreation,
			ContextScheduleCreation context)
					throws Exception {

		ContextScheduleCreationAssembly contextEffectivities = (ContextScheduleCreationAssembly) context;
		HashMap<String, List<?>> listResult = new HashMap<String, List<?>>();
		HashMap<String, String> effectivitiesFromMethod = new HashMap<String, String>();
		List<Node> methodsNodes;
		String industrialValidation = "";
		String xpath = getXpathFromScheduleAndContext(scheduleCreation,
				context.isExternalCreation());
		logger.debug("ServiceSearchScheduleIntermediaryDao  - searchScheduleIntermediary xpath : "
				+ xpath);
		ScheduleLoader2 scheduleLoader = new ScheduleLoader2(
				contextEffectivities.getSynthesis());


		// Detect All Entity
		NodeList listEntity = scheduleLoader.findList(xpath);

		if (listEntity == null || listEntity.getLength() == 0) {
			throw new CreateScheduleAssemblyException(
					CreateScheduleAssemblyException.EXCEPTION_EFFECTIVITY_NOT_FOUND);
		}

		Node entity = null;
		String variant = "";
		Map<String, String> methodEntityMap = new HashMap<String, String>();
		// Get All Method From Entity
		for (int i = 0; i < listEntity.getLength(); i++) {
			Node current = listEntity.item(i);
			if (current.getNodeType() == Element.ELEMENT_NODE) {
				String effectivity = current.getAttributes().getNamedItem(ModelGlobalConstants.ATTR_FILE_NAME).getTextContent();
				NodeList listEffectivityVariant = (NodeList) scheduleLoader.findFromNode(
						XpathConstantsUtils.XPATH_SEARCH_EFFECTIVITY_VARIANT, current);
				if(listEffectivityVariant != null && listEffectivityVariant.getLength() > 0){
					List<Node> listVariant = BinderUtils.list(listEffectivityVariant);
					Node effectivityVariant = getVariantFromPn(listVariant, scheduleCreation.getPn(), scheduleLoader);
					if(effectivityVariant != null){
						variant = effectivityVariant.getAttributes().getNamedItem(ModelGlobalConstants.ATTR_VARIANT).getTextContent();
					}
				}
				NodeList listMethod = (NodeList) scheduleLoader.findFromNode(
						XpathConstantsUtils.XPATH_SEARCH_METHOD, current);
				if (listMethod != null && listMethod.getLength() > 0) {
					methodsNodes = BinderUtils.list(listMethod);
					if (effectivitiesFromMethod.containsKey(methodsNodes.get(0).getNodeValue())) {
						if (isLaterVersion(current, methodEntityMap.get(methodsNodes.get(0).getNodeValue()))) {
							effectivitiesFromMethod.put(methodsNodes.get(0).getNodeValue(), effectivity);
							methodEntityMap.put(methodsNodes.get(0).getNodeValue(), current.getAttributes().getNamedItem("version").getTextContent());
							entity = current;
						}
					} else {
						effectivitiesFromMethod.put(methodsNodes.get(0).getNodeValue(), effectivity);
						methodEntityMap.put(methodsNodes.get(0).getNodeValue(), current.getAttributes().getNamedItem("version").getTextContent());
						entity = current;
					}
				} else {
					entity = getLastVersionEntities(listEntity);
					break;
				}
			}
		}
		scheduleCreation.setMethodLastVersion(methodEntityMap);
		
		if(!context.isExternalCreation() && !variant.isEmpty() && context.getScheduleType().equals(SCHEDULE_ASSEMBLY_TYPE)&& context.getContext().equals(SCHEDULE_NEW_CONTEXT)){
			scheduleCreation.setVariant(variant);
		} 

		List<String> methods = new ArrayList<String>(effectivitiesFromMethod.keySet());

		NodeList listLevels = (NodeList) scheduleLoader.findFromNode(XpathConstantsUtils.XPATH_SEARCH_LEVEL, entity);
		NodeList listInterventions = (NodeList) scheduleLoader.findFromNode(XpathConstantsUtils.XPATH_SEARCH_INTERVENTION, entity);
		NodeList listInstances = (NodeList) scheduleLoader.findFromNode(XpathConstantsUtils.XPATH_SEARCH_INSTANCES, entity);

		listResult.put("levels", BinderUtils.list(listLevels));
		listResult.put("methods", methods);
		listResult.put("interventions", BinderUtils.list(listInterventions));
		listResult.put("instances", BinderUtils.list(listInstances));

		scheduleCreation.setFamily(entity.getAttributes().getNamedItem("family").getTextContent());
		Node suffixNode = entity.getAttributes().getNamedItem("suffix");
		scheduleCreation.setSuffix(suffixNode != null ? suffixNode.getTextContent() : null);
		
		if(entity.getAttributes()
				.getNamedItem("industrialValidation") != null) {
			industrialValidation = entity.getAttributes()
					.getNamedItem("industrialValidation").getTextContent();
		}
		scheduleCreation.setIndustrialValidation(industrialValidation);
		scheduleCreation.setMaterial(entity.getAttributes()
				.getNamedItem("material").getTextContent());
		scheduleCreation.setLastVersionPublish(entity.getAttributes()
				.getNamedItem("version").getTextContent());
		scheduleCreation.setScheduleIntermediaryFile(entity.getAttributes()
				.getNamedItem(ModelGlobalConstants.ATTR_FILE_NAME).getTextContent());
		scheduleCreation.setParameters(listResult);
		scheduleCreation.setEffectivityFileName(entity.getAttributes().getNamedItem(ModelGlobalConstants.ATTR_FILE_NAME).getTextContent());
		scheduleCreation.setMethodFromEffectivities(effectivitiesFromMethod);
		
		NodeList isSapScheduleNode = (NodeList) scheduleLoader.findFromNode(XpathConstantsUtils.XPATH_SEARCH_IS_SAP_SCHEDULE, entity);
		if (isSapScheduleNode != null && isSapScheduleNode.getLength() > 0) {
			Boolean isSapSchedule = Boolean.parseBoolean(isSapScheduleNode.item(0).getTextContent());
			scheduleCreation.setIsSapSchedule(isSapSchedule);
		}
		
		return scheduleCreation;
	}

	/**
	 * Get the node effectivityVariant that has this Pn
	 * @param listVariant
	 * @param pn
	 * @param scheduleLoader
	 * @return
	 */
	private Node getVariantFromPn(List<Node> listVariant, String pn, ScheduleLoader2 scheduleLoader) {
		Node result = null;
		for(Node effectivityVariant : listVariant){
			NodeList listPn = (NodeList) scheduleLoader.findFromNode(
					XpathConstantsUtils.XPATH_SEARCH_PN_FROM_EFFECTIVITY_VARIANT, effectivityVariant);
			if(listPn.getLength() > 0){
				List<Node> listNodePn = BinderUtils.list(listPn);
				for(Node nodePn : listNodePn){
					String valuePn = nodePn.getTextContent();
					if(valuePn != null && valuePn.equals(pn)){
						result = effectivityVariant;
						break;
					}
				}
			}
		}
		return result;
	}

	public Node getLastVersionEntities(NodeList nodeListEntities) {
		Node node = nodeListEntities.item(0);
		for (int i = 1; i < nodeListEntities.getLength(); i++) {
			Node current = nodeListEntities.item(i);
			if (isLaterVersion(current, node.getAttributes().getNamedItem("version").getTextContent())) {
				node = current;
			}
		}
		return node;
	}

	public boolean isLaterVersion(Node currentNode,  String previousVersion) {
		String currentVersion = currentNode.getAttributes().getNamedItem("version").getTextContent();
		return VersionManager.isLaterVersion(currentVersion, previousVersion);
	}

	private String getXpathFromScheduleAndContext(ScheduleCreationDTO dto,
			boolean externalCreation) {
		String xpathFormat;
		if (externalCreation && dto.getContext().equals(SCHEDULE_NEW_CONTEXT)) {

			xpathFormat = String.format(
					XpathConstantsUtils.XPATH_EXTERNAL_CREATION_NEW, dto.getLanguage()
					.toUpperCase(),dto.getSite().toUpperCase(), dto.getPn());

		} else {
			if (dto.getRangeType().equals(SCHEDULE_ASSEMBLY_TYPE) && dto.getContext().equals(SCHEDULE_NEW_CONTEXT)) {
				xpathFormat = String.format(XpathConstantsUtils.XPATH_ASSEMBLY_NEW, dto.getLanguage().toUpperCase(),dto.getSite().toUpperCase(),dto.getPn(), dto.getConf());

			} else {
				if (dto.getConf() == null || dto.getConf().length() == 0) {
					xpathFormat = String.format(XpathConstantsUtils.XPATH_OUT_MODIFICATION, dto.getLanguage().toUpperCase(),dto.getSite().toUpperCase(), dto.getVariant(), dto.getPn(), dto.getConf());
				} else {
					String xpathTemp = buildXpathDynamic(dto.getConf().split(" "),XpathConstantsUtils.XPATH_ASSEMBLY_DISASSEMBLY, XpathConstantsUtils.XPATH_END_XPATH);
					String xpathTempFormat = String.format(xpathTemp, dto.getLanguage().toUpperCase(),dto.getSite().toUpperCase(), dto.getVariant(), dto.getPn(), dto.getConf());
					xpathFormat = xpathTempFormat;
				}
			}
		}
		return xpathFormat;
	}

	public static String buildModificationFromCondition(String[] modifications,
			String operator) {
		String modifXpath = "modification ='%s'";
		String xpathFormat;
		StringBuilder buffer = new StringBuilder();

		for (int indexModif = 0; indexModif <= modifications.length; indexModif++) {

			if (indexModif != modifications.length) {
				if(indexModif==0){
					buffer.append("(");
				}
				xpathFormat = String.format(modifXpath, modifications[indexModif]);
				buffer.append(xpathFormat);

				if(indexModif != modifications.length-1){
					buffer.append(" " + operator + " ");
				}
			} else {
				buffer.append(") ");
				String endCountModification = null;
				if (operator.equals(XpathConstantsUtils.XPATH_OR_OPERATOR)) {
					endCountModification = String.format(XpathConstantsUtils.XPATH_COUNT_MODIFICATION_OR_MODE, modifications.length);
				} else {
					endCountModification = String.format(XpathConstantsUtils.XPATH_COUNT_MODIFICATION_AND_MODE, modifications.length);
				}
				buffer.append(endCountModification);
				buffer.append(")");
			}
		}
		return buffer.toString();
	}


	public static String buildXpathDynamic(String[] modifications, String currentXpath, String endXpath) {
		String andModeCondition = " or  (@andMode = 'true' and ";
		StringBuffer buffer = new StringBuffer();
		buffer.append(currentXpath);
		buffer.append(buildModificationFromCondition(modifications, XpathConstantsUtils.XPATH_OR_OPERATOR));
		buffer.append(andModeCondition);
		buffer.append(buildModificationFromCondition(modifications, XpathConstantsUtils.XPATH_AND_OPERATOR));
		buffer.append(endXpath);
		return buffer.toString();
	}

	@Override
	public List<String> getInformationFromScheduleIntermediary(
			String parentNode, String node, String attrNode,
			File fileIntermediary) throws Exception {
		return null;
	}

}
