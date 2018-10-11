package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CRUDRepository;
import ru.javawebinar.topjava.dao.VirtualMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private CRUDRepository<Meal> repository = VirtualMealRepository.getInstance();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        String action = request.getParameter("action");
        if (action == null) action = "";
        String mealId = request.getParameter("id");
        int id = 0;
        if (mealId != null) id = Integer.parseInt(mealId);
        RequestDispatcher view = null;
        switch (action) {
            case "delete":
                log.debug("remove meal");
                repository.remove(id);
                response.sendRedirect("meals");
                break;
            case "edit":
                log.debug("forward to edit page");
                Meal meal = repository.getById(id);
                request.setAttribute("meal", meal);
                view = request.getRequestDispatcher("edit.jsp");
                view.forward(request, response);
                break;
            default:
                log.debug("list meals");
                List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("meals", meals);
                view = request.getRequestDispatcher("meals.jsp");
                view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) action = "";
        if (action.equals("new")) {
            log.debug("new meal");
            Meal meal = new Meal(
                    0,
                    LocalDateTime.parse(request.getParameter("date") + " " + request.getParameter("time"), formatter),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))
            );
            repository.add(meal);
            response.sendRedirect("meals");
        }
        if (action.equals("update")) {
            log.debug("update meal");
            Meal meal = new Meal(
                    Integer.parseInt(request.getParameter("mealId")),
                    LocalDateTime.parse(request.getParameter("date") + " " + request.getParameter("time"), formatter),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))
            );
            repository.update(meal);
            response.sendRedirect("meals");
        }
    }
}
