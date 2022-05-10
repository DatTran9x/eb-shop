package com.example.ebshop.dto;

import com.example.ebshop.dto.response.PublisherDTO;

import java.util.Comparator;

public class SortForPublisher implements Comparator<PublisherDTO> {
    @Override
    public int compare(PublisherDTO a, PublisherDTO b)
    {
        return a.getTotalQuantitySold().compareTo(b.getTotalQuantitySold());
    }

}
