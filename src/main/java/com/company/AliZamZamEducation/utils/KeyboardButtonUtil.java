package com.company.AliZamZamEducation.utils;

import java.util.List;
import java.util.Arrays;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;


public class KeyboardButtonUtil {

    public static KeyboardButton buttonEmoji(String text, String emoji) {
        String emojiText = EmojiParser.parseToUnicode(emoji + " " + text);
        return new KeyboardButton(emojiText);
    }

    public static ReplyKeyboardMarkup menuLenguage() {
        KeyboardButton uz = KeyboardButtonUtil.buttonEmoji("O'zbekcha", "\uD83C\uDDFA\uD83C\uDDFF");
        KeyboardButton ru = KeyboardButtonUtil.buttonEmoji("Русский", "\uD83C\uDDF7\uD83C\uDDFA");
        KeyboardRow row = new KeyboardRow();
        row.add(uz);
        row.add(ru);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(List.of(row));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup kursMenu() {
        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Orqaga", ":arrow_left:");
        KeyboardButton rus_tili = KeyboardButtonUtil.buttonEmoji("Rus Tili Kursi", "\uD83C\uDDF7\uD83C\uDDFA");
        KeyboardButton arab_tili = KeyboardButtonUtil.buttonEmoji("Arab tili Kursi", "\uD83C\uDDF8\uD83C\uDDE6");
        KeyboardButton ingliz_tili = KeyboardButtonUtil.buttonEmoji("Ingiliz Tili Kursi", "\uD83C\uDDEC\uD83C\uDDE7");
        KeyboardButton matimatika = KeyboardButtonUtil.buttonEmoji("Matematika Kursi", "\uD83D\uDC68\uD83C\uDFFB\u200D\uD83C\uDF93");
        KeyboardButton naqqoshlik = KeyboardButtonUtil.buttonEmoji("Naqqoshlik Kursi", "✍️");
        KeyboardButton xattotlik = KeyboardButtonUtil.buttonEmoji("Hattotlik", "\uD83D\uDC68\uD83C\uDFFB\u200D\uD83C\uDFA8");

        KeyboardRow orqa = new KeyboardRow();
        orqa.add(orqaga);

        KeyboardRow row = new KeyboardRow();
        row.add(arab_tili);
        row.add(rus_tili);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(ingliz_tili);
        row1.add(matimatika);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(naqqoshlik);
        row2.add(xattotlik);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row, row1, row2, orqa));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup asosiyMenu() {
        KeyboardButton kurslar = KeyboardButtonUtil.buttonEmoji("Asosiy Kurslar", "\uD83D\uDCDA");
        KeyboardButton manzil = KeyboardButtonUtil.buttonEmoji("Bizning Manzillar", "\uD83C\uDFE2");
        KeyboardButton Biz_haqimizda = KeyboardButtonUtil.buttonEmoji("Biz Haqimizda", "ℹ️");
        KeyboardButton til_sozlari = KeyboardButtonUtil.buttonEmoji("Sozlamalar", "⚙️");

        KeyboardRow row = new KeyboardRow();
        row.add(kurslar);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(manzil);
        row1.add(Biz_haqimizda);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(til_sozlari);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row, row1, row2));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup arabTiliMenu() {
        KeyboardButton tajvid = KeyboardButtonUtil.buttonEmoji("O'qish(Tajvid)", "\uD83D\uDCD9");
        KeyboardButton muloqot = KeyboardButtonUtil.buttonEmoji("So'zlashish", "\uD83E\uDD35↔️\uD83D\uDC68\uD83C\uDFFC\u200D\uD83D\uDCBC");
        KeyboardButton gramatika = KeyboardButtonUtil.buttonEmoji("Grammatika", "\uD83D\uDDD2");

        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Orqaga", "⏪");
        KeyboardRow row = new KeyboardRow();
        row.add(tajvid);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(muloqot);
        row1.add(gramatika);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(orqaga);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row, row1, row2));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup rusTiliMenu() {
        KeyboardButton boshidan = KeyboardButtonUtil.buttonEmoji("Noldan Boshlab", "0️⃣");
        KeyboardButton muloqot = KeyboardButtonUtil.buttonEmoji("So'zlashish", "\uD83D\uDC6E\uD83C\uDFFB\u200D♂️↔️\uD83D\uDE4D\uD83C\uDFFB\u200D♂️");

        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Orqaga", "⏪");
        KeyboardRow row = new KeyboardRow();
        row.add(boshidan);
        row.add(muloqot);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(orqaga);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row, row2));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup englishMenu() {
        KeyboardButton ielts = KeyboardButtonUtil.buttonEmoji("Ielts Ingliz tili Kursi", "\uD83D\uDCC3");
        KeyboardButton general = KeyboardButtonUtil.buttonEmoji("General Ingliz tili Kursi", "\uD83D\uDCD1");
        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Orqaga", "⏪");

        KeyboardRow row = new KeyboardRow();
        row.add(ielts);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(general);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(orqaga);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row, row1, row2));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup darajaMenu() {
        KeyboardButton beginner = KeyboardButtonUtil.buttonEmoji("Beginner", "\uD83D\uDEB2");
        KeyboardButton elementry = KeyboardButtonUtil.buttonEmoji("Elementary", "\uD83D\uDEF5");
        KeyboardButton pri_intermediate = KeyboardButtonUtil.buttonEmoji("Pri-intermediate", "\uD83C\uDFCD");
        KeyboardButton intermediate = KeyboardButtonUtil.buttonEmoji("Intermediate", "\uD83D\uDE98");
        KeyboardButton Upper_intermediate = KeyboardButtonUtil.buttonEmoji("Upper-intermediate", "\uD83D\uDE84");
        KeyboardButton advanced = KeyboardButtonUtil.buttonEmoji("Advanced", "\uD83D\uDE80");
        KeyboardButton nomalum = KeyboardButtonUtil.buttonEmoji("Endi Bilib olaman", "\uD83D\uDE0C");
        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Orqaga", "⬅️");

        KeyboardRow row = new KeyboardRow();
        row.add(beginner);
        row.add(elementry);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(pri_intermediate);
        row1.add(intermediate);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(Upper_intermediate);
        row2.add(advanced);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(nomalum);

        KeyboardRow row4 = new KeyboardRow();
        row3.add(orqaga);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row, row1, row2, row3, row4));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup contact() {
        KeyboardButton contact = KeyboardButtonUtil.buttonEmoji("Contact", "\uD83D\uDCF2");
        contact.setRequestContact(true);

        KeyboardRow row = new KeyboardRow();
        row.add(contact);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(List.of(row));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup sorovnoma() {
        KeyboardButton sorov = KeyboardButtonUtil.buttonEmoji("Sorovnoma Yuborish", "\uD83D\uDCC3✍\uD83C\uDFFB");
        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Bekor Qilish", "❌");
        KeyboardRow row = new KeyboardRow();
        row.add(sorov);
        row.add(orqaga);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(List.of(row));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup orqaga() {
        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Bekor Qilish", "❌");

        KeyboardRow row = new KeyboardRow();
        row.add(orqaga);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(List.of(row));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup adminMenu() {
        KeyboardButton tema = KeyboardButtonUtil.buttonEmoji("Yangilik Ulashish", "\uD83D\uDCF0");
        KeyboardButton users = KeyboardButtonUtil.buttonEmoji("Foydalanuvchilar sonini Ko'rish", "\uD83E\uDDD1\u200D\uD83D\uDCBB");
        KeyboardButton exel = KeyboardButtonUtil.buttonEmoji("Exel Users", "\uD83E\uDD35\uD83E\uDD35\uD83C\uDFFB\u200D♀️ ");
        KeyboardButton til_sozlari = KeyboardButtonUtil.buttonEmoji("Sozlamalar", "⚙️");

        KeyboardRow row = new KeyboardRow();
        row.add(tema);
        row.add(users);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(exel);
        row1.add(til_sozlari);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Arrays.asList(row, row1));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }


    public static ReplyKeyboardMarkup orqaga_admin(){
        KeyboardButton orqaga = KeyboardButtonUtil.buttonEmoji("Bekor Qilish", "✖️");
        KeyboardButton davomi = KeyboardButtonUtil.buttonEmoji("Mediasiz Jo'natish", "\uD83D\uDE42");

        KeyboardRow row = new KeyboardRow();
        row.add(orqaga);
        row.add(davomi);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(List.of(row));
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
