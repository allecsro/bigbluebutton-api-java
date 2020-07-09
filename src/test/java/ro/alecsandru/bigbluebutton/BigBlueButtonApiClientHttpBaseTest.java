package ro.alecsandru.bigbluebutton;

import org.apache.http.NameValuePair;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigBlueButtonApiClientHttpBaseTest {
	private static final String A_MEETING_ID = "a_mock_test_meeting";
	private static final String A_USER_ID = "a_user_id";

	private BigBlueButtonApiClientHttpBase client;

	@BeforeEach
	public void setUp() throws Exception {
		final String secret = "xyz";
		final String endpoint = "https://acme.com/bigbluebutton/api";
		final HttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager();

		client = new BigBlueButtonApiClientHttpBase(secret, endpoint, connectionManager);
	}

	@Test
	public void testGetRequestParameters_createMeeting() {
		CreateMeetingRequest createMeetingRequest = CreateMeetingRequest.builder()
			.name("Acme Test")
			.meetingID("acme_test")
			.build();

		List<NameValuePair> params = client.getRequestParameters(createMeetingRequest);
		assertEquals(2, params.size());
		assertEquals("name", params.get(0).getName());
		assertEquals("Acme Test", params.get(0).getValue());
		assertEquals("acme_test", params.get(1).getValue());
		assertEquals("meetingID", params.get(1).getName());
	}

	@Test
	void testGetRequestParameters_joinMeeting() {
		String customCssUrl = "https://cdn.acme.com/static/custom.css";


		JoinMeetingRequest joinMeetingRequest = JoinMeetingRequest.builder()
			.fullName("John Doe")
			.userID(A_USER_ID)
			.meetingID(A_MEETING_ID)
			.guest(false)
			.redirect(false)
			.password("1234")
			.customCssStyleUrl(customCssUrl)
			.build();

		List<NameValuePair> params = client.getRequestParameters(joinMeetingRequest);
		System.out.println(params);

		Assertions.assertTrue(params.contains(new BasicNameValuePair("fullName", "John Doe")));
		Assertions.assertTrue(params.contains(new BasicNameValuePair("userID", A_USER_ID)));
		Assertions.assertTrue(params.contains(new BasicNameValuePair("meetingID", A_MEETING_ID)));
		Assertions.assertTrue(params.contains(new BasicNameValuePair("guest", "false")));
		Assertions.assertTrue(params.contains(new BasicNameValuePair("redirect", "false")));
		Assertions.assertTrue(params.contains(new BasicNameValuePair("password", "1234")));
		Assertions.assertTrue(params.contains(new BasicNameValuePair("userdata-bbb_custom_style_url", customCssUrl)));
	}
}