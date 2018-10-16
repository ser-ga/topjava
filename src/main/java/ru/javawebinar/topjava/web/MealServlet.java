package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;


public class MealServlet extends HttpServlet {

    private MealRestController controller;
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            controller = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.info("doPost MealServlet");
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        String startDate = request.getParameter("startDate");
        String finishDate = request.getParameter("finishDate");
        String startTime = request.getParameter("startTime");
        String finishTime = request.getParameter("finishTime");
        if (action != null && action.equals("filter")) {
            request.setAttribute("meals", controller.getFiltered(
                    getDate(request, startDate),
                    getDate(request, finishDate),
                    getTime(request, startTime),
                    getTime(request, finishTime)
            ));
            request.setAttribute("startDate", startDate);
            request.setAttribute("finishDate", finishDate);
            request.setAttribute("startTime", startTime);
            request.setAttribute("finishTime", finishTime);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                getAuthUserId(), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (meal.isNew()) {
            controller.create(meal);
        } else {
            controller.update(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("doGet MealServlet");
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                controller.delete(getId(request));
                request.setAttribute("meals", controller.getAll());
                response.sendRedirect("meals");
                break;
            case "update":
                request.setAttribute("meal", controller.get(getId(request)));
            case "create":
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("meals", controller.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private LocalDate getDate(HttpServletRequest request, String param) {
        if (param != null && !param.isEmpty()) return LocalDate.parse(param);
        return null;
    }

    private LocalTime getTime(HttpServletRequest request, String param) {
        if (param != null && !param.isEmpty()) return LocalTime.parse(param);
        return null;
    }
}
