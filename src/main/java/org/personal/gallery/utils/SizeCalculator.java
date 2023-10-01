package org.personal.gallery.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SizeCalculator {

    private SizeCalculator() {
      throw new IllegalArgumentException();
    }

    public static long getObjectSize(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            return byteArrayOutputStream.size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
