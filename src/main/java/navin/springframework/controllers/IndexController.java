package navin.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.repositories.CategoryRepository;
import navin.springframework.repositories.UnitOfMeasureRepository;
import navin.springframework.services.RecipieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@RequestMapping({ "", "/", "/index" })
	public String getIndexPage(Model model) {
		log.debug("I'm inside getIndexController...");
		model.addAttribute("recipies", recipieService.getRecipies());
		return "index";
	}

}
