package com.kevinberg.kontespringboot.backend.services;

import com.kevinberg.kontespringboot.backend.entity.Category;
import com.kevinberg.kontespringboot.backend.entity.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Transactional //whener you use entitymanager methods?
public class CategoryService {

    @Autowired
    EntityManager em;


    public Long createCategory(String name) {

        Category category = new Category();
        category.setName(name);

        em.persist(category);

        return category.getId();
    }

    public Long createSubCategory(long parentId, String name) {

        Category category = em.find(Category.class, parentId);
        if(category==null) {
            throw new IllegalArgumentException("Category with id "+parentId+" does not exist");
        }


        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setParent(category);

        em.persist(subCategory);

        return subCategory.getId();
    }

    public Category getCategory(long id, boolean withSub){

        Category category = em.find(Category.class, id);
        if(withSub && category != null){
            category.getSubCategories().size();
        }

        return category;
    }

    public SubCategory getSubCategory(long id){

        return em.find(SubCategory.class, id);
    }


    public List<Category> getAllCategories(boolean withSub) {

        TypedQuery<Category> query = em.createQuery("select c from Category c", Category.class);
        List<Category> categories = query.getResultList();

        if(withSub) {
            categories.forEach(c -> c.getSubCategories().size());
        }

        return categories;
    }
}
