package com.stormpath.spring.config

import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import javax.servlet.http.HttpServletResponse

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.testng.Assert.assertFalse
import static org.testng.Assert.assertNotNull
import static org.testng.Assert.assertTrue

/**
 * @since 1.2.3
 */
@WebAppConfiguration
@ContextConfiguration(classes = [ChangePasswordTestAppConfig, TwoAppTenantStormpathTestConfiguration])
class ChangePasswordIT extends AbstractClientIT {

    private MockMvc mvc

    @Autowired
    WebApplicationContext context

    @BeforeMethod
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(StormpathMockMvcConfigurers.stormpath())
            .build()
    }

    @Test
    void testChange() {

        // create dummy email
        def guerillaEmail = getGuerrillaEmail()

        // create temp account with email
        createTempAccount("foo-account-deleteme-" + UUID.randomUUID(), guerillaEmail.email, "Changeme1")

        mvc
            .perform(post("/forgot")
            .param("email", guerillaEmail.email)
            .accept(MediaType.TEXT_HTML))
            .andExpect(status().is(HttpServletResponse.SC_MOVED_TEMPORARILY)) //302

        // retrieve email
        Document email = retrieveEmail(guerillaEmail)

        // extract sptoken
        Elements hrefs = email.getElementsByTag("a")
        // should only be one, but wanna be safe
        String href = null
        hrefs.each { element ->
            href = element.attributes().get("href")
        }
        assertNotNull href
        def sptoken = href.substring(href.indexOf("sptoken=") + "sptoken=".length())

        // change password test
        // default policy is: min=8, max=100, lowercase=1, uppercase=1, numeric=1
        [
            "aaa", // too short
            ("a" * 101), // too long
            "AAAAAAAA", // no lowercase
            "aaaaaaaa", // no uppercase
            "Aaaaaaaa", // no numbers

        ].each { password ->
            def result = mvc
                .perform(post("/change")
                .param("sptoken", sptoken)
                .param("password", password)
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().is(HttpServletResponse.SC_MOVED_TEMPORARILY))
                .andReturn()

            // we don't want to test for specific error strings as they may change
            assertTrue result.response.getHeader("location").contains("error=")
        }

        def result = mvc
            .perform(post("/change")
            .param("sptoken", sptoken)
            .param("password", "ChangeMe2") // good password
            .accept(MediaType.TEXT_HTML))
            .andExpect(status().is(HttpServletResponse.SC_MOVED_TEMPORARILY))
            .andReturn()

        assertFalse result.response.getHeader("location").contains("error=")
    }
}
