package com.wwx.minishop.service;

import com.wwx.minishop.beans.ImageHolder;
import com.wwx.minishop.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductList(Integer shopId);

    int addProduct(Product product, ImageHolder image, List<ImageHolder> productImgList);
}
