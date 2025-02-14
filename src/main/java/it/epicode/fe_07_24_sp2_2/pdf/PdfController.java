package it.epicode.fe_07_24_sp2_2.pdf;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor

public class PdfController {
    private final PdfService pdfService;

    @PostMapping
    @Operation(
            summary = "Genera PDF",
            description = "Genera un PDF a partire da una mappa contenente i dati da sostituire nel template.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    example = "{\n  \"campo1\": \"\",\n  \"campo2\": \"\",\n  \"campo3\": \"\"\n}"
                            )
                    )
            )
    )

    public ResponseEntity<byte[]> generatePdf(@RequestBody Map<String, Object> data) {
        try {
            byte[] pdfBytes = pdfService.generateAndSendPdf("template.html", data, "mauro.larese@gmail.com,svetlanka.valeri96@gmail.com"
                    , "Dati di viaggio (Eccolo qua - by Mauro)", "I suoi dati di viaggio", "document.pdf");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "document.pdf");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Errore nella generazione del PDF", e);
        }
    }
}
