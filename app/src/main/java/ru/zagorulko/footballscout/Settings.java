package ru.zagorulko.footballscout;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.preference.Preference;

import static android.content.Context.MODE_PRIVATE;

class Settings {
    private String[] teamNames;
    private String chooseTeamTitle;

    private static final String APP_PREFERENCES = "fssettings";
    private static String LANGUAGE = "language";
    private static String SELECTED_TEAM = "selected_team";
    private static String RESEARCHED_TEAM = "researched_team";
    private static SharedPreferences sharedPreferences;


    // getters

    static String getTeamByPosition(Context context, int position) {
        return getTeamNames(context)[position + (position < getSelectedTeam(context) ? 0 : 1)];
    }

    static String[] getTeamNames(Context context)
    {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ?
                new String[]{"Ахмат", "Анжи", "Арсенал", "ЦСКА", "Динамо", "Енисей", "Краснодар", "Крылья советов", "Локоматив",
                        "Оренбург", "Ростов", "Рубин", "Спартак", "Уфа", "Урал", "Зенит"
        } : language.equals("EN") ?
                new String[]{"Akhmat", "Anji", "Arsenal", "PFC CSKA", "Dynamo", "Enisey", "Krasnodar", "Kr. Sovetov", "Lokomotiv",
                        "Orenburg", "Rostov", "Rubin", "Spartak", "FC Ufa", "Ural", "Zenit"
        } : new String[]{""};
    }

    static int[] getTeamImages() {
        return new int[]{R.drawable.akhmat, R.drawable.anzhi, R.drawable.arsenal, R.drawable.cska, R.drawable.dinamo,
                R.drawable.enisey, R.drawable.krasnodar, R.drawable.krylia, R.drawable.lokomotiv, R.drawable.orenburg,
                R.drawable.rostov, R.drawable.rubin, R.drawable.spartak, R.drawable.ufa, R.drawable.ural, R.drawable.zenit
        };
    }

    static String[] getTeamActivityTitle(Context context) {
        /*
        In array:  0 - last name,
                   1 - position,
                   2 - age,
                   3 - attacking skills,
                   4 - defending skills,
                   5 - physical skill.
         */
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ?
                new String[]{"Фамилия", "ПОЗ", "ВОЗ", "АТК", "ЗАЩ", "ФИЗ"} :
               language.equals("EN") ?
                new String[]{"Last Name", "POS", "AGE", "ATC", "DEF", "PHY"} : new String[]{""};
    }

    static String getChooseTeamTitle(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ? "Выберите команду:" :
               language.equals("EN") ? "Choose a team:" : "";
    }

    static int getSelectedTeam(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getInt(SELECTED_TEAM, -1);
    }

    static String getLanguage(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
    }


    static String getPositionsThatRequireStrengthening(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ? "Позиции, требующие усиления" :
               language.equals("EN") ? "Positions that require strengthening" : "";
    }

    static String getTeamsForSearch(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ? "Команды для поиска" :
               language.equals("EN") ? "Teams for search" : "";
    }

    static String getResearchedTeam(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(RESEARCHED_TEAM, "");
    }

    // setters

    static void setResearchedTeam(Context context, String team) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(RESEARCHED_TEAM, team);
        editor.apply();
    }

    static void setLanguage(Context context, String language) {
        /*
        This is a settings file.
        Languages:
        RU - Russian
        EN - English
        DE - German
         */
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    static void setSelectedTeam(Context context, int selectedTeam) {
        /*
        Teams are numbered from 0 to 15.

        Num| English     | Russian
        ---+-------------+---------
        0  | Akhmat      | Ахмат
        1  | Anji        | Анжи
        2  | Arsenal     | Арсенал
        3  | PFC CSKA    | ЦСКА
        4  | Dynamo      | Динамо
        5  | Enisey      | Енисей
        6  | Krasnodar   | Краснодар
        7  | Kr. Sovetov | Крылья советов
        8  | Lokomotiv   | Локоматив
        9  | Orenburg    | Оренбург
        10 | Rostov      | Ростов
        11 | Rubin       | Рубин
        12 | Spartak     | Спартак
        13 | FC Ufa      | Уфа
        14 | Ural        | Урал
        15 | Zenit       | Зенит
        ---+-------------+---------
         */
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECTED_TEAM, selectedTeam);
        editor.apply();
    }
}
