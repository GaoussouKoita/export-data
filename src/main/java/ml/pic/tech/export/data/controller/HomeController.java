package ml.pic.tech.export.data.controller;

import ml.pic.tech.export.data.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private UtilisateurService service;

    @GetMapping("/")
    public String acceuit(Model model) {
        model.addAttribute("utilisateurs", service.utilisateurList());
        return "index";
    }
}
