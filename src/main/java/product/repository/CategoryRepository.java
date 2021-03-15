package product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import product.model.Category;
@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {
}
