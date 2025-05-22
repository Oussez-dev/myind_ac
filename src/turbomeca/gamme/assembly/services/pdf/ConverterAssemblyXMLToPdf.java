package turbomeca.gamme.assembly.services.pdf;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;

import turbomeca.gamme.assembly.services.constants.AssemblyXsltConstants;
import turbomeca.gamme.assembly.services.constants.ConstantsGenerationPDF;
import turbomeca.gamme.ecran.services.common.utils.pdf.AConverterXMLToPdf;

/**
 * 
 * @author fHallot
 */
public class ConverterAssemblyXMLToPdf extends AConverterXMLToPdf {

	/** */
	protected static Logger logger = Logger.getLogger(ConverterAssemblyXMLToPdf.class);

	/** XSLT file used for PDF generation */
	private String xsltFile;
	
	@Override
	public void init(int type) throws TransformerException {
	    switch(type) {
	    case ConstantsGenerationPDF.GENERATION_PDF_SCHEDULE:
	        xsltFile = AssemblyXsltConstants.XSLT_GAMME_PDF_GEE.value();
	        break;
	    case ConstantsGenerationPDF.GENERATION_PDF_SYNTHESIS:
	        xsltFile = AssemblyXsltConstants.XSLT_GAMME_PDF_SYNTHESIS_GEE.value();
            break;
	    case ConstantsGenerationPDF.GENERATION_PDF_INDICATRICE_PLATE:
	        xsltFile = AssemblyXsltConstants.XSLT_GAMME_PDF_INDICATRICE_PLATE_GEE.value();
            break;
	    case ConstantsGenerationPDF.GENERATION_PDF_TRACEABILITY_BOOK:
	        xsltFile = AssemblyXsltConstants.XSLT_GAMME_PDF_TRACEABILITY_BOOK.value();
            break;
	    case ConstantsGenerationPDF.GENERATION_PDF_PVRI:
	        xsltFile = AssemblyXsltConstants.XSLT_GAMME_PDF_PVRI.value();
            break;
	    }
		super.init();
	}
	

    public void init() throws TransformerException {
        init(ConstantsGenerationPDF.GENERATION_PDF_SCHEDULE);
    }

    // FIXME ; Use FolderService to perform this ?
	@Override
    public String getRepository(String scheduleName) {
        String scheduleRepo;
        if (scheduleName.contains("_assembly_new")) {
            scheduleRepo = "assembly/assembly_new";
        } else if (scheduleName.equalsIgnoreCase("_assembly_repaired")) {
            scheduleRepo = "assembly/assembly_repaired";
        } else {
            scheduleRepo = "disassembly/disassembly";
        }
        return scheduleRepo;
    }
	
    @Override
    protected String getXsltFile() {
        return xsltFile;
    }
}