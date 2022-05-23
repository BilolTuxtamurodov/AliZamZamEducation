package com.company.AliZamZamEducation.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Collections;

public class InlineButtonUtil {

    public static InlineKeyboardButton button(String text, String callBack) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBack);
        return button;
    }

    public static List<InlineKeyboardButton> row(InlineKeyboardButton... buttons) {
        return new LinkedList<>(Arrays.asList(buttons));
    }

    public static InlineKeyboardMarkup keyboard(List<List<InlineKeyboardButton>> rows) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup unchecked(){
        InlineKeyboardButton button = InlineButtonUtil.button("Qo'ng'iroq Qilinmagan ❌", "ffalse");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);
        return InlineButtonUtil.keyboard(Collections.singletonList(row));
    }

    public static InlineKeyboardMarkup checked(){
        InlineKeyboardButton button = InlineButtonUtil.button("Qo'ng'iroq Qilindi ✅", "ttrue");
        List<InlineKeyboardButton> row = InlineButtonUtil.row(button);
        return InlineButtonUtil.keyboard(Collections.singletonList(row));
    }

}
