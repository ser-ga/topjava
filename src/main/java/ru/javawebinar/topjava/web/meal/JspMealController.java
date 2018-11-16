package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("delete/{id}")
    public String deleteMeal(@PathVariable(value = "id") Integer id) {
        delete(id);
        return "redirect:/meals";
    }

    @GetMapping("update/{id}")
    public String updateMeal(Model model, @PathVariable(value = "id") Integer id) {
        model.addAttribute("meal", get(id));
        return "mealForm";
    }

    @GetMapping("new")
    public String addMeal(Model model) {
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.now());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("update")
    public String updateMeal(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        Meal meal = new Meal(null, LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        if(id.isEmpty())
            create(meal);
        else {
            int mealId = Integer.parseInt(id);
            meal.setId(mealId);
            update(meal, mealId);
        }
        return "redirect:/meals";
    }

    @PostMapping("filter")
    public String filter(Model model, HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate,startTime,endDate,endTime));
        return "meals";
    }
}
