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
                password: 'secret')
                //homepage: 'http://www.grailsinaction.com')
        assert user.save() != null
        assert user.id != null

        def foundUser = User.get(user.id)
        assert 'joe' == foundUser.userId

    }

    void testSaveAndUpdate() {

        def user = new User(
                userId: 'joe',
                password: 'secret')
                //homepage: 'http://www.grailsinaction.com')
        assert user.save() != null

        def foundUser = User.get(user.id)
        foundUser.password = 'sesame'
        foundUser.save()

        def editedUser = User.get(user.id)
        assert 'sesame' == editedUser.password

    }

    void testSaveThenDelete() {

        def user = new User(
                userId: 'joe',
                password: 'secret')
               // homepage: 'http://www.grailsinaction.com')
        assert user.save() != null

        def foundUser = User.get(user.id)
        foundUser.delete()

        assert !User.exists(foundUser.id)

    }

    void testEvilSave() {
        def user = new User(
                userId: 'chuck_norris',
                password: 'tiny')
                //homepage: 'not-a-url')

        if (user.validate())
            user.save()
        else 
            user.discard()
        assert user.hasErrors()

        def errors = user.errors

        assert "size.toosmall" == errors.getFieldError("password").code
        assert "tiny" == errors.getFieldError("password").rejectedValue

        assert !errors.getFieldError("userId")       // Checks valid fields not in error

    }

    void testEvilSaveCorrected() {
       def user = new User(
                userId: 'chuck_norris',
                password: 'tiny')
                //homepage: 'not-a-url')
        assert !user.validate()
        assert user.hasErrors()
        assert user.save() == null

        user.password = "fistfist"
       // user.homepage = "http://www.chucknorrisfacts.com"

        assert user.validate()
        assert !user.hasErrors()
        assert user.save() != null
    }

    void testFollowing() {
  
        def glen = new User(userId: 'glen', password:'password').save()
        def peter = new User(userId: 'peter', password:'password').save()
        def sven = new User(userId: 'sven', password:'password').save()

        glen.addToFollowing(peter)
        glen.addToFollowing(sven)
        assertEquals 2 == glen.following.size()

        sven.addToFollowing(peter)
        assertEquals 1 == sven.following.size()
    }
}
