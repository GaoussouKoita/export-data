package ml.pic.tech.export.data.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
public class Utilisateur {

    private Long id;
    private String nom;
    private String prenom;
    private String adresse;

    private Long telephone;
    private String login;
    private String password;
    private List<Role> roles = new ArrayList<>();
}

