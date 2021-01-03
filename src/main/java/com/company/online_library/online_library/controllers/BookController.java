package com.company.online_library.online_library.controllers;

import com.company.online_library.online_library.damain.Author;
import com.company.online_library.online_library.damain.Book;
import com.company.online_library.online_library.damain.Genre;
import com.company.online_library.online_library.damain.Publisher;
import com.company.online_library.online_library.interfaces.IAuthorServices;
import com.company.online_library.online_library.interfaces.IBookServices;
import com.company.online_library.online_library.interfaces.IGenreServices;
import com.company.online_library.online_library.interfaces.IPublisherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Controller
public class BookController {
    private IBookServices services;
    private IGenreServices genreServices;
    private IPublisherServices publisherServices;
    private IAuthorServices authorServices;

    @Autowired
    public BookController(IBookServices services, IGenreServices genreServices,
                          IPublisherServices publisherServices, IAuthorServices authorServices) {
        this.services = services;
        this.genreServices = genreServices;
        this.publisherServices = publisherServices;
        this.authorServices = authorServices;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.path1}")
    private String uploadPath1;

    @GetMapping("/admin/addBook")
    public String addBook(Model model){
        Iterable<Genre>genres=genreServices.findAll();
        model.addAttribute("genres",genres);
        Iterable<Publisher>publishers= publisherServices.findAll();
        model.addAttribute("publishers",publishers);
        Iterable<Author>authors=authorServices.findAllAuthors();
        model.addAttribute("authors",authors);
        return "index";
    }

//    @GetMapping("/error")
//    public List<Book> qw()throws MyException{
//        throw new MyException("Заповніть всі поля");
//    }


    @PostMapping(value = "/admin/addBook")
    public String addBook(@RequestParam String bookName, int isbn, int publishYear, String descr,
                          Publisher publisher, Genre genre, @RequestParam("authors") Set<Author> authors,
                          @RequestParam("image") MultipartFile image,
                          @RequestParam("pdf")MultipartFile pdf) throws IOException{

        Book newBook=new Book();
        newBook.setName(bookName);
        newBook.setDescr(descr);
        newBook.setIsbn(isbn);
        newBook.setPublishYear(publishYear);
        newBook.setPublisher(publisher);
        newBook.setGenre(genre);
        newBook.setAuthor(authors);
        if (image != null && !image.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + image.getOriginalFilename();

            image.transferTo(new File(uploadPath + "/" + resultFilename));

            newBook.setImage(resultFilename);
        }

        if (pdf != null && !pdf.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath1);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + pdf.getOriginalFilename();

            pdf.transferTo(new File(uploadPath1 + "/" + resultFilename));

            newBook.setContent(resultFilename);
        }
        services.createBook(newBook);
        return "redirect:/admin/addBook";
    }


    @GetMapping("/home/genre={id}/page={pageNo}")
    public String allBooksByGenre(@PathVariable("id") long id,@PathVariable("pageNo") int pageNo,
                                  @RequestParam(value = "sortField",defaultValue = "name")String sortField, Model model){
        int pageSize=18;
        Page<Book>pages=services.findAllByGenre(pageNo,pageSize,id,sortField);
        List<Book> booksBygenre=pages.getContent();
        model.addAttribute("currentPage1", pageNo);
        model.addAttribute("totalPages1", pages.getTotalPages());
        model.addAttribute("totalItems1", pages.getTotalElements());
        model.addAttribute("booksBygenre",booksBygenre);
        model.addAttribute("genres",genreServices.findAll());
        model.addAttribute("countByGenre",services.countBooksByGenreId(id));
        model.addAttribute("sortField",sortField);
        return "bookByGenre";
    }

    @GetMapping("/home/search")
    public String findByAuthorOrBook(@RequestParam("value")String value,Model model){
        model.addAttribute("find",services.findByNameContainingIgnoreCase(value));
        Iterable<Genre> genres = genreServices.findAll();
        model.addAttribute("genres", genres);
        return "home";
    }

    @GetMapping("/admin/listBook")
    public String listBook(Model model){
        model.addAttribute("books",services.findAllBooks());
        return "listBook";
    }

    @GetMapping("/admin/editBookById={id}")
    public String edition(@PathVariable("id") long id, Model model){
        model.addAttribute("editBook", services.findById(id).get());
        model.addAttribute("genres", genreServices.findAll());
        model.addAttribute("publishers", publisherServices.findAll());
        model.addAttribute("authors", authorServices.findAllAuthors());
        return "editBook";
    }

    @PostMapping("/admin/editBookById={id}")
    public String update(@PathVariable("id") long id,@Valid Book book,
                         BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return "editBook";
        }
        services.updateBookById(book,id);
        return "redirect:/admin/listBook";
    }

    @GetMapping("/admin/deleteBookById={id}")
    public String deleteAuthor(@PathVariable("id")long id){
        services.deleteBookById(id);
        return "redirect:/admin/listBook";
    }

    @GetMapping("/readBookById={id}")
    public  String readBook(@PathVariable("id") long id,Model model){
        model.addAttribute("selectedBook",services.findById(id).get());
        model.addAttribute("genres",genreServices.findAll());
        return "book";
    }
}
