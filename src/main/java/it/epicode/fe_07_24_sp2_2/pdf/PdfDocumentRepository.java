package it.epicode.fe_07_24_sp2_2.pdf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
}
