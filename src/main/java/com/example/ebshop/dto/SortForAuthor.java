package com.example.ebshop.dto;

import com.example.ebshop.dto.request.AuthorDTO;

import java.util.Comparator;

public class SortForAuthor implements Comparator<AuthorDTO> {
    @Override
    public int compare(AuthorDTO a, AuthorDTO b)
    {
        return a.getTotalQuantitySold().compareTo(b.getTotalQuantitySold());
    }

}
