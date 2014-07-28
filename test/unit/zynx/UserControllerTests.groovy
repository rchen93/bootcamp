package zynx



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User, Profile])
class UserControllerTests {

    void testRegisterGoodUser() {
      params.with {
            userId = "glen_a_smith"
            password = "winnning"
            homepage = "http://blogs.bytecode.com.au/glen"
        }

        params['profile.fullName'] = "Glen Smith"
        params['profile.email'] = "glen@bytecode.com.au"
        params['profile.homepage'] = "http://blogs.bytecode.com.au/glen"

        request.method = "POST"
        this.controller.register()

        assert response.redirectedUrl == '/'
        assert User.count() == 1
        assert Profile.count() == 1
    }
}
