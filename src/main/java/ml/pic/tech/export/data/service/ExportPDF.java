package ml.pic.tech.export.data.service;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import ml.pic.tech.export.data.domaine.Role;
import ml.pic.tech.export.data.domaine.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * La Classe Export PDF permet d'exporter des donnees au format PDF
 * pour cela elle utilise requiert la dependance openpdf et definie trois methodes
 *
 * private void writeTableHeader(PdfPTable table){
 *     qui definie le font, le nom des colonnes d'entete
 * }
 *
 * private void writeTableData(PdfPTable table){
 *     parcourt les donnes pour les ajouter dans la table
 *
 * }
 *public void export(HttpServletResponse response){
 *
 *     Definie le nom et type du fichier
 *     Definie un Header pour la page avec son font
 *     Definie le pourcentage, largeur des colonnes, margin de la table
 *     inserer l'entete et le corps du tableau
 *}
 *
 */
@Service
public class ExportPDF {


    @Autowired
    private UtilisateurService service;

    private String attUtilisateur[] = {"Prenom", "Nom", "Login", "Adresse", "Telephone", "Roles"};


    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY); //Background Header
        cell.setPadding(2);

        //Font l'entete du tableau
        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.BLACK);
        font.setSize(14);

        for (String attribut : attUtilisateur) {
            cell.setPhrase(new Phrase(attribut, font));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table) {
        for (Utilisateur user : service.utilisateurList()) {
            String roles = "";
            for (Role r : user.getRoles()) {
                roles += r.getRoleName() + " ";
            }
            table.addCell(user.getPrenom());
            table.addCell(user.getNom());
            table.addCell(user.getLogin());
            table.addCell(user.getAdresse());
            table.addCell(String.valueOf(user.getTelephone()));
            table.addCell(roles);
        }
    }


    public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException {
        //Definition du type de fichier, nom
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Utilisateurs_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        //Font du Header Page
        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
        font.setSize(32);
        font.setColor(Color.pink);
        Paragraph p = new Paragraph("Liste des Utilisateurs", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        PdfPTable table = new PdfPTable(attUtilisateur.length);
        table.setWidthPercentage(100f); //Pourcentange du tableau dans la page
        table.setWidths(new float[]{1.5f, 2f, 1.5f, 1f, 1.5f, 2.1f});//Largeur de Colonnes
        table.setSpacingBefore(10); //Espace avant le table pour ne pas entre coller au Paragraphe Para de

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();


    }
}
