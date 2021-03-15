package product.service.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import product.model.Product;
import product.repository.ProductRepository;

import java.util.List;
@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> findByCategory(Long category_id, Pageable pageable) {
        return productRepository.findProductByCategory(pageable,category_id);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<Product> findTopExpensive(int index) {
        return productRepository.findTopPriceExpensive(index);
    }

    @Override
    public List<Product> findTopNew(int index) {
        return productRepository.findTopNewDateTime(index);
    }

    @Override
    public double getSumMoney() {
        return productRepository.getSumMoney();
    }
}
