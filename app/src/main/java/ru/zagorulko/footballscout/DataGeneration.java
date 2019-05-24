package ru.zagorulko.footballscout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
// import android.support.v5.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class DataGeneration extends AppCompatActivity {

    DBHelper dbHelper;
    public static String[] RU_TEAMS= {"Ахмат", "Анжи", "Арсенал Тула", "ЦСКА Москва", "Динамо Москва", "Енисей", "Краснодар",
            "Крылья советов", "Локоматив Москва", "Оренбург", "Ростов", "Рубин", "Спартак Москва", "Уфа", "Урал", "Зенит"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_generation);

        dbHelper = new DBHelper(this);

        generate(10, true);

        Intent intent = new Intent(this, ChooseTeamActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_data_generation);
        finish();
    }

    private void generate(int numbersOfPlayers, boolean differentAge) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        for(String team : Settings.getTeamNames(this)) {

            for(int i = 0; i < numbersOfPlayers; i++) {
                String name = getName();
                int age = differentAge ? (int) (Math.random() * 17 + 17) : 16;
                int assaultSkill = (int) (Math.random() * 20 + 1);
                int defendSkill = (int) (Math.random() * 20 + 1);
                int physicalSkill = (int) (Math.random() * 20 + 1);
                String position = getPosition(1);
                int potential = assaultSkill + physicalSkill + defendSkill +
                        (int) (Math.random() * (61 - (assaultSkill + physicalSkill + defendSkill)));

                dbHelper.insert(name, age, assaultSkill, defendSkill, physicalSkill, position, potential, team, database);
            }
        }


    }

    private String getName() {

        return getFirstName(1) + " " + getLastName(1);
    }

    private String getPosition(int language) {
        /*
        languages:
        1 - rus
         */
        String[] positions;
        if(language == 1) {
            positions = new String[]{"ЦЗ", "ЛЗ", "ПЗ", "ЦП", "АПЛ", "АПЦ", "АПП", "НП"};
        } else {
            positions = new String[]{"LB", "CB", "RB", "CM", "LW", "AM", "RW", "CF"};
        }
        return positions[(int)(Math.random() * positions.length)];
    }

    private String getFirstName(int language) {
        /*
        languages:
        1 - rus
         */
        String[] names;
        if (language == 1) {
            names = new String[]{
                    "Авдей", "Аверкий", "Авксентий", "Агафон", "Александр", "Алексей", "Альберт", "Альвиан", "Анатолий", "Андрей",
                    "Антон", "Антонин", "Анфим", "Аристарх", "Аркадий", "Арсений", "Артём", "Артур", "Архипп", "Афанасий",
                    "Богдан", "Борис",  "Вадим", "Валентин", "Валерий", "Валерьян", "Варлам", "Варфоломей", "Василий", "Венедикт",
                    "Вениамин", "Викентий", "Виктор", "Виссарион", "Виталий", "Владимир", "Владислав", "Владлен", "Влас",
                    "Всеволод", "Вячеслав", "Гавриил", "Галактион", "Геласий", "Геннадий", "Георгий", "Герасим", "Герман", "Глеб",
                    "Гордей", "Григорий", "Даниил", "Демид", "Демьян", "Денис", "Дмитрий", "Добрыня", "Донат", "Дорофей",
                    "Евгений", "Евграф", "Евдоким", "Евсей", "Евстафий", "Егор", "Емельян", "Еремей", "Ермолай", "Ефим", "Ждан",
                    "Зиновий", "Иакинф", "Иван", "Игнатий", "Игорь", "Илья", "Иннокентий", "Ираклий", "Ириней", "Исидор",
                    "Иулиан", "Ким", "Кирилл", "Климент", "Кондрат", "Константин", "Корнилий", "Кузьма", "Куприян", "Лаврентий",
                    "Лев", "Леонид", "Леонтий", "Лука", "Лукий", "Лукьян", "Магистриан", "Макар", "Максим", "Марк", "Мартын",
                    "Матвей", "Мелентий", "Мирон", "Мирослав", "Митрофан", "Михаил", "Мстислав", "Назар", "Нестор", "Никанор",
                    "Никита", "Никифор", "Николай", "Никон", "Олег", "Онисим", "Павел", "Пантелеймон", "Парфений", "Пётр",
                    "Платон", "Порфирий", "Потап", "Пров", "Прокопий", "Протасий", "Прохор", "Разумник", "Родион", "Роман",
                    "Ростислав", "Руслан", "Савва", "Савелий", "Самуил", "Святополк", "Святослав", "Севастьян", "Семён", "Сергей",
                    "Сильвестр", "Сильвестр", "Созон", "Спиридон", "Станислав", "Степан", "Тарас", "Тимофей", "Тимур", "Тихон",
                    "Тихон", "Трифон", "Трофим", "Фаддей", "Фёдор", "Федосей", "Федот", "Феликс", "Феоктист", "Филат", "Филипп",
                    "Фома", "Харитон", "Христофор", "Эдуард", "Эраст", "Юлиан", "Юрий", "Юстин", "Яков", "Якун", "Ярослав"
            };
        } else {
            names = new String[]{"John"};
        }

        return names[(int)(Math.random() * names.length)];
    }

    private String getLastName(int language) {
        /*
        languages:
        1 - rus
         */
        String[] lastNames;
        if(language == 1) {
            lastNames = new String[]{
                    "Иванов", "Смирнов", "Кузнецов", "Попов", "Васильев", "Петров", "Соколов", "Михайлов", "Новиков", "Федоров",
                    "Морозов", "Волков", "Алексеев", "Лебедев", "Семенов", "Егоров", "Павлов", "Козлов", "Степанов", "Николаев",
                    "Орлов", "Андреев", "Макаров", "Никитин", "Захаров", "Зайцев", "Соловьев", "Борисов", "Яковлев", "Григорьев",
                    "Романов", "Воробьев", "Сергеев", "Кузьмин", "Фролов", "Александров", "Дмитриев", "Королев", "Гусев",
                    "Киселев", "Ильин", "Максимов", "Поляков", "Сорокин", "Виноградов", "Ковалев", "Белов", "Медведев", "Антонов",
                    "Тарасов", "Жуков", "Баранов", "Филиппов", "Комаров", "Давыдов", "Беляев", "Герасимов", "Богданов", "Осипов",
                    "Сидоров", "Матвеев", "Титов", "Марков", "Миронов", "Крылов", "Куликов", "Карпов", "Власов", "Мельников",
                    "Денисов", "Гаврилов", "Тихонов", "Казаков", "Афанасьев", "Данилов", "Савельев", "Тимофеев", "Фомин",
                    "Чернов", "Абрамов", "Мартынов", "Ефимов", "Федотов", "Щербаков", "Назаров", "Калинин", "Исаев", "Чернышев",
                    "Быков", "Маслов", "Родионов", "Коновалов", "Лазарев", "Воронин", "Климов", "Филатов", "Пономарев", "Голубев",
                    "Кудрявцев", "Прохоров", "Наумов", "Потапов", "Журавлев", "Овчинников", "Трофимов", "Леонов", "Соболев",
                    "Ермаков", "Колесников", "Гончаров", "Емельянов", "Никифоров", "Грачев", "Котов", "Гришин", "Ефремов",
                    "Архипов", "Громов", "Кириллов", "Малышев", "Панов"
            };
        } else {
            lastNames = new String[]{"Smith"};
        }
        return lastNames[(int)(Math.random() * lastNames.length)];
    }
}
