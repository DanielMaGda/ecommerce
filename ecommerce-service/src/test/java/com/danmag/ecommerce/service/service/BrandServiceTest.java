package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.BrandDTO;
import com.danmag.ecommerce.service.dto.response.AccountResponse;
import com.danmag.ecommerce.service.model.Brand;
import com.danmag.ecommerce.service.repository.BrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class BrandServiceTest {
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        brandService = new BrandService(brandRepository, modelMapper);
    }

    @Test
    void should_get_list_of_brands() {
        //given
        List<Brand> brandList = Arrays.asList(
                Brand.builder()
                        .id(1)
                        .name("hp")
                        .build(),
                Brand.builder()
                        .id(2)
                        .name("lenovo")
                        .build()
        );
        List<BrandDTO> brandDTOS = Arrays.asList(
                BrandDTO.builder()
                        .id(1)
                        .name("hp")
                        .build(),
                BrandDTO.builder()
                        .id(2)
                        .name("lenovo")
                        .build()
        );
        when(brandRepository.findAll()).thenReturn(brandList);
        when(modelMapper.map(brandList.get(0), BrandDTO.class)).thenReturn(brandDTOS.get(0));
        when(modelMapper.map(brandList.get(1), BrandDTO.class)).thenReturn(brandDTOS.get(1));

        List<BrandDTO> returnedBrandDtos = brandService.getAllBrands();

        assertEquals(2, returnedBrandDtos.size());
        assertEquals("hp", returnedBrandDtos.get(0).getName());
        assertEquals("lenovo", returnedBrandDtos.get(1).getName());
        verify(brandRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(brandList.get(0), BrandDTO.class);
        verify(modelMapper, times(1)).map(brandList.get(1), BrandDTO.class);
    }


}