package com.example.rcca2.Services;

import com.example.rcca2.Entities.Item;
import com.example.rcca2.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    ItemRepository repo;

    public List<Item> findUnapp(){
        return repo.searchItemsByStatusEquals(0);
    }

    public List<Item> findApp(){
        return repo.searchItemsByStatusEquals(1);
    }

    public List<Item> findAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void approval(Long rid, int status) {
        repo.approval(rid, status);
    }

    public Item findById(Long id) {
        return repo.findById(id).get();
    }

    public void save(Item item) {
        repo.save(item);
    }

    public List<Item> pagination(Integer pageNum, Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        Page<Item> items = repo.findAll(pageRequest);

        return items.getContent();
    }

    public List<Item> searchItemsByType(String type, String title) {
        title = "%" + title + "%";
        //return repo.searchItemsByStatusEqualsAndTypeAndTitleLike(1, type, title);
        return null;
    }
}
