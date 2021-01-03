package com.company.online_library.online_library.implements_;

import com.company.online_library.online_library.damain.Publisher;
import com.company.online_library.online_library.interfaces.IPublisherServices;
import com.company.online_library.online_library.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherServices implements IPublisherServices {
    @Autowired
    private PublisherRepository repository;

    @Override
    public Iterable<Publisher> findAll() {
        return repository.findAll();
    }

    @Override
    public Publisher addPublisher(Publisher publisher) {
        return repository.save(publisher);
    }

    @Override
    public void deletePublisherById(long id) {
        Publisher publisher=findById(id).get();
//        publisher.removePublisherFromBooks(publisher.getBooks());
        repository.delete(publisher);
    }

    @Override
    public Optional<Publisher> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public Publisher updatePublisher(long id, Publisher publisher) {
        Optional<Publisher> newPublisher=repository.findById(id);
        newPublisher.get().setName(publisher.getName());
        return repository.save(newPublisher.get());
    }
}
