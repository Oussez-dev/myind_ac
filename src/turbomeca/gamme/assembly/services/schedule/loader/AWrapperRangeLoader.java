package turbomeca.gamme.assembly.services.schedule.loader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;

import turbomeca.gamme.assembly.services.schedule.edition.wrapper.IWrapperRange;
import turbomeca.gamme.ecran.services.common.bean.ContextUser;
import turbomeca.gamme.ecran.services.common.exceptions.RangeIoException;
import turbomeca.gamme.ecran.services.common.utils.file.FileLocker;
import turbomeca.gamme.ecran.services.common.utils.xml.ModelXmlDaoService;
import turbomeca.gamme.ecran.services.common.utils.xml.XmlDocumentEditor;

/**
 * 
 * @author Sopra Group
 */
public abstract class AWrapperRangeLoader<T> implements IWrapperRangeLoader {

	/** */
	private static Logger logger = Logger.getLogger(AWrapperRangeLoader.class);

	/** Ranges file */
	private File file;

	/** XML CASTOR model service */
	private ModelXmlDaoService modelService;

	/** XML basic editor */
	private XmlDocumentEditor xmlEditor;

	/** Range JDom element */
	private Element domObject;
	
	/** Range JDom element */
	private List<Element> domObjects;

	/** File locker */
	private FileLocker fileLocker;

	/**
	 * Constructor
	 * 
	 * @param summaryFile
	 */
	public AWrapperRangeLoader(File file) {
		this.file = file;
		modelService = new ModelXmlDaoService();
		modelService.setObjectClass(getRangeClass());
	}

	/**
	 * 
	 * @param rangeName
	 * @return
	 */
	protected abstract String getXPath(ContextUser contextUser);

	/**
	 * 
	 * @param contextUser
	 * @param range
	 * @return
	 * @throws RangeIoException
	 */
	protected abstract List<IWrapperRange> createWrappers(ContextUser contextUser, T range)
			throws RangeIoException;

	/**
	 * 
	 * @return
	 */
	protected abstract Class<?> getRangeClass();

	/**
	 * Load ranges from file
	 * 
	 * @return ranges model
	 * @throws RangeManagerMemoryException
	 */
	@SuppressWarnings("unchecked")
	public List<IWrapperRange> load(ContextUser contextUser) throws RangeIoException {
		logger.debug("Load range : " + contextUser.getRangeName());
		List<IWrapperRange> wrappers = null;
		fileLocker = new FileLocker(getFile().getPath());
		if (fileLocker.lock()) {
			try {
				xmlEditor = new XmlDocumentEditor(file.getPath());
				logger.debug("Load range : file path : " + file.getPath());
				domObject = xmlEditor.find(getXPath(contextUser));
				if (domObject == null) {
					throw new RangeIoException(RangeIoException.EXCEPTION_RANGE_NOT_FOUND);
				} else {
					if (modelService.setXmlModel(xmlEditor.getXmlSource(domObject))) {
						wrappers = createWrappers(contextUser, (T) modelService.getObject());
					} else {
						throw new RangeIoException(RangeIoException.EXCEPTION_MODEL_NOT_CONFORM);
					}
				}
			} catch (IOException e) {
				throw new RangeIoException(RangeIoException.EXCEPTION_XML_FILE_NOT_FOUND);
			} catch (JDOMException e) {
				throw new RangeIoException(RangeIoException.EXCEPTION_MODEL_NOT_CONFORM);
			}
		} else {
			throw new RangeIoException(RangeIoException.EXCEPTION_LOCKING_FAILED);
		}
		return wrappers;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IWrapperRange> load() throws RangeIoException {
		logger.debug("Load All range from file");
		List<IWrapperRange> wrappers = new ArrayList<IWrapperRange>();
		fileLocker = new FileLocker(getFile().getPath());
		if (fileLocker.lock()) {
			try {
				xmlEditor = new XmlDocumentEditor(file.getPath());
				logger.debug("Load range : file path : " + file.getPath());
				domObjects = xmlEditor.finds(getXPath(null));
				if (domObjects == null) {
					throw new RangeIoException(RangeIoException.EXCEPTION_RANGE_NOT_FOUND);
				} else {
					for( Element domObject : domObjects) {
						if (modelService.setXmlModel(xmlEditor.getXmlSource(domObject))) {
							wrappers.addAll(createWrappers(null, (T) modelService.getObject()));
						} else {
							throw new RangeIoException(RangeIoException.EXCEPTION_MODEL_NOT_CONFORM);
						}
					}
				}
			} catch (IOException e) {
				throw new RangeIoException(RangeIoException.EXCEPTION_XML_FILE_NOT_FOUND);
			} catch (JDOMException e) {
				throw new RangeIoException(RangeIoException.EXCEPTION_MODEL_NOT_CONFORM);
			}
		} else {
			throw new RangeIoException(RangeIoException.EXCEPTION_LOCKING_FAILED);
		}
		return wrappers;
	}
	

	/**
	 * 
	 * @throws RangeManagerMemoryException
	 */
	public void unload() throws RangeIoException {
		xmlEditor = null;
		domObject = null;
		domObjects = null;
		if (!fileLocker.unlock()) {
			throw new RangeIoException(RangeIoException.EXCEPTION_LOCKING_FAILED);
		}
	}

	/**
	 * Save current model to file
	 * 
	 * @throws RangeManagerMemoryException
	 */
	public void save() throws RangeIoException {
		try {
			xmlEditor.replaceXmlSource(domObject, modelService.getXmlSource());
			xmlEditor.save();
		} catch (IOException e) {
			throw new RangeIoException(RangeIoException.EXCEPTION_XML_FILE_NOT_FOUND);
		} catch (JDOMException e) {
			throw new RangeIoException(RangeIoException.EXCEPTION_MODEL_NOT_CONFORM);
		}
	}
	
	/**
	 * Save current model to file
	 * 
	 * @throws RangeManagerMemoryException
	 */
	public void removeAndSave() throws RangeIoException {
		try {
			xmlEditor.replaceXmlSource(domObject, null);
			xmlEditor.save();
		} catch (IOException e) {
			throw new RangeIoException(RangeIoException.EXCEPTION_XML_FILE_NOT_FOUND);
		} catch (JDOMException e) {
			throw new RangeIoException(RangeIoException.EXCEPTION_MODEL_NOT_CONFORM);
		}
	}

	/**
	 * Save current model to file
	 * 
	 * @throws RangeManagerMemoryException
	 */
	public void save(File file) throws RangeIoException {
		try {
			xmlEditor.replaceXmlSource(domObject, modelService.getXmlSource());
			xmlEditor.save(file);
		} catch (IOException e) {
			throw new RangeIoException(RangeIoException.EXCEPTION_XML_FILE_NOT_FOUND);
		} catch (JDOMException e) {
			throw new RangeIoException(RangeIoException.EXCEPTION_MODEL_NOT_CONFORM);
		}
	}
	
	public File getFile() {
		return file;
	}
}
