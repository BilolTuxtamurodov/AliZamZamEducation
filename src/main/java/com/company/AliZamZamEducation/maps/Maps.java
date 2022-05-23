package com.company.AliZamZamEducation.maps;

import com.company.AliZamZamEducation.dto.ProfileDTO;
import com.company.AliZamZamEducation.dto.User_Time;
import com.company.AliZamZamEducation.entity.ProfileEntity;
import com.company.AliZamZamEducation.enums.AdminStatus;
import com.company.AliZamZamEducation.enums.LanguageStatus;
import com.company.AliZamZamEducation.enums.ProfileStatus;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Maps {
    public static int COUNT = 1;
    public static final String Man = "1056262092";
    public static final String ADMIN = "1297901310";
    public static Map<Long, User> userMap = new HashMap<>();
    public static Map<Long, Contact> CONTACT_MAP = new HashMap<>();
    public static Map<User, User_Time> USER_TIME_MAP = new HashMap<>();
    public static Map<Long, Contact> USER_CONTACT_MAP = new HashMap<>();
    public static Map<Long, ProfileStatus> USER_STATUS_MAP = new HashMap<>();
    public static Map<Long, AdminStatus> ADMIN_STATUS_MAP = new HashMap<>();
    public static Map<Long, ProfileEntity> USER_LEVEL_LIST = new LinkedHashMap<>();
    public static Map<Long, LanguageStatus> LANGUAGE_STATUS_MAP = new HashMap<>();

}
