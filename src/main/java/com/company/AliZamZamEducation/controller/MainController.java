package com.company.AliZamZamEducation.controller;

import com.company.AliZamZamEducation.MyTelegramBot;
import com.company.AliZamZamEducation.dto.User_Time;
import com.company.AliZamZamEducation.entity.ProfileEntity;
import com.company.AliZamZamEducation.enums.AdminStatus;
import com.company.AliZamZamEducation.enums.ProfileStatus;
import com.company.AliZamZamEducation.mapper.ProfileUserIdMapper;
import com.company.AliZamZamEducation.maps.Maps;
import com.company.AliZamZamEducation.repository.ProfileRepository;
import com.company.AliZamZamEducation.utils.InlineButtonUtil;
import com.company.AliZamZamEducation.utils.KeyboardButtonUtil;
import com.company.AliZamZamEducation.words.Words;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MainController {
    private String news;
    @Autowired
    @Lazy
    private MyTelegramBot myTelegramBot;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BolimMenuController bolimMenuController;


    public void handleText(User user, Message message) {
        String text = message.getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        if (text.equals("\uD83C\uDDFA\uD83C\uDDFF O'zbekcha")) {
            if (user.getId().equals(Long.valueOf(Maps.Man))) {
                sendMessage.setText("Assalomalekom Admin");
                sendMessage.setChatId(Maps.Man);
                sendMessage.setReplyMarkup(KeyboardButtonUtil.adminMenu());
                myTelegramBot.send(sendMessage);
            } else {
                String builder = "Hush kelibsiz ! <b>" + user.getFirstName() + "</b>\n";
                sendMessage.setText(builder);
                sendMessage.setParseMode("HTML");
                sendMessage.setReplyMarkup(KeyboardButtonUtil.asosiyMenu());
                messageToAdmin(user);
                myTelegramBot.send(sendMessage);
            }
        } else if (text.equals("✖️ Bekor Qilish")) {
            sendMessage.setText("Asosiy Menu");
            sendMessage.setChatId(Maps.Man);
            sendMessage.setReplyMarkup(KeyboardButtonUtil.adminMenu());
            news = null;
            Maps.ADMIN_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83E\uDD35\uD83E\uDD35\uD83C\uDFFB\u200D♀️  Exel Users")) {
            List<ProfileEntity> user_dtos = profileRepository.findAll();
            Words.writeToExel(user_dtos);
            InputFile inputFile = new InputFile(new File("univer.xlsx"));
            sendMessage.setText("Exel File");
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(Maps.Man);
            sendDocument.setCaption("Foydalanuvchilar Malumoti");
            sendDocument.setDocument(inputFile);
            myTelegramBot.send(sendDocument);
        } else if (text.equals("\uD83D\uDE42 Mediasiz Jo'natish")) {
            SendMessage toAdmin = new SendMessage();
            toAdmin.setChatId(Maps.Man);
            toAdmin.setText("Xabar Yuborildi");
            toAdmin.setReplyMarkup(KeyboardButtonUtil.adminMenu());
            Maps.ADMIN_STATUS_MAP.remove(user.getId());
            for (ProfileUserIdMapper userChatId : profileRepository.getUserId()) {
                sendMessage.setText(news);
                sendMessage.setChatId(userChatId.getUser_id());
                myTelegramBot.send(sendMessage);
            }
            myTelegramBot.send(toAdmin);
        } else if (text.equals("\uD83D\uDCF0 Yangilik Ulashish")) {
            sendMessage.setText("Xabarni Jonating");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            Maps.ADMIN_STATUS_MAP.put(user.getId(), AdminStatus.SEND_NEWS);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83E\uDDD1\u200D\uD83D\uDCBB Foydalanuvchilar sonini Ko'rish")) {
            Integer s = profileRepository.getUsersCount();
            sendMessage.setText("Foydalanuvchilar soni: " + s);
            sendMessage.setChatId(Maps.Man);
            Maps.ADMIN_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (Maps.ADMIN_STATUS_MAP.containsKey(user.getId())) {
            if (Maps.ADMIN_STATUS_MAP.get(user.getId()) == AdminStatus.SEND_NEWS) {
                SendMessage toAdmin = new SendMessage();
                toAdmin.setText("Xabarga oid Videoni yoki Rasmni Jonating");
                toAdmin.setChatId(Maps.Man);
                toAdmin.setReplyMarkup(KeyboardButtonUtil.orqaga_admin());
                news = text;
                myTelegramBot.send(toAdmin);
            }
        } else if (text.equals("❌ Bekor Qilish")) {
            sendMessage.setText("Kurslardan Birini Tanlang!");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.kursMenu());
            Maps.USER_STATUS_MAP.remove(user.getId());
            ProfileEntity profileDTO = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDTO.setLevel(null);
            profileDTO.setKurs(null);
            profileDTO.setBolim(null);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⬅️ Orqaga")) {
            sendMessage.setText("Ingliz Tili Kurslari");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.englishMenu());
            Maps.USER_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⏪ Orqaga")) {
            sendMessage.setText("Asosiy Kurslar");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.kursMenu());
            Maps.USER_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⬅ Orqaga")) {
            sendMessage.setText("Asosiy Menu");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.asosiyMenu());
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            Maps.USER_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else if (Maps.USER_STATUS_MAP.containsKey(user.getId())) {
            switch (Maps.USER_STATUS_MAP.get(user.getId())) {
                case SEND_NAME : {
                    String str = checkName(text);
                    if (str != null) {
                        sendMessage.setText("Familiyangizni kiriting\n(Namuna: Sultanov)");
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
                        ProfileEntity profileDTO = Maps.USER_LEVEL_LIST.get(user.getId());
                        profileDTO.setName(str);
                        Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_FAMILYA);
                        myTelegramBot.send(sendMessage);
                    } else {
                        sendMessage.setText("Ismingizda faqat Harflar qatnashishi Kerak! \nVa Juda Qisqa Bo'lmasiligi Lozim");
                        myTelegramBot.send(sendMessage);
                    }
                    break;
                }
                case SEND_FAMILYA : {
                    String str = checkName(text);
                    if (str != null) {
                        sendMessage.setText("Kantaktingizni jo'nating !");
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        sendMessage.setReplyMarkup(KeyboardButtonUtil.contact());
                        ProfileEntity profileDTO = Maps.USER_LEVEL_LIST.get(user.getId());
                        profileDTO.setSurname(str);
                        Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_CONTACT);
                        myTelegramBot.send(sendMessage);
                    } else {
                        sendMessage.setText("Familyangizda faqat Harflar qatnashishi Kerak! \nVa Juda Qisqa Bo'lmasiligi Lozim");
                        myTelegramBot.send(sendMessage);
                    }
                    break;
                }
                case SEND_CONTACT : {
                    sendMessage.setText("Iltimos Raqamingizni Yuborish Uchun Tugmachani Bosing");
                    sendMessage.setChatId(String.valueOf(message.getChatId()));
                    sendMessage.setReplyMarkup(KeyboardButtonUtil.contact());
                    myTelegramBot.send(sendMessage);
                    break;
                }
                case KURS_MENU : {
                    bolimMenuController.equal(user, message);
                    break;
                }
                case LEVEL_USER : {
                    if (text.contains("\uD83D") || text.contains("\uD83C")) {
                        ProfileEntity profileDTO = Maps.USER_LEVEL_LIST.get(user.getId());
                        profileDTO.setLevel(text);
                        sendMessage.setText("Ismingizni Kiriting\n(Namuna: Bilol)");
                        sendMessage.setChatId(String.valueOf(message.getChatId()));
                        sendMessage.setReplyMarkup(KeyboardButtonUtil.orqaga());
                        Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.SEND_NAME);
                        myTelegramBot.send(sendMessage);
                    }
                    break;
                }
            }
        } else if (text.equals("\uD83D\uDCDA Asosiy Kurslar")) {
            sendMessage.setText("Asosiy Kurslar");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.kursMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("/start")) {
            sendMessage.setText("Asosiy Menu");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.asosiyMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDFE2 Bizning Manzillar")) {
            String builder = "<a href=\"https://maps.google.com/maps?q=41.323760,69.226390&ll=41.323760,69.226390&z=16" + "\"" + ">\nUSTIGA BOSING</a>";
            sendMessage.setText("Bizning manzil: " + builder);
            sendMessage.setParseMode("HTML");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.asosiyMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("ℹ️ Biz Haqimizda")) {
            sendMessage.setText("Bu Bo'limda Hozircha Malumotlar Yo'q");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.asosiyMenu());
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDDF7\uD83C\uDDFA Rus Tili Kursi")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Rus tili kursi \nYo'nalishlardan Birini Tanlanab Bizga So'rovnoma Yuborishingiz mumkin\uD83D\uDE42");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.rusTiliMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83C\uDF93 Matematika Kursi")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Matematika Kursi haqida ma'lumot olmoqchi bo'lsangiz Bizga sorovnoma jonating,\nBiz tez orada siz bilan bog'lanamiz\uD83D\uDE42");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.sorovnoma());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("✍️ Naqqoshlik Kursi")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Naqqoshlik Kursi haqida ma'lumot olmoqchi bo'lsangiz Bizga sorovnoma jonating,\nBiz tez orada siz bilan bog'lanamiz\uD83D\uDE42");
            sendMessage.setReplyMarkup(KeyboardButtonUtil.sorovnoma());
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83D\uDC68\uD83C\uDFFB\u200D\uD83C\uDFA8 Hattotlik")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Hattotlik Kursi haqida ma'lumot olmoqchi bo'lsangiz Bizga sorovnoma jonating,\nBiz tez orada siz bilan bog'lanamiz\uD83D\uDE42");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.sorovnoma());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDDF8\uD83C\uDDE6 Arab tili Kursi")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Arab Tili Kursi \nYo'nalishlardan Birini Tanlanab Bizga So'rovnoma Yuborishingiz mumkin\uD83D\uDE42");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.arabTiliMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("\uD83C\uDDEC\uD83C\uDDE7 Ingiliz Tili Kursi")) {
            ProfileEntity user_dto = new ProfileEntity();
            user_dto.setKurs(text);
            Maps.USER_LEVEL_LIST.put(user.getId(), user_dto);
            sendMessage.setText("Ingiliz TIli Kurslari");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.englishMenu());
            Maps.USER_STATUS_MAP.put(user.getId(), ProfileStatus.KURS_MENU);
            myTelegramBot.send(sendMessage);
        } else if (text.equals("⚙️ Sozlamalar")) {
            sendMessage.setText("Tilni Tanlang");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(KeyboardButtonUtil.menuLenguage());
            Maps.LANGUAGE_STATUS_MAP.remove(user.getId());
            myTelegramBot.send(sendMessage);
        } else {
            sendMessage.setText("Bunday Buyuruq mavjud emas!\uD83E\uDD72");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            myTelegramBot.send(sendMessage);
        }
    }

    public void handleContact(Message message, User user) {
        Contact contact = message.getContact();
        System.out.println(contact.getFirstName() + " " + contact.getPhoneNumber());
        SendMessage toUser = new SendMessage();
        toUser.setChatId(String.valueOf(message.getChatId()));
        if (Maps.USER_TIME_MAP.containsKey(user)) {
            User_Time user_time = Maps.USER_TIME_MAP.get(user);
            LocalDateTime localDateTime = user_time.getCreated_time().plusDays(1);
            if (localDateTime.isAfter(LocalDateTime.now())) {
                toUser.setText("Yuborgan so'rovingiz ko'rib chiqilmoqda iltimos Operator Qo'ng'irog'ini kuting \uD83D\uDE42");
            } else {
                toUser.setText("So'rovingiz qabul qilindi!\uD83D\uDE0A\nTez orada siz bilan aloqaga chiqamiz!");
                message(user, contact);
            }
        } else {
            toUser.setText("So'rovingiz qabul qilindi!\uD83D\uDE0A\nTez orada siz bilan aloqaga chiqamiz!");
            message(user, contact);
        }
        toUser.setReplyMarkup(KeyboardButtonUtil.asosiyMenu());
        myTelegramBot.send(toUser);
        Maps.CONTACT_MAP.put(contact.getUserId(), contact);
        Maps.USER_STATUS_MAP.remove(message.getContact().getUserId());
    }

    private  void message(User user, Contact contact) {
        SendMessage toAdmin = new SendMessage();
        if (Maps.USER_LEVEL_LIST.containsKey(user.getId())) {
            User_Time user_time = new User_Time();
            user_time.setUser(user);
            user_time.setCreated_time(LocalDateTime.now());
            Maps.USER_TIME_MAP.put(user, user_time);
            ProfileEntity profileDTO = Maps.USER_LEVEL_LIST.get(user.getId());
            profileDTO.setPhone(contact.getPhoneNumber());
            profileDTO.setCreatedDate(LocalDateTime.now());
            profileDTO.setUserId(user.getId().toString());
            StringBuilder builder = new StringBuilder();

            builder.append("Ismi: ").append(profileDTO.getName()).append("\nFamilyasi: ").append(profileDTO.getSurname()).append("\nKursi: ").append(profileDTO.getKurs());
            if (profileDTO.getBolim() != null) {
                builder.append("\nBo'lim: ").append(profileDTO.getBolim());
            }
            if (profileDTO.getLevel() != null) {
                builder.append("\nLevel: ").append(profileDTO.getLevel());
            }
            builder.append("\nRaqami: ");
            if (contact.getPhoneNumber().length() == 12) {
                builder.append("+");
            }
            builder.append(contact.getPhoneNumber());
            if (user.getUserName() != null) {
                builder.append("\nTelegram: @").append(user.getUserName());
            }

            if (Maps.COUNT % 2 != 0) {
                toAdmin.setChatId(Maps.Man);
                Maps.COUNT++;
            } else {
                toAdmin.setChatId(Maps.ADMIN);
                Maps.COUNT++;
            }
            profileRepository.save(profileDTO);
            toAdmin.setText(builder.toString());
            toAdmin.setReplyMarkup(InlineButtonUtil.unchecked());
            myTelegramBot.send(toAdmin);
            Maps.USER_CONTACT_MAP.put(user.getId(), contact);
        }
    }

    private  void messageToAdmin(User user) {
        if (!Maps.userMap.containsKey(user.getId())) {
            SendMessage adminMessage = new SendMessage();
            adminMessage.setChatId(Maps.Man);
            StringBuilder builder = new StringBuilder();
            builder.append("Name: ").append(user.getFirstName());
            if (user.getUserName() != null) {
                builder.append("\nNikName:  @").append(user.getUserName());
            }
            builder.append(" joined bot.");
            adminMessage.setText(builder.toString());
            Maps.userMap.put(user.getId(), user);
            myTelegramBot.send(adminMessage);
        }
    }

    private static String checkName(String text) {
        boolean t = text.length() < 3;
        for (char i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                t = true;
            }
        }
        if (!t) {
            return text;
        }
        return null;
    }

    public void handleCallBack(User user, Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        if (text.equals("ffalse")) {
            EditMessageText toAdmin = new EditMessageText();
            toAdmin.setText(message.getText());
            toAdmin.setChatId(user.getId().toString());
            toAdmin.setMessageId(message.getMessageId());
            toAdmin.setReplyMarkup(InlineButtonUtil.checked());
            myTelegramBot.send(toAdmin);

        } else if (text.equals("ttrue")) {
            EditMessageText toAdmin = new EditMessageText();
            toAdmin.setText(message.getText());
            toAdmin.setChatId(user.getId().toString());
            toAdmin.setMessageId(message.getMessageId());
            toAdmin.setReplyMarkup(InlineButtonUtil.unchecked());
            myTelegramBot.send(toAdmin);
        }
    }

    public void handleVideoUpload(User user, Message message) {
        Video video = message.getVideo();
        System.out.println("Video Upload: " + video.getFileId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));
        sendMessage.setText("Xabar yuborildi");
        sendMessage.setReplyMarkup(KeyboardButtonUtil.adminMenu());
        SendVideo sendVideo = new SendVideo();
        for (ProfileUserIdMapper user1 : profileRepository.getUserId()) {
            sendVideo.setChatId(user1.getUser_id());
            sendVideo.setVideo(new InputFile(video.getFileId()));
            sendVideo.setCaption(news);
            myTelegramBot.send(sendVideo);
        }
        Maps.ADMIN_STATUS_MAP.remove(user.getId());
        myTelegramBot.send(sendMessage);
        news = null;
    }

    public void handlePhotoUpload(User user, Message message) {
        log_photo(user, message.getPhoto());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(user.getId()));
        sendMessage.setText("Xabar jo'natildi");
        sendMessage.setReplyMarkup(KeyboardButtonUtil.adminMenu());
        myTelegramBot.send(sendMessage);
        String newphoto = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();

        SendPhoto sendPhoto = new SendPhoto();
        for (ProfileUserIdMapper user1 : profileRepository.getUserId()) {
            sendPhoto.setChatId(user1.getUser_id());
            sendPhoto.setPhoto(new InputFile(newphoto));
            sendPhoto.setCaption(news);
            myTelegramBot.send(sendPhoto);
        }
        news = null;
        Maps.ADMIN_STATUS_MAP.remove(user.getId());
    }

    private void log_photo(User user, List<PhotoSize> photoSizeList) {
        String str = String.format(LocalDateTime.now() + ",  userId: %d, firstName: %s, lastName: %s",
                user.getId(), user.getFirstName(), user.getLastName());
        System.out.println(str);
        for (PhotoSize photo : photoSizeList) {
            System.out.println("FileID: " + photo.getFileId() + " Width: " + photo.getWidth() + " Height: " + photo.getHeight());
        }
    }

}


