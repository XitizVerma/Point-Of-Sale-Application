package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao extends AbstractDao{

    private final static String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode=:barcode";
    private final static String SELECT_BY_BRAND_ID = "select p from ProductPojo p where brandCategoryId =:brandCategoryId";

    public void add(ProductPojo productPojo)
    {
        addAbs(productPojo);

    }

    public ProductPojo selectByBarcode(String barcode)
    {
        TypedQuery<ProductPojo> query = em().createQuery(SELECT_BY_BARCODE, ProductPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }
    public List<ProductPojo> selectByBrandId(int brandCategoryId)
    {
        TypedQuery<ProductPojo> query=em().createQuery(SELECT_BY_BRAND_ID, ProductPojo.class);
        query.setParameter("brandCategoryId",brandCategoryId);
        return query.getResultList();
    }

    public ProductPojo select(int id)
    {
        return select(ProductPojo.class,id);
    }

    public List<ProductPojo> selectAll()
    {
        return selectAll(ProductPojo.class);
    }

}
