package com.company.online_library.online_library.interfaces;


import com.company.online_library.online_library.damain.Publisher;

import java.util.List;
import java.util.Optional;

public interface IPublisherServices {
    List<Publisher> findAll();
    Publisher addPublisher(Publisher publisher);
    void deletePublisherById(long id);
    Optional<Publisher>findById(long id);
    Publisher updatePublisher(long id,Publisher publisher);
}
