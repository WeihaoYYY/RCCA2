package com.example.rcca2.Controllers;

import com.example.rcca2.DTO.ItemDetailsDTO;
import com.example.rcca2.Entities.Item;
import com.example.rcca2.Entities.User;
import com.example.rcca2.Services.UserService;
import com.example.rcca2.Services.ItemService;
import com.example.rcca2.common.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/item")  //TODO 以后放到docker中
//@PermitAll
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;



    @ApiOperation(value = "Say hi", notes = "Simple hello endpoint for testing purposes")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // 只有角色为 ADMIN 的用户可以访问
    public R hi() {
        return R.ok("Hello, ADMIN!");
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public R a() {

        return R.ok("user");
    }



    @PostMapping("/user/login")
    public R login(@RequestBody User user) {

        return userService.login(user);
    }

    @RequestMapping("/user/logout")
    public R logout() {
        return userService.logout();
    }


    /*
    * 1. 通过itemService调用homeList方法获取首页展示的item列表
    * 2. 返回R对象，其中包含了ItemDetailsDTO列表
     */
    @GetMapping("/index")
    @ApiOperation(value = "Get item list for homepage", notes = "Fetches the list of items to be displayed on the homepage")
    public R<List<ItemDetailsDTO>> index() {
        return R.ok(itemService.homeList());
    }


    @ApiOperation(value = "Get item details by ID", notes = "Fetches details of an item based on its ID")
    @GetMapping("/detail/{sid}")
    public R<ItemDetailsDTO> detail(@ApiParam(value = "ID of the item to be fetched", required = true) @PathVariable Long sid) {
        ItemDetailsDTO itemDetails = itemService.findItemDetailsByID(sid);
        return itemDetails == null ? R.error("Item not found") : R.ok(itemDetails);
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




    @GetMapping("/search")
    public ModelAndView search(@RequestParam String category, @RequestParam String search) {
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("list", itemService.searchItemsByType(category, search));
        return mav;
    }

    @Transactional
    @PostMapping(value = "/save")
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

