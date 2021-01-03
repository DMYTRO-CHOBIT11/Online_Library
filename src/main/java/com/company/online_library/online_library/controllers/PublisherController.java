package com.company.online_library.online_library.controllers;

import com.company.online_library.online_library.damain.Publisher;
import com.company.online_library.online_library.interfaces.IPublisherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PublisherController {
    private IPublisherServices services;
    @Autowired
    public PublisherController(IPublisherServices services) {
        this.services = services;
    }

    @GetMapping("/admin/addPublisher")
    public String addPublish(Publisher publisher, Model model){
        model.addAttribute("publisher",publisher);
        return "addPublisher";
    }

    @PostMapping("/admin/addPublisher")
    public String add(@Valid Publisher publisher, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "addPublisher";
        }
        services.addPublisher(publisher);
        return "redirect:/admin/listPublishers";
    }

    @GetMapping("/admin/deletePublisherById={id}")
    public String delGenre(@PathVariable("id") long id){
        services.deletePublisherById(id);
        return "redirect:/admin/listPublishers";
    }

    @GetMapping("/admin/listPublishers")
    public String listGenres(Model model){
        model.addAttribute("publishers",services.findAll());
        return "listPublish";
    }

    @GetMapping("/admin/editPublisherById={id}")
    public String editGenre(@PathVariable("id") long id,Model model){
        model.addAttribute("publisher",services.findById(id).get());
        return "editPublisher";
    }

    @PostMapping("/admin/editPublisherById={id}")
    public String editGenre(@PathVariable("id") long id, @Valid Publisher publisher,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "editPublisher";
        }
        services.updatePublisher(id,publisher);
        return "redirect:/admin/listPublishers";
    }

}
