package com.company.online_library.online_library.controllers;

import com.company.online_library.online_library.damain.Book;
import com.company.online_library.online_library.damain.User;
import com.company.online_library.online_library.interfaces.IBookServices;
import com.company.online_library.online_library.interfaces.IGenreServices;
import com.company.online_library.online_library.interfaces.IUserServices;
import com.company.online_library.online_library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
    UserRepository repository;

    @GetMapping("/reg")
    public String reg(User user, Model model) {
        model.addAttribute("user",user);
        return "registration";
    }

    @GetMapping("/logout")
    public String logout(){
        return  "login";
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
        Page<Book> pages = bookServices.findAll(pageNo,pageSize);
        List<Book> books = pages.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalItems", pages.getTotalElements());
        model.addAttribute("genres", genreServices.findAll());
        model.addAttribute("count", books.size());
        model.addAttribute("books",bookServices.sortByParam(books,sortField));
        return "home";
    }

    @GetMapping("/user")
    public String userRoom(Model model) throws Exception{
        if(true){
            User user = services.findUserByEmail(
                    SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("user",user);
            throw new Exception();
        }
        return "userRoom";
    }

    @GetMapping("/user/{email}/updateUsername")
    public String updateUserUsername(@PathVariable("email")String email, Model model){
        model.addAttribute("user",services.findUserByEmail(email));
        return "updateUsername";
    }

    @GetMapping("/user/{email}/updatePassword")
    public String updateUserPassword(@PathVariable("email")String email, Model model){
        model.addAttribute("user",services.findUserByEmail(email));
        return "updatePassword";
    }

    @PostMapping("/user/{email}/updateUsername")
    public String updateUsername(@PathVariable("email")String email,
                                 @RequestParam("username")String username){
       services.updateUsername(email, username);
        return "redirect:/user";
    }
    @PostMapping("/user/{email}/updatePassword")
    public String updatePassword(@PathVariable("email")String email,
                                 @RequestParam("password")String password){
        services.updatePassword(email, password);
        return "redirect:/user";
    }

    @ExceptionHandler(Exception.class)
    public String myException(Model model){
        model.addAttribute("message","Поганий запрос");
        return "error";
    }
}
