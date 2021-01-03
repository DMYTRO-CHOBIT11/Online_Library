package com.company.online_library.online_library.controllers;

import com.company.online_library.online_library.damain.Genre;
import com.company.online_library.online_library.interfaces.IGenreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class GenreController {
    private IGenreServices services;
    @Autowired
    public GenreController(IGenreServices services) {
        this.services = services;
    }

    @GetMapping("/admin/addGenre")
    public String addGEnre(Genre genre, Model model){
        model.addAttribute("genre",genre);
        return "addGenre";
    }

    @PostMapping("/admin/addGenre")
    public String add(@Valid Genre genre, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "addGenre";
        }
        services.addGenre(genre);
        return "redirect:/admin/listGenres";
    }

    @GetMapping("/admin/deleteGenreById={id}")
    public String delGenre(@PathVariable("id") long id){
        services.deleteGenreById(id);
        return "redirect:/admin/listGenres";
    }

    @GetMapping("/admin/listGenres")
    public String listGenres(Model model){
        model.addAttribute("genres",services.findAll());
        return "listGenres";
    }

    @GetMapping("/admin/editGenreById={id}")
    public String editGenre(@PathVariable("id") long id, Model model){
        model.addAttribute("genre",services.findById(id).get());
        return "editGenre";
    }

    @PostMapping("/admin/editGenreById={id}")
    public String editGenre(@PathVariable("id") long id, @Valid Genre genre,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "editGenre";
        }
        services.updateGenreById(genre, id);
        return "redirect:/admin/listGenres";
    }
}
