package turbomeca.gamme.assembly.services.schedule.config;

import java.io.File;

import org.apache.log4j.Logger;

import turbomeca.gamme.assembly.services.schedule.creation.utils.AssemblyGlobalServices;
import turbomeca.gamme.assembly.services.schedule.edition.RangeEditor;
import turbomeca.gamme.assembly.services.schedule.loader.IWrapperRangeLoader;
import turbomeca.gamme.assembly.services.schedule.loader.WrapperRangeGenericLoader;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.config.ARepositoryConfig;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;
import turbomeca.gamme.ecran.services.publication.constants.FoldersConstants;

public class AssemblyRepositoryConfig extends ARepositoryConfig {
 
	private static Logger logger = Logger.getLogger(AssemblyRepositoryConfig.class);

    /**
	 * 
	 */
	private static final long serialVersionUID = -8050932475527667035L;

	/**
	 * Path official directory Assembly New
	 * 	{pub_synthesis_new_official}
	 */
	private String officialAssemblyNewPath;

	/**
	 * Path official directory Assembly Repaired
	 */
	private String officialAssemblyRepairedPath;

	/**
	 * path official Disassembly
	 */
	private String officialDisassemblyPath;
	
	/**
	 * Synthesis disAssembly
	 */
	private String synthesisDisassembly;
	
	/**
	 * Synthesis assemblyNew
	 */
	private String synthesisAssemblyNew;
	
	/**
	 * Synthesis assembly repaired
	 */
	private String synthesisAssemblyRepaired;

	/** */
	private RangeEditor rangeEditor;
	

	public RangeEditor getRangeEditor(ContextUser contextUser)
			throws RangeIoException {
		IWrapperRangeLoader wrapperLoader = new WrapperRangeGenericLoader(
				getSummaryFile());
		rangeEditor.setRangeWrapperLoader(wrapperLoader);
		return rangeEditor;
	}

	public RangeEditor getRangeSummaryEditor() throws RangeIoException {
		IWrapperRangeLoader wrapperLoader = null;
		wrapperLoader = new WrapperRangeGenericLoader(getSummaryFile());
		rangeEditor.setRangeWrapperLoader(wrapperLoader);
		return rangeEditor;
	}

	public void setRangeEditor(RangeEditor summaryEditor) {
		this.rangeEditor = summaryEditor;
	}

	public String getOfficialDirectory(int scheduleType, String context) {
		String directioryPath = null;
		if (scheduleType == AssemblyGlobalServices.SCHEDULE_IDENTIFIER_ASSEMBLY_TYPE) {
			if (context.equals("new")) {
				directioryPath = getOfficialAssemblyNewPath();
			} else {
				directioryPath = getOfficialAssemblyRepairedPath();
			}
		} else {
			directioryPath = getOfficialDisassemblyPath();
		}
		return directioryPath;
	}
	
	public String getSynthesisPath(int scheduleType, String context) {
		String synthesisFile = null;
		if (scheduleType == AssemblyGlobalServices.SCHEDULE_IDENTIFIER_ASSEMBLY_TYPE) {
			if (context.equals("new")) {
				synthesisFile = getSynthesisAssemblyNew();
			} else {
				synthesisFile = getSynthesisAssemblyRepaired();
			}
		} else {
			synthesisFile = getSynthesisDisassembly();
		}
		return synthesisFile;
	}
	
	@Override
	public File getPublicationSynthesisFile(String docType) {
		File pubSynthesisForDocType = null;
		if( FoldersConstants.ASSEMBLY_NEW_DOCTYPE.equals(docType)) {
			pubSynthesisForDocType = new File(getSynthesisAssemblyNew());
		} else if( FoldersConstants.ASSEMBLY_REPAIRED_DOCTYPE.equals(docType)) {
			pubSynthesisForDocType = new File(getSynthesisAssemblyRepaired());
		} else if( FoldersConstants.DISASSEMBLY_NEW_REPAIRED_DOCTYPE.equals(docType)) {
			pubSynthesisForDocType = new File(getSynthesisDisassembly());
		} else {
			logger.warn("Unkown docType : " + docType);
		}
		return pubSynthesisForDocType;
	}

	public String getOfficialAssemblyNewPath() {
		return officialAssemblyNewPath;
	}

	public String getOfficialAssemblyRepairedPath() {
		return officialAssemblyRepairedPath;
	}

	public String getOfficialDisassemblyPath() {
		return officialDisassemblyPath;
	}

	public void setOfficialAssemblyNewPath(String officialAssemblyNewPath) {
		this.officialAssemblyNewPath = officialAssemblyNewPath;
	}

	public void setOfficialAssemblyRepairedPath(
			String officialAssemblyRepairedPath) {
		this.officialAssemblyRepairedPath = officialAssemblyRepairedPath;
	}

	public void setOfficialDisassemblyPath(String officialDisassemblyPath) {
		this.officialDisassemblyPath = officialDisassemblyPath;
	}

	public String getSynthesisDisassembly() {
		return synthesisDisassembly;
	}

	public void setSynthesisDisassembly(String synthesisDisassembly) {
		this.synthesisDisassembly = synthesisDisassembly;
	}

	public String getSynthesisAssemblyNew() {
		return synthesisAssemblyNew;
	}

	public void setSynthesisAssemblyNew(String synthesisAssemblyNew) {
		this.synthesisAssemblyNew = synthesisAssemblyNew;
	}

	public String getSynthesisAssemblyRepaired() {
		return synthesisAssemblyRepaired;
	}

	public void setSynthesisAssemblyRepaired(String synthesisAssemblyRepaired) {
		this.synthesisAssemblyRepaired = synthesisAssemblyRepaired;
	}

	@Override
	public String getOfficialFolder(String scheduleType)
			throws RangeIoException {
		return null;
	}

}
