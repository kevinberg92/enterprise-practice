package org.kevinberg.konteprise.quizgame.services;

import org.kevinberg.konteprise.quizgame.entity.Category;
import org.kevinberg.konteprise.quizgame.entity.SubCategory;
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


    public List<Category> getAllCategories(boolean withSub) {

        TypedQuery<Category> query = em.createQuery("select c from Category c", Category.class);
        List<Category> categories = query.getResultList();

        if(withSub) {
            categories.forEach(c -> c.getSubCategories().size());
        }

        return categories;
    }
}
