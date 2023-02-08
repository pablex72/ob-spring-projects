package com.example.obspringsecuritycifrado;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class EncryptionTest {

    /**
     * BCrypt que genera su propio
     *
     * El resultado de crifar con BCrypt sera un sting de 60 caracteres:* *
     * $a version*
     * $10 fuerza* se puede modificar -> va de 4 a 31 por defecto 10 ->  perdemos rendimiento a contra de seguridad
     * Los 22 siguientes caracteres son el salt generado  *
     */
    @Test
    void bcryptTest(){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("admin");
        System.out.println(hashedPassword);

        boolean matches = passwordEncoder.matches("admin", hashedPassword);
        System.out.println(matches);
    }

    @Test
    void bcryptCheckMultiplePasswords(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        for (int i = 0; i < 30 ; i++){
            System.out.println(bCryptPasswordEncoder.encode("admin"));
        }
    }

//    @Test
//    void pbkdf2() {
//        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();
//        for (int i = 0; i < 30 ; i++){
//            System.out.println(pbkdf2PasswordEncoder.encode("admin"));
//        }
//    }
//
//    @Test
//    void argon() {
//        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
//        for (int i = 0; i < 30 ; i++){
//            System.out.println(argon2PasswordEncoder.encode("admin"));
//        }
//    }

//    @Test
//    void scrypt() {
//        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
//        for (int i = 0; i < 30 ; i++){
//            System.out.println(sCryptPasswordEncoder.encode("admin"));
//        }
//    }

    void springPasswordEncoders(){

        //String ifForEncode ="bcrypt";

        Map<String, PasswordEncoder> encoders = new HashMap<>();

        encoders.put("bcrypt", new BCryptPasswordEncoder());
//        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
//        encoders.put("argon2", new Argon2PasswordEncoder());
//        encoders.put("scrypt", new SCryptPasswordEncoder());

        // no seguro

        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("sha256", new StandardPasswordEncoder());

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("", encoders);

        String hashedPassword = passwordEncoder.encode("admin");
        System.out.println(hashedPassword);

    }

}
