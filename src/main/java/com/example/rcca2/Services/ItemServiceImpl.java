package com.example.rcca2.Services;

import com.example.rcca2.DTO.ItemDetailsDTO;
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
public class ItemServiceImpl implements ItemService{

    @Autowired
    ItemRepository repo;

    public ItemDetailsDTO findItemDetailsByID(Long id) {
        Item item = repo.findById(id).orElse(null);
        return convertToDto(item);
    }

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

    @Transactional(rollbackFor = Exception.class)
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

    public ItemDetailsDTO convertToDto(Item item) {
        ItemDetailsDTO dto = new ItemDetailsDTO();
        dto.setSid(item.getSid());
        dto.setApprovedDate(item.getApproved_date());
        dto.setAuthor(item.getAuthor());
        dto.setCategory(item.getCategory());
        dto.setContributor(item.getContributor());
        dto.setDescription(item.getDescription());
        dto.setEmail(item.getEmail());
        dto.setEngine(item.getEngine());
        dto.setFileFormat(item.getFile_format());
        dto.setFilePath(item.getFile_path());
        dto.setLastUpdatedDate(item.getLast_updated_date().toString());
        dto.setMake(item.getMake());
        dto.setModel(item.getModel());
        dto.setPublishDate(item.getPublish_date().toString());
        dto.setSource(item.getSource());
        dto.setStatus(item.getStatus());
        dto.setTitle(item.getTitle());
        //dto.setUid(item.getUid());  //TODO 转换成User用户名
        dto.setYear(item.getYear());
        return dto;
    }

}
