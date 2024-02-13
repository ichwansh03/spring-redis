package com.ichwan.springredis.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductService {

    /**
     * data pada anotasi Cacheable datanya tidak akan berubah
     */
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(String id){
      log.info("product by id: {}",id);
      return Product.builder().id(id).name("Sample").price(1000L).build();
    }

    /**
     * data pada anotasi CachePut datanya akan berubah
     */
    @CachePut(value = "products", key = "#product.id")
    public Product create(Product product){
        log.info("save product: {}",product);
        return product;
    }

    /**
     * selain gunakan TTL, cache evict akan menghapus data dari cache
     */
    @CacheEvict(value = "products", key = "#id")
    public void remove(String id) {
        log.info("remove id: {}",id);
    }
}
