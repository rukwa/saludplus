package com.clinica.saludplus.service;

import org.springframework.stereotype.Service;

@Service
public class BackupService {

    public void createBackup() {
        // Ruta donde Laragon guarda el binario de mysql (ajusta según tu instalación)
        String dumpPath = "C:/laragon/bin/mysql/mysql-8.0.30-winx64/bin/mysqldump";
        String dbName = "db_saludplus";
        String dbUser = "root";
        String dbPass = ""; // Laragon suele no tener password por defecto
        String savePath = "C:/backups/backup_saludplus.sql";

        String command = String.format("%s -u %s %s --databases %s -r %s",
                dumpPath, dbUser,
                dbPass.isEmpty() ? "" : "-p" + dbPass,
                dbName, savePath);

        try {
            Process process = Runtime.getRuntime().exec(command);
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup creado con éxito en: " + savePath);
            } else {
                System.err.println("Fallo al crear el backup");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

