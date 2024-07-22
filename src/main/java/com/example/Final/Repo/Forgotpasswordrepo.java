package com.example.Final.Repo;

import com.example.Final.model.ForgotPassword;
import com.example.Final.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Forgotpasswordrepo  extends JpaRepository<ForgotPassword,Integer> {

    @Query("select fb from ForgotPassword fb where fb.otp = ?1 and fb.user = ?2")
    Optional<ForgotPassword> findByOtpandUser(Integer otp, User user);
}
