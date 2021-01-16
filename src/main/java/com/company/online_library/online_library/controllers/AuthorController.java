package com.company.online_library.online_library.controllers;

import com.company.online_library.online_library.damain.Author;
import com.company.online_library.online_library.interfaces.IAuthorServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthorController {
    private final IAuthorServices services;

    public AuthorController(IAuthorServices services) {
        this.services = services;
    }

    @GetMapping("/admin/listAuthors")
    public String find(Model model){
        Iterable<Author>authors=services.findAllAuthors();
        model.addAttribute("authors",authors);
        return "directory";
    }

    @GetMapping("/admin/editAuthorById={id}")
    public String edition(@PathVariable("id") long id, Model model){
        model.addAttribute("editAuthor", services.findById(id).get());
        return "edit";
    }

    @PostMapping("/admin/editAuthorById={id}")
    public String update(@PathVariable("id") long id,@Valid Author author,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "edit";
        }
        services.updateAuthorById(author,id);
        return "redirect:/admin/listAuthors";
    }
    @GetMapping("/admin/listAuthors/delete={id}")
    public String deleteAuthor(@PathVariable("id")long id){
        services.deleteAuthorById(id);
        return "redirect:/admin/listAuthors";
    }

    @GetMapping("/admin/addAuthor")
    public String addAuthor(Author author,Model model){
        model.addAttribute("author",author);
        return "addAuthor";
    }

    @PostMapping("/admin/addAuthor")
    public String add(@Valid Author author, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "addAuthor";
        }
        services.createAuthor(author);
        return "redirect:/admin/listAuthors";
    }

}
