package com.company.AliZamZamEducation.controller;

import com.company.AliZamZamEducation.MyTelegramBot;
import com.company.AliZamZamEducation.entity.ProfileEntity;
import com.company.AliZamZamEducation.enums.ProfileStatus;
import com.company.AliZamZamEducation.maps.Maps;
import com.company.AliZamZamEducation.utils.KeyboardButtonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
@Controller
public class BolimMenuController {
    @Autowired
    private MyTelegramBot myTelegramBot;
    public void equal(User user, Message message) {

        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        if (text.contains("Ingliz tili Kursi")) {
            ProfileEntity profileDto = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDto.setBolim(text);
            sendMessage.setText("Darajangizni tanlab olsangiz Bizga malumot yubora olasiz va biz albatta siz bilan bo'g'lanamiz\uD83D\uDE0A");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.darajaMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.LEVEL_USER);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDCD9 O'qish(Tajvid)")) {
            ProfileEntity profileDto = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDto.setBolim(text);
            sendMessage.setText("Ismingizni Kiriting.\n(Namuna: Bilol)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83E\uDD35↔️\uD83D\uDC68\uD83C\uDFFC\u200D\uD83D\uDCBC So'zlashish")) {
            ProfileEntity profileDto = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDto.setBolim(text);
            sendMessage.setText("Ismingizni Kiriting.\n(Namuna: Bilol)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDDD2 Grammatika")) {
            ProfileEntity profileDto = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDto.setBolim(text);
            sendMessage.setText("Ismingizni Kiriting.\n(Namuna: Bilol)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("0️⃣ Noldan Boshlab")) {
            ProfileEntity profileDto = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDto.setBolim(text);
            sendMessage.setText("Ismingizni Kiriting.\n(Namuna: Bilol)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDC6E\uD83C\uDFFB\u200D♂️↔️\uD83D\uDE4D\uD83C\uDFFB\u200D♂️ So'zlashish")) {
            ProfileEntity profileDto = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDto.setBolim(text);
            sendMessage.setText("Ismingizni Kiriting.\n(Namuna: Bilol)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDCC3✍\uD83C\uDFFB Sorovnoma Yuborish")) {
            ProfileEntity profileDto = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDto.setBolim(null);
            sendMessage.setText("Ismingizni Kiriting.\n(Namuna: Bilol)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else {
            sendMessage.setText("Iltimos Bo'limlardan Birini tanlang!\uD83D\uDE42");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            myTelegramBot.send(sendMessage);
        }
    }
}