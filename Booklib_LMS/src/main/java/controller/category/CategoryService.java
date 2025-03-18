package controller.category;

import model.Category;

import java.util.List;

public interface CategoryService {
    boolean addCategory(Category category);
    boolean updateCategory(Category category);
    boolean deleteCategory(String id);
    Category searchCategoryById(String id);
    Category searchCategoryByName(String name);
    List<Category> getAll();
}
