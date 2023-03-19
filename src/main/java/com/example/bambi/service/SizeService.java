package com.example.bambi.service;

import com.example.bambi.entity.Size;

import java.util.List;

public interface SizeService {

    List<Size> getSizesByProductId(Long productId);

    Size saveSize(Size size);

    Size getSizeById(Long sizeId);

    void deleteSizeById(Long sizeId);

    List<Size> getSizesLowInStock();

    List<Size> getSizesOutOfStock();
}
