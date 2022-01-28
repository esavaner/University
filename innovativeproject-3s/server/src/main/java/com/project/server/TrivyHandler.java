package com.project.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TrivyHandler {
    
    public static void startTestcmd(){
    
        try{    
            // Run a shell script
        Process proc = Runtime.getRuntime().exec("docker run --rm  aquasec/trivy -f json --light python:3.4-alpine");
        System.out.println("Success!");

        BufferedReader stdInput = new BufferedReader(new 
        InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new 
        InputStreamReader(proc.getErrorStream()));

        // Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // Read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
             }
    }
}