package com.example.rcca2.Services;

import com.example.rcca2.DTO.ItemDetailsDTO;
import com.example.rcca2.Entities.Item;

import java.util.List;

public interface ItemService {

    List<Item> findUnapp();  // Find unapproved items

    List<Item> findApp();  // Find approved items

    List<Item> findAll();

    void delete(Long id);

    void approval(Long rid, int status);

    Item findById(Long id);

    void save(Item item);

    List<Item> pagination(Integer pageNum, Integer pageSize);

    List<Item> searchItemsByType(String type, String title);  //

    ItemDetailsDTO findItemDetailsByID(Long id);
}
