package com.example.rcca2.Services;

import com.example.rcca2.Entities.AdminRepository;
import com.example.rcca2.Entities.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    public void save(Administrator admin) {
        adminRepo.save(admin);
    }

    public Administrator findById(Long id) {
        return adminRepo.findById(id).orElse(null);
    }

    public Administrator findByName(String name) {
        return adminRepo.findByName(name);
    }

    public String findRoleByName(String name) {
        return adminRepo.findRoleByName(name);
    }

    public List<Administrator> search (String type, String keyword) {
        return adminRepo.search(type, keyword);
    }


    public void delete(Long id) {
        adminRepo.deleteById(id);
    }

    public Boolean ifExist(Long id){
        return findById(id) != null;
    }

    public List<Administrator> pagination(Integer pageNum,Integer pageSize) {

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        Page<Administrator> admin = adminRepo.findAll(pageRequest);

        return admin.getContent();
    }

}
