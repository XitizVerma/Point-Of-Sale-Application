package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static java.util.Objects.isNull;

import static com.increff.pos.dto.dtoHelper.ProductDtoHelper.validateBarcode;
import static com.increff.pos.dto.dtoHelper.ProductDtoHelper.validateMRP;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public void add(ProductPojo productPojo)throws ApiException{
        validateBarcode(productPojo.getBarcode());
        validateMRP(productPojo.getMrp());
        checkBarcodeExist(productPojo.getBarcode());
        productDao.add(productPojo);

    }

    public void bulkAdd(List<ProductPojo> productPojoList)throws ApiException
    {
        for(ProductPojo productPojo : productPojoList)
        {
            add(productPojo);
        }
    }

    @Transactional(readOnly = true)
    public ProductPojo get(int id)throws ApiException{
        return getCheck(id);
    }
    public List<ProductPojo> getAll()throws ApiException
    {
        return productDao.selectAll();
    }

    public void update(ProductPojo productPojo)throws ApiException
    {
        validateBarcode(productPojo.getBarcode());
        validateMRP(productPojo.getMrp());
        ProductPojo productPojo1 = productDao.selectByBarcode(productPojo.getBarcode());
        if(isNull(productPojo1))
        {
            throw new ApiException("Product with the given Barcode does not exist , barcode : "+productPojo.getBarcode());
        }
        productPojo1.setBrandCategoryId(productPojo.getBrandCategoryId());
        productPojo1.setName(productPojo.getName());
        productPojo1.setMrp(productPojo.getMrp());
//        TODO update
    }

    public List<ProductPojo> selectByBrandId(Integer brandId) {
        return productDao.selectByBrandId(brandId);
    }

    public ProductPojo selectByBarcode(String barcode)
    {
        return productDao.selectByBarcode(barcode);
    }

    public ProductPojo getCheck(Integer id)throws ApiException{
        ProductPojo productPojo=productDao.select(id);
        if(productPojo == null)
        {
            throw new ApiException("Product with given id does not exist, id : "+id);
        }
        return productPojo;
    }
    private void checkBarcodeExist(String barcode)throws ApiException
    {
        if(!isNull(productDao.selectByBarcode(barcode)))
        {
            throw new ApiException("Product exists for this barcode : " + barcode);
        }

    }

}
