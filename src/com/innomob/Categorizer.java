package com.innomob;

import java.io.IOException;
import java.io.InputStream;

public class Categorizer {

    private InputStream propIn = null;

    public static void main(String[] args) throws IOException {
        /*
        Categorizer cat = new Categorizer();
        Properties properties = cat.readProperties();

        List<Integer> glitters = cat.convertPropertyStringToList(properties.getProperty("glitter"));
        glitters.stream().filter(x -> x != 0).forEach(System.out::println);
        */

        CategorizerForm.open();


    }


}
