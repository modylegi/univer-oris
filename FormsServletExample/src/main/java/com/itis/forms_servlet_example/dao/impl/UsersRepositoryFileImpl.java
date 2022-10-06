package com.itis.forms_servlet_example.dao.impl;

import com.itis.forms_servlet_example.dao.UsersRepository;
import com.itis.forms_servlet_example.model.User;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static com.itis.forms_servlet_example.context.AppContext.usersRepository;

// TODO: Implement
public class UsersRepositoryFileImpl implements UsersRepository {
    private final List<User> data = new ArrayList<>();
    File file = new File();
    {
        try {
            FileWriter outputfileHeader = new FileWriter(file,true);
            CSVWriter writerHeader = new CSVWriter(outputfileHeader);
            CSVReader csvReader = new CSVReader(new FileReader(file));
            String[] header = {"id",  "firstName", "lastName", "courseName", "age" };
            if(csvReader.readNext() == null){
                writerHeader.writeNext(header);
            }
            writerHeader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() != null) {
            data.removeIf(item -> user.getId().equals(item.getId()));
        } else {
            user.setId(usersRepository.getAll().size());
        }
        try {
            FileWriter outputfileData = new FileWriter(file,true);
            CSVWriter writerData = new CSVWriter(outputfileData);
            String[] dataWriter = {
                    String.valueOf(user.getId()),
                    String.valueOf(user.getFirstName()),
                    String.valueOf(user.getLastName()),
                    String.valueOf(user.getCourseName()),
                    String.valueOf(user.getAge()) };
            writerData.writeNext(dataWriter);
            writerData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        data.add(user);
    }

    @Override
    public List<User> getAll() {
        List<User> listOfSavedUsers = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(file));) {
            String[] line = null;
            int lineCounter = 0;
            while ((line = csvReader.readNext()) != null) {
                lineCounter++;
                if (lineCounter != 1){
                listOfSavedUsers.add(new User(Integer.parseInt(line[0]),line[1],line[2],line[3],Integer.parseInt(line[4])));
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(listOfSavedUsers);
    }

    @Override
    public User getById(int id) {
        return data.stream().filter(item -> item.getId() == id).findFirst().get();
    }

    @Override
    public void delete(int id) {
        data.removeIf(item -> item.getId() == id);
    }
}
