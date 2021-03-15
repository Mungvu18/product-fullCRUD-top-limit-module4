package product.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("edit")
    public ModelAndView showFormEdit(@RequestParam Long id){
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = iProductService.findById(id);
        modelAndView.addObject("product",product);
        return modelAndView;
    }
    @PostMapping("edit")
    public ModelAndView edit(@ModelAttribute Product product, @RequestParam Long id){
        product.setId(id);
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
        return new ModelAndView("redirect:/products");
    }
    @GetMapping("delete")
    public ModelAndView detele(@RequestParam Long id){
        iProductService.delete(id);
        return new ModelAndView("redirect:/products");
    }
    @PostMapping("search")
    public ModelAndView search(@RequestParam Long category_id,@PageableDefault(size = 3) Pageable pageable){
        Page<Product> products = iProductService.findByCategory(category_id,pageable);
        return new ModelAndView("search","list",products);
    }
    @GetMapping("top_expensive")
    public ModelAndView top_expensive(){
        List<Product> productList= iProductService.findTopExpensive(5);
        return new ModelAndView("search","list",productList);

    }
    @GetMapping("top_new")
    public ModelAndView top_new(){
        List<Product> productList= iProductService.findTopNew(5);
        return new ModelAndView("search","list",productList);

    }
    @GetMapping("get_sum")
    public ModelAndView get_sum( @PageableDefault(size = 3) Pageable pageable){
        double sum= iProductService.getSumMoney();
        ModelAndView modelAndView=new ModelAndView("home","sum",sum);
        Page<Product> productList=iProductService.findAll(pageable);
        modelAndView.addObject("list",productList);
        return modelAndView;
    }
}
