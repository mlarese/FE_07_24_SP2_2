package it.epicode.fe_07_24_sp2_2.pdf;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pdf_documents")
@Data

@AllArgsConstructor
@NoArgsConstructor
public class PdfDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Lob
    @Column(name = "pdf_content", nullable = false)
    private byte[] pdfContent;

}
