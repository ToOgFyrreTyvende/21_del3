package functionality;

import static org.junit.jupiter.api.Assertions.*;

import dal.IUserDAO;
import dal.UserDAOMySQL;
import dto.UserDTO;
import org.junit.jupiter.api.*;
import utils.CreateDB;

class UserFunctionalityTest {
    // Vars
    private static String dbTestName = "21_d1_users_test";
    private IUserFunctionality func;
    private UserDTO testUser1;
    private UserDTO testUser2;

    @BeforeAll
    static void setUpTestTable(){
        // Generate test table
        CreateDB creator = new CreateDB();
        creator.generateTable(dbTestName);
    }

    @BeforeEach
    void setUp(){
        // reset dao and func before each test is run
        IUserDAO dao = new UserDAOMySQL(dbTestName);
        func = new UserFunctionality(dao);
        // testUser1 values set:
        testUser1 = new UserDTO();
        do {
            testUser1.setUserId(42);
            testUser1.setUserName("testUser");
            testUser1.setIni("test");
            testUser1.setCpr("123456-7890");
            testUser1.setPassword("testPass");
            testUser1.addRole("Operator");
        } while (false);
        testUser2 = new UserDTO();
        do {
            testUser2.setUserId(2);
            testUser2.setUserName("derp");
            testUser2.setIni("derp");
        } while (false);
    }

    @Test
    void createUpdateDeleteUser() throws IUserFunctionality.UserInputException, IUserDAO.DALException{
        // Create 'testUser1' in database
        func.createUser(testUser1);
        // Check user was correctly created
        assertEquals(testUser1.getUserId(), func.getUser(testUser1.getUserId()).getUserId());
        assertEquals(testUser1.getUserName(), func.getUser(testUser1.getUserId()).getUserName());
        // Update 'testUser1'
        testUser1.setUserName("UpdatedTestUser");
        testUser1.setIni("UTU");
        // Check 'testUser1' is different from user in database
        assertNotEquals(testUser1.getUserName(), func.getUser(testUser1.getUserId()).getUserName());
        assertNotEquals(testUser1.getIni(), func.getUser(testUser1.getUserId()).getIni());
        // Update user in database
        func.updateUser(testUser1);
        // Check database user has been updated with correct values
        assertEquals(testUser1.getUserName(), func.getUser(testUser1.getUserId()).getUserName());
        assertEquals(testUser1.getIni(), func.getUser(testUser1.getUserId()).getIni());
        // Delete user
        func.deleteUser(testUser1.getUserId());
        // Check user has been correctly deleted
        assertEquals("[]", func.getUserList().toString());
    }

    @Test
    void exceptionTesting(){
        IUserFunctionality.UserInputException createThrown = assertThrows(IUserFunctionality.UserInputException.class,
                () -> func.createUser(testUser2),
                "Should throw a nice custom error... But i guess not");

        assertTrue(createThrown.getMessage().contains("Fejl"));
    }
}