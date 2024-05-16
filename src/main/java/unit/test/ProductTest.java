package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.ProductDAO;
import org.project.iotprojecttest.model.objects.Product;

public class ProductTest {
    private ProductDAO productDAO;

    @Before
    public void setup() {
        productDAO = new ProductDAO();
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductType("Test Type");
        product.setUnitPrice(9.99);
        product.setQuantity(10);

        int productId = productDAO.createProduct(product);

        Assert.assertTrue(productId > 0);
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductType("Test Type");
        product.setUnitPrice(9.99);
        product.setQuantity(10);
        int productId = productDAO.createProduct(product);

        Product updatedProduct = productDAO.getProductById(productId);
        updatedProduct.setUnitPrice(19.99);
        productDAO.updateProduct(updatedProduct);

        Product retrievedProduct = productDAO.getProductById(productId);

        Assert.assertEquals(19.99, retrievedProduct.getUnitPrice(), 0.01);
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductType("Test Type");
        product.setUnitPrice(9.99);
        product.setQuantity(10);
        int productId = productDAO.createProduct(product);

        productDAO.deleteProduct(productId);

        Product deletedProduct = productDAO.getProductById(productId);

        Assert.assertNull(deletedProduct);
    }

}