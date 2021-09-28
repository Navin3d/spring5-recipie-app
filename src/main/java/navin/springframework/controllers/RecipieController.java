package navin.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.commands.RecipieCommand;
import navin.springframework.exceptions.NotFoundException;
import navin.springframework.services.RecipieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipieController {

    private static final String Recipie_RecipieForm_Url = "recipie/recipieform";
    private final RecipieService recipieService;

    @Autowired
    public RecipieController(RecipieService recipieService) {
        this.recipieService = recipieService;
    }

    @GetMapping("recipie/{id}/show")
    public String showRecipieById(@PathVariable String id, Model model) {
        model.addAttribute("recipie", recipieService.findById(Long.valueOf(id)));
        return "recipie/show";
    }

    @GetMapping("recipie/new")
    public String newRecipie(Model model) {
        model.addAttribute("recipie", new RecipieCommand());
        return "recipie/recipieform";
    }

    @GetMapping("recipie/{id}/update")
    public String updateRecipie(@PathVariable String id, Model model) {
        model.addAttribute("recipie", recipieService.findCommandById(Long.valueOf(id)));
        return Recipie_RecipieForm_Url;
    }

    @PostMapping("recipie")
    public String saveOrUpdate(@Valid @ModelAttribute("recipie") RecipieCommand recipieCommand, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return Recipie_RecipieForm_Url;
        }
        RecipieCommand savedCommand = recipieService.saveRecipieCommand(recipieCommand);

        return "redirect:/recipie/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipie/{id}/delete")
    public String deleteById(@PathVariable String id) {
        recipieService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {

        log.error("Handling not found exception...");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
