package com.example.bambi.service.impl;

import com.example.bambi.repository.SizeRepository;
import com.example.bambi.service.SizeService;
import com.example.bambi.entity.Size;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Size saveSize(Size size) {
        return sizeRepository.save(size);
    }

    @Override
    public Size getSizeById(Long id) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            return optionalSize.get();
        } else {
            throw new RuntimeException("Size not found for id :: " + id);
        }
    }

    @Override
    public void deleteSizeById(Long sizeId) {
        sizeRepository.deleteById(sizeId);
    }

    @Override
    public List<Size> getSizesLowInStock() {
        return sizeRepository.getSizesLowInStock();
    }
    @Override
    public List<Size> getSizesOutOfStock() {
        return sizeRepository.getSizesOutOfStock();
    }
}
