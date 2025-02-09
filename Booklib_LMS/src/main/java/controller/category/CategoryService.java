package controller.category;

import model.Category;

import java.util.List;

public interface CategoryService {
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(String id);
    Category searchCategory(String id);
    List<Category> getAll();
}
