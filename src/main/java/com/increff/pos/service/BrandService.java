package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.increff.pos.util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

@Service
@Transactional(rollbackFor = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    @Transactional(readOnly = true)
    public List<BrandPojo> getAll() {
        return brandDao.selectAll();
    }

    public void add(BrandPojo brandPojo) throws ApiException {
        checkUnique(brandPojo);
        brandDao.add(brandPojo);
    }

    public void bulkAdd(List<BrandPojo> brandPojoList) throws ApiException
    {
        List<String> errorList = new ArrayList<>();
        Integer row = 1;
        try{
            for (BrandPojo brandPojo : brandPojoList) {
                checkUnique(brandPojo);
                add(brandPojo);
                row++;
            }
        }catch (ApiException e){
            errorList.add(e.getMessage());
        }catch (Exception e){
            errorList.add(e.getMessage());
        }
        if (!CollectionUtils.isEmpty(errorList))
            throwError(errorList);

    }

    public BrandPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    public BrandPojo getCheck(Integer id) throws ApiException {
        BrandPojo brandPojo = brandDao.select(id);
        if (isNull(brandPojo)) {
            throw new ApiException("Brand with given id does not exist,id : " + id);
        }
        return brandPojo;
    }

    public void update(BrandPojo brandPojo,Integer id) throws ApiException
    {
        checkUnique(brandPojo);
        BrandPojo exists = getCheck(id);
        exists.setBrand(brandPojo.getBrand());
        exists.setCategory(brandPojo.getCategory());
        brandDao.update();
    }

    public BrandPojo selectByBrandCategory(String brand, String category) {
        return brandDao.selectByBrandCategory(brand, category);
    }

    public BrandPojo selectCheckByBrandCategory(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandDao.selectByBrandCategory(brand,category);
        if (isNull(brandPojo)) {
            throw new ApiException("Brand with given brand-category comibnation does not exist");
        }
        return brandPojo;
    }

    public List<BrandPojo> selectByBrand(String brand) {
        return brandDao.selectByBrand(brand);
    }

    public List<BrandPojo> selectByCategory(String category) {
        return brandDao.selectByCategory(category);
    }

    private void checkUnique(BrandPojo brandPojo) throws ApiException {
        if (!Objects.isNull(selectByBrandCategory(brandPojo.getBrand(), brandPojo.getCategory()))) {
            throw new ApiException(brandPojo.getBrand() + " - " + brandPojo.getCategory() + " pair already exists");
        }
    }


}
