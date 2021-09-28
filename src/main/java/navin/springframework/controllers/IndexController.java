package navin.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.repositories.CategoryRepository;
import navin.springframework.repositories.UnitOfMeasureRepository;
import navin.springframework.services.RecipieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import navin.springframework.domain.Category;
import navin.springframework.domain.UnitOfMeasure;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

	private final RecipieService recipieService;

	public IndexController(RecipieService recipieService) {
		this.recipieService = recipieService;
	}

	@GetMapping({ "", "/", "/index" })
	public String getIndexPage(Model model) {
		log.debug("I'm inside getIndexController...");
		model.addAttribute("recipies", recipieService.getRecipies());
		return "index";
	}

	@GetMapping("/login")
	public String getLogin(Model model) {
		return "login";
	}

}
