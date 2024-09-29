package com.example.rcca2.Controllers;

import com.example.rcca2.Entities.Administrator;
import com.example.rcca2.Entities.Item;
import com.example.rcca2.Services.AdminService;
import com.example.rcca2.Services.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/item")
//@PermitAll
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private AdminService adminService;



    @RequestMapping("/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("list", itemService.findApp());
        return "index";
    }


/*    @RequestMapping("/index")
    public String pagination(ModelMap modelMap, @RequestParam(required = false, defaultValue = "0") int page) {
        System.out.println(page);
        modelMap.addAttribute("list", itemService.pagination(page, 3));
        return "index";
    }*/
/*//    @PostMapping("/ax1")
//    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String uname = req.getParameter("userName");
//        String pwd = req.getParameter("userPwd");
//        System.out.println(uname + " " + pwd);
//
////        resp.setCharacterEncoding("utf-8");
////        resp.setContentType("text/html;charset=utf-8");
////        resp.getWriter().write(uname + " " + pwd);
//    }

//    @GetMapping("/map")
//    //@RequestBody接受的是一个json对象的字符串，而不是Json对象，
//    // 在请求时往往都是Json对象，用JSON.stringify(data)的方式就能将对象变成json字符串
//    public @ResponseBody Map<String, Object> map() {
//        Map<String, Object> map = new HashMap();
//        map.put("admin", adminService.search("two", "a"));
//        map.put("item", itemService.findById(1L));
//        return map;
//    }

    @RequestMapping("/hi")
    public String hi(ModelMap model){
        Administrator admin = adminService.findById(1L);
        Administrator admin2 = adminService.findById(2L);
        List<Administrator> admins = new ArrayList<>();
        admins.add(admin);
        admins.add(admin2);
        model.addAttribute("admin", admins);
        model.addAttribute("sb", "abcd");
        model.addAttribute("sb2", admins);
        return "hello";
    }*/

    @PostMapping( "/jump")
    @ResponseBody
    public void jump(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 获取文件名
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                // 将文件保存到目标路径
                Path targetLocation = Paths.get("src/main/webapp/static/asset/" + fileName).toAbsolutePath().normalize();
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to upload file.");
            }
        } else {
            System.out.println("File is empty.");
        }
/*//        System.out.println(file);
//        if (!file.isEmpty()) {
//            try {
//                // 指定文件保存的路径和文件名
////                String filePath = "C:\\Users\\Weihao\\Desktop\\photoalbum_OCI_v3.0\\" + file.getOriginalFilename();
////                File destFile = new File("src/main/webapp/static/asset/" + file.getOriginalFilename());
//                File destFile = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static\\" + file.getOriginalFilename());
//
//                // 保存文件到指定位置
//                file.transferTo(destFile);
//
//                System.out.println("File uploaded successfully!");
//            } catch (IOException e) {
//                System.out.println("Failed to upload file.");
//                System.out.println(e);
//            }
//        } else {
//            System.out.println("File is empty.");
//        }*/
    }


    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/detail");
        mav.addObject("ad", itemService.findItemDetailsByID(id));
        return mav;
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam String category, @RequestParam String search) {
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("list", itemService.searchItemsByType(category, search));
        return mav;
    }

    @Transactional
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveItem(@ModelAttribute("item") Item i, @RequestParam("file") MultipartFile file ) {
        System.out.println("-----------SAVING------------");
        System.out.println(i);
        if (!file.isEmpty()) {
            try {
                // 获取文件名
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                // 将文件保存到目标路径
                Path targetLocation = Paths.get("src/main/webapp/static/asset/" + fileName).toAbsolutePath().normalize();
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File uploaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to upload file.");
            }
        } else {
            System.out.println("File is empty.");
        }
        i.setFile_path("/static/asset/" + StringUtils.cleanPath(file.getOriginalFilename()));
        i.setFile_format(file.getOriginalFilename().split("\\.", 2)[1]);
        itemService.save(i);
        return "redirect:/item/index";
    }


    @RequestMapping(value = "/ut1", method = RequestMethod.GET)
    public ModelAndView ut() {
        return new ModelAndView("uploadTest", "item", new Item());
    }



}


/*
    1. Get Everything Working and ready before presentation
    2. Don't change template, keywords in individual report
    3. Breif explain in introduction
    4. 可以适当加小标题，比如说介绍tech的时候，小标题hardware，小标题software，language
    5. specification：methodology，
    6. 一定要提供contribtion的证据
    7. 不要复制粘贴任何
    8. 记得还有个单独的peer review
    9. Code demo: technology stack, language, database, a diagram of the architecture, screenshots of code
 */

