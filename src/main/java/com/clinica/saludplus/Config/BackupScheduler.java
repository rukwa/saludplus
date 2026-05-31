package com.clinica.saludplus.Config;

import com.clinica.saludplus.service.BackupService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BackupScheduler {

    private final BackupService backupService;

    public BackupScheduler(BackupService backupService) {
        this.backupService = backupService;
    }

    // Se ejecuta a las 12:00 AM todos los días
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleBackup() {
        backupService.createBackup();
    }
}

