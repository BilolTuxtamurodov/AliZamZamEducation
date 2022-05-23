package com.company.AliZamZamEducation.controller;

import com.company.AliZamZamEducation.MyTelegramBot;
import com.company.AliZamZamEducation.controllerRu.MainControllerRu;
import com.company.AliZamZamEducation.enums.LanguageStatus;
import com.company.AliZamZamEducation.maps.Maps;
import com.company.AliZamZamEducation.utils.KeyboardButtonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
@Controller
public class LanguageController {
    @Autowired
    private MyTelegramBot myTelegramBot;
    @Autowired
    private MainController mainController;
    @Autowired
    private MainControllerRu mainControllerRu;

    public void handleText(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        switch (text) {
            case "/start" : {
                String builder = "🇺🇿 Assalamu Aleykum siz Ali_ZamZam o'quv markazining rasmiy botiga hush kelibsiz!\uD83D\uDE0A" +
                        "Bu yerdan o'zingiz istagan barcha ma'lumotga ega bo'lishingiz mumkin. Avval Tilni Tanlab Olaylik \uD83D\uDE0A" +
                        "\n\n\uD83C\uDDF7\uD83C\uDDFA Ассаламу Алейкум, добро пожаловать на официальный бот учебного центра Ali_ZamZam! \uD83D\uDE0A\n" +
                        "Здесь вы можете получить всю интересующую вас информацию.\n\n" +
                        "Давайте сначала выберем язык \uD83D\uDE0A";
                sendMessage.setText(builder);
                sendMessage.setReplyMarkup(KeyboardButtonUtil.menuLenguage());
                myTelegramBot.send(sendMessage);
                break;
            }
            case "\uD83C\uDDFA\uD83C\uDDFF O'zbekcha" : {

                Maps.LANGUAGE_STATUS_MAP.put(user.getId(), LanguageStatus.UZ);
                mainController.handleText(user, message);
                break;
            }
            case "\uD83C\uDDF7\uD83C\uDDFA Русский" : {
                Maps.LANGUAGE_STATUS_MAP.put(user.getId(), LanguageStatus.RU);
                mainControllerRu.handleTextRu(user, message);
                break;
            }
            default : {
                sendMessage.setText("\uD83C\uDDFA\uD83C\uDDFF Botni qaytadan ishga tushuring\n\uD83C\uDDF7\uD83C\uDDFA Перезапустите бота /start");
                myTelegramBot.send(sendMessage);
            }
        }
    }
}
