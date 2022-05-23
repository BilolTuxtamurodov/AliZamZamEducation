package com.company.AliZamZamEducation.controllerRu;

import com.company.AliZamZamEducation.MyTelegramBot;
import com.company.AliZamZamEducation.controller.MainController;
import com.company.AliZamZamEducation.dto.User_Time;
import com.company.AliZamZamEducation.entity.ProfileEntity;
import com.company.AliZamZamEducation.enums.AdminStatus;
import com.company.AliZamZamEducation.enums.ProfileStatus;
import com.company.AliZamZamEducation.maps.Maps;
import com.company.AliZamZamEducation.repository.ProfileRepository;
import com.company.AliZamZamEducation.utils.InlineButtonUtil;
import com.company.AliZamZamEducation.utils.KeyboardButtonUtil;
import com.company.AliZamZamEducation.utils.KeyboardButtonUtilRu;
import com.company.AliZamZamEducation.words.Words;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MainControllerRu {
    @Autowired
    private MyTelegramBot myTelegramBot;
    @Autowired
    private BolimMenuControllerRu bolimMenuControllerRu;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MainController mainController;
    private String news;
    public void handleTextRu(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        if (text.equals("\uD83C\uDDF7\uD83C\uDDFA Русский")) {
            if (user.getId().equals(Long.valueOf(Maps.Man))) {
                sendMessage.setText("Привет админ");
                sendMessage.setChatId(Maps.Man);
                sendMessage.setReplyMarkup(KeyboardButtonUtilRu.adminMenu());
                myTelegramBot.send(sendMessage);
            } else {
                String builder = "Добро пожаловать ! <b>" + user.getFirstName() + "</b>\n";
                sendMessage.setText(builder);
                sendMessage.setParseMode("HTML");
                sendMessage.setReplyMarkup(KeyboardButtonUtilRu.asosiyMenu());
                messageToAdminRu(user);
                myTelegramBot.send(sendMessage);
            }
        } else if (text.equals("✖️ Отмена")){
            sendMessage.setText("Главное меню");
            sendMessage.setChatId(Maps.Man);
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.adminMenu());
            news = null;
            Maps.ADMIN_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83E\uDD35\uD83E\uDD35\uD83C\uDFFB\u200D♀️  Пользователи Exel")){
            List<ProfileEntity> user_dtos = profileRepository.findAll();
            Words.writeToExel(user_dtos);
            InputFile inputFile = new InputFile(new File("univer.xlsx"));
            sendMessage.setText("Exel File");
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(Maps.Man);
            sendDocument.setCaption("Информация о пользователе");
            sendDocument.setDocument(inputFile);
            myTelegramBot.send(sendDocument);
        }
        else if (text.equals("\uD83D\uDE42 Отправить без медиа")){
            SendMessage toAdmin = new SendMessage();
            toAdmin.setChatId(Maps.Man);
            toAdmin.setText("Сообщение отправлено");
            toAdmin.setReplyMarkup(KeyboardButtonUtilRu.adminMenu());
            Maps.ADMIN_STATUS_MAP.remove(user.getId());
            for (User user1 : Maps.userMap.values()){
                sendMessage.setText(news);
                sendMessage.setChatId(user1.getId().toString());
                myTelegramBot.send(sendMessage);
            }
            myTelegramBot.send(toAdmin);
        } else if (text.equals("\uD83D\uDCF0 Обмен новостями")) {
            sendMessage.setText("Отправить сообщение");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            Maps.ADMIN_STATUS_MAP.put(user.getId(), AdminStatus.SEND_NEWS);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83E\uDDD1\u200D\uD83D\uDCBB Посмотреть количество пользователей")) {
            String s = String.valueOf(Maps.userMap.size());
            sendMessage.setText("Количество пользователей: " + s);
            sendMessage.setChatId(Maps.Man);
            Maps.ADMIN_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (Maps.ADMIN_STATUS_MAP.containsKey(user.getId())) {
            if (Maps.ADMIN_STATUS_MAP.get(user.getId()) == AdminStatus.SEND_NEWS) {
                SendMessage toAdmin = new SendMessage();
                toAdmin.setText("Загрузить сообщение видео или изображение");
                toAdmin.setChatId(Maps.Man);
                news = text;
                toAdmin.setReplyMarkup(KeyboardButtonUtilRu.orqaga_admin());
                myTelegramBot.send(toAdmin);
            }
        } else if (text.equals("❌ Отмена")) {
            sendMessage.setText("Выберите один из курсов!");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.kursMenu());
            Maps.USER_STATUS_MAP.remove(user.getId());
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            userDto.setLevel(null);
            userDto.setKurs(null);
            userDto.setBolim(null);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⬅️ Назад")) {
            sendMessage.setText("Курсы английского языка");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.englishMenu());
            Maps.USER_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⏪ Назад")) {
            sendMessage.setText("Базовые курсы");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.kursMenu());
            Maps.USER_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⬅ Назад")) {
            sendMessage.setText("Главное меню");
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.asosiyMenu());
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            Maps.USER_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (Maps.USER_STATUS_MAP.containsKey(user.getId())) {
            switch (Maps.USER_STATUS_MAP.get(user.getId())) {
                case SEND_NAME : {
                    String str = checkNameRu(text);
                    if (str != null) {
                        sendMessage.setText("Введите вашу фамилию\n(Пример: Султанов)");
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
                        ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
                        userDto.setName(str);
                        Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_FAMILYA);
                        myTelegramBot.send(sendMessage);
                    } else {
                        sendMessage.setText("В вашем имени должны быть только буквы! \nИ это не должно быть слишком коротким");
                        myTelegramBot.send(sendMessage);
                    }
                    break;
                }
                case SEND_FAMILYA : {
                    String str = checkNameRu(text);
                    if (str != null) {
                        sendMessage.setText("Отправьте свой контакт !");
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        sendMessage.setReplyMarkup(KeyboardButtonUtilRu.contact());
                        ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
                        userDto.setSurname(str);
                        Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_CONTACT);
                        myTelegramBot.send(sendMessage);
                    } else {
                        sendMessage.setText("В вашей фамилии должны быть только буквы! \nИ это не должно быть слишком коротким");
                        myTelegramBot.send(sendMessage);
                    }
                    break;
                }
                case SEND_CONTACT : {
                    sendMessage.setText("Пожалуйста, нажмите кнопку, чтобы отправить свой номер");
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    sendMessage.setReplyMarkup(KeyboardButtonUtilRu.contact());
                    myTelegramBot.send(sendMessage);
                    break;
                }
                case KURS_MENU : {
                    /*sendMessage.setText("\uD83D\uDE0A");
                    sendMessage.setChatId(String.valueOf(message.getChatId()));*/
                    bolimMenuControllerRu.equal(user, message);
//                    myTelegramBot.send(sendMessage);
                    break;
                }
                case LEVEL_USER : {
                    if (text.contains("\uD83D") || text.contains("\uD83C")) {
                        ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
                        userDto.setLevel(text);
                        sendMessage.setText("Введите ваше имя\n\"(Пример: Билол)");
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        sendMessage.setReplyMarkup(KeyboardButtonUtilRu.orqaga());
                        Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
                        myTelegramBot.send(sendMessage);
                    }
                    break;
                }
            }
        } else if (text.equals("\uD83D\uDCDA Базовые курсы")) {
            sendMessage.setText("Базовые курсы");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.kursMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("/start")) {
            sendMessage.setText("Главное меню");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.asosiyMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDFE2 Наши адреса")) {
            String builder = "<a href=\"https://maps.google.com/maps?q=41.323760,69.226390&ll=41.323760,69.226390&z=16" + "\"" + ">\nНажмите здесь</a>";
            sendMessage.setText("Наш адрес: " + builder);
            sendMessage.setParseMode("HTML");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.asosiyMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("ℹ️ О нас")) {
            sendMessage.setText("В данный момент в этом разделе нет информации");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.asosiyMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDDF7\uD83C\uDDFA Курс русского языка")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Курс русского языка");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.rusTiliMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83C\uDF93 Курсы математики")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Если вы хотите узнать больше о курсе математики, отправьте нам опрос,\n" +
                    "Мы скоро с Вами свяжемся");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.sorovnoma());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("✍️ Курс живописи")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Если вы хотите узнать больше о курсе живописи, отправьте нам опрос,\n" +
                    "Мы скоро с Вами свяжемся");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.sorovnoma());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83C\uDFA8 Хаттотлик Курси")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Если вы хотите узнать больше о курсе каллиграфии, отправьте нам опрос,\n" +
                    "Мы скоро с Вами свяжемся");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.sorovnoma());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDDF8\uD83C\uDDE6 Курс арабского языка")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Курс арабского языка\nВы можете отправить нам опрос, выбрав одно из направлений\uD83D\uDE42");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.arabTiliMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDDEC\uD83C\uDDE7 Курс английского языка")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Курсы английского языка");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtilRu.englishMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⚙️ Настройки")) {
            sendMessage.setText("Выберите язык");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.menuLenguage());
            Maps.LANGUAGE_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else {
            sendMessage.setText("Такого приказа не существует \uD83E\uDD72");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            myTelegramBot.send(sendMessage);
        }
    }

    public void handleContactRu(Message message, User user) {
        Contact contact = message.getContact();
        System.out.println(contact.getFirstName() + " " + contact.getPhoneNumber());
        SendMessage toUser = new SendMessage();
        toUser.setChatId(String.valueOf(message.getChatId()));
        if (Maps.USER_TIME_MAP.containsKey(user)) {
            User_Time user_time = Maps.USER_TIME_MAP.get(user);
            LocalDateTime localDateTime = user_time.getCreated_time().plusDays(1);
            if (localDateTime.isAfter(LocalDateTime.now())) {
                toUser.setText("Ваш запрос обрабатывается Дождитесь звонка от Оператора \uD83D\uDE42");
            } else {
                toUser.setText("Ваш запрос принят!\uD83D\uDE0A\nМы скоро с Вами свяжемся!");
                message(user, contact);
            }
        } else {
            toUser.setText("Ваш запрос принят!\uD83D\uDE0A\nМы скоро с Вами свяжемся!");
            message(user, contact);
        }
        toUser.setReplyMarkup(KeyboardButtonUtilRu.asosiyMenu());
        myTelegramBot.send(toUser);
        Maps.CONTACT_MAP.put(contact.getUserId(), contact);
        Maps.USER_STATUS_MAP.remove(message.getContact().getUserId());
    }

    private void message(User user, Contact contact) {
        SendMessage toAdmin = new SendMessage();
        if (Maps.USER_LEVEL_LIST.containsKey(user.getId())) {
            User_Time user_time = new User_Time();
            user_time.setUser(user);
            user_time.setCreated_time(LocalDateTime.now());
            Maps.USER_TIME_MAP.put(user, user_time);
            ProfileEntity userDto = Maps.USER_LEVEL_LIST.get(user.getId());
            StringBuilder builder = new StringBuilder();
            builder.append("Ismi: ").append(userDto.getName()).append("\nFamilyasi: ").append(userDto.getSurname()).append("\nKursi: ").append(userDto.getKurs());
            if (userDto.getBolim() != null) {
                builder.append("\nBo'lim: ").append(userDto.getBolim());
            }
            if (userDto.getLevel() != null) {
                builder.append("\nLevel: ").append(userDto.getLevel());
            }
            builder.append("\nRaqami: ");
            if(contact.getPhoneNumber().length() == 12){
                builder.append("+");
            }
            builder.append(contact.getPhoneNumber());

            if (user.getUserName() != null) {
                builder.append("\nTelegram: @").append(user.getUserName());
            }

            if (Maps.COUNT % 2 == 0) {
                toAdmin.setChatId(Maps.Man);
            } else {
                toAdmin.setChatId(Maps.ADMIN);
            }
            Maps.COUNT++;
            toAdmin.setText(builder.toString());
            toAdmin.setReplyMarkup(InlineButtonUtil.unchecked());
            myTelegramBot.send(toAdmin);
            Maps.USER_CONTACT_MAP.put(user.getId(), contact);
        }
    }

    private void messageToAdminRu(User user) {
        if (!Maps.userMap.containsKey(user.getId())) {
            SendMessage adminMessage = new SendMessage();
            adminMessage.setChatId(Maps.Man);
            StringBuilder builder = new StringBuilder();
            builder.append("Name: ").append(user.getFirstName());
            if(user.getUserName() != null){
                builder.append("\nNikName:  @").append(user.getUserName());
            }
            builder.append(" joined bot.");
            adminMessage.setText(builder.toString());
            Maps.userMap.put(user.getId(), user);
            myTelegramBot.send(adminMessage);
        }
    }

    private static String checkNameRu(String text) {
        boolean t = false;
        if (text.length() < 3) {
            t = true;
        }
        for (char i = 0; i < text.length(); i++) {
            if (!Character.isAlphabetic(text.charAt(i))) {
                t = true;
                if (text.charAt(i) == '\'' && (i != text.length() - 1)) {
                    t = false;
                }
            }
        }
        if (!t) {
            return text;
        }
        return null;
    }

    public void handleVideoUpload(User user, Message message) {
        Video video = message.getVideo();
        System.out.println("Video Upload: " + video.getFileId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));
        sendMessage.setText("Сообщение было отправлено");
        sendMessage.setReplyMarkup(KeyboardButtonUtilRu.adminMenu());
        SendVideo sendVideo = new SendVideo();
        for (User user1 : Maps.userMap.values()) {
            sendVideo.setChatId(user1.getId().toString());
            sendVideo.setVideo(new InputFile(video.getFileId()));
            sendVideo.setCaption(news);
            myTelegramBot.send(sendVideo);
        }
        myTelegramBot.send(sendMessage);
        Maps.ADMIN_STATUS_MAP.remove(user.getId());
        news = null;
    }

    public void handlePhotoUpload(User user, Message message) {
        log_photo(user, message.getPhoto());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));
        sendMessage.setText("Сообщение было отправлено");
        sendMessage.setReplyMarkup(KeyboardButtonUtilRu.adminMenu());
        myTelegramBot.send(sendMessage);
        String newphoto = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();

        SendPhoto sendPhoto = new SendPhoto();
        for (User user1 : Maps.userMap.values()) {
            sendPhoto.setChatId(String.valueOf(user1.getId()));
            sendPhoto.setPhoto(new InputFile(newphoto));
            sendPhoto.setCaption(news);
            myTelegramBot.send(sendPhoto);
        }
        Maps.ADMIN_STATUS_MAP.remove(user.getId());
        news = null;
    }

    private void log_photo(User user, List<PhotoSize> photoSizeList){
        String str = String.format(LocalDateTime.now() + ",  userId: %d, firstName: %s, lastName: %s",
                user.getId(), user.getFirstName(), user.getLastName());
        System.out.println(str);
        for (PhotoSize photo : photoSizeList){
            System.out.println("FileID: " + photo.getFileId() + " Width: " + photo.getWidth() + " Height: " + photo.getHeight());
        }
    }
}
