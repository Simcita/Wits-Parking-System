package com.example.myparkingapp;

public class Password {
    public static boolean isValid(String password){
        /*int p1=0,p2=0,p3=0;
        if(password.length() <6){
            return false;
        }
        else {
            for(int i=0; i < password.length();i++){
                if(Character.isLetter(password.charAt(i))){
                    p1 =1;
                }
            }
            for(int i =0;i < password.length();i++){
                if(Character.isDigit(password.charAt(i))){
                    p2 =1;
                }
            }
            for(int i =0;i <password.length();i++){
                char c = password.charAt(i);
                if(c >= 35 && c<= 46 || c==64){
                    p3 =1;
                }
            }
            if(p1 ==1 && p2 ==1 && p3==1){
                return true;
            }else{
                return false;
            }
        }

         */
        return password.length() >= 6;
    }
}
