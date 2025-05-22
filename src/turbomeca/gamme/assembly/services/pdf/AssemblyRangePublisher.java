
package turbomeca.gamme.assembly.services.pdf;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import turbomeca.gamme.ecran.services.common.utils.pdf.AConverterXMLToPdf;
import turbomeca.gamme.ecran.services.schedule.service.instanciated.InstanciatedScheduleService;
import turbomeca.gamme.ecran.services.schedule.service.pdf.IPdfInterfaceService;
import turbomeca.gamme.ecran.services.schedule.service.pdf.bean.RangeProperties;
import turbomeca.gamme.ecran.services.schedule.service.pdf.config.ReportConfiguration;
import turbomeca.gamme.ecran.services.schedule.service.pdf.exception.ReportGenerationException;
import turbomeca.gamme.ecran.services.schedule.service.pdf.service.impl.APdfGenerationObserver;
import turbomeca.gamme.ecran.services.schedule.service.pdf.service.impl.ARangePublisher;

public class AssemblyRangePublisher extends ARangePublisher {

    final static String URL_WS_PDF = "http://pdf.services.assembly.gamme.turbomeca/";
    final static String QNAME_PDF = "AssemblyPdfGenerationServiceService";
    final static String SERVICE_NAME_PDF = "PdfInterfaceService";

    /** */
    private IPdfInterfaceService pdfService;

    public AssemblyRangePublisher(ReportConfiguration config, InstanciatedScheduleService instanciatedScheduleService) {
        super(config, instanciatedScheduleService);
    }

    @Override
    public APdfGenerationObserver newObserver() {
        return new AssemblyPdfGenerationObserver(getConfig().getBatchConfig());
    }

    @Override
    public void servicePublishing(RangeProperties context, File rangeFile)
            throws ReportGenerationException {
        // Call print ws

        try {
            IPdfInterfaceService pdfGenetration = getInterfaceService().getPort(IPdfInterfaceService.class);
            RangeProperties propsCpy = new RangeProperties();
            propsCpy.setUserLogin(context.getUserLogin());
            propsCpy.setRepositoryIndex(context.getRepositoryIndex());
            propsCpy.setUserName(context.getUserName());
            propsCpy.setFamily(context.getFamily());
            propsCpy.setVersion(context.getVersion());
            propsCpy.setType(context.getType());
            propsCpy.setFileName(context.getFileName());
            pdfGenetration.printRange(propsCpy, rangeFile.getParentFile().getName() + "/" + rangeFile.getName());
        } catch (Exception e) {
            throw new ReportGenerationException(e);
        }
    }

    public URL getUrlConnection() throws MalformedURLException {
        String url = getConfig().getPrintServiceUrl();
        url = url.startsWith("http://") || url.startsWith("https://") ? url : "http://" + url;
        final String urlString = url
                + ":"
                + getConfig().getPrintServicePort()
                + "/turbomeca.gamme."
                + getConfig().getPrintServiceHost()
                + ".server.ws/"
                + SERVICE_NAME_PDF
                + "?wsdl";
        return new URL(urlString);
    }

    public Service getInterfaceService() throws MalformedURLException {
        QName qname = new QName(URL_WS_PDF, QNAME_PDF);
        Service service = Service.create(getUrlConnection(), qname);
        return service;
    }

    @Override
    public AConverterXMLToPdf getConverterXMLToPdf() {
        return new ConverterAssemblyXMLToPdf();
    }

    public void setPdfService(IPdfInterfaceService pdfService) {
        this.pdfService = pdfService;
    }

    public IPdfInterfaceService getPdfService() {
        return pdfService;
    }

}
