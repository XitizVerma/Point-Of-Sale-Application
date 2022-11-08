package com.increff.pos.model;

import com.increff.pos.exception.ApiException;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Getter
@Setter
public class SalesReportForm
{
    @NotNull(message="Brand cant be Null")
    private String brand;

    @NotNull(message="Category cant be Null")
    private String category;

    @NotNull
    private ZonedDateTime from;

    @NotNull
    private ZonedDateTime to;

    //FORMAT CHECK VALIDATION
    public void setFrom(String from)throws ApiException
    {
        try{
            this.from=ZonedDateTime.parse(from);
        }catch (Throwable e)
        {
            throw new ApiException("Invalid! The from date time format must be of the Format ZonedDateTime");
        }
    }

    public void setTo(String to)throws ApiException
    {
        try{
            this.to=ZonedDateTime.parse(to);
        }catch(Throwable e)
        {
            throw new ApiException("Invalid! The to date time format must be of the Format ZonedDateTime");
        }
    }

    @Override
    public String toString()
    {
        return this.getBrand()+", "+this.getCategory()+", "+this.getFrom()+", "+this.getTo();
    }

}
