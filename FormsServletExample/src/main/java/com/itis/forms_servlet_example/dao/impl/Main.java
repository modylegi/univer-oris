package com.itis.forms_servlet_example.dao.impl;

import com.itis.forms_servlet_example.model.User;
import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.itis.forms_servlet_example.context.AppContext.usersRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println(usersRepository.getAll().toString());


    }
    }

