/*******************************************************************************
 * Copyright 2005-2015 Open Source Applications Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.azafiu.hibernatetest.rest;

import java.util.List;

import org.azafiu.hibernatetest.datainteractor.ProductDetailsDAO;
import org.azafiu.hibernatetest.datainteractor.ProductDetailsRepository;
import org.azafiu.hibernatetest.datainteractor.ProductRepository;
import org.azafiu.hibernatetest.entities.ProductDetailsEntity;
import org.azafiu.hibernatetest.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST service for product details
 * 
 * @author andrei.zafiu
 * 
 */
@RestController
@RequestMapping("/rest")
public class ProductDetailsRESTService {

    @Autowired
    private ProductDetailsDAO        productDetailsDAO;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductRepository        productRepository;

    /**
     * Remove a list of product details.
     * 
     * @param products
     *            a list of {@link ProductDetailsEntity} objects to be deleted
     */
    @RequestMapping(value = "/product-details/batch-remove",
            method = RequestMethod.POST, consumes = "application/json")
    public void
            removeProductDetailsBatch(@RequestBody final List<ProductDetailsEntity> products) {

        this.productDetailsRepository.delete(products);

    }

    /**
     * Save a batch of {@link ProductDetailsEntity}
     * 
     * @param productDetailList
     *            a list of {@link ProductDetailsEntity} to be added
     */
    @RequestMapping(value = "/product-details/batch",
            method = RequestMethod.POST, consumes = "application/json")
    @Transactional
    public void
            saveProductDetailsBatch(@RequestBody final List<ProductDetailsEntity> productDetailList) {

        for (int i = 0; i < productDetailList.size(); i++) {
            final ProductDetailsEntity productDetails = productDetailList.get(i);

            final ProductEntity product = this.productRepository.findOne(productDetails.getFkProduct());

            /**
             * for each {@link ProductDetailsEntity} find the corresponding
             * {@link ProductEntity} and assign it to the product detail
             */
            productDetails.setProduct(product);
        }

        this.productDetailsRepository.save(productDetailList);
    }

}
