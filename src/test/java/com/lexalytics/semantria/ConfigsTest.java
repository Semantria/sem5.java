package com.lexalytics.semantria;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class ConfigsTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(TestUtil.MOCK_PORT);

	@Before
	public void beforeEach() {
		wireMockRule.resetAll();
	}

	@After
	public void afterEach() {
	}

	@Test
	public void testConfigurationGetAll() throws Exception {
		String json = "["
            + "{"
            + "\"callback_url\":\"http://groot.semantria.com:8880/groot/\","
            + "\"config_routes\": [],"
            + "\"deep_models\": [],"
            + "\"detect_language\":false,"
            + "\"features\":[],"
            + "\"id\":\"config-en-2\","
            + "\"is_autoresponse_enabled\":false,"
            + "\"language_id\":\"en\","
            + "\"language_name\":\"English\","
            + "\"name\":\"English Config With Callback\","
            + "\"one_sentence_mode\":false,"
            + "\"process_html\":false,"
            + "\"modification_date\":\"2018-06-25T12:38:14.484Z\","
            + "\"tags\": [],"
            + "\"version\":\"v2\""
            + "},{"
            + "\"detect_language\":false,"
            + "\"features\":[],"
            + "\"id\":\"config-en-1\","
            + "\"is_autoresponse_enabled\":false,"
            + "\"language_id\":\"en\","
            + "\"language_name\":\"English\","
            + "\"name\":\"Food Pack\","
            + "\"one_sentence_mode\":false,"
            + "\"process_html\":false,"
            + "\"modification_date\":\"2018-06-18T17:36:28.111Z\","
            + "\"tags\": []"
            + "}"
            + "]";
		stubFor(get(urlEqualTo("/auth/sessions/good"))
				.willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("{\"access_token\": \"good\"}")));
		stubFor(get(urlMatching("/configs/\\?group_id=?"))
				.withHeader("Authorization", equalTo("good"))
				.willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(json)));

		List<String> args = Arrays.asList("get-configs", "-v", "--access-token", "good");
        String output = TestUtil.runNoAuth(args);
		JSONAssert.assertEquals(json, output, false);
	}
}
