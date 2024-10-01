package com.odin.core.update.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "core_auth")
@AllArgsConstructor
@NoArgsConstructor
public class Auth {

    @Id
    @Column(name = "customer_id", unique = true)
    private Integer customerId;

    @CreationTimestamp
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;

    @UpdateTimestamp
    @Column(name = "update_timestamp")
    private Timestamp updateTimestamp;

    @Column(name = "is_first_time_login")
    private Boolean isFirstTimeLogin;

    @Column(name = "last_login_timestamp")
    private Timestamp lastLoginTimestamp;

    @Column(name = "is_temp_password")
    private Boolean isTempPassword;

    @Column(name = "password_changed_date")
    private Timestamp passwordChangedDate;

    @Column(name = "password_change_count")
    private Integer passwordChangeCount;

    @Column(name = "password")
    private String password;
    
    @Column(name = "incorrect_password_count")
    private Integer incorrectPasswordCount;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_temp_lock")
    private Boolean isTempLock;

    @Column(name = "is_perm_lock")
    private Boolean isPermLock;

    @Column(name = "temp_lock_count")
    private Integer tempLockCount;

    @Column(name = "perm_lock_count")
    private Integer permLockCount;

    @Column(name = "temp_lock_date")
    private Timestamp tempLockDate;

    @Column(name = "perm_lock_date")
    private Timestamp permLockDate;
}
