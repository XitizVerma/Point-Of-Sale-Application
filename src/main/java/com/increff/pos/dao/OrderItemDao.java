package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{

    private static final String DELETE="delete from OrderItemPojo where orderId=:orderId and barcode=:barcode";

    private static final String SELECT_BY_ORDER_ID = "select p from OrderItemPojo p where orderId=:orderId";

    private static final String SELECT_BY_BARCODE = "select p from OrderItemPojo p where barcode=:barcode";

    private static final String SELECT_BY_ORDER_ID_BARCODE = "select p from OrderItemPojo p where barcode=:barcode and orderId=:orderId";

    private static final String SELECT_BY_ORDER_ID_LIST = "select p from OrderItemPojo p where orderId in :orderIdList";


    public void add(OrderItemPojo orderItemPojo)
    {
        addAbs(orderItemPojo);
    }

    public List<OrderItemPojo> selectAll()
    {
        return selectAll(OrderItemPojo.class);
    }

    public OrderItemPojo select(int id)
    {
        return select(OrderItemPojo.class,id);
    }

    public List<OrderItemPojo> selectFromOrderId(int orderId)
    {
        TypedQuery<OrderItemPojo> query=em().createQuery(SELECT_BY_ORDER_ID,OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        return query.getResultList();
    }

    public List<OrderItemPojo> selectFromOrderIdList(List<Integer>orderIdList)
    {
        TypedQuery<OrderItemPojo>query= em().createQuery(SELECT_BY_ORDER_ID_LIST, OrderItemPojo.class);
        query.setParameter("orderIdList",orderIdList);
        return query.getResultList();
    }

    public OrderItemPojo selectFromOrderIdBarcode(Integer orderId,String barcode)
    {
        TypedQuery<OrderItemPojo> query=em().createQuery(SELECT_BY_ORDER_ID_BARCODE, OrderItemPojo.class);
        query.setParameter("orderId",orderId);
        query.setParameter("barcode",barcode);
        return getSingle(query);
    }

    public void update()
    {
        //SYMBOLIC
    }

    public int delete(int orderId,String barcode)
    {
        Query query = em().createQuery(DELETE);
        query.setParameter("orderId",orderId);
        query.setParameter("barcode",barcode);
        return query.executeUpdate();

    }
}
