package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ValidateRequestBodyControllerTest {
    @Autowired
    private MockMvc mvc;

    @DisplayName("Post Невалидные данные код ответа 400")
    @MethodSource("test1MethodSource")
    @ParameterizedTest(name = "{index} {0} {1}")
    public void test1_postInvalid_thenReturnsStatus400(String urlTemplate, String body) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Невалидные данные для post
    private static Stream<Arguments> test1MethodSource() {
        return Stream.of(
                //почта не может быть пустой
                Arguments.of("/users", "{\"login\": \"dol\", \"name\": \"Dolly\", \"birthday\": \"1946-08-20\"}"),
                //почта без собаки
                Arguments.of("/users", "{\"login\": \"dol\", \"name\": \"Dolly\"," +
                        "\"email\":\"mailmail.ru\", \"birthday\": \"1946-08-20\"}"),
                //невалидная почта
                Arguments.of("/users", "{\"login\": \"dol\", \"name\": \"Dolly\"," +
                        "\"email\":\"mailmail.ru@\", \"birthday\": \"1946-08-20\"}"),
                //пустой запрос
                Arguments.of("/users", ""),
                //логин содержит пробелы
                Arguments.of("/users", "{\"login\": \"dolore ullamco\", \"email\": \"mail@gmail.com\"," +
                        "\"birthday\": \"1980-08-20\"}"),
                //дата рождения в будущем
                Arguments.of("/users", "{\"login\": \"doloreullamco\", \"email\": \"mail@gmail.com\"," +
                        "\"birthday\": \"2100-08-20\"}"),
                //всё неправильно
                Arguments.of("/users", "{\"login\": \"dol dol\", \"email\":\"mailmail.ru\"," +
                        "\"birthday\": \"2200-08-20\"}"),

                //фильмы
                //без названия
                Arguments.of("/films",  "{ \"description\": \"bad film1\"," +
                        "\"releaseDate\": \"2000-10-15\",  \"duration\": 100}"),
                //пусто
                Arguments.of("/films",  ""),
                //отрицательная продолжительность
                Arguments.of("/films",  "{ \"name\": \"anti-film\",  \"description\": \"anti-universe\"," +
                        "\"releaseDate\": \"1967-03-25\",  \"duration\": -100}"),
                //невалидная дата
                Arguments.of("/films",  "{ \"name\": \"no film\",  \"description\": \"no film test\"," +
                        "\"releaseDate\": \"1895-12-27\",  \"duration\": 100}"),
                //длинное описание
                Arguments.of("/films",  "{"+ longFilm() + "}")
        );
    }

    private static String longFilm() {
        char[] arr = new char[201];
        Arrays.fill(arr, 'f');
        return String.format("\"name\": \"fff\",  \"description\": \"%s\",", new String(arr)) +
                "\"releaseDate\": \"2000-10-25\",  \"duration\": 100";
    }

    @DisplayName("Post Валидные данные код ответа 200")
    @MethodSource("test2MethodSource")
    @ParameterizedTest(name = "{index} {0} {1}")
    public void test2_postValid_thenReturnsStatusOK(String urlTemplate, String body) throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Валидные данные для post
    private static  Stream<Arguments> test2MethodSource() {
        return Stream.of(
                Arguments.of("/users", "{\"login\": \"dol\", \"name\": \"Nick Name\"," +
                        "\"email\":\"mail4@mail.ru\", \"birthday\": \"1986-08-20\"}"),
                //пустое имя
                Arguments.of("/users", "{\"login\": \"dolore\", \"email\":\"mai33l@mail.ru\"," +
                        "\"birthday\": \"1986-08-20\"}"),

                //фильмы
                Arguments.of("/films",  "{ \"name\": \"nisi eiusmod\",  \"description\": \"{}\"," +
                        "\"releaseDate\": \"1967-03-25\",  \"duration\": 100}"),
                //первый фильм граничное условие
                Arguments.of("/films",  "{ \"name\": \"Roundhay Garden Scence\",  \"description\": \"first film!!!\"," +
                        "\"releaseDate\": \"1895-12-28\",  \"duration\": 1}")

        );
    }

    @DisplayName("Put Невалидные данные код ответа 400")
    @MethodSource("test3MethodSource")
    @ParameterizedTest(name = "{index} {0} {1}")
    public void test3_putInvalid_thenReturnsStatus400(String urlTemplate, String body) throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Невалидные данные для put
    private static  Stream<Arguments> test3MethodSource() {
        return Stream.of(
                //почта не может быть пустой
                Arguments.of("/users", "{ \"id\": 1,\"login\": \"dol\", \"name\": \"Dolly\"," +
                        "\"birthday\": \"1946-08-20\"}"),
                //почта без собаки
                Arguments.of("/users", "{ \"id\": 1,\"login\": \"dol\", \"name\": \"Dolly\"," +
                        "\"email\":\"mailmail.ru\", \"birthday\": \"1946-08-20\"}"),
                //невалидная почта
                Arguments.of("/users", "{ \"id\": 1,\"login\": \"dol\", \"name\": \"Dolly\"," +
                        "\"email\":\"mailmail.ru@\", \"birthday\": \"1946-08-20\"}"),
                //пустой запрос
                Arguments.of("/users", ""),
                //логин содержит пробелы
                Arguments.of("/users", "{ \"id\": 1,\"login\": \"dolore ullamco\", \"email\": \"mail@gmail.com\"," +
                        "\"birthday\": \"1980-08-20\"}"),
                //дата рождения в будущем
                Arguments.of("/users", "{ \"id\": 1,\"login\": \"doloreullamco\", \"email\": \"mail@gmail.com\"," +
                        "\"birthday\": \"2100-08-20\"}"),
                //всё неправильно
                Arguments.of("/users", "{ \"id\": 1,\"login\": \"dol dol\", \"email\":\"mailmail.ru\"," +
                        "\"birthday\": \"2200-08-20\"}"),

                //Фильмы
                //без названия
                Arguments.of("/films",  "{ \"id\": 1, \"description\": \"bad film1\"," +
                        "\"releaseDate\": \"2000-10-15\",  \"duration\": 100}"),
                //пусто
                Arguments.of("/films",  ""),
                //отрицательная продолжительность
                Arguments.of("/films",  "{ \"id\": 1, \"name\": \"anti-film\"," +
                        "\"description\": \"anti-universe\", \"releaseDate\": \"1967-03-25\", \"duration\": -100}"),
                //невалидная дата
                Arguments.of("/films",  "{ \"id\": 1, \"name\": \"no film\"," +
                        "\"description\": \"no film test\", \"releaseDate\": \"1895-12-27\", \"duration\": 100}"),
                //длинное описание
                Arguments.of("/films",  "{\"id\": 1," + longFilm() + "}")
        );
    }

    @DisplayName("Put Валидные данные код ответа 200")
    @MethodSource("test4MethodSource")
    @ParameterizedTest(name = "{index} {0} {1}")
    public void test4_putValid_thenReturnsStatusOK(String urlTemplate, String body) throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //Валидные данные для put
    private static  Stream<Arguments> test4MethodSource() {
        return Stream.of(
                Arguments.of("/users", "{\"login\": \"doloreUpdate\", \"name\": \"est adipisicing\", \"id\": 1," +
                                "\"email\": \"mail@yandex.ru\",\"birthday\": \"1976-09-20\"}"),
                Arguments.of("/users", "{\"login\": \"dol\", \"name\": \"Dolly\",  \"id\": 1," +
                        "\"email\":\"mail@mail.ru\", \"birthday\": \"1990-08-20\"}")
        );
    }

    @DisplayName("Put Невалидный ID код ответа 500")
    @MethodSource("test5MethodSource")
    @ParameterizedTest(name = "{index} {0} {1}")
    public void test5_putInvalid_thenReturnsStatus500(String urlTemplate, String body) throws Exception {
        mvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError());
    }

    //Невалидный id для put
    private static  Stream<Arguments> test5MethodSource() {
        return Stream.of(
                //нет id
                Arguments.of("/users", "{\"login\": \"dol\", \"name\": \"Nick Name\"," +
                        "\"email\":\"mail4@mail.ru\", \"birthday\": \"1986-08-20\"}"),
                //отрицательный id
                Arguments.of("/users", "{ \"id\": -100,\"login\": \"dol\", \"name\": \"Dolly\"," +
                        "\"email\":\"mail@mail.ru\", \"birthday\": \"1946-08-20\"}"),

                //отрицательный id
                Arguments.of("/films",  "{ \"id\": -100, \"name\": \"no film\",  \"description\": \"no film test\"," +
                        "\"releaseDate\": \"2000-12-27\",  \"duration\": 100}"),
                //нет id
                Arguments.of("/films",  "{ \"name\": \"nisi eiusmod\",  \"description\": \"{}\"," +
                        "\"releaseDate\": \"1967-03-25\",  \"duration\": 100}")
        );
    }
}