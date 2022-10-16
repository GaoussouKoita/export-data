package ml.pic.tech.export.data.service;

import ml.pic.tech.export.data.domaine.Role;
import ml.pic.tech.export.data.domaine.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExportCSV {
    @Autowired
    private UtilisateurService service;

    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Utilisaeur_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Utilisateur> listUsers = service.utilisateurList();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] attUtilisateur= {"Prenom", "Nom", "Login", "Adresse", "Telephone", "Roles"};
        String[] nameMapping= {"prenom", "nom", "login", "adresse", "telephone", "roles"};

        csvWriter.writeHeader(attUtilisateur);

        for (Utilisateur user : listUsers) {
            csvWriter.write(user, nameMapping);
        }
        csvWriter.close();

    }
}
