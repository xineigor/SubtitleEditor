package main;

import tools.MenuBuilder;
import tools.TableBuilder;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static final String RESET = "\u001B[0m";
    public static final String PURPLE = "\u001B[35m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";

    private static final String PROTOCOL = "jdbc:postgresql://";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL_LOCALE_NAME = "172.18.0.2:5432/";

    private static final String DATABASE_NAME = "subtitle";

    public static final String DATABASE_URL = PROTOCOL + URL_LOCALE_NAME + DATABASE_NAME;
    public static final String USER_NAME = "postgres";
    public static final String DATABASE_PASS = "postgres";

    public static void main(String[] args) {
        checkDriver();
        checkDB();
        System.out.println("Подключение к базе данных | " + DATABASE_URL);

        Scanner scanner = new Scanner(System.in);
        MenuBuilder menuBuilder = new MenuBuilder();
        menuBuilder.addOption("Список всех существующих файлов субтитров", 1);
        menuBuilder.addOption("Вывести все строки субтитров, расположенные в верхней части экрана", 2);
        menuBuilder.addOption("Удалить файл субтитров по id", 3);
        menuBuilder.addOption("Копировать файл субтитров по id с заменой языка", 4);
        menuBuilder.addOption("Добавить стиль субтитров", 5);
        menuBuilder.addOption("Обновить стиль субтитров по id", 6);
        menuBuilder.addOption("Показать все стили субтитров", 7);
        menuBuilder.addOption("Найти все файлы субтитров определенного формата", 8);
        menuBuilder.addOption("Добавить блок субтитров", 9);
        menuBuilder.addOption("Показать все блоки субтитров", 10);
        menuBuilder.addOption("Удалить блок субтитров по id", 11);
        menuBuilder.addOption("Добавить строку субтитров", 12);
        menuBuilder.addOption("Удалить строку субтитров по id", 13);
        menuBuilder.addOption("Вывести все строки субтитров", 14);
        menuBuilder.addOption("Вывести все строки субтитров c определённым текстом", 15);
        menuBuilder.addOption("Вывести количество файлов субтитров на разных языках", 16);
        menuBuilder.addOption("Выход", 17);

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, DATABASE_PASS)) {
            outerLoop:
            //метка !
            while (true) {
                System.out.print(menuBuilder);
                String userInput = scanner.nextLine();
                switch (userInput) {
                    case "1" -> getSubs(connection);
                    case "2" -> getUpStrings(connection);
                    case "3" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите id файла " + PURPLE + "◉" + RESET);
                        String idDelSub = scanner.nextLine();
                        deleteSubtitleFile(connection, Integer.parseInt(idDelSub));
                    }
                    case "4" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите через пробел id файла и желаемый язык " + PURPLE + "◉" + RESET);
                        String in = scanner.nextLine();
                        String[] in_array = in.split(" ");
                        copyСaptionsAnotherLanguage(connection, Integer.parseInt(in_array[0]), in_array[1]);
                    }
                    case "5" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите id тех субтитров куда хотите добавить стиль " + PURPLE + "◉" + RESET);
                        String addIdSub = scanner.nextLine();
                        System.out.println(PURPLE + "◉" + RESET + " Введите данные стиля " + PURPLE + "◉" + RESET);
                        String addStyleSub = scanner.nextLine();
                        addSubtitleStyle(connection, Integer.parseInt(addIdSub), addStyleSub);
                    }
                    case "6" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите id файла субтитров " + PURPLE + "◉" + RESET);
                        String updateIdSub = scanner.nextLine();
                        System.out.println(PURPLE + "◉" + RESET + " Введите данные стиля " + PURPLE + "◉" + RESET);
                        String updateStyleSub = scanner.nextLine();
                        updateSubtitleStyle(connection, updateStyleSub, Integer.parseInt(updateIdSub));
                    }
                    case "7" -> {
                        displayAllSubtitleStyles(connection);
                    }
                    case "8" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите формат файла субтитров (ass/srt) " + PURPLE + "◉" + RESET);
                        findSubtitleType(connection, scanner.nextLine());
                    }
                    case "9" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите id файла субтитров куда нужно добавить блок " + PURPLE + "◉" + RESET);
                        String idSubFile = scanner.nextLine();
                        System.out.println(PURPLE + "◉" + RESET + " Введите время начало блока " + PURPLE + "◉" + RESET);
                        String blockStartTime = scanner.nextLine();
                        System.out.println(PURPLE + "◉" + RESET + " Введите время конца блока " + PURPLE + "◉" + RESET);
                        String blockEndTime = scanner.nextLine();
                        addSubtitleBlock(connection, Integer.parseInt(idSubFile), blockStartTime, blockEndTime);
                    }
                    case "10" -> {
                        displayAllSubtitleBlocks(connection);
                    }
                    case "11" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите id блока субтитров " + PURPLE + "◉" + RESET);
                        deleteSubtitleBlock(connection, Integer.parseInt(scanner.nextLine()));
                    }
                    case "12" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите id блока куда нужно добавить строку субтитров " + PURPLE + "◉" + RESET);
                        String idSubBlock = scanner.nextLine();
                        System.out.println(PURPLE + "◉" + RESET + " Введите текст строки " + PURPLE + "◉" + RESET);
                        String lineText = scanner.nextLine();
                        System.out.println(PURPLE + "◉" + RESET + " Введите номер линии субтитров" + PURPLE + "◉" + RESET);
                        String lineNumber = scanner.nextLine();
                        System.out.println(PURPLE + "◉" + RESET + " Введите позицию (default/top)" + PURPLE + "◉" + RESET);
                        String position = scanner.nextLine();
                        addSubtitleLine(connection, Integer.parseInt(idSubBlock), lineText, Integer.parseInt(lineNumber), position);
                    }
                    case "13" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите id строки субтитров " + PURPLE + "◉" + RESET);
                        deleteSubtitleLine(connection, Integer.parseInt(scanner.nextLine()));
                    }
                    case "14" -> {
                        displayAllSubtitleLines(connection);
                    }
                    case "15" -> {
                        System.out.println(PURPLE + "◉" + RESET + " Введите текст, который хотите найти " + PURPLE + "◉" + RESET);
                        findSubtitleLines(connection, scanner.nextLine());
                    }
                    case "16" -> {
                        displaySubtitleLanguage(connection);
                    }
                    case "17" -> {
                        break outerLoop;
                    } //выходим по метке outerLoop!
                    default -> System.out.println(RED + "◉" + RESET + " Re-enter! " + RED + "◉" + RESET);
                }
            }
        } catch (SQLException e) {
            switch (e.getSQLState().substring(0, 2)) {
                case "23" -> System.out.println("Произошло нарушение ограничения целостности: " + e.getMessage());
                case "42" -> System.out.println("Ошибка синтаксиса SQL: " + e.getMessage());
                case "08" -> System.out.println("Ошибка соединения с базой данных: " + e.getMessage());
                case "22" -> System.out.println("Ошибка неверного типа данных: " + e.getMessage());
                default -> throw new RuntimeException(e);
            }
        }
    }

    public static void checkDriver() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Нет JDBC-драйвера!");
            throw new RuntimeException(e);
        }
    }

    public static void checkDB() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, DATABASE_PASS);
        } catch (SQLException e) {
            System.out.println("Нет базы данных!");
            throw new RuntimeException(e);
        }
    }

    private static void getSubs(Connection connection) throws SQLException {

        String columnName0 = "id", columnName1 = "file_name", columnName2 = "format", columnName3 = "language", columnName4 = "movie_title", columnName5 = "upload_date";
        String param0 = null;
        String param1 = null;
        String param2 = null;
        String param3 = null;
        String param4 = null;
        String param5 = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM subtitle_file;");
        TableBuilder tableBuilder = new TableBuilder("id", "file_name", "format", "language", "movie_title", "upload_date");

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            param1 = rs.getString(columnName1);
            param2 = rs.getString(columnName2);
            param3 = rs.getString(columnName3);
            param4 = rs.getString(columnName4);
            param5 = rs.getString(columnName5);

            tableBuilder.addRow(param0, param1, param2, param3, param4, param5);
        }
        System.out.print(tableBuilder);
    }

    private static void getUpStrings(Connection connection) throws SQLException {

        String columnName0 = "sub_text";
        String param0 = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT sub_text FROM subtitle_line WHERE position = 'top';");
        TableBuilder tableBuilder = new TableBuilder("sub_text");

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            tableBuilder.addRow(param0);
        }

        if (param0 != null) {
            System.out.print(tableBuilder);
        }

        if (param0 == null) {
            System.out.println(RED + "◉" + RESET + " Ничего не найдено! " + RED + "◉" + RESET);
        }
    }

    private static void copyСaptionsAnotherLanguage(Connection connection, Integer id, String language) throws SQLException {

        if (id == null || (language == null || language.isBlank())) {
            return;
        }

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO subtitle_file (file_name, format, language, movie_title, upload_date) \n" +
                        "SELECT file_name, format, ?, movie_title, upload_date \n" +
                        "FROM subtitle_file \n" +
                        "WHERE id = ?;");
        statement.setString(1, language);
        statement.setInt(2, id);
        int rs = statement.executeUpdate();

        System.out.println(GREEN + "◉" + RESET + " Добавление успешно! " + "Добавлено строк: " + rs + " " + GREEN + "◉" + RESET);
    }

    private static void deleteSubtitleFile(Connection connection, Integer id) throws SQLException {
        if (id == null) { return; }

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM subtitle_file WHERE id = ?;");
        statement.setInt(1, id);
        int count = statement.executeUpdate();
        System.out.println(GREEN + "◉" + RESET + " Удаление успешно! " + "Изменено строк: " + count + " " + GREEN + "◉" + RESET);
    }

    private static void addSubtitleStyle(Connection connection, Integer id, String style) throws SQLException {
        if (id == null || (style == null || style.isBlank())) { return; }

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO subtitle_style (sub_file_id, style_data) VALUES (?,?);");
        statement.setInt(1, id);
        statement.setString(2, style);
        int count = statement.executeUpdate();
        System.out.println(GREEN + "◉" + RESET + " Стиль добавлен! " + "Изменено строк: " + count + " " + GREEN + "◉" + RESET);
    }

    private static void updateSubtitleStyle(Connection connection, String style, Integer id) throws SQLException {
        if ((style == null || style.isBlank()) || id == null) { return; }

        PreparedStatement statement = connection.prepareStatement(
                "UPDATE subtitle_style SET style_data = ? WHERE sub_file_id = ?");
        statement.setString(1, style);
        statement.setInt(2, id);
        int count = statement.executeUpdate();
        System.out.println(GREEN + "◉" + RESET + " Данные записаны! " + "Изменено строк: " + count + " " + GREEN + "◉" + RESET);
    }

    private static void displayAllSubtitleStyles(Connection connection) throws SQLException {

        String columnName0 = "id", columnName1 = "sub_file_id", columnName2 = "style_data", columnName3 = "file_name";
        String param0 = null;
        String param1 = null;
        String param2 = null;
        String param3 = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT subtitle_style.id, subtitle_style.sub_file_id, subtitle_style.style_data, subtitle_file.file_name FROM subtitle_style JOIN subtitle_file ON subtitle_style.sub_file_id = subtitle_file.id;");
        TableBuilder tableBuilder = new TableBuilder("id", "sub_file_id", "style_data", "file_name");

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            param1 = rs.getString(columnName1);
            param2 = rs.getString(columnName2);
            param3 = rs.getString(columnName3);
            tableBuilder.addRow(param0, param1, param2, param3);
        }
        System.out.print(tableBuilder);
    }

    private static void findSubtitleType(Connection connection, String type) throws SQLException {
        if (type == null || type.isBlank()) { return; }

        String columnName0 = "id", columnName1 = "file_name", columnName2 = "format", columnName3 = "language", columnName4 = "movie_title", columnName5 = "upload_date";
        String param0 = null;
        String param1 = null;
        String param2 = null;
        String param3 = null;
        String param4 = null;
        String param5 = null;

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM subtitle_file WHERE format = sub_type(?);");
        statement.setString(1, type);
        TableBuilder tableBuilder = new TableBuilder("id", "file_name", "format", "language", "movie_title", "upload_date");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            param1 = rs.getString(columnName1);
            param2 = rs.getString(columnName2);
            param3 = rs.getString(columnName3);
            param4 = rs.getString(columnName4);
            param5 = rs.getString(columnName5);

            tableBuilder.addRow(param0, param1, param2, param3, param4, param5);
        }
        System.out.print(tableBuilder);
    }

    private static void addSubtitleBlock(Connection connection, Integer id, String start_time, String end_time) throws SQLException {
        if (id == null || (start_time == null || start_time.isBlank()) || (end_time == null || end_time.isBlank())) { return; }

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO subtitle_block (sub_file_id, start_time, end_time) VALUES (?,?,?);");
        statement.setInt(1, id);
        statement.setTime(2, Time.valueOf(start_time));
        statement.setTime(3, Time.valueOf(end_time));
        int count = statement.executeUpdate();
        System.out.println(GREEN + "◉" + RESET + " Блок добавлен! " + "Изменено строк: " + count + " " + GREEN + "◉" + RESET);
    }

    private static void displayAllSubtitleBlocks(Connection connection) throws SQLException {

        String columnName0 = "id", columnName1 = "sub_file_id", columnName2 = "start_time", columnName3 = "end_time", columnName4 = "movie_title", columnName5 = "language";

        String param0 = null;
        String param1 = null;
        String param2 = null;
        String param3 = null;
        String param4 = null;
        String param5 = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT subtitle_block.id, subtitle_block.sub_file_id, subtitle_block.start_time, subtitle_block.end_time, subtitle_file.movie_title, subtitle_file.language FROM subtitle_block JOIN subtitle_file ON subtitle_block.sub_file_id = subtitle_file.id;");
        TableBuilder tableBuilder = new TableBuilder("id", "sub_file_id", "start_time", "end_time", "movie_title", "language");

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            param1 = rs.getString(columnName1);
            param2 = rs.getString(columnName2);
            param3 = rs.getString(columnName3);
            param4 = rs.getString(columnName4);
            param5 = rs.getString(columnName5);
            tableBuilder.addRow(param0, param1, param2, param3, param4, param5);
        }
        System.out.print(tableBuilder);
    }

    private static void deleteSubtitleBlock(Connection connection, Integer id) throws SQLException {
        if (id == null) { return; }

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM subtitle_block WHERE id = ?;");
        statement.setInt(1, id);
        int count = statement.executeUpdate();
        System.out.println(GREEN + "◉" + RESET + " Удаление успешно! " + "Изменено строк: " + count + " " + GREEN + "◉" + RESET);
    }

    private static void addSubtitleLine(Connection connection, Integer id, String sub_text, Integer line_number, String position) throws SQLException {
        if (id == null || (sub_text == null || sub_text.isBlank()) || line_number == null || (position == null || position.isBlank())) { return; }

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO subtitle_line (sub_block_id, sub_text, line_number, position) VALUES (?,?,?,position_type(?));");
        statement.setInt(1, id);
        statement.setString(2, sub_text);
        statement.setInt(3, line_number);
        statement.setString(4, position);
        int count = statement.executeUpdate();
        System.out.println(GREEN + "◉" + RESET + " Строка добавлена! " + "Изменено строк: " + count + " " + GREEN + "◉" + RESET);
    }

    private static void deleteSubtitleLine(Connection connection, Integer id) throws SQLException {
        if (id == null) { return; }

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM subtitle_line WHERE id = ?;");
        statement.setInt(1, id);
        int count = statement.executeUpdate();
        System.out.println(GREEN + "◉" + RESET + " Удаление успешно! " + "Изменено строк: " + count + " " + GREEN + "◉" + RESET);
    }

    private static void displayAllSubtitleLines(Connection connection) throws SQLException {

        String columnName0 = "id", columnName1 = "sub_block_id", columnName2 = "sub_text", columnName3 = "line_number", columnName4 = "position", columnName5 = "start_time", columnName6 = "end_time", columnName7 = "language", columnName8 = "movie_title", columnName9 = "file_name";
        String param0 = null;
        String param1 = null;
        String param2 = null;
        String param3 = null;
        String param4 = null;
        String param5 = null;
        String param6 = null;
        String param7 = null;
        String param8 = null;
        String param9 = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT subtitle_line.id, subtitle_line.sub_block_id, subtitle_line.sub_text, subtitle_line.line_number, subtitle_line.position, subtitle_block.start_time, subtitle_block.end_time, subtitle_file.language, subtitle_file.movie_title, subtitle_file.file_name FROM subtitle_line JOIN subtitle_block ON subtitle_line.sub_block_id = subtitle_block.id JOIN subtitle_file ON subtitle_block.sub_file_id = subtitle_file.id;");
        TableBuilder tableBuilder = new TableBuilder("id", "sub_block_id", "sub_text", "line_number", "position", "start_time", "end_time", "language", "movie_title", "file_name");

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            param1 = rs.getString(columnName1);
            param2 = rs.getString(columnName2);
            param3 = rs.getString(columnName3);
            param4 = rs.getString(columnName4);
            param5 = rs.getString(columnName5);
            param6 = rs.getString(columnName6);
            param7 = rs.getString(columnName7);
            param8 = rs.getString(columnName8);
            param9 = rs.getString(columnName9);
            tableBuilder.addRow(param0, param1, param2, param3, param4, param5, param6, param7, param8, param9);
        }
        System.out.print(tableBuilder);
    }

    private static void findSubtitleLines(Connection connection, String request) throws SQLException {
        if (request == null || request.isBlank()) { return; }

        String columnName0 = "id", columnName1 = "sub_block_id", columnName2 = "sub_text", columnName3 = "line_number", columnName4 = "position", columnName5 = "start_time", columnName6 = "end_time", columnName7 = "language", columnName8 = "movie_title";
        String param0 = null;
        String param1 = null;
        String param2 = null;
        String param3 = null;
        String param4 = null;
        String param5 = null;
        String param6 = null;
        String param7 = null;
        String param8 = null;

        PreparedStatement statement = connection.prepareStatement(
                "SELECT subtitle_line.id, subtitle_line.sub_block_id, subtitle_line.sub_text, subtitle_line.line_number, subtitle_line.position, subtitle_block.start_time, subtitle_block.end_time, subtitle_file.language, subtitle_file.movie_title FROM subtitle_line JOIN subtitle_block ON subtitle_line.sub_block_id = subtitle_block.id JOIN subtitle_file ON subtitle_block.sub_file_id = subtitle_file.id WHERE subtitle_line.sub_text LIKE CONCAT('%', ?, '%');");
        statement.setString(1, request);
        ResultSet rs = statement.executeQuery();
        TableBuilder tableBuilder = new TableBuilder("id", "sub_block_id", "sub_text", "line_number", "position", "start_time", "end_time", "language", "movie_title");

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            param1 = rs.getString(columnName1);
            param2 = rs.getString(columnName2);
            param3 = rs.getString(columnName3);
            param4 = rs.getString(columnName4);
            param5 = rs.getString(columnName5);
            param6 = rs.getString(columnName6);
            param7 = rs.getString(columnName7);
            param8 = rs.getString(columnName8);
            tableBuilder.addRow(param0, param1, param2, param3, param4, param5, param6, param7, param8);
        }
        System.out.print(tableBuilder);
    }

    private static void displaySubtitleLanguage(Connection connection) throws SQLException {

        String columnName0 = "language", columnName1 = "count";
        String param0 = null;
        String param1 = null;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT language, COUNT(id) FROM subtitle_file GROUP BY language");
        TableBuilder tableBuilder = new TableBuilder("language", "count");

        while (rs.next()) {
            param0 = rs.getString(columnName0);
            param1 = rs.getString(columnName1);
            tableBuilder.addRow(param0, param1);
        }
        System.out.print(tableBuilder);
    }


}
