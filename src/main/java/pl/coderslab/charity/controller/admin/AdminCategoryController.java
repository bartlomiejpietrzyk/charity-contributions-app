package pl.coderslab.charity.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.UserRepository;

import java.util.List;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminCategoryController(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute("categories")
    public List<Category> allCategoriesList() {
        return categoryRepository.findAll();
    }

    @GetMapping
    public String showCategoriesList(Model model) {
        return "admin/categoriesList";
    }

    @GetMapping("/add")
    public String showCategoryAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categoriesAdd";
    }

    @PostMapping("/add")
    public String proceedCategoryAddForm(@ModelAttribute("category") Category category,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/admin/categories/add?id=" + category.getId() + "&failed";
        }
        categoryRepository.save(category);
        return "redirect:/admin/categories/add?id=" + category.getId() + "&success";
    }

    @GetMapping("/edit")
    public String showCategoryEditForm(@RequestParam Long id, Model model) {
        model.addAttribute("category", categoryRepository.getOne(id));
        return "admin/categoriesEdit";
    }

    @PostMapping("/edit")
    public String proceedCategoryEditForm(@ModelAttribute("category") Category category,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/admin/categories/edit?id=" + category.getId() + "&failed";
        }
        categoryRepository.save(category);
        return "redirect:/admin/categories/edit?id=" + category.getId() + "&success";
    }

    @RequestMapping("/delete")
    public String deleteCategory(@RequestParam Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/categories?deletesuccess";
    }
}
