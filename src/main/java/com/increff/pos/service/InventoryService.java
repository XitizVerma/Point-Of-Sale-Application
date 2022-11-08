package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.InventoryReport;
import com.increff.pos.pojo.InventoryPojo;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    public void add(InventoryPojo inventoryPojo)throws ApiException
    {
        if(!isNull(inventoryDao.selectByBarcode(inventoryPojo.getBarcode())))
        {
            throw new ApiException("Inventory Data Alredy Exists");
        }
        if(inventoryPojo.getQuantity()<=0)
        {
            throw new ApiException("Quantity must be Greater than 0");
        }
        inventoryDao.add(inventoryPojo);
    }

    public void bulkAdd(List<InventoryPojo> inventoryPojoList)throws ApiException
    {
        List<String> errorList = new ArrayList<>();
        Integer row =1;
        for(InventoryPojo inventoryPojo : inventoryPojoList)
        {
            if(!isNull(inventoryDao.selectByBarcode(inventoryPojo.getBarcode())))
            {
                errorList.add("Error : row = " + row + " Inventory already exists for barcode "+inventoryPojo.getBarcode());
            }
            if(inventoryPojo.getQuantity()<=0)
            {
                errorList.add("Error : row = "+ row + " Quantity must be greater than 0, Quantity = "+inventoryPojo.getQuantity());
            }
            row++;
        }
        if(CollectionUtils.isEmpty(errorList))
        {
            throwError(errorList);
        }
        for(InventoryPojo inventoryPojo:inventoryPojoList)
        {
            inventoryDao.add(inventoryPojo);
        }

    }
    public InventoryPojo get(String barcode)throws ApiException
    {
        return getCheck(barcode);
    }
    public List<InventoryPojo> getAll()throws ApiException
    {
        return inventoryDao.selectAll();
    }

    public void update(InventoryForm inventoryForm)throws ApiException
    {
        InventoryPojo inventoryPojo=getCheck(inventoryForm.getBarcode());
        inventoryPojo.setQuantity(inventoryForm.getQuantity());
        inventoryDao.update();
    }
    public InventoryPojo getCheck(String barcode)throws ApiException
    {
        InventoryPojo inventoryPojo=inventoryDao.selectByBarcode(barcode);
        if(isNull(inventoryPojo))
        {
            throw new ApiException("Inventory with given barcode does not exist, barcode :"+barcode);
        }
        return inventoryPojo;
    }
    public InventoryPojo selectByBarcode(String barcode)throws ApiException
    {
        return getCheck(barcode);
    }

}
