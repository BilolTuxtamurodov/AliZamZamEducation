package com.company.AliZamZamEducation;

import com.company.AliZamZamEducation.config.BotConfig;
import com.company.AliZamZamEducation.controller.LanguageController;
import com.company.AliZamZamEducation.controller.MainController;
import com.company.AliZamZamEducation.controllerRu.MainControllerRu;
import com.company.AliZamZamEducation.maps.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    @Autowired
    @Lazy
    private MainController mainController;
    @Autowired
    @Lazy
    private MainControllerRu mainControllerRu;
    @Autowired
    @Lazy
    private LanguageController languageController;

    private final BotConfig botConfig;

    public MyTelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return this.botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return this.botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            User user = message.getFrom();
            if (message.hasText()) {
                log(user, message.getText());
                if (Maps.LANGUAGE_STATUS_MAP.containsKey(user.getId())) {
                    switch (Maps.LANGUAGE_STATUS_MAP.get(user.getId())) {
                        case UZ : {
                            mainController.handleText(user, message);
                            break;
                        }
                        case RU : {
                            mainControllerRu.handleTextRu(user, message);
                            break;
                        }
                    }
                } else {
                    languageController.handleText(user, message);
                }
            } else if (message.hasContact()) {
                if (Maps.LANGUAGE_STATUS_MAP.containsKey(user.getId())) {
                    switch (Maps.LANGUAGE_STATUS_MAP.get(user.getId())) {
                        case UZ : {
                            mainController.handleContact(message, user);
                            break;
                        }
                        case RU : {
                            mainControllerRu.handleContactRu(message, user);
                            break;
                        }
                    }
                }
            } else if (message.hasVideo()){
                if(user.getId().equals(Long.valueOf(Maps.Man))){
                    if (Maps.LANGUAGE_STATUS_MAP.containsKey(user.getId())) {
                        switch (Maps.LANGUAGE_STATUS_MAP.get(user.getId())){
                            case UZ : {
                                mainController.handleVideoUpload(user, message);
                                break;
                            }
                            case RU : {
                                mainControllerRu.handleVideoUpload(user, message);
                                break;
                            }
                        }
                    }
                }
            } else if (message.hasPhoto()){
                if(user.getId().equals(Long.valueOf(Maps.Man))){
                    if (Maps.LANGUAGE_STATUS_MAP.containsKey(user.getId())) {
                        switch (Maps.LANGUAGE_STATUS_MAP.get(user.getId())){
                            case UZ : {
                                mainController.handlePhotoUpload(user, message);
                                break;
                            }
                            case RU : {
                                mainControllerRu.handlePhotoUpload(user, message);
                                break;
                            }
                        }
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            Message message = update.getCallbackQuery().getMessage();
            User user = update.getCallbackQuery().getFrom();
            String text = update.getCallbackQuery().getData();
            mainController.handleCallBack(user, message, text);
            log(user, text);
        }
    }

    public void send(Object obj) {
        try {
            if (obj instanceof SendMessage) {
                execute((SendMessage) obj);
            } else if (obj instanceof SendPhoto) {
                execute((SendPhoto) obj);
            } else if (obj instanceof SendVideo) {
                execute((SendVideo) obj);
            } else if (obj instanceof SendLocation) {
                execute((SendLocation) obj);
            } else if (obj instanceof SendContact) {
                execute((SendContact) obj);
            } else if (obj instanceof EditMessageText) {
                execute((EditMessageText) obj);
            } else if (obj instanceof  SendDocument){
                execute((SendDocument) obj);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void log(User user, String text) {
        String str = String.format(LocalDateTime.now() + ",  userId: %d, firstName: %s, lastName: %s, text: %s",
                user.getId(), user.getFirstName(), user.getLastName(), text);
        System.out.println(str);
    }
}
