package controller.publisher;

import model.Category;
import model.Publisher;

import java.util.List;

public interface PublisherService {
    boolean addPublisher(Publisher publisher);
    boolean updatePublisher(Publisher publisher);
    boolean deletePublisher(String id);
    Publisher searchPublisherById(String id);
    Publisher searchPublisherByName(String name);
    List<Publisher> getAll();

}
