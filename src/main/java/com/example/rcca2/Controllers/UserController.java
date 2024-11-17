package com.example.rcca2.Controllers;

//import jakarta.annotation.security.RolesAllowed;
import com.example.rcca2.Entities.Administrator;
import com.example.rcca2.Entities.Item;
import com.example.rcca2.Entities.User;
import com.example.rcca2.Repository.UserRepository;
import com.example.rcca2.Services.ItemService;
import com.example.rcca2.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@Controller
//@RolesAllowed("ADMIN")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;


    @GetMapping("/hi/{rid}")
    public String hi(@PathVariable("rid") Long id) {
        System.out.println(id);
        return "test";
    }

    @GetMapping("/index")
    public RedirectView redirect() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/submission.html");
        return redirectView;
    }

    @GetMapping("/findAll")
    public List<Item> findAll() {
        return itemService.findAll();
    }

/*
    public static void getUsername() {
        //Authentication是spring security中保存用户信息的对象，里面包含用户信息，权限信息，登录密码等。
        // 可以通过SecurityContextHolder来获取Authentication对象，然后再获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
    }


    @GetMapping
    public List<Administrator> pagination2() {
        return adminService.pagination(0, 5);
    }

    @GetMapping("/raw")
    public Administrator test() {
        log.info("Admin - {}", adminService.findById(1L));
        return adminService.findById(12L);  //return raw data
    }

    @GetMapping("/{page}/{pageSize}")
    public List<Administrator> pagination(
            @PathVariable("page") int page,
            @PathVariable("pageSize") int pageSize) {
        return adminService.pagination(page, pageSize);
    }

    @GetMapping("/detail/{id}")
    public Administrator detail(@PathVariable("id") Long id) {
        Administrator admin = adminService.findById(id);
        System.out.println(admin);
        return admin;
    }

    @GetMapping("/status/{rid}/{status}")
    public RedirectView approval(@PathVariable("rid") Long rid, @PathVariable("status") int status) {
        itemService.approval(rid, status);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/submission.html");
        return redirectView;
    }


@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {
    String username = user.getName();
    String password = user.getPassword();

    // 对密码进行 MD5 加密
    password = DigestUtils.md5DigestAsHex(password.getBytes());

    // TODO: 验证用户名和密码的逻辑
    if ("admin".equals(username) && "encrypted_password".equals(password)) { // 示例验证逻辑
        // 验证通过，返回登录成功信息
        return ResponseEntity.ok(userRepository.);
    } else {
        // 验证失败，返回错误信息
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}*/

    @PostMapping("/login")
    public void login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) throws IOException {

        // 打印用户名和密码
        log.info("Username: {}, Password: {}", username, password);

        // 对密码进行 MD5 加密
        // password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 验证用户名和密码
        if (userService.findByName(username) != null) {
            // 重定向到 /item/index
            response.sendRedirect("/user/index");
        } else {
            // 设置未授权状态码，并返回消息
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid credentials");
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(
//            @RequestParam String username,
//            @RequestParam String password) {
//
//        // 打印用户名和密码
//        log.info("Username: {}, Password: {}", username, password);
//
//        // 对密码进行 MD5 加密
//        //password = DigestUtils.md5DigestAsHex(password.getBytes());
//
//        // 验证用户名和密码
//        //if ("admin".equals(username) && "encrypted_password".equals(password)) {
//        if(true){
//            return ResponseEntity.ok(userService.findByName(username));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }

    @GetMapping("/logout")
    public RedirectView logout() {
        SecurityContextHolder.clearContext();

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8081/item/index");
        return redirectView;
    }

    /*
    @GetMapping("/{type}/{keyword}")
    public List<Administrator> search(
            @PathVariable("type") String type,
            @PathVariable("keyword") String keyword) {
        return adminService.search(type, keyword);
    }

    @GetMapping("/unapp")
    public List<Item> unapp(){
        return itemService.findUnapp();
    }



    @PostMapping("/saving")
    public void saving(@ModelAttribute("admin") Administrator admin) {
        adminService.save(admin);
        System.out.println(admin);
    }

    @Transactional
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RedirectView saveItem(@ModelAttribute("item") Item i, @RequestParam(value="file", required = false) MultipartFile file ) {
        System.out.println("-----------SAVING------------");
        System.out.println(i);
        if (!file.isEmpty()) {
            try {
                // 获取文件名
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                // 将文件保存到目标路径
                Path targetLocation = Paths.get("src/main/webapp/static/asset/" + fileName).toAbsolutePath().normalize();
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                i.setFile_path("/static/asset/" + StringUtils.cleanPath(file.getOriginalFilename()));
                i.setFile_format(file.getOriginalFilename().split("\\.", 2)[1]);
                System.out.println("File uploaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to upload file.");
            }
        } else {
            System.out.println("File is empty.");
        }

        itemService.save(i);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/submission.html");
        return redirectView;
    }

    @GetMapping("/get/{id}")
    public Item get(@PathVariable("id") Long id) {
        System.out.println(itemService.findById(id));
        return itemService.findById(id);
    }

    @GetMapping("/edit/{id}")
    public RedirectView edit(@PathVariable("id") Long id) {
        return new RedirectView("http://localhost:8080/edit.html?rid=" + id);
    }
*/


}

