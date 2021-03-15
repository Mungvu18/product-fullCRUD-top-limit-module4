package product.service.category;

import product.model.Category;

import java.util.List;

public interface ICategoryServie {
    List<Category> findAll();
    Category findById(Long id);
}
