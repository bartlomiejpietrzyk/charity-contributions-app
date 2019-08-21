package pl.coderslab.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.InstitutionRepository;
import pl.coderslab.charity.repository.UserRepository;

import javax.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/institutions")
public class AdminInstitutionController {
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdminInstitutionController(InstitutionRepository institutionRepository, UserRepository userRepository) {
        this.institutionRepository = institutionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String showInstitutionsPanel(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("institutionList", institutionRepository
                .findAll(new PageRequest(page, 10)));
        model.addAttribute("currentPage", page);
        return "admin/institutionsList";
    }

    @GetMapping("/add")
    public String showInstitutionAddForm(Model model) {
        model.addAttribute("institution", new Institution());
        return "admin/institutionsAdd";
    }

    @PostMapping("/add")
    public String proceedInstitutionAddForm(@ModelAttribute("institution") @Valid Institution institution,
                                            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/admin/institutions/add?failed";
        }
        institutionRepository.save(institution);
        return "redirect:/admin/institutions/add?success";
    }

    @GetMapping("/edit")
    public String showEditInstitutionForm(@RequestParam Long id, Model model) {
        model.addAttribute("institution", institutionRepository.getOne(id));
        return "admin/institutionsEdit";
    }

    @PostMapping("/edit")
    public String proceedEditInstitutionForm(@ModelAttribute("institution") @Valid Institution institution,
                                             BindingResult result) {

        if (result.hasErrors()) {
            return "redirect:/admin/institutions/edit?id=" + institution.getId() + "&failed";
        }
        institutionRepository.save(institution);
        return "redirect:/admin/institutions/edit?id=" + institution.getId() + "&success";
    }

    @RequestMapping("/delete")
    public String deleteCategory(@RequestParam Long id) {
        institutionRepository.deleteById(id);
        return "redirect:/admin/institutions?deletesuccess";
    }
}
