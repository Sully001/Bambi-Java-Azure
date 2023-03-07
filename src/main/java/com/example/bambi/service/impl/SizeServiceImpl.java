package com.example.bambi.service.impl;

import com.example.bambi.repository.SizeRepository;
import com.example.bambi.service.SizeService;
import com.example.bambi.entity.Size;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        super();
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Size> getSizesByProductId(Long productId) {
        return sizeRepository.findByProductId(productId);
    }

}
