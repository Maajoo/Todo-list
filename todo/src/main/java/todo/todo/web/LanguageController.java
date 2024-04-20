package todo.todo.web;

import java.util.Locale;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class LanguageController {

    @Autowired
    private LocaleResolver localeResolver;

    @PostMapping("/lang")
    public String setLanguage(HttpServletRequest request, HttpServletResponse response, String lang) {
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request, response, locale);
        return "redirect:/";
    }
}
