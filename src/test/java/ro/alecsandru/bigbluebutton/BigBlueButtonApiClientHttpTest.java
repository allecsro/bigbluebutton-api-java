package ro.alecsandru.bigbluebutton;

import lombok.AccessLevel;
import lombok.Getter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BigBlueButtonApiClientHttpTest extends BigBlueButtonBaseApiTest {
	private static final String A_MEETING_ID = "a_mock_test_meeting";
	private static final String A_USER_ID = "a_user_id";

	@Mock
	@Getter(AccessLevel.PACKAGE)
	private CloseableHttpClient httpClient;

	private BigBlueButtonApiClientHttp bigBlueButtonApiClient;

	@BeforeEach
	public void setUp() throws Exception {
		final String secret = "xyz_122";
		final String endpoint = "https://acme.com/bigbluebutton/api";

		bigBlueButtonApiClient = new BigBlueButtonApiClientHttp(secret, endpoint, httpClient);
	}

	@Test
	public void testCreateMeeting() {
		setResponse(
			"<response>\n" +
			"    <returncode>SUCCESS</returncode>\n" +
			"    <meetingID>"+A_MEETING_ID+"</meetingID>\n" +
			"    <internalMeetingID>091f263e4d5794c480d00332af2e681ea3ac6fe7-1590072910172</internalMeetingID>\n" +
			"    <parentMeetingID>bbb-none</parentMeetingID>\n" +
			"    <attendeePW>1234</attendeePW>\n" +
			"    <moderatorPW>abcd</moderatorPW>\n" +
			"    <createTime>1590072910172</createTime>\n" +
			"    <voiceBridge>99651</voiceBridge>\n" +
			"    <dialNumber>613-555-1234</dialNumber>\n" +
			"    <createDate>Thu May 21 15:01:16 UTC 2020</createDate>\n" +
			"    <hasUserJoined>false</hasUserJoined>\n" +
			"    <duration>0</duration>\n" +
			"    <hasBeenForciblyEnded>false</hasBeenForciblyEnded>\n" +
			"    <messageKey></messageKey>\n" +
			"    <message></message>\n" +
			"</response>"
		);

		Map<String, String> meta = new HashMap<>();
		meta.put("bbb-origin", "ACME");
		meta.put("bbb-origin-version", "1.0-SNAPSHOT");
		meta.put("bbb-origin-server-name", "acnme.com");

		CreateMeetingRequest createMeetingRequest = CreateMeetingRequest.builder()
			.name("Mock Test")
			.meetingID(A_MEETING_ID)
			.welcome("Welcome to the videochat of ACME")
			.record(false)
			.isBreakout(false)
			.autoStartRecording(false)
			.allowStartStopRecording(false)
			.copyright("Copyright © 2020 ACME inc")
			.bannerText("Hello World!")
			.lockSettingsDisablePrivateChat(true)
			.lockSettingsDisablePublicChat(true)
			.lockSettingsDisableNote(true)
			.guestPolicy(GuestPolicyType.ALWAYS_ACCEPT_AUTH)
			.bannerColor("#ff549a")
			.maxParticipants(20)
			.logo("https://cdn.acme.com/user/image/840bebc64")
			.attendeePW("1234")
			.moderatorPW("abcd")
			.logoutURL("https://www.acme.com/logout")
			.meta(meta)
			.build();

		CreateMeetingResponse createMeetingResponse = bigBlueButtonApiClient.createMeeting(createMeetingRequest);

		assertEquals("SUCCESS", createMeetingResponse.getReturncode());
		assertEquals(A_MEETING_ID, createMeetingResponse.getMeetingID());
	}

	@Test
	public void testGetMeetings_none() {
		setResponse(
			"<response>\n" +
			"    <returncode>SUCCESS</returncode>\n" +
			"    <meetings/>\n" +
			"    <messageKey>noMeetings</messageKey>\n" +
			"    <message>no meetings were found on this server</message>\n" +
			"</response>"
		);

		GetMeetingsResponse getMeetingsResponse = bigBlueButtonApiClient.getMeetings();
		assertEquals("SUCCESS", getMeetingsResponse.getReturncode());
	}

	@Test
	public void testGetMeetings_noparticipants() {
		setResponse(
			"<response>\n" +
			"    <returncode>SUCCESS</returncode>\n" +
			"    <meetings>\n" +
			"        <meeting>\n" +
			"            <meetingName>ACME Test</meetingName>\n" +
			"            <meetingID>acme_test</meetingID>\n" +
			"            <internalMeetingID>23b6d60f1802301a0f6af44fb7d573dbae861ee1-1590146963416</internalMeetingID>\n" +
			"            <createTime>1590146963416</createTime>\n" +
			"            <createDate>Fri May 22 11:29:23 UTC 2020</createDate>\n" +
			"            <voiceBridge>06377</voiceBridge>\n" +
			"            <dialNumber>613-555-1234</dialNumber>\n" +
			"            <attendeePW>84a15d8</attendeePW>\n" +
			"            <moderatorPW>c86d2c1</moderatorPW>\n" +
			"            <running>false</running>\n" +
			"            <duration>0</duration>\n" +
			"            <hasUserJoined>true</hasUserJoined>\n" +
			"            <recording>false</recording>\n" +
			"            <hasBeenForciblyEnded>false</hasBeenForciblyEnded>\n" +
			"            <startTime>1590146963494</startTime>\n" +
			"            <endTime>1590147528510</endTime>\n" +
			"            <participantCount>0</participantCount>\n" +
			"            <listenerCount>0</listenerCount>\n" +
			"            <voiceParticipantCount>0</voiceParticipantCount>\n" +
			"            <videoCount>0</videoCount>\n" +
			"            <maxUsers>20</maxUsers>\n" +
			"            <moderatorCount>0</moderatorCount>\n" +
			"            <attendees></attendees>\n" +
			"            <metadata></metadata>\n" +
			"            <isBreakout>false</isBreakout>\n" +
			"        </meeting>\n" +
			"    </meetings>\n" +
			"</response>"
		);

		GetMeetingsResponse getMeetingsResponse = bigBlueButtonApiClient.getMeetings();
		assertEquals("SUCCESS", getMeetingsResponse.getReturncode());
	}

	@Test
	public void testJoinMeeting() {
		setResponse(
			"<response>\n" +
			"    <returncode>SUCCESS</returncode>\n" +
			"    <messageKey>successfullyJoined</messageKey>\n" +
			"    <message>You have joined successfully.</message>\n" +
			"    <meeting_id>091f263e4d5794c480d00332af2e681ea3ac6fe7-1590072910172</meeting_id>\n" +
			"    <user_id>w_cdjhentvf4pz</user_id>\n" +
			"    <auth_token>5vtyvdc7uum1</auth_token>\n" +
			"    <session_token>62o7tllvvmd0dley</session_token>\n" +
			"    <guestStatus>ALLOW</guestStatus>\n" +
			"    <url>https://acme.com/html5client/join?sessionToken=62o7tllvvmd0dley</url>\n" +
			"</response>"
		);

		JoinMeetingRequest joinMeetingRequest = JoinMeetingRequest.builder()
			.fullName("John Doe")
			.userID(A_USER_ID)
			.meetingID(A_MEETING_ID)
			.guest(false)
			.redirect(false)
			.password("1234")
			.build();

		JoinMeetingResponse joinMeetingResponse = bigBlueButtonApiClient.joinMeeting(joinMeetingRequest);
		assertEquals("SUCCESS", joinMeetingResponse.getReturncode());
		assertEquals("62o7tllvvmd0dley", joinMeetingResponse.getSessionToken());
		assertEquals("https://acme.com/html5client/join?sessionToken=62o7tllvvmd0dley", joinMeetingResponse.getUrl());
	}

	@Test
	public void testIsMeetingRunning() {
		setResponse(
			"<response>\n" +
			"    <returncode>SUCCESS</returncode>\n" +
			"    <running>true</running>\n" +
			"</response>"
		);

		IsMeetingRunningRequest isMeetingRunningRequest = IsMeetingRunningRequest.builder()
			.meetingID(A_MEETING_ID)
			.build();

		IsMeetingRunningResponse isMeetingRunningResponse = bigBlueButtonApiClient.isMeetingRunning(isMeetingRunningRequest);
		assertEquals("SUCCESS", isMeetingRunningResponse.getReturncode());
		assertTrue(isMeetingRunningResponse.isRunning());
	}

	@Test
	public void testGetMeetingInfo() throws ParseException {
		setResponse(
			"<response>\n" +
			"    <returncode>SUCCESS</returncode>\n" +
			"    <meetingName>Mock Test</meetingName>\n" +
			"    <meetingID>"+A_MEETING_ID+"</meetingID>\n" +
			"    <internalMeetingID>091f263e4d5794c480d00332af2e681ea3ac6fe7-1590073276727</internalMeetingID>\n" +
			"    <createTime>1590073276727</createTime>\n" +
			"    <createDate>Thu May 21 15:01:16 UTC 2020</createDate>\n" +
			"    <voiceBridge>31189</voiceBridge>\n" +
			"    <dialNumber>613-555-1234</dialNumber>\n" +
			"    <attendeePW>1234</attendeePW>\n" +
			"    <moderatorPW>abcd</moderatorPW>\n" +
			"    <running>false</running>\n" +
			"    <duration>0</duration>\n" +
			"    <hasUserJoined>false</hasUserJoined>\n" +
			"    <recording>false</recording>\n" +
			"    <hasBeenForciblyEnded>false</hasBeenForciblyEnded>\n" +
			"    <startTime>1590073276828</startTime>\n" +
			"    <endTime>0</endTime>\n" +
			"    <participantCount>0</participantCount>\n" +
			"    <listenerCount>0</listenerCount>\n" +
			"    <voiceParticipantCount>0</voiceParticipantCount>\n" +
			"    <videoCount>0</videoCount>\n" +
			"    <maxUsers>20</maxUsers>\n" +
			"    <moderatorCount>0</moderatorCount>\n" +
			"    <attendees></attendees>\n" +
			"    <metadata></metadata>\n" +
			"    <isBreakout>false</isBreakout>\n" +
			"</response>"
		);

		GetMeetingInfoRequest getMeetingInfoRequest = GetMeetingInfoRequest.builder()
			.meetingID(A_MEETING_ID)
			.build();

		DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");

		GetMeetingInfoResponse getMeetingInfoResponse = bigBlueButtonApiClient.getMeetingInfo(getMeetingInfoRequest);
		assertEquals("SUCCESS", getMeetingInfoResponse.getReturncode());
		assertEquals(A_MEETING_ID, getMeetingInfoResponse.getMeetingID());
		assertEquals("Mock Test", getMeetingInfoResponse.getMeetingName());
		assertEquals(df.parse("Thu May 21 15:01:16 UTC 2020"), getMeetingInfoResponse.getCreateDate());
		assertEquals(1590073276828L, getMeetingInfoResponse.getStartTime());

	}

	@Test
	public void testEndMeeting() {
		setResponse(
			"<response>\n" +
			"    <returncode>SUCCESS</returncode>\n" +
			"    <messageKey>sentEndMeetingRequest</messageKey>\n" +
			"</response>"
		);

		EndMeetingRequest endMeetingRequest = EndMeetingRequest.builder()
			.meetingID(A_MEETING_ID)
			.password("bcd")
			.build();

		EndMeetingResponse endMeetingResponse = bigBlueButtonApiClient.endMeeting(endMeetingRequest);
		assertEquals("SUCCESS", endMeetingResponse.getReturncode());
	}
}