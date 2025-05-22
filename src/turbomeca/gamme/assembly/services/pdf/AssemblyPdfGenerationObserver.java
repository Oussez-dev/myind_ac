package turbomeca.gamme.assembly.services.pdf;

import org.apache.log4j.Logger;

import turbomeca.gamme.assembly.services.schedule.loader.RangesLoader;
import turbomeca.gamme.assembly.services.schedule.model.Range;
import turbomeca.gamme.assembly.services.schedule.model.types.StatusRangeType;
import turbomeca.gamme.ecran.services.common.bean.ContextConfig;
import turbomeca.gamme.ecran.services.common.config.ARepositoryConfig;
import turbomeca.gamme.ecran.services.schedule.service.pdf.bean.RangePropertiesExt;
import turbomeca.gamme.ecran.services.schedule.service.pdf.config.BatchConfiguration;
import turbomeca.gamme.ecran.services.schedule.service.pdf.service.impl.APdfGenerationObserver;
import turbomeca.gamme.ecran.services.schedule.service.pdf.service.impl.ExternalPdfGenerator;


public class AssemblyPdfGenerationObserver extends APdfGenerationObserver {

	private static Logger logger = Logger.getLogger(AssemblyPdfGenerationObserver.class);

	public AssemblyPdfGenerationObserver(BatchConfiguration configuration) {
		super(configuration);
	}

	public void fillQueueOnStart(ARepositoryConfig repository, ContextConfig serverConfig) {
		Range range;
		
		try {
			if(!repository.getSummaryFile().exists()) {
				repository.createSummaryFile();
			}
			RangesLoader loader = new RangesLoader(repository.getSummaryFile());
			loader.loadAndRelease();
			for (int index = 0; index < loader.getCastorObject().getRangeCount(); ++index) {
				range = loader.getCastorObject().getRange(index);
				if (range.getInstance().getStatus() == StatusRangeType.PDF_GENERATION) {
					RangePropertiesExt rangeCtx = new RangePropertiesExt(repository, serverConfig, 0, range
							.getModification().getLogin(), range.getModification().getUser(),range.getRangeIdentification().getFile());
					ExternalPdfGenerator epg = new ExternalPdfGenerator(rangeCtx, repository
							.createRangeFilePath(range.getRangeIdentification().getFile()));
					epg.setConverterXmlToPdf(new ConverterAssemblyXMLToPdf());
					push(epg);
				}
			}
		} catch (Exception e) {
			logger.error("Loading summary model failed", e);
		}
	}
}
