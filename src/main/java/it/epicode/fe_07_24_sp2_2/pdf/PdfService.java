package it.epicode.fe_07_24_sp2_2.pdf;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import it.epicode.fe_07_24_sp2_2.pdf.EmailSenderService;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfService {
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final PdfDocumentRepository pdfDocumentRepository;
    private final EmailSenderService emailService;

    public byte[] generatePdfFromTemplate(String templateName, Map<String, Object> data) throws Exception {
        Configuration cfg = freeMarkerConfigurer.getConfiguration();
        Template template = cfg.getTemplate(templateName);
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(os);
            return os.toByteArray();
        }
    }


    public PdfDocument savePdf(byte[] pdfBytes, String description) {
        PdfDocument pdfDoc = new PdfDocument();
        pdfDoc.setDescription(description);
        pdfDoc.setPdfContent(pdfBytes);
        return pdfDocumentRepository.save(pdfDoc);
    }

    public PdfDocument generateAndSavePdf(String templateName, Map<String, Object> data, String description) throws Exception {
        byte[] pdfBytes = generatePdfFromTemplate(templateName, data);
        return savePdf(pdfBytes, description);
    }

    public byte[] generateAndSendPdf(String templateName, Map<String, Object> data,
                                   String emailTo, String emailSubject, String emailBody,
                                   String attachmentName) throws Exception {
        byte[] pdfBytes = generatePdfFromTemplate(templateName, data);
        emailService.sendEmailWithAttachment(emailTo, emailSubject, emailBody, pdfBytes, attachmentName);

        return pdfBytes;
    }
}
