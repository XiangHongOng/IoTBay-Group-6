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
        product.setUnitPrice(10.0);
        product.setQuantity(100);

        ProductDAO productDAO = new ProductDAO();
        int productId = productDAO.createProduct(product);

        Assert.assertNotEquals(0, productId);

        Product createdProduct = productDAO.getProductById(productId);
        if (createdProduct != null)
        {
            productDAO.deleteProduct(productId);
        }
    }

    @Test
    public void testGetProductById() {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(1);

        Assert.assertNotNull(product);
        Assert.assertEquals(1, product.getProductId());
    }

    @Test
    public void testUpdateProduct() {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(1);

        product.setProductName("Updated Product");
        product.setProductType("Updated Type");
        product.setUnitPrice(20.0);
        product.setQuantity(200);

        productDAO.updateProduct(product);

        Product updatedProduct = productDAO.getProductById(1);
        Assert.assertEquals("Updated Product", updatedProduct.getProductName());
        Assert.assertEquals("Updated Type", updatedProduct.getProductType());
        Assert.assertEquals(20.0, updatedProduct.getUnitPrice(), 0.001);
        Assert.assertEquals(200, updatedProduct.getQuantity());
    }

    @Test
    public void testDeleteProduct() {
        ProductDAO productDAO = new ProductDAO();
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductType("Test Type");
        product.setUnitPrice(10.0);
        product.setQuantity(100);

        int productId = productDAO.createProduct(product);

        productDAO.deleteProduct(productId);

        Product deletedProduct = productDAO.getProductById(productId);
        Assert.assertNull(deletedProduct);
    }

}