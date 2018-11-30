package app;

import app.domain.Message;
import app.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public ModelAndView index() {
        Iterable<Message> messages = messageRepository.findAll();
        ModelAndView modelAndView = new ModelAndView(
                "index",
                "messages",
                messages);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView add(@RequestParam String text, @RequestParam String tag) {
        Message message = new Message(text, tag);
        messageRepository.save(message);

        return index();
    }

    @GetMapping("/greeting")
    public ModelAndView greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name) {
        ModelAndView modelAndView = new ModelAndView(
                "greeting",
                "name",
                name);

        return modelAndView;
    }

    @PostMapping("/filter")
    public ModelAndView filter(@RequestParam String filter) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        ModelAndView modelAndView = new ModelAndView(
                "index",
                "messages",
                messages);

        return modelAndView;
    }
}
