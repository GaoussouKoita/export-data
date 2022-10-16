package ml.pic.tech.export.data.service;

import ml.pic.tech.export.data.domaine.Role;
import ml.pic.tech.export.data.domaine.Utilisateur;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {


    public List<Utilisateur> init() {
        List<Utilisateur> listUsers = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        Role role1 = new Role(1L, "ADMINISTRATEUR");
        Role role2 = new Role(2L, "UTILISATEUR");

        roles.add(role1);
        roles.add(role2);
        for (int i = 1; i < 250; i++) {
            Utilisateur utilisateur = new Utilisateur((long) (i), "Utilisateur_" + i, "Prenom_" + i, "Dakar",
                    773332211L, "user_" + i, "1234", roles);

            listUsers.add(utilisateur);
        }
        return listUsers;
    }

    public List<Utilisateur> utilisateurList() {
        return init();
    }
}
