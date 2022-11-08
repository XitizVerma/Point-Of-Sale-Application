package com.increff.pos.dao;


import com.increff.pos.model.InventoryReport;
import com.increff.pos.pojo.InventoryPojo;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{
    private final String SELECT_BY_BARCODE = "select p from InventoryPojo p where barcode=:barcode";


    public void add(InventoryPojo inventoryPojo)
    {
        addAbs(inventoryPojo);
    }
    public InventoryPojo select(int id)
    {
        return select(InventoryPojo.class,id);
    }

    public List<InventoryPojo> selectAll()
    {
        return selectAll(InventoryPojo.class);
    }
    public InventoryPojo selectByBarcode(String  barcode)
    {
        TypedQuery<InventoryPojo> query = em().createQuery(SELECT_BY_BARCODE, InventoryPojo.class);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }

    public void update()
    {
        //Symbolic
    }

}
