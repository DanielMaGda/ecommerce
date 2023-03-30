package com.danmag.ecommerce.service.dto;


public class ProductFeatureDTO {
    private long id;
    //    @JsonIgnoreProperties("features")
    private ProductDTO product;
    private FeatureDTO feature;

    private FeatureValueDTO featureValueDTO;

    public FeatureValueDTO getFeatureValueDTO() {
        return featureValueDTO;
    }

    public void setFeatureValueDTO(FeatureValueDTO featureValueDTO) {
        this.featureValueDTO = featureValueDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public FeatureDTO getFeature() {
        return feature;
    }

    public void setFeature(FeatureDTO feature) {
        this.feature = feature;
    }


}
