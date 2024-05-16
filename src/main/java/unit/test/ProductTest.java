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
    public void CreateNewProduct() {
        Product product = new Product();
        product.setProductName("Test Product 1");
        product.setProductType("Test Type 1");
        product.setUnitPrice(9.99);
        product.setQuantity(10);

        int productId = productDAO.createProduct(product);

        Assert.assertNotNull(product);
        Assert.assertEquals(product.getProductName(), "Test Product 1");
        Assert.assertEquals(product.getProductType(), "Test Type 1");
        Assert.assertEquals(9.99, product.getUnitPrice(), 0.01);
        Assert.assertEquals(10, product.getQuantity(), 0.01);
        Assert.assertTrue(productId > 20);
    }

    @Test
    public void UpdateExistingProduct() {
        Product product = new Product();
        product.setProductName("Test Product 2");
        product.setProductType("Test Type 2");
        product.setUnitPrice(9.99);
        product.setQuantity(10);

        int productId = productDAO.createProduct(product);

        Product updatedProduct = productDAO.getProductById(productId);
        updatedProduct.setProductName("Test Product 2.1");
        updatedProduct.setProductType("Test Type 2.1");
        updatedProduct.setUnitPrice(10.01);
        updatedProduct.setQuantity(11);
        productDAO.updateProduct(updatedProduct);

        Product newlyUpdatedProduct = productDAO.getProductById(productId);

        Assert.assertNotNull(newlyUpdatedProduct);
        Assert.assertEquals(newlyUpdatedProduct.getProductName(), "Test Product 2.1");
        Assert.assertEquals(newlyUpdatedProduct.getProductType(), "Test Type 2.1");
        Assert.assertEquals(10.01, newlyUpdatedProduct.getUnitPrice(), 0.01);
        Assert.assertEquals(11, newlyUpdatedProduct.getQuantity(), 0.01);
    }

    @Test
    public void UpdateNonExistingProduct() {
        Product product = new Product();
        product.setProductId(999);
        product.setProductName("NON");
        product.setProductType("NON");
        product.setUnitPrice(0);
        product.setQuantity(0);

        productDAO.updateProduct(product);

        Product newlyUpdatedProduct = productDAO.getProductById(999);

        Assert.assertNull(newlyUpdatedProduct);
    }

    @Test
    public void deleteCreateProduct() {
        Product deletedProduct = productDAO.getProductById(21);

        Assert.assertNull(deletedProduct);
    }

    @Test
    public void deleteNonExistingProduct() {
        Product NonExistingProduct = productDAO.getProductById(999);

        Assert.assertNull(NonExistingProduct);
    }


}