package com.example.rcca2;

import com.example.rcca2.Entities.Item;
import com.example.rcca2.Services.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Rcca2ApplicationTests {

    @Autowired
    private ItemService itemService;

    @Test
    void search() {
        List<Item> items = itemService.searchItemsByType("SalesBrochure", "1987");
        for(Item item : items) {
            System.out.println(item.getRid());
        }
    }

    @Test
    void approval(){
        itemService.approval(24L, 2);
    }

}
