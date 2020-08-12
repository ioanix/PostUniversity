package repository;

import com.google.gson.Gson;
import domain.Entity;

import java.io.*;
import java.lang.reflect.Type;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.max;

public class JSONFileRepository<T extends Entity> implements IRepository<T> {


    private String fileName;
    private Type type;
    private Gson gson;

    private Map<Integer, T> storage = new HashMap<>();

    private int currentId = 0;

    public JSONFileRepository(String fileName, Type type) {

        this.fileName = fileName;
        this.type = type;
        this.gson = new Gson();
    }


    public void readFile() {

        storage.clear();

        try(FileReader in = new FileReader(fileName)) {

            try(BufferedReader br = new BufferedReader(in)) {

                String line;

                while((line = br.readLine()) != null) {

                    T entity = gson.fromJson(line, type);
                    storage.put(entity.getId(), entity);
                }
            }

        } catch (Exception e) {

            File file = new File("doctors.txt");
            File file1 = new File("patients.txt");


            try {

                if (file.createNewFile() && file1.createNewFile()) {

                    System.out.println("File is created");

                } else {

                    System.out.println("The file already exists");
                }



            } catch(IOException ex) {

                System.out.println("Exception caught");

            }

        }
    }


    public void saveFile() {

        try(FileWriter out = new FileWriter(fileName)) {

            try(BufferedWriter bw = new BufferedWriter(out)) {

                for(T entity : storage.values()) {

                    bw.write(gson.toJson(entity));
                    bw.newLine();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void create(T entity) throws KeyException {

        readFile();

        if(storage.size() == 0) {

            entity.setId(currentId++);

        } else {

            entity.setId(max(storage.keySet()) + 1);
        }


        if(storage.containsKey(entity.getId())) {

            throw new KeyException("The id already exists");
        }

        storage.put(entity.getId(), entity);

        saveFile();


    }

    @Override
    public T read(int id) {

        readFile();
        return storage.get(id);
    }

    @Override
    public List<T> readAll() {

        readFile();
        return new ArrayList<>(storage.values());
    }

    @Override
    public void update(T entity) throws KeyException{

        readFile();

        if(!storage.containsKey(entity.getId())) {

            throw new KeyException("The id does not exist");
        }

        storage.put(entity.getId(), entity);

        saveFile();

    }

    @Override
    public void delete(int id) throws KeyException {

        readFile();

        if(!storage.containsKey(id)) {

            throw new KeyException("The id does not exist");
        }

        storage.remove(id);

        saveFile();

    }
}
