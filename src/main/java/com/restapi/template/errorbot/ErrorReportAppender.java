package com.restapi.template.errorbot;

import static com.restapi.template.errorbot.util.JsonUtils.toPrettyJson;
import static com.restapi.template.errorbot.util.MdcUtil.AGENT_DETAIL_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.BODY_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.HEADER_MAP_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.PARAMETER_MAP_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.REQUEST_URI_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.getFromMdc;
import static org.apache.commons.text.StringEscapeUtils.unescapeJava;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.restapi.template.errorbot.config.LogConfig;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackAttachment;
import net.gpedro.integrations.slack.SlackField;
import net.gpedro.integrations.slack.SlackMessage;

/**
 * Logging Event를 감지하여 설정한 레벨이상의 로그를 알림.
 *
 * @author always0ne
 * @version 1.0
 */
@RequiredArgsConstructor
public class ErrorReportAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

  private final LogConfig logConfig;
  private final ErrorLogsRepository errorLogsRepository;

  /**
   * 이벤트를 감지했을 때 설정한 레벨의 이상인지 확인한 후, <br>
   * DB에 저장, Slack채널에 알림 옵션에 맞게 동작한다.
   *
   * @param eventObject 로깅 이벤트
   */
  @Override
  protected void append(ILoggingEvent eventObject) {
    if (eventObject.getLevel().isGreaterOrEqual(logConfig.getLevel())) {
      ErrorLogs errorLog = new ErrorLogs(
          eventObject,
          logConfig.getServerName(),
          getFromMdc(REQUEST_URI_MDC),
          getFromMdc(PARAMETER_MAP_MDC),
          getFromMdc(HEADER_MAP_MDC),
          getFromMdc(BODY_MDC),
          getFromMdc(AGENT_DETAIL_MDC)
      );
      if (logConfig.getDatabase().isEnabled()) {
        errorLogsRepository.save(errorLog);
      }
      if (logConfig.getSlack().isEnabled()) {
        sendSlackMessage(errorLog);
      }
    }
  }

  /**
   * Slack 메시지 제작 후 설정된 채널로 알림메시지를 전송한다.<br>
   * - 전달되는 Data <br>
   * Error Message, Stack Trace, URL, Parameter, Header, Body,
   * UserInfo, User Agent, Time, Server Name, Server OS, Server Host Name
   *
   * @param errorLog 에러 정보
   */
  private void sendSlackMessage(ErrorLogs errorLog) {
    errorLog.markAsAlert();

    SlackMessage slackMessage = new SlackMessage("");
    slackMessage.setChannel("#" + logConfig.getSlack().getChannel());
    slackMessage.setUsername(logConfig.getSlack().getUserName());
    slackMessage.setIcon(":exclamation:");

    SlackAttachment slackAttachment = new SlackAttachment();
    slackAttachment.setFallback("Api 서버 에러발생!! 확인요망");
    slackAttachment.setColor("danger");
    if (logConfig.getDatabase().isEnabled()) {
      slackAttachment
          .setTitle(errorLog.getErrorInfo().getMessage() + "[" + errorLog.getId() + "] ");
    } else {
      slackAttachment.setTitle(errorLog.getErrorInfo().getMessage());
    }
    slackAttachment.setText(errorLog.getErrorInfo().getTrace());

    List<SlackField> fields = new ArrayList<>();

    fields.add(generateSlackField(
        "Request URL",
        errorLog.getRequestInfo().getPath(),
        true));

    fields.add(generateSlackField(
        "Request Parameter",
        toPrettyJson(errorLog.getRequestInfo().getParameterMap()),
        true));

    fields.add(generateSlackField(
        "Request Header",
        toPrettyJson(errorLog.getRequestInfo().getHeaderMap()),
        false));

    fields.add(generateSlackField(
        "Request Body",
        unescapeJava(toPrettyJson(errorLog.getRequestInfo().getBody())),
        false));

    fields.add(generateSlackField(
        "User Info",
        errorLog.getUserInfo(),
        true));

    fields.add(generateSlackField(
        "User Agent",
        toPrettyJson(errorLog.getRequestInfo().getAgentDetail()),
        true));

    fields.add(generateSlackField(
        "Time",
        errorLog.getErrorDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
        true));

    fields.add(generateSlackField(
        "Server Name",
        errorLog.getSystemInfo().getServerName(),
        true));

    fields.add(generateSlackField(
        "Server System OS",
        errorLog.getSystemInfo().getSystem(),
        true));

    fields.add(generateSlackField(
        "Server Host Name",
        errorLog.getSystemInfo().getHostName(),
        true));

    slackAttachment.setFields(fields);
    slackMessage.setAttachments(Collections.singletonList(slackAttachment));

    new SlackApi(logConfig.getSlack().getWebHookUrl()).call(slackMessage);
  }

  /**
   * Slack Field 생성자 역할을 하는 메소드.
   *
   * @param title   필드 제목
   * @param value   필드 값
   * @param shorten 필드의 가로폭이 절반으로 좁혀지는지 여부
   * @return SlackField
   */
  private SlackField generateSlackField(String title, String value, Boolean shorten) {
    SlackField slackField = new SlackField();
    slackField.setTitle(title);
    slackField.setValue(value);
    slackField.setShorten(shorten);
    return slackField;
  }
}