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

}