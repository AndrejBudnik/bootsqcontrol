package sample;

/**
 * Created by kotsy on 25.12.2016.
 */
public final class Constants {

    //  Language: russian   //

    public static final String NEWLINE = "\n";
    public static final String COMMA = ",";
    public static final String TAB = "  ";
    public static final String EMPTY = "";


    public static final String EXECUTING = "Executing: ";
    public static final String MD5HASH = "MD5";
    public static final String ZERO = "0";
    public static final String HEADER_PROGRAM_NAME = "BQCv1.0";
    public static final String LOGIN_WINDOW_FXML = "login.fxml";
    public static final String UNCHECKED = "unchecked";


    public static final String USER_CHOOSE_ORDER = "Выберите заказ";
    public static final String USER_CHOOSE_CUSTOMER = "Выберите заказ";
    public static final String USER_CHOOSE_PLAN = "Выберите план";
    public static final String USER_CHOOSE_DEFECT_TYPE = "Выберите тип брака";
    public static final String ALERT_CONTEXT = "Ошибка при чтении данных. Поля, содержащие ошибку, были выделены." +
                                                " Пожалуйста, исправьте данные и попробуйте снова.";
    public static final String ALERT_INCORRECT_DATA = "Неверные данные";
    public static final String ALERT_INCORRECT_LOGIN = "Неверно введен логин или пароль.";
    public static final String ALERT_NO_SUCH_ORDER = "Такого заказа не существует";
    public static final String ALERT_EXIT_CONFIRM_TITLE = "Выход";
    public static final String ALERT_EXIT_CONFIRM_HEADER = "Вы действительно хотите выйти?";
    public static final String ALERT_EXIT_CONFIRM_TEXT = "Все изменения при выходе сохраняются. При повторном входе необходимо будет пройти авторизацию.";

    public static final String ALERT_DELETE_CONFIRM_TITLE = "Удаление";
    public static final String ALERT_DELETE_CONFIRM_HEADER = "Вы действительно удалить объект?";
    public static final String ALERT_DELETE_CONFIRM_TEXT = "Отменить это действие будет невозможно.";


    public static final String STYLE_BG_COLOR = "-fx-background-color: #ff5d5d";

    public static final String DATE_FORMAT = "yyyy-MM-dd";


    public static final String REGEX_NAME_MATCH = "[А-Я][а-я]{1,20}\\s[А-Я]\\.[А-Я]\\.";
    public static final String REGEX_EMAIL_MATCH = ".*@.*\\..*";
    public static final String REGEX_TELEPHONE_NUMBER_MATCH = "^\\+[0-9]+$";
    public static final String REGEX_SIMPLE_TELEPHONE_NUMBER_MATCH = "\\+[0-9]{12}";
    public static final String REGEX_DESCRIPTION_MATCH = ".{1,249}";
    public static final String REGEX_POST_MATCH = "[А-Я][а-я]+";
    public static final String REGEX_CURRENCY_MATCH = "[0-9]+(\\.[0-9][0-9]?)?";
    public static final String REGEX_FASON_MATCH = "[0-9]{2}[А-Я]";
    public static final String REGEX_COUNT_MATCH = "[0-9]+";
    public static final String REGEX_SIZE_MATCH = "[0-9]{1,2}";


    public static final String EXPORT_CUSTOMER_FILEPATH = "src\\main\\resources\\Reports\\Customer.csv";
    public static final String EXPORT_CUSTOMER_TABLE_HEADER = "NAME,TELEPHONE,EMAIL\n";

    public static final String EXPORT_PLAN_FILEPATH = "src\\main\\resources\\Reports\\Plan.csv";
    public static final String EXPORT_PLAN_TABLE_HEADER = "ID,EMPLOYEE,EXPECTED,ACTUAL,SIZE,FASON\n";

    public static final String EXPORT_ORDER_FILEPATH = "src\\main\\resources\\Reports\\Order.csv";
    public static final String EXPORT_ORDER_TABLE_HEADER = "ID,FASON,COUNT,SIZE,DATE\n";

    public static final String EXPORT_EMPLOYEE_FILEPATH = "src\\main\\resources\\Reports\\Employee.csv";
    public static final String EXPORT_EMPLOYEE_TABLE_HEADER = "NAME,POST,SALARY,TELEPHONE\n";

    public static final String EXPORT_DEFECT_FILEPATH = "src\\main\\resources\\Reports\\Defect.csv";
    public static final String EXPORT_DEFECT_TABLE_HEADER = "ID,TYPE\n";

    public static final String EXPORT_DEFECT_MONITOR_FILEPATH = "src\\main\\resources\\Reports\\DefectMonitor.csv";
    public static final String EXPORT_DEFECT_MONITOR_TABLE_HEADER = "DATE,COUNT,TYPE\n";


    public static final String MAIN_VIEW_FILEPATH = "tableView.fxml";
    public static final String CSS_FILEPATH = "src\\main\\resources\\styles.css";

    public static final String CUSTOMER_VIEW_FILEPATH = "Entityview/customerView.fxml";
    public static final String EMPLOYEE_VIEW_FILEPATH = "Entityview/employeeView.fxml";
    public static final String ORDER_VIEW_FILEPATH = "Entityview/orderView.fxml";
    public static final String PLAN_VIEW_FILEPATH = "Entityview/planView.fxml";
    public static final String DEFECT_VIEW_FILEPATH = "Entityview/defectView.fxml";
    public static final String DEFECT_MONITOR_VIEW_FILEPATH = "Entityview/defectMonView.fxml";


    public static final String GUI_CONTEXT_DELETE = "Удалить";
    public static final String GUI_BUTTON_ADD = "Добавить";
    public static final String GUI_BUTTON_REFRESH = "Обновить";
    public static final String GUI_BUTTON_EXIT = "Выход";

    public static final String GUI_CUSTOMER_TAB = "Заказчики";
    public static final String GUI_ORDER_TAB = "Заказы";
    public static final String GUI_EMPLOYEE_TAB = "Сотрудники";
    public static final String GUI_PLAN = "План";
    public static final String GUI_DEFECT_MONITOR_TAB = "Учёт брака";
    public static final String GUI_DEFECT_TAB = "Виды брака";

    public static final String GUI_COLUMN_ID = "ID";
    public static final String GUI_COLUMN_NAME = "Имя";
    public static final String GUI_COLUMN_NUM = "Тел. номер";
    public static final String GUI_COLUMN_MAIL = "Эл. почта";

    public static final String GUI_COLUMN_CUSTOMER = "Заказчик";
    public static final String GUI_COLUMN_COUNT = "Кол-во";
    public static final String GUI_COLUMN_FASON = "Фасон";
    public static final String GUI_COLUMN_DATE = "Дата";
    public static final String GUI_COLUMN_SIZE = "Размер";
    public static final String GUI_COLUMN_POST = "Должность";
    public static final String GUI_COLUMN_SALARY = "Оклад";
    public static final String GUI_COLUMN_ORDER = "Заказ";
    public static final String GUI_COLUMN_EXECUTOR = "Исполнитель";
    public static final String GUI_COLUMN_DONE = "Выполнено";
    public static final String GUI_COLUMN_DEFECT_COUNT = "Кол-во брак.";
    public static final String GUI_COLUMN_DEFECT_TYPE = "Тип брака";
    public static final String GUI_COLUMN_DESC = "Описание";

}
