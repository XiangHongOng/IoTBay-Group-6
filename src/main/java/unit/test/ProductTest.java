package unit.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.project.iotprojecttest.model.dao.ProductDAO;
//import org.project.iotprojecttest.model.objects.Customer;
import org.project.iotprojecttest.model.objects.Product;
import java.util.List;

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
        Assert.assertTrue(productId > 0);

        productDAO.deleteProduct(productId);
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

        productDAO.deleteProduct(newlyUpdatedProduct.getProductId());
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
    public void deleteCreatedProduct() {
        Product product = new Product();
        product.setProductName("Test Product 3");
        product.setProductType("Test Type 3");
        product.setUnitPrice(9.99);
        product.setQuantity(10);

        int productId = productDAO.createProduct(product);

        productDAO.deleteProduct(productId);
        Product deletedProduct = productDAO.getProductById(productId);

        Assert.assertNull(deletedProduct);
    }

    @Test
    public void deleteNonExistingProduct() {
        productDAO.deleteProduct(999);
        Product NonExistingProduct = productDAO.getProductById(999);

        Assert.assertNull(NonExistingProduct);
    }

    @Test
    public void viewProductList() {
        List<Product> productList = productDAO.getAllProducts();

        Assert.assertFalse(productList.isEmpty());
    }

    @Test
    public void searchProductByName() {
        Product product1 = new Product();
        product1.setProductName("Test Product 4");
        product1.setProductType("Test Type 4");
        product1.setUnitPrice(9.99);
        product1.setQuantity(10);

        int product1Id = productDAO.createProduct(product1);

        Product product2 = new Product();
        product2.setProductName("Test Product 5");
        product2.setProductType("Test Type 5");
        product2.setUnitPrice(10.01);
        product2.setQuantity(11);

        int product2Id = productDAO.createProduct(product2);

        List<Product> searchedProduct1 = productDAO.searchProductsByName("Test Product 4");

        Assert.assertNotNull(searchedProduct1);
        Assert.assertEquals(1, searchedProduct1.size(), 0.01);
        Assert.assertEquals("Test Product 4", searchedProduct1.get(0).getProductName());

        productDAO.deleteProduct(product1Id);
        productDAO.deleteProduct(product2Id);
    }

    @Test
    public void searchNonExistingProductByName() {
        Product product1 = new Product();
        product1.setProductName("Test Product 6");
        product1.setProductType("Test Type 6");
        product1.setUnitPrice(9.99);
        product1.setQuantity(10);

        int product1Id = productDAO.createProduct(product1);

        Product product2 = new Product();
        product2.setProductName("Test Product 7");
        product2.setProductType("Test Type 7");
        product2.setUnitPrice(10.01);
        product2.setQuantity(11);

        int product2Id = productDAO.createProduct(product2);

        List<Product> searchedProduct2 = productDAO.searchProductsByName("Test Product 999");

        Assert.assertEquals(0, searchedProduct2.size(), 0.01);

        productDAO.deleteProduct(product1Id);
        productDAO.deleteProduct(product2Id);
    }

    @Test
    public void searchProductByType() {
        Product product1 = new Product();
        product1.setProductName("Test Product 9.1");
        product1.setProductType("Test Type 9");
        product1.setUnitPrice(9.99);
        product1.setQuantity(10);

        int product1Id = productDAO.createProduct(product1);

        Product product2 = new Product();
        product2.setProductName("Test Product 9.2");
        product2.setProductType("Test Type 9");
        product2.setUnitPrice(10.01);
        product2.setQuantity(11);

        int product2Id = productDAO.createProduct(product2);

        List<Product> searchedProduct3 = productDAO.searchProductsByType("Test Type 9");

        Assert.assertNotNull(searchedProduct3);
        Assert.assertEquals(2, searchedProduct3.size(), 0.01);
        Assert.assertEquals("Test Type 9", searchedProduct3.get(0).getProductType());

        productDAO.deleteProduct(product1Id);
        productDAO.deleteProduct(product2Id);
    }

    @Test
    public void searchNonExistingProductByType() {
        Product product1 = new Product();
        product1.setProductName("Test Product 10.1");
        product1.setProductType("Test Type 10");
        product1.setUnitPrice(9.99);
        product1.setQuantity(10);

        int product1Id = productDAO.createProduct(product1);

        Product product2 = new Product();
        product2.setProductName("Test Product 10.2");
        product2.setProductType("Test Type 10");
        product2.setUnitPrice(10.01);
        product2.setQuantity(11);

        int product2Id = productDAO.createProduct(product2);

        List<Product> searchedProduct4 = productDAO.searchProductsByName("Test Product 999");

        Assert.assertEquals(0, searchedProduct4.size(), 0.01);

        productDAO.deleteProduct(product1Id);
        productDAO.deleteProduct(product2Id);
    }

}