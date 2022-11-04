package ru.itis.helpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataAccessException;
import ru.itis.models.Book;
import ru.itis.models.Entity;
import ru.itis.repositories.EntityRepository;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ModelUtil {
    public static List<String> getFieldsFromEntity(Class<? extends Entity> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
    }

    public static List<Object> getEntityValues(Entity entity) {
        Class<?> aClass = entity.getClass();
        Field[] fields = aClass.getDeclaredFields();

        List<Object> resultList = new LinkedList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                resultList.add(field.get(entity));
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return resultList;
    }

    public static void addEntity(EntityRepository repository, HttpServletRequest req) throws IOException {
        Map<String, String[]> map = req.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();

        for (String s : map.keySet()) {
            if (!s.equals("aClass")) {
                paramsMap.put(s, map.get(s)[0]);
            }
        }

        repository.save(paramsMap);
    }

    public static void updateEntity(EntityRepository entityRepository, HttpServletRequest req) {
        Long id = Long.parseLong(req.getParameter("entityId"));

        Map<String, String[]> map = req.getParameterMap();
        String[] types = map.get("type");

        Map<String,Object> typedMap = new HashMap<>();

        int i = 0;
        for (String key : map.keySet()) {
            if (!key.equals("entityId") && !key.equals("type")) {
                String type = types[i++];
                switch (type) {
                    case "String":
                        typedMap.put(key, map.get(key)[0]);
                        break;
                    case "Integer":
                        Integer integer = Integer.parseInt(map.get(key)[0]);
                        typedMap.put(key, integer);
                        break;
                    case "Long":
                        Long longVal = Long.parseLong(map.get(key)[0]);
                        typedMap.put(key, longVal);
                        break;
                }
            }
        }

        entityRepository.update(typedMap);
    }

    public static void handleUpdateAndDeleteAndRedirect(EntityRepository repository, String redirectTo, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String[] toDelete = req.getParameterValues("toDelete");
        String updateId = req.getParameter("toUpdate");

        if (updateId != null) {
            Long id = Long.parseLong(updateId);

            Map<String, Optional<Entity>> singletonMap = Collections.singletonMap("entity", repository.findById(id));
            req.getServletContext().setAttribute("entityToUpdate", singletonMap);

            resp.sendRedirect(redirectTo + "/update");
            return;
        } else if (toDelete != null) {
            for (String i : toDelete) {
                try {
                    repository.delete(Long.parseLong(i));
                } catch (DataAccessException e) {
                    ModelUtil.handleException(e, req, resp);
                }
            }
        }

        resp.sendRedirect(redirectTo);
    }

    public static void handleException(Exception e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errorMessage", e);
        req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
    }

    public static Map<String, Object> fieldsAndValuesMap(Entity entity) {
        List<String> fields = getFieldsFromEntity(entity.getClass());
        List<Object> values = getEntityValues(entity);

        Map<String, Object> resultMap = new HashMap<>();
        for (int i = 0; i < fields.size(); i++) {
            resultMap.put(fields.get(i), values.get(i));
        }

        return resultMap;
    }

    public static void getFieldsFromEntityAndSetAsRequestAttribute(HttpServletRequest req) {
        try {
            Class<? extends Entity> aClass = (Class<? extends Entity>) Class.forName(req.getParameter("aClass"));

            List<String> fieldsOfEntity = ModelUtil.getFieldsFromEntity(aClass);
            req.setAttribute("headers", fieldsOfEntity);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
