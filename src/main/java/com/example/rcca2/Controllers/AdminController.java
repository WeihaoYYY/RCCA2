package com.example.rcca2.Controllers;


import com.example.rcca2.Entities.Item;
import com.example.rcca2.Services.ItemService;
import com.example.rcca2.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/itemList")
    public R<List> itemList() {
        return R.ok(itemService.findAll());
    }

    @PutMapping("/status/{itemId}/{status}")
    public R<String> statusOperation(@PathVariable Long itemId, @PathVariable int status) {
        itemService.approval(itemId, status);
        return R.ok("success");
    }




}
