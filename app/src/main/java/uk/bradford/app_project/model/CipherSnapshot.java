package uk.bradford.app_project.model;

import java.util.ArrayList;
import java.util.UUID;


public class CipherSnapshot {

    private String id;

    private ArrayList<ArrayList<String>> ciphersPairs;

    // Empty Constructor necessary to restore data object from prefs file
    public CipherSnapshot() {
    }


    public CipherSnapshot(ArrayList<ArrayList<String>> keyAndMsgArray) {
        this.ciphersPairs = keyAndMsgArray;
        this.id = UUID.randomUUID().toString();

        for (ArrayList<String> keyAndMsg : keyAndMsgArray) {
            if (keyAndMsg.size() != 3)
                throw new IllegalArgumentException("Badly formatted Cipher, only length 2 (key + msg) allowed");
        }
    }


    public int getLength() {
        return ciphersPairs.size();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCiphersPairs(ArrayList<ArrayList<String>> ciphersPairs) {
        this.ciphersPairs = ciphersPairs;
    }

    public String getId() {
        return this.id;
    }

    public ArrayList<ArrayList<String>> getCiphersPairs() {
        return this.ciphersPairs;
    }


}
