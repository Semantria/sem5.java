package com.lexalytics.semantria;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class AuthTest {

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(TestUtil.MOCK_PORT);

	@Before
	public void beforeEach() {
		wireMockRule.resetAll();
		stubFor(get(urlEqualTo("/auth/sessions/good"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("{\"access_token\": \"good\"}")));

	}

	@After
	public void afterEach() {
	}

	@Test
	public void testSessionCreateDefault() throws Exception {
		String json = "{\"permissions\":[{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"1d3a9ff96c090e17c821706b0594a9b5\"},{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"58c247add7e031058633431103716794\"}],\"expiration\":\"2019-01-21T17:05:35.699Z\",\"authenticated\":\"2019-01-21T16:05:35.699Z\",\"access_token\":\"0120488b-7777-41a3-b23b-75422c7b8fac\",\"user_id\":\"973a5f0ccbc4ee3524ccf035d35b284b\",\"account_id\":\"1b06de18-a304-4fc0-a8f0-8407643ec320\",\"user_login\":\"ericw\",\"renewal_type\":\"AUTO\"}";
		stubFor(post(urlEqualTo("/auth/sessions/?expiration=default&expire_after_minutes=0"))
				.withRequestBody(equalToJson("{ \"login\": \"foo\", \"password\": \"bar\" }"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));

		List<String> args = Arrays.asList("auth", "sessions", "create", "--credentials", "foo:bar");
		String output = TestUtil.runNoAuth(args);
		JSONAssert.assertEquals(json, output, false);
	}

	@Test
	public void testSessionCreateForever() throws Exception {
		String json = "{\"permissions\":[{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"1d3a9ff96c090e17c821706b0594a9b5\"},{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"58c247add7e031058633431103716794\"}],\"expiration\":\"2019-01-21T17:05:35.699Z\",\"authenticated\":\"2019-01-21T16:05:35.699Z\",\"access_token\":\"0120488b-7777-41a3-b23b-75422c7b8fac\",\"user_id\":\"973a5f0ccbc4ee3524ccf035d35b284b\",\"account_id\":\"1b06de18-a304-4fc0-a8f0-8407643ec320\",\"user_login\":\"ericw\",\"renewal_type\":\"NONE\"}";
		stubFor(post(urlEqualTo("/auth/sessions/?expiration=forever&expire_after_minutes=0"))
				.withRequestBody(equalToJson("{ \"login\": \"foo\", \"password\": \"bar\" }"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));

		List<String> args = Arrays.asList("auth", "sessions", "create", "--expiration", "forever", "--credentials",
				"foo:bar");
		String output = TestUtil.runNoAuth(args);
		JSONAssert.assertEquals(json, output, false);
	}

	@Test
	public void testSessionCreateCustom() throws Exception {
		String json = "{\"permissions\":[{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"1d3a9ff96c090e17c821706b0594a9b5\"},{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"58c247add7e031058633431103716794\"}],\"expiration\":\"2019-01-21T17:05:35.699Z\",\"authenticated\":\"2019-01-21T16:05:35.699Z\",\"access_token\":\"0120488b-7777-41a3-b23b-75422c7b8fac\",\"user_id\":\"973a5f0ccbc4ee3524ccf035d35b284b\",\"account_id\":\"1b06de18-a304-4fc0-a8f0-8407643ec320\",\"user_login\":\"ericw\",\"renewal_type\":\"NONE\"}";
		stubFor(post(urlEqualTo("/auth/sessions/?expiration=custom&expire_after_minutes=10"))
				.withRequestBody(equalToJson("{ \"login\": \"foo\", \"password\": \"bar\" }"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));

		List<String> args = Arrays.asList("auth", "sessions", "create", "--expiration", "custom",
				"--expire-after-minutes", "10", "--credentials", "foo:bar");
		String output = TestUtil.runNoAuth(args);
		JSONAssert.assertEquals(json, output, false);
	}

	@Test
	public void testSessionDelete() throws Exception {
		stubFor(delete(urlEqualTo("/auth/sessions/xxyy")).withHeader("Authorization", equalTo("good"))
				.willReturn(aResponse().withStatus(200)));

		List<String> args = Arrays.asList("auth", "sessions", "xxyy", "delete");
		String output = TestUtil.run(args);
		assertEquals("", output.trim());
	}

	@Test
	public void testSessionGet() throws Exception {
		String json = "{\"permissions\":[{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"1d3a9ff96c090e17c821706b0594a9b5\"},{\"permission_type\":\"CONFIGURATION\",\"permission_id\":\"LEGACY\",\"config_id\":\"58c247add7e031058633431103716794\"}],\"expiration\":\"2019-01-21T17:05:35.699Z\",\"authenticated\":\"2019-01-21T16:05:35.699Z\",\"access_token\":\"0120488b-7777-41a3-b23b-75422c7b8fac\",\"user_id\":\"973a5f0ccbc4ee3524ccf035d35b284b\",\"account_id\":\"1b06de18-a304-4fc0-a8f0-8407643ec320\",\"user_login\":\"ericw\",\"renewal_type\":\"NONE\"}";
		stubFor(get(urlEqualTo("/auth/sessions/f3f58194-6ccc-4597-9420-a07d6c4b60d0"))
				.withHeader("Authorization", equalTo("good"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));

		List<String> args = Arrays.asList("auth", "sessions", "f3f58194-6ccc-4597-9420-a07d6c4b60d0");
		String output = TestUtil.run(args);
		JSONAssert.assertEquals(json, output, false);
	}

	@Test
	public void testAllSessionsGet() throws Exception {
		String json = "{\n" + "  \"live_session_count\" : 3,\n" + "  \"user_sessions\" : {\n"
				+ "    \"jporter\" : [ \"good\", \"da969785-5fdc-4565-82b1-2d227f5875ab\", \"329645ed-ef89-4326-ba7a-e717d4462207\" ]\n"
				+ "  }\n" + "}";
		stubFor(get(urlEqualTo("/auth/sessions/")).withHeader("Authorization", equalTo("good"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));
		List<String> args = Arrays.asList("auth", "sessions", "get", "-v");
		String output = TestUtil.run(args);
		JSONAssert.assertEquals(json, output, false);
	}

	@Test
	public void testUserSessionsClear() throws Exception {
		String json = "{\n" + "  \"live_session_count\" : 3,\n" + "  \"user_sessions\" : {\n"
				+ "    \"jporter\" : [ \"good\", \"da969785-5fdc-4565-82b1-2d227f5875ab\", \"329645ed-ef89-4326-ba7a-e717d4462207\" ]\n"
				+ "  }\n" + "}";
		stubFor(get(urlEqualTo("/auth/users/jporter/sessions/")).withHeader("Authorization", equalTo("good"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));
		stubFor(delete(urlEqualTo("/auth/sessions/da969785-5fdc-4565-82b1-2d227f5875ab"))
				.withHeader("Authorization", equalTo("good")).willReturn(aResponse().withStatus(200)));
		stubFor(delete(urlEqualTo("/auth/sessions/329645ed-ef89-4326-ba7a-e717d4462207"))
				.withHeader("Authorization", equalTo("good")).willReturn(aResponse().withStatus(200)));

		List<String> args = Arrays.asList("auth", "users", "jporter", "sessions", "clear");
		String output = TestUtil.run(args);
		assertEquals("", output.trim());
	}

	@Test
	public void testUserSessionsGet() throws Exception {
		String json = "{\n" + "  \"live_session_count\" : 3,\n" + "  \"user_sessions\" : {\n"
				+ "    \"jporter\" : [ \"da969785-5fdc-4565-82b1-2d227f5875ab\", \"329645ed-ef89-4326-ba7a-e717d4462207\", \"6d145c91-711d-46d5-8c72-ec5511b1d295\" ]\n"
				+ "  }\n" + "}";
		stubFor(get(urlEqualTo("/auth/users/jporter/sessions/")).withHeader("Authorization", equalTo("good"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));

		List<String> args = Arrays.asList("auth", "users", "jporter", "sessions");
		String output = TestUtil.run(args);
		JSONAssert.assertEquals(json, output, false);
	}

	@Test
	public void testAccountSessionsGet() throws Exception {
		String json = "{\n" + "  \"live_session_count\" : 3,\n" + "  \"user_sessions\" : {\n"
				+ "    \"jporter\" : [ \"da969785-5fdc-4565-82b1-2d227f5875ab\", \"329645ed-ef89-4326-ba7a-e717d4462207\", \"6d145c91-711d-46d5-8c72-ec5511b1d295\" ]\n"
				+ "  }\n" + "}";

		stubFor(get(urlEqualTo("/auth/accounts/account-1/sessions/")).withHeader("Authorization", equalTo("good"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(json)));
		List<String> args = Arrays.asList("auth", "accounts", "account-1", "sessions");
		String output = TestUtil.run(args);
		JSONAssert.assertEquals(json, output, false);
	}
}
