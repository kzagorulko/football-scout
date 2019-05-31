package ru.zagorulko.footballscout;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

class Settings {
    private String[] teamNames;
    private String chooseTeamTitle;

    private static final String APP_PREFERENCES = "fssettings";
    private static String LANGUAGE = "language";
    private static String SELECTED_TEAM = "selected_team";
    private static String RESEARCHED_TEAM = "researched_team";
    private static String ENERGY = "energy";
    private static SharedPreferences sharedPreferences;


    // getters

    static int getEnergy(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getInt(ENERGY, -1);
    }

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

    static String getProgressTitle(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ? "Прогресс ваших игроков:" :
                language.equals("EN") ? "Players progress:" : "";
    }

    static String getEnergyTitle(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return (language.equals("RU") ? "Энергия: " :
                language.equals("EN") ? "Energy: " : "") + getEnergy(context);
    }

    static String getProgressButtonTitle(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ? "Следующее межсезонье" :
                language.equals("EN") ? "Next off-season" : "";
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
        return language.equals("RU") ? new String[]{"Фамилия", "ПОЗ", "ВОЗ", "АТК", "ЗАЩ", "ФИЗ"} :
               language.equals("EN") ? new String[]{"Last Name", "POS", "AGE", "ATC", "DEF", "PHY"} :
                                       new String[]{""};
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

    static String[] getPositions(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return language.equals("RU") ? new String[]{"ЦЗ", "ЛЗ", "ПЗ", "ЦП", "АПЛ", "АПЦ", "АПП", "НП"} :
               language.equals("EN") ? new String[]{"LB", "CB", "RB", "CM", "LW", "AM", "RW", "CF"} :
                                       new String[]{""};
    }

    static String getName(Context context) {
        String language = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE).getString(LANGUAGE, "");
        assert language != null;
        return getFirstName(language) + " " + getLastName(language);
    }

    // setters

    static void setEnergy(Context context, int energy) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ENERGY, energy);
        editor.apply();
    }

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

    // private functions

    private static String getFirstName(String language) {
        String[] names;
        switch (language) {
            case "RU":
                names = new String[]{
                        "Авдей", "Аверкий", "Авксентий", "Агафон", "Александр", "Алексей", "Альберт", "Альвиан", "Анатолий",
                        "Андрей", "Антон", "Антонин", "Анфим", "Аристарх", "Аркадий", "Арсений", "Артём", "Артур", "Архипп",
                        "Афанасий", "Богдан", "Борис", "Вадим", "Валентин", "Валерий", "Валерьян", "Варлам", "Варфоломей",
                        "Василий", "Венедикт", "Вениамин", "Викентий", "Виктор", "Виссарион", "Виталий", "Владимир", "Владислав",
                        "Владлен", "Влас", "Всеволод", "Вячеслав", "Гавриил", "Галактион", "Геласий", "Геннадий", "Георгий",
                        "Герасим", "Герман", "Глеб", "Гордей", "Григорий", "Даниил", "Демид", "Демьян", "Денис", "Дмитрий",
                        "Добрыня", "Донат", "Дорофей", "Евгений", "Евграф", "Евдоким", "Евсей", "Евстафий", "Егор", "Емельян",
                        "Еремей", "Ермолай", "Ефим", "Ждан", "Зиновий", "Иакинф", "Иван", "Игнатий", "Игорь", "Илья",
                        "Иннокентий", "Ираклий", "Ириней", "Исидор", "Иулиан", "Ким", "Кирилл", "Климент", "Кондрат",
                        "Константин", "Корнилий", "Кузьма", "Куприян", "Лаврентий", "Лев", "Леонид", "Леонтий", "Лука", "Лукий",
                        "Лукьян", "Магистриан", "Макар", "Максим", "Марк", "Мартын", "Матвей", "Мелентий", "Мирон", "Мирослав",
                        "Митрофан", "Михаил", "Мстислав", "Назар", "Нестор", "Никанор", "Никита", "Никифор", "Николай", "Никон",
                        "Олег", "Онисим", "Павел", "Пантелеймон", "Парфений", "Пётр", "Платон", "Порфирий", "Потап", "Пров",
                        "Прокопий", "Протасий", "Прохор", "Разумник", "Родион", "Роман", "Ростислав", "Руслан", "Савва",
                        "Савелий", "Самуил", "Святополк", "Святослав", "Севастьян", "Семён", "Сергей", "Сильвестр", "Сильвестр",
                        "Созон", "Спиридон", "Станислав", "Степан", "Тарас", "Тимофей", "Тимур", "Тихон", "Тихон", "Трифон",
                        "Трофим", "Фаддей", "Фёдор", "Федосей", "Федот", "Феликс", "Феоктист", "Филат", "Филипп", "Фома",
                        "Харитон", "Христофор", "Эдуард", "Эраст", "Юлиан", "Юрий", "Юстин", "Яков", "Якун", "Ярослав"
                };
                break;
            case "EN":
                names = new String[]{"John"};
                break;
            default:
                names = new String[]{""};
                break;
        }

        return names[(int)(Math.random() * names.length)];
    }

    private static String getLastName(String language) {
        String[] lastNames;
        switch (language) {
            case "RU":
                lastNames = new String[]{
                        "Иванов", "Смирнов", "Кузнецов", "Попов", "Васильев", "Петров", "Соколов", "Михайлов", "Новиков",
                        "Федоров", "Морозов", "Волков", "Алексеев", "Лебедев", "Семенов", "Егоров", "Павлов", "Козлов",
                        "Степанов", "Николаев", "Орлов", "Андреев", "Макаров", "Никитин", "Захаров", "Зайцев", "Соловьев",
                        "Борисов", "Яковлев", "Григорьев", "Романов", "Воробьев", "Сергеев", "Кузьмин", "Фролов", "Александров",
                        "Дмитриев", "Королев", "Гусев", "Киселев", "Ильин", "Максимов", "Поляков", "Сорокин", "Виноградов",
                        "Ковалев", "Белов", "Медведев", "Антонов", "Тарасов", "Жуков", "Баранов", "Филиппов", "Комаров",
                        "Давыдов", "Беляев", "Герасимов", "Богданов", "Осипов", "Сидоров", "Матвеев", "Титов", "Марков",
                        "Миронов", "Крылов", "Куликов", "Карпов", "Власов", "Мельников", "Денисов", "Гаврилов", "Тихонов",
                        "Казаков", "Афанасьев", "Данилов", "Савельев", "Тимофеев", "Фомин", "Чернов", "Абрамов", "Мартынов",
                        "Ефимов", "Федотов", "Щербаков", "Назаров", "Калинин", "Исаев", "Чернышев", "Быков", "Маслов", "Родионов",
                        "Коновалов", "Лазарев", "Воронин", "Климов", "Филатов", "Пономарев", "Голубев", "Кудрявцев", "Прохоров",
                        "Наумов", "Потапов", "Журавлев", "Овчинников", "Трофимов", "Леонов", "Соболев", "Ермаков", "Колесников",
                        "Гончаров", "Емельянов", "Никифоров", "Грачев", "Котов", "Гришин", "Ефремов", "Архипов", "Громов",
                        "Кириллов", "Малышев", "Панов"
                };
                break;
            case "EN":
                lastNames = new String[]{"Smith"};
                break;
            default:
                lastNames = new String[]{""};
                break;
        }
        return lastNames[(int)(Math.random() * lastNames.length)];
    }
}
