package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import product.model.Product;
import product.service.Product.IProductService;
import product.service.category.ICategoryServie;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class RestControllerProduct {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private ICategoryServie iCategoryServie;
    @Autowired
    private Environment environment;
    @GetMapping("")
    public ResponseEntity<List<Product>>  showAll(){
        return new ResponseEntity<>(iProductService.findAll(), HttpStatus.OK);
    }
    @PostMapping("create")
    public ResponseEntity<Product> create(@RequestBody Product product){
        iProductService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("edit")
    public ResponseEntity<Product> edit(@RequestBody Product product, @RequestParam Long id){
        product.setId(id);
        iProductService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("delete")
    public ResponseEntity delete(@RequestParam Long id){
        iProductService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("search")
    public ResponseEntity search(){
        return new ResponseEntity<>(iProductService.findAll(), HttpStatus.OK);
    }
}
