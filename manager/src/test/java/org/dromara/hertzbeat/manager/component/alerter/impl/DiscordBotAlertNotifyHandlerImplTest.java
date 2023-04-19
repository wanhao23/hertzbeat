package org.dromara.hertzbeat.manager.component.alerter.impl;

import org.dromara.hertzbeat.common.entity.alerter.Alert;
import org.dromara.hertzbeat.common.entity.manager.NoticeReceiver;
import org.dromara.hertzbeat.common.constants.CommonConstants;
import org.dromara.hertzbeat.manager.AbstractSpringIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Test case for {@link DiscordBotAlertNotifyHandlerImpl}
 *
 *
 * Created by gcdd1993 on 2023/1/19
 */
@Slf4j
class DiscordBotAlertNotifyHandlerImplTest extends AbstractSpringIntegrationTest {

    @Resource
    private DiscordBotAlertNotifyHandlerImpl discordBotAlertNotifyHandler;

    @Test
    void send() {
        var discordChannelId = System.getenv("DISCORD_CHANNEL_ID");
        var discordBotToken = System.getenv("DISCORD_BOT_TOKEN");
        if (!StringUtils.hasText(discordChannelId) || !StringUtils.hasText(discordBotToken)) {
            log.warn("Please provide environment variables DISCORD_CHANNEL_ID, DISCORD_BOT_TOKEN");
            return;
        }
        var receiver = new NoticeReceiver();
        receiver.setId(1L);
        receiver.setName("Mock 告警");
        receiver.setDiscordChannelId(discordChannelId);
        receiver.setDiscordBotToken(discordBotToken);
        var alert = new Alert();
        alert.setId(1L);
        alert.setTarget("Mock Target");
        var map = Map.of(
                CommonConstants.TAG_MONITOR_ID, "Mock monitor id",
                CommonConstants.TAG_MONITOR_NAME, "Mock monitor name"
        );
        alert.setTags(map);
        alert.setContent("mock content");
        alert.setPriority((byte) 0);
        alert.setLastTriggerTime(System.currentTimeMillis());

        discordBotAlertNotifyHandler.send(receiver, alert);
    }
}