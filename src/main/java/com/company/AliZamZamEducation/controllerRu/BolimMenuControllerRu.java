package com.company.AliZamZamEducation.controllerRu;

import com.company.AliZamZamEducation.MyTelegramBot;
import com.company.AliZamZamEducation.entity.ProfileEntity;
import com.company.AliZamZamEducation.enums.ProfileStatus;
import com.company.AliZamZamEducation.maps.Maps;
import com.company.AliZamZamEducation.utils.KeyboardButtonUtilRu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
@Controller
public class BolimMenuControllerRu {
    @Autowired
    private MyTelegramBot myTelegramBot;
    public void equal(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        if (text.contains("курс английского языка")) {
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setBolim(text);
            sendMessage.setText("Если вы выберете свой уровень, вы можете отправить нам информацию, и мы обязательно свяжемся с вами\uD83D\uDE0A");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.darajaMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.LEVEL_USER);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDCD9 Чтение (Таджвид)")) {
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setBolim(text);
            sendMessage.setText("Введите ваше имя. \n(Пример: Билол)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83E\uDD35↔️\uD83D\uDC68\uD83C\uDFFC\u200D\uD83D\uDCBC Беседа")){
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setBolim(text);
            sendMessage.setText("Введите ваше имя. \n(Пример: Билол)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDDD2 Грамматика")){
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setBolim(text);
            sendMessage.setText("Введите ваше имя. \n(Пример: Билол)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("0️⃣ Начиная с нуля")){
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setBolim(text);
            sendMessage.setText("Введите ваше имя. \n(Пример: Билол)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDC6E\uD83C\uDFFB\u200D♂️↔️\uD83D\uDE4D\uD83C\uDFFB\u200D♂️ Беседа")){
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setBolim(text);
            sendMessage.setText("Введите ваше имя. \n(Пример: Билол)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDCC3✍\uD83C\uDFFB Отправить опрос")){
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setBolim(null);
            sendMessage.setText("Введите ваше имя.\n" +
                    "(Пример: Билол)");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
            myTelegramBot.send(sendMessage);
        } else {
            sendMessage.setText("Пожалуйста, выберите один из разделов!\uD83D\uDE42");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            myTelegramBot.send(sendMessage);
        }
    }
}
