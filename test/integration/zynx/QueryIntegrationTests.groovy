package zynx

import static org.junit.Assert.*
import org.junit.*

class QueryIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testPropertyComparison() {
        def users = User.where {
            password == "testing"
        }.list(sort: "userId")

        users*.userId == ["frankie"]
    }

    void testMultipleCriteria() {
        def users = User.where {
            userId == "frankie" || password == "crikey"
        }.list(sort: "userId")

        users*.userId == ["dillon", "frankie", "sara"]
    }

    void testQueryOnAssociation() {
        def users = User.where {
            following.userId == "sara"
        }.list(sort: "userId")

        users*.userId == ["phil"]
    }

    void testQueryAgainstRange() {
        def now = new Date()

        def users = User.where {
            dateCreated in (now-1)..now
        }.list(sort: "userId", order: "desc")

        users*.userId == ["phil", "peter", "glen", "frankie", "chuck_norris", "admin"]
    }

    void testQueryOfSingleInstance() {
        def user = User.where {
            userId == "phil"
        }.get()

        user.password == "thomas"
    }
}
