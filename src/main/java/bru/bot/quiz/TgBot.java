package bru.bot.quiz;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;


public class TgBot extends TelegramLongPollingBot {

    public String token;
    public String name;

    public void setToken(String token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBotUsername() {
        return name;
    }

    public String getBotToken() {
        return token;
    }

    private ArrayList<Questions> questions = new ArrayList<>() {{
        add(new Questions("Как называется еврейский Новый год?", "Рош а-Шана", "Йом Кипур", "Кванза", "Ханука"));
        add(new Questions("Сколько синих полос на флаге США?", "0", "7", "13", "6"));
        add(new Questions("Кто из этих персонажей не дружит с Гарри Поттером?", "Драко Малфой", "Рон Уизли", "Невилл Лонгботтом", "Гермиона Грейнджер"));
        add(new Questions("Какая планета самая горячая?", "Венера", "Сатурн", "Меркурий", "Марс"));
    }};

    private HashMap<Long, Questions> usersQuest = new HashMap<>();

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                cmdStart(update);
            } else if (update.getMessage().getText().equals("Начать викторину")) {
                cmdStartGame(update);
            } else {
                cmdCheckAnswer(update);
            }
        }
    }

    private void cmdStart(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String response = "Привет! Давай сыграем в викторину. Жми \"Начать викторину\"";
        SendMessage outMess = new SendMessage();

        outMess.setChatId(chatId);
        outMess.setText(response);
        outMess.setReplyMarkup(initKeyboardStartMenu());

        try {
            execute(outMess);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void cmdStartGame(Update update) {
        Questions question = questions.get(new Random().nextInt(questions.size()));
        SendMessage outQuest = new SendMessage();

        outQuest.setChatId(update.getMessage().getChatId());
        outQuest.setText(question.getQuestion());
        outQuest.setReplyMarkup(initKeyboardAnswers(question));

        usersQuest.put(update.getMessage().getChatId(), question);

        try {
            execute(outQuest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void cmdCheckAnswer(Update update) {
        Questions question = usersQuest.get(update.getMessage().getChatId());
        String answer = update.getMessage().getText();
        String message;

        System.out.println(question.getAnswer());
        System.out.println(answer);

        if (question.getAnswer().trim().equals(answer.trim())) {
            message = "Правильный ответ!";
        } else {
            message = "Попробуй еще!";
        }
        SendMessage m = new SendMessage();
        m.setText(message);
        m.setChatId(update.getMessage().getChatId());

        try {
            execute(m);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        cmdStartGame(update);
    }

    ReplyKeyboardMarkup initKeyboardStartMenu() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);

        keyboardRow.add(new KeyboardButton("Начать викторину"));
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    ReplyKeyboardMarkup initKeyboardAnswers(Questions question) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);

        ArrayList<String> answers = question.shuffleAnswers();

        keyboardRow1.add(new KeyboardButton(answers.get(0)));
        keyboardRow1.add(new KeyboardButton(answers.get(1)));
        keyboardRow2.add(new KeyboardButton(answers.get(2)));
        keyboardRow2.add(new KeyboardButton(answers.get(3)));
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }
}
