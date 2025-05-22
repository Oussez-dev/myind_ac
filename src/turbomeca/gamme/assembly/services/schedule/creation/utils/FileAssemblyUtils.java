package turbomeca.gamme.assembly.services.schedule.creation.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextScheduleCreationAssembly;
import turbomeca.gamme.assembly.services.schedule.creation.bean.ContextUserAssemblyServer;
import turbomeca.gamme.assembly.services.utils.DocTypeUtils;
import turbomeca.gamme.ecran.services.common.constants.GlobalConstants;
import turbomeca.gamme.ecran.services.common.enumtypes.ScheduleState;
import turbomeca.gamme.ecran.services.common.utils.comparator.AlphaNumericComparator;
import turbomeca.gamme.ecran.services.common.utils.file.FileLocker;
import turbomeca.gamme.ecran.services.publication.constants.FoldersConstants;
import turbomeca.gamme.ecran.services.publication.utils.IFolderService;
import turbomeca.gamme.ecran.services.schedule.bean.ContextScheduleCreation;
import turbomeca.gamme.ecran.services.schedule.bean.ContextUserServer;


public class FileAssemblyUtils {

	private static Logger logger = Logger.getLogger(FileAssemblyUtils.class);
	
	private final static String UNDERSCORE = "_";

	public static List<String> listWholeDirectories(File directoryPath) {
		List<String> directories = new ArrayList<String>();
		File[] files = directoryPath.listFiles();
		for (File fileinventory : files) {
			if (fileinventory.isDirectory()) {
				directories.add(fileinventory.getName());
			}
		}
		return directories;
	}

	public static String returnLastIndex(File directoryPath) {
		List<String> directories = listWholeDirectories(directoryPath);
		String dir = directories.get(directories.size() - 1);
		Collections.sort(directories,
				new AlphaNumericComparator<CharSequence>());
		logger.debug("Last index returned " + dir);
		return directories.get(directories.size() - 1);
	}

	public static String buildPathIntermediary(IFolderService folderService, ContextScheduleCreation context) {
		ContextScheduleCreationAssembly contextCreationAssembly = (ContextScheduleCreationAssembly) context;
		String docType = DocTypeUtils.getDoctype(context.getScheduleType(), context.getContext());
		context.setDocType(docType);
		return buildPathIntermediary(folderService, contextCreationAssembly.getFamily(), contextCreationAssembly.getEffectivity(), 
				contextCreationAssembly.getVersion(), contextCreationAssembly.getMaterial(), context.getSuffix(), context.getDocType(), ScheduleState.valueOf(context.getReferential()));
	}
	
	public static String buildPathIntermediary(IFolderService folderService, String family, String effectivity, String version, 
			String material, String suffix, String docType, ScheduleState state) {
		File tt = folderService.getScheduleIntermediaryFile(family, material, suffix, docType, effectivity, state, version);
		return tt.getAbsolutePath();
	}

	public static String buildInstanciatedPath(ContextUserServer contextuser,
			ContextScheduleCreation context) {
		ContextUserAssemblyServer contextUserServer = (ContextUserAssemblyServer) contextuser;
		StringBuilder buf = new StringBuilder();
		buf.append(contextUserServer.getRepository().getInstanciatedFolder().getPath());
		buf.append(FoldersConstants.FOLDER_SEPARATOR);
		buf.append(context.getScheduleInstanciatedFile().getPath());
		return buf.toString();
	}
	
	public static String buildInstanciatedFileAndParentFolderPath(ContextUserServer contextuser,
			ContextScheduleCreation context) {
		ContextUserAssemblyServer contextUserServer = (ContextUserAssemblyServer) contextuser;
		StringBuilder buf = new StringBuilder();
		buf.append(buildDirectoryInstanciated(contextUserServer, context));
		buf.append(FoldersConstants.FOLDER_SEPARATOR);
		buf.append(buildInstanciatedFileName(contextUserServer, context));
		return buf.toString();
	}
	
	private static String buildDirectoryInstanciated(ContextUserServer contextuser,
			ContextScheduleCreation context) {
		StringBuilder buf = new StringBuilder();
		buf.append(context.getOrder());
		buf.append(UNDERSCORE);
		buf.append(contextuser.getSite().toLowerCase());
		buf.append(UNDERSCORE);
		buf.append(contextuser.getLanguage().toLowerCase());
		buf.append(UNDERSCORE);
		buf.append(context.getVariant().toLowerCase().replace(" ", "-"));
		buf.append(UNDERSCORE);
		String folderUUID = UUID.randomUUID().toString();
		buf.append(folderUUID);
		return buf.toString();
	}

	private static String buildInstanciatedFileName(ContextUserServer contextuser,
			ContextScheduleCreation context) {
		StringBuilder buf = new StringBuilder();
		String effectivityWithoutExtension = context.getEffectivity().replace(FoldersConstants.XML_FILE_EXTENSION, "");
		buf.append(effectivityWithoutExtension);
		buf.append(UNDERSCORE);
		String scheduleUUID = UUID.randomUUID().toString();
		buf.append(scheduleUUID);
		buf.append(FoldersConstants.XML_FILE_EXTENSION);
		return buf.toString();
	}

	public static boolean delete(File file) throws IOException {
		boolean result = false;
		if (file != null) {
			if (file.isDirectory()) {
				if (file.list().length == 0) {
					result = file.delete();
					logger.debug("Directory is deleted : "
							+ file.getPath());
				}
			} else {
				result = file.delete();
				logger.debug("File is deleted : " + file.getPath());
				result &= delete(file.getParentFile());
			}
		}
		return result;
	}

	public static String getSynthesisFlow(File file){
		String flow =null;
		FileLocker fileLocker = null;
			try {
				fileLocker = new FileLocker(file);
				if (fileLocker.lock()) {
					flow=FileUtils.readFileToString(file, GlobalConstants.MODEL_ENCODING);
				}				
			} catch (IOException e) {
				logger.error("getSynthesisFlow : "
						+ file.getPath(), e);
			} finally{
				if (fileLocker != null){
					fileLocker.unlock();
				}
			}
		return flow;
	}
}