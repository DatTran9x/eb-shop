package com.example.ebshop.dto;

import com.example.ebshop.dto.request.AuthorDTO;
import com.example.ebshop.dto.response.BestCustomerDTO;

import java.util.Comparator;

public class SortForCustomer implements Comparator<BestCustomerDTO> {
    @Override
    public int compare(BestCustomerDTO a, BestCustomerDTO b)
    {
        return a.getTotalMoneySpent().compareTo(b.getTotalMoneySpent());
    }

}
