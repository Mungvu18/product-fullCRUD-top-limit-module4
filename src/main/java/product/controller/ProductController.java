package product.controller;

import org.apache.commons.io.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import product.model.Category;
import product.model.Product;
import product.service.Product.IProductService;
import product.service.category.ICategoryServie;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    Environment environment;
    @Autowired
    ICategoryServie iCategoryServie;
    @Autowired
    IProductService iProductService;
    @ModelAttribute("categories")
    public List<Category> getCategories(){
        return iCategoryServie.findAll();
    }
    @GetMapping("")
    public ModelAndView index(@PageableDefault(size = 5) Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("home");
        Page<Product> products = iProductService.findAll(pageable);
        modelAndView.addObject("list",products);
        return modelAndView;
    }
    @GetMapping("create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product",new Product());
        return modelAndView;
    }
    @PostMapping("create")
    public ModelAndView create(@ModelAttribute Product product){
        MultipartFile avatar = product.getAvatar();
        String image = avatar.getOriginalFilename();
        String resource = environment.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(avatar.getBytes(),new File(resource+image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setImage(image);
        iProductService.save(product);
        return new ModelAndView("create","product",new Product());
    }

}
