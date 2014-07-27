package zynx

import static org.junit.Assert.*
import org.junit.*

class UserIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

   // Integration tests always returns to the database's original state
    @Test
    void testFirstSaveEver() {

        def user = new User(
                userId: 'joe',
                password: 'secret',
                homepage: 'http://www.grailsinaction.com')
        assertNotNull user.save()
        assertNotNull user.id

        def foundUser = User.get(user.id)
        assert 'joe' == foundUser.userId

    }

    void testSaveAndUpdate() {

        def user = new User(
                userId: 'joe',
                password: 'secret',
                homepage: 'http://www.grailsinaction.com')
        assertNotNull user.save()

        def foundUser = User.get(user.id)
        foundUser.password = 'sesame'
        foundUser.save()

        def editedUser = User.get(user.id)
        assert 'sesame' == editedUser.password

    }

    void testSaveThenDelete() {

        def user = new User(
                userId: 'joe',
                password: 'secret',
                homepage: 'http://www.grailsinaction.com')
        assertNotNull user.save()

        def foundUser = User.get(user.id)
        foundUser.delete()

        assert !User.exists(foundUser.id)

    }

    void testEvilSave() {
        def user = new User(userId: 'galen', password: 'tiny',
            homepage: 'http://www.zynxhealth.com')

        if (user.validate())
            user.save()
        else 
            user.discard()
        assertTrue user.hasErrors()

        def errors = user.errors

        assertEquals "size.toosmall", errors.getFieldError("password").code
        assertEquals "tiny", errors.getFieldError("password").rejectedValue

        user.password = 'galen'
        assertFalse(user.validate())
        assertTrue(user.hasErrors())

        assertNull errors.getFieldError("userId")
    }

    void testEvilSaveCorrected() {
        def user = new User(userId: 'galen', password: 'tiny',
            homepage: 'http://www.zynxhealth.com')

        assertFalse(user.validate())
        assertTrue(user.hasErrors())
        assertNull user.save()

        user.password = "password#1"
        assertTrue(user.validate())
        assertFalse(user.hasErrors())
        assertNotNull user.save()
    }


}
