package uk.bradford.app_project.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;

public class User {

    private String id;
    private String email;

    private CipherSnapshot lastSnapshot;

    private LinkedList<CipherSnapshot> savedSnapshots;

    public User(){

    }
    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(FirebaseUser user) {
        this.id = user.getUid();
        this.email = user.getEmail();
    }

    public CipherSnapshot getLastSnapshot() {
        return lastSnapshot;
    }

    public void setLastSnapshot(CipherSnapshot lastSnapshot) {
        this.lastSnapshot = lastSnapshot;
    }

    public LinkedList<CipherSnapshot> getSavedSnapshots() {
        return savedSnapshots;
    }

    public void setSavedSnapshots(LinkedList<CipherSnapshot> savedSnapshots) {
        this.savedSnapshots = savedSnapshots;
    }

    public String getEmail() {
        return this.email;
    }


    public String getId() {
        return id;
    }

}
