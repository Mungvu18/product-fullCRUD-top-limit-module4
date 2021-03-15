package product.service.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import product.model.Product;

import java.util.List;

public interface IProductService {
    Page<Product> findAll(Pageable pageable);
    Product findById(Long id);
    Product save(Product product);
    Page<Product> findByCategory(Long category_id, Pageable pageable);
    void delete(Long id);
    List<Product> findTopExpensive(int index);
    List<Product> findTopNew(int index);
    double getSumMoney();
}
