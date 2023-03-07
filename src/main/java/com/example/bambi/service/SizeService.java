package com.example.bambi.service;

import com.example.bambi.entity.Size;

import java.util.List;

public interface SizeService {

    List<Size> getSizesByProductId(Long productId);

}
