package com.company.online_library.online_library.controllers;

import com.company.online_library.online_library.damain.Book;
import com.company.online_library.online_library.damain.Genre;
import com.company.online_library.online_library.damain.User;
import com.company.online_library.online_library.interfaces.IBookServices;
import com.company.online_library.online_library.interfaces.IGenreServices;
import com.company.online_library.online_library.interfaces.IPublisherServices;
import com.company.online_library.online_library.interfaces.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private IUserServices services;
    @Autowired
    private IBookServices bookServices;
    @Autowired
    private IGenreServices genreServices;
    @Autowired
    private IPublisherServices publisherServices;

    @GetMapping("/reg")
    public String reg(User user, Model model) {
        model.addAttribute("user",user);
        return "registration";
    }

    @PostMapping("/reg")
    public String addUser(@Valid User user, BindingResult bindingResult) {

        if (services.userExist(user.getEmail())){
            bindingResult.addError(new FieldError("user","email","Користувач з таким емейлом вже існує"));
        }
        if(bindingResult.hasErrors()){
            return "registration";
        }
        services.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/home/page={pageNo}")
    public String homePage(Model model, @PathVariable("pageNo")int pageNo,
                           @RequestParam(value = "sortField",defaultValue = "name") String sortField){
        int pageSize=18;
        Page<Book> pages = bookServices.findAll(pageNo,pageSize,sortField);
        List<Book> books = pages.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalItems", pages.getTotalElements());
        model.addAttribute("books", books);
        Iterable<Genre> genres = genreServices.findAll();
        model.addAttribute("genres", genres);
        model.addAttribute("count", bookServices.countBooks());
        model.addAttribute("sortField",sortField);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(){
        return  "login";
    }
}
