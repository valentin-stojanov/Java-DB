package com.example.xml_processing.services;

import com.example.xml_processing.entities.products.ExportProductsInRangeDTO;

public interface ProductService {

    ExportProductsInRangeDTO getInRange(float from, float to);
}
