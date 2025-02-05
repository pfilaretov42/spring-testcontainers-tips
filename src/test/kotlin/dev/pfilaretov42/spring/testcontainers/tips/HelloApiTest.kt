package dev.pfilaretov42.spring.testcontainers.tips

import org.junit.jupiter.api.Test

class HelloApiTest : AppAbstractTest() {
    @Test
    fun `test stop container`() {
        println("api test")
    }

    // add db record 1
    // get db records count = 1
    // with reuse flag - this fill fail next time the test runs
//    @Test
//    fun `reuse`() {
//        // add record via api
//        // get all records via api
//        // check that count = 1
//        // several test runs should be fine
//
//        // then add reuse flag - run tests two times and the second time it should fail -
//        // because container is not stopping between tests
//    }
}