package models;

import com.vdurmont.emoji.EmojiParser;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private long chat_id;
    private Searcher searcher;
    private JediBotUser user;
    private enum SearchServices{GOOGLE,YANDEX,HABR;};
    //private enum StartMessages{,}
    String userName = null;
    String lastMessage = "";
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    public Bot() {
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getFrom().getId();
        String text = update.getMessage().getText();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        userName= update.getMessage().getFrom().getFirstName();
        try {
            sendMessage.setText(getMessage(text));
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    @Override
    public String getBotUsername() {
        return "Programming Jedi";
    }

    @Override
    public String getBotToken() {
        return "1013547723:AAEdDszQpJ5ybyE1U8Y19eS9C9VtYcUfrMc";
    }
    public String getMessage(String msg)
    {
        String smileForCpp = EmojiParser.parseToUnicode(":muscle|type_1_2:");
        String smileForPy = EmojiParser.parseToUnicode(":chicken:");
        String smileForJava = EmojiParser.parseToUnicode(":coffee:");
        String smileForMainMenu = EmojiParser.parseToUnicode(":door:");
        String smileForGreetings = EmojiParser.parseToUnicode(":wave|type_1_2:");
        String smileForWeb = EmojiParser.parseToUnicode("");
        String smileForData = EmojiParser.parseToUnicode("");
        String smileForMobile = EmojiParser.parseToUnicode("");
        String smileForLearnMore = EmojiParser.parseToUnicode("");

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true) ;
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        boolean is_started = false;
        if(msg.equals("/start"))
        {
            UserService userService = new UserService();
            if(userService.findUser(chat_id) == null)
            {
                user = new JediBotUser(chat_id,0,userName);
                userService.saveUser(user);
            }
            is_started = true;

        }
        if(msg.toLowerCase().equals("привет") || msg.equals("В главное"+smileForMainMenu)||is_started)
        {
            //UserService userService = new UserService();
           // JediBotUser currentUser= userService.findUser(chat_id);

            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardFirstRow.add("Web"+smileForWeb);
            keyboardFirstRow.add("Mobile"+smileForMobile);
            keyboardFirstRow.add("Data"+smileForData);
            keyboardSecondRow.add("Узнать больше"+smileForLearnMore);
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            if(is_started)
                return "Привет, " +userName+smileForGreetings;
            return "Выбрать...";

        }
        if(msg.toLowerCase().equals("web"+smileForWeb))
        {

            lastMessage = msg;
            UserService userService = new UserService();
            user = userService.findUser(chat_id);
            if(user.getExperience()==0)
            {
                user.setExperience(1); // добавить проверку чтобы так делало только первое нажатие
                userService.updateUser(user);
            }
            keyboard.clear();
            keyboardFirstRow.clear();
            try {
                SendMessage sendMessage = new SendMessage();
                StringBuilder foundedResults = new StringBuilder();

                /*searcher = new models.Searcher(SearchServices.GOOGLE.name());
                foundedResults.append("Немного полезных ссылок: \n");
                String googleResults = searcher.search("c++");
                foundedResults.append(googleResults);
*/
                searcher = new Searcher(SearchServices.YANDEX.name());
                foundedResults.append("Немного полезных ссылок: \n");
                String yandexResults = searcher.search("");
                foundedResults.append(yandexResults);

                searcher = new Searcher(SearchServices.HABR.name());
                foundedResults.append("Полезные статьи:\n");
                String habrResults = searcher.search("C++");
                foundedResults.append(habrResults);

                sendMessage.setText(foundedResults.toString());
                sendMessage.setChatId(chat_id);
                execute(sendMessage);

                } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
            keyboardFirstRow.add("");
            keyboardFirstRow.add("");
            keyboardFirstRow.add("");
            keyboardSecondRow.add("В главное"+smileForMainMenu);
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "";
        }
        if(msg.toLowerCase().equals("mobile" + smileForMobile))
        {
            lastMessage = msg;
            UserService userService = new UserService();
            user = userService.findUser(chat_id);
            if(user.getExperience()==0)
            {

                user.setExperience(1); // добавить проверку чтобы так делало только первое нажатие
                userService.updateUser(user);
            }

            keyboard.clear();
            keyboardFirstRow.clear();
            try {
                SendMessage sendMessage = new SendMessage();
                StringBuilder foundedResults = new StringBuilder();


                searcher = new Searcher(SearchServices.YANDEX.name());
                foundedResults.append("Немного полезных ссылок: \n");
                String yandexResults = searcher.search("");
                foundedResults.append(yandexResults);

                searcher = new Searcher(SearchServices.HABR.name());
                foundedResults.append("Полезные статьи:\n");
                String habrResults = searcher.search("");
                foundedResults.append(habrResults);

                sendMessage.setText(foundedResults.toString());
                sendMessage.setChatId(chat_id);
                execute(sendMessage);

            } catch (IOException | TelegramApiException e) {
                e.printStackTrace();
            }
            keyboardFirstRow.add("");
            keyboardFirstRow.add("");
            keyboardFirstRow.add("");
            keyboardSecondRow.add("В главное"+smileForMainMenu);
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "";
        }


        return msg;
    }
}
